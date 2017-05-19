package managedbean.avisos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import ejb.AvisNegociRemote;
import jpa.EmpresaJPA;
import jpa.UsuariEmpresaJPA;

@ManagedBean(name="crearAvis")
@SessionScoped
public class CrearAvisMBean implements Serializable{

	@EJB(name="AvisNegociEJB")
	private AvisNegociRemote avisNegoci;
	private String cifReceptor="Selecciona";
	private String tipus;
	private String descripcio;
	private Collection<EmpresaJPA>empreses;
	private String[] llistaEmpreses;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String nouAvis() throws NamingException{
		if (checkSession()){
			UsuariEmpresaJPA usuari = getSessionObject();
			String cifEmisor = usuari.getEmpresa();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
			avisNegoci.crearAvis(cifEmisor, cifReceptor, tipus,descripcio);
			clearFields();
			msgCorrecte();
			return "vistaUsuariAvisos";
		}else{
			clearFields();
			msgError();
			return "accessError";
		}
	}
	
	private void clearFields() {
		setDescripcio(null);
		setTipus(null);
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
	
	public UsuariEmpresaJPA getSessionObject(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari;
	}

	public String iniciaPagina() throws NamingException{
		if (checkSession()){
			mostraEmpreses();
			return "vistaUsuariAvisos";
		}else{
			return "accessError?faces-redirect=true";
		}
	}
	
	public void mostraEmpreses() throws NamingException{
		this.empreses=null;
		UsuariEmpresaJPA usuari = getSessionObject();
		String cif = usuari.getEmpresa();
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
		this.empreses = avisNegoci.consultaEmpreses(cif);
		this.llistaEmpreses=new String [empreses.size()-1];
		EmpresaJPA empresa = null;
		Iterator <EmpresaJPA> iter = empreses.iterator();
		iter.next(); //Saltem la primera farmàcia de la BBDD. "ninguna farmacia"
		int i = 0;
		while (iter.hasNext()){
			empresa = iter.next();
			this.llistaEmpreses[i]=empresa.getNom();
			i++;
		}
		setLlistaEmpreses(llistaEmpreses);
	}
 	
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Missatge enviat"));
 	}
 	
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear el missatge."));
 	}

	/**
	 * @return the cifReceptor
	 */
	public String getCifReceptor() {
		return cifReceptor;
	}

	/**
	 * @param cifReceptor the cifReceptor to set
	 */
	public void setCifReceptor(String cifReceptor) {
		this.cifReceptor = cifReceptor;
	}

	/**
	 * @return the tipus
	 */
	public String getTipus() {
		return tipus;
	}

	/**
	 * @param tipus the tipus to set
	 */
	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	/**
	 * @return the descripcio
	 */
	public String getDescripcio() {
		return descripcio;
	}

	/**
	 * @param descripcio the descripcio to set
	 */
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	/**
	 * @return the empreses
	 */
	public Collection<EmpresaJPA> getEmpreses() {
		return empreses;
	}

	/**
	 * @param empreses the empreses to set
	 */
	public void setEmpreses(Collection<EmpresaJPA> empreses) {
		this.empreses = empreses;
	}
	
	/**
	 * @return the llistaEmpreses
	 */
	public String[] getLlistaEmpreses() {
		return llistaEmpreses;
	}

	/**
	 * @param llistaEmpreses the llistaEmpreses to set
	 */
	public void setLlistaEmpreses(String[] llistaEmpreses) {
		this.llistaEmpreses = llistaEmpreses;
	}
	
}
