package com.algatek.crudservice.service;

import com.algatek.crudservice.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IAbstractService<Entity, BasicDTO, FullDTO extends BasicDTO, IEntityDAO extends JpaRepository<Entity, Long>> {

    FullDTO create(FullDTO obj) throws CustomException;

    void delete(Long id) throws CustomException, AccessDeniedException;

    List<BasicDTO> findAll();

    FullDTO findById(Long id) throws CustomException;

    <T extends BasicDTO> T findById(Long id, Class<T> type) throws CustomException;

    IEntityDAO getDAO();

    FullDTO update(FullDTO fullObj) throws CustomException, AccessDeniedException;

    boolean ifEntityExistById(Long id) throws CustomException;
}
