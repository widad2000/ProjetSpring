package com.gsnotes.web.models;

public class Inscription {
	private long idEtudiant;
	private String CNE;
	private String Nom;
	private String Prenom;
	private String type;
	private long idNiveau;
	public long getIdEtudiant() {
		return idEtudiant;
	}
	public void setIdEtudiant(long idEtudiant) {
		this.idEtudiant = idEtudiant;
	}
	public String getCNE() {
		return CNE;
	}
	public void setCNE(String cNE) {
		CNE = cNE;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getIdNiveau() {
		return idNiveau;
	}
	public void setIdNiveau(long idNiveau) {
		this.idNiveau = idNiveau;
	}
	public Inscription(long idEtudiant, String cNE, String nom, String prenom, String type, long idNiveau) {
		super();
		this.idEtudiant = idEtudiant;
		this.CNE = cNE;
		this.Nom = nom;
		this.Prenom = prenom;
		this.type = type;
		this.idNiveau = idNiveau;
	}
	public Inscription() {
	}
	@Override
	public String toString() {
		return "Inscription [idEtudiant=" + idEtudiant + ", CNE=" + CNE + ", Nom=" + Nom + ", Prenom=" + Prenom
				+ ", type=" + type + ", idNiveau=" + idNiveau + "]";
	}
	
	
}
