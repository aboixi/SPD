package ejb;

import java.util.Collection;

import javax.persistence.PersistenceException;

import jpa.AvisJPA;
import jpa.EmpresaJPA;

public interface AvisNegociRemote {
	public String crearAvis(String cifE, String cifR, String tipus, String descripcio);
	public Collection<AvisJPA> llistarAvisos(String cif)throws PersistenceException;
	public Collection<AvisJPA> llistarAvisosRebuts(String cif);
	public String eliminarAvis (String idAvis);
	public Collection<EmpresaJPA> consultaEmpreses(String cif);
}
