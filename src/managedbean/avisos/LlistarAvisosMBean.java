/**
 * TFG JEE-SimpleSPD - Component: Avís
 * @author Albert Boix Isern
 */
package managedbean.avisos;

import java.io.Serializable;
import java.util.ArrayList;
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
import jpa.AvisJPA;
import jpa.EmpresaJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per llistar els avisos
 */
@ManagedBean(name="llistarAvisos")
@SessionScoped
public class LlistarAvisosMBean implements Serializable{

	@EJB(name="AvisNegociEJB")
	private AvisNegociRemote avisNegoci;
	private Collection<AvisJPA> avisos;
	private Collection<AvisJPA> enviats;
	private Collection<AvisJPA> rebuts;
	private Collection<EmpresaJPA>empreses;
	private String[] llistaEmpreses;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 *Mètode que mostra una llista amb els avisos, rebuts i enviats.
	 */
	public String llistarAvisos() throws NamingException{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			setLlistaEmpreses(mostraEmpreses());
			avisNegoci = (AvisNegociRemote) ctx.lookup("java:app/SPD.jar/AvisNegociEJB!ejb.AvisNegociRemote");
			avisos = avisNegoci.llistarAvisos(getSessionCif());
			iniciaLlistaEnviats(avisos);
			iniciaLlistaRebuts(avisos);
			return "vistaUsuariAvisos";
		}else{
			return "accessError";
		}
	}
	/**
	 * Inicialitza la llista d'avisos enviats
	 */
	public void iniciaLlistaEnviats(Collection<AvisJPA> tots){
		Collection<AvisJPA> enviats = new ArrayList<AvisJPA>();
		String nif = getSessionCif();
		
		AvisJPA avis = null;
		Iterator<AvisJPA> iter = tots.iterator();
		while (iter.hasNext()){
			avis = iter.next();
			if (avis.getCifEmisor().equals(nif)){
				enviats.add(avis);				
			}
		}
		if (enviats.isEmpty()){
			enviats.clear();
		}
		setEnviats(enviats);
	}
	/**
	 * Inicialitza la llista d'avisos rebuts
	 */
	public void iniciaLlistaRebuts(Collection<AvisJPA> tots){
		Collection<AvisJPA> rebuts = new ArrayList<AvisJPA>();
		String nif = getSessionCif();
		
		AvisJPA avis = null;
		Iterator<AvisJPA> iterE = tots.iterator();
		while (iterE.hasNext()){
			avis = iterE.next();
			if (avis.getCifReceptor().equals(nif)){
				rebuts.add(avis);
			}
		}
		if (rebuts.isEmpty()){
			rebuts.clear();
		}
		setRebuts(rebuts);
	}
	/**
	 * Mostra una llista amb les empreses
	 */
	public String[] mostraEmpreses() throws NamingException{
		this.empreses=null;
		String cif = getSessionCif();
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
		return llistaEmpreses;
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
	 * Consulta el cif de la sessió
	 */
	public String getSessionCif(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getEmpresa();
	}
 	/**
 	 * Genera un missatge d'informació
 	 */
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Missatge enviat"));
 	}
 	/**
 	 * Genera un missatge d'error
 	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear el missatge."));
 	}
 	/**
	 * Getters i Setters
	 */
	public Collection<AvisJPA> getAvisos() {
		return avisos;
	}

	public void setAvisos(Collection<AvisJPA> avisos) {
		this.avisos = avisos;
	}

	public Collection<AvisJPA> getEnviats() {
		return enviats;
	}

	public void setEnviats(Collection<AvisJPA> enviats) {
		this.enviats = enviats;
	}

	public Collection<AvisJPA> getRebuts() {
		return rebuts;
	}

	public void setRebuts(Collection<AvisJPA> rebuts) {
		this.rebuts = rebuts;
	}

	public Collection<EmpresaJPA> getEmpreses() {
		return empreses;
	}

	public void setEmpreses(Collection<EmpresaJPA> empreses) {
		this.empreses = empreses;
	}

	public String[] getLlistaEmpreses() {
		return llistaEmpreses;
	}

	public void setLlistaEmpreses(String[] llistaEmpreses) {
		this.llistaEmpreses = llistaEmpreses;
	}	
}
