package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Orders;
import com.entity.Items;

@Repository // 注册dao层bean等同于@Component
public class OrderDao extends BaseDao{

	/**
	 * 获取列表
	 * @param status
	 * @param page
	 * @param row
	 */
	public List<Orders> getList(byte status, int page, int rows) {
		return status > 0 ? getSession().createQuery("from Orders where status=:status order by id desc", Orders.class)
				.setParameter("status", status).setFirstResult((page-1)*rows).setMaxResults(rows).list() :
				getSession().createQuery("from Orders order by id desc", Orders.class).setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}

	/**
	 * 获取总数
	 * @param status
	 * @return
	 */
	public long getTotal(byte status) {
		return status > 0 ? getSession().createQuery("select count(*) from Orders where status=:status", Long.class)
				.setParameter("status", status).uniqueResult() :
				getSession().createQuery("select count(*) from Orders", Long.class).uniqueResult();
	}

	/**
	 * 通过用户获取列表
	 * @param userid
	 */
	public List<Orders> getListByUserid(int userid) {
		return getSession().createQuery("from Orders where user_id=:userid order by id desc", Orders.class)
				.setParameter("userid", userid).list();
	}
	
	/**
	 * 订单项列表
	 * @param Ordersid
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Items> getItemList(int orderid) {
		return getSession().createQuery("from Items where order_id=:orderid", Items.class)
				.setParameter("orderid", orderid).list();
	}


}
