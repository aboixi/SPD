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
 * JPA Classe ExpedientJPA
 */
@Entity
@Table(name="spd.expedients")
public class ExpedientJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_expedient")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;

	/**
	 * Relacions de persistència
	 */
	
	@OneToMany(targetEntity=TractamentJPA.class,mappedBy="expedient", fetch=FetchType.EAGER, orphanRemoval=true)
	private Collection<TractamentJPA> tractaments;
	
	@OneToOne(mappedBy = "expedient")
	@JoinColumn(name = "pacient", referencedColumnName = "cip")
	private PacientJPA pacient;
	
	/**
	 * Constructor
	 */
	public ExpedientJPA(){
		super();
		this.tractaments= new HashSet<TractamentJPA>();
	}
	
	/**
	 * Getters i setters
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Collection<TractamentJPA> getTractaments() {
		return tractaments;
	}
	public void setTractaments(Collection<TractamentJPA> tractaments) {
		this.tractaments = tractaments;
	}
	public PacientJPA getPacient() {
		return pacient;
	}
	public void setPacient(PacientJPA pacient) {
		this.pacient = pacient;
	}
}
