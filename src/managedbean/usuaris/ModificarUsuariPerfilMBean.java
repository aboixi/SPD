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
 	
 	public boolean compara(UsuariEmpresaJPA usr){
 		boolean sonIguals=false;
 		if(this.nom.equals(usr.getNom())&&this.cognom1.equals(usr.getCognom1())&&this.cognom2.equals(usr.getCognom2())&&
 				this.telefon.equals(usr.getTelefon())&&this.clau.equals(usr.getClau())){
 			return sonIguals;
 		}else{
 			return sonIguals=false;
 		}
 	}
 	
 	public void actualitzarUsuariSessio(UsuariEmpresaJPA usuari){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
 		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
 		activeSession.setAttribute("sessioUsari", usuari);
 	}
 	
 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Canvi correcte."));
 	}
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Revisa les dades."));
 	}
	
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
	 * @return the usuari
	 */
	public UsuariEmpresaJPA getUsuari() {
		return usuari;
	}
	/**
	 * @param usuari the usuari to set
	 */
	public void setUsuari(UsuariEmpresaJPA usuari) {
		this.usuari = usuari;
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