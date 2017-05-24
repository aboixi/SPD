/**
 * TFG JEE-SimpleSPD - Component: Pacients
 * @author Albert Boix Isern
 */

package ejb;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import jpa.EmpresaJPA;
import jpa.PacientJPA;

/**
 * EJB Session Bean Class
 */
@Stateless
public class PacientsNegociEJB implements PacientsNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	/**
	 * Mètode que consulta els pacients vinculats a una empresa
	 * @param cif de l'empresa
	 * @return col·lecció de pacients
	 */
	public Collection<PacientJPA> llistarPacients (String cif){
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
	    return pacients;
	}
	
	/**
	 * Mètode que elimina un pacient
	 * @param cip del pacient i cif de l'empresa
	 * @return una col·lecció amb els pacients que queden després de la eliminació
	 */
	public Collection<PacientJPA> eliminarPacient(String cip, String cif){
		EmpresaJPA empresa=entman.find(EmpresaJPA.class, cif);
		PacientJPA pacient = entman.find(PacientJPA.class, cip);
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
		if (empresa.getTipus().equals("Residencia")&& pacient.getResidencia().equals(empresa.getCif())){
			Iterator<PacientJPA> iter = pacients.iterator();
			boolean trobat = false;
			while (iter.hasNext()&&!(trobat)){
				pacient = iter.next();
				if(pacient.getCip().equals(cip)){
					entman.remove(pacient);
					pacients.remove(pacient);
					trobat=true;
				}
			}
			return pacients;
			
		}else if(empresa.getTipus().equals("Farmacia")&& pacient.getResidencia().equals(empresa.getCif())){
			Iterator<PacientJPA> iter = pacients.iterator();
			boolean trobat = false;
			while (iter.hasNext()&&!(trobat)){
				pacient = iter.next();
				if(pacient.getCip().equals(cip)){
					entman.remove(pacient);
					pacients.remove(pacient);
					trobat=true;
				}
			}
			return pacients;
		}
		return pacients;	
	}
	/**
	 * Mètode per modificar les dades personals d'un pacient
	 * @param tots els camps excepte el cip
	 * @return un missatge amb el resultat del procés en forma de String
	 */
	public String modificarPacient(String cif, String cip, String nom, String cognom1, String cognom2, String nomFarmacia, String cifFarmacia, String malalties, 
			String alergies, String metge, boolean autoritzacio, boolean spd, boolean hospitalitzat, boolean exitus)throws PersistenceException{
		PacientJPA pacient = entman.find(PacientJPA.class, cip);
		EmpresaJPA empresa = entman.find(EmpresaJPA.class, cif);
		EmpresaJPA farmacia = entman.find(EmpresaJPA.class, cifFarmacia);
		String nouCif=cifFarmacia;
		if (!(farmacia.getNom().equals(nomFarmacia))){
			try{
				String query = "SELECT f.cif "+"FROM EmpresaJPA f "+"WHERE f.nom = '" + nomFarmacia +"'";
				nouCif = entman.createQuery(query, String.class).getSingleResult();
			}catch (PersistenceException e) {
				System.out.println(e);
			}	
		}
		
		if (empresa.getTipus().equals("Residencia")&& pacient.getResidencia().equals(empresa.getCif())){
			pacient.setNom(nom);
			pacient.setCognom1(cognom1);
			pacient.setCognom2(cognom2);
			pacient.setNomFarmacia(nomFarmacia);
			pacient.setFarmacia(nouCif);
			pacient.setMalalties(malalties);
			pacient.setAlergies(alergies);
			pacient.setMetge(metge);
			pacient.setAutoritzacio(autoritzacio);
			pacient.setSpd(spd);
			pacient.setHospitalitzat(hospitalitzat);
			pacient.setExitus(exitus);		
			try{
				entman.merge(pacient);
				entman.flush();
				entman.refresh(pacient);
				return "canviCorrecte";
			}catch (PersistenceException e) {
				System.out.println(e);
				return "persistenceException";
			}
		}else if (empresa.getTipus().equals("Farmacia")&& pacient.getResidencia().equals(empresa.getCif())){
			pacient.setNom(nom);
			pacient.setCognom1(cognom1);
			pacient.setCognom2(cognom2);
			pacient.setNomFarmacia(nomFarmacia);
			pacient.setFarmacia(nouCif);
			pacient.setMalalties(malalties);
			pacient.setAlergies(alergies);
			pacient.setMetge(metge);
			pacient.setAutoritzacio(autoritzacio);
			pacient.setSpd(spd);
			pacient.setHospitalitzat(hospitalitzat);
			pacient.setExitus(exitus);		
			try{
				entman.merge(pacient);
				entman.flush();
				entman.refresh(pacient);
				return "canviCorrecte";
			}catch (PersistenceException e) {
				System.out.println(e);
				return "persistenceException";
			}
		}
		return "canviNoGuardat";
	}
	/**
	 * Metode per consultar les farmàcies donades d'alta al sistema
	 * @return una col·lecció de farmàcies
	 */
	public Collection<EmpresaJPA> consultarFarmacies(){
		@SuppressWarnings("unchecked")
		Collection<EmpresaJPA> farmacies = entman.createQuery("FROM EmpresaJPA e WHERE e.tipus = 'Farmacia'").getResultList();
		return farmacies;
	}
	/**
	 * Mètode per donar d'alta un pacient
	 * @param les dades del pacient
	 * @return un missatge amb el resultat del procés en forma de String
	 */
	public String crearPacient (String cip, String nom, String cognom1, String cognom2, String malalties,String alergies, String metge, 
			String cifResidencia, String nomResidencia, String cifFarmacia, String nomFarmacia, boolean autoritzacio, boolean spd, boolean hospitalitzat, boolean exitus)
					throws PersistenceException{	
		PacientJPA pacient = null;
		
		try{
			pacient = entman.find(PacientJPA.class, cip);
		}catch (PersistenceException e){
			System.out.println(e);
		}
		
		if (pacient==null){
			pacient = new PacientJPA(cip, nom, cognom1, cognom2, metge, alergies, malalties, spd, autoritzacio, hospitalitzat, cifResidencia, nomResidencia);
			pacient.setFarmacia(cifFarmacia);
			pacient.setNomFarmacia(nomFarmacia);
			entman.persist(pacient);
			return "procesCorrecte";
		}else{
			return "pacientExistent";
		}
	}
	/**
	 * Mètode per consultar una empresa
	 * @param el cif de l'empresa
	 * @return la empresa
	 */
	public EmpresaJPA consultarEmpresa(String cif){
		EmpresaJPA empresa = entman.find(EmpresaJPA.class, cif);
		return empresa;
	}
}