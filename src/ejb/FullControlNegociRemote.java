/**
 * TFG JEE-SimpleSPD - Component: Full de Control
 * @author Albert Boix Isern
 */
package ejb;

import java.util.Collection;
import javax.persistence.PersistenceException;

import jpa.BlisterJPA;
import jpa.EmpresaJPA;
import jpa.PacientJPA;

/**
 * EJB Session Bean Interface
 */
public interface FullControlNegociRemote {
	/**
	 * Mètode que crea un full de control
	 * @param idFull La id del full de treball associat.
	 * @param dniP El dni del treballador que el prepara.
	 */
	public void creaFullControl(int idFull, String dniP);
	/**
	 * Mètode que consulta els blísters associats a un pacient
	 * @param cip El cip del pacient
	 * @return blisters Una col·lecció amb els blísters
	 */
	public Collection<BlisterJPA> consultarBlisters (String cip);	
	/**
	 * Mètode per consultar els fulls de treball vinculats a una empresa
	 * @param cif El cif de l'empresa
	 * @return pacients Una col·lecció de pacients
	 */
	public Collection<PacientJPA> llistarFulls (String cif);
	/**
	 * Mètode per validar un full de treball
	 * @param idFull La id del full de treball i el dni del treballador que realitza la validació
	 * @param dniV El dni del treballador que realitza la validació
	 * @return String Un missatge amb el resultat del procés en forma de String
	 */
	public String validar(int idFull, String dniV)throws PersistenceException;
	/**
	 * Mètode que consulta un blíster a la base de dades
	 * @param idBlister
	 * @return blister
	 */
	public BlisterJPA imprimir(String idBlister)throws PersistenceException;
	/**
	 * Mètode per consultar una empresa
	 * @param cif El cif de l'empresa
	 * @return empresa La empresa
	 */
	public EmpresaJPA consultarEmpresa(String cif);
}
