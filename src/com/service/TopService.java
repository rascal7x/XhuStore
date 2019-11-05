package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.TopDao;
import com.entity.Tops;

/**
 * 商品推荐服务
 */
@Service	// 注解为service层spring管理bean, @Service注释表示定义一个bean，自动根据bean的类名实例化一个首写字母为小写的bean，这里就实例化了一个topService
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class TopService {

	@Resource	
	private TopDao topDao;
	
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Tops> getList(byte type, int page, int size){
		return topDao.getList(type, page, size);
	}
	
	/**
	 * 获取总数
	 * @param type
	 * @return
	 */
	public long getTotal(byte type) {
		return topDao.getTotal(type);
	}
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Tops> getListByGoodid(int goodid){
		return topDao.getListByGoodid(goodid);
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Tops get(int id) {
		return topDao.get(Tops.class, id);
	}
	
	/**
	 * 添加
	 * @param top
	 * @return
	 */
	public Integer add(Tops top) {
		return topDao.save(top);
	}

	/**
	 * 更新
	 * @param top
	 */
	public boolean update(Tops top) {
		return topDao.update(top);
	}

	/**
	 * 删除
	 * @param top
	 */
	public boolean delete(Tops top) {
		return top.getId() > 0 ? topDao.delete(top) : topDao.deleteByGoodidAndType(top.getGood().getId(), top.getType());
	}
	
	/**
	 * 按商品删除
	 * @param goodid
	 * @return
	 */
	public boolean deleteByGoodid(int goodid) {
		return topDao.deleteByGoodid(goodid);
	}
	
}
