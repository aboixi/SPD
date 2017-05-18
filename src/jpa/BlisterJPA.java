/**
 * TFG JEE-SimpleSPD - Component: 
 * @author Albert Boix Isern
 */
package jpa;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.*;

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
	
	@OneToMany(targetEntity=FullDeControlJPA.class,mappedBy="idBlister", fetch=FetchType.EAGER, orphanRemoval=true)
	private Collection<FullDeControlJPA> fullsControl;
	
	public BlisterJPA(){
		super();
	}
	
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
	 * @return the fullsControl
	 */
	public Collection<FullDeControlJPA> getFullsControl() {
		return fullsControl;
	}

	/**
	 * @param fullsControl the fullsControl to set
	 */
	public void setFullsControl(Collection<FullDeControlJPA> fullsControl) {
		this.fullsControl = fullsControl;
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
	 * @return the setmana
	 */
	public String getSetmana() {
		return setmana;
	}

	/**
	 * @param setmana the setmana to set
	 */
	public void setSetmana(String setmana) {
		this.setmana = setmana;
	}

	/**
	 * @return the preparatPer
	 */
	public String getPreparatPer() {
		return preparatPer;
	}

	/**
	 * @param preparatPer the preparatPer to set
	 */
	public void setPreparatPer(String preparatPer) {
		this.preparatPer = preparatPer;
	}

	/**
	 * @return the validatPer
	 */
	public String getValidatPer() {
		return validatPer;
	}

	/**
	 * @param validatPer the validatPer to set
	 */
	public void setValidatPer(String validatPer) {
		this.validatPer = validatPer;
	}
	
}
	