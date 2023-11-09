package com.algatek.crudservice.service.impl;


import com.algatek.crudservice.entity.IDTO;
import com.algatek.crudservice.entity.IEntity;
import com.algatek.crudservice.exception.CustomException;
import com.algatek.crudservice.service.IAbstractService;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractServiceImpl<Entity extends IEntity, BasicDTO extends IDTO, FullDTO extends BasicDTO, IEntityDAO extends JpaRepository<Entity, Long>> implements IAbstractService<Entity, BasicDTO, FullDTO, IEntityDAO> {

    private final Class<Entity> entityClass;
    private final Class<BasicDTO> basicDTOClass;
    private final Class<FullDTO> fullDTOClass;

    @Getter
    @Resource(name = "model-mapper")
    private ModelMapper mapper;

    public AbstractServiceImpl(Class<Entity> myEntityClass, Class<BasicDTO> basicDTOClass, Class<FullDTO> fullViewClass) {
        this.entityClass = myEntityClass;
        this.basicDTOClass = basicDTOClass;
        this.fullDTOClass = fullViewClass;
    }

    @Override
    public FullDTO create(FullDTO fullObj) throws CustomException {
        Entity entity = this.getMapper().map(fullObj, this.entityClass);
        this.getDAO().save(entity);
        fullObj.setId(entity.getId());
        return fullObj;
    }

    @Override
    public void delete(Long id) throws CustomException, AccessDeniedException {
        try {
            this.getDAO().deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Cet élement pas ");
        }
    }

    @Override
    public List<BasicDTO> findAll() {
        List<Entity> list = this.getDAO().findAll();
        List<BasicDTO> viewList = new ArrayList<BasicDTO>();
        for (Entity ent : list) {
            BasicDTO view = this.getMapper().map(ent, this.basicDTOClass);
            viewList.add(view);
        }
        return viewList;
    }

    @Override
    public FullDTO findById(Long id) throws CustomException {
        Entity entity = this.getDAO().findById(id).orElse(null);
        if (entity != null) {
            return this.getMapper().map(entity, this.fullDTOClass);
        } else {
            return null;
        }
    }

    @Override
    public <T extends BasicDTO> T findById(Long id, Class<T> type) throws CustomException {
        Optional<Entity> ent = this.getDAO().findById(id);
        if (ent.isPresent()) {
            return this.getMapper().map(ent, type);
        }
        return null;
    }

    @Override
    public abstract IEntityDAO getDAO();

    @Override
    public boolean ifEntityExistById(Long id) throws CustomException {
        return this.getDAO().existsById(id);
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public FullDTO update(FullDTO fullObj) throws CustomException, AccessDeniedException {
        Entity entity = this.getDAO().findById(fullObj.getId()).orElse(null);
        if (entity != null) {
            entity = this.getMapper().map(fullObj, this.entityClass);
            this.getDAO().saveAndFlush(entity);
        } else {
            throw new CustomException("L'objet à modifier n'existe pas en Base...");
        }
        return fullObj;
    }

}
