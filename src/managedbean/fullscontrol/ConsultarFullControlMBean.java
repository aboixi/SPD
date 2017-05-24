/**
 * TFG JEE-SimpleSPD - Component: FullsDeControl
 * @author Albert Boix Isern
 */
package managedbean.fullscontrol;

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
import javax.servlet.http.HttpSession;

import ejb.FullControlNegociRemote;
import jpa.BlisterJPA;
import jpa.FullDeControlJPA;
import jpa.PacientJPA;

/**
 * Bean per consultar els fulls de control
 */
@ManagedBean (name="consultarFullControl")
@SessionScoped
public class ConsultarFullControlMBean implements Serializable{
	
	@EJB (name="FullControlNegociEJB")
	FullControlNegociRemote controlRemotEJB;
	private PacientJPA pacient;
	private Collection<FullDeControlJPA>fullsControl;
	private Collection<BlisterJPA>blisters;
	private FullDeControlJPA fullControl;
	private BlisterJPA blister;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 *Consulta els fulls de control 
	 */
	public String consultarFullsControl()throws Exception{
		if (checkSession()){
			try{
				this.fullsControl = blister.getFullsControl();
				return "vistaUsuariFullsControlBlister";
			}catch (Exception e){
				System.out.println(e);
			}
			Collection<FullDeControlJPA> fulls= null;
			this.setFullsControl(fulls);
			msgAvis();
			return null;
		}else{
			return "accessError";
		}
	}
	
	/**
	 * 
	 * Constula els blísters
	 */
	public String consultarBlisters()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			controlRemotEJB = (FullControlNegociRemote) ctx.lookup("java:app/SPD.jar/FullControlNegociEJB!ejb.FullControlNegociRemote");
			this.blisters = controlRemotEJB.consultarBlisters(pacient.getCip());
			return "vistaUsuariFullsControlControl";
		}else{
			return "accessError";
		}
	}
	/**
	 * Mostra un missatge d'avís
	 */
 	public void msgAvis(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Avís", "Cap blíster seleccionat"));
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
	 * Getters i setters
	 */
	public PacientJPA getPacient() {
		return pacient;
	}

	public void setPacient(PacientJPA pacient) {
		this.pacient = pacient;
	}

	public Collection<FullDeControlJPA> getFullsControl() {
		return fullsControl;
	}

	public void setFullsControl(Collection<FullDeControlJPA> fullsControl) {
		this.fullsControl = fullsControl;
	}

	public FullDeControlJPA getFullControl() {
		return fullControl;
	}

	public void setFullControl(FullDeControlJPA fullControl) {
		this.fullControl = fullControl;
	}

	public BlisterJPA getBlister() {
		return blister;
	}

	public void setBlister(BlisterJPA blister) {
		this.blister = blister;
	}

	public Collection<BlisterJPA> getBlisters() {
		return blisters;
	}

	public void setBlisters(Collection<BlisterJPA> blisters) {
		this.blisters = blisters;
	}
}
