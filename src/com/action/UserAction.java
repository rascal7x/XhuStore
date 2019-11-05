package com.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.entity.Orders;
import com.entity.Types;
import com.entity.Items;
import com.entity.Goods;
import com.entity.Users;
import com.service.OrderService;
import com.service.TypeService;
import com.service.GoodService;
import com.service.UserService;
import com.util.SafeUtil;

@Namespace("/index")
@Results({
	@Result(name="login",location="/index/login.jsp"),
	@Result(name="register",location="/index/register.jsp"),
	@Result(name="reindex",type="redirect",location="index.action"), // redirect是重定向到一个action
	@Result(name="cart",location="/index/cart.jsp"),
	@Result(name="order",location="/index/order.jsp"),
	@Result(name="my",location="/index/my.jsp"),
	@Result(name="pay",location="/index/pay.jsp"),
	@Result(name="payok",location="/index/payok.jsp"),
	@Result(name="retopay",type="redirect",location="topay.action?orderid=${orderid}"),
	@Result(name="repayok",type="redirect",location="payok.action?orderid=${order.id}"),
})
@SuppressWarnings("serial")
public class UserAction extends BaseAction{
	
	private static final String INDENT_KEY = "order";  // INDENT_KEY为order字符串
	
	private int flag; // 页面标记
	private int goodid; // 商品id
	private int orderid; // 订单id
	private String province;//地址省份
	private String city;//地址城市
	private String area;//地址所在区
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	private Users user; // 用户实体
	private Orders order; // 订单实体
	private List<Types> typeList; // 分类列表
	private List<Orders> orderList; // 订单列表
	
	@Resource
	private UserService userService;
	@Resource
	private OrderService orderService;
	@Resource
	private GoodService productService;
	@Resource
	private TypeService typeService;

	
	/**
	 * 注册用户
	 * @return
	 */
	@Action("register")
	public String register(){
		typeList = typeService.getList();
		if(flag==-1) {
			flag = 5; // 注册页面
			return "register";
		}
		if (user.getUsername().isEmpty()) {
			getServletRequest().setAttribute("msg", "用户名不能为空!");
			return "register";
		}else if (userService.isExist(user.getUsername())) {
			getServletRequest().setAttribute("msg", "用户名已存在!");
			return "register";
		}else {
			String password = user.getPassword();
			String address=province+city+area+user.getAddress();
			System.out.println(address);
			user.setAddress(address);
			userService.add(user);
			user.setPassword(password);
			
			return login(); // 注册成功后转去登录
		}
	}

	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	@Action("login")
	public String login() {
		typeList = typeService.getList();
		if(flag==-1) {
			flag = 6; // 登录页面
			return "login";
		}
		if(userService.checkUser(user.getUsername(), user.getPassword())){
			getSession().put("user", userService.get(user.getUsername()));
			return "reindex";
		} else {
			getServletRequest().setAttribute("msg", "用户名或密码错误!");
			return "login";
		}
	}

	/**
	 * 注销登录
	 * @return
	 */
	@Action("logout")
	public String logout() {
		getSession().remove("user");
		getSession().remove("order");
		return "login";
	}
	
	/**
	 * 查看购物车
	 * @return
	 */
	@Action("cart")
	public String cart() {
		typeList = typeService.getList();
		return "cart";
	}
	
	/**
	 * 购买
	 * @return
	 */
	@Action("buy")    // 执行此方法 ，返回的是一个字符，是ok或者empty,以供cart.js里的方法使用
	public void buy(){
		Goods product = productService.get(goodid); // 返回产品Id赋给product
		
		if (product.getStock() <= 0) { // 库存不足，Dao层getStock直接查询
			sendResponseMsg("empty");		// 返回响应数据这个函数是自己写的，在BaseAction.java文件
			return;
		}
		
		// 以下的代码就是往order这个map对象里添加一个键值对，即添加一件订单商品，若已经有同样商品，则添加件数
		Orders order = (Orders) getSession().get(INDENT_KEY);   // INDENT_KEY= "order"
		if (order==null) {
			getSession().put(INDENT_KEY, orderService.add(product));  // .add()方法返回的是订单对象
		}else {
			getSession().put(INDENT_KEY, orderService.addOrderItem(order, product));
		}
		sendResponseMsg("ok");
	}
	
	/**
	 * 减少
	 */
	@Action("lessen")
	public void lessen(){
		Orders order = (Orders) getSession().get(INDENT_KEY);
		if (order != null) {
			getSession().put(INDENT_KEY, orderService.lessenIndentItem(order, productService.get(goodid)));
		}
		sendResponseMsg("ok");
	}
	
