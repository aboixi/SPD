/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;
/**
 * JPA Classe TractamentJPA
 */
@Entity
@Table(name="spd.tractaments")
public class TractamentJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_tractament")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
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
	
	/**
	 * Relacions de persistència
	 */
	@ManyToOne(targetEntity=ExpedientJPA.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "expedient", referencedColumnName = "id_expedient")
	private ExpedientJPA expedient;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "medicament", referencedColumnName = "cn")
	private MedicamentJPA medicament;
	
	/**
	 * Constructor
	 */
	public TractamentJPA(){
		super();
	}

	/**
	 * Getters i setters
	 */
	public int getIdTractament() {
		return idTractament;
	}

	public void setIdTractament(int idTractament) {
		this.idTractament = idTractament;
	}

	public String getDataInici() {
		return dataInici;
	}

	public void setDataInici(String dataInici) {
		this.dataInici = dataInici;
	}

	public String getQuantEntera() {
		return quantEntera;
	}

	public void setQuantEntera(String quantEntera) {
		this.quantEntera = quantEntera;
	}

	public String getQuantFraccio() {
		return quantFraccio;
	}

	public void setQuantFraccio(String quantFraccio) {
		this.quantFraccio = quantFraccio;
	}

	public String getQuantitatSetmanal() {
		return quantitatSetmanal;
	}

	public void setQuantitatSetmanal(String quantitatSetmanal) {
		this.quantitatSetmanal = quantitatSetmanal;
	}

	public String getQuantitatPresa() {
		return quantitatPresa;
	}

	public void setQuantitatPresa(String quantitatPresa) {
		this.quantitatPresa = quantitatPresa;
	}

	public boolean isEsmorcar() {
		return esmorcar;
	}

	public void setEsmorcar(boolean esmorcar) {
		this.esmorcar = esmorcar;
	}

	public boolean isDinar() {
		return dinar;
	}

	public void setDinar(boolean dinar) {
		this.dinar = dinar;
	}

	public boolean isSopar() {
		return sopar;
	}

	public void setSopar(boolean sopar) {
		this.sopar = sopar;
	}

	public boolean isDormir() {
		return dormir;
	}

	public void setDormir(boolean dormir) {
		this.dormir = dormir;
	}

	public boolean isDilluns() {
		return dilluns;
	}

	public void setDilluns(boolean dilluns) {
		this.dilluns = dilluns;
	}

	public boolean isDimarts() {
		return dimarts;
	}

	public void setDimarts(boolean dimarts) {
		this.dimarts = dimarts;
	}

	public boolean isDimecres() {
		return dimecres;
	}

	public void setDimecres(boolean dimecres) {
		this.dimecres = dimecres;
	}

	public boolean isDijous() {
		return dijous;
	}

	public void setDijous(boolean dijous) {
		this.dijous = dijous;
	}

	public boolean isDivendres() {
		return divendres;
	}

	public void setDivendres(boolean divendres) {
		this.divendres = divendres;
	}

	public boolean isDissabte() {
		return dissabte;
	}

	public void setDissabte(boolean dissabte) {
		this.dissabte = dissabte;
	}

	public boolean isDiumenge() {
		return diumenge;
	}

	public void setDiumenge(boolean diumenge) {
		this.diumenge = diumenge;
	}

	public boolean isForaBlister() {
		return foraBlister;
	}

	public void setForaBlister(boolean foraBlister) {
		this.foraBlister = foraBlister;
	}

	public ExpedientJPA getExpedient() {
		return expedient;
	}

	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
	}

	public MedicamentJPA getMedicament() {
		return medicament;
	}

	public void setMedicament(MedicamentJPA medicament) {
		this.medicament = medicament;
	}

	public String getNumLot() {
		return numLot;
	}

	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}
}