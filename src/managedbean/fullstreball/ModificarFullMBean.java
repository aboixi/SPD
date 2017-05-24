/**
 * TFG JEE-SimpleSPD - Component: Fulls de Treball
 * @author Albert Boix Isern
 */
package managedbean.fullstreball;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
 * Bean per modificar el full de treball
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
	
	/**
	 * Modifica el full de treball
	 */
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
	
	/**
	 * Consulta el tractament del full de treball i substitueix el tractament amb el medicament
	 * obtingut de la recerca amb el buscador de medicaments.
	 */
	public void preModificarMedicament(){		
		this.tractament=crearFullMBean.getTractament();
		if (tractament==null){
			 msgAvis();
		}
		try{
			this.tractament.setMedicament(buscarMedMBean.getMedicament());
		}catch (Exception e){
			System.out.println(e);
		}	
	}
	/**
	 * Modifica el medicament del tractament amb el medicament trobat al buscador
	 */
	public void modificarMedicament(){
		this.tractament=crearFullMBean.getTractament();
		this.tractament.setNumLot(crearFullMBean.getTractament().getNumLot());
		this.full=getFullSessio();
		crearFullMBean.setTractament(this.tractament);
		setFullSessio(this.full);
		crearFullMBean.setTractament(tractament);
		Collection<MedicamentJPA> medicaments = null;
		MedicamentJPA medicament = null;
		buscarMedMBean.setMedicaments(medicaments);
		buscarMedMBean.setMedicament(medicament);
		buscarMedMBean.setParaula(null);
	}
	/**
	 * Consulta el full de treball pujat a la sessió
	 */
	public FullDeTreballJPA getFullSessio(){
		FullDeTreballJPA fullTreball = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		fullTreball =(FullDeTreballJPA) activeSession.getAttribute("full");
		return fullTreball;
	}
	/**
	 * Puja a sessió el full de treball
	 */
	public void setFullSessio(FullDeTreballJPA fullTreball){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		activeSession.setAttribute("full", fullTreball);
	}
	/**
	 * Consulta la llista pujada a sessió que conté les id dels expedients assistencials
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getExpedientsSessio(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		List<Integer> indexExpedients = new ArrayList<Integer>();
		indexExpedients = (List<Integer>) activeSession.getAttribute("indexExpedients");
		return indexExpedients;
	}
	/**
	 * Puja a sessió la llista amb les id dels expedients 
	 */
	public void setExpedientsSessio(List<Integer> llistaId){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		activeSession.setAttribute("indexExpedients", llistaId);
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
	 * Mostra un missatge d'informació
	 */
 	public void msgAvis(){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Avís", "Selecciona primer un tractament"));
 	}

	/**
	 * Getters i setters
	 */
	public FullDeTreballJPA getFull() {
		return full;
	}

	public void setFull(FullDeTreballJPA full) {
		this.full = full;
	}

	public TractamentJPA getTractament() {
		return tractament;
	}

	public void setTractament(TractamentJPA tractament) {
		this.tractament = tractament;
	}

	public MedicamentJPA getMedicament() {
		return medicament;
	}

	public void setMedicament(MedicamentJPA medicament) {
		this.medicament = medicament;
	}

	public CrearFullDeTreballMBean getCrearFullMBean() {
		return crearFullMBean;
	}

	public void setCrearFullMBean(CrearFullDeTreballMBean crearFullMBean) {
		this.crearFullMBean = crearFullMBean;
	}

	public buscarMedicamentMBean getBuscarMedMBean() {
		return buscarMedMBean;
	}

	public void setBuscarMedMBean(buscarMedicamentMBean buscarMedMBean) {
		this.buscarMedMBean = buscarMedMBean;
	}

	public List<Integer> getIdExpedients() {
		return idExpedients;
	}

	public void setIdExpedients(List<Integer> idExpedients) {
		this.idExpedients = idExpedients;
	}
}
