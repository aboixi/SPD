package ejb;

import java.util.Collection;

import jpa.PacientJPA;

public interface PacientsNegociRemote {
	public Collection<PacientJPA> llistarPacients (String cif);
	public Collection<PacientJPA> eliminarPacient(String cip, String cif);
}
