/**
 * 
 */
package cn.edu.cqupt.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import cn.edu.cqupt.core.SqlMapTemplateAware;
import cn.edu.cqupt.core.TransactionTemlateAware;

/**
 * @author Leo
 *
 */
public abstract class BaseController implements SqlMapTemplateAware,
TransactionTemlateAware {
	static Logger logger = LoggerFactory.getLogger(BaseController.class);
	private SqlSessionTemplate sqlMapTemplate;
	private TransactionTemplate transactionTemplate;

	public void setSqlMapTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlMapTemplate = sqlSessionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transaction) {
		this.transactionTemplate = transaction;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public SqlSessionTemplate getSqlMapTemplate() {
		return sqlMapTemplate;
	}

	enum Color {
		INFO("#025889"),
		WARN("#ffa830");
		private String name;
		Color(String name){
			this.name=name;
		}
		public String getName() {
			return this.name;
		}
	}
	
	/**
	 * 向前台输出javascript 提示信息
	 * @param message 提示信息
	 * @param color 提示信息的背景颜色
	 * @return
	 */
	public String ShowTips(String message, Color color){
		StringBuffer showTips = new StringBuffer("<script>");
		showTips.append("$(function(){");
		showTips.append("$(document.body).append(\" <div id='JSNote' style='display:none;top:45px;left: 50%;font-size:16px; color:#fff;font-weight: bold; padding: 0 15px;line-height: 30px;position: absolute;height:30px;background-color:");
		showTips.append(color.getName());
		showTips.append("';border-radius:25px;>");
		showTips.append(message);
		showTips.append("</div>\");");
		showTips.append("$(\"#JSNote\").slideDown();");
		showTips.append("setTimeout(function(){$(\"#JSNote\").slideUp();},2000);");
		showTips.append("setTimeout(function(){$(\"#JSNote\").remove();},3000);");
		showTips.append("})");
		showTips.append("</script>");
		return showTips.toString();
	}
	
	/**
	 * 输出全url(带参数的全路径)
	 * @param request
	 */
	public void printRequestUrl(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();  
		Set<Entry<String, String[]>> set = map.entrySet();  
		Iterator<Entry<String, String[]>> it = set.iterator();  
		String s = "";
		while (it.hasNext()) {  
			Entry<String, String[]> entry = it.next();  

			s += entry.getKey() + "=";  
			for (String i : entry.getValue()) {  
				s +=i + "&&"; 
			}  
		}  
		logger.debug("##############-CSJS-###########：" + ";请求的url(带参数):" + request.getRequestURI() + "?" + s);
	}

}
