/**
 * TFG JEE-SimpleSPD - Component: Avis
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

/**
 * EJB Session Bean Class 
 */
@Stateless
public class AvisNegociEJB implements AvisNegociRemote{
	@PersistenceContext(unitName="SPD")
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	/**
	 * Mètode per crear un nou avís.
	 * @return un missatge en forma de String.
	 */
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
	
	/**
	 * Mètode per consultar tots els avisos on la empresa apareix com a emissor o receptor.
	 * @return una colecció amb els missatges.
	 */
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
	
	/**
	 * Mètode per eliminar un avís.
	 * * @return Un missatge en forma de String.
	 */
	public String eliminarAvis (int idAvis){
		AvisJPA avis = entman.find(AvisJPA.class, idAvis);
		entman.remove(avis);
		return "procesCorrecte";
	}
	
	/**
	 * Mètode per canviar l'estat d'un avís.
	 * @return Un missatge en forma de String.
	 */
	public String canviarEstatAvis(int idAvis){
		AvisJPA avis = entman.find(AvisJPA.class, idAvis);
		avis.setEstat("LLEGIT");
		return "procesCorrecte";
	}
	
	/**
	 * Mètode que consulta totes les empreses i excepte la que realitza la consulta.
	 * @return Una col·lecció amb les empresses.
	 */
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
