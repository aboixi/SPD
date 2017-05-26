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
	 * @param cif El cif de l'empresa
	 * @return pacients Una col·lecció de pacients amb fulls de control
	 */
	public Collection<PacientJPA> llistarFulls(String cif)throws PersistenceException;
	/**
	 * Mètode per consultar l'expedient
	 * @param idExpedient La id de l'expedient
	 * @return expedient L'expedient
	 */
	public ExpedientJPA consultarExpedient(int idExpedient);
	/**
	 * Mètode que actualitza el full de treball
	 * @param full El full de treball
	 */
	public void modificarFull (FullDeTreballJPA full);
}
