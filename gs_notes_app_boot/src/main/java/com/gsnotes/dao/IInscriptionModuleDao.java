package com.gsnotes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionModule;



public interface IInscriptionModuleDao extends JpaRepository<InscriptionModule, Long> {
	public List<InscriptionModule> findByInscriptionAnnuelle(InscriptionAnnuelle IA);

}
