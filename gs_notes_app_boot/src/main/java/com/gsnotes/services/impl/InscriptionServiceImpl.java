package com.gsnotes.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionMatiere;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Module;
import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.Utilisateur; 
import com.gsnotes.dao.IElementDao;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.dao.IInscriptionAnnuelleDao;
import com.gsnotes.dao.IInscriptionMatiereDao;
import com.gsnotes.dao.IInscriptionModuleDao;
import com.gsnotes.dao.IModuleDao;
import com.gsnotes.dao.INiveauDao;
import com.gsnotes.dao.IUtilisateurDao;
import com.gsnotes.exceptionhandlers.InscriptionException;
import com.gsnotes.services.IInscriptionService;
import com.gsnotes.web.models.Inscription;



@Service
@Transactional
public class InscriptionServiceImpl implements IInscriptionService {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private IEtudiantDao etudiantdao;
	@Autowired
	private IUtilisateurDao utilisateurdao;
	@Autowired
	private INiveauDao niveaudao;
	@Autowired
	private IInscriptionAnnuelleDao inscriptionannuelledao;
	@Autowired
	private IModuleDao moduledao;
	@Autowired
	private IInscriptionModuleDao inscriptionmoduledao;
	@Autowired
	private IElementDao matieredao;
	@Autowired 
	private IInscriptionMatiereDao inscriptionmatieredao;
	
	@Override
	public String InscriptionEtudiant( Inscription inscription)  {
	if(session.getAttribute("messageInscription")!=null)	session.removeAttribute("messageInscription");
		//Recherche si l'etudiant existe dans la base de données
		boolean bool = etudiantdao.existsById(inscription.getIdEtudiant());
		//si oui redirection a la page qui affiche l'erreur
if(bool) {
	Etudiant etd =		etudiantdao.getById(inscription.getIdEtudiant());
	System.out.println(etd); 
	session.setAttribute("messageInscription", "L'id "+ inscription.getIdEtudiant() +"est deja attribué a un autre etudiant , sinon l'etudiant existe deja");
	return null;
		}
	//Sinon on commence l'insertion 
else {
	//inserer l'etudiant dans la base de données
	Etudiant etudiant = new Etudiant(inscription.getIdEtudiant(),inscription.getNom(),inscription.getPrenom(),null,null,null,null,null,null,null,inscription.getCNE(),null,null);
	
	etudiant=etudiantdao.save(etudiant);
	System.out.println("etudiant insere :"+etudiant);
	// verifier que le niveau existe dans la base de données
	bool=niveaudao.existsById(inscription.getIdNiveau());
	//si oui creer l'inscription annuelle, l'inscription au module et au elements
		if(bool) {
					Niveau niveau= niveaudao.getById(inscription.getIdNiveau());
				//	System.out.println("niveau est :"+niveau);
					//Inserer a Inscription annuelle
					InscriptionAnnuelle inscriptionannuelle = new InscriptionAnnuelle(2020,1,inscription.getType(),0,null,null,null,niveau,etudiant) ;
					inscriptionannuelle= inscriptionannuelledao.save(inscriptionannuelle);
					
					//recherche des modules qui appartiennent au niveau
						List<Module> list = moduledao.findAllByNiveau(niveau);
					
						Iterator<Module> it =list.iterator();
					// Creer une inscriptionmodule pour chaque module
						while(it.hasNext()) {
						
												Module module = it.next();
											
												//System.out.println("module est :"+ module); 
												InscriptionModule inscriptionmodule = new InscriptionModule(0,0,0,null,null,module,inscriptionannuelle);
												
												inscriptionmodule=inscriptionmoduledao.save(inscriptionmodule);
												
												// recherche des element qui appartiennent au module
												List<Element> list2=matieredao.findAllByModule(module);
												
												Iterator<Element> it2 =list2.iterator();
												// Creer une inscriptionmodule pour chaque element
												while(it2.hasNext()) {
													
																		Element matiere = it2.next();
																	
																		InscriptionMatiere inscriptionmatiere=new InscriptionMatiere(0,0,0,null,null,1,matiere,inscriptionannuelle);
																	
																		inscriptionmatiere = inscriptionmatieredao.save(inscriptionmatiere);
													
												}
												
											}
	 
				}
		// si le niveau n'existe pas , afficher l'erreur
		else {
		  session.setAttribute("messageInscription", "L'id "+ inscription.getIdNiveau() +"n'est attribué a aucun niveau");
		 return null;
			}                                                             
	
	
}
return null;
		
	}

}
