/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.*;
/**
 * JPA Classe BlisterJPA
 */
@Entity
@Table(name="spd.blisters")
public class BlisterJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_blister")
	private String idBlister;
	
	@Column(name = "cip")
	private String cip;
	
	@Column(name = "setmana")
	private String setmana;
	
	@Column(name = "preparat_per")
	private String preparatPer;
	
	@Column(name = "validat_per")
	private String validatPer;
	
	/**
	 * Relacions de persistència
	 */
	@OneToMany(targetEntity=FullDeControlJPA.class,mappedBy="idBlister", fetch=FetchType.EAGER, orphanRemoval=true)
	private Collection<FullDeControlJPA> fullsControl;
	
	/**
	 * Constructor
	 */
	public BlisterJPA(){
		super();
	}
	
	/**
	 * Constructor amb paràmetres
	 */
	public BlisterJPA(String cip, int numSetmana, int numAny, String dniP){
		super();
		this.cip=cip;
		this.idBlister = cip.concat(String.valueOf(numAny)).concat("-").concat(String.valueOf(numSetmana));
		this.setmana=String.valueOf(numSetmana);
		this.preparatPer=dniP;
		this.validatPer="PENDENT";
		this.fullsControl= new HashSet<FullDeControlJPA>();
	}

	/**
	 * Getters i setters
	 */
	public String getIdBlister() {
		return idBlister;
	}

	public void setIdBlister(String idBlister) {
		this.idBlister = idBlister;
	}

	public Collection<FullDeControlJPA> getFullsControl() {
		return fullsControl;
	}

	public void setFullsControl(Collection<FullDeControlJPA> fullsControl) {
		this.fullsControl = fullsControl;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getSetmana() {
		return setmana;
	}

	public void setSetmana(String setmana) {
		this.setmana = setmana;
	}

	public String getPreparatPer() {
		return preparatPer;
	}

	public void setPreparatPer(String preparatPer) {
		this.preparatPer = preparatPer;
	}

	public String getValidatPer() {
		return validatPer;
	}

	public void setValidatPer(String validatPer) {
		this.validatPer = validatPer;
	}
}
	