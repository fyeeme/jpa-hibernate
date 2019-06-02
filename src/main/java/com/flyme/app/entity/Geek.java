package com.flyme.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Geek extends Person {
    private String pragramLanage;

    private List<Project> projects = new ArrayList<>();

    @Column(name = "prog_lang")
    public String getPragramLanage() {
        return pragramLanage;
    }

    public void setPragramLanage(String pragramLanage) {
        this.pragramLanage = pragramLanage;
    }


    @ManyToMany(targetEntity = Project.class, cascade = { CascadeType.REFRESH })
    @JoinTable(name = "geek_project", 
    joinColumns = {
            @JoinColumn(name = "geek_id", referencedColumnName = "id") }, 
    inverseJoinColumns = {
            @JoinColumn(name = "project_id", referencedColumnName = "id") })
    public List<Project> getProjects() {
        return projects;
    }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }




}