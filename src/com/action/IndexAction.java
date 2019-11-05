package com.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import com.service.GoodService;
import com.service.TopService;
import com.service.TypeService;
import com.util.PageUtil;

@Namespace("/index")
@Results({
	@Result(name="index",location="/index/index.jsp"),
	@Result(name="header",location="/index/header.jsp"),
	@Result(name="goods",location="/index/goods.jsp"),
	@Result(name="detail",location="/index/detail.jsp"),
})

@SuppressWarnings("serial")
public class IndexAction extends BaseAction{
	
	private static final int rows = 4; // 默认每页数量

	private int flag; // 标记, 用于记录当前所在位置等
	private int goodid; // 商品id
	private int typeid; // 类目id
	private String name; // 商品搜索关键词
	
	private Goods good; // 商品实体
	private Types type; // 类目实体
	
	private List<Tops> top1List; // 首页推荐商品列表
	private List<Tops> top2List; // 首页推荐商品列表
	private List<Tops> top3List; // 首页推荐商品列表
	private List<Goods> goodList; // 商品列表
	private List<Types> typeList; // 分类列表
	
	@Resource
	private TopService topService;
	@Resource
	private GoodService goodService;
	@Resource
	private TypeService typeService;
	

	/**
	 * 首页
	 * @return
	 */
	@Action("index")
	public String index(){
		flag = 1;
		typeList = typeService.getList();
		top1List = topService.getList(Tops.TYPE_SCROLL, 1, 1); // 只取前1个
		top2List = topService.getList(Tops.TYPE_LARGE, 1, 6); // 只取前6个
		top3List = topService.getList(Tops.TYPE_SMALL, 1, 8); // 只取前8个
		
		
//		返回的名字就对应了上面@result设置的名字，即返回了一个页面
		return "index";
	}
	
	/**
	 * 推荐列表，分为 热销推荐7就是新品推荐8
	 * @return
	 */
	@Action("top")
	public String tops() {
		flag = typeid==2 ? 7 : 8;
		typeList = typeService.getList();
		goodList = goodService.getList(typeid, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, goodService.getTotal(typeid), page, rows);
		return "goods";
	}
	
	/**
	 * 商品列表
	 * @return
	 */
	@Action("goods")
	public String goods(){
		flag = 2;
		if (typeid > 0) {
			type = typeService.get(typeid);
		}
		typeList = typeService.getList();
		goodList = goodService.getListByType(typeid, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, goodService.getTotalByType(typeid), page, rows);
		return "goods";
	}
	
	/**
	 * 商品详情
	 * @return
	 */
	@Action("detail")
	public String detail(){
		good = goodService.get(goodid);
		typeList = typeService.getList();
		return "detail";
	}
	
	/**
	 * 搜索
	 * @return
	 */
	@Action("search")
	public String search() {
		if (name==null || name.isEmpty()) {
			return goods();
		}
		typeList = typeService.getList();
		goodList = goodService.getListByName(name, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, goodService.getTotalByName(name), page, rows);
		return "goods";
	}



	public List<Types> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Types> typeList) {
		this.typeList = typeList;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public int getGoodid() {
		return goodid;
	}

	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public List<Tops> getTop1List() {
		return top1List;
	}

	public void setTop1List(List<Tops> top1List) {
		this.top1List = top1List;
	}

	public List<Tops> getTop2List() {
		return top2List;
	}

	public void setTop2List(List<Tops> top2List) {
		this.top2List = top2List;
	}

	public List<Tops> getTop3List() {
		return top3List;
	}

	public void setTop3List(List<Tops> top3List) {
		this.top3List = top3List;
	}

	public List<Goods> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<Goods> goodList) {
		this.goodList = goodList;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}