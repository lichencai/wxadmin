package wxadmin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wxadmin.dao.entity.WxArticle;
import wxadmin.response.ArticleResponse;
import wxadmin.service.ArticleService;
import wxadmin.util.DateUtil;

@Controller
@RequestMapping(value="/article")
public class ArticleController {
	
	
	@Autowired
	private ArticleService articleService;
	
	
	@RequestMapping(value = {"/getList"}, method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsp/articleList");
		
		return mav;
	}
	
	
	@RequestMapping(value = {"/getData"}, method = {RequestMethod.POST,RequestMethod.GET})
	public void getData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String title = request.getParameter("title");
		String createtime = request.getParameter("createtime");
		String type = request.getParameter("type");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		
		Date time = null;
		if(createtime != null && !"".equals(createtime)){
			time = DateUtil.stringToDate(createtime, "yyyy-MM-dd");
		}
		
		ArticleResponse resp = articleService.getList(title, type, time, Integer.parseInt(page), Integer.parseInt(pageSize));
		
		JSONObject json = new JSONObject();
		json.put("total", resp.getTotal());
		JSONArray array = new JSONArray();
		for(WxArticle article : resp.getRows()){
			JSONObject temp = new JSONObject();
			temp.put("title", article.getTitle());
			temp.put("content", article.getContent());
			temp.put("createtime", DateUtil.dateToString(article.getCreatetime(), null));
			temp.put("id", article.getId());
			temp.put("price", article.getPrice());
			temp.put("status", article.getStatus());
			temp.put("summary", article.getSummary());
			temp.put("type", article.getType());
			array.add(temp);
		}
		json.put("rows", array);
		
		String result = json.toString();
		
		response.getWriter().write(result);
		
		return ;
	}
	
	@RequestMapping(value = {"/savedata"}, method = {RequestMethod.POST,RequestMethod.GET})
	public void savedata(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JSONObject json = new JSONObject();
		
		try{
			String title = request.getParameter("title");
			String summary = request.getParameter("summary");
			String type = request.getParameter("type");
			String price = request.getParameter("price");
			String content = request.getParameter("content");
			String id = request.getParameter("id");
			Integer articleId = null;
			if(id!=null && !"".equals(id)){
				articleId = Integer.parseInt(id);
			}
			
			double priceDouble = Double.parseDouble(price);
			int priceInt = (int)(priceDouble * 100);
			
			WxArticle wxArticle = new WxArticle();
			wxArticle.setContent(content);
			wxArticle.setCreatetime(new Date());
			wxArticle.setPrice(priceInt);
			wxArticle.setStatus("");
			wxArticle.setSummary(summary);
			wxArticle.setTitle(title);
			wxArticle.setType(type);
			wxArticle.setId(articleId);
			
			
			int flag = articleService.savedata(wxArticle);
			if(flag == 0){
				json.put("msge", "success");
			}else{
				json.put("errorMsg","error");
			}
		}catch(Exception e){
			json.put("errorMsg","error");
		}
		
		response.getWriter().write(json.toString());
	}
	
	
	@RequestMapping(value = {"/del"}, method = {RequestMethod.POST,RequestMethod.GET})
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("id");
		Integer articleId = null;
		if(id!=null && !"".equals(id)){
			articleId = Integer.parseInt(id);
		}
		
		articleService.del(articleId);
		JSONObject json = new JSONObject();
		json.put("success", "success");
		response.getWriter().write(json.toString());
	}
	
	
	
}
