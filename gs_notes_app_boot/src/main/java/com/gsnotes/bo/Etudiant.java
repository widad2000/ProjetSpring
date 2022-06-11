package com.gsnotes.bo;


import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;


/**
 * Represente un Etudiant.
 * 
 * Un enseignant est un cas spéciale de l'Etudiant
 * 
 * @author T. BOUDAA
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "idEtudiant")
public class Etudiant extends Utilisateur {

	

	public Etudiant(Long idUtilisateur, String nom, String prenom, String cin, String email, String telephone,
			String nomArabe, String prenomArabe, String photo, Set<Compte> comptes, String cne, Date dateNaissance,
			List<InscriptionAnnuelle> inscriptions) {
		super(idUtilisateur, nom, prenom, cin, email, telephone, nomArabe, prenomArabe, photo, comptes);
		this.cne = cne;
		this.dateNaissance = dateNaissance;
		this.inscriptions = inscriptions;
	}

	public Etudiant() {
		super();	}

	private String cne;

	private Date dateNaissance;

	@OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL , targetEntity = InscriptionAnnuelle.class)
	private List<InscriptionAnnuelle> inscriptions;


	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(java.util.Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public List<InscriptionAnnuelle> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<InscriptionAnnuelle> inscriptions) {
		this.inscriptions = inscriptions;
	}

//	@Override
//	public String toString() {
//		return "Etudiant [cne=" + cne + ", dateNaissance=" + dateNaissance + ", inscriptions=" + inscriptions + "]";
//	}


	

}