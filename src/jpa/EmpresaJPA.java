/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 * JPA Class EmpresaJPA
 */

@Entity
@Table(name="spd.empresa")
public class EmpresaJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cif")
	private String cif;
	@Column(name = "nom")
	private String nom;
	@Column(name = "poblacio")
	private String poblacio;
	@Column(name = "carrer")
	private String carrer;
	@Column(name = "cp")
	private String cp;
	@Column(name = "telefon")
	private String telefon;
	@Column(name = "fax")
	private String fax;
	@Column(name = "correu")
	private String correu;
	@Column(name = "clau")
	private String clau;
	@Column(name = "contacte")
	private String contacte;
	@Column(name = "tipus")
	private String tipus;
	
	/**
	 * Relacions de persistencia
	 */

	@OneToMany(targetEntity=UsuariEmpresaJPA.class,mappedBy="empresa", cascade={CascadeType.ALL},orphanRemoval=true)
	private Collection<UsuariEmpresaJPA> usuarisEmpresa;
	@OneToMany(targetEntity=PacientJPA.class,mappedBy="residencia", cascade={CascadeType.ALL},orphanRemoval=true)
	private Collection<PacientJPA> pacientsResidencia;
	@OneToMany(targetEntity=PacientJPA.class,mappedBy="farmacia", cascade={CascadeType.ALL},orphanRemoval=true)
	private Collection<PacientJPA> pacientsFarmacia;

	/**
	 * Constructor
	 */
	public EmpresaJPA() {
		super();
	}
	
	/**
	 * Constructor with attributes
	 */
	public EmpresaJPA(String cif, String nom, String poblacio, String carrer, String cp, String telefon, String fax,
			String correu, String clau, String contacte, String tipus) {
		super();
		this.cif = cif;
		this.nom = nom;
		this.poblacio = poblacio;
		this.carrer = carrer;
		this.cp = cp;
		this.telefon = telefon;
		this.fax = fax;
		this.correu=correu;
		this.clau=clau;
		this.contacte = contacte;
		this.tipus = tipus;
	}

	/**
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the poblacio
	 */
	public String getPoblacio() {
		return poblacio;
	}

	/**
	 * @param poblacio the poblacio to set
	 */
	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}

	/**
	 * @return the carrer
	 */
	public String getCarrer() {
		return carrer;
	}

	/**
	 * @param carrer the carrer to set
	 */
	public void setCarrer(String carrer) {
		this.carrer = carrer;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the telefon
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * @param telefon the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the correu
	 */
	public String getCorreu() {
		return correu;
	}

	/**
	 * @param correu the correu to set
	 */
	public void setCorreu(String correu) {
		this.correu = correu;
	}

	/**
	 * @return the clau
	 */
	public String getClau() {
		return clau;
	}

	/**
	 * @param clau the clau to set
	 */
	public void setClau(String clau) {
		this.clau = clau;
	}

	/**
	 * @return the contacte
	 */
	public String getContacte() {
		return contacte;
	}

	/**
	 * @param contacte the contacte to set
	 */
	public void setContacte(String contacte) {
		this.contacte = contacte;
	}

	/**
	 * @return the tipus
	 */
	public String getTipus() {
		return tipus;
	}

	/**
	 * @param tipus the tipus to set
	 */
	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	/**
	 * @return the usuarisEmpresa
	 */
	public Collection<UsuariEmpresaJPA> getUsuarisEmpresa() {
		return usuarisEmpresa;
	}

	/**
	 * @param usuarisEmpresa the usuarisEmpresa to set
	 */
	public void setUsuarisEmpresa(Collection<UsuariEmpresaJPA> usuarisEmpresa) {
		this.usuarisEmpresa = usuarisEmpresa;
	}

	/**
	 * @return the pacientsResidencia
	 */
	public Collection<PacientJPA> getPacientsResidencia() {
		return pacientsResidencia;
	}

	/**
	 * @param pacientsResidencia the pacientsResidencia to set
	 */
	public void setPacientsResidencia(Collection<PacientJPA> pacientsResidencia) {
		this.pacientsResidencia = pacientsResidencia;
	}

	/**
	 * @return the pacientsFarmacia
	 */
	public Collection<PacientJPA> getPacientsFarmacia() {
		return pacientsFarmacia;
	}

	/**
	 * @param pacientsFarmacia the pacientsFarmacia to set
	 */
	public void setPacientsFarmacia(Collection<PacientJPA> pacientsFarmacia) {
		this.pacientsFarmacia = pacientsFarmacia;
	}

	/**
	 * @param cif the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}
}