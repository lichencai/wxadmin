package wxadmin.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import wxadmin.dao.entity.WxArticle;
import wxadmin.util.DateUtil;

@Repository
public class WxArticleJDBC {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<WxArticle> getAll(){
		List<Map<String, Object>> temp = jdbcTemplate.queryForList("select * from wxarticle order by createtime");
		List<WxArticle> list = getArticle(temp);
		return list;
	}
	
	
	public List<WxArticle> queryArticle(String title, String type, Integer number, Integer id, Date date, Integer page, Integer pageSize){
		String sql = "select * from wxarticle where 1=1";
		if(title != null && !"".equals(title)){
			sql += " and title like %" + title + "%";
		}
		if(type != null && !"".equals(type)){
			sql += " and type=" + type;
		}
		if(id != null && !"".equals(id)){
			sql += " and id=" + id;
		}
		if(date != null){
			String dateStr = DateUtil.dateToString(date, "yyyy-MM-dd");
			String begin = dateStr + " 00:00:00";
			String end = dateStr + " 23:59:59";
			sql += " and createtime>=" + "\'" + begin + "\'";
			sql += " and createtime<=" + "\'" + end + "\'";
		}
		sql += " order by createtime desc";
		if(number != null && !"".equals(number)){
			sql += " LIMIT " + number;
		}
		if(page != null && pageSize != null && !"".equals(page) && !"".equals(pageSize)){
			sql += " LIMIT " + page + "," + pageSize;
		}
		List<Map<String, Object>> temp = jdbcTemplate.queryForList(sql);
		return getArticle(temp);
	}
	
	
	public int count(String title, String type, Integer number, Integer id, Date date){
		
		String sql = "select count(*) from wxarticle where 1=1";
		if(title != null && !"".equals(title)){
			sql += " and title like %" + title + "%";
		}
		if(type != null && !"".equals(type)){
			sql += " and type=" + type;
		}
		if(id != null && !"".equals(id)){
			sql += " and id=" + id;
		}
		if(date != null){
			String dateStr = DateUtil.dateToString(date, "yyyy-MM-dd");
			String begin = dateStr + " 00:00:00";
			String end = dateStr + " 23:59:59";
			sql += " and createtime>=" + "\'" + begin + "\'";
			sql += " and createtime<=" + "\'" + end + "\'";
		}
		sql += " order by createtime desc";
		if(number != null && !"".equals(number)){
			sql += " LIMIT " + number;
		}
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}
	
	
	
	private List<WxArticle> getArticle(List<Map<String, Object>> result){
		List<WxArticle> list = new ArrayList<WxArticle>();
		for(Map<String, Object> map : result){
			WxArticle article = new WxArticle();
			for(String str : map.keySet()){
				Object obj = map.get(str);
				if("id".equals(str)){
					article.setId((Integer)obj);
				}else if("title".equals(str)){
					article.setTitle((String)obj);
				}else if("summary".equals(str)){
					article.setSummary((String)obj);
				}else if("type".equals(str)){
					article.setType((String)obj);
				}else if("content".equals(str)){
					article.setContent((String)obj);
				}else if("status".equals(str)){
					article.setStatus((String)obj);
				}else if("price".equals(str)){
					article.setPrice((Integer)obj);
				}else if("createtime".equals(str)){
					article.setCreatetime((Date)obj);
				}
			}
			list.add(article);
		}
		return list;
	}
	
	
	public void saveArticle(WxArticle wxArticle){
		String createtime = DateUtil.dateToString(wxArticle.getCreatetime(), null);
		String sql = "INSERT INTO `wxarticle`(title, summary, type, content, status, price, createtime) "
				+ "VALUES('TITLE','SUMMARY','TYPE','CONTENT','1', PRICE, 'CREATETIME');";
		
		sql = sql.replace("TITLE", wxArticle.getTitle()).replace("SUMMARY", wxArticle.getSummary()).replace("TYPE", wxArticle.getType())
				.replace("CONTENT", wxArticle.getContent()).replace("PRICE", wxArticle.getPrice()+"").replace("CREATETIME", createtime);
		
		
		jdbcTemplate.execute(sql);
	}
	
	
	public void updateArticle(WxArticle wxArticle){
		String createtime = DateUtil.dateToString(wxArticle.getCreatetime(), null);
		String sql = "UPDATE `wxarticle` SET title='TITLE', summary='SUMMARY', type='TYPE', content='CONTENT', price=PRICE, createtime='CREATETIME' "
				+ "where id=ID;";
		
		sql = sql.replace("TITLE", wxArticle.getTitle()).replace("SUMMARY", wxArticle.getSummary()).replace("TYPE", wxArticle.getType())
				.replace("CONTENT", wxArticle.getContent()).replace("PRICE", wxArticle.getPrice()+"").replace("CREATETIME", createtime)
				.replace("ID", wxArticle.getId()+"");
		
		
		jdbcTemplate.execute(sql);
	}


	public void del(Integer articleId) {
		String sql = "delete from `wxarticle` where id=" + articleId;
		jdbcTemplate.execute(sql);
	}
	
	
	
	
	
}
