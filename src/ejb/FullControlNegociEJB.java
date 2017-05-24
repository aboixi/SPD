/**
 * TFG JEE-SimpleSPD - Component: Full de Control
 * @author Albert Boix Isern
 */
package ejb;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.BlisterJPA;
import jpa.ExpedientJPA;
import jpa.FullDeControlJPA;
import jpa.FullDeTreballJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;

/**
 * EJB Session Bean Class 
 */
@Stateless
public class FullControlNegociEJB implements FullControlNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	/**
	 * Mètode que crea un full de control
	 * @param La id del full de treball associat, i el dni del treballador que el prepara.
	 */
	public void creaFullControl(int idFull, String dniP){
		FullDeTreballJPA full = entman.find(FullDeTreballJPA.class,idFull);
		ExpedientJPA expedient = full.getExpedient();

		Calendar calendar = new GregorianCalendar();
		Date time = new Date(); 
		calendar.setTime(time);  
		int numSetmana = calendar.get(Calendar.WEEK_OF_YEAR);
		int numAny = calendar.get(Calendar.YEAR);
		
		String idBlister = expedient.getPacient().getCip().concat(String.valueOf(numAny)).concat("-").concat(String.valueOf(numSetmana));
		
		BlisterJPA blister = entman.find(BlisterJPA.class, idBlister);
		
		if(blister==null){
			blister = new BlisterJPA(expedient.getPacient().getCip(),numSetmana ,numAny,dniP);
			entman.persist(blister);
		}
		
		TractamentJPA tractament = null;
		Collection<TractamentJPA> tractaments = expedient.getTractaments();
		Iterator <TractamentJPA> iter = tractaments.iterator();
		while (iter.hasNext()){
			tractament=iter.next();
			FullDeControlJPA fullControl = new FullDeControlJPA(dniP,blister.getIdBlister(),tractament, numSetmana);
			blister.getFullsControl().add(fullControl);
			entman.persist(fullControl);
		}
		entman.merge(blister);
	}
	/**
	 * Mètode que consulta els blísters associats a un pacient
	 * @param el cip del pacient
	 * @return una col·lecció amb els blísters
	 */
	@SuppressWarnings("unchecked")
	public Collection<BlisterJPA> consultarBlisters (String cip){
		Collection<BlisterJPA> blisters = entman.createQuery("FROM BlisterJPA b WHERE b.cip = '" + cip +"' ").getResultList();
	    return blisters;
	}
	/**
	 * Mètode per consultar els fulls de treball vinculats a una empresa
	 * @param el cif de l'empresa
	 * @return una col·lecció de pacients
	 */
	public Collection<PacientJPA> llistarFulls (String cif){
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
	    return pacients;
	}

	/**
	 * Mètode per validar un full de treball
	 * @param la id del full de treball i el dni del treballador que realitza la validació
	 * @return Un missatge amb el resultat del procés en forma de String
	 */
	public String validar(int idFull, String dniV) throws PersistenceException{
		FullDeTreballJPA full = null;
		try{
			full = entman.find(FullDeTreballJPA.class,idFull);
		}catch (PersistenceException e){
			System.out.println(e);
		}
		if (full==null){
			return "fullControlNoTrobat";
		}else{
			ExpedientJPA expedient = full.getExpedient();
			
			Calendar calendar = new GregorianCalendar();
			Date time = new Date(); 
			calendar.setTime(time);  
			int numSetmana = calendar.get(Calendar.WEEK_OF_YEAR);
			int numAny = calendar.get(Calendar.YEAR);
			
			String idBlister = expedient.getPacient().getCip().concat(String.valueOf(numAny)).concat("-").concat(String.valueOf(numSetmana));
			
			BlisterJPA blister = entman.find(BlisterJPA.class, idBlister);
			blister.setValidatPer(dniV);
			entman.merge(blister);
			return "procesCorrecte";
		}
	  }
}
