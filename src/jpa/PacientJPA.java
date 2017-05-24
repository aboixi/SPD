/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Classe PacientJPA
 */
@Entity
@Table(name="spd.pacients")
public class PacientJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cip")
	private String cip;
	@Column(name = "nom")
	private String nom;
	@Column(name = "cognom1")
	private String cognom1;
	@Column(name = "cognom2")
	private String cognom2;
	@Column(name = "metge")
	private String metge;
	@Column(name = "alergies")
	private String alergies;
	@Column(name = "malalties")
	private String malalties;
	@Column(name = "spd_actiu")
	private boolean spd;
	@Column(name = "residencia")
	private String residencia;
	@Column(name = "nom_residencia")
	private String nomResidencia;
	@Column(name = "farmacia")
	private String farmacia;
	@Column(name = "nom_farmacia")
	private String nomFarmacia;
	@Column(name = "autoritzacio")
	private boolean autoritzacio;
	@Column(name = "hospitalitzat")
	private boolean hospitalitzat;
	@Column(name = "exitus")
	private boolean exitus;
	
	/**
	 * Relacions de persistència
	 */
	@OneToOne(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name = "expedient", referencedColumnName = "id_expedient")
	private ExpedientJPA expedient;
	
	/**
	 * Constructor
	 */
	public PacientJPA() {
		super();
	}
	/**
	 * Constructor amb paràmetres
	 */
	public PacientJPA(String cip, String nom, String cognom1, String cognom2, String metge, String alergies,
			String malalties, boolean spd, boolean autoritzacio, boolean hospitalitzat, String cifResidencia, String nomResidencia) {
		super();
		this.cip = cip;
		this.nom = nom;
		this.cognom1 = cognom1;
		this.cognom2 = cognom2;
		this.metge = metge;
		this.alergies = alergies;
		this.malalties = malalties;
		this.spd = spd;
		this.autoritzacio = autoritzacio;
		this.hospitalitzat = hospitalitzat;
		this.residencia = cifResidencia;
		this.nomResidencia = nomResidencia;
		this.farmacia="00000000F";
		this.nomFarmacia="NINGUNA FARMACIA";
		this.expedient=new ExpedientJPA();
	}

	/**
	 * Getters i setters
	 */
	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognom1() {
		return cognom1;
	}

	public void setCognom1(String cognom1) {
		this.cognom1 = cognom1;
	}

	public String getCognom2() {
		return cognom2;
	}

	public void setCognom2(String cognom2) {
		this.cognom2 = cognom2;
	}

	public String getMetge() {
		return metge;
	}

	public void setMetge(String metge) {
		this.metge = metge;
	}

	public String getAlergies() {
		return alergies;
	}

	public void setAlergies(String alergies) {
		this.alergies = alergies;
	}

	public String getMalalties() {
		return malalties;
	}

	public void setMalalties(String malalties) {
		this.malalties = malalties;
	}

	public boolean isSpd() {
		return spd;
	}

	public void setSpd(boolean spd) {
		this.spd = spd;
	}

	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	public String getNomResidencia() {
		return nomResidencia;
	}

	public void setNomResidencia(String nomResidencia) {
		this.nomResidencia = nomResidencia;
	}

	public String getFarmacia() {
		return farmacia;
	}

	public void setFarmacia(String farmacia) {
		this.farmacia = farmacia;
	}

	public String getNomFarmacia() {
		return nomFarmacia;
	}

	public void setNomFarmacia(String nomFarmacia) {
		this.nomFarmacia = nomFarmacia;
	}

	public boolean isAutoritzacio() {
		return autoritzacio;
	}

	public void setAutoritzacio(boolean autoritzacio) {
		this.autoritzacio = autoritzacio;
	}

	public boolean isHospitalitzat() {
		return hospitalitzat;
	}

	public void setHospitalitzat(boolean hospitalitzat) {
		this.hospitalitzat = hospitalitzat;
	}

	public boolean isExitus() {
		return exitus;
	}

	public void setExitus(boolean exitus) {
		this.exitus = exitus;
	}

	public ExpedientJPA getExpedient() {
		return expedient;
	}

	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
	}
}