	/**
	 * 删除
	 */
	@Action("delete")
	public void delete(){
		Orders order = (Orders) getSession().get(INDENT_KEY);
		if (order != null) {
			getSession().put(INDENT_KEY, orderService.deleteIndentItem(order, productService.get(goodid)));
		}
		sendResponseMsg("ok");
	}
	
	
	/**
	 * 提交订单
	 * @return
	 */
	@Action("save")  // 浏览器的地址栏里显示的是save.action
	public String save(){
		typeList = typeService.getList();
		Users user = (Users) getSession().get("user");
		
		if (user == null) {
			getServletRequest().setAttribute("msg", "请登录后提交订单!");
			return "login";
		}
		
		Orders sessionOrder = (Orders) getSession().get(INDENT_KEY);
		
		if (sessionOrder != null) {
			if (sessionOrder != null) {
				for(Items item : sessionOrder.getItemList()){ // 检测商品库存(防止库存不足)
					Goods product = productService.get(item.getGood().getId());
					if(item.getAmount() > product.getStock()){
						getServletRequest().setAttribute("msg", "商品 ["+product.getName()+"] 库存不足! 当前库存数量: "+product.getStock());
						return "cart";
					}
				}
			}
			
			Users u = userService.get(user.getId());
			sessionOrder.setUser(u);
			orderid = orderService.save(sessionOrder);	// 保存订单
			getSession().remove(INDENT_KEY);	// 清除购物车
			return "retopay";
		}
		
		getServletRequest().setAttribute("msg", "处理失败!");
		
		return "cart";
	}
	
	/**
	 * 支付页面、与下面的界面一起、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
	 * @return
	 */
	@Action("topay")
	public String topay() {
		typeList = typeService.getList();
		getServletRequest().setAttribute("order", orderService.get(orderid));
		return "pay";
	}
	
	/**
	 * 支付(模拟)
	 * @return
	 */
	@Action("pay")
	public String pay() {
		typeList = typeService.getList();
		orderService.pay(order);
		return "repayok";
	}
	
	/**
	 * 支付成功
	 * @return
	 */
	@Action("payok")
	public String payok() {
		typeList = typeService.getList();
		order = orderService.get(orderid);
		int paytype = order.getPaytype();
		if(paytype == Orders.PAYTYPE_WECHAT || paytype == Orders.PAYTYPE_ALIPAY) {
			getServletRequest().setAttribute("msg", "订单["+orderid+"]支付成功");
		}else {
			getServletRequest().setAttribute("msg", "订单["+orderid+"]货到付款");
		}
		return "payok";
	}
	
	/**
	 * 查看订单
	 * @return
	 */
	@Action("order")
	public String order(){
		flag = 3;
		typeList = typeService.getList();
		Users user = (Users) getSession().get("user");
		if (user == null) {
			addActionError("请登录后查看订单!");
			return "login";
		}
		orderList = orderService.getListByUserid(user.getId());
		if (orderList!=null && !orderList.isEmpty()) {
			for(Orders order : orderList){
				order.setItemList(orderService.getItemList(order.getId()));
			}
		}
		return "order";
	}
	
	
	/**
	 * 个人信息
	 * @return
	 */
	@Action("my")
	public String my(){
		flag = 4;
		typeList = typeService.getList();
		Users userLogin = (Users) getSession().get("user");
		if (userLogin == null) {
			getServletRequest().setAttribute("msg", "请先登录!");
			return "login";
		}
		if (user==null) { // 进入个人中心
			return "my";
		}
		Users u = userService.get(user.getId());
		// 修改资料
		u.setName(user.getName());
		u.setPhone(user.getPhone());
		u.setAddress(user.getAddress());
		userService.update(u);  // 更新数据库
		getSession().put("user", u); // 更新session
		getServletRequest().setAttribute("msg", "信息修改成功!");
		// 修改密码
		if(user.getPasswordNew()!=null && !user.getPasswordNew().trim().isEmpty()) {
			if (user.getPassword()!=null && !user.getPassword().trim().isEmpty() 
					&& SafeUtil.encode(user.getPassword()).equals(u.getPassword())) {
				if (user.getPasswordNew()!=null && !user.getPasswordNew().trim().isEmpty()) {
					u.setPassword(SafeUtil.encode(user.getPasswordNew()));
				}
				userService.update(u);  // 更新数据库
				getSession().put("user", u); // 更新session
				getServletRequest().setAttribute("msg", "密码修改成功!");
			}else {
				getServletRequest().setAttribute("msg", "原密码错误!");
			}
		}
		return "my";
	}
	
	
	public List<Types> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Types> typeList) {
		this.typeList = typeList;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getGoodid() {
		return goodid;
	}

	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}

	public List<Orders> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	
}