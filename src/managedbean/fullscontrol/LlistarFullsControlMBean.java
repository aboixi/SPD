/**
 * TFG JEE-SimpleSPD - Component: FullsDeControl
 * @author Albert Boix Isern
 */
package managedbean.fullscontrol;

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

import ejb.FullControlNegociRemote;
import ejb.PacientsNegociRemote;
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per llistar els fulls de control
 */
@ManagedBean (name="llistarFullsControl")
@SessionScoped
public class LlistarFullsControlMBean implements Serializable{
	
	@EJB (name="PacientsNegociEJB")
	PacientsNegociRemote pacientsRemotEJB;
	private Collection<PacientJPA> pacients;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private FullControlNegociRemote controlRemotEJB;
	private static final long serialVersionUID = 1L;
	
	public String llistarPacients()throws Exception{
		if (checkSession()){
			String cif=getSessionCif();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			controlRemotEJB = (FullControlNegociRemote) ctx.lookup("java:app/SimpleSPD.jar/FullControlNegociEJB!ejb.FullControlNegociRemote");
			this.pacients=controlRemotEJB.llistarFulls(cif);
			this.setPacients(pacients);
			return "vistaUsuariFullsControl";
		}else{
			return "accessError";
		}
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
	
	public String getSessionCif(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getEmpresa();
	}
}