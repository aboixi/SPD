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
 * 
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
	
	public String crearFullControl()throws Exception{
		if (checkSession()){
			FullDeTreballJPA full=getFullSessio();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			controlRemotEJB = (FullControlNegociRemote) ctx.lookup("java:app/SPD.jar/FullControlNegociEJB!ejb.FullControlNegociRemote");
			controlRemotEJB.creaFullControl(full.getExpedient().getId(), getDniUsuariSessio());
			String [] blister = new String[28];
			fullTreMBean.setBlister(blister);
			msgCorrecte();
			return "vistaUsuariModificarFull";
		}else{
			return "accessError";
		}
	}
	
	public FullDeTreballJPA getFullSessio(){
		FullDeTreballJPA fullTreball = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		fullTreball =(FullDeTreballJPA) activeSession.getAttribute("full");
		return fullTreball;
	}
	
	public String getDniUsuariSessio(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getDni();
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
	
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Full de treball confirmat."));
 	}

	/**
	 * @return the fullTreMBean
	 */
	public CrearFullDeTreballMBean getFullTreMBean() {
		return fullTreMBean;
	}

	/**
	 * @param fullTreMBean the fullTreMBean to set
	 */
	public void setFullTreMBean(CrearFullDeTreballMBean fullTreMBean) {
		this.fullTreMBean = fullTreMBean;
	}
}