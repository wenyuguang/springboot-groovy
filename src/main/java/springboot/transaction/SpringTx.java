package springboot.transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 
 * SpringTxManual.java
 * Description: spring事务
 * <p>name: wen </p>
 * <p>Date:2018年8月6日 上午10:58:00</p>
 * @author Tony
 * @version 1.0
 *
 */
public class SpringTx {

	public void transactionManualTest() {
		ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		//设置事务传播行为
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //设置事务隔离级别
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
        TransactionStatus transactionStatus = (TransactionStatus) transactionManager.getTransaction(definition);
        try {
			//do something
        	transactionManager.commit(transactionStatus);
		} catch (Exception e) {
			//err
			transactionManager.rollback(transactionStatus);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, 
			timeout = 60, readOnly = false, rollbackFor = {RuntimeException.class, Exception.class},
			rollbackForClassName = {"RuntimeException","Exception"})
	public void transactionAnnotationTest() {
		//do something
	}
	
}