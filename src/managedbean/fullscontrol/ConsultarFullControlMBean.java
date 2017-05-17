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
import jpa.FullDeControlJPA;
import jpa.PacientJPA;

/**
 * 
 */
@ManagedBean (name="consultarFullControl")
@SessionScoped
public class ConsultarFullControlMBean implements Serializable{
	
	@EJB (name="FullControlNegociEJB")
	FullControlNegociRemote controlRemotEJB;
	private PacientJPA pacient;
	private Collection<FullDeControlJPA>fullsControl;
	private FullDeControlJPA fullControl;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String consultarFullControl()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			controlRemotEJB = (FullControlNegociRemote) ctx.lookup("java:app/SPD.jar/FullControlNegociEJB!ejb.FullControlNegociRemote");
			this.fullsControl = controlRemotEJB.consultarFulls(pacient.getCip());
			return "vistaUsuariFullsControl";
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
	 * @return the pacient
	 */
	public PacientJPA getPacient() {
		return pacient;
	}

	/**
	 * @param pacient the pacient to set
	 */
	public void setPacient(PacientJPA pacient) {
		this.pacient = pacient;
	}

	/**
	 * @return the fullsControl
	 */
	public Collection<FullDeControlJPA> getFullsControl() {
		return fullsControl;
	}

	/**
	 * @param fullsControl the fullsControl to set
	 */
	public void setFullsControl(Collection<FullDeControlJPA> fullsControl) {
		this.fullsControl = fullsControl;
	}

	/**
	 * @return the fullControl
	 */
	public FullDeControlJPA getFullControl() {
		return fullControl;
	}

	/**
	 * @param fullControl the fullControl to set
	 */
	public void setFullControl(FullDeControlJPA fullControl) {
		this.fullControl = fullControl;
	}
	
}
