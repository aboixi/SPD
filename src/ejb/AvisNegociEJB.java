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

import jpa.AvisJPA;
import jpa.EmpresaJPA;

@Stateless
public class AvisNegociEJB implements AvisNegociRemote{
	@PersistenceContext(unitName="SPD")
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	public String crearAvis(String cifE, String cifR, String tipus, String descripcio){
		AvisJPA avis = new AvisJPA();
		avis.setCifEmisor(cifE);
		avis.setCifReceptor(cifR);
		avis.setTipus(tipus);
		avis.setDescripcio(descripcio);
		entman.persist(avis);
		return "procesCorrecte";
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AvisJPA> llistarAvisosEmessos(String cif){
		Collection<AvisJPA> avisos = null;
		avisos=(Collection<AvisJPA>) entman.find(AvisJPA.class, cif);
		return avisos;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AvisJPA> llistarAvisosRebuts(String cif){
		Collection<AvisJPA> avisos = null;
		avisos=(Collection<AvisJPA>) entman.find(AvisJPA.class, cif);
		return avisos;
	}
	
	public String eliminarAvis (String idAvis){
		entman.remove(idAvis);
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
