/**
 * TFG JEE-SimpleSPD - Component: Pacients
 * @author Albert Boix Isern
 */
package managedbean.pacients;

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
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import ejb.PacientsNegociRemote;
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;

@ManagedBean (name="eliminarPacient")
@SessionScoped
public class EliminarPacientMBean implements Serializable{
	
	@EJB (name="PacientsNegociEJB")
	PacientsNegociRemote PacientsRemotEJB;
	private PacientJPA pacient;
	private Collection<PacientJPA> pacients;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mètode per eliminar un pacient
	 * @return la vista amb els canvis
	 * @throws NamingException
	 */
	public String eliminar() throws NamingException{
		if (checkSession()){
			String cif = null;
			String cip = null;
			try{
				cif=getSessionCif();
				cip=pacient.getCip();
			}catch (Exception e){
				System.out.println(e);
			}
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			if (cip==null||cif==null){
				msgAvis();
				return "vistaUsuariPacients";
			}
			PacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
			PacientsRemotEJB.eliminarPacient(cip, cif);
			clearFields();
			if (comprovaTipusUsuari().equals("Residencia")){
				msgInfo();
			}else{
				msgError();
			}
			return null;
		}else{
			return "accessError";
		}
	}
 	/**
 	 * Mètode que dona valor null als atributs
 	 */
 	public void clearFields(){
 		setPacient(null);
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
	 * Mètode que consulta el CIF de la empresa de l'usuari actiu
	 * @return El cif de l'empresa.
	 */
	public String getSessionCif(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getEmpresa();
	}
	/**
	 * Metode que comprova si es tracta d'un usuari de Farmacia o Residència
	 * @return Un missatge amb el tipus d'usuari
	 */
	public String comprovaTipusUsuari(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		if (usuari.getTipusEmpresa().equals("Farmacia")){
			return "Farmacia";
		}else{
			return "Residencia";
		}
	}
	/**
	 * Mostra un missatge d'error
	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No estas autoritzat a eliminar l'usuari."));
 	}
 	/**
 	 * Mostra un missatge d'avís
 	 */
 	public void msgAvis(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Avis", "Cap pacient seleccionat."));
 	}
 	/**
 	 * Mostra un missatge d'informació
 	 */
 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Pacient eliminat."));
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

	public Collection<PacientJPA> getPacients() {
		return pacients;
	}

	public void setPacients(Collection<PacientJPA> pacients) {
		this.pacients = pacients;
	}	
}