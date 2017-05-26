/**
 * TFG JEE-SimpleSPD - Component: FullsDeControl
 * @author Albert Boix Isern
 */
package managedbean.fullscontrol;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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
import jpa.EmpresaJPA;
import jpa.ExpedientJPA;
import jpa.FullDeTreballJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per mostrar per pantalla la etiqueta per imprimir del blíster
 */
@ManagedBean (name="imprimir")
@SessionScoped
public class ImprimirEtiquetaMBean implements Serializable{
	
	@EJB (name="FullControlNegociEJB")
	FullControlNegociRemote controlRemotEJB;
	private BlisterJPA blister;
	private Collection<TractamentJPA> tractaments;
	private PacientJPA pacient;
	private String nomPacient;
	private UsuariEmpresaJPA usuari;
	private EmpresaJPA empresa;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mostra la pantalla amb les dades per imprimir la etiqueta
	 */
	public String imprimir()throws Exception{
		if (checkSession()){
			int setmana = calcularSetmanaActual();
			int any = calcularAnyActual();
			FullDeTreballJPA full=getFullSessio();
			ExpedientJPA expedient = full.getExpedient();
			String idBlister = expedient.getPacient().getCip().concat(String.valueOf(any)).concat("-").concat(String.valueOf(setmana));
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			controlRemotEJB = (FullControlNegociRemote) ctx.lookup("java:app/SimpleSPD.jar/FullControlNegociEJB!ejb.FullControlNegociRemote");
			this.setTractaments(expedient.getTractaments());
			this.setPacient(expedient.getPacient());
			this.setUsuari(getUsuariSessio());
			this.nomPacient = pacient.getCognom1().concat(" ").concat(pacient.getCognom2()).concat(", ").concat(pacient.getNom());
			this.setBlister(controlRemotEJB.imprimir(idBlister));
			this.setEmpresa(controlRemotEJB.consultarEmpresa(usuari.getEmpresa()));
			if (this.blister==null){
				msgError();
				return null;
			}else{
				return "vistaUsuariEtiquetaBlister";
			}			
		}else{
			return "accessError";
		}
	}
	
	/**
	 * Mètode per calcular el número de setmana actual
	 * @return numSetmana Amb el número de setmana
	 */
	public int calcularSetmanaActual(){
		Calendar calendar = new GregorianCalendar();
		Date time = new Date(); 
		calendar.setTime(time);  
		int numSetmana = calendar.get(Calendar.WEEK_OF_YEAR);
	    return numSetmana;
	}
	
	/**
	 * Mètode per calcular el any actual
	 * @return numAny Amb el número de l'any
	 */
	public int calcularAnyActual(){
		Calendar calendar = new GregorianCalendar();
		Date time = new Date(); 
		calendar.setTime(time);  
		int numAny = calendar.get(Calendar.YEAR);
	    return numAny;
	}
	
	/**
	 * Consulta l'usuari amb la sessió activa
	 */
	public UsuariEmpresaJPA getUsuariSessio(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari;
	}
	/**
	 * Constula el full pujat a sessió
	 */
	public FullDeTreballJPA getFullSessio(){
		FullDeTreballJPA fullTreball = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		fullTreball =(FullDeTreballJPA) activeSession.getAttribute("full");
		return fullTreball;
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
	 * Mostra un missatge d'error
	 */
	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Blíster no trobat."));
 	}

	/**
	 * Getters i setters
	 */
	public BlisterJPA getBlister() {
		return blister;
	}

	public void setBlister(BlisterJPA blister) {
		this.blister = blister;
	}

	public Collection<TractamentJPA> getTractaments() {
		return tractaments;
	}

	public void setTractaments(Collection<TractamentJPA> tractaments) {
		this.tractaments = tractaments;
	}

	public PacientJPA getPacient() {
		return pacient;
	}

	public void setPacient(PacientJPA pacient) {
		this.pacient = pacient;
	}

	public UsuariEmpresaJPA getUsuari() {
		return usuari;
	}

	public void setUsuari(UsuariEmpresaJPA usuari) {
		this.usuari = usuari;
	}

	public String getNomPacient() {
		return nomPacient;
	}

	public void setNomPacient(String nomPacient) {
		this.nomPacient = nomPacient;
	}

	public EmpresaJPA getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaJPA empresa) {
		this.empresa = empresa;
	}
}