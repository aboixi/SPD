package managedbean.fullstreball;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.FullTreballNegociRemote;
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;

/**
 * 
 */
@ManagedBean (name="llistarFulls")
@SessionScoped
public class LlistarFullsMBean implements Serializable{
	
	@EJB (name="FullTreballNegociEJB")
	FullTreballNegociRemote fullsRemotEJB;
	private Collection<PacientJPA> pacients;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String llistarFulls()throws Exception{
		if (checkSession()){
			UsuariEmpresaJPA usuari = getSessioUsuari();
			if (!(usuari.getTipusEmpresa().equals("Farmacia"))){
				return null;
			}
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			fullsRemotEJB = (FullTreballNegociRemote) ctx.lookup("java:app/SPD.jar/FullTreballNegociEJB!ejb.FullTreballNegociRemote");
			this.pacients=fullsRemotEJB.llistarFulls(usuari.getEmpresa());
			this.setPacients(pacients);
			return "vistaUsuariFullsTreball";
		}else{
			return "accessError";
		}
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
	
	public UsuariEmpresaJPA getSessioUsuari(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari;
	}

	/**
	 * @return the pacients
	 */
	public Collection<PacientJPA> getPacients() {
		return pacients;
	}

	/**
	 * @param pacients the pacients to set
	 */
	public void setPacients(Collection<PacientJPA> pacients) {
		this.pacients = pacients;
	}
}