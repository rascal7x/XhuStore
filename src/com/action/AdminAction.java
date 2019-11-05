package com.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.entity.Admins;
import com.entity.Goods;
import com.entity.Items;
import com.entity.Orders;
import com.entity.Tops;
import com.entity.Types;
import com.entity.Users;
import com.service.AdminService;
import com.service.GoodService;
import com.service.OrderService;
import com.service.TopService;
import com.service.TypeService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.SafeUtil;
import com.util.UploadUtil;

@Namespace("/admin")
@Results({ 
		@Result(name = "login", location = "/admin/login.jsp"),
		@Result(name = "index", location = "/admin/index.jsp"),
		@Result(name = "reindex", type = "redirect", location = "index.action"),

		@Result(name = "typeList", location = "/admin/type_list.jsp"),
		@Result(name = "typeEdit", location = "/admin/type_edit.jsp"),
		@Result(name = "reTypeList", type = "redirect", location = "typeList.action?page=${page}&flag=${flag}"),

		@Result(name = "goodList", location = "/admin/good_list.jsp"),
		@Result(name = "goodAdd", location = "/admin/good_add.jsp"),
		@Result(name = "goodEdit", location = "/admin/good_edit.jsp"),
		@Result(name = "reGoodList", type = "redirect", location = "goodList.action?page=${page}&flag=${flag}&status=${status}"),

		@Result(name = "userList", location = "/admin/user_list.jsp"),
		@Result(name = "userAdd", location = "/admin/user_add.jsp"),
		@Result(name = "userReset", location = "/admin/user_reset.jsp"),
		@Result(name = "userEdit", location = "/admin/user_edit.jsp"),
		@Result(name = "reUserList", type = "redirect", location = "userList.action?page=${page}&flag=${flag}"),

		@Result(name = "orderList", location = "/admin/order_list.jsp"),
		@Result(name = "reOrderList", type = "redirect", location = "orderList.action?status=${status}&page=${page}&flag=${flag}"),
		@Result(name = "itemList", location = "/admin/item_list.jsp"),

		@Result(name = "adminList", location = "/admin/admin_list.jsp"),
		@Result(name = "adminAdd", location = "/admin/admin_add.jsp"),
		@Result(name = "adminReset", location = "/admin/admin_reset.jsp"),
		@Result(name = "adminEdit", location = "/admin/admin_edit.jsp"),
		@Result(name = "reAdminList", type = "redirect", location = "adminList.action?page=${page}&flag=${flag}"), })
@SuppressWarnings("serial")
public class AdminAction extends BaseAction {

	private static final int rows = 10;

	private Users user;
	private Goods good;
	private Tops tops;
	private Types type;
	private Admins admin;
	private byte status;
	private int id;
	private int flag;
	private int typeid;

	private File cover; // 获取上传文件
	private String coverFileName; // 获取上传文件名称
	private File image1; // 获取上传文件
	private String image1FileName; // 获取上传文件名称
	private File image2; // 获取上传文件
	private String image2FileName; // 获取上传文件名称

	private List<Orders> orderList;
	private List<Items> itemList;
	private List<Users> userList;
	private List<Goods> goodList;
	private List<Types> typeList;
	private List<Admins> adminList;

	@Resource
	private AdminService adminService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;
	@Resource
	private GoodService goodService;
	@Resource
	private TopService topService;
	@Resource
	private TypeService typeService;

