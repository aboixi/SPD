/**
 * TFG JEE-SimpleSPD - Component: Full de Treball
 * @author Albert Boix Isern
 */

package ejb;

import java.util.ArrayList;
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

import jpa.ExpedientJPA;
import jpa.FullDeControlJPA;
import jpa.FullDeTreballJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;

/**
 * EJB Session Bean Class 
 */
@Stateless
public class FullTreballNegociEJB implements FullTreballNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	/**
	 * Mètode que consulta els fulls de controls vinculats a l'empresa.
	 * @param cif El cif de l'empresa
	 * @return pacients Una col·lecció de pacients amb fulls de control
	 */
	@SuppressWarnings("unchecked")
	public Collection<PacientJPA> llistarFulls(String cif) throws PersistenceException{
		Calendar calendar = new GregorianCalendar();
		Date time = new Date();
		calendar.setTime(time);
		int numSetmana = calendar.get(Calendar.WEEK_OF_YEAR);
		int numAny = calendar.get(Calendar.YEAR);	
		
		ArrayList<PacientJPA> pacients = new ArrayList<PacientJPA>();
		Collection<PacientJPA> llistaPacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
		PacientJPA pacient = null;
		Iterator <PacientJPA> iter = llistaPacients.iterator();
		while (iter.hasNext()){
			ArrayList<FullDeControlJPA> fullsControl = new ArrayList<FullDeControlJPA>();
			pacient = iter.next();
			if((pacient.getExpedient().getTractaments().size()>0)&&(pacient.isAutoritzacio())&&(!(pacient.isExitus()))&&(!(pacient.isHospitalitzat()))&&(pacient.isSpd())){
				String blister = pacient.getCip().concat(String.valueOf(numAny)).concat("-").concat(String.valueOf(numSetmana));
				try{
					fullsControl = (ArrayList<FullDeControlJPA>) entman.createQuery("FROM FullDeControlJPA f WHERE f.idBlister = '" + blister +"' ").getResultList();
				}catch (PersistenceException e) {
					System.out.println(e);
				}
				if (fullsControl.isEmpty()){
				pacients.add(pacient);
				}
			}
		}
		return pacients;		
	}
	/**
	 * Mètode per consultar l'expedient
	 * @param idExpedient La id de l'expedient
	 * @return expedient L'expedient
	 */
	public ExpedientJPA consultarExpedient(int idExpedient){
		ExpedientJPA expedient = entman.find(ExpedientJPA.class, idExpedient);
		return expedient;
	}
	/**
	 * Mètode que actualitza el full de treball
	 * @param full El full de treball
	 */
	public void modificarFull (FullDeTreballJPA full){
		//Full de treball
		FullDeTreballJPA fullTreball = entman.find(FullDeTreballJPA.class, full.getExpedient().getId());
		if (fullTreball==null){
			entman.persist(full);
		}else{
			entman.merge(full);
		}
		//Expedient
		ExpedientJPA expedient = full.getExpedient();
		TractamentJPA tractament = null;
		//Tractaments
		Collection<TractamentJPA> tractaments=expedient.getTractaments();
		Iterator<TractamentJPA> iter = tractaments.iterator();
		while (iter.hasNext()){
			tractament=iter.next();
			entman.merge(tractament);
		}
	}
}
	