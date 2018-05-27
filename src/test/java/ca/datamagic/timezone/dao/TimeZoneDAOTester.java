/**
 * 
 */
package ca.datamagic.timezone.dao;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Greg
 *
 */
public class TimeZoneDAOTester {
	private static Logger _logger = LogManager.getLogger(TimeZoneDAOTester.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DOMConfigurator.configure("src/test/resources/META-INF/log4j.cfg.xml");
		BaseDAO.setDataPath((new File("src/test/resources/data")).getAbsolutePath());
	}

	@Test
	public void collegeParkTest() throws Exception {
		TimeZoneDAO dao = new TimeZoneDAO();
		String timeZone = dao.getTimeZone(39.0103227,-76.9124463);
		_logger.debug("timeZone: " + timeZone);
		Assert.assertEquals("America/New_York", timeZone);
	}
	
	@Test
	public void toledoTest() throws Exception {
		TimeZoneDAO dao = new TimeZoneDAO();
		String timeZone = dao.getTimeZone(41.6566619,-83.6444444);
		_logger.debug("timeZone: " + timeZone);
		Assert.assertEquals("America/New_York", timeZone);
	}
	
	@Test
	public void indianapolisTest() throws Exception {
		TimeZoneDAO dao = new TimeZoneDAO();
		String timeZone = dao.getTimeZone(39.7799637,-86.2731768);
		_logger.debug("timeZone: " + timeZone);
		Assert.assertEquals("America/Indiana/Indianapolis", timeZone);
	}

}
