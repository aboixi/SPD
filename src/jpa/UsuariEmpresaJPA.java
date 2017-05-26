/**
 * TFG JEE-SimpleSPD
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA Classe UsuarisEmpresaJPA
 */

@Entity
@Table(name="spd.usuaris_empresa")
public class UsuariEmpresaJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "dni")
	private String dni;
	@Column(name = "nom")
	private String nom;
	@Column(name = "cognom1")
	private String cognom1;
	@Column(name = "cognom2")
	private String cognom2;
	@Column(name = "telefon")
	private String telefon;
	@Column(name = "empresa")
	private String empresa;
	@Column(name = "tipusEmpresa")
	private String tipusEmpresa;
	@Column(name = "usuari")
	private String usuari;
	@Column(name = "clau")
	private String clau;
	
	/**
	 * Constructor
	 */
	public UsuariEmpresaJPA() {
		super();
	}
	/**
	 * Constructor amb paràmetres
	 */
	public UsuariEmpresaJPA(String dni, String nom, String cognom1, String cognom2, String telefon, String empresa,
			String usuari, String clau, String tipus) {
		super();
		this.dni = dni;
		this.nom = nom;
		this.cognom1 = cognom1;
		this.cognom2 = cognom2;
		this.telefon = telefon;
		this.empresa = empresa;
		this.usuari = usuari;
		this.clau = clau;
		this.tipusEmpresa = tipus;
	}
	
	/**
	 * Getters i setters
	 */
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognom1() {
		return cognom1;
	}

	public void setCognom1(String cognom1) {
		this.cognom1 = cognom1;
	}

	public String getCognom2() {
		return cognom2;
	}

	public void setCognom2(String cognom2) {
		this.cognom2 = cognom2;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getUsuari() {
		return usuari;
	}

	public void setUsuari(String usuari) {
		this.usuari = usuari;
	}

	public String getClau() {
		return clau;
	}

	public void setClau(String clau) {
		this.clau = clau;
	}

	public String getTipusEmpresa() {
		return tipusEmpresa;
	}

	public void setTipusEmpresa(String tipusEmpresa) {
		this.tipusEmpresa = tipusEmpresa;
	}
}