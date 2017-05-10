package managedbean.expedient;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
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
import jpa.MedicamentJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;


/**
 * Bean per llistar els usuaris vinculats a la empresa.
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
	
	public String consultar()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			ExpedientRemotEJB = (ExpedientNegociRemote) ctx.lookup("java:app/SPD.jar/ExpedientNegociEJB!ejb.ExpedientNegociRemote");
			this.setExpedient(ExpedientRemotEJB.consultarExpedient(expedient.getPacient().getCip()));
			this.pacient = expedient.getPacient();
			this.tractaments=expedient.getTractaments();
			
			return "vistaUsuariModificarExpedient";
		}else{
			return "accessError";
		}
	}
	
	public void llistarTractaments(){
		try{
			this.expedient=pacient.getExpedient();
			this.tractaments=this.expedient.getTractaments();
		}catch (Exception e){
			System.out.println("Cap tractament a la base de dades");
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
	 * @return the expedient
	 */
	public ExpedientJPA getExpedient() {
		return expedient;
	}

	/**
	 * @param expedient the expedient to set
	 */
	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
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
	 * @return the tractaments
	 */
	public Collection<TractamentJPA> getTractaments() {
		return tractaments;
	}

	/**
	 * @param tractaments the tractaments to set
	 */
	public void setTractaments(Collection<TractamentJPA> tractaments) {
		this.tractaments = tractaments;
	}
}

/*
public String consultar()throws Exception{
if (checkSession()){
	Properties props = System.getProperties();
	Context ctx = new InitialContext(props);
	ExpedientRemotEJB = (ExpedientNegociRemote) ctx.lookup("java:app/SPD.jar/ExpedientNegociEJB!ejb.ExpedientNegociRemote");
	this.setExpedient(ExpedientRemotEJB.consultarExpedient(expedient.getPacient().getCip()));
	this.pacient = expedient.getPacient();
	this.tractaments=expedient.getTractaments();
	return "vistaUsuariModificarExpedient";
}else{
	return "accessError";
}
}
*/