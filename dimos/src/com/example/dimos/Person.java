package com.example.dimos;

import java.util.Date;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class Person {
    private int id = -1;
    private String firstname = "";
    private String customer = "";
    private String projecttype = "";
    private Date startdate = null;
    private Date enddate = null;
    private Date nextdeadline = null;
    private String active = "";
    private String budget = "";
    private String description="";
    private String insertedby="";
    private Date insertedat=null;
    private String modifiedby="";
    private Date modifiedat=null;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getProjecttype() {
		return projecttype;
	}
	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Date getNextdeadline() {
		return nextdeadline;
	}
	public void setNextdeadline(Date nextdeadline) {
		this.nextdeadline = nextdeadline;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(String insertedby) {
		this.insertedby = insertedby;
	}
	public Date getInsertedat() {
		return insertedat;
	}
	public void setInsertedat(Date insertedat) {
		this.insertedat = insertedat;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public Date getModifiedat() {
		return modifiedat;
	}
	public void setModifiedat(Date modifiedat) {
		this.modifiedat = modifiedat;
	}
	
    
}