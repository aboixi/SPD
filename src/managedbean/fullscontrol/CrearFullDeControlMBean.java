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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.FullControlNegociRemote;
import jpa.FullDeTreballJPA;
import jpa.UsuariEmpresaJPA;
import managedbean.fullstreball.CrearFullDeTreballMBean;

/**
 * Bean per crer el full de control
 */
@ManagedBean (name="crearFullControl")
@SessionScoped
public class CrearFullDeControlMBean implements Serializable{
	
	@EJB (name="FullControlNegociEJB")
	FullControlNegociRemote controlRemotEJB;
	@ManagedProperty("#{crearFull}")
	private CrearFullDeTreballMBean fullTreMBean;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Crea un full de control
	 */
	public String crearFullControl()throws Exception{
		if (checkSession()){
			FullDeTreballJPA full=getFullSessio();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			controlRemotEJB = (FullControlNegociRemote) ctx.lookup("java:app/SimpleSPD.jar/FullControlNegociEJB!ejb.FullControlNegociRemote");
			controlRemotEJB.creaFullControl(full.getExpedient().getId(), getDniUsuariSessio());
			String [] blister = new String[28];
			fullTreMBean.setBlister(blister);
			msgCorrecte();
			return "vistaUsuariModificarFull";
		}else{
			return "accessError";
		}
	}
	
	/**
	 * Consulta el full de treball pujat a sessió
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
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Full de treball confirmat."));
 	}

	/**
	 * Getters i setters
	 */
	public CrearFullDeTreballMBean getFullTreMBean() {
		return fullTreMBean;
	}

	public void setFullTreMBean(CrearFullDeTreballMBean fullTreMBean) {
		this.fullTreMBean = fullTreMBean;
	}
}