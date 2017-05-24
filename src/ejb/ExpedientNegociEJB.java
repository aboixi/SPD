/**
 * TFG JEE-SimpleSPD - Component: Expedient Assistencial
 * @author Albert Boix Isern
 */

package ejb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.ExpedientJPA;
import jpa.MedicamentJPA;
import jpa.PacientJPA;
import jpa.TractamentJPA;

/**
 * EJB Session Bean Class 
 */

@Stateless
public class ExpedientNegociEJB implements ExpedientNegociRemote{
	@PersistenceContext(unitName="SPD") 
	private EntityManager entman;
	@Resource
	private SessionContext sessionContext;
	
	/**
	 * Mètode que consulta, donat el cif d'una empresa, els pacients que té vinculats.
	 * Ja sigui com a resident d'una residència o estigui assignat a la farmàcia.
	 * @param cif de l'empresa
	 * @return Una col·lecció amb els pacients.
	 */
	public Collection<PacientJPA> llistarExpedients (String cif){
		@SuppressWarnings("unchecked")
		Collection<PacientJPA> pacients = entman.createQuery("FROM PacientJPA p WHERE p.residencia = '" + cif +"' "+" OR p.farmacia = '" + cif +"'").getResultList();
	    return pacients;
	}
	/**
	 * Mètode que consulta l'expedient donada la seva id
	 * @param id de l'expedient
	 * @return L'expedient 
	 */
	public ExpedientJPA consultarExpedient(int idExpedient){
		ExpedientJPA expedient = entman.find(ExpedientJPA.class, idExpedient);
		return expedient;
	}
	
	/**
	 * Mètode que agrega un tractament a un pacient
	 * Un missatge en forma de String amb el resultat de la operació
	 */
	public String agregarTractament(String idExpedient, String cn,Date dInici, String qEntera, String qFraccio, 
			boolean esmorcar, boolean dinar, boolean sopar, boolean dormir,boolean dill, boolean dima, boolean dime, boolean dijo, boolean dive, boolean diss, boolean dium, boolean foraBlister) throws PersistenceException{
		
		String dataInici = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dInici);
		SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");		
		dataInici=formatData.format(dInici).toString();
		int idExpedientInt = Integer.parseInt(idExpedient);
		ExpedientJPA expedient = entman.find(ExpedientJPA.class, idExpedientInt);
		MedicamentJPA medicament = entman.find(MedicamentJPA.class, cn);
		TractamentJPA tractament = new TractamentJPA();
		
		double quantitatEntera = 0.0;
		double quantitatFraccio = 0.0;
		double quantitatPresa = 0.0;
		double quantitatSetmanal = 0.0;
		int contaDies = 0;
		int contaPreses = 0;
		boolean[] dies = new boolean[7];
		boolean[] preses = new boolean[4];
		
		
		tractament.setDataInici(dataInici);
		tractament.setExpedient(expedient);
		tractament.setMedicament(medicament);
		tractament.setQuantEntera(qEntera);
		tractament.setQuantFraccio(qFraccio);
		tractament.setEsmorcar(esmorcar);
		tractament.setDinar(dinar);
		tractament.setSopar(sopar);
		tractament.setDormir(dormir);
		tractament.setDilluns(dill);
		tractament.setDimarts(dima);
		tractament.setDimecres(dime);
		tractament.setDijous(dijo);
		tractament.setDivendres(dive);
		tractament.setDissabte(diss);
		tractament.setDiumenge(dium);
		tractament.setQuantEntera(qEntera);
		tractament.setQuantFraccio(qFraccio);

		quantitatEntera = Double.parseDouble(qEntera);
		if(qFraccio.equals("0")){
			quantitatFraccio=0.0;
		}else if(qFraccio.equals("1/2")){
			quantitatFraccio=0.5;
			}else if(qFraccio.equals("1/3")){
				quantitatFraccio=0.33;
				}else if(qFraccio.equals("1/4")){
					quantitatFraccio=0.25;
				}
		quantitatPresa = quantitatEntera+quantitatFraccio;
		String qp = Double.toString(quantitatPresa); 
		tractament.setQuantitatPresa(qp);
		
		dies[0]=dill;
		dies[1]=dima;
		dies[2]=dime;
		dies[3]=dijo;
		dies[4]=dive;
		dies[5]=diss;
		dies[6]=dium;
		for(int i=0;i<7;i++){
			if (dies[i]){
				contaDies++;
			}
		}	
		preses[0]=esmorcar;
		preses[1]=dinar;
		preses[2]=sopar;
		preses[3]=dormir;
		
		for(int i=0;i<4;i++){
			if (preses[i]){
				contaPreses++;
			}
		}
		quantitatSetmanal = (contaDies * contaPreses)*(quantitatPresa);
		String qSetmanal = Double.toString(quantitatSetmanal); 
		tractament.setQuantitatSetmanal(qSetmanal);
		tractament.setForaBlister(foraBlister);
		try{
			entman.persist(tractament);
			return "procesCorrecte";
		}catch (PersistenceException e) {
			System.out.println(e);
			return "persistenceException";		
		}
	}
	/**
	 * Mètode que elimina el tractament d'un pacient donada la seva id
	 * @param La id del tractament
	 */
	public void eliminarTractament(int idTractament)throws PersistenceException{
		try{
			TractamentJPA tractament = entman.find(TractamentJPA.class, idTractament);
			tractament.setExpedient(null);
			entman.remove(tractament);
		}catch (PersistenceException e){
			System.out.println(e);
		}
	}
	
	/**
	 * Mètode que busca a la base de dades els medicaments
	 * @param La paraula clau per realitzar la recerca
	 * @return Una col·lecció amb els medicaments trobats.
	 */
	@SuppressWarnings("unchecked")
	public Collection<MedicamentJPA> buscarMedicaments (String paraula){
		String paraulaMajuscula = paraula.toUpperCase();
		char primeraLletra = paraulaMajuscula.charAt(0);
		String segonaPart = paraula.substring(1,paraula.length());
		String primeraPart = String.valueOf(primeraLletra);
		String paraulaBuscar = primeraPart.concat(segonaPart);
		Collection<MedicamentJPA> medicaments = null;
		medicaments = entman.createQuery("FROM MedicamentJPA m WHERE m.nomComercial LIKE '%" + paraula +"%' OR m.nomComercial LIKE'%" + paraulaBuscar +"%' ").getResultList();
		return medicaments;
	}
}