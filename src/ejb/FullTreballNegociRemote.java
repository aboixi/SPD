package ejb;

import java.util.Collection;

import jpa.ExpedientJPA;
import jpa.FullDeTreballJPA;
import jpa.PacientJPA;

public interface FullTreballNegociRemote {
	public Collection<PacientJPA> llistarFulls(String cif);
	public ExpedientJPA consultarExpedient(int idExpedient);
	public void modificarFull (FullDeTreballJPA full);
}
