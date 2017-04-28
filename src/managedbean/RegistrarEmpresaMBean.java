package managedbean;

import java.io.Serializable;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.UsuarisNegociRemote;
//import jpa.EmpresaJPA;

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
	
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registre correcte, apreta el botó de Sortir."));
 	}
 	
 	public void msgAvis(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Avís", "L'empresa ja està registrada."));
 	}
 	
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Tria un altre correu electrònic"));
 	}

	/**
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
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
	 * @return the poblacio
	 */
	public String getPoblacio() {
		return poblacio;
	}

	/**
	 * @param poblacio the poblacio to set
	 */
	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}

	/**
	 * @return the carrer
	 */
	public String getCarrer() {
		return carrer;
	}

	/**
	 * @param carrer the carrer to set
	 */
	public void setCarrer(String carrer) {
		this.carrer = carrer;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
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
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the correu
	 */
	public String getCorreu() {
		return correu;
	}

	/**
	 * @param correu the correu to set
	 */
	public void setCorreu(String correu) {
		this.correu = correu;
	}

	/**
	 * @return the contacte
	 */
	public String getContacte() {
		return contacte;
	}

	/**
	 * @param contacte the contacte to set
	 */
	public void setContacte(String contacte) {
		this.contacte = contacte;
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
	 * @return the tipus
	 */
	public String getTipus() {
		return tipus;
	}

	/**
	 * @param tipus the tipus to set
	 */
	public void setTipus(String tipus) {
		this.tipus = tipus;
	}
}
/**
	public String registrar() throws Exception{
	Properties props = System.getProperties();
	Context ctx = new InitialContext(props);
	usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
	if (usuarisRemotEJB.registrarEmpresa(cif, nom, poblacio, carrer, cp, telefon, fax, correu, clau, contacte, tipus)){
		msgCorrecte();
		return "vistaLogin";
	}else{
		msgError();
		return null;
	}
}
*/