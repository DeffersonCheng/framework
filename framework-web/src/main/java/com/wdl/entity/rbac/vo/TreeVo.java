package com.wdl.entity.rbac.vo;

import java.util.List;

/**
 * @author bin
 */
public class TreeVo {
    private String id;

    private String text;

    private String url;

    private String code;

    private Long pid;

    private String type;

    private String deleted;

    private Integer version;

    private String icon;

    private List<TreeVo> children;

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

    public String getType() {
    
        return type;
    }

    
    public void setType(String type) {
    
        this.type = type;
    }

    
    public String getDeleted() {
    
        return deleted;
    }

    
    public void setDeleted(String deleted) {
    
        this.deleted = deleted;
    }

    
    public Integer getVersion() {
    
        return version;
    }

    
    public void setVersion(Integer version) {
    
        this.version = version;
    }

    
    public String getIcon() {
    
        return icon;
    }

    
    public void setIcon(String icon) {
    
        this.icon = icon;
    }

    
    public List<TreeVo> getChildren() {
    
        return children;
    }

    
    public void setChildren(List<TreeVo> children) {
    
        this.children = children;
    }

    
    
    public Long getPid() {
    
        return pid;
    }

    public void setPid(Long pid) {
    
        this.pid = pid;
    }

}
