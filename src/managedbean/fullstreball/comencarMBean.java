/**
 * TFG JEE-SimpleSPD - Component: Fulls de Treball
 * @author Albert Boix Isern
 */
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
import jpa.PacientJPA;
import jpa.UsuariEmpresaJPA;

/**
 * Bean per executar l'acció de començar a preparar els blísters.
 */
@ManagedBean (name="comencar")
@SessionScoped
public class comencarMBean implements Serializable{
	
	@EJB (name="FullTreballNegociEJB")
	FullTreballNegociRemote fullsRemotEJB;
	private Collection<PacientJPA> pacients;
	@SuppressWarnings("unused")
	private boolean sessionOK=false;
	private static final long serialVersionUID = 1L;
	/**
	 * Mostra per pantalla una llista amb els pacients que tenen un tractament actiu i no tenen fet
	 * el blíster d'aquella setmana.
	 */
	public String comencar()throws Exception{
		if (checkSession()){
			String cif=getSessionCif();
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			fullsRemotEJB = (FullTreballNegociRemote) ctx.lookup("java:app/SPD.jar/FullTreballNegociEJB!ejb.FullTreballNegociRemote");
			this.pacients=fullsRemotEJB.llistarFulls(cif);
			this.setPacients(pacients);
			setExpedientsSessio(pacients);
			return "vistaUsuariModificarFull";
		}else{
			return "accessError";
		}
	}
	/**
	 * Puja a sessió un llistat amb els expedients dels pacients.
	 */
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
	 * Consulta el cif de l'empresa de l'usuari actiu
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
	public Collection<PacientJPA> getPacients() {
		return pacients;
	}

	public void setPacients(Collection<PacientJPA> pacients) {
		this.pacients = pacients;
	}
}