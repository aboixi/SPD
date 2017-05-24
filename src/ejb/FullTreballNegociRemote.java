/**
 * TFG JEE-SimpleSPD - Component: Full de Treball
 * @author Albert Boix Isern
 */
package ejb;

import java.util.Collection;
import javax.persistence.PersistenceException;

import jpa.ExpedientJPA;
import jpa.FullDeTreballJPA;
import jpa.PacientJPA;

/**
 * EJB Session Bean Interface
 */
public interface FullTreballNegociRemote {
	/**
	 * Mètode que consulta els fulls de controls vinculats a l'empresa.
	 * @param cif de l'empresa
	 * @return una col·lecció de pacients amb fulls de control
	 */
	public Collection<PacientJPA> llistarFulls(String cif)throws PersistenceException;
	/**
	 * Mètode per consultar l'expedient
	 * @param la id de l'expedient
	 * @return l'expedient
	 */
	public ExpedientJPA consultarExpedient(int idExpedient);
	/**
	 * Mètode que actualitza el full de treball
	 * @param el full de treball
	 */
	public void modificarFull (FullDeTreballJPA full);
}
