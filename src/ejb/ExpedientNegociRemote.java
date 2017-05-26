/**
 * TFG JEE-SimpleSPD - Component: Expedient Assistencial
 * @author Albert Boix Isern
 */
package ejb;

import java.util.Collection;
import java.util.Date;
import javax.persistence.PersistenceException;

import jpa.ExpedientJPA;
import jpa.MedicamentJPA;
import jpa.PacientJPA;

/**
 * EJB Session Bean Interface 
 */
public interface ExpedientNegociRemote {

	/**
	 * Mètode que consulta, donat el cif d'una empresa, els pacients que té vinculats.
	 * Ja sigui com a resident d'una residència o estigui assignat a la farmàcia.
	 * @param cif de l'empresa
	 * @return pacients Una col·lecció amb els pacients.
	 */
	public Collection<PacientJPA> llistarExpedients(String cif);
	/**
	 * Mètode que consulta l'expedient donada la seva id
	 * @param idExpedient Id de l'expedient
	 * @return expedient L'expedient 
	 */
	public ExpedientJPA consultarExpedient(int idExpedient);
	/**
	 * Mètode que agrega un tractament a un pacient
	 * Un missatge en forma de String amb el resultat de la operació
	 */
	public String agregarTractament(String idExpedient, String cn,Date dInici, String qEntera, String qFraccio, boolean esmorcar, boolean dinar, boolean sopar, boolean dormir,boolean dill, boolean dima, boolean dime, boolean dijo, boolean dive, boolean diss, boolean dium, boolean foraBlister) throws PersistenceException;
	/**
	 * Mètode que elimina el tractament d'un pacient donada la seva id
	 * @param idTractament La id del tractament
	 */
	public void eliminarTractament(int id)throws PersistenceException;
	/**
	 * Mètode que busca a la base de dades els medicaments
	 * @param paraula La paraula clau per realitzar la recerca
	 * @return medicaments Una col·lecció amb els medicaments trobats.
	 */
	public Collection<MedicamentJPA> buscarMedicaments(String paraula); 
}
