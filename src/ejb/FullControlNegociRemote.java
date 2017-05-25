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
	 * @param La id del full de treball associat, i el dni del treballador que el prepara.
	 */
	public void creaFullControl(int idFull, String dniP);
	/**
	 * Mètode que consulta els blísters associats a un pacient
	 * @param el cip del pacient
	 * @return una col·lecció amb els blísters
	 */
	public Collection<BlisterJPA> consultarBlisters (String cip);
	/**
	 * Mètode per consultar els fulls de treball vinculats a una empresa
	 * @param el cif de l'empresa
	 * @return una col·lecció de pacients
	 */
	public Collection<PacientJPA> llistarFulls (String cif);
	/**
	 * Mètode per validar un full de treball
	 * @param la id del full de treball i el dni del treballador que realitza la validació
	 * @return Un missatge amb el resultat del procés en forma de String
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
	 * @param el cif de l'empresa
	 * @return empresa La empresa
	 */
	public EmpresaJPA consultarEmpresa(String cif);
}
