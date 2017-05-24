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
import jpa.UsuariEmpresaJPA;
/**
 * Bean per modificar les dades de l'usuari des del site d'usuari
 */
@ManagedBean(name = "modificarUsuariPerfil")
@SessionScoped
public class ModificarUsuariPerfilMBean implements Serializable{

	@EJB(name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	private UsuariEmpresaJPA usuari;
	private String dni;
	private String nom;
	private String cognom1;
	private String cognom2;
	private String telefon;
	private String clau;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Modifica les dades de l'usuari des del site d'usuaris. 
	 */
	public String modificar() throws Exception{
		if (checkSession()){
			donarValorAtributs();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
			String missatge=usuarisRemotEJB.modificarUsuari(dni, nom, cognom1, cognom2, telefon, clau);
			if (missatge.equals("canviCorrecte")){
				msgInfo();
				return null;
			}else{
				msgError();
				return null;
			}
		}else{
			return "accessError";
		}
	}
	/**
	 * Carrega les dades de l'usuari per ser mostrades a la vista
	 */
 	public void donarValorAtributs(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
 		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
 		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
 		this.dni=usuari.getDni();
 		this.nom=usuari.getNom();
 		this.cognom1=usuari.getCognom1();
 		this.cognom2=usuari.getCognom2();
 		this.telefon=usuari.getTelefon();
 		this.clau=usuari.getClau();
 	}
 	
 	/**
 	 * Comprova si s'ha fet algún canvi a les dades del formulari
 	 */
 	public boolean compara(UsuariEmpresaJPA usr){
 		boolean sonIguals=false;
 		if(this.nom.equals(usr.getNom())&&this.cognom1.equals(usr.getCognom1())&&this.cognom2.equals(usr.getCognom2())&&
 				this.telefon.equals(usr.getTelefon())&&this.clau.equals(usr.getClau())){
 			return sonIguals;
 		}else{
 			return sonIguals=false;
 		}
 	}
 	/**
 	 * Actualitza l'objecte usuari pujat a sessió amb les noves dades
 	 */
 	public void actualitzarUsuariSessio(UsuariEmpresaJPA usuari){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
 		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
 		activeSession.setAttribute("sessioUsari", usuari);
 	}
 	/**
 	 * Mostra un missatge d'informació
 	 */
 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Canvi correcte."));
 	}
 	/**
 	 * Mostra un missatge d'error
 	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Revisa les dades."));
 	}
 	/**
	 * Mètode que comprova si l'usuari ha fet login i té la sessió activa.
	 * @return un booleà amb el resultat
	 */
	public boolean checkSession(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		if (activeSession!=null && activeSession.getAttribute("sessioUsuari")!=null){
			return (this.sessionOK=true);
		}else{
			return (this.sessionOK=false);
		}
	}
	
	/**
	 * Getters i setters
	 */
	public UsuariEmpresaJPA getUsuari() {
		return usuari;
	}

	public void setUsuari(UsuariEmpresaJPA usuari) {
		this.usuari = usuari;
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

	public String getClau() {
		return clau;
	}

	public void setClau(String clau) {
		this.clau = clau;
	}
}