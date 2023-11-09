package com.algatek.crudservice.entity;

import java.io.Serializable;

public interface IEntity extends Serializable {
	public Long getId();

	public void setId(Long id);
}
