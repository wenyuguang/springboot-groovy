package springboot.transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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

	ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
	
	/** 声明式（XML配置）
	 * <tx:advice id="txAdvice" transaction-manager="txManager"> 
         <tx:attributes>  <!--设置所有匹配的方法，然后设置传播级别和事务隔离-->
           <tx:method name="save*" propagation="REQUIRED" /> 
           <tx:method name="add*" propagation="REQUIRED" /> 
           <tx:method name="create*" propagation="REQUIRED" /> 
           <tx:method name="insert*" propagation="REQUIRED" /> 
           <tx:method name="update*" propagation="REQUIRED" /> 
           <tx:method name="merge*" propagation="REQUIRED" /> 
           <tx:method name="del*" propagation="REQUIRED" /> 
           <tx:method name="remove*" propagation="REQUIRED" /> 
           <tx:method name="put*" propagation="REQUIRED" /> 
           <tx:method name="get*" propagation="SUPPORTS" read-only="true" /> 
           <tx:method name="count*" propagation="SUPPORTS" read-only="true" /> 
           <tx:method name="find*" propagation="SUPPORTS" read-only="true" /> 
           <tx:method name="list*" propagation="SUPPORTS" read-only="true" /> 
           <tx:method name="*" propagation="SUPPORTS" read-only="true" /> 
         </tx:attributes> 
       </tx:advice> 
       <aop:config> 
         <aop:pointcut id="txPointcut" expression="execution(* org.transaction..service.*.*(..))" /> 
         <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut" /> 
       </aop:config>
	 */
	public void transactionManualTest() {
		
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
	
	/**
	 * 事物模板方式
	 */
	public void testTransactionTemplate() {
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
		  TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager); 
		  transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED); //设置事务隔离级别
		  transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);//设置为required传播级别
		  transactionTemplate.execute(new TransactionCallbackWithoutResult() { 
		      @Override 
		      protected void doInTransactionWithoutResult(TransactionStatus status) {  //事务块
//		         jdbcTemplate.update(INSERT_SQL, "test"); 
		  }}); 
		  
		}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, 
			timeout = 60, readOnly = false, rollbackFor = {RuntimeException.class, Exception.class},
			rollbackForClassName = {"RuntimeException","Exception"})
	public void transactionAnnotationTest() {
		//do something
	}
	
}