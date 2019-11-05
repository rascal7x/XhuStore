package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.GoodDao;
import com.entity.Goods;
import com.entity.Tops;

/**
 * 商品服务
 */
@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class GoodService {

	@Resource	
	private GoodDao goodDao;
	@Resource
	private TopService topService;
	
	
	/**
	 * 获取列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getList(int status, int page, int size){
		if (status == 0) {
			return packTopList(goodDao.getList(page, size));
		}
		List<Tops> topList = topService.getList((byte)status, page, size);
		if(topList!=null && !topList.isEmpty()) {
			List<Goods> goodList = new ArrayList<>();
			for(Tops top : topList) {
				goodList.add(packTop(top.getGood()));
			}
			return goodList;
		}
		return null;
	}

	/**
	 * 获取产品总数
	 * @return
	 */
	public long getTotal(int status){
		if (status == 0) {
			return goodDao.getTotal();
		}
		return topService.getTotal((byte)status);
	}
	
	/**
	 * 通过名称获取产品列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByName(String name, int page, int size){
		return goodDao.getListByName(name, page, size);
	}
	
	/**
	 * 通过名称获取产品总数
	 * @return
	 */
	public long getTotalByName(String name){
		return goodDao.getTotalByName(name);
	}

	/**
	 * 通过分类搜索
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByType(int typeid, int page, int size) {
		return typeid > 0 ? goodDao.getListByType(typeid, page, size) : goodDao.getList(page, size);
	}
	
	/**
	 * 获取数量
	 * @param typeid
	 * @return
	 */
	public long getTotalByType(int typeid){
		return typeid > 0 ? goodDao.getTotalByType(typeid) : goodDao.getTotal();
	}
	
	/**
	 * 通过id获取，获取商品的id，GoodDao继承了BaseDao，所以下面的get，add,update,delete方法都是在BaseDao中实现的。
	 * @param productid
	 * @return
	 */
	public Goods get(int id) {
		return goodDao.get(Goods.class, id);
	}
	
	/**
	 * 添加
	 * @param product
	 */
	public Integer add(Goods good) {
		return goodDao.save(good);
	}

	/**
	 * 修改
	 * @param product
	 * @return 
	 */
	public boolean update(Goods good) {
		return goodDao.update(good);
	}
	
	/**
	 * 删除商品
	 * 先删除此商品的推荐信息
	 * @param product
	 */
	public boolean delete(Goods good) {
		topService.deleteByGoodid(good.getId());
		return goodDao.delete(good);
	}
	

	/**
	 * 封装商品推荐信息
	 * @param list
	 * @return
	 */
	private List<Goods> packTopList(List<Goods> list) {
		for(Goods good : list) {
			good = packTop(good); // 方法在下面实现
		}
		return list;
	}

	/**
	 * 封装商品推荐信息
	 * @param good
	 * @return
	 */
	private Goods packTop(Goods good) {
		if(good != null) {
			List<Tops> topList = topService.getListByGoodid(good.getId());
			if (Objects.nonNull(topList) && !topList.isEmpty()) {
				for(Tops top : topList) {
					if(top.getType()==Tops.TYPE_SCROLL) {
						good.setTopScroll(true);
					}else if (top.getType()==Tops.TYPE_LARGE) {
						good.setTopLarge(true);
					}else if (top.getType()==Tops.TYPE_SMALL) {
						good.setTopSmall(true);
					}
				}
			}
		}
		return good;
	}

}