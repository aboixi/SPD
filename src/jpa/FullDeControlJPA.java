/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="spd.fulls_de_control")
public class FullDeControlJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_full_control")
	private String idFullControl;
	
	@Column(name = "id_blister")
	private String idBlister;
	
	@Column(name = "id_fullDeTreball")
	private String idFullTreball;
	
	@Column(name = "id_tractament")
	private String idTractament;
	
	@Column(name = "id_preparat")
	private String idPreparat;
	@Column(name = "id_validat")
	private String idValidat;
	@Column(name = "setmana")
	private int setmana;
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
	@Column(name = "numero_lot")
	private String numLot;
	@Column(name = "validat")
	private boolean validat;
	
	public FullDeControlJPA(){
		super();
	}

	public FullDeControlJPA(String dnip,String idBlister, TractamentJPA tract, int setmana) {
		super();
		this.idFullControl=idBlister.concat(String.valueOf(tract.getIdTractament()));
		this.idBlister = idBlister;
		this.idFullTreball = String.valueOf(tract.getExpedient().getId());
		this.idTractament = String.valueOf(tract.getIdTractament());
		this.idPreparat = dnip;
		this.idValidat = "pendent";
		this.setmana = setmana;
		this.quantEntera = tract.getQuantEntera();
		this.quantFraccio = tract.getQuantFraccio();
		this.quantitatSetmanal = tract.getQuantitatSetmanal();
		this.quantitatPresa = tract.getQuantitatPresa();
		this.esmorcar = tract.isEsmorcar();
		this.dinar = tract.isDinar();
		this.sopar = tract.isSopar();
		this.dormir = tract.isDormir();
		this.dilluns = tract.isDilluns();
		this.dimarts = tract.isDimarts();
		this.dimecres = tract.isDimecres();
		this.dijous = tract.isDijous();
		this.divendres = tract.isDivendres();
		this.dissabte = tract.isDissabte();
		this.diumenge = tract.isDiumenge();
		this.numLot = tract.getNumLot();
		this.validat = false;
	}
	/**
	 * @return the idFullControl
	 */
	public String getIdFullControl() {
		return idFullControl;
	}
	/**
	 * @param idFullControl the idFullControl to set
	 */
	public void setIdFullControl(String idFullControl) {
		this.idFullControl = idFullControl;
	}
	/**
	 * @return the idBlister
	 */
	public String getIdBlister() {
		return idBlister;
	}
	/**
	 * @param idBlister the idBlister to set
	 */
	public void setIdBlister(String idBlister) {
		this.idBlister = idBlister;
	}
	/**
	 * @return the idFullTreball
	 */
	public String getIdFullTreball() {
		return idFullTreball;
	}
	/**
	 * @param idFullTreball the idFullTreball to set
	 */
	public void setIdFullTreball(String idFullTreball) {
		this.idFullTreball = idFullTreball;
	}
	/**
	 * @return the idTractament
	 */
	public String getIdTractament() {
		return idTractament;
	}
	/**
	 * @param idTractament the idTractament to set
	 */
	public void setIdTractament(String idTractament) {
		this.idTractament = idTractament;
	}
	/**
	 * @return the idPreparat
	 */
	public String getIdPreparat() {
		return idPreparat;
	}
	/**
	 * @param idPreparat the idPreparat to set
	 */
	public void setIdPreparat(String idPreparat) {
		this.idPreparat = idPreparat;
	}
	/**
	 * @return the idValidat
	 */
	public String getIdValidat() {
		return idValidat;
	}
	/**
	 * @param idValidat the idValidat to set
	 */
	public void setIdValidat(String idValidat) {
		this.idValidat = idValidat;
	}
	/**
	 * @return the dataInici
	 */
	public int getSetmana() {
		return setmana;
	}
	/**
	 * @param dataInici the dataInici to set
	 */
	public void setSetmana(int setmana) {
		this.setmana = setmana;
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
	/**
	 * @return the validat
	 */
	public boolean isValidat() {
		return validat;
	}
	/**
	 * @param validat the validat to set
	 */
	public void setValidat(boolean validat) {
		this.validat = validat;
	}
}