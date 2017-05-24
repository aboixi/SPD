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
import javax.servlet.http.HttpSession;

import ejb.UsuarisNegociRemote;
import jpa.EmpresaJPA;
/**
 * Bean per modificar les dades de l'empresa
 */
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
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;	

	/**
	 * Modifica les dades de l'empresa
	 */
 	public String modificar() throws Exception{
 		if (checkSession()){
 			donarValorAtributs();
 			Properties props = System.getProperties();
 			Context ctx = new InitialContext(props);
 			usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
 			EmpresaJPA empresa=usuarisRemotEJB.modificarEmpresa(cif, nom, poblacio, carrer, cp, telefon, fax, correu, clau, contacte);
 			actualitzarUsuariSessio(empresa);
 			msgInfo();
 			return "vistaEmpresaPerfil";
 		}else{
 			return "accessError";
 		}
	}
 	/**
 	 * Actualitza l'objecte d'empresa pujat a sessió amb les noves dades
 	 */
 	public void actualitzarUsuariSessio(EmpresaJPA empresa){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
 		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
 		activeSession.setAttribute("sessioEmpresa", empresa);
 	}
 	public boolean compara(EmpresaJPA emp){
 		boolean sonIguals=false;
 		if(this.nom.equals(emp.getNom())&&this.poblacio.equals(emp.getPoblacio())&&this.carrer.equals(emp.getCarrer())&&
 				this.cp.equals(emp.getCp())&&this.telefon.equals(emp.getTelefon())&&this.fax.equals(emp.getFax())
 				&&this.correu.equals(emp.getCorreu())&&this.clau.equals(emp.getClau())&&this.contacte.equals(emp.getContacte())){
 			return sonIguals;
 		}else{
 			return sonIguals=false;
 		}
 	}
 	/**
 	 * Dóna valor als atributs per que es mostrin a la vista
 	 */
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
 	/**
	 * Mètode que comprova si l'usuari ha fet login i té la sessió activa.
	 * @return un booleà amb el resultat
	 */
	public boolean checkSession(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		if (activeSession!=null && activeSession.getAttribute("sessioEmpresa")!=null){
			return (this.sessionOK=true);
		}else{
			return (this.sessionOK=false);
		}
	}
 	/**
 	 * Genera un missatge d'informació
 	 */
 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Operació efectuada."));
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
}
