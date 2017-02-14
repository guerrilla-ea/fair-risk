package com.redhat.ea.fair.service;

public class Thing {
	
	private String name;
	private int age;
	private String relationship;
	
	public Thing(String name, int age, String relationship){
		this.name = name;
		this.age = age;
		this.relationship = relationship;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	

}
