package com.dream.domain;

import com.dream.utils.DateUtils;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

public class Message {
    @Id
    private Long id;
    private String type;
    private Long uid;
    private Long rid;
    private Date date;
    private String see;
    private Long cid;
    @Transient
    private User user;
    @Transient
    private UserContent userContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSee() {
        return see;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserContent getUserContent() {
        return userContent;
    }

    public void setUserContent(UserContent userContent) {
        this.userContent = userContent;
    }
    @Transient
    public String getFormatDate(){
        return DateUtils.formatDate(getDate(),"yyyy-MM-dd HH:mm:ss");
    }
}
