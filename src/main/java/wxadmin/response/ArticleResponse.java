package wxadmin.response;

import java.util.List;

import wxadmin.dao.entity.WxArticle;

public class ArticleResponse {
	private List<WxArticle> rows = null;
	private int total;
	public List<WxArticle> getRows() {
		return rows;
	}
	public void setRows(List<WxArticle> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
