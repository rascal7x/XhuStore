package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Tops;

@Repository // 注册dao层bean等同于@Component
public class TopDao extends BaseDao{

	/**
	 * 获取列表
	 * @return
	 */
	public List<Tops> getList(byte type, int page, int size) {
		return getSession().createQuery("from Tops where type=:type order by id desc", Tops.class).setParameter("type", type).setFirstResult((page-1)*size).setMaxResults(size).list();
	}

	/**
	 * 获取总数
	 * @param type
	 * @return
	 */
	public long getTotal(byte type) {
		return getSession().createQuery("select count(*) from Tops where type=:type", Long.class).setParameter("type", type).uniqueResult();
	}
	
	/**
	 * 通过商品id获取
	 * @param goodid
	 * @return
	 */
	public List<Tops> getListByGoodid(int goodid) {
		return getSession().createQuery("from Tops where good.id=:goodid", Tops.class).setParameter("goodid", goodid).list();
	}
	
	/**
	 * 通过商品id和类型删除
	 * @param goodid
	 * @param type
	 * @return
	 */
	public boolean deleteByGoodidAndType(int goodid, byte type) {
		return getSession().createQuery("delete from Tops where good.id=:goodid and type=:type")
				.setParameter("goodid", goodid).setParameter("type", type).executeUpdate() > 0;
	}
	
	/**
	 * 通过goodid删除
	 * @param goodid
	 * @return
	 */
	public boolean deleteByGoodid(int goodid) {
		return getSession().createQuery("delete from Tops where good.id=:goodid")
				.setParameter("goodid", goodid).executeUpdate() > 0;
	}

}