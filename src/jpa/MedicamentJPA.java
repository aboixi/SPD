/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="spd.medicaments")
public class MedicamentJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cn")
	private String cn;
	
	@Column(name = "nom_comercial")
	private String nomComercial;

	public MedicamentJPA(){
		super();
	}
	public MedicamentJPA(String cn, String nom){
		super();
		this.cn=cn;
		this.nomComercial=nom;
	}
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
	
