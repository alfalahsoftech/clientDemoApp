package com.alfalahsoftech.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alfalahsoftech.inv.entity.AFMainEntity;

//@Entity
//@Table(name="USER", schema = "myapp")
public class User extends AFMainEntity implements Serializable {

	@Column(updatable=false, insertable=false)
	private Long primaryKey;
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String profession;

	public User(){}

	public User(int id, String name, String profession){
		this.id = id;
		this.name = name;
		this.profession = profession;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Override
	public Long primaryKey() {
		// TODO Auto-generated method stub
		return null;
	}		
}