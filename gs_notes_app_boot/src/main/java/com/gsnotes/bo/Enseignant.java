package com.gsnotes.bo;



import java.util.Set;

import javax.persistence.Entity;

import javax.persistence.PrimaryKeyJoinColumn;


/**
 * Represente un enseignant.
 * 
 * Un enseignant est un cas spéciale de l'Utilisateur
 * 
 * @author T. BOUDAA
 *
 */


@Entity
@PrimaryKeyJoinColumn(name = "idEnseighant")
public class Enseignant extends Utilisateur {


	
	public Enseignant(Long idUtilisateur, String nom, String prenom, String cin, String email, String telephone,
			String nomArabe, String prenomArabe, String photo, Set<Compte> comptes) {
		super(idUtilisateur, nom, prenom, cin, email, telephone, nomArabe, prenomArabe, photo, comptes);
		// TODO Auto-generated constructor stub
	}

	public Enseignant() {
		super();
	}

	private String specialite;


	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}





}