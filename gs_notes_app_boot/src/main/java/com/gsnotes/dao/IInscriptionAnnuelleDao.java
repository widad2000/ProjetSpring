package com.gsnotes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;


public interface IInscriptionAnnuelleDao extends JpaRepository<InscriptionAnnuelle, Long> {
	public List<InscriptionAnnuelle> findAllByEtudiant(Etudiant etudiant);
}