/**
 * TFG JEE-SimpleSPD - Component: Full de Treball
 * @author Albert Boix Isern
 */

package ejb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		
		Date dia = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dia);
		int numSetmana = cal.getWeekYear();
		SimpleDateFormat formatData = new SimpleDateFormat("yyyy");
		int numAny = Integer.parseInt(formatData.format(dia).toString());
		
		String idBlister = expedient.getPacient().getCip().concat(String.valueOf(numSetmana)).concat(String.valueOf(numAny));
		TractamentJPA tractament = null;
		Collection<TractamentJPA> tractaments = expedient.getTractaments();
		Iterator <TractamentJPA> iter = tractaments.iterator();
		while (iter.hasNext()){
			tractament=iter.next();
			FullDeControlJPA fullControl = new FullDeControlJPA(dniP,idBlister,tractament, Calendar.WEEK_OF_YEAR);
			entman.persist(fullControl);
		}	
	}
}