package com.gsnotes.services.impl;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gsnotes.bo.Archive;
import com.gsnotes.bo.Element;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionMatiere;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Module;
import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.Utilisateur;
import com.gsnotes.dao.IArchiveDao;
import com.gsnotes.dao.IElementDao;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.dao.IFiliereDao;
import com.gsnotes.dao.IInscriptionAnnuelleDao;
import com.gsnotes.dao.IInscriptionMatiereDao;
import com.gsnotes.dao.IInscriptionModuleDao;
import com.gsnotes.dao.IModuleDao;
import com.gsnotes.dao.INiveauDao;
import com.gsnotes.dao.IUtilisateurDao;
import com.gsnotes.services.IReinscriptionService;
import com.gsnotes.web.models.Inscription;


@Service
@Transactional
public class ReinscriptionServiceImpl implements IReinscriptionService {
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
	@Autowired
	private IArchiveDao archivedao;
	@Autowired
	private IFiliereDao filieredao;
	@ModelAttribute
	@Override
	public String ReinscriptionEtudiant(Inscription inscription,Model model) {
		if(session.getAttribute("messageReinscription")!=null)	session.removeAttribute("messageReinscription");

		 HashMap <String,String> map = new HashMap <String,String>();
		List< HashMap <String,String>> listmap = new 	ArrayList< HashMap <String,String>>();
		
		//Recherche si l'etudiant existe dans la base de données

		boolean bool = etudiantdao.existsById(inscription.getIdEtudiant());
		//Si oui on commence l'insertion 
		if(bool) {
			
				Utilisateur user = utilisateurdao.getById(inscription.getIdEtudiant()); 

				Etudiant etd =		etudiantdao.getById(inscription.getIdEtudiant());
				System.out.println("user : "+user); 
				System.out.println("inscription : "+inscription);
				//verifier les informations BD vs fichier
				
				if( !user.getNom().equals(inscription.getNom()) || !user.getPrenom().equals(inscription.getPrenom()) || !etd.getCne().equals(inscription.getCNE())) {
					System.out.println("informations incompatible");
				//	System.out.println(user.toString());
				//	System.out.println(inscription.toString());
					map.put("OldName",user.getPrenom() );
					map.put("NewName",inscription.getPrenom() );
					map.put("OldLastName", user.getNom());
					map.put("NewLastName",inscription.getNom());
					map.put("OldCNE",etd.getCne());
					map.put("NewCNE", inscription.getCNE());
					
					if(model.containsAttribute("listmap")) {
						listmap= (List<HashMap<String, String>>) model.getAttribute("listmap");
						listmap.add(map);
						model.addAttribute("listmap",listmap);
						session.setAttribute("listmap",listmap);
					}
					else {
						
						listmap.add(map);
						
						model.addAttribute("listmap",listmap);
						session.setAttribute("listmap",listmap);
						
					}
				}
				// si les informations sont compatible commencer l'insertion
				else {
					bool=niveaudao.existsById(inscription.getIdNiveau());
					
					if(bool) {
						//chercher les inscriptions precedente de l'etudiant
						etd.setInscriptions(inscriptionannuelledao.findAllByEtudiant(etd));
						List<InscriptionAnnuelle> liste= etd.getInscriptions();
						System.out.println(liste);
						InscriptionAnnuelle insannuelle = new InscriptionAnnuelle();
						InscriptionAnnuelle IA = new InscriptionAnnuelle();
						Iterator<InscriptionAnnuelle> iter =liste.iterator();
						insannuelle = iter.next();

						System.out.println("while");
						//chercher la derniere  inscriptions
							while(iter.hasNext()) {
								IA= iter.next();
								if(insannuelle.getAnnee() <IA.getAnnee())
									insannuelle=IA;
								
							}
							System.out.println("insannuelle:"+insannuelle.getValidation());
						
							if(insannuelle.getValidation().equals("nv")) {
								if(insannuelle.getNiveau().getIdNiveau().equals(inscription.getIdNiveau())) {
			
									Niveau niveaunv = insannuelle.getNiveau();
									System.out.println("test1");
									InscriptionAnnuelle inscriptionajournee= new InscriptionAnnuelle(2021,1,inscription.getType(),0,null,null,null,niveaunv,etd) ;
									inscriptionajournee = inscriptionannuelledao.save(inscriptionajournee);
									//System.out.println("test2"+ niveaunv.toString());
									Niveau niv = niveaudao.getById(niveaunv.getIdNiveau());
							//		System.out.println(niv);
									List<Module> modulenv = moduledao.findAllByNiveau(niveaunv);
									//System.out.println("gfgv"+modulenv.get(0).getElements());

									for(Module nv : modulenv) {
												List<InscriptionModule> inscriptionmodulenv = inscriptionmoduledao.findByInscriptionAnnuelle(insannuelle);
												for(InscriptionModule moduleNV : inscriptionmodulenv) {
													if(moduleNV.getValidation().equals("nv")) {
							
														InscriptionModule newinscription = new InscriptionModule(0,0,0,null,null,nv,inscriptionajournee);
														inscriptionmoduledao.save(newinscription);
														List<Element> elementnv =matieredao.findAllByModule(nv);
														
														for(Element elemnv : elementnv) {

															InscriptionMatiere inscriptionmatiere=new InscriptionMatiere(0,0,0,null,null,1,elemnv,inscriptionajournee);
															inscriptionmatiere = inscriptionmatieredao.save(inscriptionmatiere);
														}
														
													}
													}
									}
						
						}else {
							session.setAttribute("messageReinscription","l'etudiant avec l'id : " + inscription.getIdNiveau() + " n'a pas validée l'année precedente");
								return null;
						}
						
							}
							// verifier que le niveau est correcte
							else if(!insannuelle.getNiveau().getNiveauSuivant().getIdNiveau().equals(inscription.getIdNiveau())) {

								session.setAttribute("messageReinscription","le niveau attribué a l'etudiant avec l'id : "+inscription.getIdEtudiant()+" est contradictoire");
								return null;
								
							}
							// commencer les insertions
								Niveau niveau= niveaudao.getById(inscription.getIdNiveau());
								InscriptionAnnuelle inscriptionannuelle = new InscriptionAnnuelle(2020,1,inscription.getType(),0,null,null,null,niveau,etd) ;
								inscriptionannuelle= inscriptionannuelledao.save(inscriptionannuelle);
								List<Module> list = moduledao.findAllByNiveau(niveau);
								
								Iterator<Module> it =list.iterator();
									while(it.hasNext()) {
															Module module = it.next();
													
															InscriptionModule inscriptionmodule = new InscriptionModule(0,0,0,null,null,module,inscriptionannuelle);
															inscriptionmodule=inscriptionmoduledao.save(inscriptionmodule);
														
															List<Element> list2=matieredao.findAllByModule(module);
															
															Iterator<Element> it2 =list2.iterator();
															while(it2.hasNext()) {
																					Element matiere = it2.next();
																				
																					InscriptionMatiere inscriptionmatiere=new InscriptionMatiere(0,0,0,null,null,1,matiere,inscriptionannuelle);
																			
																				inscriptionmatiere = inscriptionmatieredao.save(inscriptionmatiere);
																
															}
															
														}
				 
				} 
					// is le niveau est incorrecte
					else {
					session.setAttribute("messageReinscription", "L'id : "+ inscription.getIdNiveau() +"n'appartient a aucun niveau");
					return null;
				}
				}}
		// l'etudiant n'existe pas : redirection a la page qui affiche l'erreur

		else {
			Etudiant	 etd =		etudiantdao.getById(inscription.getIdEtudiant());
			System.out.println(etd); 
			session.setAttribute("message", "L'id : "+ inscription.getIdEtudiant() +" n'appartient a aucun etudiant deja inscrit");
		return null;
		}
		//s'il y a des etudiant a mettre a jour
		if(model.containsAttribute("listmap")){
			System.out.println(listmap);
		}
		
	return null;
}

