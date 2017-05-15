/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="spd.full_de_treball")
public class FullDeTreballJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(fetch=FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "expedient", referencedColumnName = "id_expedient")
	private ExpedientJPA expedient;
	
	public FullDeTreballJPA(){
		super();
	}
	
	public FullDeTreballJPA(ExpedientJPA expedient){
		this.setExpedient(expedient);
	}

	/**
	 * @return the expedient
	 */
	public ExpedientJPA getExpedient() {
		return expedient;
	}

	/**
	 * @param expedient the expedient to set
	 */
	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
	}
	
}