package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity // 注解为hibernate实体
@Table(name="items") // 注解对应的表名
public class Items {

	@Id	// 注解主键
	@GeneratedValue //id生成策略  默认auto 相当于hibernate的native - 自增字段
	private int id;
	private int price;
	private int amount;

	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	private Goods good;

	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	private Orders order;
	
	@Transient
	private float total;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = (float)Math.round(total*100)/100;
	}
	public Goods getGood() {
		return good;
	}
	public void setGood(Goods good) {
		this.good = good;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	
	
}
