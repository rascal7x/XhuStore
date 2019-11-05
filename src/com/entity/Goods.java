package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;  //生成值
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity  // entity层的作用就是与数据库建立起一个映射关系，以此来通过entity层获取数据库的数据。注解为hibernate实体,@Entity说明这个class是实体类，并且使用默认的orm规则，即class名即数据库表中表名，class字段名即表中的字段名,如果想改变这种默认的orm规则，就要使用@Table来改变class名与数据库中表名的映射规则，@Column来改变class中字段名与db中表的字段名的映射规则
@Table(name="goods") // 注解对应的表名
public class Goods {

	@Id	// 注解主键
	@GeneratedValue //id生成策略  默认auto 相当于hibernate的native - 自增字段
	private int id;
	
	private String name;
	private String cover;
	private String image1;
	private String image2;
	private int price;
	private String intro;
	private int stock;

	@ManyToOne  // 因为多个商品可能对应的是一个商品种类，所以是单向多对一的关系
	@NotFound(action=NotFoundAction.IGNORE)  //如果属性值为某某的数据在数据库不存在了，hibernate默认会抛出异常。解决此问题，加上此注解即可
	private Types type;


	// 首页推荐标记
	@Transient // 不持久化
	private boolean topScroll; // 条幅推荐
	@Transient // 不持久化 
	private boolean topLarge; // 大图推荐
	@Transient // 不持久化
	private boolean topSmall; // 小图推荐

	

	// 获取商品id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	// 获取商品名称
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// 封面图 cover 
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	
	// 图片一 image1
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	
	// 图片二 image2
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}

	// 获取商品价格
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	// 获取商品信息，针对商品的描述
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}

	// 获取特定商品的库存量
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	// 获取商品所属的种类(5个种类)
	public Types getType() {
		return type;
	}
	public void setType(Types type) {
		this.type = type;
	}
	
	
	
	// 判断首页推荐标记
	public boolean isTopScroll() {
		return topScroll;
	}
	public void setTopScroll(boolean topScroll) {
		this.topScroll = topScroll;
	}
	public boolean isTopLarge() {
		return topLarge;
	}
	public void setTopLarge(boolean topLarge) {
		this.topLarge = topLarge;
	}
	public boolean isTopSmall() {
		return topSmall;
	}
	public void setTopSmall(boolean topSmall) {
		this.topSmall = topSmall;
	}
}
