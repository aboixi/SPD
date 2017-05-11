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
	
	public String agregarTractament()throws Exception{
		if (checkSession()){
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

	public boolean checkSession(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		if (activeSession!=null && activeSession.getAttribute("sessioUsuari")!=null){
			return (this.sessionOK=true);
		}else{
			return (this.sessionOK=false);
		}
	}
	
	public void marcaTotsDies(){
		setDill(true);
		setDima(true);
		setDime(true);
		setDijo(true);
		setDive(true);
		setDiss(true);
		setDium(true);
	}
	
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
	
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nou tractament creat."));
 	}
 	
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear el tractament."));
 	}

	/**
	 * @return the expedientRemotEJB
	 */
	public ExpedientNegociRemote getExpedientRemotEJB() {
		return expedientRemotEJB;
	}

	/**
	 * @param expedientRemotEJB the expedientRemotEJB to set
	 */
	public void setExpedientRemotEJB(ExpedientNegociRemote expedientRemotEJB) {
		this.expedientRemotEJB = expedientRemotEJB;
	}

	/**
	 * @return the dataInici
	 */
	public Date getDataInici() {
		return dataInici;
	}

	/**
	 * @param dataInici the dataInici to set
	 */
	public void setDataInici(Date dataInici) {
		this.dataInici = dataInici;
	}

	/**
	 * @return the cn
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * @param cn the cn to set
	 */
	public void setCn(String cn) {
		this.cn = cn;
	}

	/**
	 * @return the nomComercial
	 */
	public String getNomComercial() {
		return nomComercial;
	}

	/**
	 * @param nomComercial the nomComercial to set
	 */
	public void setNomComercial(String nomComercial) {
		this.nomComercial = nomComercial;
	}

	/**
	 * @return the qEntera
	 */
	public String getqEntera() {
		return qEntera;
	}

	/**
	 * @param qEntera the qEntera to set
	 */
	public void setqEntera(String qEntera) {
		this.qEntera = qEntera;
	}

	/**
	 * @return the qFraccio
	 */
	public String getqFraccio() {
		return qFraccio;
	}

	/**
	 * @param qFraccio the qFraccio to set
	 */
	public void setqFraccio(String qFraccio) {
		this.qFraccio = qFraccio;
	}

	/**
	 * @return the esmorcar
	 */
	public boolean isEsmorcar() {
		return esmorcar;
	}

	/**
	 * @param esmorcar the esmorcar to set
	 */
	public void setEsmorcar(boolean esmorcar) {
		this.esmorcar = esmorcar;
	}

	/**
	 * @return the dinar
	 */
	public boolean isDinar() {
		return dinar;
	}

	/**
	 * @param dinar the dinar to set
	 */
	public void setDinar(boolean dinar) {
		this.dinar = dinar;
	}

	/**
	 * @return the sopar
	 */
	public boolean isSopar() {
		return sopar;
	}

	/**
	 * @param sopar the sopar to set
	 */
	public void setSopar(boolean sopar) {
		this.sopar = sopar;
	}

	/**
	 * @return the dormir
	 */
	public boolean isDormir() {
		return dormir;
	}

	/**
	 * @param dormir the dormir to set
	 */
	public void setDormir(boolean dormir) {
		this.dormir = dormir;
	}

	/**
	 * @return the dill
	 */
	public boolean isDill() {
		return dill;
	}

	/**
	 * @param dill the dill to set
	 */
	public void setDill(boolean dill) {
		this.dill = dill;
	}

	/**
	 * @return the dima
	 */
	public boolean isDima() {
		return dima;
	}

	/**
	 * @param dima the dima to set
	 */
	public void setDima(boolean dima) {
		this.dima = dima;
	}

	/**
	 * @return the dime
	 */
	public boolean isDime() {
		return dime;
	}

	/**
	 * @param dime the dime to set
	 */
	public void setDime(boolean dime) {
		this.dime = dime;
	}

	/**
	 * @return the dijo
	 */
	public boolean isDijo() {
		return dijo;
	}

	/**
	 * @param dijo the dijo to set
	 */
	public void setDijo(boolean dijo) {
		this.dijo = dijo;
	}

	/**
	 * @return the dive
	 */
	public boolean isDive() {
		return dive;
	}

	/**
	 * @param dive the dive to set
	 */
	public void setDive(boolean dive) {
		this.dive = dive;
	}

	/**
	 * @return the diss
	 */
	public boolean isDiss() {
		return diss;
	}

	/**
	 * @param diss the diss to set
	 */
	public void setDiss(boolean diss) {
		this.diss = diss;
	}

	/**
	 * @return the dium
	 */
	public boolean isDium() {
		return dium;
	}

	/**
	 * @param dium the dium to set
	 */
	public void setDium(boolean dium) {
		this.dium = dium;
	}

	/**
	 * @return the foraBlister
	 */
	public boolean isForaBlister() {
		return foraBlister;
	}

	/**
	 * @param foraBlister the foraBlister to set
	 */
	public void setForaBlister(boolean foraBlister) {
		this.foraBlister = foraBlister;
	}

	/**
	 * @return the expedient
	 */
	public ConsultarExpedientMBean getExpedient() {
		return expedient;
	}

	/**
	 * @param expedient the expedient to set
	 */
	public void setExpedient(ConsultarExpedientMBean expedient) {
		this.expedient = expedient;
	}
}
