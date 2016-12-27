/**
 * 
 */
package cn.edu.cqupt.core;

import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Leo
 * 事务管理
 */
public interface TransactionTemlateAware {
	void setTransactionTemplate(TransactionTemplate transaction);
}
