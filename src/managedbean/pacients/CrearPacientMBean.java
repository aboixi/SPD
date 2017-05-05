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
	
 	public String nouPacient() throws Exception{
 		if (checkSession()){
			UsuariEmpresaJPA usuari = getSessionObject();
			cifResidencia=usuari.getEmpresa();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			pacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
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

 	public void clearFields(){
 	}
	
 	public void msgCorrecte(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nou pacient creat."));
 	}
 	
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El pacient ja existeix al sistema."));
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
	

	/**
	 * @return the cip
	 */
	public String getCip() {
		return cip;
	}

	/**
	 * @param cip the cip to set
	 */
	public void setCip(String cip) {
		this.cip = cip;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the cognom1
	 */
	public String getCognom1() {
		return cognom1;
	}

	/**
	 * @param cognom1 the cognom1 to set
	 */
	public void setCognom1(String cognom1) {
		this.cognom1 = cognom1;
	}

	/**
	 * @return the cognom2
	 */
	public String getCognom2() {
		return cognom2;
	}

	/**
	 * @param cognom2 the cognom2 to set
	 */
	public void setCognom2(String cognom2) {
		this.cognom2 = cognom2;
	}

	/**
	 * @return the malalties
	 */
	public String getMalalties() {
		return malalties;
	}

	/**
	 * @param malalties the malalties to set
	 */
	public void setMalalties(String malalties) {
		this.malalties = malalties;
	}

	/**
	 * @return the alergies
	 */
	public String getAlergies() {
		return alergies;
	}

	/**
	 * @param alergies the alergies to set
	 */
	public void setAlergies(String alergies) {
		this.alergies = alergies;
	}

	/**
	 * @return the metge
	 */
	public String getMetge() {
		return metge;
	}

	/**
	 * @param metge the metge to set
	 */
	public void setMetge(String metge) {
		this.metge = metge;
	}

	/**
	 * @return the autoritzacio
	 */
	public boolean isAutoritzacio() {
		return autoritzacio;
	}

	/**
	 * @param autoritzacio the autoritzacio to set
	 */
	public void setAutoritzacio(boolean autoritzacio) {
		this.autoritzacio = autoritzacio;
	}

	/**
	 * @return the spd
	 */
	public boolean isSpd() {
		return spd;
	}

	/**
	 * @param spd the spd to set
	 */
	public void setSpd(boolean spd) {
		this.spd = spd;
	}

	/**
	 * @return the hospitalitzat
	 */
	public boolean isHospitalitzat() {
		return hospitalitzat;
	}

	/**
	 * @param hospitalitzat the hospitalitzat to set
	 */
	public void setHospitalitzat(boolean hospitalitzat) {
		this.hospitalitzat = hospitalitzat;
	}

	/**
	 * @return the exitus
	 */
	public boolean isExitus() {
		return exitus;
	}

	/**
	 * @param exitus the exitus to set
	 */
	public void setExitus(boolean exitus) {
		this.exitus = exitus;
	}

	/**
	 * @return the cifResidencia
	 */
	public String getCifResidencia() {
		return cifResidencia;
	}

	/**
	 * @param cifResidencia the cifResidencia to set
	 */
	public void setCifResidencia(String cifResidencia) {
		this.cifResidencia = cifResidencia;
	}

	/**
	 * @return the nomResidencia
	 */
	public String getNomResidencia() {
		return nomResidencia;
	}

	/**
	 * @param nomResidencia the nomResidencia to set
	 */
	public void setNomResidencia(String nomResidencia) {
		this.nomResidencia = nomResidencia;
	}

	/**
	 * @return the cifFarmacia
	 */
	public String getCifFarmacia() {
		return cifFarmacia;
	}

	/**
	 * @param cifFarmacia the cifFarmacia to set
	 */
	public void setCifFarmacia(String cifFarmacia) {
		this.cifFarmacia = cifFarmacia;
	}

	/**
	 * @return the nomFarmacia
	 */
	public String getNomFarmacia() {
		return nomFarmacia;
	}

	/**
	 * @param nomFarmacia the nomFarmacia to set
	 */
	public void setNomFarmacia(String nomFarmacia) {
		this.nomFarmacia = nomFarmacia;
	}
}
