package com.wdl.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wdl.base.entity.BaseEntity;

@Entity
@Table(name = "t_org")
public class Org extends BaseEntity {

	private static final long serialVersionUID = -892723165144628561L;

	@Column(name = "name", length = 200)
	private String name;

	@Column(name = "code", length = 20)
	private String code;

	@Column(name = "parentId", length = 20)
	private Long parentId;

	@Column(name = "levelCode", length = 36)
	private String levelCode;

	@Column(name = "remark", length = 300)
	private String remark;

	@Column(name = "deleted", length = 5)
	private Integer deleted;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public String getLevelCode() {

		return levelCode;
	}

	public void setLevelCode(String levelCode) {

		this.levelCode = levelCode;
	}

	public String getRemark() {

		return remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}
