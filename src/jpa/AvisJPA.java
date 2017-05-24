/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;
import java.io.Serializable;
import javax.persistence.*;
/**
 * JPA Classe AvisJPA
 */
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
	
	@Column(name = "nom_emisor")
	private String nomEmisor;
	
	@Column(name = "cif_receptor")
	private String cifReceptor;
	
	@Column(name = "nom_receptor")
	private String nomReceptor;
	
	@Column(name = "tipus")
	private String tipus;
	
	@Column(name = "descripcio")
	private String descripcio;
	
	@Column(name = "estat")
	private String estat;
	
	/**
	 * Constructor
	 */
	public AvisJPA(){
		super();
	}
	
	/**
	 * Constructor amb paràmetres
	 */
	public AvisJPA(String cife, String cifr, String nomR, String tipus, String descripcio){
		super();
		this.cifEmisor = cife;
		this.cifReceptor = cifr;
		this.nomReceptor = nomR;
		this.tipus = tipus;
		this.descripcio = descripcio;
		this.estat="PENDENT";
	}

	/**
	 * Getters i setters
	 */
	public int getIdAvis() {
		return idAvis;
	}

	public void setIdAvis(int idAvis) {
		this.idAvis = idAvis;
	}

	public String getCifEmisor() {
		return cifEmisor;
	}

	public void setCifEmisor(String cifEmisor) {
		this.cifEmisor = cifEmisor;
	}

	public String getCifReceptor() {
		return cifReceptor;
	}

	public void setCifReceptor(String cifReceptor) {
		this.cifReceptor = cifReceptor;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public String getNomReceptor() {
		return nomReceptor;
	}

	public void setNomReceptor(String nomReceptor) {
		this.nomReceptor = nomReceptor;
	}

	public String getNomEmisor() {
		return nomEmisor;
	}

	public void setNomEmisor(String nomEmisor) {
		this.nomEmisor = nomEmisor;
	}
}