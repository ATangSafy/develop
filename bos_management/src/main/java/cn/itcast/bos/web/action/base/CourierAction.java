package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
@Actions
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{

	//模型驱动
	private Courier courier = new Courier();
	
	@Override
	public Courier getModel() {
		return courier;
	}
	
	//注入Service
	@Autowired
	private CourierService courierService;
	
	//添加快递员的方法
	@Action(value = "courier_save" , 
			results = { @Result(name = "success",
			location ="./pages/base/courier.html",type="redirect")})
	public String save(){
		courierService.save(courier);
		return SUCCESS;
	}
	
	// 属性驱动
	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	// 分页查询方法 
	@Action(value="courier_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//封装Pageable对象
		Pageable pageable = new PageRequest(page-1,rows);
		// 调用业务层 ，返回 Page
		Page<Courier> pageData = courierService.findPageData(pageable);
		// 将返回page对象 转换datagrid需要格式
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pageData.getTotalElements());
		result.put("rows", pageData.getContent());
		// 将结果对象 压入值栈顶部
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
	
	// 属性驱动
		private String ids;

		public void setIds(String ids) {
			this.ids = ids;
		}

	// 作废快递员
	@Action(value = "courier_delBatch", results = { @Result(name = "success", location = "./pages/base/courier.html", type = "redirect") })
	public String delBatch() {
		// 按,分隔ids
		String[] idArray = ids.split(",");
		// 调用业务层，批量作废
		courierService.delBatch(idArray);
		return SUCCESS;
	}
}
