/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.*;

@Entity
@Table(name="spd.expedients")
public class ExpedientJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_expedient")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int id;
	
	@OneToMany(targetEntity=TractamentJPA.class,mappedBy="expedient", fetch=FetchType.EAGER, cascade={CascadeType.ALL},orphanRemoval=true)
	private Collection<TractamentJPA> tractaments;
	
	@OneToOne(mappedBy = "expedient")
	@JoinColumn(name = "pacient", referencedColumnName = "cip")
	private PacientJPA pacient;
	
	public ExpedientJPA(){
		super();
		this.tractaments= new HashSet<TractamentJPA>();
	}

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
