package com.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity // 注解为hibernate实体
@Table(name="orders") // 注解对应的表名
public class Orders {
	
	/** 订单状态 - 未付款 */
	public static final byte STATUS_UNPAY = 1;
	
	/** 订单状态 - 已付款 */
	public static final byte STATUS_PAYED = 2;
	
	/** 订单状态 - 配送中 */
	public static final byte STATUS_SEND = 3;
	
	/** 订单状态 - 已完成 */
	public static final byte STATUS_FINISH = 4;
	
	
	
	/** 支付方式 - 微信 */
	public static final byte PAYTYPE_WECHAT = 1;
	/** 支付方式 - 支付宝 */
	public static final byte PAYTYPE_ALIPAY = 2;
	/** 支付方式 - 线下 */
	public static final byte PAYTYPE_OFFLINE = 3;
	
	
	
	@Id	// 注解主键
	@GeneratedValue //id生成策略  默认auto 相当于hibernate的native - 自增字段
	private int id;
	
	private int total;
	private int amount;
	private byte status;
	private byte paytype;
	private String name;
	private String phone;
	private String address;
	private Date systime;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)  // 使用hibernate 注解配置实体类的关联关系，在many-to-one,one-to-one关联中，一边引用自另一边的属性，如果属性值为某某的数据在数据库不存在了，hibernate默认会抛出异常。解决此问题，加上如下注解就可以了： 
	private Users user;							//意思是找不到引用的外键数据时忽略
	
	
	@OneToMany
	@Transient // 不持久化
	private List<Items> itemList;

	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Date getSystime() {
		return systime;
	}
	public void setSystime(Date systime) {
		this.systime = systime;
	}
	
	
	
	
	public List<Items> getItemList() {
		return itemList;
	}
	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}
	
	
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public byte getPaytype() {
		return paytype;
	}
	public void setPaytype(byte paytype) {
		this.paytype = paytype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
}
