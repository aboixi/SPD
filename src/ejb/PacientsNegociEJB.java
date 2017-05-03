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
import javax.persistence.PersistenceException;
import jpa.EmpresaJPA;
import jpa.PacientJPA;


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
	
	public String modificarPacient(String cip, String nom, String cognom1, String cognom2, String farmacia, String malalties, 
			String alergies, String metge, boolean autoritzacio, boolean spd, boolean hospitalitzat, boolean exitus)throws PersistenceException{
		PacientJPA pacient = entman.find(PacientJPA.class, cip);
		EmpresaJPA empresa = entman.find(EmpresaJPA.class, farmacia);
		pacient.setNom(nom);
		pacient.setCognom1(cognom1);
		pacient.setCognom2(cognom2);
		pacient.setFarmacia(farmacia);
		pacient.setNomFarmacia(empresa.getNom());
		pacient.setMalalties(malalties);
		pacient.setAlergies(alergies);
		pacient.setMetge(metge);
		pacient.setSpd(spd);
		try{
			entman.merge(pacient);
			entman.flush();
			entman.refresh(pacient);
			return "canviCorrecte";
		}catch (PersistenceException e) {
			System.out.println(e);
			return "persistenceException";
		}
	}
	public Collection<EmpresaJPA> consultarFarmacies(){
		@SuppressWarnings("unchecked")
		Collection<EmpresaJPA> farmacies = entman.createQuery("FROM EmpresaJPA e WHERE e.tipus = 'Farmacia'").getResultList();
		return farmacies;
	}
}