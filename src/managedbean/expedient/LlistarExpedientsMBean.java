/**
 * TFG JEE-SimpleSPD - Component: Expedient
 * @author Albert Boix Isern
 */
package managedbean.expedient;

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

import ejb.ExpedientNegociRemote;
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per llistar els expedients
 */
@ManagedBean (name="llistarExpedients")
@SessionScoped
public class LlistarExpedientsMBean implements Serializable{
	
	@EJB (name="ExpedientsNegociEJB")
	ExpedientNegociRemote expedientRemotEJB;
	private Collection<PacientJPA> pacients;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Llista els expedients
	 */
	public String llistar()throws Exception{
		if (checkSession()){
			String cif=getSessionCif();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			expedientRemotEJB = (ExpedientNegociRemote) ctx.lookup("java:app/SPD.jar/ExpedientNegociEJB!ejb.ExpedientNegociRemote");
			this.pacients=expedientRemotEJB.llistarExpedients(cif);
			this.setPacients(pacients);
			return "vistaUsuariExpedients";
		}else{
			return "accessError";
		}
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
	 * Consulta el cif de l'empresa del treballador actiu.
	 */
	public String getSessionCif(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getEmpresa();
	}

	/**
	 * Getters i setters
	 */
	public Collection<PacientJPA> getPacients() {
		return pacients;
	}

	public void setPacients(Collection<PacientJPA> pacients) {
		this.pacients = pacients;
	}
}