package managedbean.expedient;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.ExpedientNegociRemote;
import jpa.MedicamentJPA;

@ManagedBean (name="buscarMedicament")
@SessionScoped
public class buscarMedicamentMBean implements Serializable{
	
	@EJB (name="ExpedientsNegociEJB")
	private ExpedientNegociRemote expedientRemotEJB;
	private String paraula;
	private MedicamentJPA medicament;
	private Collection<MedicamentJPA>medicaments;
	@ManagedProperty("#{agregarTractament}")
	private AgregarTractamentMBean agregarTractamentMBean;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String buscarMedicament()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			expedientRemotEJB = (ExpedientNegociRemote) ctx.lookup("java:app/SPD.jar/ExpedientNegociEJB!ejb.ExpedientNegociRemote");
			setMedicaments(expedientRemotEJB.buscarMedicaments(paraula));
			return "vistaUsuariModificarExpedient";
		}else{
			return "accessError";
		}
	}	
	public void copiarMedicament(){
		agregarTractamentMBean.setCn(getMedicament().getCn());
		agregarTractamentMBean.setNomComercial(getMedicament().getNomComercial());
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
	 * @return the expedientRemotEJB
	 */
	public ExpedientNegociRemote getExpedientRemotEJB() {
		return expedientRemotEJB;
	}

	/**
	 * @param expedientRemotEJB the expedientRemotEJB to set
	 */
	public void setExpedientRemotEJB(ExpedientNegociRemote expedientRemotEJB) {
		this.expedientRemotEJB = expedientRemotEJB;
	}

	/**
	 * @return the medicaments
	 */
	public Collection<MedicamentJPA> getMedicaments() {
		return medicaments;
	}

	/**
	 * @param medicaments the medicaments to set
	 */
	public void setMedicaments(Collection<MedicamentJPA> medicaments) {
		this.medicaments = medicaments;
	}

	/**
	 * @return the paraula
	 */
	public String getParaula() {
		return paraula;
	}

	/**
	 * @param paraula the paraula to set
	 */
	public void setParaula(String paraula) {
		this.paraula = paraula;
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
	 * @return the agregarTractamentMBean
	 */
	public AgregarTractamentMBean getAgregarTractamentMBean() {
		return agregarTractamentMBean;
	}
	/**
	 * @param agregarTractamentMBean the agregarTractamentMBean to set
	 */
	public void setAgregarTractamentMBean(AgregarTractamentMBean agregarTractamentMBean) {
		this.agregarTractamentMBean = agregarTractamentMBean;
	}
	
}