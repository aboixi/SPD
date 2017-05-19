/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;
import java.io.Serializable;
import javax.persistence.*;
@Entity
@Table(name="spd.avisos")
public class AvisJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_avis")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int idAvis;
	
	@Column(name = "cif_emisor")
	private String cifEmisor;
	
	@Column(name = "cif_Receptor")
	private String cifReceptor;
	
	@Column(name = "tipus")
	private String tipus;
	
	@Column(name = "descripcio")
	private String descripcio;
	
	public AvisJPA(){
		super();
	}
	
	public AvisJPA(String cife, String cifr, String tipus, String descripcio){
		super();
		this.cifEmisor = cife;
		this.cifReceptor = cifr;
		this.tipus = tipus;
		this.descripcio = descripcio;
	}

	/**
	 * @return the idAvis
	 */
	public int getIdAvis() {
		return idAvis;
	}

	/**
	 * @param idAvis the idAvis to set
	 */
	public void setIdAvis(int idAvis) {
		this.idAvis = idAvis;
	}

	/**
	 * @return the cifEmisor
	 */
	public String getCifEmisor() {
		return cifEmisor;
	}

	/**
	 * @param cifEmisor the cifEmisor to set
	 */
	public void setCifEmisor(String cifEmisor) {
		this.cifEmisor = cifEmisor;
	}

	/**
	 * @return the cifReceptor
	 */
	public String getCifReceptor() {
		return cifReceptor;
	}

	/**
	 * @param cifReceptor the cifReceptor to set
	 */
	public void setCifReceptor(String cifReceptor) {
		this.cifReceptor = cifReceptor;
	}

	/**
	 * @return the tipus
	 */
	public String getTipus() {
		return tipus;
	}

	/**
	 * @param tipus the tipus to set
	 */
	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	/**
	 * @return the descripcio
	 */
	public String getDescripcio() {
		return descripcio;
	}

	/**
	 * @param descripcio the descripcio to set
	 */
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
}