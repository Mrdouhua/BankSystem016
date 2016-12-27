
package cn.edu.cqupt.core;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.transaction.support.TransactionTemplate;

import cn.edu.cqupt.core.SqlMapTemplateAware;
import cn.edu.cqupt.core.TransactionTemlateAware;

/**
 * @author Leo
 *
 */
public class DbAccessAwareProcessor implements BeanPostProcessor {
	private SqlSessionTemplate sqlSessionTemplate;
	private TransactionTemplate transactionTemplate;
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessAfterInitialization(Object bean, String arg1)
			throws BeansException {
		if(null!=sqlSessionTemplate && bean !=null && bean instanceof SqlMapTemplateAware){
			SqlMapTemplateAware sta = (SqlMapTemplateAware)bean;
			sta.setSqlMapTemplate(this.sqlSessionTemplate);
		}
		if(null!=transactionTemplate && bean !=null && bean instanceof TransactionTemlateAware){
			TransactionTemlateAware tta = (TransactionTemlateAware)bean;
			tta.setTransactionTemplate(this.transactionTemplate);
		}
		return bean;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	

}
