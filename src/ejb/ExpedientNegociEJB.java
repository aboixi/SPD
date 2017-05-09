/**
 * TFG JEE-SimpleSPD - Component: Expedient Assistencial
 * @author Albert Boix Isern
 */

package ejb;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.ExpedientJPA;
import jpa.PacientJPA;

@Stateless
public class ExpedientNegociEJB implements ExpedientNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	public Collection<PacientJPA> llistarExpedients (String cif){
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
	    return pacients;
	}
	
	public ExpedientJPA consultarExpedient(String cip){
		ExpedientJPA expedient = entman.find(ExpedientJPA.class, cip);
		return expedient;
	}
}