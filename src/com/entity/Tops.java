package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity // 注解为hibernate实体
@Table(name="tops") // 注解对应的表名
public class Tops {
	
	/** 首页推荐类型 - 条幅 */
	public static final byte TYPE_SCROLL = 1;
	/** 首页推荐类型 - 大图 */
	public static final byte TYPE_LARGE = 2;
	/** 首页推荐类型 - 小图 */
	public static final byte TYPE_SMALL = 3;
	
	
	@Id	// 注解主键
	@GeneratedValue //id生成策略  默认auto 相当于hibernate的native - 自增字段
	private int id;
	private byte type;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	private Goods good;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public Goods getGood() {
		return good;
	}
	public void setGood(Goods good) {
		this.good = good;
	}
	
}
