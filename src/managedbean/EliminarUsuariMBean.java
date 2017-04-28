package managedbean;

import java.io.Serializable;
import java.util.Collection;
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

import ejb.UsuarisNegociRemote;
import jpa.UsuariEmpresaJPA;

@ManagedBean (name="eliminarUsuari")
@SessionScoped
public class EliminarUsuariMBean implements Serializable{
	
	@EJB (name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	private UsuariEmpresaJPA usuari;
	private Collection<UsuariEmpresaJPA> usuaris;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;

	public String eliminar() throws NamingException{
		if (checkSession()){
			String dni=usuari.getDni();
			String cif=usuari.getEmpresa();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
			usuarisRemotEJB.eliminarUsuari(cif, dni);
			return null;
		}else{
			return "accessError";
		}
	}
	public void consultar(){}
	
	public boolean checkSession(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		if (activeSession!=null && activeSession.getAttribute("sessioEmpresa")!=null){
			return (this.sessionOK=true);
		}else{
			return (this.sessionOK=false);
		}
	}

 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Canvi correcte."));
 	}
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Revisa les dades."));
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
	 * @return the usuaris
	 */
	public Collection<UsuariEmpresaJPA> getUsuaris() {
		return usuaris;
	}

	/**
	 * @param usuaris the usuaris to set
	 */
	public void setUsuaris(Collection<UsuariEmpresaJPA> usuaris) {
		this.usuaris = usuaris;
	}
}
