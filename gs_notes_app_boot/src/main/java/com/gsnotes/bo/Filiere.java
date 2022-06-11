package com.gsnotes.bo;


import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 * Represente une filière.
 * 
 * 
 * @author T. BOUDAA
 *
 */

@Entity
public class Filiere {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFiliere;

	private String titreFiliere;

	private String codeFiliere;

	private int anneeaccreditation;

	private int anneeFinaccreditation;

	@OneToMany(mappedBy = "filiere" ,  cascade = CascadeType.ALL, targetEntity = Niveau.class)
	private Set<Niveau> niveaux;
	public Filiere() {
		
	}

	public Filiere(Long idFiliere, String titreFiliere, String codeFiliere, int anneeaccreditation,
			int anneeFinaccreditation, Set<Niveau> niveaux) {
		super();
		this.idFiliere = idFiliere;
		this.titreFiliere = titreFiliere;
		this.codeFiliere = codeFiliere;
		this.anneeaccreditation = anneeaccreditation;
		this.anneeFinaccreditation = anneeFinaccreditation;
		this.niveaux = niveaux;
	}

	@Override
	public String toString() {
		return "Filiere [idFiliere=" + idFiliere + ", titreFiliere=" + titreFiliere + ", codeFiliere=" + codeFiliere
				+ ", anneeaccreditation=" + anneeaccreditation + ", anneeFinaccreditation=" + anneeFinaccreditation
				+ ", niveaux=" + niveaux + "]";
	}

	public Long getIdFiliere() {
		return idFiliere;
	}

	public void setIdFiliere(Long idFiliere) {
		this.idFiliere = idFiliere;
	}

	public String getTitreFiliere() {
		return titreFiliere;
	}

	public void setTitreFiliere(String titreFiliere) {
		this.titreFiliere = titreFiliere;
	}

	public String getCodeFiliere() {
		return codeFiliere;
	}

	public void setCodeFiliere(String codeFiliere) {
		this.codeFiliere = codeFiliere;
	}

	public int getAnneeaccreditation() {
		return anneeaccreditation;
	}

	public void setAnneeaccreditation(int anneeaccreditation) {
		this.anneeaccreditation = anneeaccreditation;
	}

	public int getAnneeFinaccreditation() {
		return anneeFinaccreditation;
	}

	public void setAnneeFinaccreditation(int anneeFinaccreditation) {
		this.anneeFinaccreditation = anneeFinaccreditation;
	}

	public Set<Niveau> getNiveaux() {
		return niveaux;
	}

	public void setNiveaux(Set<Niveau> niveaux) {
		this.niveaux = niveaux;
	}

	
	
}