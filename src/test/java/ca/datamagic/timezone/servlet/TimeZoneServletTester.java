/**
 * 
 */
package ca.datamagic.timezone.servlet;

import static org.mockito.Mockito.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import ca.datamagic.timezone.dao.BaseDAO;

/**
 * @author Greg
 *
 */
public class TimeZoneServletTester {
	private static Logger logger = LogManager.getLogger(TimeZoneServletTester.class);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DOMConfigurator.configure("src/test/resources/log4j.cfg.xml");
		BaseDAO.setDataPath((new File("src/test/resources/data")).getAbsolutePath());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void collegeParkTest() throws Exception {
		double latitude = 39.0103227;
		double longitude = -76.9124463;
		
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    

        when(request.getPathInfo()).thenReturn(MessageFormat.format("/{0}/{1}/timeZone", Double.toString(latitude), Double.toString(longitude)));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        TimeZoneServlet servlet = new TimeZoneServlet();
        servlet.doGet(request, response);
        
        String json = stringWriter.toString();
        logger.debug("json: " + json);
        
        String timeZone = (new Gson()).fromJson(json, String.class);
        logger.debug("timeZone: " + timeZone);
		Assert.assertEquals("America/New_York", timeZone);
	}
	
	@Test
	public void toledoTest() throws Exception {
		double latitude = 41.6566619;
		double longitude = -83.6444444;
		
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    

        when(request.getPathInfo()).thenReturn(MessageFormat.format("/{0}/{1}/timeZone", Double.toString(latitude), Double.toString(longitude)));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        TimeZoneServlet servlet = new TimeZoneServlet();
        servlet.doGet(request, response);
        
        String json = stringWriter.toString();
        logger.debug("json: " + json);
        
        String timeZone = (new Gson()).fromJson(json, String.class);
        logger.debug("timeZone: " + timeZone);
		Assert.assertEquals("America/New_York", timeZone);
	}
	
	@Test
	public void indianapolisTest() throws Exception {
		double latitude = 39.7799637;
		double longitude = -86.2731768;
		
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    

        when(request.getPathInfo()).thenReturn(MessageFormat.format("/{0}/{1}/timeZone", Double.toString(latitude), Double.toString(longitude)));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        TimeZoneServlet servlet = new TimeZoneServlet();
        servlet.doGet(request, response);
        
        String json = stringWriter.toString();
        logger.debug("json: " + json);
        
        String timeZone = (new Gson()).fromJson(json, String.class);
        logger.debug("timeZone: " + timeZone);
		Assert.assertEquals("America/Indiana/Indianapolis", timeZone);
	}

}
