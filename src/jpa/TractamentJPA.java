/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="spd.tractaments")
public class TractamentJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_tractament")
	@GeneratedValue(strategy=GenerationType.TABLE) 
	private int idTractament;
	@Column(name = "data_inici")
	private String dataInici;
	@Column(name = "quantitat_entera")
	private String quantEntera;	
	@Column(name = "quant_fraccio")
	private String quantFraccio;
	@Column(name = "quant_setmanal")
	private String quantitatSetmanal;
	@Column(name = "quant_presa")
	private String quantitatPresa;
	@Column(name = "esmorcar")
	private boolean esmorcar;
	@Column(name = "dinar")
	private boolean dinar;
	@Column(name = "sopar")
	private boolean sopar;
	@Column(name = "dormir")
	private boolean dormir;
	@Column(name = "dilluns")
	private boolean dilluns;
	@Column(name = "dimarts")
	private boolean dimarts;
	@Column(name = "dimecres")
	private boolean dimecres;
	@Column(name = "dijous")
	private boolean dijous;
	@Column(name = "divendres")
	private boolean divendres;
	@Column(name = "dissabte")
	private boolean dissabte;
	@Column(name = "diumenge")
	private boolean diumenge;
	@Column(name = "fora_blister")
	private boolean foraBlister;
	@Column(name = "numero_lot")
	private String numLot;
	
	@ManyToOne(targetEntity=ExpedientJPA.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "expedient", referencedColumnName = "id_expedient")
	private ExpedientJPA expedient;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "medicament", referencedColumnName = "cn")
	private MedicamentJPA medicament;
	
	public TractamentJPA(){
		super();
	}

	/**
	 * @return the idTractament
	 */
	public int getIdTractament() {
		return idTractament;
	}

	/**
	 * @param idTractament the idTractament to set
	 */
	public void setIdTractament(int idTractament) {
		this.idTractament = idTractament;
	}

	/**
	 * @return the dataInici
	 */
	public String getDataInici() {
		return dataInici;
	}

	/**
	 * @param dataInici the dataInici to set
	 */
	public void setDataInici(String dataInici) {
		this.dataInici = dataInici;
	}

	/**
	 * @return the quantEntera
	 */
	public String getQuantEntera() {
		return quantEntera;
	}

	/**
	 * @param quantEntera the quantEntera to set
	 */
	public void setQuantEntera(String quantEntera) {
		this.quantEntera = quantEntera;
	}

	/**
	 * @return the quantFraccio
	 */
	public String getQuantFraccio() {
		return quantFraccio;
	}

	/**
	 * @param quantFraccio the quantFraccio to set
	 */
	public void setQuantFraccio(String quantFraccio) {
		this.quantFraccio = quantFraccio;
	}

	/**
	 * @return the quantitatSetmanal
	 */
	public String getQuantitatSetmanal() {
		return quantitatSetmanal;
	}

	/**
	 * @param quantitatSetmanal the quantitatSetmanal to set
	 */
	public void setQuantitatSetmanal(String quantitatSetmanal) {
		this.quantitatSetmanal = quantitatSetmanal;
	}

	/**
	 * @return the quantitatPresa
	 */
	public String getQuantitatPresa() {
		return quantitatPresa;
	}

	/**
	 * @param quantitatPresa the quantitatPresa to set
	 */
	public void setQuantitatPresa(String quantitatPresa) {
		this.quantitatPresa = quantitatPresa;
	}

	/**
	 * @return the esmorcar
	 */
	public boolean isEsmorcar() {
		return esmorcar;
	}

	/**
	 * @param esmorcar the esmorcar to set
	 */
	public void setEsmorcar(boolean esmorcar) {
		this.esmorcar = esmorcar;
	}

	/**
	 * @return the dinar
	 */
	public boolean isDinar() {
		return dinar;
	}

	/**
	 * @param dinar the dinar to set
	 */
	public void setDinar(boolean dinar) {
		this.dinar = dinar;
	}

	/**
	 * @return the sopar
	 */
	public boolean isSopar() {
		return sopar;
	}

	/**
	 * @param sopar the sopar to set
	 */
	public void setSopar(boolean sopar) {
		this.sopar = sopar;
	}

	/**
	 * @return the dormir
	 */
	public boolean isDormir() {
		return dormir;
	}

	/**
	 * @param dormir the dormir to set
	 */
	public void setDormir(boolean dormir) {
		this.dormir = dormir;
	}

	/**
	 * @return the dilluns
	 */
	public boolean isDilluns() {
		return dilluns;
	}

	/**
	 * @param dilluns the dilluns to set
	 */
	public void setDilluns(boolean dilluns) {
		this.dilluns = dilluns;
	}

	/**
	 * @return the dimarts
	 */
	public boolean isDimarts() {
		return dimarts;
	}

	/**
	 * @param dimarts the dimarts to set
	 */
	public void setDimarts(boolean dimarts) {
		this.dimarts = dimarts;
	}

	/**
	 * @return the dimecres
	 */
	public boolean isDimecres() {
		return dimecres;
	}

	/**
	 * @param dimecres the dimecres to set
	 */
	public void setDimecres(boolean dimecres) {
		this.dimecres = dimecres;
	}

	/**
	 * @return the dijous
	 */
	public boolean isDijous() {
		return dijous;
	}

	/**
	 * @param dijous the dijous to set
	 */
	public void setDijous(boolean dijous) {
		this.dijous = dijous;
	}

	/**
	 * @return the divendres
	 */
	public boolean isDivendres() {
		return divendres;
	}

	/**
	 * @param divendres the divendres to set
	 */
	public void setDivendres(boolean divendres) {
		this.divendres = divendres;
	}

	/**
	 * @return the dissabte
	 */
	public boolean isDissabte() {
		return dissabte;
	}

	/**
	 * @param dissabte the dissabte to set
	 */
	public void setDissabte(boolean dissabte) {
		this.dissabte = dissabte;
	}

	/**
	 * @return the diumenge
	 */
	public boolean isDiumenge() {
		return diumenge;
	}

	/**
	 * @param diumenge the diumenge to set
	 */
	public void setDiumenge(boolean diumenge) {
		this.diumenge = diumenge;
	}

	/**
	 * @return the foraBlister
	 */
	public boolean isForaBlister() {
		return foraBlister;
	}

	/**
	 * @param foraBlister the foraBlister to set
	 */
	public void setForaBlister(boolean foraBlister) {
		this.foraBlister = foraBlister;
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

	/**
	 * @return the medicament
	 */
	public MedicamentJPA getMedicament() {
		return medicament;
	}

	/**
	 * @param medicament the medicament to set
	 */
	public void setMedicament(MedicamentJPA medicament) {
		this.medicament = medicament;
	}

	/**
	 * @return the numLot
	 */
	public String getNumLot() {
		return numLot;
	}

	/**
	 * @param numLot the numLot to set
	 */
	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}
}