/**
 * TFG JEE-SimpleSPD - Component: Expedient
 * @author Albert Boix Isern
 */
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

/**
 * Bean per buscar un medicament
 */
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
	
	/**
	 * Busca un medicament a la base de dades
	 */
	public String buscarMedicament()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			expedientRemotEJB = (ExpedientNegociRemote) ctx.lookup("java:app/SPD.jar/ExpedientNegociEJB!ejb.ExpedientNegociRemote");
			setMedicaments(expedientRemotEJB.buscarMedicaments(paraula));
			setParaula(null);
			return "vistaUsuariModificarExpedient";
		}else{
			return "accessError";
		}
	}
	/**
	 * Còpia un medicament del buscador al formulari de creació del tractament.
	 */
	public void copiarMedicament(){
		agregarTractamentMBean.setCn(getMedicament().getCn());
		agregarTractamentMBean.setNomComercial(getMedicament().getNomComercial());
	}
	/**
	 * Neteja els camps del formulari del buscador de medicaments
	 */
	public void clearFields(){
		this.setParaula(null);
		this.medicaments.clear();
		this.setMedicaments(medicaments);
	}
	/**
	 * Mètode per refrescar el formulari
	 */
	public void actualitzaFormulari(){}
	
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
	 * Getters i Setters
	 */
	public ExpedientNegociRemote getExpedientRemotEJB() {
		return expedientRemotEJB;
	}

	public void setExpedientRemotEJB(ExpedientNegociRemote expedientRemotEJB) {
		this.expedientRemotEJB = expedientRemotEJB;
	}

	public Collection<MedicamentJPA> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(Collection<MedicamentJPA> medicaments) {
		this.medicaments = medicaments;
	}

	public String getParaula() {
		return paraula;
	}

	public void setParaula(String paraula) {
		this.paraula = paraula;
	}

	public MedicamentJPA getMedicament() {
		return medicament;
	}

	public void setMedicament(MedicamentJPA medicament) {
		this.medicament = medicament;
	}

	public AgregarTractamentMBean getAgregarTractamentMBean() {
		return agregarTractamentMBean;
	}

	public void setAgregarTractamentMBean(AgregarTractamentMBean agregarTractamentMBean) {
		this.agregarTractamentMBean = agregarTractamentMBean;
	}
}