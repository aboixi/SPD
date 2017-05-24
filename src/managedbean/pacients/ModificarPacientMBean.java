/**
 * TFG JEE-SimpleSPD - Component: Pacients
 * @author Albert Boix Isern
 */
package managedbean.pacients;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import ejb.PacientsNegociRemote;
import jpa.EmpresaJPA;
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per modificar les dades d'un pacient.
 */
@ManagedBean(name="modificarPacient")
@SessionScoped
public class ModificarPacientMBean implements Serializable{
	@EJB(name="PacientsNegociEJB")
	PacientsNegociRemote pacientsRemotEJB;
	@ManagedProperty("#{eliminarPacient}")
	private EliminarPacientMBean eliminarPacientMBean;
	private PacientJPA pacient;
	private Collection<EmpresaJPA>farmacies;
	private String[] llistaNomsFarmacies;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Modifica les dades personals d'un pacient
	 */
	public String modificar() throws Exception{
		if (checkSession()){
			if (eliminarPacientMBean.getPacient()==null){
				msgAvis();
				return null;
			}
			String cif = getSessionCif();
			consultaFarmacies();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			pacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
			String missatge=null;
			missatge=pacientsRemotEJB.modificarPacient(cif, eliminarPacientMBean.getPacient().getCip(),eliminarPacientMBean.getPacient().getNom(),eliminarPacientMBean.getPacient().getCognom1(),eliminarPacientMBean.getPacient().getCognom2(),
					eliminarPacientMBean.getPacient().getNomFarmacia(),eliminarPacientMBean.getPacient().getFarmacia(),eliminarPacientMBean.getPacient().getMalalties(),eliminarPacientMBean.getPacient().getAlergies(),eliminarPacientMBean.getPacient().getMetge(),eliminarPacientMBean.getPacient().isAutoritzacio(),
					eliminarPacientMBean.getPacient().isSpd(),eliminarPacientMBean.getPacient().isHospitalitzat(),eliminarPacientMBean.getPacient().isExitus());
			if (missatge.equals("canviCorrecte")){
				msgInfo();
				return "vistaUsuariPacients";
			}else if (missatge.equals("canviNoGuardat")){
				msgError();
				return "vistaUsuariPacients";
			}
		}else{
			return "accessError";
		}
		return null;
	}
	
	/**
	 * Mostra un llistat amb les farmacies donades d'alta al sistema
	 */
	public void consultaFarmacies() throws NamingException{
		clearFields();//Evita que aparegui el llistat de farmacies quan l'usuari es d'una.
		this.farmacies=null;
		EmpresaJPA farmacia = null;
		if (comprovaTipusUsuari().equals("Residencia")){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			pacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
			this.farmacies = pacientsRemotEJB.consultarFarmacies();
			this.llistaNomsFarmacies=new String [farmacies.size()];
			Iterator <EmpresaJPA> iter = farmacies.iterator();
			int i = 0;
			while (iter.hasNext()){
				farmacia = iter.next();
				this.llistaNomsFarmacies[i]=farmacia.getNom();
				i++;
			}
		}
	}
	
	/**
	 * Comprova si l'usuari actiu és una farmàcia o una residència
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
	 * Neteja els camps del formulari de modificar pacient
	 */
 	public void clearFields(){
 		setLlistaNomsFarmacies(null);
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
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No estas autoritzat a modificar l'usuari."));
 	}
 	/**
 	 * Mostra un missatge d'avís
 	 */
 	public void msgAvis(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Avis", "Cap pacient seleccionat."));
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
	public EliminarPacientMBean getEliminarPacientMBean() {
		return eliminarPacientMBean;
	}

	public void setEliminarPacientMBean(EliminarPacientMBean eliminarPacientMBean) {
		this.eliminarPacientMBean = eliminarPacientMBean;
	}

	public PacientJPA getPacient() {
		return pacient;
	}

	public void setPacient(PacientJPA pacient) {
		this.pacient = pacient;
	}

	public Collection<EmpresaJPA> getFarmacies() {
		return farmacies;
	}

	public void setFarmacies(Collection<EmpresaJPA> farmacies) {
		this.farmacies = farmacies;
	}

	public String[] getLlistaNomsFarmacies() {
		return llistaNomsFarmacies;
	}

	public void setLlistaNomsFarmacies(String[] llistaNomsFarmacies) {
		this.llistaNomsFarmacies = llistaNomsFarmacies;
	}
}