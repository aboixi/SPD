package managedbean.fullstreball;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.FullTreballNegociRemote;
import jpa.ExpedientJPA;
import jpa.FullDeTreballJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;
import jpa.UsuariEmpresaJPA;

/**
 * 
 */
@ManagedBean (name="crearFull")
@SessionScoped
public class CrearFullDeTreballMBean implements Serializable{
	
	@EJB (name="FullTreballNegociEJB")
	FullTreballNegociRemote fullsRemotEJB;
	private List<Integer> idExpedients = new ArrayList<Integer>();
	private String [] blister = new String[28];
	private ExpedientJPA expedient;
	private TractamentJPA tractament;
	private FullDeTreballJPA full;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	
	public String crearFull()throws Exception{
		if (checkSession()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			fullsRemotEJB = (FullTreballNegociRemote) ctx.lookup("java:app/SPD.jar/FullTreballNegociEJB!ejb.FullTreballNegociRemote");
			this.idExpedients = getExpedientsSessio();
			int idExpedient = consultaPrimerIdExpedient();
			if(idExpedient==-1){
				return "vistaUsuariExpedients";
			}else{
				this.expedient = fullsRemotEJB.consultarExpedient(idExpedient);	
				this.full = new FullDeTreballJPA(expedient);
				setFullSessio(full);
				return "vistaUsuariModificarFull?faces-redirect=true";
			}
		}else{
			return "accessError";
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getExpedientsSessio(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		List<Integer> indexExpedients = new ArrayList<Integer>();
		indexExpedients = (List<Integer>) activeSession.getAttribute("indexExpedients");
		return indexExpedients;
	}
	
	public void setExpedientsSessio(Collection<PacientJPA> pacients){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		List<Integer> indexExpedients = new ArrayList<Integer>();
		PacientJPA pacient = null;
		Iterator<PacientJPA> iter = pacients.iterator();
		while (iter.hasNext()){
			pacient = iter.next();
			indexExpedients.add(pacient.getExpedient().getId());
		}
		activeSession.setAttribute("indexExpedients", indexExpedients);
	}
	
	public void setFullSessio(FullDeTreballJPA full){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		activeSession.setAttribute("full", full);
	}
	
	public int consultaPrimerIdExpedient(){
		int idExpedient=-1;
		if (this.idExpedients.isEmpty()){
			return idExpedient;
		}else{
			return this.idExpedients.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public void eliminarIdExpedientSessio(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession activeSession = (HttpSession) facesContext.getExternalContext().getSession(true);
		List<Integer> indexExpedients = (List<Integer>) activeSession.getAttribute("indexExpedients");
		if (!(indexExpedients.isEmpty())){
			indexExpedients.remove(0);
		}
	}
	
	public String actualitzaFormulariBlister(){
		ompleBlister();
		return "vistaUsuariModificarFull";
	}

	public void ompleBlister(){
		
		int i=0;
		String [] preses=new String[4];
		if (this.tractament.isEsmorcar()){
			preses[0]=this.tractament.getQuantitatPresa();
		}if (this.tractament.isDinar()){
			preses[1]=this.tractament.getQuantitatPresa();
		}if (this.tractament.isSopar()){
			preses[2]=this.tractament.getQuantitatPresa();
		}if (this.tractament.isDormir()){
			preses[3]=this.tractament.getQuantitatPresa();
		}
		
		if (this.tractament.isDilluns()){
			this.blister[i]=preses[0];
			this.blister[i+1]=preses[1];
			this.blister[i+2]=preses[2];
			this.blister[i+3]=preses[3];
		}else{
			this.blister[i]="0";
			this.blister[i+1]="0";
			this.blister[i+2]="0";
			this.blister[i+3]="0";
		}
		if (this.tractament.isDimarts()){
			this.blister[i+4]=preses[0];
			this.blister[i+5]=preses[1];
			this.blister[i+6]=preses[2];
			this.blister[i+7]=preses[3];
		}else{
			this.blister[i+4]="0";
			this.blister[i+5]="0";
			this.blister[i+6]="0";
			this.blister[i+7]="0";
		}
		if (this.tractament.isDimecres()){
			this.blister[i+8]=preses[0];
			this.blister[i+9]=preses[1];
			this.blister[i+10]=preses[2];
			this.blister[i+11]=preses[3];
		}else{
			this.blister[i+8]="0";
			this.blister[i+9]="0";
			this.blister[i+10]="0";
			this.blister[i+11]="0";
		}
		if (this.tractament.isDijous()){
			this.blister[i+12]=preses[0];
			this.blister[i+13]=preses[1];
			this.blister[i+14]=preses[2];
			this.blister[i+15]=preses[3];
		}else{
			this.blister[i+12]="0";
			this.blister[i+13]="0";
			this.blister[i+14]="0";
			this.blister[i+15]="0";
		}
		if (this.tractament.isDivendres()){
			this.blister[i+16]=preses[0];
			this.blister[i+17]=preses[1];
			this.blister[i+18]=preses[2];
			this.blister[i+19]=preses[3];
		}else{
			this.blister[i+16]="0";
			this.blister[i+17]="0";
			this.blister[i+18]="0";
			this.blister[i+19]="0";
		}
		if (this.tractament.isDissabte()){
			this.blister[i+20]=preses[0];
			this.blister[i+21]=preses[1];
			this.blister[i+22]=preses[2];
			this.blister[i+23]=preses[3];
		}else{
			this.blister[i+20]="0";
			this.blister[i+21]="0";
			this.blister[i+22]="0";
			this.blister[i+23]="0";
		}
		if (this.tractament.isDiumenge()){
			this.blister[i+24]=preses[0];
			this.blister[i+25]=preses[1];
			this.blister[i+26]=preses[2];
			this.blister[i+27]=preses[3];
		}else{
			this.blister[i+24]="0";
			this.blister[i+25]="0";
			this.blister[i+26]="0";
			this.blister[i+27]="0";
		}
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
	 * @return the fullsRemotEJB
	 */
	public FullTreballNegociRemote getFullsRemotEJB() {
		return fullsRemotEJB;
	}

	/**
	 * @param fullsRemotEJB the fullsRemotEJB to set
	 */
	public void setFullsRemotEJB(FullTreballNegociRemote fullsRemotEJB) {
		this.fullsRemotEJB = fullsRemotEJB;
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

	/**
	 * @return the blister
	 */
	public String[] getBlister() {
		return blister;
	}

	/**
	 * @param blister the blister to set
	 */
	public void setBlister(String[] blister) {
		this.blister = blister;
	}

	/**
	 * @return the expedient
	 */
	public ExpedientJPA getExpedient() {
		return expedient;
	}

	/**
	 * @param expedient the expedient to set
	 */
	public void setExpedient(ExpedientJPA expedient) {
		this.expedient = expedient;
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
	
}
