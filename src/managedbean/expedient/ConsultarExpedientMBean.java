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
import jpa.ExpedientJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;

/**
 * Bean per consultar un expedient
 */
@ManagedBean (name="consultarExpedient")
@SessionScoped
public class ConsultarExpedientMBean implements Serializable{
	
	@EJB (name="ExpedientsNegociEJB")
	ExpedientNegociRemote ExpedientRemotEJB;
	private ExpedientJPA expedient;
	private PacientJPA pacient;
	private Collection<TractamentJPA>tractaments;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Consulta els tractaments
	 */
	public String consultarTractaments()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			ExpedientRemotEJB = (ExpedientNegociRemote) ctx.lookup("java:app/SPD.jar/ExpedientNegociEJB!ejb.ExpedientNegociRemote");
			this.setExpedient(ExpedientRemotEJB.consultarExpedient(pacient.getExpedient().getId()));
			this.pacient = expedient.getPacient();
			this.tractaments=expedient.getTractaments();
			return "vistaUsuariModificarExpedient";
		}else{
			return "accessError";
		}
	}
	/**
	 * Llista els tractaments
	 */
	public void llistarTractaments(){
		try{
			this.expedient=pacient.getExpedient();
			this.tractaments=this.expedient.getTractaments();
		}catch (Exception e){
			System.out.println("Cap tractament a la base de dades");
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
	 * Getters i setters
	 */
	public ExpedientJPA getExpedient() {
		return expedient;
	}

	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
	}

	public PacientJPA getPacient() {
		return pacient;
	}

	public void setPacient(PacientJPA pacient) {
		this.pacient = pacient;
	}

	public Collection<TractamentJPA> getTractaments() {
		return tractaments;
	}

	public void setTractaments(Collection<TractamentJPA> tractaments) {
		this.tractaments = tractaments;
	}

	public ExpedientNegociRemote getExpedientRemotEJB() {
		return ExpedientRemotEJB;
	}

	public void setExpedientRemotEJB(ExpedientNegociRemote expedientRemotEJB) {
		ExpedientRemotEJB = expedientRemotEJB;
	}
}
