/**
 * TFG JEE-SimpleSPD - Component: Avís
 * @author Albert Boix Isern
 */
package managedbean.avisos;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import ejb.AvisNegociRemote;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per crear un avís
 */
@ManagedBean(name="crearAvis")
@SessionScoped
public class CrearAvisMBean implements Serializable{

	@EJB(name="AvisNegociEJB")
	private AvisNegociRemote avisNegoci;
	private String nomReceptor="Selecciona";
	private String tipus;
	private String descripcio;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Crea un nou avís
	 */
	public String nouAvis() throws NamingException{
		if (checkSession()){
			UsuariEmpresaJPA usuari = getSessionObject();
			String cifEmisor = usuari.getEmpresa();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SimpleSPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
			if (cifEmisor==null||nomReceptor==null){
				msgError();
				return "vistaUsuariAvisos";
			}
			avisNegoci.crearAvis(cifEmisor, nomReceptor, tipus,descripcio);
			clearFields();
			msgCorrecte();
			return "vistaUsuariAvisos";
		}else{
			clearFields();
			msgError();
			return "accessError";
		}
	}
	
	/**
	 * Neteja els camps del formulari de nou avís
	 */
	private void clearFields() {
		setDescripcio("");
		setTipus("");
		setNomReceptor("");
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
	 * Consulta l'usuari carregat a sessió
	 */
	public UsuariEmpresaJPA getSessionObject(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari;
	}
	/**
	 * Getters i Setters
	 */
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Missatge enviat."));
 	}

 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Falta seleccionar el destinatari."));
 	}

	public String getNomReceptor() {
		return nomReceptor;
	}

	public void setNomReceptor(String nomReceptor) {
		this.nomReceptor = nomReceptor;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
}
