package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Goods; // 既然要操作数据，那么就要导入对应entity的数据获取层类，即Goods类

@Repository // 注册dao层bean(等同于@Component), @Repository用于标注数据访问层，即DAO层；或者说是数据存储层？
public class GoodDao extends BaseDao{

	
	/**
	 * 获取列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getList(int page, int size){
		return getSession().createQuery("from Goods order by id desc", Goods.class).setFirstResult((page-1)*size).setMaxResults(size).list();
		// size每页的数据个数，setFirstResult()从第几条数据开始查询，setMaxResults()要查询的数据条数，这条语句就是返回查询到的list集合
		// createQuery()方法用的是hql数据库查询语句，从Goods这个实体类中进行查询，返回的是bean作为对象存储到List
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public long getTotal(){
		return getSession().createQuery("select count(*) from Goods", Long.class).uniqueResult();  //返回的是一个数字结果 
	}
	
	/**
	 * 通过类型获取列表
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByType(int typeid, int page, int size){
		return getSession().createQuery("from Goods where type_id=:typeid order by id desc", Goods.class).setParameter("typeid", typeid).setFirstResult((page-1)*size).setMaxResults(size).list();
		//这个setParameter就是设置通过什么来查询数据库
		//降序查询商品种类
	
	}
	
	/**
	 * 通过类型获取总数
	 * @param typeid
	 * @return
	 */
	public long getTotalByType(int typeid){
		return (Long) getSession().createQuery("select count(*) from Goods where type_id=:typeid").setParameter("typeid", typeid).uniqueResult();
	}
	
	/**
	 * 通过名称获取列表
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByName(String name, int page, int size){
		return getSession().createQuery("from Goods where name like :name order by id desc", Goods.class).setParameter("name", "%"+name+"%").setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 通过名称获取总数
	 * @param name
	 * @return
	 */
	public long getTotalByName(String name){
		return getSession().createQuery("select count(*) from Goods where name like :name", Long.class).setParameter("name", "%"+name+"%").uniqueResult();
	}
	
}