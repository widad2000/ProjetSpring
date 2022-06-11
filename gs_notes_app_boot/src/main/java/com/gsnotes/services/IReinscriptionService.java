package com.gsnotes.services;


import java.util.Map;


import org.springframework.ui.Model;

import com.gsnotes.web.models.Inscription;

public interface IReinscriptionService {
	public String ReinscriptionEtudiant(Inscription inscription,Model model);
	public void MAJEtudiant(Map<String, String> allParams);
	
}
