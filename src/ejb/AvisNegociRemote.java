/**
 * TFG JEE-SimpleSPD - Component: Avis
 * @author Albert Boix Isern
 */

package ejb;

import java.util.Collection;
import javax.persistence.PersistenceException;

import jpa.AvisJPA;
import jpa.EmpresaJPA;

/**
 * EJB Session Bean Interface
 */
public interface AvisNegociRemote {
	/**
	 * Mètode per crear un nou avís.
	 * @return un missatge en forma de String.
	 */
	public String crearAvis(String cifE, String cifR, String tipus, String descripcio);
	/**
	 * Mètode per consultar tots els avisos on la empresa apareix com a emissor o receptor.
	 * @return una colecció amb els missatges.
	 */
	public Collection<AvisJPA> llistarAvisos(String cif)throws PersistenceException;
	/**
	 * Mètode per eliminar un avís.
	 * * @return Un missatge en forma de String.
	 */
	public String eliminarAvis (int idAvis);
	/**
	 * Mètode que consulta totes les empreses i excepte la que realitza la consulta.
	 * @return Una col·lecció amb les empresses.
	 */
	public Collection<EmpresaJPA> consultaEmpreses(String cif);
	/**
	 * Mètode per canviar l'estat d'un avís.
	 * @return Un missatge en forma de String.
	 */
	public String canviarEstatAvis(int i);
}
