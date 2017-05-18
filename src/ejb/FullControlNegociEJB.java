/**
 * TFG JEE-SimpleSPD - Component: Full de Treball
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

import jpa.BlisterJPA;
import jpa.ExpedientJPA;
import jpa.FullDeControlJPA;
import jpa.FullDeTreballJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;


@Stateless
public class FullControlNegociEJB implements FullControlNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
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
	
	@SuppressWarnings("unchecked")
	public Collection<BlisterJPA> consultarBlisters (String cip){
		Collection<BlisterJPA> blisters = entman.createQuery("FROM BlisterJPA b WHERE b.cip = '" + cip +"' ").getResultList();
	    return blisters;
	}

	public Collection<PacientJPA> llistarFulls (String cif){
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
	    return pacients;
	}
	
	//Pendent d'esborrar
	@SuppressWarnings("unchecked")
	public Collection<FullDeControlJPA> consultarFulls(String cip){
		PacientJPA pacient = entman.find(PacientJPA.class, cip);
		int expedient = -1;
		expedient=pacient.getExpedient().getId();
		Collection<FullDeControlJPA> fulls = entman.createQuery("FROM FullDeControlJPA f WHERE f.idFullTreball = '" + expedient +"' ").getResultList();
	//	HashSet<FullDeControlJPA> fullsReturn=new HashSet<FullDeControlJPA>();
		return fulls;
	}
	public void validar(int idFull, String dniV){
		FullDeTreballJPA full = entman.find(FullDeTreballJPA.class,idFull);
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
	}
}