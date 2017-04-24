/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */

package ejb;
import javax.ejb.Remote;

import jpa.EmpresaJPA;

/**
 * EJB remote interface
 */
@Remote
public interface UsuarisNegociRemote {
	public String registrarEmpresa (String cif, String nom, String poblacio, String carrer, String cp, String telefon, String fax,String correu, String clau, String contacte, String tipus);
	public Object login(String usuari, String clau);
	public EmpresaJPA modificarEmpresa (String nif, String nom, String poblacio, String carrer, String cp, String telefon, String fax,String correu, String clau, String contacte);
}
