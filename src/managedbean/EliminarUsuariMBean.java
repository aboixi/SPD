package managedbean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.UsuarisNegociRemote;
import jpa.UsuariEmpresaJPA;

@ManagedBean (name="eliminarUsuari")
@SessionScoped
public class EliminarUsuariMBean implements Serializable{
	
	@EJB (name="UsuarisNegociEJB")
	UsuarisNegociRemote usuarisRemotEJB;
	private UsuariEmpresaJPA usuari;
	private Collection<UsuariEmpresaJPA> usuaris;
	private static final long serialVersionUID = 1L;
	
	public void eliminar() throws NamingException{
		String dni=usuari.getDni();
		String cif=usuari.getEmpresa();
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		usuarisRemotEJB = (UsuarisNegociRemote) ctx.lookup("java:app/SPD.jar/UsuarisNegociEJB!ejb.UsuarisNegociRemote");
		usuarisRemotEJB.eliminarUsuari(cif, dni);
//		return "vistaUsuaris";
	}

	/**
	 * @return the usuari
	 */
	public UsuariEmpresaJPA getUsuari() {
		return usuari;
	}

	/**
	 * @param usuari the usuari to set
	 */
	public void setUsuari(UsuariEmpresaJPA usuari) {
		this.usuari = usuari;
	}

	/**
	 * @return the usuaris
	 */
	public Collection<UsuariEmpresaJPA> getUsuaris() {
		return usuaris;
	}

	/**
	 * @param usuaris the usuaris to set
	 */
	public void setUsuaris(Collection<UsuariEmpresaJPA> usuaris) {
		this.usuaris = usuaris;
	}
}
