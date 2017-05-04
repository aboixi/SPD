package ejb;

import java.util.Collection;

import jpa.EmpresaJPA;
import jpa.PacientJPA;

public interface PacientsNegociRemote {
	public Collection<PacientJPA> llistarPacients (String cif);
	public Collection<PacientJPA> eliminarPacient(String cip, String cif);
	public String modificarPacient(String cif, String cip, String nom, String cognom1, String cognom2, String nomFarmacia, String farmacia, String malalties,String alergies, String metge, boolean autoritzacio, boolean spd, boolean hospitalitzat, boolean exitus);
	public Collection<EmpresaJPA> consultarFarmacies();
	public String crearPacient (String cip, String nom, String cognom1, String cognom2, String malalties,String alergies, String metge, String cifResidencia, String nomResidencia, String cifFarmacia, String nomFarmacia, boolean autoritzacio, boolean spd, boolean hospitalitzat, boolean exitus);
	public EmpresaJPA consultarEmpresa(String cif);
}
