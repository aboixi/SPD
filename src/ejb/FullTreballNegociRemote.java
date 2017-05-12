package ejb;

import java.util.Collection;

import jpa.FullDeTreballJPA;
import jpa.PacientJPA;

public interface FullTreballNegociRemote {
	public Collection<PacientJPA> llistarFulls(String cif);
}
