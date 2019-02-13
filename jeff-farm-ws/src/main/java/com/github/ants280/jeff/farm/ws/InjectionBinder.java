package com.github.ants280.jeff.farm.ws;

import com.github.ants280.jeff.farm.ws.dao.FarmDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class InjectionBinder extends AbstractBinder
{
	@Override
	protected void configure()
	{
		bind(getDataSource()).to(DataSource.class);
		bindAsContract(FarmDao.class);
	}

	private DataSource getDataSource()
	{
		try
		{
			// https://tomcat.apache.org/tomcat-8.0-doc/jndi-resources-howto.html#JDBC_Data_Sources
			// https://stackoverflow.com/questions/23393913/how-can-i-make-resource-annotation-work-with-tomcat
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			return (DataSource) envCtx.lookup("jdbc/jeff-farm-data-source");
		}
		catch (NamingException ex)
		{
			Logger.getLogger(InjectionBinder.class.getName())
					.log(
							Level.SEVERE,
							"Could not lookup DataSource",
							ex);
			return null;
		}
	}
}
