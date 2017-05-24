/**
 * TFG JEE-SimpleSPD - Component: Pacients
 * @author Albert Boix Isern
 */
package ejb;

import java.util.Collection;

import javax.persistence.PersistenceException;

import jpa.EmpresaJPA;
import jpa.PacientJPA;

/**
 * EJB Session Bean Interface
 */
public interface PacientsNegociRemote {
	/**
	 * Mètode que consulta els pacients vinculats a una empresa
	 * @param cif de l'empresa
	 * @return col·lecció de pacients
	 */
	public Collection<PacientJPA> llistarPacients (String cif);
	/**
	 * Mètode que elimina un pacient
	 * @param cip del pacient i cif de l'empresa
	 * @return una col·lecció amb els pacients que queden després de la eliminació
	 */
	public Collection<PacientJPA> eliminarPacient(String cip, String cif);
	/**
	 * Mètode per modificar les dades personals d'un pacient
	 * @param tots els camps excepte el cip
	 * @return un missatge amb el resultat del procés en forma de String
	 */
	public String modificarPacient(String cif, String cip, String nom, String cognom1, String cognom2, String nomFarmacia, String farmacia, String malalties,String alergies, String metge, boolean autoritzacio, boolean spd, boolean hospitalitzat, boolean exitus) throws PersistenceException;
	/**
	 * Mètode per consultar les farmàcies donades d'alta al sistema
	 * @return una col·lecció de farmàcies
	 */
	public Collection<EmpresaJPA> consultarFarmacies();
	/**
	 * Mètode per donar d'alta un pacient
	 * @param les dades del pacient
	 * @return un missatge amb el resultat del procés en forma de String
	 */
	public String crearPacient (String cip, String nom, String cognom1, String cognom2, String malalties,String alergies, String metge, String cifResidencia, String nomResidencia, String cifFarmacia, String nomFarmacia, boolean autoritzacio, boolean spd, boolean hospitalitzat, boolean exitus)throws PersistenceException;
	/**
	 * Mètode per consultar una empresa
	 * @param el cif de l'empresa
	 * @return la empresa
	 */
	public EmpresaJPA consultarEmpresa(String cif);
}
