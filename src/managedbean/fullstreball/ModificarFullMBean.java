package managedbean.fullstreball;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.FullTreballNegociRemote;
import jpa.FullDeTreballJPA;
import jpa.MedicamentJPA;
import jpa.TractamentJPA;
import managedbean.expedient.buscarMedicamentMBean;

/**
 * 
 */
@ManagedBean (name="modificarFull")
@SessionScoped
public class ModificarFullMBean implements Serializable{
	
	@EJB (name="FullTreballNegociEJB")
	FullTreballNegociRemote fullsRemotEJB;
	private FullDeTreballJPA full;
	private TractamentJPA tractament;
	private MedicamentJPA medicament;
	private List<Integer> idExpedients = new ArrayList<Integer>();
	@ManagedProperty("#{crearFull}")
	private CrearFullDeTreballMBean crearFullMBean;
	@ManagedProperty("#{buscarMedicament}")
	private buscarMedicamentMBean buscarMedMBean;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String modificarFull()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			fullsRemotEJB = (FullTreballNegociRemote) ctx.lookup("java:app/SPD.jar/FullTreballNegociEJB!ejb.FullTreballNegociRemote");
			fullsRemotEJB.modificarFull(getFullSessio());
			idExpedients = getExpedientsSessio();
			idExpedients.remove(0);
			setExpedientsSessio(idExpedients);
		}else{
			return "accessError";
		}
		return null;
	}
	
	public void preModificarMedicament(){		
		this.tractament=crearFullMBean.getTractament();
		this.tractament.setMedicament(buscarMedMBean.getMedicament());
	}
	
	public void modificarMedicament(){
		this.tractament=crearFullMBean.getTractament();
		this.tractament.setNumLot(crearFullMBean.getTractament().getNumLot());
		this.full=getFullSessio();
		crearFullMBean.setTractament(this.tractament);
		setFullSessio(this.full);
		crearFullMBean.setTractament(tractament);
	}
	
	public FullDeTreballJPA getFullSessio(){
		FullDeTreballJPA fullTreball = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		fullTreball =(FullDeTreballJPA) activeSession.getAttribute("full");
		return fullTreball;
	}
	
	public void setFullSessio(FullDeTreballJPA fullTreball){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		activeSession.setAttribute("full", fullTreball);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getExpedientsSessio(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		List<Integer> indexExpedients = new ArrayList<Integer>();
		indexExpedients = (List<Integer>) activeSession.getAttribute("indexExpedients");
		return indexExpedients;
	}
	
	public void setExpedientsSessio(List<Integer> llistaId){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		activeSession.setAttribute("indexExpedients", llistaId);
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

	/**
	 * @return the full
	 */
	public FullDeTreballJPA getFull() {
		return full;
	}

	/**
	 * @param full the full to set
	 */
	public void setFull(FullDeTreballJPA full) {
		this.full = full;
	}

	/**
	 * @return the tractament
	 */
	public TractamentJPA getTractament() {
		return tractament;
	}

	/**
	 * @param tractament the tractament to set
	 */
	public void setTractament(TractamentJPA tractament) {
		this.tractament = tractament;
	}

	/**
	 * @return the medicament
	 */
	public MedicamentJPA getMedicament() {
		return medicament;
	}

	/**
	 * @param medicament the medicament to set
	 */
	public void setMedicament(MedicamentJPA medicament) {
		this.medicament = medicament;
	}

	/**
	 * @return the crearFullMBean
	 */
	public CrearFullDeTreballMBean getCrearFullMBean() {
		return crearFullMBean;
	}

	/**
	 * @param crearFullMBean the crearFullMBean to set
	 */
	public void setCrearFullMBean(CrearFullDeTreballMBean crearFullMBean) {
		this.crearFullMBean = crearFullMBean;
	}

	/**
	 * @return the buscarMedMBean
	 */
	public buscarMedicamentMBean getBuscarMedMBean() {
		return buscarMedMBean;
	}

	/**
	 * @param buscarMedMBean the buscarMedMBean to set
	 */
	public void setBuscarMedMBean(buscarMedicamentMBean buscarMedMBean) {
		this.buscarMedMBean = buscarMedMBean;
	}

	/**
	 * @return the idExpedients
	 */
	public List<Integer> getIdExpedients() {
		return idExpedients;
	}

	/**
	 * @param idExpedients the idExpedients to set
	 */
	public void setIdExpedients(List<Integer> idExpedients) {
		this.idExpedients = idExpedients;
	}

}
