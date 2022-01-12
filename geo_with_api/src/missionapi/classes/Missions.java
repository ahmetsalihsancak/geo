package missionapi.classes;

import java.util.ArrayList;
import java.util.List;

import missionapi.classes.Styles.POINT_TYPE;

public class Missions {

	public enum MISSIONS {
		M1,
		M2,
		M3
	}
	
	public List<PointClass> getMission(MISSIONS mType) {
		List<PointClass> points = new ArrayList<PointClass>();
		switch (mType) {
		case M1:
			points.add(new PointClass(35.2312, 35.00343, 0, POINT_TYPE.RELEASE, "RELEASE"));
			points.add(new PointClass(42.1231, 28.872437, 1, POINT_TYPE.WAYPOINT, "WAYPOINT")); 
			points.add(new PointClass(42.12331, 30.22, 2, POINT_TYPE.WAYPOINT, "WAYPOINT")); 
			points.add(new PointClass(41.36243, 32.34535, 3, POINT_TYPE.WAYPOINT, "WAYPOINT"));
			points.add(new PointClass(37.8724387, 39.000096364, 4, POINT_TYPE.TARGET, "TARGET"));
			return points;
		case M2:
			double lat[] = {41.34345,	41.4343,	41.43344,	41.54343,	41.68343443};
			double lon[] = {35.05434,	36.003434,	36.27343,	36.543343,	36.894333};
			points.add(new PointClass(lat[0], lon[0], 0, POINT_TYPE.RELEASE, "R"));
			points.add(new PointClass(lat[1], lon[1], 1, POINT_TYPE.WAYPOINT, "W1")); 
			points.add(new PointClass(lat[2], lon[2], 2, POINT_TYPE.WAYPOINT, "")); 
			points.add(new PointClass(lat[3], lon[3], 3, POINT_TYPE.WAYPOINT, "W2"));
			points.add(new PointClass(lat[4], lon[4], 4, POINT_TYPE.TARGET, ""));
			return points;
		case M3:
			double lat1[] = {41.34345,	41.4343,	41.43344,	41.54343,	41.68343443};
			double lon1[] = {34.05434,	34.003434,	34.27343,	34.543343,	34.894333};
			points.add(new PointClass(lat1[0], lon1[0], 0, POINT_TYPE.RELEASE, ""));
			points.add(new PointClass(lat1[1], lon1[1], 1, POINT_TYPE.WAYPOINT, "")); 
			points.add(new PointClass(lat1[2], lon1[2], 2, POINT_TYPE.WAYPOINT, "")); 
			points.add(new PointClass(lat1[3], lon1[3], 3, POINT_TYPE.WAYPOINT, ""));
			points.add(new PointClass(lat1[4], lon1[4], 4, POINT_TYPE.TARGET, ""));
			return points;
		default:
			return null;
		}
	}
	
}
