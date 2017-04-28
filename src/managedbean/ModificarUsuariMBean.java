package managedbean;

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

import ejb.UsuarisNegociRemote;
import jpa.UsuariEmpresaJPA;

@ManagedBean(name="modificarUsuari")
@SessionScoped
public class ModificarUsuariMBean implements Serializable{
	@EJB(name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	@ManagedProperty("#{eliminarUsuari}")
	private EliminarUsuariMBean eliminarUsuariMBean;
	private UsuariEmpresaJPA usuari;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String modificar() throws Exception{
		if (checkSession()){
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
		String missatge=usuarisRemotEJB.modificarUsuari(eliminarUsuariMBean.getUsuari().getDni(), eliminarUsuariMBean.getUsuari().getNom(), eliminarUsuariMBean.getUsuari().getCognom1(), eliminarUsuariMBean.getUsuari().getCognom2(), eliminarUsuariMBean.getUsuari().getTelefon(), eliminarUsuariMBean.getUsuari().getClau());
		if (missatge.equals("canviCorrecte")){
			msgInfo();
			return "vistaEmpresaUsuaris";
		}else{
			msgError();
			return "vistaEmpresaUsuaris";
		}
		}else{
			return "accessError";
		}
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
		
		if (activeSession!=null && activeSession.getAttribute("sessioEmpresa")!=null){
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
	 * @return the eliminarUsuariMBean
	 */
	public EliminarUsuariMBean getEliminarUsuariMBean() {
		return eliminarUsuariMBean;
	}

	/**
	 * @param eliminarUsuariMBean the eliminarUsuariMBean to set
	 */
	public void setEliminarUsuariMBean(EliminarUsuariMBean eliminarUsuariMBean) {
		this.eliminarUsuariMBean = eliminarUsuariMBean;
	}
}
