/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package managedbean.usuaris;

import java.io.Serializable;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UsuarisNegociRemote;
/**
 * Bean per registrar una nova empresa al sistema
 */
@ManagedBean(name = "registrarEmpresa")
@SessionScoped
public class RegistrarEmpresaMBean implements Serializable{

	@EJB(name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	private String cif;
	private String nom;
	private String poblacio;
	private String carrer;
	private String cp;
	private String telefon;
	private String fax;
	private String correu;
	private String contacte;
	private String clau;
	private String tipus;
	private static final long serialVersionUID = 1L;	

	/**
	 * Registra una nova empresa al sistema
	 */
 	public void registrar() throws Exception{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
		String missatge=usuarisRemotEJB.registrarEmpresa(cif, nom, poblacio, carrer, cp, telefon, fax, correu, clau, contacte, tipus);
		if (missatge.equals("registreCorrecte")){
			msgCorrecte();
		}else if(missatge.equals("errorEmpresaExistent")){
			msgAvis();
		}else if(missatge.equals("errorCorreuRepetit")){
			msgError();
		}
	}
	/**
	 * Missatge d'informació
	 */
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registre correcte, apreta el botó de Sortir."));
 	}
 	/**
 	 * Missatge d'avís
 	 */
 	public void msgAvis(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Avís", "L'empresa ja està registrada."));
 	}
 	/**
 	 * Missatge d'error
 	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Tria un altre correu electrònic"));
 	}

	/**
	 * Getters i setters
	 */
	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPoblacio() {
		return poblacio;
	}

	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}

	public String getCarrer() {
		return carrer;
	}

	public void setCarrer(String carrer) {
		this.carrer = carrer;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCorreu() {
		return correu;
	}

	public void setCorreu(String correu) {
		this.correu = correu;
	}

	public String getContacte() {
		return contacte;
	}

	public void setContacte(String contacte) {
		this.contacte = contacte;
	}

	public String getClau() {
		return clau;
	}

	public void setClau(String clau) {
		this.clau = clau;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}
}