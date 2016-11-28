package com.wdl.entity.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wdl.base.entity.BaseEntity;


/**
 * @author bin
 *
 */
@Entity
@Table(name = "tb_demo")
public class DemoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
