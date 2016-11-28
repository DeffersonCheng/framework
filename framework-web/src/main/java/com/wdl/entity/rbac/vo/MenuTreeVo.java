package com.wdl.entity.rbac.vo;

import java.util.List;

/**
 * @author bin
 */
public class MenuTreeVo {
	private String id;
	private String text;
	private String url;
	private String code;
	private String levelCode;
	private Long pid;
	private Long roleMenuId;
	private int version;
	private List<MenuTreeVo> children;
	/**
	 * 是否选中（前台显示）
	 */
	private boolean checked;
	/**
	 * 数据库是否选中
	 */
	private boolean dataChecked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public List<MenuTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTreeVo> children) {
		this.children = children;
	}

	public boolean isDataChecked() {
		return dataChecked;
	}

	public void setDataChecked(boolean dataChecked) {
		this.dataChecked = dataChecked;
	}

	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

}