	/**
	 * 管理员登录
	 * 
	 * @return
	 */
	@Action("login")
	public String login() {
		if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
			getSession().put("username", admin.getUsername());
			return "reindex";
		}
		getServletRequest().setAttribute("msg", "用户名或密码错误!");
		return "login";
	}

	/**
	 * 退出
	 * @return
	 */
	@Action("logout")
	public String logout() {
		getSession().remove("admin");
		return "login";
	}
	
	/**
	 * 后台首页
	 * @return
	 */
	@Action("index")
	public String index() {
		getServletRequest().setAttribute("msg", "恭喜你! 登录成功了");
		return "index";
	}

	/**
	 * 订单列表
	 * 
	 * @return
	 */
	@Action("orderList")
	public String orderList() {
		flag = 1;
		orderList = orderService.getList(status, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, orderService.getTotal(status), page, rows);
		return "orderList";
	}

	/**
	 * 订单发货
	 * 
	 * @return
	 */
	@Action("orderDispose")
	public String orderDispose() {
		flag = 1;
		orderService.dispose(id);
		return "reOrderList";
	}
	
	/**
	 * 订单完成
	 * 
	 * @return
	 */
	@Action("orderFinish")
	public String orderFinish() {
		flag = 1;
		orderService.finish(id);
		return "reOrderList";
	}

	/**
	 * 订单删除
	 * 
	 * @return
	 */
	@Action("orderDelete")
	public String orderDelete() {
		flag = 1;
		orderService.delete(id);
		return "reOrderList";
	}

	/**
	 * 顾客管理
	 * 
	 * @return
	 */
	@Action("userList")
	public String userList() {
		flag = 2;
		userList = userService.getList(page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, userService.getTotal(), page, rows);
		return "userList";
	}

	/**
	 * 顾客添加
	 * 
	 * @return
	 */
	@Action("userAdd")
	public String userAdd() {
		flag = 2;
		return "userAdd";
	}

	/**
	 * 顾客添加
	 * 
	 * @return
	 */
	@Action("userSave")
	public String userSave() {
		flag = 2;
		if (userService.isExist(user.getUsername())) {
			getServletRequest().setAttribute("msg", "用户名已存在!");
			return "userAdd";
		}
		userService.add(user);
		return "reUserList";
	}

	/**
	 * 顾客密码重置页面
	 * 
	 * @return
	 */
	@Action("userRe")
	public String userRe() {
		flag = 2;
		user = userService.get(id);
		return "userReset";
	}

	/**
	 * 顾客密码重置
	 * 
	 * @return
	 */
	@Action("userReset")
	public String userReset() {
		flag = 2;
		String password = SafeUtil.encode(user.getPassword());
		user = userService.get(user.getId());
		user.setPassword(password);
		userService.update(user);
		return "reUserList";
	}

	/**
	 * 顾客更新
	 * 
	 * @return
	 */
	@Action("userEdit")
	public String userEdit() {
		flag = 2;
		user = userService.get(id);
		return "userEdit";
	}

	/**
	 * 顾客更新
	 * 
	 * @return
	 */
	@Action("userUpdate")
	public String userUpdate() {
		flag = 2;
		userService.update(user);
		return "reUserList";
	}

	/**
	 * 顾客删除
	 * 
	 * @return
	 */
	@Action("userDelete")
	public String userDelete() {
		flag = 2;
		userService.delete(user);
		return "reUserList";
	}

	/**
	 * 产品列表
	 * 
	 * @return
	 */
	@Action("goodList")
	public String goodList() {
		flag = 3;
		goodList = goodService.getList(status, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, goodService.getTotal(status), page, rows);
		return "goodList";
	}

	/**
	 * 产品添加
	 * 
	 * @return
	 */
	@Action("goodAdd")
	public String goodAdd() {
		flag = 3;
		typeList = typeService.getList();
		return "goodAdd";
	}

	/**
	 * 产品添加
	 * 
	 * @return
	 */
	@Action("goodSave")
	public String goodSave() {
		flag = 3;
		good.setCover(UploadUtil.fileUpload(cover, coverFileName, "picture"));
		good.setImage1(UploadUtil.fileUpload(image1, image1FileName, "picture"));
		good.setImage2(UploadUtil.fileUpload(image2, image2FileName, "picture"));
		goodService.add(good);
		return "reGoodList";
	}

	/**
	 * 产品更新
	 * 
	 * @return
	 */
	@Action("goodEdit")
	public String goodEdit() {
		flag = 3;
		typeList = typeService.getList();
		good = goodService.get(id);
		return "goodEdit";
	}

	/**
	 * 产品更新
	 * 
	 * @return
	 */
	@Action("goodUpdate")
	public String goodUpdate() {
		flag = 3;
		if (cover != null) {
			good.setCover(UploadUtil.fileUpload(cover, coverFileName, "picture"));
		}
		if (image1 != null) {
			good.setImage1(UploadUtil.fileUpload(image1, image1FileName, "picture"));
		}
		if (image2 != null) {
			good.setImage2(UploadUtil.fileUpload(image2, image2FileName, "picture"));
		}
		goodService.update(good);
		return "reGoodList";
	}

	/**
	 * 产品删除
	 * 
	 * @return
	 */
	@Action("goodDelete")
	public String goodDelete() {
		flag = 3;
		goodService.delete(good);
		return "reGoodList";
	}
	
	/**
	 * 添加推荐
	 * @return
	 * @throws IOException 
	 */
	@Action("topSave")
	public void topSave() throws IOException {
		int id = topService.add(tops);
		if(id > 0) {
			servletResponse.setContentType("text/text");
			servletResponse.setCharacterEncoding("utf-8");
			servletResponse.getWriter().write("ok");
		}
	}
	
	/**
	 * 删除推荐
	 * @return
	 */
	@Action("topDelete")
	public void topDelete() throws IOException {
		boolean flag = topService.delete(tops);
		if(flag) {
			servletResponse.setContentType("text/text");
			servletResponse.setCharacterEncoding("utf-8");
			servletResponse.getWriter().write("ok");
		}
	}

	/**
	 * 类目列表
	 * 
	 * @return
	 */
	@Action("typeList")
	public String typeList() {
		flag = 4;
		typeList = typeService.getList();
		return "typeList";
	}

	/**
	 * 类目添加
	 * 
	 * @return
	 */
	@Action("typeSave")
	public String typeSave() {
		flag = 4;
		typeService.add(type);
		return "reTypeList";
	}

	/**
	 * 类目更新
	 * 
	 * @return
	 */
	@Action("typeEdit")
	public String typeUp() {
		flag = 4;
		type = typeService.get(id);
		return "typeEdit";
	}

	/**
	 * 类目更新
	 * 
	 * @return
	 */
	@Action("typeUpdate")
	public String typeUpdate() {
		flag = 4;
		typeService.update(type);
		return "reTypeList";
	}

	/**
	 * 类目删除
	 * 
	 * @return
	 */
	@Action("typeDelete")
	public String typeDelete() {
		flag = 4;
		typeService.delete(type);
		return "reTypeList";
	}

	/**
	 * 管理员列表
	 * 
	 * @return
	 */
	@Action("adminList")
	public String adminList() {
		adminList = adminService.getList(page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, adminService.getTotal(), page, rows);
		return "admin";
	}

	/**
	 * 管理员修改自己密码
	 * 
	 * @return
	 */
	@Action("adminRe")
	public String adminRe() {
		flag = 5;
		admin = adminService.getByUsername(String.valueOf(getSession().get("username")));
		return "adminReset";
	}

	/**
	 * 管理员修改自己密码
	 * 
	 * @return
	 */
	@Action("adminReset")
	public String adminReset() {
		flag = 5;
		if (adminService.get(admin.getId()).getPassword().equals(SafeUtil.encode(admin.getPassword()))) {
			admin.setPassword(SafeUtil.encode(admin.getPasswordNew()));
			adminService.update(admin);
			getServletRequest().setAttribute("msg", "修改成功!");
		}else {
			getServletRequest().setAttribute("msg", "原密码错误!");
		}
		return "adminReset";
	}

	/**
	 * 管理员添加
	 * 
	 * @return
	 */
	@Action("adminSave")
	public String adminSave() {
		if (adminService.isExist(admin.getUsername())) {
			addActionError("用户名已存在!");
			return "adminAdd";
		}
		adminService.add(admin);
		return "reAdminList";
	}

	/**
	 * 管理员修改
	 * 
	 * @return
	 */
	@Action("adminEdit")
	public String adminEdit() {
		admin = adminService.get(id);
		return "adminEdit";
	}

	/**
	 * 管理员更新
	 * 
	 * @return
	 */
	@Action("adminUpdate")
	public String adminUpdate() {
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		adminService.update(admin);
		return "reAdminList";
	}

	/**
	 * 管理员删除
	 * 
	 * @return
	 */
	@Action("adminDelete")
	public String adminDelete() {
		adminService.delete(admin);
		return "reAdminList";
	}
	/*
	 * 按类型显示
	 */
	@Action("goodListType")
	public String goodListType() {
		flag=3;
		 //System.out.println("@@@@@"+typeid);
		typeList = typeService.getList();
		goodList = goodService.getListByType(typeid, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, goodService.getTotalByType(typeid), page, rows);
		return "goodList";
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public Admins getAdmin() {
		return admin;
	}

	public void setAdmin(Admins admin) {
		this.admin = admin;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public File getCover() {
		return cover;
	}

	public void setCover(File cover) {
		this.cover = cover;
	}

	public String getCoverFileName() {
		return coverFileName;
	}

	public void setCoverFileName(String coverFileName) {
		this.coverFileName = coverFileName;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public File getImage2() {
		return image2;
	}

	public void setImage2(File image2) {
		this.image2 = image2;
	}

	public String getImage2FileName() {
		return image2FileName;
	}

	public void setImage2FileName(String image2FileName) {
		this.image2FileName = image2FileName;
	}

	public List<Orders> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}

	public List<Items> getItemList() {
		return itemList;
	}

	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public List<Goods> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<Goods> goodList) {
		this.goodList = goodList;
	}

	public List<Types> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Types> typeList) {
		this.typeList = typeList;
	}

	public List<Admins> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admins> adminList) {
		this.adminList = adminList;
	}

	public static int getRows() {
		return rows;
	}

	public Tops getTops() {
		return tops;
	}

	public void setTops(Tops tops) {
		this.tops = tops;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid=typeid;
	}


}
