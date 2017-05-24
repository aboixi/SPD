/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 * JPA Classe EmpresaJPA
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
	 * Relacions de persistència
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
	 * Constructor amb paràmetres
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
	 * Getters i setters
	 */
	public String getCif() {
		return cif;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPoblacio() {
		return poblacio;
	}

	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}

	public String getCarrer() {
		return carrer;
	}

	public void setCarrer(String carrer) {
		this.carrer = carrer;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCorreu() {
		return correu;
	}

	public void setCorreu(String correu) {
		this.correu = correu;
	}

	public String getClau() {
		return clau;
	}

	public void setClau(String clau) {
		this.clau = clau;
	}

	public String getContacte() {
		return contacte;
	}

	public void setContacte(String contacte) {
		this.contacte = contacte;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public Collection<UsuariEmpresaJPA> getUsuarisEmpresa() {
		return usuarisEmpresa;
	}

	public void setUsuarisEmpresa(Collection<UsuariEmpresaJPA> usuarisEmpresa) {
		this.usuarisEmpresa = usuarisEmpresa;
	}

	public Collection<PacientJPA> getPacientsResidencia() {
		return pacientsResidencia;
	}

	public void setPacientsResidencia(Collection<PacientJPA> pacientsResidencia) {
		this.pacientsResidencia = pacientsResidencia;
	}

	public Collection<PacientJPA> getPacientsFarmacia() {
		return pacientsFarmacia;
	}

	public void setPacientsFarmacia(Collection<PacientJPA> pacientsFarmacia) {
		this.pacientsFarmacia = pacientsFarmacia;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}
}