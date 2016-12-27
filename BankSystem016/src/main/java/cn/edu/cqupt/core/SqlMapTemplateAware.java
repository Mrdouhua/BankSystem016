/**
 * 
 */
package cn.edu.cqupt.core;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * @author Leo
 *	
 */
public interface SqlMapTemplateAware {
	void setSqlMapTemplate(SqlSessionTemplate sqlSessionTemplate);
}
