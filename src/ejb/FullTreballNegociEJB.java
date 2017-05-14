/**
 * TFG JEE-SimpleSPD - Component: Full de Treball
 * @author Albert Boix Isern
 */

package ejb;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jpa.ExpedientJPA;
import jpa.MedicamentJPA;
import jpa.PacientJPA;


@Stateless
public class FullTreballNegociEJB implements FullTreballNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	public Collection<PacientJPA> llistarFulls(String cif){
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
		return pacients;		
	}
	public ExpedientJPA consultarExpedient(int idExpedient){
		ExpedientJPA expedient = entman.find(ExpedientJPA.class, idExpedient);
		return expedient;
	}
}
	