package com.darinpope;

import java.io.Serializable;
import java.util.Date;

public class Business implements Serializable {
    private Integer id;
    private String companyName;
    private String tag;
    private Date createDate;
    private Boolean active;

    public Business(Integer id, String companyName, String tag, Date createDate, Boolean active) {
        this.id = id;
        this.companyName = companyName;
        this.tag = tag;
        this.createDate = createDate;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

