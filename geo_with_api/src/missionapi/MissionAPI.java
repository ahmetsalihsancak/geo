package missionapi;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.CurvedGeometryFactory;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.Font;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.swing.JMapFrame;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;
import org.opengis.feature.simple.SimpleFeatureType;

import missionapi.classes.LayerBuilder;
import missionapi.classes.PointClass;
import missionapi.classes.Styles;
import missionapi.classes.Styles.POINT_TYPE;

public class MissionAPI {
	
	private MapContent mapContent;
	private JMapFrame mapFrame;
	
	private List<Layer> IRLAR_LAYER_List;
	private List<Layer> POINT_LAYER_List;
	private List<Layer> IZLAR_LAYER_List;
	private List<Layer> TEXT_LAYER_List;
	private List<Layer> TRAJECTORY_LAYER_List;
	private List<Layer> LINE_LAYER_List;
	private List<Layer> RECT_LAYER_List;
	
	private List<PointClass> POINTS_List;
	
	private Styles StylesClass;
	
	private SimpleFeatureType POINT_FEATURE_TYPE;
	private SimpleFeatureType PATH_FEATURE_TYPE;
	private SimpleFeatureType CIRCLE_FEATURE_TYPE;
	private SimpleFeatureType RECT_FEATURE_TYPE;
	private SimpleFeatureType ARC_FEATURE_TYPE;
	private SimpleFeatureType DOT_FEATURE_TYPE;
	
	private Style LINE_STYLE;
	private Style ARC_STYLE;
	private Style POINT_STYLE;
	private Style POLYGON_STYLE;
	
	private GeometryFactory geometryFactory;
	
