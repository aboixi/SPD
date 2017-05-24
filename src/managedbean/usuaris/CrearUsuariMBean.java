/**
 * TFG JEE-SimpleSPD - Component: Usuaris
 * @author Albert Boix Isern
 */
package managedbean.usuaris;

import java.io.Serializable;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import ejb.UsuarisNegociRemote;
import jpa.EmpresaJPA;
/**
 * Bean per crear un nou usuari vinculat a empresa
 */
@ManagedBean(name = "crearUsuari")
@SessionScoped
public class CrearUsuariMBean implements Serializable{

	@EJB(name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	private String dni;
	private String nom;
	private String cognom1;
	private String cognom2;
	private String telefon;
	private String empresa;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;	
	/**
	 * Crea un nou usuari vinculat a l'emapresa que el dona d'alta
	 */
 	public String nouUsuari() throws Exception{
 		if (checkSession()){
			EmpresaJPA empresa = getSessionObject();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
			String missatge=usuarisRemotEJB.crearUsuari(dni,nom,cognom1,cognom2,telefon,empresa.getCif(),empresa.getTipus());
			if (missatge.equals("procesCorrecte")){
				clearFields();
				msgCorrecte();
				return null;
			}else if(missatge.equals("usuariExistent")){
				clearFields();
				msgError();
				return null;
			}else if(missatge.equals("nomUsuariRepetit")){
				clearFields();
				msgAvis();
			}
			return null;
		}else{
			return "accesError";
			}
 		}
 	/**
 	 * Neteja els camps del formulari de donar d'alta un nou usuari
 	 */
 	public void clearFields(){
 		setDni(null);
 		setNom(null);
 		setCognom1(null);
 		setCognom2(null);
 		setTelefon(null);
 		setEmpresa(null);
 	}
	/**
	 * Mostra un missatge d'informació
	 */
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nou usuari creat."));
 	}
 	/**
 	 * Mostra un missatge d'avís
 	 */
 	public void msgAvis(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Avís", "El nom d'usuari per entrar al sistema serà el seu DNI"));
 	}
 	/**
 	 * Mostra un missatge d'error
 	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "L'usuari ja existeix al sistema."));
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
	 * Consulta la empresa pujada a sessió
	 */
	public EmpresaJPA getSessionObject(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		EmpresaJPA empresa = (EmpresaJPA) activeSession.getAttribute("sessioEmpresa");
		return empresa;
	}

	/**
	 * Getters i setters
	 */
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognom1() {
		return cognom1;
	}

	public void setCognom1(String cognom1) {
		this.cognom1 = cognom1;
	}

	public String getCognom2() {
		return cognom2;
	}

	public void setCognom2(String cognom2) {
		this.cognom2 = cognom2;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}