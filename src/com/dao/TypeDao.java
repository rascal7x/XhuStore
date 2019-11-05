package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Types;

@Repository // 注册dao层bean等同于@Component
public class TypeDao extends BaseDao{

	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Types> getList() {
		return getSession().createQuery("from Types order by id desc", Types.class).list();
	}

}