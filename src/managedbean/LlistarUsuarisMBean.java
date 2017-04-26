package managedbean;

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

import ejb.UsuarisNegociRemote;
import jpa.EmpresaJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per llistar els usuaris vinculats a la empresa.
 */
@ManagedBean (name="llistarUsuaris")
@SessionScoped
public class LlistarUsuarisMBean implements Serializable{
	
	@EJB (name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	private Collection<UsuariEmpresaJPA> usuaris;
	private static final long serialVersionUID = 1L;
	
	public String llistar()throws Exception{
		String cif=getSessionCif();
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
		this.usuaris = usuarisRemotEJB.llistarUsuaris(cif);
		this.usuaris.toArray();
		this.setUsuaris(usuaris);
		return "vistaEmpresaUsuaris";
	}
	
	public String getSessionCif(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		EmpresaJPA empresa = (EmpresaJPA) activeSession.getAttribute("sessioEmpresa");
		return empresa.getCif();
	}
	
	public void setSessionLlistaUsuaris(Collection<UsuariEmpresaJPA> usuaris){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		activeSession.removeAttribute("usuarisEmpresa");
		activeSession.setAttribute("usuarisEmpresa", usuaris);
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
