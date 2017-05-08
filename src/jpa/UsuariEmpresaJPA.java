/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA Class UsuarisEmpresaJPA
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
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the cognom1
	 */
	public String getCognom1() {
		return cognom1;
	}

	/**
	 * @param cognom1 the cognom1 to set
	 */
	public void setCognom1(String cognom1) {
		this.cognom1 = cognom1;
	}

	/**
	 * @return the cognom2
	 */
	public String getCognom2() {
		return cognom2;
	}

	/**
	 * @param cognom2 the cognom2 to set
	 */
	public void setCognom2(String cognom2) {
		this.cognom2 = cognom2;
	}

	/**
	 * @return the telefon
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * @param telefon the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the usuari
	 */
	public String getUsuari() {
		return usuari;
	}

	/**
	 * @param usuari the usuari to set
	 */
	public void setUsuari(String usuari) {
		this.usuari = usuari;
	}

	/**
	 * @return the clau
	 */
	public String getClau() {
		return clau;
	}

	/**
	 * @param clau the clau to set
	 */
	public void setClau(String clau) {
		this.clau = clau;
	}

	/**
	 * @return the tipusEmpresa
	 */
	public String getTipusEmpresa() {
		return tipusEmpresa;
	}

	/**
	 * @param tipusEmpresa the tipusEmpresa to set
	 */
	public void setTipusEmpresa(String tipusEmpresa) {
		this.tipusEmpresa = tipusEmpresa;
	}
}