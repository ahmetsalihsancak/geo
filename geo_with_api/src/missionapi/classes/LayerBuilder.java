package missionapi.classes;

import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.CircularString;
import org.geotools.geometry.jts.CurvedGeometryFactory;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.Style;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class LayerBuilder {
	
	private static int defaultDotCount = 30;
	
	public static void setDefaultDotCount(int dotCount) {
		defaultDotCount = dotCount;
	}

	public static int getDefaultDotCount() {
		return defaultDotCount;
	}
	
	/**
	 *
	 * @return SimpleFeatureType to use to creating point
	 */
	public static SimpleFeatureType createPointFeatureType() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
       builder.setName("Point");
       builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system

       builder.add("the_geom", org.locationtech.jts.geom.Point.class);
       builder.length(15).add("Name:", String.class);
       builder.add("No:", Integer.class);
       builder.add("LAT:", Double.class);
       builder.add("LONG:", Double.class);

       return builder.buildFeatureType();
	}
	
	public static SimpleFeatureType createTextFeatureType() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
       builder.setName("Text");
       builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system

       builder.add("the_geom", org.locationtech.jts.geom.Point.class);

       return builder.buildFeatureType();
	}

	public static SimpleFeatureType createPathFeatureType() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
       builder.setName("Path");
       builder.setCRS(DefaultGeographicCRS.WGS84);

       builder.add("the_geom", org.locationtech.jts.geom.LineString.class);
       builder.length(15).add("Name:", String.class);
       builder.add("No:", Integer.class);

       return builder.buildFeatureType();
	}

	public static SimpleFeatureType createPolygonFeatureType() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
       builder.setName("Poly");
       builder.setCRS(DefaultGeographicCRS.WGS84);

       builder.add("the_geom", org.locationtech.jts.geom.Polygon.class);

       return builder.buildFeatureType();
	}
	
	public static SimpleFeatureType createArcFeatureType() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
       builder.setName("Arc");
       builder.setCRS(DefaultGeographicCRS.WGS84);

       builder.add("the_geom", org.locationtech.jts.geom.Polygon.class);

       return builder.buildFeatureType();
	}
	
	public static SimpleFeatureType createDotFeatureType() throws SchemaException {
	   SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
	   builder.setName("Dot");
	   builder.setCRS(DefaultGeographicCRS.WGS84);

	   builder.add("the_geom", org.locationtech.jts.geom.MultiPoint.class);

	   return builder.buildFeatureType();
	}

	/**
	 *
	 * @param coords
	 * @param path_no
	 * @param PATH_TYPE
	 * @param geometryFactory
	 * @param LineStyle
	 * @return Path layer
	 */
	public static Layer createPathLayer(Coordinate[] coords, int path_no, SimpleFeatureType PATH_TYPE, GeometryFactory geometryFactory, Style LineStyle) {
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(PATH_TYPE);
       LineString line = geometryFactory.createLineString(coords);
       featureBuilder.add(line);
       featureBuilder.add("Path");
       featureBuilder.add(path_no);
       SimpleFeature feature = featureBuilder.buildFeature(null);
       DefaultFeatureCollection col = new DefaultFeatureCollection();
       col.add(feature);
       Layer lay = new FeatureLayer(col, LineStyle);
       return lay;
	}

	/**
	 *
	 * @param lat
	 * @param lon
	 * @param point_no
	 * @param POINT_TYPE
	 * @param PointStyle
	 * @return Point layer
	 */
	public static Layer createPointLayer(double lat, double lon, int point_no, SimpleFeatureType POINT_TYPE, GeometryFactory geometryFactory, Style PointStyle) {
       SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(POINT_TYPE);
       Point locationPoint = geometryFactory.createPoint(new Coordinate(lat, lon));
       featureBuilder.add(locationPoint);
       featureBuilder.add("Point");
       featureBuilder.add(point_no);
       featureBuilder.add(lat);
       featureBuilder.add(lon);
       SimpleFeature feature = featureBuilder.buildFeature(null);
       DefaultFeatureCollection col = new DefaultFeatureCollection();
       col.add(feature);
       Layer lay = new FeatureLayer(col, PointStyle);
       return lay;
	}
	
	/**
	 *
	 * @param name
	 * @param lat
	 * @param lon
	 * @param point_no
	 * @param POINT_TYPE
	 * @param geometryFactory
	 * @param PointStyle
	 * @return Point layer
	 */
	public static Layer createPointLayer(String name, double lat, double lon, int point_no, SimpleFeatureType POINT_TYPE, 
			GeometryFactory geometryFactory, Style PointStyle) {
       SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(POINT_TYPE);
       Point locationPoint = geometryFactory.createPoint(new Coordinate(lon, lat));
       featureBuilder.add(locationPoint);
       featureBuilder.add(name);
       featureBuilder.add(point_no);
       featureBuilder.add(lat);
       featureBuilder.add(lon);
       SimpleFeature feature = featureBuilder.buildFeature(null);
       DefaultFeatureCollection col = new DefaultFeatureCollection();
       col.add(feature);
       Layer lay = new FeatureLayer(col, PointStyle);
       return lay;
	}
	
	public static Layer createPointLayer(String name, Point locationPoint, int point_no, SimpleFeatureType POINT_TYPE, GeometryFactory geometryFactory, Style PointStyle) {
       SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(POINT_TYPE);
       featureBuilder.add(locationPoint);
       featureBuilder.add(name);
       featureBuilder.add(point_no);
       featureBuilder.add(locationPoint.getX());
       featureBuilder.add(locationPoint.getY());
       SimpleFeature feature = featureBuilder.buildFeature(null);
       DefaultFeatureCollection col = new DefaultFeatureCollection();
       col.add(feature);
       Layer lay = new FeatureLayer(col, PointStyle);
       return lay;
	}

	/**
	 * Creates a dashed line between 2 points with default number of points setting. 
	 * 
	 * */
	public static Layer createDashedLineLayer(double startLat, double startLon, double endLat, double endLon, 
			SimpleFeatureType POINT_TYPE, GeometryFactory geometryFactory, Style PointStyle) {
		return createDashedLineLayer(startLat, startLon, endLat, endLon, defaultDotCount, POINT_TYPE, geometryFactory, PointStyle);
	}

	/**
	 * Creates a dashed line between 2 points with given number of points.
	 * 
	 * */
	public static Layer createDashedLineLayer(double startLat, double startLon, double endLat, double endLon, int dotCount, 
			SimpleFeatureType POINT_TYPE, GeometryFactory geometryFactory, Style PointStyle) {
		dotCount++;
		Coordinate[] coords = new Coordinate[dotCount+1];
		double lon = endLon - startLon;
		double lat = endLat - startLat;
		double a = lat / dotCount;
		double b = lon / dotCount;
		
		for (int i = 0; i < dotCount+1; i++) {
			coords[i] = new Coordinate(startLon+(i*b), startLat+(i*a));
		}
		
	    SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(POINT_TYPE);
	    
		@SuppressWarnings("deprecation")
		MultiPoint points = geometryFactory.createMultiPoint(coords);
		
		featureBuilder.add(points);
		SimpleFeature feature = featureBuilder.buildFeature(null);
		DefaultFeatureCollection col = new DefaultFeatureCollection();
		col.add(feature);
		Layer layer = new FeatureLayer(col, PointStyle);
		return layer;
	}
	
	public static Layer createCircleLayer(Polygon circle, GeometryFactory geometryFactory, SimpleFeatureType circleFeatureType, Style circleStyle) {
       SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(circleFeatureType);
       featureBuilder.add(circle);
       SimpleFeature feature = featureBuilder.buildFeature(null);
       DefaultFeatureCollection col = new DefaultFeatureCollection();
       col.add(feature);
       Layer lay = new FeatureLayer(col, circleStyle);
       return lay;
	}
	
	public static Layer createArcLayer(CoordinateSequence coordsSeq, CurvedGeometryFactory curvedFactory, SimpleFeatureType arcFeatureType, Style arcStyle) {
		CircularString arc = (CircularString) curvedFactory.createCurvedGeometry(coordsSeq);
       SimpleFeatureBuilder featureBuilder1 = new SimpleFeatureBuilder(arcFeatureType);
       featureBuilder1.add(arc);
       SimpleFeature feature1 = featureBuilder1.buildFeature(null);
       DefaultFeatureCollection col1 = new DefaultFeatureCollection();
       col1.add(feature1);
       Layer lay = new FeatureLayer(col1, arcStyle);
       return lay;
	}
	
	public static Layer createTextLayer(double lat, double lon, int point_no, SimpleFeatureType POINT_TYPE, GeometryFactory geometryFactory, Style PointStyle) {
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(POINT_TYPE);
       Point locationPoint = geometryFactory.createPoint(new Coordinate(lon, lat));
       featureBuilder.add(locationPoint);
       SimpleFeature feature = featureBuilder.buildFeature(null);
       DefaultFeatureCollection col = new DefaultFeatureCollection();
       col.add(feature);
       Layer lay = new FeatureLayer(col, PointStyle);
       return lay;
	}
	
}
