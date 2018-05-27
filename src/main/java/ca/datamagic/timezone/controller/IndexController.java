/**
 * 
 */
package ca.datamagic.timezone.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.datamagic.timezone.dao.BaseDAO;
import ca.datamagic.timezone.dao.TimeZoneDAO;
import ca.datamagic.timezone.dto.SwaggerConfigurationDTO;
import ca.datamagic.timezone.dto.SwaggerResourceDTO;

/**
 * @author Greg
 *
 */
@Controller
@RequestMapping("")
public class IndexController {
	private static Logger _logger = LogManager.getLogger(IndexController.class);
	private static SwaggerConfigurationDTO _swaggerConfiguration = null;
	private static SwaggerResourceDTO[] _swaggerResources = null;
	private static String _swagger = null;
	private static TimeZoneDAO _dao = null;
	private static String _nullResponse = "\"\"";
	
	static {
		FileInputStream swaggerStream = null;
		try {
			DefaultResourceLoader loader = new DefaultResourceLoader();       
		    Resource dataResource = loader.getResource("classpath:data");
		    Resource metaInfResource = loader.getResource("META-INF");
		    String dataPath = dataResource.getFile().getAbsolutePath();
		    String metaInfPath = metaInfResource.getFile().getAbsolutePath();
		    _logger.debug("dataPath: " + dataPath);
		    _logger.debug("metaInfPath: " + metaInfPath);
		    
		    String swaggerFileName = MessageFormat.format("{0}/swagger.json", metaInfPath);
		    swaggerStream = new FileInputStream(swaggerFileName);
		    _swagger = IOUtils.toString(swaggerStream, "UTF-8");
		    
		    BaseDAO.setDataPath(dataPath);
			_dao = new TimeZoneDAO();
			_swaggerConfiguration = new SwaggerConfigurationDTO();
			_swaggerResources = new SwaggerResourceDTO[] { new SwaggerResourceDTO() };
		} catch (Throwable t) {
			_logger.error("Exception", t);
		}
		if (swaggerStream != null) {
			IOUtils.closeQuietly(swaggerStream);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/{latitude}/{longitude}/timeZone", produces="application/json")
	@ResponseBody
    public String getTimeZone(@PathVariable String latitude, @PathVariable String longitude, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String timeZone = _dao.getTimeZone(Double.parseDouble(latitude), Double.parseDouble(longitude));
			if (timeZone != null) {
				return MessageFormat.format("\"{0}\"", timeZone);
			}
			return _nullResponse;
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@RequestMapping(value="/swagger-resources/configuration/ui", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerConfigurationDTO getSwaggerConfigurationUI() {
		return _swaggerConfiguration;
	}
	
	@RequestMapping(value="/swagger-resources", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerResourceDTO[] getSwaggerResources() {
		return _swaggerResources;
	}
	
	@RequestMapping(value="/v2/api-docs", method=RequestMethod.GET, produces="application/json")
	public void getSwagger(Writer responseWriter) throws IOException {
		responseWriter.write(_swagger);
	}
}
