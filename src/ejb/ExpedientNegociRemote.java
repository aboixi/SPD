package ejb;

import java.util.Collection;

import jpa.ExpedientJPA;
import jpa.PacientJPA;

public interface ExpedientNegociRemote {

	public Collection<PacientJPA> llistarExpedients(String cif);
	public ExpedientJPA consultarExpedient(String cip);

}
