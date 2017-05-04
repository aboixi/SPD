package managedbean;

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
	
	public String modificar() throws Exception{
		if (checkSession()){
			String cif = getSessionCif();
			consultaFarmacies();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			pacientsRemotEJB = (PacientsNegociRemote) ctx.lookup("java:app/SPD.jar/PacientsNegociEJB!ejb.PacientsNegociRemote");
			String missatge=null;
			missatge=pacientsRemotEJB.modificarPacient(cif, eliminarPacientMBean.getPacient().getCip(),eliminarPacientMBean.getPacient().getNom(),eliminarPacientMBean.getPacient().getCognom1(),eliminarPacientMBean.getPacient().getCognom2(),
					eliminarPacientMBean.getPacient().getNomFarmacia(),eliminarPacientMBean.getPacient().getFarmacia(),eliminarPacientMBean.getPacient().getMalalties(),eliminarPacientMBean.getPacient().getAlergies(),eliminarPacientMBean.getPacient().getMetge(),eliminarPacientMBean.getPacient().getAutoritzacio(),
					eliminarPacientMBean.getPacient().isSpd(),eliminarPacientMBean.getPacient().getHospitalitzat(),eliminarPacientMBean.getPacient().getExitus());
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
	
 	public void clearFields(){
 		setLlistaNomsFarmacies(null);
 	}
	
 	public void msgInfo(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Canvi correcte."));
 	}
 	public void msgError(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No estas autoritzat a modificar l'usuari."));
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

	/**
	 * @return the eliminarPacientMBean
	 */
	public EliminarPacientMBean getEliminarPacientMBean() {
		return eliminarPacientMBean;
	}

	/**
	 * @param eliminarPacientMBean the eliminarPacientMBean to set
	 */
	public void setEliminarPacientMBean(EliminarPacientMBean eliminarPacientMBean) {
		this.eliminarPacientMBean = eliminarPacientMBean;
	}

	/**
	 * @return the pacient
	 */
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
	 * @return the farmacies
	 */
	public Collection<EmpresaJPA> getFarmacies() {
		return farmacies;
	}

	/**
	 * @param farmacies the farmacies to set
	 */
	public void setFarmacies(Collection<EmpresaJPA> farmacies) {
		this.farmacies = farmacies;
	}

	/**
	 * @return the llistaNomsFarmacies
	 */
	public String[] getLlistaNomsFarmacies() {
		return llistaNomsFarmacies;
	}

	/**
	 * @param llistaNomsFarmacies the llistaNomsFarmacies to set
	 */
	public void setLlistaNomsFarmacies(String[] llistaNomsFarmacies) {
		this.llistaNomsFarmacies = llistaNomsFarmacies;
	}
}