package missionapi.classes;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import missionapi.classes.Styles.POINT_TYPE;

public class PointClass {

	private double latitude = 0;
    private double longitude = 0;
    private Point locationPoint;
    private Coordinate coordPoint;
    private int no = -1;
    private POINT_TYPE pointType = POINT_TYPE.DEFAULT;
    private String pointName = "";
	
	public PointClass(double latitude, double longitude,int no, POINT_TYPE pointType, String pointName) {
		this.pointType = pointType;
		this.pointName = pointName;
		setPoint(latitude,longitude,no);
	}

	private void setPoint(double latitude, double longitude, int no) {
     	GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		this.latitude = latitude;
     	this.longitude = longitude;
     	coordPoint = new Coordinate(this.longitude, this.latitude);
     	locationPoint = geometryFactory.createPoint(coordPoint);
     	this.no = no;
	}

	public Point getLocation() {
		return locationPoint;
	}

	public Coordinate getCoordinate() {
		return coordPoint;
	}

	public double getLatitude() {
		return locationPoint.getY();
	}

	public double getLongtitude() {
		return locationPoint.getX();
	}

	public int getPointNo() {
		return no;
	}
	
	public POINT_TYPE getPointType() {
		return this.pointType;
	}
	
	public String getPointName() {
		return this.pointName;
	}
}
