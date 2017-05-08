/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

@Entity
@Table(name="spd.tractaments")
public class TractamentJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_tractament")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int idTractament;
	@Column(name = "data_inici")
	private Calendar dataInici;
	@Column(name = "quantitat_entera")
	private String quantEntera;	
	@Column(name = "quant_fraccio")
	private String quantFraccio;
	@Column(name = "quant_setmanal")
	private String quantitatSetmanal;
	@Column(name = "hora")
	private String hora;
	@Column(name = "dies")
	private String dies;
	@Column(name = "fora_blister")
	private boolean foraBlister;
	
	@ManyToOne(targetEntity=ExpedientJPA.class)
	@JoinColumn(name = "expedient", referencedColumnName = "id_expedient")
	private ExpedientJPA expedient;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "medicament", referencedColumnName = "cn")
	private MedicamentJPA medicament;
	
	public TractamentJPA(){
		super();
	}

	public int getIdTractament() {
		return idTractament;
	}

	public void setIdTractament(int idTractament) {
		this.idTractament = idTractament;
	}

	public MedicamentJPA getMedicament() {
		return medicament;
	}

	public void setMedicament(MedicamentJPA medicament) {
		this.medicament = medicament;
	}

	public Calendar getDataInici() {
		return dataInici;
	}

	public void setDataInici(Calendar dataInici) {
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

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDies() {
		return dies;
	}

	public void setDies(String dies) {
		this.dies = dies;
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
}