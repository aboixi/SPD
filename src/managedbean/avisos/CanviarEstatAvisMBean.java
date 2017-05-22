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

@ManagedBean(name="canviarEstatAvis")
@SessionScoped
public class CanviarEstatAvisMBean implements Serializable{

	@EJB(name="AvisNegociEJB")
	private AvisNegociRemote avisNegoci;
	private AvisJPA avis;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String canviarEstat() throws NamingException{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
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
	 * @return the avis
	 */
	public AvisJPA getAvis() {
		return avis;
	}
	/**
	 * @param avis the avis to set
	 */
	public void setAvis(AvisJPA avis) {
		this.avis = avis;
	}
}
