/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */

package ejb;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.AvisJPA;
import jpa.EmpresaJPA;

@Stateless
public class AvisNegociEJB implements AvisNegociRemote{
	@PersistenceContext(unitName="SPD")
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	public String crearAvis(String cifE, String nomR, String tipus, String descripcio){
		String query = "SELECT e.cif "+"FROM EmpresaJPA e WHERE e.nom = '" + nomR +"'";
		String cifR = entman.createQuery(query, String.class).getSingleResult();	
		query = "SELECT e.nom "+"FROM EmpresaJPA e WHERE e.cif = '" + cifE +"'";
		String nomE = entman.createQuery(query, String.class).getSingleResult();	
		AvisJPA avis = new AvisJPA();
		avis.setCifEmisor(cifE);
		avis.setNomEmisor(nomE);
		avis.setCifReceptor(cifR);
		avis.setNomReceptor(nomR);
		avis.setTipus(tipus);
		avis.setDescripcio(descripcio);
		avis.setEstat("PENDENT");
		entman.persist(avis);
		return "procesCorrecte";
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AvisJPA> llistarAvisos(String cif)throws PersistenceException{
		try{
			Collection<AvisJPA> avisos = entman.createQuery("FROM AvisJPA a WHERE a.cifEmisor = '" + cif +"' "+" OR a.cifReceptor = '" + cif +"'").getResultList();
			return avisos;
		}catch (PersistenceException e){
			System.out.println(e);
		}
		return null;		
	}
	
	public String eliminarAvis (int idAvis){
		AvisJPA avis = entman.find(AvisJPA.class, idAvis);
		entman.remove(avis);
		return "procesCorrecte";
	}
	
	public String canviarEstatAvis(int idAvis){
		AvisJPA avis = entman.find(AvisJPA.class, idAvis);
		avis.setEstat("LLEGIT");
		return "procesCorrecte";
	}
	
	@SuppressWarnings("unchecked")
	public Collection<EmpresaJPA> consultaEmpreses(String cif){
		Collection<EmpresaJPA> empreses = null;
		EmpresaJPA emp = null;
		empreses = entman.createQuery("FROM EmpresaJPA").getResultList();
		emp = entman.find(EmpresaJPA.class, "00000000F");
		empreses.remove(emp);
		emp = entman.find(EmpresaJPA.class, "cif");
		empreses.remove(emp);
		return empreses;
	}
}
