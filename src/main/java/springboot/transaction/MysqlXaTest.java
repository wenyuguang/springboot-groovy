package springboot.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

public class MysqlXaTest {

	protected static MysqlXADataSource getMysqlXADataSource1() {
		MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
		mysqlXADataSource.setURL("jdbc:mysql://127.0.0.1:3306/test");
		mysqlXADataSource.setUser("root");
		mysqlXADataSource.setPassword("wenyuguang");
		return mysqlXADataSource;
	}

	protected static MysqlXADataSource getMysqlXADataSource2() {
		MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
		mysqlXADataSource.setURL("jdbc:mysql://127.0.0.1:3306/test1");
		mysqlXADataSource.setUser("root");
		mysqlXADataSource.setPassword("wenyuguang");
		return mysqlXADataSource;
	}

	public static AtomikosDataSourceBean getXADataSource1() {
		AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
		dataSourceBean.setUniqueResourceName("dn1");
		dataSourceBean.setXaDataSource(getMysqlXADataSource1());
		dataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		return dataSourceBean;
	}

	public static AtomikosDataSourceBean getXADataSource2() {
		AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
		dataSourceBean.setUniqueResourceName("dn2");
		dataSourceBean.setXaDataSource(getMysqlXADataSource2());
		/*
		 * Properties xaProperties = new Properties(); xaProperties.setProperty("url",
		 * "jdbc:mysql://10.0.0.104:8066/TESTDB"); xaProperties.setProperty("user",
		 * "user"); xaProperties.setProperty("password", "user");
		 * dataSourceBean.setXaProperties(xaProperties);
		 */
		dataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		return dataSourceBean;
	}

	public static UserTransactionManager getUserTransactionManager() throws Exception {
		return new UserTransactionManager();
	}

	public static void main(String[] args) throws Exception {
		AtomikosDataSourceBean atomikos1 = getXADataSource1();
		AtomikosDataSourceBean atomikos2 = getXADataSource2();
		XAConnection xaCon1 = atomikos1.getXaDataSource().getXAConnection();
		XAConnection xaCon2 = atomikos2.getXaDataSource().getXAConnection();
		Connection conn1 = xaCon1.getConnection();
		Connection conn2 = xaCon2.getConnection();
		XAResource xaResource1 = xaCon1.getXAResource();
		XAResource xaResource2 = xaCon2.getXAResource();
		Xid xid1 = new MysqlXid(new byte[] { 0x01 }, new byte[] { 0x02 }, 0);
		Xid xid2 = new MysqlXid(new byte[] { 0x01 }, new byte[] { 0x03 }, 0);

		try {
			xaResource1.start(xid1, XAResource.TMNOFLAGS);
			PreparedStatement ps1 = conn1.prepareStatement("INSERT INTO xa_test(id) VALUES(1)");
			ps1.executeUpdate();
			xaResource1.end(xid1, XAResource.TMSUCCESS);

			xaResource2.start(xid2, XAResource.TMNOFLAGS);
			PreparedStatement ps2 = conn2.prepareStatement("INSERT INTO xa_test(id) VALUES(1)");
			ps2.executeUpdate();
			xaResource2.end(xid2, XAResource.TMSUCCESS);

			int prepare1 = xaResource1.prepare(xid1);
			int prepare2 = xaResource2.prepare(xid2);

			if (prepare1 == XAResource.XA_OK && prepare2 == XAResource.XA_OK) {
				xaResource1.commit(xid1, false);
				System.out.println("suc1");
				xaResource2.commit(xid2, false);
				System.out.println("suc2");
			} else {
				System.out.println("rollback");
				xaResource1.rollback(xid1);
				xaResource2.rollback(xid2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			xaResource1.rollback(xid1);
			xaResource2.rollback(xid2);
		}
	}
}