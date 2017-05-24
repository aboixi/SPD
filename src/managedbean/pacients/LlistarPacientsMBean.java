/**
 * TFG JEE-SimpleSPD - Component: Pacients
 * @author Albert Boix Isern
 */
package managedbean.pacients;

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

import ejb.PacientsNegociRemote;
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per llistar els usuaris vinculats a la empresa.
 */
@ManagedBean (name="llistarPacients")
@SessionScoped
public class LlistarPacientsMBean implements Serializable{
	
	@EJB (name="PacientsNegociEJB")
	PacientsNegociRemote pacientsRemotEJB;
	private Collection<PacientJPA> pacients;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mostra una llista amb els pacients vinculats a l'empresa
	 */
	public String llistar()throws Exception{
		if (checkSession()){
			String cif=getSessionCif();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			pacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
			this.pacients=pacientsRemotEJB.llistarPacients(cif);
			this.setPacients(pacients);
			return "vistaUsuariPacients";
		}else{
			return "accessError";
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
	 * Consulta el cif de l'empresa amb l'usuari actiu.
	 */
	public String getSessionCif(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari.getEmpresa();
	}
	
	/**
	 * Getters i setters
	 */
	public Collection<PacientJPA> getPacients() {
		return pacients;
	}

	public void setPacients(Collection<PacientJPA> pacients) {
		this.pacients = pacients;
	}
}