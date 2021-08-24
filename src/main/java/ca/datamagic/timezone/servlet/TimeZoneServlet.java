/**
 * 
 */
package ca.datamagic.timezone.servlet;

import java.io.IOError;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import ca.datamagic.timezone.dao.TimeZoneDAO;

/**
 * @author Greg
 *
 */
public class TimeZoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(TimeZoneServlet.class);
	private static final Pattern timeZonePattern = Pattern.compile("/(?<latitude>[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+))/(?<longitude>[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+))/timeZone", Pattern.CASE_INSENSITIVE);
	private static TimeZoneDAO dao = null;
	
	private static synchronized TimeZoneDAO getDAO() throws IOException {
		if (dao == null) {
			dao = new TimeZoneDAO();
		}
		return dao;
 	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pathInfo = request.getPathInfo();
			logger.debug("pathInfo: " + pathInfo);
			Matcher timeZoneMatcher = timeZonePattern.matcher(pathInfo);
			if (timeZoneMatcher.find()) {
				logger.debug("timeZone");
				String latitude = timeZoneMatcher.group("latitude");
				logger.debug("latitude: " + latitude);
				String longitude = timeZoneMatcher.group("longitude");
				logger.debug("longitude: " + longitude);
				double doubleLatitude = Double.parseDouble(latitude);
				double doubleLongitude = Double.parseDouble(longitude);
				String timeZone = getDAO().getTimeZone(doubleLatitude, doubleLongitude);
				String json = (new Gson()).toJson(timeZone);
				response.setContentType("application/json");
				response.getWriter().println(json);
				return;
			}
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (Throwable t) {
			logger.error("Exception", t);
			throw new IOError(t);
		}
	}
}