	public MissionAPI(MapContent mapContent, JMapFrame mapFrame) {
		
		this.mapContent = mapContent;
		this.mapFrame = mapFrame;
		
		IRLAR_LAYER_List = new ArrayList<>();
		POINT_LAYER_List = new ArrayList<>();
		IZLAR_LAYER_List = new ArrayList<>();
		TEXT_LAYER_List = new ArrayList<>();
		TRAJECTORY_LAYER_List = new ArrayList<>();
		LINE_LAYER_List = new ArrayList<>();
		RECT_LAYER_List = new ArrayList<>();
		
		POINTS_List = new ArrayList<>();
		
		StylesClass = new Styles();
		
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
		
		LINE_STYLE = StylesClass.getLineStyle(Color.RED, 2);
		ARC_STYLE = StylesClass.getLineStyle();
		POINT_STYLE = StylesClass.getPointStyle(POINT_TYPE.DEFAULT);
		POLYGON_STYLE = StylesClass.getPolygonStyle(Color.BLUE, null, 1);
        
		POINT_FEATURE_TYPE = LayerBuilder.createPointFeatureType();
		PATH_FEATURE_TYPE = LayerBuilder.createPathFeatureType();
		CIRCLE_FEATURE_TYPE = LayerBuilder.createPolygonFeatureType();
		RECT_FEATURE_TYPE = LayerBuilder.createPolygonFeatureType();
		ARC_FEATURE_TYPE = LayerBuilder.createPolygonFeatureType();
		try {
			DOT_FEATURE_TYPE = LayerBuilder.createDotFeatureType();
		} catch (SchemaException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteTrajectoryLayer() {

		for (Layer layer : TRAJECTORY_LAYER_List) {
			mapContent.removeLayer(layer);
		}
		
		TRAJECTORY_LAYER_List.clear();
	}
	
	public void deletePointsLayer() {
		
		for (Layer layer : POINT_LAYER_List) {
			mapContent.removeLayer(layer);
		}
		
		POINT_LAYER_List.clear();
	}
	
	public void deleteIRLAR_Layer() {
		
		for (Layer layer : IRLAR_LAYER_List) {
			mapContent.removeLayer(layer);
		}
		IRLAR_LAYER_List.clear();
	}
	
	public void deleteIZLAR_Layer() {
		
		for (Layer layer : IZLAR_LAYER_List) {
			mapContent.removeLayer(layer);
		}
		IZLAR_LAYER_List.clear();
	}

	public void deleteTextLayer() {
		
		for (Layer layer : TEXT_LAYER_List) {
			mapContent.removeLayer(layer);
		}
		TEXT_LAYER_List.clear();
	}
	
	public void deleteRectangleLayer() {
		
		for (Layer layer : RECT_LAYER_List) {
			mapContent.removeLayer(layer);
		}
		RECT_LAYER_List.clear();
	}
	
	public void deleteLineLayer() {
		
		for (Layer layer : LINE_LAYER_List) {
			mapContent.removeLayer(layer);
		}
		LINE_LAYER_List.clear();
	}
	
	/**
	 * Deletes all layers
	 * */
	public void deleteAllLayers() {
		deleteIRLAR_Layer();
		deleteIZLAR_Layer();
		deleteLineLayer();
		deletePointsLayer();
		deleteRectangleLayer();
		deleteTextLayer();
		deleteTrajectoryLayer();
	}

	/**
	 * Clear points list
	 * */
	public void clearPointsList() {
		POINTS_List.clear();
	}
	
	/**
	 * Deletes all layers and point list
	 * */
	public void deleteAllLayersAndPoints() {
		deleteAllLayers();
		clearPointsList();
	}
	
	/**
	 * Draws IR LAR
	 * 
	 * @param centerLat : Center latitude of IRLAR
	 * @param centerLon : Center longitude of IRLAR
	 * @param color : IRLAR color
	 * @param radius : IRLAR radius
	 * 
	 * */
	public void drawIRLAR(double centerLat, double centerLon, Color color, int radius) {
        int r2 = radius * 1000;
        Coordinate[] coords2  = new Coordinate[4];
        GeodeticCalculator geoCalc = new GeodeticCalculator(DefaultGeographicCRS.WGS84);
		geoCalc.setStartingGeographicPoint(centerLon, centerLat);

		Point2D dest;
		
		for (int i = 0; i < 4; i++) {
			geoCalc.setDirection((90*i), r2);
			dest = geoCalc.getDestinationGeographicPoint();
	        coords2[i] = new Coordinate(dest.getX(),dest.getY());
		}
		CoordinateList list = new CoordinateList(coords2);
		list.closeRing();
		LinearRing ring = geometryFactory.createLinearRing(list.toCoordinateArray());
		Geometry circleGeometry = JTS.smooth(ring, 0.0);
		Polygon circle = geometryFactory.createPolygon((LinearRing) circleGeometry, null);
		POLYGON_STYLE = StylesClass.getPolygonStyle(color, null, 1);
		Layer layer = LayerBuilder.createCircleLayer(circle, geometryFactory, CIRCLE_FEATURE_TYPE, POLYGON_STYLE);
		mapContent.addLayer(layer);
		IRLAR_LAYER_List.add(layer);  
	}
	
	
	/**
	 * Adds waypoint with given name.
	 * 
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param pointName : Point name to show
	 * @param col : Point color
	 * @param fontname : Font name of the pointName
	 * @param size : Size of the pointName
	 * */
	public void addWaypoint(double centerLat, double centerLon, String pointName, Color col, String fontname, int size) {
		addPoint(POINT_TYPE.WAYPOINT, centerLat, centerLon, pointName, col, fontname, size);
	}

	/**
	 * Adds waypoint.
	 * 
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param col : Point color
	 * */
	public void addWaypoint(double centerLat, double centerLon, Color col) {
		addWaypoint(centerLat, centerLon, "", col, "", 0);
	}

	/**
	 * Adds target point with given name.
	 * 
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param pointName : Point name to show
	 * @param col : Point color
	 * @param fontname : Font name of the pointName
	 * @param size : Size of the pointName
	 * */
	public void addTarget(double centerLat, double centerLon, String pointName, Color col, String fontname, int size) {
		addPoint(POINT_TYPE.TARGET, centerLat, centerLon, pointName, col, fontname, size);
	}

	/**
	 * Adds target point.
	 * 
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param col : Point color
	 * */
	public void addTarget(double centerLat, double centerLon, Color col) {
		addTarget(centerLat, centerLon, "", col, "", 0);
	}

	/**
	 * Adds release point with given name.
	 * 
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param pointName : Point name to show
	 * @param col : Point color
	 * @param fontname : Font name of the pointName
	 * @param size : Size of the pointName
	 * */
	public void addReleasePoint(double centerLat, double centerLon, String pointName, Color col, String fontname, int size) {
		addPoint(POINT_TYPE.RELEASE, centerLat, centerLon, pointName, col, fontname, size);
	}

	/**
	 * Adds release point.
	 * 
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param col : Point color
	 * */
	public void addReleasePoint(double centerLat, double centerLon, Color col) {
		addReleasePoint(centerLat, centerLon, "", col, "", 0);
	}

	/**
	 * Adds point with given type and name.
	 * 
	 * @param pType : POINT_TYPE of the point
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param pointName : Point name to show
	 * @param col : Point color
	 * @param fontname : Font name of the pointName
	 * @param size : Size of the pointName
	 * */
	public void addPoint(POINT_TYPE pType, double centerLat, double centerLon, String pointName, Color col, String fontname, int size) {
		int pointNumber = POINTS_List.size();
		StyleBuilder styleBuilder = new StyleBuilder();
		Font font = styleBuilder.createFont(fontname, size);
		POINT_STYLE = StylesClass.getPointStyle(pType, col, "Name:", font);
		Layer layer = LayerBuilder.createPointLayer(pointName, centerLat, centerLon, pointNumber, POINT_FEATURE_TYPE, geometryFactory, POINT_STYLE);
		POINTS_List.add(new PointClass(centerLat, centerLon, pointNumber, pType, pointName));
		POINT_LAYER_List.add(layer);
		mapContent.addLayer(layer);
	}

	/**
	 * Adds point with given type.
	 * 
	 * @param pType : POINT_TYPE of the point
	 * @param centerLat : Center latitude of point
	 * @param centerLon : Center longitude of point
	 * @param col : Point color
	 * */
	public void addPoint(POINT_TYPE pType, double centerLat, double centerLon, Color col) {
		addPoint(pType, centerLat, centerLon, "", col, "", 0);
	}

	/**
	 * Adds text.
	 * 
	 * @param text : Text to show
	 * @param lat : Starting latitude of the text
	 * @param lon : Starting longitude of the text
	 * @param col : Text color
	 * @param fontname : Font name of the text
	 * @param size : Size of the text
	 * */
	public void drawText(String text, double lat, double lon, Color col, String fontname, int size) {
		int pointNumber = TEXT_LAYER_List.size();
		Style style = StylesClass.getTextStyle(text, col, fontname, size);
		Layer layer = LayerBuilder.createTextLayer(lat, lon, pointNumber, LayerBuilder.createTextFeatureType(), geometryFactory, style);
		mapContent.addLayer(layer);
		TEXT_LAYER_List.add(layer);
	}
	
	/**
	 * Draws IZ LAR
	 * 
	 * @param centerLat : Center latitude of IZLAR
	 * @param centerLon : Center longitude of IZLAR
	 * @param color : IZLAR color
	 * @param line_width : IZLAR line width
	 * @param outer_radius : IZLAR outer radius
	 * @param inner_radius : IZLAR inner radius
	 * @param angle : Angle of the IZLAR
	 * 
	 * */
	public void drawIZLAR(double centerLat, double centerLon, Color color, float line_width, double outer_radius, double inner_radius, double angle) {
		GeodeticCalculator geoCalc = new GeodeticCalculator(DefaultGeographicCRS.WGS84);
		geoCalc = new GeodeticCalculator(DefaultGeographicCRS.WGS84);
		double r2 = outer_radius * 1000;
		angle = angle - 90;
		Coordinate[] coords3  = new Coordinate[7];
        
		Point2D dest;
        
		geoCalc.setStartingGeographicPoint(centerLon, centerLat);
        
		for (int i = 0; i < 3; i++) {
			geoCalc.setDirection((360-90*i)+angle, r2);
        	dest = geoCalc.getDestinationGeographicPoint();
        	coords3[i] = new Coordinate(dest.getX(),dest.getY());
		}
        
		geoCalc.setDirection(180+angle, (r2-1));
		dest = geoCalc.getDestinationGeographicPoint();
		coords3[3] = new Coordinate(dest.getX(),dest.getY());

		r2 = inner_radius * 1000;

		for (int i = 0; i < 3; i++) {
        	
			geoCalc.setDirection((180+90*i)+angle, r2);
			dest = geoCalc.getDestinationGeographicPoint();
			coords3[i+4] = new Coordinate(dest.getX(),dest.getY());
		}
        
		CurvedGeometryFactory curvedFactory = new CurvedGeometryFactory(geometryFactory, Double.MAX_VALUE);

		double packedCoords[] = new double[coords3.length*2];
        
		for (int i = 0; i < coords3.length; i++) {
			packedCoords[2*i] = coords3[i].x;
			packedCoords[(2*i)+1] = coords3[i].y;
		}
        
		CoordinateSequence coordsSeq = PackedCoordinateSequenceFactory.DOUBLE_FACTORY.create(packedCoords,2);
		ARC_STYLE = StylesClass.getLineStyle(color, line_width);
		Layer layer = LayerBuilder.createArcLayer(coordsSeq, curvedFactory, ARC_FEATURE_TYPE, ARC_STYLE);
		mapContent.addLayer(layer);
		IZLAR_LAYER_List.add(layer);
	}
	
	/**
	 * Draws trajectory and points using the given lat-lon arrays.
	 * <p>
	 * Points must be entered sequentially. (RELEASE-WAYPOINT(S)-TARGET)
	 * 
	 * @param POINTS_List : List of points
	 * 
	 * */
	public void drawTrajectory(double[] lat, double[] lon, Color point_color, Color trajectory_color, int trajectory_width) {
		addPoint(POINT_TYPE.RELEASE, lat[0], lon[0], point_color);
		for (int i = 1; i < lon.length-1; i++) {
			addPoint(POINT_TYPE.WAYPOINT, lat[i], lon[i], point_color);
		}
		addPoint(POINT_TYPE.TARGET, lat[lat.length-1], lon[lon.length-1], point_color);
		drawTrajectory(trajectory_color, trajectory_width);
	}
	
	/**
	 * Draws trajectory and points (with name) using the given PointClass list.
	 * 
	 * @param POINTS_List : List of points
	 * @param font : null == default font
	 * 
	 * */
	public void drawTrajectory(List<PointClass> POINTS_List, Color trajectory_color, int trajectory_width, Font font) {
		setPointsList(POINTS_List);
		deletePointsLayer();
		for (PointClass points : POINTS_List) {
    		Style PointStyle = StylesClass.getPointStyle(points.getPointType(), StylesClass.getDefaultPointColor(), "Name:", font);
    		Layer layer = LayerBuilder.createPointLayer(points.getPointName(), points.getLatitude(), points.getLongtitude(), points.getPointNo(), POINT_FEATURE_TYPE, geometryFactory, PointStyle);
    		POINT_LAYER_List.add(layer);
    		mapContent.addLayer(layer);
		}
		drawTrajectory(trajectory_color, trajectory_width);
	}

	/**
	 * Draws trajectory and points using the points list of this class.
	 * 
	 * */
	public void drawTrajectory(Color trajectory_color, int trajectory_width) {
		int points_list_size = POINTS_List.size();
		Coordinate[] coords  = new Coordinate[points_list_size];
		List<PointClass> waypointList = new ArrayList<PointClass>();
		boolean hasRelease = false;
		if (points_list_size >= 1) {
			deleteTrajectoryLayer();
	        int pointLayerSize = POINT_LAYER_List.size();
			for (PointClass points : POINTS_List) {
				POINT_TYPE pType = points.getPointType();
	        	switch (pType) {
				case TARGET:
					coords[points_list_size-1] = points.getCoordinate();
					break;
				case RELEASE:
					coords[0] = points.getCoordinate();
					hasRelease = true;
					break;
				case WAYPOINT:
					waypointList.add(points);
					break;
				default:
						break;
				}
			}
			
			for (int i = 0; i < waypointList.size(); i++) {
				
				if (hasRelease) {
					coords[i+1] = waypointList.get(i).getCoordinate();
				} else {
					coords[i] = waypointList.get(i).getCoordinate();
				}
			}	
			
			if (points_list_size >= 2) {
				if (hasRelease) {
					POINT_STYLE = StylesClass.getPointStyle(POINT_TYPE.DOT, trajectory_color, trajectory_width, null, null);
					Layer dashedLineLayer = LayerBuilder.createDashedLineLayer(coords[0].y, coords[0].x, coords[1].y, coords[1].x, DOT_FEATURE_TYPE, geometryFactory, POINT_STYLE);
					mapContent.addLayer(dashedLineLayer);
					TRAJECTORY_LAYER_List.add(dashedLineLayer);
				} else {
					LINE_STYLE = StylesClass.getLineStyle(trajectory_color, trajectory_width);
			        Layer layer = LayerBuilder.createPathLayer(new Coordinate[] {coords[0], coords[1]}, TRAJECTORY_LAYER_List.size(), PATH_FEATURE_TYPE, geometryFactory, LINE_STYLE);
					mapContent.addLayer(layer);
					TRAJECTORY_LAYER_List.add(layer);
				}
				
				if (coords.length-2 > 0) {
					Coordinate coords2[] = new Coordinate[coords.length-1];
					for (int i = 0; i < coords.length-1; i++) {
						coords2[i] = coords[i+1];
						
					}
					LINE_STYLE = StylesClass.getLineStyle(trajectory_color, trajectory_width);
			        Layer layer = LayerBuilder.createPathLayer(coords2, TRAJECTORY_LAYER_List.size(), PATH_FEATURE_TYPE, geometryFactory, LINE_STYLE);
					mapContent.addLayer(layer);
					TRAJECTORY_LAYER_List.add(layer);
				}
			}
			mapFrame.getMapPane().moveImage(1, 0);
			mapFrame.getMapPane().moveImage(-1, 0);
		}
	}
	
	/**
	 * Draws rectangle.
	 * <p>
	 * LAT/LON point represents the midpoint of the south side of the rectangle.
	 * 
	 * @param lat
	 * @param lon
	 * @param width 
	 * @param height
	 * @param angle
	 * @param col : Color
	 * 
	 * */
	public void drawRectangle(double lat, double lon, int width, int height, double angle, Color col) {
		height = height * 1000;
		width = width * 1000;
        Coordinate[] coords2  = new Coordinate[5];
        GeodeticCalculator geoCalc = new GeodeticCalculator(DefaultGeographicCRS.WGS84);
		Point2D dest;
		
		geoCalc.setStartingGeographicPoint(lon, lat);
		
		geoCalc.setDirection((270-angle), width/2);
		dest = geoCalc.getDestinationGeographicPoint();
		coords2[0] = new Coordinate(dest.getX(),dest.getY());
        
		geoCalc.setStartingGeographicPoint(coords2[0].getX(),coords2[0].getY());
		geoCalc.setDirection(0-angle, height);
		dest = geoCalc.getDestinationGeographicPoint();
		coords2[1] = new Coordinate(dest.getX(),dest.getY());

		geoCalc.setStartingGeographicPoint(coords2[1].getX(),coords2[1].getY());
		geoCalc.setDirection(90-angle, width);
		dest = geoCalc.getDestinationGeographicPoint();
		coords2[2] = new Coordinate(dest.getX(),dest.getY());
        
		geoCalc.setStartingGeographicPoint(coords2[2].getX(),coords2[2].getY());
		geoCalc.setDirection(180-angle, height);
		dest = geoCalc.getDestinationGeographicPoint();
		coords2[3] = new Coordinate(dest.getX(),dest.getY());
		
		coords2[4] = coords2[0];
        
		LinearRing ring = geometryFactory.createLinearRing(coords2);
		Polygon rect = geometryFactory.createPolygon(ring, null);
		POLYGON_STYLE = StylesClass.getPolygonStyle(col, null, 1);
		Layer layer = LayerBuilder.createCircleLayer(rect, geometryFactory, RECT_FEATURE_TYPE, POLYGON_STYLE);
		mapContent.addLayer(layer);
		RECT_LAYER_List.add(layer);
	}
	
	public void drawLine(double startLat, double startLon, double endLat, double endLon, Color color, float width) {
		LINE_STYLE = StylesClass.getLineStyle(color, width);
		Coordinate[] coords = new Coordinate[2];
		coords[0] = new Coordinate(startLon, startLat);
		coords[1] = new Coordinate(endLon, endLat);
		Layer layer = LayerBuilder.createPathLayer(coords, TRAJECTORY_LAYER_List.size(), PATH_FEATURE_TYPE, geometryFactory, LINE_STYLE);
		mapContent.addLayer(layer);
		LINE_LAYER_List.add(layer);
	}
	
	public void drawDashedLine(double startLat, double startLon, double endLat, double endLon, Color color, float width) {
		POINT_STYLE = StylesClass.getPointStyle(POINT_TYPE.DOT, color, width, null, null);
		Layer layer = LayerBuilder.createDashedLineLayer(startLat, startLon, endLat, endLon, DOT_FEATURE_TYPE, geometryFactory, POINT_STYLE);
		mapContent.addLayer(layer);
		TRAJECTORY_LAYER_List.add(layer);
	}
	
	public void setPointsList(List<PointClass> POINTS_List) {
		this.POINTS_List = POINTS_List;
	}
	
	public List<PointClass> getPointsList() {
		return POINTS_List;
	}

	/**
	 * Calculates angle between two points.
	 * 
	 * */
	public double calculateAngle(double lat1, double lon1, double lat2, double lon2) {
		GeodeticCalculator geoCalc = new GeodeticCalculator(DefaultGeographicCRS.WGS84);
		geoCalc.setStartingGeographicPoint(lon1, lat1);
		geoCalc.setDestinationGeographicPoint(lon2, lat2);
        
        return geoCalc.getAzimuth();
	}
}
