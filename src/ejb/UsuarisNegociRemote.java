/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */

package ejb;
import java.util.Collection;

import javax.ejb.Remote;

import jpa.EmpresaJPA;
import jpa.UsuariEmpresaJPA;

/**
 * EJB remote interface
 */
@Remote
public interface UsuarisNegociRemote {
	public String registrarEmpresa (String cif, String nom, String poblacio, String carrer, String cp, String telefon, String fax,String correu, String clau, String contacte, String tipus);
	public Object login(String usuari, String clau);
	public EmpresaJPA modificarEmpresa (String nif, String nom, String poblacio, String carrer, String cp, String telefon, String fax,String correu, String clau, String contacte);
	public String crearUsuari (String dni, String nom, String cognom1, String cognom2, String telefon, String empresa, String tipus);
	public Collection<UsuariEmpresaJPA> llistarUsuaris (String cif); 
	public Collection<UsuariEmpresaJPA> eliminarUsuari(String cif, String dni);
}
