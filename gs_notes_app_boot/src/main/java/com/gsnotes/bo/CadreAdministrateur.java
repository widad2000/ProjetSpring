package com.gsnotes.bo;

import java.util.Set;

import javax.persistence.Entity;

import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Represente un cadre qui travaille à l'administration
 * 
 * @author T. BOUDAA
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "idCardreAdmin")
public class CadreAdministrateur extends Utilisateur {

	public CadreAdministrateur(Long idUtilisateur, String nom, String prenom, String cin, String email,
			String telephone, String nomArabe, String prenomArabe, String photo, Set<Compte> comptes) {
		super(idUtilisateur, nom, prenom, cin, email, telephone, nomArabe, prenomArabe, photo, comptes);
		// TODO Auto-generated constructor stub
	}

	public CadreAdministrateur() {
		// TODO Auto-generated constructor stub
	}

	private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}