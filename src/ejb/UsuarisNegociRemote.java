/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */

package ejb;
import java.util.Collection;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import jpa.EmpresaJPA;
import jpa.UsuariEmpresaJPA;

/**
 * EJB Session Bean Interface
 */
@Remote
public interface UsuarisNegociRemote {
	/**
	 *Mètode per registrar una nova empresa al sistema
	 *@return String Un missatge amb el resultat del procés en forma de String 
	 */
	public String registrarEmpresa (String cif, String nom, String poblacio, String carrer, String cp, String telefon, String fax,String correu, String clau, String contacte, String tipus)throws PersistenceException;
	/**
	 * Mètode per identificar-se al sistema. Es comprova si es tracta d'una empresa o d'un usuari.
	 * @param usuari Nom d'usuari 
	 * @param clau Clau d'accés
	 * @return String Si el la identificació és correcta, retorna l'objecte de qui s'ha identificat, usuari o empresa.
	 * @return String Si la identificació és incorrecta, retorna un usuari amb els camps "invalid" 
	 */
	public Object login(String usuari, String clau)throws PersistenceException;
	/**
	 * Mètode per modificar les dades de l'empresa
	 * @return empresa L'objecte modificat
	 */
	public EmpresaJPA modificarEmpresa (String nif, String nom, String poblacio, String carrer, String cp, String telefon, String fax,String correu, String clau, String contacte);
	/**
	 * Mètode per donar d'alta un usuari d'una empresa al sistema.
	 * @return String Un missatge amb el resultat del procés en forma de String
	 */
	public String crearUsuari (String dni, String nom, String cognom1, String cognom2, String telefon, String empresa, String tipus);
	/**
	 * Mètode per consultar els usuari vinculats a l'empresa
	 * @param cif El cif de l'empresa
	 * @return usuaris La col·lecció d'usuaris
	 */
	public Collection<UsuariEmpresaJPA> llistarUsuaris (String cif); 
	/**
	 * Mètode per eliminar un usuari vinculat a l'empresa
	 * @param cif El cif de l'empresa.
	 * @param dni El dni de l'usuari que es vol eliminar.
	 * @return usuaris Una col·lecció amb els usuaris de l'empresa després de la eliminació.
	 */
	public Collection<UsuariEmpresaJPA> eliminarUsuari(String cif, String dni);
	/**
	 * Mètode per modificar les dades d'un usuari. No es pot modificar ni el nom, ni el nom d'usuari
	 * @return String Un missatge amb el resultat del procés en forma de String
	 */
	public String modificarUsuari(String dni, String nom, String cognom1, String cognom2, String telefon, String clau) throws PersistenceException;
}
