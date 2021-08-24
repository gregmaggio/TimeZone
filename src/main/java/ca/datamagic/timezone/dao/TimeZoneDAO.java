/**
 * 
 */
package ca.datamagic.timezone.dao;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;

/**
 * @author Greg
 *
 */
public class TimeZoneDAO extends BaseDAO {
	private static Logger logger = LogManager.getLogger(TimeZoneDAO.class);
	private String fileName = null;
	private String typeName = null; 
	private SimpleFeatureSource featureSource = null;

	public TimeZoneDAO() throws IOException {
		this.fileName = MessageFormat.format("{0}/world/tz_world.shp", getDataPath());
		HashMap<Object, Object> connect = new HashMap<Object, Object>();
		connect.put("url", "file://" + this.fileName);
		DataStore dataStore = DataStoreFinder.getDataStore(connect);
		String[] typeNames = dataStore.getTypeNames();
		String typeName = typeNames[0];
		SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
		this.typeName = typeName;
		this.featureSource = featureSource;
	}

	public String getTimeZone(double latitude, double longitude) throws CQLException, IOException {
		String filter = MessageFormat.format("CONTAINS (the_geom, POINT({0} {1}))", Double.toString(longitude), Double.toString(latitude));
		logger.debug("filter: " + filter);
		Query query = new Query(this.typeName, CQL.toFilter(filter));
		SimpleFeatureCollection collection = this.featureSource.getFeatures(query);
		SimpleFeatureIterator iterator = null;
		try {
			iterator = collection.features();
			if (iterator.hasNext()) {
				return (String)iterator.next().getAttribute("TZID");
			}
			return null;
		} finally {
			if (iterator != null) {
				iterator.close();
			}
		}
	}
}
