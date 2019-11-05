package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Admins;

@Repository // 注册dao层bean等同于@Component
public class AdminDao extends BaseDao{

	/**
	 * 通过用户名查找
	 * @param username
	 * @return
	 */
	public Admins getByUsername(String username) {
		return getSession().createQuery("from Admins where username=:username", Admins.class)
				.setParameter("username", username).uniqueResult();
	}
	
	/**
	 * 通过用户名和密码查找
	 * @param username
	 * @param password
	 * @return 无记录返回null
	 */
	public Admins getByUsernameAndPassword(String username, String password){
		return getSession().createQuery("from Admins where username=:username and password=:password", Admins.class)
				.setParameter("username", username).setParameter("password", password).uniqueResult();
	}

	/**
	 * 获取列表
	 * @param page
	 * @param rows
	 * @return 无记录返回空集合
	 */
	public List<Admins> getList(int page, int rows){
		return getSession().createQuery("from Admins", Admins.class).setFirstResult(rows*(page-1)).setMaxResults(rows).list();
	}

	/**
	 * 总数
	 * @return
	 */
	public long getTotal() {
		return getSession().createQuery("select count(*) from Admins", Long.class).uniqueResult();
	}

}