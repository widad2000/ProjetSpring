package com.gsnotes.web.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.boudaa.tools.ExcelImporter;
import com.gsnotes.exceptionhandlers.InscriptionException;
import com.gsnotes.services.IInscriptionService;
import com.gsnotes.services.IReinscriptionService;
import com.gsnotes.web.models.Inscription;

@Controller
@RequestMapping("/Inscription")
public class InscriptionController {   
	
	
	@Autowired
	private HttpSession session;
	@Autowired
	private IInscriptionService InscriptionService;
	@Autowired
	private IReinscriptionService ReinscriptionService;
	private List<Inscription> listInscription=new ArrayList<>();
	
	@PostMapping("/excelimport")
	//@ResponseBody
	public String ImportEXCEL( @RequestParam("file") MultipartFile file) throws IOException{
	
		 ExcelImporter ex= new ExcelImporter();
		
		listInscription=ex.excelImport(session,file.getInputStream());
//		Iterator<Inscription> iterator = listInscription.iterator();
//	
//		
//		  if(iterator.hasNext()) {
//			  ins=iterator.next();
//		  
//		  System.out.println(ins.getNom()); }
		 
		// la session contient un message d'erreur de l'import 
		if(session.getAttribute("messageExcelImporter")!=null) {
			System.out.println("message est "+ session.getAttribute("messageExcelImporter"));
			session.setAttribute("message", session.getAttribute("messageExcelImporter"));

			return"erreur";
		}
		// commencer le traitement des inscriptions
		else {
			System.out.println("redirect");
		return "redirect:/Inscription/Studentloading";
		}
	}
	@RequestMapping("/Studentloading")
	//@ResponseBody
	public 	String Studentloading(Model model) throws InscriptionException {
		System.out.println("studentLoading");
		Inscription ins =new Inscription();
	
		Iterator<Inscription> iterator = listInscription.iterator();
		
		while(iterator.hasNext()) 
		{ ins=iterator.next();
		// s'il s'agit d'une inscription appeler InscriptionService
		if(ins.getType().equals("inscription")) {
			System.out.println("inscription controleur");
//			System.out.println(ins.getType());
	InscriptionService.InscriptionEtudiant(ins);	
		if(session.getAttribute("messageInscription")!=null) {
			System.out.println("message est "+ session.getAttribute("messageInscription"));
			session.setAttribute("message", session.getAttribute("messageInscription"));
			return"/erreur";
		}
			}
		//sinon appeler ReinscriptionService
		else {
			System.out.println("reinscription controleur");
			System.out.println(ins.getType());
			ReinscriptionService.ReinscriptionEtudiant(ins,model);
			System.out.println(ins.toString());
			if(session.getAttribute("messageReinscription")!=null) {
				System.out.println("message est "+ session.getAttribute("messageReinscription"));
				session.setAttribute("message", session.getAttribute("messageReinscription"));

				return"/erreur";
			}
			
		}
		}
		if(model.getAttribute("listmap")!= null) {
			return "/update";
		}
		else {
			return"succees";
		}
		
	}
	
	
	
	
}
