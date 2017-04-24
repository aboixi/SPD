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
import jpa.EmpresaJPA;

@ManagedBean(name = "modificarEmpresa")
@SessionScoped
public class ModificarEmpresaMBean implements Serializable{

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
	private static final long serialVersionUID = 1L;	

 	public String modificar() throws Exception{
 		donarValorAtributs();
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
		EmpresaJPA empresa=usuarisRemotEJB.modificarEmpresa(cif, nom, poblacio, carrer, cp, telefon, fax, correu, clau, contacte);
		actualitzarUsuariSessio(empresa);
		msgInfo();
		return "vistaPerfilEmpresa";
	}
 	
 	public void actualitzarUsuariSessio(EmpresaJPA empresa){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
 		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
 		activeSession.setAttribute("sessioEmpresa", empresa);
 	}
 	
 	public void donarValorAtributs(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
 		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
 		EmpresaJPA empresa = (EmpresaJPA) activeSession.getAttribute("sessioEmpresa");
 		this.cif=empresa.getCif();
 		this.nom=empresa.getNom();
 		this.poblacio=empresa.getPoblacio();
 		this.carrer=empresa.getCarrer();
 		this.cp=empresa.getCp();
 		this.telefon=empresa.getTelefon();
 		this.fax=empresa.getFax();
 		this.correu=empresa.getCorreu();
 		this.contacte=empresa.getContacte();
 		this.clau=empresa.getClau();
 	}
 	
 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Operació efectuada."));
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
}
