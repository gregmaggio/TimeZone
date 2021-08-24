/**
 * 
 */
package ca.datamagic.timezone.servlet;

import java.text.MessageFormat;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.datamagic.timezone.dao.BaseDAO;

/**
 * @author Greg
 *
 */
public class TimeZoneContextListener implements ServletContextListener {
	private static Logger logger = LogManager.getLogger(TimeZoneContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String realPath = sce.getServletContext().getRealPath("/");
		String dataPath = MessageFormat.format("{0}/WEB-INF/classes/data", realPath);
		BaseDAO.setDataPath(dataPath);
		logger.debug("contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("contextDestroyed");
	}
}
