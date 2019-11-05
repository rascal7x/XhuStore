package com.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * action基类, 继承自ActionSupport类  
 * 为子类提供struts2 map类型session对象
 * 提供分页工具变量等
 */
public class BaseAction extends ActionSupport implements SessionAware,ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	
	protected int page = 1;
	protected String pageTool;
	protected Map<String, Object> session;
	protected HttpServletRequest servletRequest;
	protected HttpServletResponse servletResponse;

	
	@Override   //重写自父接口SessionAware的方法,有容器自行调用并给session对象赋值
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	//用于在子类中获取session对象
	public Map<String, Object> getSession(){
		return session;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPageTool() {
		return pageTool;
	}

	public void setPageTool(String pageTool) {
		this.pageTool = pageTool;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}
	
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.servletResponse = response;
	}
	
	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}
	
	
	/**
	 * 返回响应数据
	 * @param msg
	 */
	protected void sendResponseMsg(String msg) {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/html"); 								//固定写法
		response.setHeader("Pragma", "no-cache");							//固定写法
		response.setHeader("Cache-Control", "no-store");					//固定写法
		response.addHeader("Cache-Control", "must-revalidate");  			//固定写法
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");	//固定写法
		response.setDateHeader("Expires", 0); 								//固定写法
		response.setCharacterEncoding("UTF-8");								//固定写法
		
		PrintWriter out = null;  // PrintWriter() 的作用是为了定义流输出的位置，并且此流可以正常的存储中文，减少乱码输出。用完要close掉。
		try {
			out = response.getWriter();
			out.print(msg);  			//输出打印sendResponseMsg()中的参数
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}


}
