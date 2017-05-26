/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
import javax.persistence.*;
/**
 * JPA Classe FullDeControlJPA
 */
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
	@Column(name = "nom_medicament")
	private String nomMedicament;	
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
	/**
	 * Constructor
	 */
	public FullDeControlJPA(){
		super();
	}
	/**
	 * Constructor amb paràmetres
	 */
	public FullDeControlJPA(String dnip,String idBlister, TractamentJPA tract, int setmana) {
		super();
		this.idFullControl=idBlister.concat("-").concat(String.valueOf(tract.getIdTractament()));
		this.idBlister = idBlister;
		this.idFullTreball = String.valueOf(tract.getExpedient().getId());
		this.idTractament = String.valueOf(tract.getIdTractament());
		this.nomMedicament = tract.getMedicament().getNomComercial();
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
	}
	/**
	 * Getters i setters
	 */
	public String getIdFullControl() {
		return idFullControl;
	}

	public void setIdFullControl(String idFullControl) {
		this.idFullControl = idFullControl;
	}

	public String getIdBlister() {
		return idBlister;
	}

	public void setIdBlister(String idBlister) {
		this.idBlister = idBlister;
	}

	public String getIdFullTreball() {
		return idFullTreball;
	}

	public void setIdFullTreball(String idFullTreball) {
		this.idFullTreball = idFullTreball;
	}

	public String getIdTractament() {
		return idTractament;
	}

	public void setIdTractament(String idTractament) {
		this.idTractament = idTractament;
	}

	public int getSetmana() {
		return setmana;
	}

	public void setSetmana(int setmana) {
		this.setmana = setmana;
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

	public String getNumLot() {
		return numLot;
	}

	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}

	public String getNomMedicament() {
		return nomMedicament;
	}

	public void setNomMedicament(String nomMedicament) {
		this.nomMedicament = nomMedicament;
	}
}