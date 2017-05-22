package ejb;

import java.util.Collection;

import javax.persistence.PersistenceException;

import jpa.BlisterJPA;
import jpa.FullDeControlJPA;
import jpa.PacientJPA;

public interface FullControlNegociRemote {
	public void creaFullControl(int idFull, String dniP);
	public Collection<BlisterJPA> consultarBlisters (String cip);
//	public Collection<FullDeControlJPA> consultarFulls(String cip);
	public Collection<PacientJPA> llistarFulls (String cif);
	public String validar(int idFull, String dniV)throws PersistenceException;
}
