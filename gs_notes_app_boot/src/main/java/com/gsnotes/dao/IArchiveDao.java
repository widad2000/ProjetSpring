package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Archive;

public interface IArchiveDao extends JpaRepository<Archive, Long> {

}
