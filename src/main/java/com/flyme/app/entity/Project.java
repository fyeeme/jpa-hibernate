package com.flyme.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "projects")
    private List<Geek> geeks = new ArrayList<>();

    @Embedded
    private Period projectPeriod;

    @ElementCollection
    @CollectionTable(
        name="billing_period",
        joinColumns =@JoinColumn(name="project_id")
    )
    private List<Period> projectPeriods = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    // @Enumerated(EnumType.STRING)
    private ProjectType projectType;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Geek> getGeeks() {
        return geeks;
    }

    public void setGeeks(List<Geek> geeks) {
        this.geeks = geeks;
    }

    public Period getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(Period projectPeriod) {
        this.projectPeriod = projectPeriod;
    }
    

    @Override
    public String toString() {
        return "Project [geeks=" + geeks + ", id=" + id + ", name=" + name + ", projectPeriod=" + projectPeriod + "]";
    }

    public List<Period> getProjectPeriods() {
        return projectPeriods;
    }

    public void setProjectPeriods(List<Period> projectPeriods) {
        this.projectPeriods = projectPeriods;
    }
    
    public enum ProjectType{
        FIXED, TIME_AND_MATERIAL
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
    
}