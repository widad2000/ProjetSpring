package com.gsnotes.web.controllers;

import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gsnotes.services.IReinscriptionService;

@Controller
@RequestMapping("/UpdateStudent")
public class UpdateController {
	@Autowired
	private IReinscriptionService ReinscriptionService;


@RequestMapping("/maj")
	public String UpdateStudent( @RequestParam Map<String, String> allParams) {
		System.out.println("avant");

		ReinscriptionService.MAJEtudiant(allParams );
		System.out.println("apres");
		return "/succees";
	}
	

}
