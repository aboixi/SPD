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
@Table(name="spd.medicaments")
public class MedicamentJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cn")
	private String cn;
	
	@Column(name = "nom_comercial")
	private String nomComercial;
	/**
	 * Constructor
	 */
	public MedicamentJPA(){
		super();
	}
	/**
	 * Constructor amb paràmetres
	 */
	public MedicamentJPA(String cn, String nom){
		super();
		this.cn=cn;
		this.nomComercial=nom;
	}
	/**
	 * Getters i setters
	 */
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getNomComercial() {
		return nomComercial;
	}
	public void setNomComercial(String nomComercial) {
		this.nomComercial = nomComercial;
	}
}
	
