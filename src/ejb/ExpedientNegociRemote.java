package ejb;

import java.util.Collection;
import java.util.Date;

import javax.persistence.PersistenceException;

import jpa.ExpedientJPA;
import jpa.PacientJPA;


public interface ExpedientNegociRemote {

	public Collection<PacientJPA> llistarExpedients(String cif);
	public ExpedientJPA consultarExpedient(int idExpedient);
	public String agregarTractament(String idExpedient, String cn,Date dInici, String qEntera, String qFraccio, 
			boolean esmorcar, boolean dinar, boolean sopar, boolean dormir,boolean dill, boolean dima, boolean dime, boolean dijo, boolean dive, boolean diss, boolean dium, boolean foraBlister);
	public void eliminarTractament(int id)throws PersistenceException;
}
