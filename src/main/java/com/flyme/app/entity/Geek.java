package com.flyme.app.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Geek extends Person{
    private String pragramLanage;
    // private List<Project> projects = new ArrayList<Project>();

    @Column(name="prog_lang")
    public String getPragramLanage() {
        return pragramLanage;
    }

    public void setPragramLanage(String pragramLanage) {
        this.pragramLanage = pragramLanage;
    }

    @Override
    public String toString() {
        return "Geek [pragramLanage=" + pragramLanage + "]";
    }

    
}