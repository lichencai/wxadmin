package wxadmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wxadmin.dao.entity.WxArticle;
import wxadmin.dao.jdbc.WxArticleJDBC;
import wxadmin.response.ArticleResponse;

@Service
public class ArticleService {
	
	@Autowired
	private WxArticleJDBC wxArticleJDBC;
	
	public ArticleResponse getList(String title, String type, Date date, int page, int pageSize){
		
		ArticleResponse resp = new ArticleResponse();
		
		List<WxArticle> rows = wxArticleJDBC.queryArticle(title, type, null, null, date, (page - 1) * pageSize, pageSize);
		int total = wxArticleJDBC.count(title, type, null, null, date);
		
		resp.setTotal(total);
		resp.setRows(rows);
		
		return resp;
	}
	
	
	public int savedata(WxArticle wxArticle){
		if(wxArticle.getId() == null)
			wxArticleJDBC.saveArticle(wxArticle);
		else
			wxArticleJDBC.updateArticle(wxArticle);
		return 0;
	}


	public void del(Integer articleId) {
		wxArticleJDBC.del(articleId);
		
	}
	
	
	
}
