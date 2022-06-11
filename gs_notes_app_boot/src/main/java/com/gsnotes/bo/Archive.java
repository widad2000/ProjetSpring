package com.gsnotes.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Archive {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMiseAjour;
	private String OldName;
	private String OldLastName;
	private String OldCNE;	
	private String NewName;
	private String NewLastName;
	private String NewCNE;

	@ManyToOne
	@JoinColumn(name = "idEtudiant")
	private Etudiant etudiant;

	public Archive( String oldName, String oldLastName, String oldCNE, String newName,
			String newLastName, String newCNE, Etudiant etudiant) {
		super();
		
		OldName = oldName;
		OldLastName = oldLastName;
		OldCNE = oldCNE;
		NewName = newName;
		NewLastName = newLastName;
		NewCNE = newCNE;
		this.etudiant = etudiant;
	}

	public Archive() {
		super();
	}

	public Long getIdMiseAjour() {
		return idMiseAjour;
	}

	public void setIdMiseAjour(Long idMiseAjour) {
		this.idMiseAjour = idMiseAjour;
	}

	public String getOldName() {
		return OldName;
	}

	public void setOldName(String oldName) {
		OldName = oldName;
	}

	public String getOldLastName() {
		return OldLastName;
	}

	public void setOldLastName(String oldLastName) {
		OldLastName = oldLastName;
	}

	public String getOldCNE() {
		return OldCNE;
	}

	public void setOldCNE(String oldCNE) {
		OldCNE = oldCNE;
	}

	public String getNewName() {
		return NewName;
	}

	public void setNewName(String newName) {
		NewName = newName;
	}

	public String getNewLastName() {
		return NewLastName;
	}

	public void setNewLastName(String newLastName) {
		NewLastName = newLastName;
	}

	public String getNewCNE() {
		return NewCNE;
	}

	public void setNewCNE(String newCNE) {
		NewCNE = newCNE;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	@Override
	public String toString() {
		return "Archive [idMiseAjour=" + idMiseAjour + ", OldName=" + OldName + ", OldLastName=" + OldLastName
				+ ", OldCNE=" + OldCNE + ", NewName=" + NewName + ", NewLastName=" + NewLastName + ", NewCNE=" + NewCNE
				+ ", etudiant=" + etudiant + "]";
	}
	
	
}
