/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
//import java.util.Collection;

import javax.persistence.*;

/**
 * JPA Class PacientJPA
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
	 * Relacions de persistencia
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "expedient", referencedColumnName = "id_expedient")
	private ExpedientJPA expedient;
	
	/**
	 * Constructor
	 */
	public PacientJPA() {
		super();
	}

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
	}

	/**
	 * @return the cip
	 */
	public String getCip() {
		return cip;
	}

	/**
	 * @param cip the cip to set
	 */
	public void setCip(String cip) {
		this.cip = cip;
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
	 * @return the cognom1
	 */
	public String getCognom1() {
		return cognom1;
	}

	/**
	 * @param cognom1 the cognom1 to set
	 */
	public void setCognom1(String cognom1) {
		this.cognom1 = cognom1;
	}

	/**
	 * @return the cognom2
	 */
	public String getCognom2() {
		return cognom2;
	}

	/**
	 * @param cognom2 the cognom2 to set
	 */
	public void setCognom2(String cognom2) {
		this.cognom2 = cognom2;
	}

	/**
	 * @return the metge
	 */
	public String getMetge() {
		return metge;
	}

	/**
	 * @param metge the metge to set
	 */
	public void setMetge(String metge) {
		this.metge = metge;
	}

	/**
	 * @return the alergies
	 */
	public String getAlergies() {
		return alergies;
	}

	/**
	 * @param alergies the alergies to set
	 */
	public void setAlergies(String alergies) {
		this.alergies = alergies;
	}

	/**
	 * @return the malalties
	 */
	public String getMalalties() {
		return malalties;
	}

	/**
	 * @param malalties the malalties to set
	 */
	public void setMalalties(String malalties) {
		this.malalties = malalties;
	}

	/**
	 * @return the spd
	 */
	public boolean isSpd() {
		return spd;
	}

	/**
	 * @param spd the spd to set
	 */
	public void setSpd(boolean spd) {
		this.spd = spd;
	}

	/**
	 * @return the residencia
	 */
	public String getResidencia() {
		return residencia;
	}

	/**
	 * @param residencia the residencia to set
	 */
	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	/**
	 * @return the nomResidencia
	 */
	public String getNomResidencia() {
		return nomResidencia;
	}

	/**
	 * @param nomResidencia the nomResidencia to set
	 */
	public void setNomResidencia(String nomResidencia) {
		this.nomResidencia = nomResidencia;
	}

	/**
	 * @return the farmacia
	 */
	public String getFarmacia() {
		return farmacia;
	}

	/**
	 * @param farmacia the farmacia to set
	 */
	public void setFarmacia(String farmacia) {
		this.farmacia = farmacia;
	}

	/**
	 * @return the nomFarmacia
	 */
	public String getNomFarmacia() {
		return nomFarmacia;
	}

	/**
	 * @param nomFarmacia the nomFarmacia to set
	 */
	public void setNomFarmacia(String nomFarmacia) {
		this.nomFarmacia = nomFarmacia;
	}

	/**
	 * @return the autoritzacio
	 */
	public boolean isAutoritzacio() {
		return autoritzacio;
	}

	/**
	 * @param autoritzacio the autoritzacio to set
	 */
	public void setAutoritzacio(boolean autoritzacio) {
		this.autoritzacio = autoritzacio;
	}

	/**
	 * @return the hospitalitzat
	 */
	public boolean isHospitalitzat() {
		return hospitalitzat;
	}

	/**
	 * @param hospitalitzat the hospitalitzat to set
	 */
	public void setHospitalitzat(boolean hospitalitzat) {
		this.hospitalitzat = hospitalitzat;
	}

	/**
	 * @return the exitus
	 */
	public boolean isExitus() {
		return exitus;
	}

	/**
	 * @param exitus the exitus to set
	 */
	public void setExitus(boolean exitus) {
		this.exitus = exitus;
	}

	/**
	 * @return the expedient
	 */
	public ExpedientJPA getExpedient() {
		return expedient;
	}

	/**
	 * @param expedient the expedient to set
	 */
	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
	}
}