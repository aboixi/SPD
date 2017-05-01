/**
 * TFG JEE-SimpleSPD - Component: Usuaris
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

import jpa.EmpresaJPA;
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;


@Stateless
public class PacientsNegociEJB implements PacientsNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	public Collection<PacientJPA> llistarPacients (String cif){
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
	    return pacients;
	}
	
	public Collection<PacientJPA> eliminarPacient(String cip, String cif){
		EmpresaJPA empresa=entman.find(EmpresaJPA.class, cif);
		PacientJPA pacient = entman.find(PacientJPA.class, cip);
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
		if (empresa.getTipus().equals("Residencia")
				&& pacient.getResidencia().equals(empresa.getCif())){
			Iterator<PacientJPA> iter = pacients.iterator();
			boolean trobat = false;
			while (iter.hasNext()&&!(trobat)){
				pacient = iter.next();
				if(pacient.getCip().equals(cip)){
					entman.remove(pacient);
					pacients.remove(pacient);
					trobat=true;
				}
			}
			return pacients;
		}else{
			return pacients;
		}	
	}
}