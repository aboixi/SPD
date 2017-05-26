/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package managedbean.usuaris;

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

import ejb.UsuarisNegociRemote;
import jpa.UsuariEmpresaJPA;
/**
 * Bean per eliminar un usuari vinculat a empresa
 */
@ManagedBean (name="eliminarUsuari")
@SessionScoped
public class EliminarUsuariMBean implements Serializable{
	
	@EJB (name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	private UsuariEmpresaJPA usuari;
	private Collection<UsuariEmpresaJPA> usuaris;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Elimina un usuari 
	 */
	public String eliminar() throws NamingException{
		if (checkSession()){
			String dni=usuari.getDni();
			String cif=usuari.getEmpresa();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
			usuarisRemotEJB.eliminarUsuari(cif, dni);
			clearFields();
			return null;
		}else{
			return "accessError";
		}
	}
	/**
	 * Mètode utilitzat per refrescar un formulari.
	 */
	public void actualitzaFormulari(){}
	
	/**
	 * Neteja els camps del formulari alta pacient.
	 */
 	public void clearFields(){
 		setUsuari(null);
 	}
 	/**
	 * Mètode que comprova si l'usuari ha fet login i té la sessió activa.
	 * @return un booleà amb el resultat
	 */
	public boolean checkSession(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		if (activeSession!=null && activeSession.getAttribute("sessioEmpresa")!=null){
			return (this.sessionOK=true);
		}else{
			return (this.sessionOK=false);
		}
	}
	/**
	 * Mostra un missatge d'informació
	 */
 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Canvi correcte."));
 	}
 	/**
 	 * Mostra un missatge d'error
 	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Revisa les dades."));
 	}
	/**
	 * Getters i setters
	 */
	public UsuariEmpresaJPA getUsuari() {
		return usuari;
	}

	public void setUsuari(UsuariEmpresaJPA usuari) {
		this.usuari = usuari;
	}

	public Collection<UsuariEmpresaJPA> getUsuaris() {
		return usuaris;
	}

	public void setUsuaris(Collection<UsuariEmpresaJPA> usuaris) {
		this.usuaris = usuaris;
	}
}
