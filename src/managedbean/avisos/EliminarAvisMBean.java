/**
 * TFG JEE-SimpleSPD - Component: Avís
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
 * Bean per eliminar un avís
 */
@ManagedBean(name="eliminarAvis")
@SessionScoped
public class EliminarAvisMBean implements Serializable{

	@EJB(name="AvisNegociEJB")
	private AvisNegociRemote avisNegoci;
	private AvisJPA avis;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Elimina un avís 
	 */
	public String eliminarAvis() throws NamingException{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SimpleSPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
			try{
				avisNegoci.eliminarAvis(avis.getIdAvis());
			}catch (Exception e){
				System.out.println(e);
			}
			return "vistaUsuariAvisos";
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
	 * Getters i Setters
	 */
	public AvisJPA getAvis() {
		return avis;
	}

	public void setAvis(AvisJPA avis) {
		this.avis = avis;
	}
}