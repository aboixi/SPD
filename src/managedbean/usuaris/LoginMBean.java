/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package managedbean.usuaris;

import java.io.Serializable;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import ejb.UsuarisNegociRemote;
import jpa.EmpresaJPA;
import jpa.UsuariEmpresaJPA;
import managedbean.avisos.LlistarAvisosMBean;

/**
 * Bean per identificar-se al sistema
 */
@ManagedBean(name = "loginUsuari")
@SessionScoped
public class LoginMBean implements Serializable{
	
	@EJB(name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	@ManagedProperty("#{llistarAvisos}")
	private LlistarAvisosMBean llistarAvisosMBean;
	private String nom;
	private String clau;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mètode per identificar-se al sistema i pujar l'usuari o empresa a sessió si és correcte.
	 */
	public String login() throws Exception{	
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
		Object usuari = usuarisRemotEJB.login(nom, clau);
		String tipusUsuari=usuari.getClass().getName();

		if (tipusUsuari=="jpa.EmpresaJPA"){ 
			EmpresaJPA empresa = (EmpresaJPA) usuari;
			if (empresa.getCif().equals("invalid")){
				clearFields();
				return "accessError?faces-redirect=true";
			}else{
				activeSession.setAttribute("sessioEmpresa", empresa);
				clearFields();
				return "vistaEmpresaPerfil?faces-redirect=true";
			}
		}else{
			UsuariEmpresaJPA usuariEmpresa = (UsuariEmpresaJPA) usuari;
			if (usuariEmpresa.getDni().equals("invalid")){
				clearFields();
				return "accessError?faces-redirect=true";
			}else{
				activeSession.setAttribute("sessioUsuari", usuariEmpresa);
				clearFields();
				llistarAvisosMBean.llistarAvisos();
				return "vistaUsuariAvisos?faces-redirect=true";
			}			
		}
	}
	/**
	 * Mètode que invalida la sessió de l'usuari/empresa i el redirigeix cap a la vista de login
	 */
	public String logout() throws Exception{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		activeSession.removeAttribute("sessioEmpresa");
		activeSession.removeAttribute("sessioUsuari");
		activeSession.invalidate();
		
		return "inici?faces-redirect=true";
	}
	
	/**
	 * Neteja els camps del formulari de login
	 */
 	public void clearFields(){
 		setNom(null);
 		setClau(null);
 	}
	
	/**
	 * Getters i setters
	 */
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getClau() {
		return clau;
	}

	public void setClau(String clau) {
		this.clau = clau;
	}

	public LlistarAvisosMBean getLlistarAvisosMBean() {
		return llistarAvisosMBean;
	}

	public void setLlistarAvisosMBean(LlistarAvisosMBean llistarAvisosMBean) {
		this.llistarAvisosMBean = llistarAvisosMBean;
	}
}
