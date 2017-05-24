/**
 * TFG JEE-SimpleSPD - Component: FullsDeControl
 * @author Albert Boix Isern
 */
package managedbean.fullscontrol;

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

import ejb.FullControlNegociRemote;
import jpa.FullDeTreballJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per validar el blíster
 */
@ManagedBean (name="validar")
@SessionScoped
public class ValidarBlisterMBean implements Serializable{
	
	@EJB (name="FullControlNegociEJB")
	FullControlNegociRemote controlRemotEJB;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Valida un full de control
	 */
	public String validar()throws Exception{
		if (checkSession()){
			FullDeTreballJPA full=getFullSessio();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			controlRemotEJB = (FullControlNegociRemote) ctx.lookup("java:app/SPD.jar/FullControlNegociEJB!ejb.FullControlNegociRemote");
			String msg = controlRemotEJB.validar(full.getExpedient().getId(), getDniUsuariSessio());
			if (msg.equals("procesCorrecte")){
				msgCorrecte();
				return "vistaUsuariModificarFull";
			}else if (msg.equals("fullControlNoTrobat")){
				msgError();
				return "vistaUsuariModificarFull";
			}			
		}else{
			return "accessError";
		}
		return null;
	}
	
	/**
	 * consulta el full de treball pujat a sessió
	 */
	public FullDeTreballJPA getFullSessio(){
		FullDeTreballJPA fullTreball = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		fullTreball =(FullDeTreballJPA) activeSession.getAttribute("full");
		return fullTreball;
	}
	/**
	 * Consulta el dni de l'usuari amb la sessió activa
	 */
	public String getDniUsuariSessio(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getDni();
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
	 * Mostra un missatge d'informació
	 */
	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Full de control validat."));
 	}
	/**
	 * Mostra un missatge d'error
	 */
	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Encara no has confirmat el full de treball."));
 	}
}