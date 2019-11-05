package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity // 注解为hibernate实体
@Table(name="users") // 注解对应的表名
public class Users {
	
	@Id	// 注解主键
	@GeneratedValue // id生成策略  默认auto 相当于hibernate的native - 自增字段
	private int id;
	private String username;
	private String password;
	private String name;
	private String phone;
	private String address;
	@Transient
	private String passwordNew;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPasswordNew() {
		return passwordNew;
	}
	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
