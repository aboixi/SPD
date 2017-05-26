/**
 * TFG JEE-SimpleSPD - Component: Pacients
 * @author Albert Boix Isern
 */
package managedbean.pacients;

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

import ejb.PacientsNegociRemote;
import jpa.EmpresaJPA;
import jpa.UsuariEmpresaJPA;
/**
 * Bean per crear un pacient
 */
@ManagedBean(name = "crearPacient")
@SessionScoped
public class CrearPacientMBean implements Serializable{

	@EJB(name="PacientsNegociEJB")
	PacientsNegociRemote pacientsRemotEJB;

	private String cip;
	private String nom;
	private String cognom1;
	private String cognom2;
	private String malalties;
	private String alergies;
	private String metge;
	private String cifResidencia;
	private String nomResidencia;
	private String cifFarmacia;
	private String nomFarmacia;
	private boolean autoritzacio=true;
	private boolean spd=true;
	private boolean hospitalitzat=false;
	private boolean exitus=false;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;	
	
	/**
	 * Crea un nou pacient al sistema vinculat amb l'empresa de l'usuari que el crea
	 */
 	public String nouPacient() throws Exception{
 		if (checkSession()){
			UsuariEmpresaJPA usuari = getSessionObject();
			cifResidencia=usuari.getEmpresa();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			pacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SimpleSPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
			EmpresaJPA empresa = pacientsRemotEJB.consultarEmpresa(cifResidencia);
			if (empresa.getTipus().equals("Residencia")){
				nomResidencia=empresa.getNom();
				cifFarmacia="00000000F";
				nomFarmacia="NINGUNA FARMACIA";
				String missatge=pacientsRemotEJB.crearPacient(cip, nom, cognom1, cognom2, malalties,alergies, metge, cifResidencia, nomResidencia, cifFarmacia, nomFarmacia, autoritzacio, spd, hospitalitzat, exitus);
				if (missatge.equals("procesCorrecte")){
					clearFields();
					msgCorrecte();
					return null;
				}else if(missatge.equals("pacientExistent")){
					clearFields();
					msgError();
					return null;
				}
			}else if (empresa.getTipus().equals("Farmacia")){
				nomResidencia=empresa.getNom();
				cifFarmacia=empresa.getCif();
				nomFarmacia=empresa.getNom();
				String missatge=pacientsRemotEJB.crearPacient(cip, nom, cognom1, cognom2, malalties,alergies, metge, cifResidencia, nomResidencia, cifFarmacia, nomFarmacia, autoritzacio, spd, hospitalitzat, exitus);
				if (missatge.equals("procesCorrecte")){
					clearFields();
					msgCorrecte();
					return null;
				}else if(missatge.equals("pacientExistent")){
					clearFields();
					msgError();
					return null;
				}
			}
			return null;
		}else{
			return "accesError";
		}
	}
 	/**
 	 * Neteja els camps del formulari de crear pacient
 	 */
 	public void clearFields(){
 		setCip(null);
 		setNom(null);
 		setCognom1(null);
 		setCognom2(null);
 		setMetge(null);
 		setAlergies(null);
 		setMalalties(null);
 	}
	/**
	 * Mostra un missatge d'informació
	 */
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nou pacient creat."));
 	}
 	/**
 	 * Mostra un missatge d'error
 	 */
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El pacient ja existeix al sistema."));
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
	 * Consulta l'usuari pujat a sessió
	 */
	public UsuariEmpresaJPA getSessionObject(){
 		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		UsuariEmpresaJPA usuari = (UsuariEmpresaJPA) activeSession.getAttribute("sessioUsuari");
		return usuari;
	}
	
	/**
	 * Getters i setters
	 */
	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
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

	public String getMalalties() {
		return malalties;
	}

	public void setMalalties(String malalties) {
		this.malalties = malalties;
	}

	public String getAlergies() {
		return alergies;
	}

	public void setAlergies(String alergies) {
		this.alergies = alergies;
	}

	public String getMetge() {
		return metge;
	}

	public void setMetge(String metge) {
		this.metge = metge;
	}

	public boolean isAutoritzacio() {
		return autoritzacio;
	}

	public void setAutoritzacio(boolean autoritzacio) {
		this.autoritzacio = autoritzacio;
	}

	public boolean isSpd() {
		return spd;
	}

	public void setSpd(boolean spd) {
		this.spd = spd;
	}

	public boolean isHospitalitzat() {
		return hospitalitzat;
	}

	public void setHospitalitzat(boolean hospitalitzat) {
		this.hospitalitzat = hospitalitzat;
	}

	public boolean isExitus() {
		return exitus;
	}

	public void setExitus(boolean exitus) {
		this.exitus = exitus;
	}

	public String getCifResidencia() {
		return cifResidencia;
	}

	public void setCifResidencia(String cifResidencia) {
		this.cifResidencia = cifResidencia;
	}

	public String getNomResidencia() {
		return nomResidencia;
	}

	public void setNomResidencia(String nomResidencia) {
		this.nomResidencia = nomResidencia;
	}

	public String getCifFarmacia() {
		return cifFarmacia;
	}

	public void setCifFarmacia(String cifFarmacia) {
		this.cifFarmacia = cifFarmacia;
	}

	public String getNomFarmacia() {
		return nomFarmacia;
	}

	public void setNomFarmacia(String nomFarmacia) {
		this.nomFarmacia = nomFarmacia;
	}
}
