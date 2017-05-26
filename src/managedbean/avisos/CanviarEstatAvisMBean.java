/**
 * TFG JEE-SimpleSPD - Component: Avis
 * @author Albert Boix Isern
 */
package managedbean.avisos;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import ejb.AvisNegociRemote;
import jpa.AvisJPA;
/**
 * Bean per canviar l'estat d'un avís
 */
@ManagedBean(name="canviarEstatAvis")
@SessionScoped
public class CanviarEstatAvisMBean implements Serializable{

	@EJB(name="AvisNegociEJB")
	private AvisNegociRemote avisNegoci;
	private AvisJPA avis;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mètode per canviar l'estat d'un avís.
	 */
	public String canviarEstat() throws NamingException{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SimpleSPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
			try{
				avisNegoci.canviarEstatAvis(avis.getIdAvis());
			}catch (Exception e){
				System.out.println(e);
			}
			return "vistaUsuariAvisos";
		}else{
			return "accessError";
		}
	}
	/**
	 * Mètode que comprova la sessió de l'usuari
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
	 * Getters i setters
	 */
	public AvisJPA getAvis() {
		return avis;
	}

	public void setAvis(AvisJPA avis) {
		this.avis = avis;
	}
}