	@Override
	
	public void MAJEtudiant(Map<String, String> allParams) {
		List< HashMap <String,String>> listmap=	(List<HashMap<String, String>>) session.getAttribute("listmap");

		System.out.println("MAJetudiant : ");
		//parcourir la liste des mises a jours
		for(HashMap <String,String> map : listmap)
		{
			
			// verifier si l'utilisateur a choisit de mettre a jour
				if(allParams.containsValue(map.get("OldCNE"))) { 
					System.out.println(map.get("OldCNE"));
					Etudiant etudiant=	etudiantdao.findByCne(map.get("OldCNE"));
					// enregistrer la modification dans la table archive
					System.out.println(map.get("NewName"));
					Archive archive = new Archive(map.get("OldName"),map.get("OldLastName"),map.get("OldCNE"),map.get("NewName"),map.get("NewLastName"),map.get("NewCNE"),etudiant);
					System.out.println(archive);
					archivedao.save(archive);
					// modifier les informations de l'etudiant
					System.out.println("old student"+etudiant);
					etudiant.setCne(map.get("NewCNE"));
					etudiant.setPrenom(map.get("NewName"));
					etudiant.setNom(map.get("NewLastName"));
					System.out.println("new student :"+etudiant);
					etudiant=etudiantdao.save(etudiant);
					System.out.println("saved student :"+etudiant);

					
				}
			}
			
		}

				
	}