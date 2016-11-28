package com.wdl.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

/**
 * 菜单表
 * 
 * @author bin
 * 
 */
@Entity
@Table(name = "t_menu")
public class MenuEntity extends com.wdl.base.entity.BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 菜单链接，如果是作为模块，不应该有链接
     */
    @Column(name = "url")
    private String url;

    /**
     * 层级编码，每6位代表一个层级
     */
    @Column(name = "code")
    private String code;

    /**
     * 父节点ID
     */
    @Column(name = "pid")
    private Long pid;

    /**
     * 功能类型：0菜单、1按钮
     */
    @Column(name = "typeCode")
    private String typeCode;

    @Column(name = "icon")
    private String icon;

    /**
     * 是否可用
     */
    @Column(name = "isDel")
    private String isDel;

    @Transient
    private boolean isChack;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public Long getPid() {

        return pid;
    }

    public void setPid(Long pid) {

        this.pid = pid;
    }

    public boolean isChack() {

        return isChack;
    }

    public void setChack(boolean isChack) {

        this.isChack = isChack;
    }

    public String getIsDel() {

        return isDel;
    }

    public void setIsDel(String isDel) {

        this.isDel = isDel;
    }

    
    public String getTypeCode() {
    
        return typeCode;
    }

    
    public void setTypeCode(String typeCode) {
    
        this.typeCode = typeCode;
    }

    
    public String getIcon() {
    
        return icon;
    }

    
    public void setIcon(String icon) {
    
        this.icon = icon;
    }

}
