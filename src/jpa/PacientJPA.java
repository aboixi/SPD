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
	@Column(name = "farmacia")
	private String farmacia;

	/**
	 * Relacions de persistencia
	 */
	
	/**
	 * Constructor
	 */
	public PacientJPA() {
		super();
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
}