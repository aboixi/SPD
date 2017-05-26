/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;
/**
 * JPA Classe FullDeTreballJPA
 */
@Entity
@Table(name="spd.full_de_treball")
public class FullDeTreballJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Relacions de persistència
	 */
	@Id
	@OneToOne(fetch=FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "expedient", referencedColumnName = "id_expedient")
	private ExpedientJPA expedient;
	/**
	 * Constructor
	 */
	public FullDeTreballJPA(){
		super();
	}
	/**
	 * Getters i setters
	 */
	public FullDeTreballJPA(ExpedientJPA expedient){
		this.setExpedient(expedient);
	}

	public ExpedientJPA getExpedient() {
		return expedient;
	}

	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
	}
}