/**
 * TFG JEE-SimpleSPD - Component: Expedient
 * @author Albert Boix Isern
 */
package managedbean.expedient;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.ExpedientNegociRemote;

/**
 * Bean per llistar els usuaris vinculats a la empresa.
 */
@ManagedBean (name="agregarTractament")
@SessionScoped
public class AgregarTractamentMBean implements Serializable{
	
	@EJB (name="ExpedientsNegociEJB")
	ExpedientNegociRemote expedientRemotEJB;
	private Date dataInici;
	private String cn;
	private String nomComercial;
	private String qEntera;
	private String qFraccio;
	private boolean esmorcar;
	private boolean dinar;
	private boolean sopar;
	private boolean dormir;
	private boolean dill;
	private boolean dima;
	private boolean dime;
	private boolean dijo;
	private boolean dive;
	private boolean diss;
	private boolean dium;
	private boolean foraBlister;
	@ManagedProperty("#{consultarExpedient}")
	private ConsultarExpedientMBean expedient;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Agrega un tractament
	 */
	public String agregarTractament()throws Exception{
		if (checkSession()){
			if(qEntera.equals("0")&&qFraccio.equals("0")){
				msgError2();
				return "vistaUsuariModificarExpedient";
			}
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			expedientRemotEJB = (ExpedientNegociRemote) ctx.lookup("java:app/SPD.jar/ExpedientNegociEJB!ejb.ExpedientNegociRemote");
			String missatge=expedientRemotEJB.agregarTractament(Integer.toString(expedient.getExpedient().getId()), cn, dataInici, qEntera, qFraccio, esmorcar, dinar, sopar, 
					dormir, dill, dima, dime, dijo, dive, diss, dium, foraBlister);
			clearFields();
			if (missatge.equals("procesCorrecte")){
				msgCorrecte();
				return "vistaUsuariModificarExpedient";
			}else if(missatge.equals("pacientExistent")){
				msgError();
				return "vistaUsuariModificarExpedient";
			}
		}else{
			return "accessError";
		}
		return null;
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
	 * Selecciona tots els dies del formulari
	 */
	public void marcaTotsDies(){
		setDill(true);
		setDima(true);
		setDime(true);
		setDijo(true);
		setDive(true);
		setDiss(true);
		setDium(true);
	}
	/**
	 * Posa en blanc els camps del formulari
	 */
	public void clearFields(){
		setDataInici(null);
		setCn(null);
		setNomComercial(null);
		setqEntera(null);
		setqFraccio(null);
		setForaBlister(false);
		setEsmorcar(false);
		setDinar(false);
		setSopar(false);
		setDormir(false);
		setDill(false);
		setDima(false);
		setDime(false);
		setDijo(false);
		setDive(false);
		setDiss(false);
		setDium(false);
	}
	/**
	 * Mostra un missatge d'informació
	 */
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nou tractament creat."));
 	}
 	/**
 	 * Mostra un missatge d'error al crear el tractament
 	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear el tractament."));
 	}
 	/**
 	 * Mostra un missatge d'error si no s'ha seleccionat cap quantitat de medicament
 	 */
 	public void msgError2(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cap quantitat seleccionada."));
 	} 	

	/**
	 * Getters i setters
	 */
	public ExpedientNegociRemote getExpedientRemotEJB() {
		return expedientRemotEJB;
	}

	public void setExpedientRemotEJB(ExpedientNegociRemote expedientRemotEJB) {
		this.expedientRemotEJB = expedientRemotEJB;
	}

	public Date getDataInici() {
		return dataInici;
	}

	public void setDataInici(Date dataInici) {
		this.dataInici = dataInici;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getNomComercial() {
		return nomComercial;
	}

	public void setNomComercial(String nomComercial) {
		this.nomComercial = nomComercial;
	}

	public String getqEntera() {
		return qEntera;
	}

	public void setqEntera(String qEntera) {
		this.qEntera = qEntera;
	}

	public String getqFraccio() {
		return qFraccio;
	}

	public void setqFraccio(String qFraccio) {
		this.qFraccio = qFraccio;
	}

	public boolean isEsmorcar() {
		return esmorcar;
	}

	public void setEsmorcar(boolean esmorcar) {
		this.esmorcar = esmorcar;
	}

	public boolean isDinar() {
		return dinar;
	}

	public void setDinar(boolean dinar) {
		this.dinar = dinar;
	}

	public boolean isSopar() {
		return sopar;
	}

	public void setSopar(boolean sopar) {
		this.sopar = sopar;
	}

	public boolean isDormir() {
		return dormir;
	}

	public void setDormir(boolean dormir) {
		this.dormir = dormir;
	}

	public boolean isDill() {
		return dill;
	}

	public void setDill(boolean dill) {
		this.dill = dill;
	}

	public boolean isDima() {
		return dima;
	}

	public void setDima(boolean dima) {
		this.dima = dima;
	}

	public boolean isDime() {
		return dime;
	}

	public void setDime(boolean dime) {
		this.dime = dime;
	}

	public boolean isDijo() {
		return dijo;
	}

	public void setDijo(boolean dijo) {
		this.dijo = dijo;
	}

	public boolean isDive() {
		return dive;
	}

	public void setDive(boolean dive) {
		this.dive = dive;
	}

	public boolean isDiss() {
		return diss;
	}

	public void setDiss(boolean diss) {
		this.diss = diss;
	}

	public boolean isDium() {
		return dium;
	}

	public void setDium(boolean dium) {
		this.dium = dium;
	}

	public boolean isForaBlister() {
		return foraBlister;
	}

	public void setForaBlister(boolean foraBlister) {
		this.foraBlister = foraBlister;
	}

	public ConsultarExpedientMBean getExpedient() {
		return expedient;
	}

	public void setExpedient(ConsultarExpedientMBean expedient) {
		this.expedient = expedient;
	}
}
