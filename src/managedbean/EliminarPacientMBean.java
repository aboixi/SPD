package managedbean;

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
import javax.persistence.PersistenceException;
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
	
	public String eliminar() throws NamingException{
		if (checkSession()){
			String cif=getSessionCif();
			String cip=pacient.getCip();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			try{
				PacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
				PacientsRemotEJB.eliminarPacient(cip, cif);
			}catch (PersistenceException e){
				clearFields();
				msgError();
			}
			return null;
		}else{
			return "accessError";
		}
	}
	
	public void actualitzar(){}
		
 	public void clearFields(){
 		setPacient(null);
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
	
	public String getSessionCif(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getEmpresa();
	}
	
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No estas autoritzat a eliminar l'usuari."));
 	}

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
	 * @return the pacients
	 */
	public Collection<PacientJPA> getPacients() {
		return pacients;
	}
	/**
	 * @param pacients the pacients to set
	 */
	public void setPacients(Collection<PacientJPA> pacients) {
		this.pacients = pacients;
	}	
}