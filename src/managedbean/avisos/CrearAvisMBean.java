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
	
	public String nouAvis() throws NamingException{
		if (checkSession()){
			UsuariEmpresaJPA usuari = getSessionObject();
			String cifEmisor = usuari.getEmpresa();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
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
	
	private void clearFields() {
		setDescripcio("");
		setTipus("");
		setNomReceptor("");
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
	
	public UsuariEmpresaJPA getSessionObject(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari;
	}

 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Missatge enviat."));
 	}
 	
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Falta seleccionar el destinatari."));
 	}

	/**
	 * @return the cifReceptor
	 */
	public String getNomReceptor() {
		return nomReceptor;
	}

	/**
	 * @param cifReceptor the cifReceptor to set
	 */
	public void setNomReceptor(String nomReceptor) {
		this.nomReceptor = nomReceptor;
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

	/**
	 * @return the descripcio
	 */
	public String getDescripcio() {
		return descripcio;
	}

	/**
	 * @param descripcio the descripcio to set
	 */
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
}
