package missionapi.start;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.geotools.map.MapContent;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;

import missionapi.MissionAPI;
import missionapi.classes.Map;
import missionapi.classes.PointClass;
import missionapi.classes.Styles.POINT_TYPE;

public class MissionAPIMap {

	private static JMapFrame mapFrame;
	private static MapContent mapContent;
	private static String shapefilePath = "C:\\Users\\ahmet\\ecws\\geo\\bin\\ne_10m_admin_0_countries.shp";
	private static final DirectPosition2D ankaraLocation = new DirectPosition2D(39.925533,32.866287);
	private static DirectPosition2D centerLocation;
	
	public static MissionAPI API;
	
	private static Graphics2D created_graphics;
	private static List<PointClass> points = new ArrayList<PointClass>();
	
	private static DefaultTableModel tableModel;
	private static Frame1 frame1;
	
	private static ScheduledExecutorService afterDrawTaskExecutor = Executors.newSingleThreadScheduledExecutor();
	
	private static void TEST() {
		API.deleteAllLayersAndPoints();
		API.addReleasePoint(35, 35, Color.MAGENTA); // Ýsimsiz Release Point
		API.drawIRLAR(42, 28, new Color(252, 207, 3), 100); //100km çaplý IR LAR
		API.addWaypoint(42, 28, Color.GREEN); // Ýsimsiz waypoint
		API.addWaypoint(42, 30, Color.RED); // Ýsimsiz waypoint
		API.drawText("Ahmet Salih Sancak", 42, 30, Color.ORANGE, "Georgia", 12); // Text
		API.drawIZLAR(33, 33, Color.RED, 1, 150, 25, 0); // 0 derece 150km-25km IZ LAR
		API.addTarget(37, 39, Color.RED); // Ýsimsiz target
		API.drawTrajectory(Color.BLUE, 2); // Class içerisinde kaydedilen noktalarýn rotasý
		afterDraw();
	}
	
	private static void TEST2() {
		API.deleteAllLayersAndPoints();
		API.addReleasePoint(35, 35, "RELEASE", Color.RED, "Times New Roman", 10); // Ýsimli release
		API.addWaypoint(42, 28, Color.RED);
		API.addWaypoint(42, 30, "WAYPOINT", Color.RED, "Georgia", 10); // Ýsimli waypoint
		API.addWaypoint(41, 32, Color.RED);
		API.addTarget(37, 39, "Target", Color.RED, "Georgia", 12); // Ýsimli target
		API.drawTrajectory(Color.BLUE, 2);
		API.drawIRLAR(37, 39, Color.BLUE, 300); // 300km IR LAR
		API.drawIZLAR(33, 35, Color.BLACK, 2, 150, 25, 45); // 45 derece
		API.drawIZLAR(28, 35, Color.BLUE, 2, 150, 25, 32); // 32 derece
		API.drawIZLAR(24, 35, Color.ORANGE, 2, 150, 25, 111); // 111 derece
		afterDraw();
	}
	
	private static void TEST3() {
		API.deleteAllLayersAndPoints();
		List<PointClass> points = new ArrayList<PointClass>();
		points.add(new PointClass(35, 35, 0, POINT_TYPE.RELEASE, "RELEASE")); // isimli release
		points.add(new PointClass(42, 28, 1, POINT_TYPE.WAYPOINT, "")); // isimsiz waypoint
		points.add(new PointClass(42, 30, 2, POINT_TYPE.WAYPOINT, "WAYPOINT")); // isimli waypoint
		points.add(new PointClass(41, 32, 3, POINT_TYPE.WAYPOINT, ""));
		points.add(new PointClass(37, 39, 4, POINT_TYPE.TARGET, ""));
		API.drawTrajectory(points, Color.BLUE, 2); // noktalarýn çizdirilmesi
		afterDraw();
	}
	
	private static void TEST4() {
		API.deleteAllLayersAndPoints();
		API.drawRectangle(40, 33, 100, 50, 0, Color.BLUE); // 100x50km 0 derece dikdörtgen
		API.drawRectangle(40, 43, 22, 250, 0, Color.BLUE); // 22x250km 0 derece dikdörtgen
		API.drawRectangle(38, 33, 100, 50, 45, Color.ORANGE); // 100x50km 45 derece dikdörtgen
		API.drawRectangle(36, 33, 100, 50, 103, Color.MAGENTA); // 100x50km 103 derece dikdörtgen
		API.drawLine(42, 28, 39, 40, Color.BLACK, 2);
		double lat[] = {41.34345,	41.4343,	41.43344,	41.54343,	41.68343443};
		double lon[] = {35.05434,	36.003434,	36.27343,	36.543343,	36.894333};
		API.drawTrajectory(lat, lon, Color.RED, Color.RED, 2);
		afterDraw();
	}
	
	private static void TEST_MISSION() {
		double lat[] = {41.34345,	41.4343,	41.43344,	41.54343,	41.68343443};
		double lon[] = {35.05434,	36.003434,	36.27343,	36.543343,	36.894333};
		API.deleteAllLayersAndPoints();
		API.addReleasePoint(lat[0], lon[0], Color.RED);
		API.addWaypoint(lat[1], lon[1], Color.RED);
		API.addWaypoint(lat[2], lon[2], Color.RED);
		API.addWaypoint(lat[3], lon[3], Color.RED);
		API.addTarget(lat[4], lon[4], Color.RED);
		API.drawTrajectory(Color.RED, 2);
		API.drawText("Salma", lat[0]+0.1, lon[0], Color.RED, "Georgia", 12);
		API.drawText("YN1", lat[1]-0.1, lon[1], Color.RED, "Georgia", 12);
		API.drawText("YN2", lat[2]+0.1, lon[2], Color.RED, "Georgia", 12);
		API.drawText("TBYN", lat[3]+0.1, lon[3], Color.RED, "Georgia", 12);
		API.drawText("Hedef", lat[4]-0.1, lon[4], Color.RED, "Georgia", 12);
		API.drawIRLAR(lat[4], lon[4], Color.BLUE, 300);
		API.drawIZLAR(lat[0], lon[0], Color.RED, 1, 100, 15, 
				API.calculateAngle(lat[0], lon[0], lat[1], lon[1]));
		afterDraw();
	}
	
	public static void StartApp(File file) {
		try {
			setShapefilePath(file.getAbsolutePath());
			beforeStart();
			Map.StartMap(file);
			afterStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setCenterLocation(DirectPosition2D loc) {
		centerLocation = loc;
	}
	
	public static DirectPosition2D getCenterLocation() {
		return centerLocation;
	}
	
	public static void setShapefilePath(String path) {
		shapefilePath = path;
	}
	
	public static String getShapefilePath() {
		return shapefilePath;
	}
	
	public static void setStartDisplayArea(DirectPosition2D centerPos, int scale) {
		Envelope2D newMapArea = new Envelope2D();

        DirectPosition2D corner =
                new DirectPosition2D(
                        centerPos.getX() - 0.5d * 600 / scale,
                        centerPos.getY() + 0.5d * 800 / scale);

        newMapArea.setFrameFromCenter(centerPos, corner);
		mapFrame.getMapPane().setDisplayArea(newMapArea);
	}
	
	public static DirectPosition2D getWorldPointToScreen(DirectPosition2D pos) {
   	 	AffineTransform tr = mapFrame.getMapPane().getWorldToScreenTransform();
        tr.transform(pos, pos);
        pos.setCoordinateReferenceSystem(mapFrame.getMapPane().getMapContent().getCoordinateReferenceSystem());
        return pos;
	}
	
	private static void beforeStart() {
		centerLocation = ankaraLocation;
		mapFrame = Map.mapFrame;
		mapContent = Map.map;
	}
	
	private static void afterStart() {
		setStartDisplayArea(centerLocation,25);
		createButtonsOnMapToolbar();
		API = new MissionAPI(mapContent, mapFrame);
		frame1 = new Frame1();
		frame1.setVisible(true);
		tableModel = frame1.getTableModel();
	}
	
	private static void afterDraw() {
		mapFrame.getMapPane().moveImage(1, 0);
		afterDrawTaskExecutor.schedule(
            () -> {
        		mapFrame.getMapPane().moveImage(-1, 0);
            },
            100,
            TimeUnit.MILLISECONDS);
	}
	
	private static void createButtonsOnMapToolbar() {
		deleteButton();
		testButton();
		test2Button();
		test3Button();
		test4Button();
		testMissionButton();
		relocateButton();
	}
	
	private static void deleteButton() {
		
		JButton btn = new JButton("DELETE ALL");
		JToolBar toolBar = mapFrame.getToolBar();
        toolBar.addSeparator();
        toolBar.add(btn);
        
        btn.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
      			API.deleteAllLayersAndPoints();
      		}
        });
        
	}
	
	private static void testButton() {
		
		JButton btn = new JButton("TEST");
		JToolBar toolBar = mapFrame.getToolBar();
        toolBar.addSeparator();
        toolBar.add(btn);
        
        btn.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
    			TEST();
      		}
        });
	}
	
	private static void test2Button() {
		
		JButton btn = new JButton("TEST2");
		JToolBar toolBar = mapFrame.getToolBar();
        toolBar.addSeparator();
        toolBar.add(btn);
        
        btn.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
    			TEST2();
      		}
        });
	}
	
	private static void test3Button() {
		
		JButton btn = new JButton("TEST3");
		JToolBar toolBar = mapFrame.getToolBar();
        toolBar.addSeparator();
        toolBar.add(btn);
        
        btn.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
    			TEST3();
      		}
        });
	}
	
	private static void test4Button() {
		
		JButton btn = new JButton("TEST4");
		JToolBar toolBar = mapFrame.getToolBar();
        toolBar.addSeparator();
        toolBar.add(btn);
        
        btn.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
    			TEST4();
      		}
        }); 
	}
	
	private static void testMissionButton() {
		
		JButton btn = new JButton("TEST MISSION");
		JToolBar toolBar = mapFrame.getToolBar();
        toolBar.addSeparator();
        toolBar.add(btn);
        
        btn.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
    			TEST_MISSION();
      		}
        }); 
	}
	/**
	 * Nokta relocate iþlemini point_no deðerine göre yapmaktadýr.
	 * Noktalar sýralý halde deðilse çalýþmamaktadýr.
	 * 
	 * */
	private static void relocateButton() {

		JButton btn = new JButton("RELOCATE");
		JToolBar toolBar = mapFrame.getToolBar();
        toolBar.addSeparator();
        toolBar.add(btn);

        btn.addActionListener(new ActionListener() {
        	boolean graphicF = false;
      		@Override
      		public void actionPerformed(ActionEvent e) {
      			if (!graphicF) {
      				graphicF = true;
		        	created_graphics = (Graphics2D) mapFrame.getMapPane().getGraphics().create();
		        	created_graphics.setColor(Color.WHITE);
		        	created_graphics.setXORMode(Color.BLACK);
      				}
      			}
      		});

        btn.addActionListener(e -> mapFrame.getMapPane().setCursorTool(new CursorTool() {

        	private boolean dragged = false;
        	private boolean enabled = false;
        	private int xPos[] = new int[3];
        	private int yPos[] = new int[3];
        	private int lineSize;
        	private int lineCode;
        	private java.awt.Point posDevice;
        	private java.awt.Point wheelPos;
        	private int pointNo;
        	private POINT_TYPE pointType;
        	private String pointName;
        	private boolean readyToPrint = true;
        	private boolean wheel = false;
        	private double relocateVal = 0.1;
        	private ScheduledExecutorService wheelTaskExecutor = Executors.newSingleThreadScheduledExecutor();
        	
        	
        	public void onMouseWheelMoved(MapMouseEvent ev) {
        		wheel = enabled;
        		wheelPos = ev.getPoint();
        		enabled = false;
                if (wheel) {
            		created_graphics.dispose();
            		lineCode = getLineCode(points.size(), pointNo , wheelPos);
               	 	updateLinePoints(lineCode, wheelPos);
               	 	DirectPosition2D worldPos = ev.getWorldPos();
                    updateTable(worldPos, pointNo, 3);
                	wheelTaskExecutor.schedule(
                            () -> {
                                afterWheel();
                            	enabled = true;
                            },
                            100,
                            TimeUnit.MILLISECONDS);
				}
        	}
        	
             @Override
			public void onMousePressed(MapMouseEvent ev) {
            	 wheel = false;
            	 if (SwingUtilities.isLeftMouseButton(ev) && readyToPrint) {
            		 DirectPosition2D worldPos = ev.getWorldPos();
                	 posDevice = ev.getPoint();
                	 points = API.getPointsList();
                	 for (PointClass point : points) {
                		 if((!((worldPos.x - point.getLongtitude() > relocateVal) ||
                    			 (worldPos.x - point.getLongtitude() < -relocateVal)))
                    			 && (!((worldPos.y - point.getLatitude() > relocateVal) ||
        		            			 (worldPos.y - point.getLatitude() < -relocateVal))) && (points.size() > 1)) {
                			 pointNo = point.getPointNo();
                			 pointType = point.getPointType();
                			 pointName = point.getPointName();
                			 lineCode = getLineCode(points.size(), pointNo , posDevice);
                			 enabled = true;
                			 readyToPrint = false;
                			 break;
                		 } else {
                        	 enabled = false;
                			 readyToPrint = true;
                    	 }
    				}
             	 }
            	 if (SwingUtilities.isRightMouseButton(ev)) {
 	                mapFrame.getMapPane().repaint();
 	                enabled = false;
 	                dragged = false;
 	                readyToPrint = true;
            	 }
             }

             @Override
			public void onMouseDragged(MapMouseEvent ev) {
            	 if (enabled) {
            		 if (wheel) {
             			wheel = false;
             			created_graphics.fillRect(wheelPos.x - 2, wheelPos.y - 2, 4, 4);
             			created_graphics.drawPolyline(xPos, yPos, lineSize);
                 		created_graphics.dispose();
            			dragged = false;
                   	 	createGraphics2D();
            		 }
                     if (dragged) {
                    	 created_graphics.fillRect(posDevice.x - 2, posDevice.y - 2, 4, 4);
                    	 created_graphics.drawPolyline(xPos, yPos, lineSize);
                     }
                     posDevice = ev.getPoint();
                     created_graphics.fillRect(posDevice.x - 2, posDevice.y - 2, 4, 4);
                	 updateLinePoints(lineCode, posDevice);
                	 DirectPosition2D worldPos = ev.getWorldPos();
                	 created_graphics.drawPolyline(xPos, yPos, lineSize);
	                 updateTable(worldPos, pointNo, 3);
                     dragged = true;
                 } 
             }

             @Override
             public void onMouseReleased(MapMouseEvent ev) {
            	 if (dragged && enabled) {
	                 dragged = false;
	                 enabled = false;
	                 DirectPosition2D worldPos = ev.getWorldPos();
	                 mapFrame.getMapPane().repaint();
	                 points.remove(pointNo);
		             PointClass PC = new PointClass(worldPos.y, worldPos.x,pointNo, pointType, pointName);
		             points.add(pointNo,PC);
		             API.drawTrajectory(points, Color.RED, 1);
					 readyToPrint = true;
                	 created_graphics.dispose();
                	 createGraphics2D();
				}
            	 enabled = false;
             }
             
             private void afterWheel() {
            	 createGraphics2D();
            	 created_graphics.fillRect(wheelPos.x - 2, wheelPos.y - 2, 4, 4);
            	 created_graphics.drawPolyline(xPos, yPos, lineSize);
             }
             
             private void createGraphics2D() {
            	 	created_graphics = (Graphics2D) mapFrame.getMapPane().getGraphics().create();
            	 	created_graphics.setColor(Color.WHITE);
            	 	created_graphics.setXORMode(Color.BLACK);
             }

             private int getLineCode(int total_points, int point_no, java.awt.Point ev) {
            	 return getLinePoints(total_points, point_no, ev);
             }

             /**
              * Calculates the required line points to draw lines and returns line code.
              *
              * @param total_points
              * @param point_no
              * @param ev
              * @return line code for updating lines
              */
             private int getLinePoints(int total_points, int point_no, java.awt.Point ev) {
            	 DirectPosition2D pos;
            	 if (point_no == 0) {
            		xPos[0] = ev.x;
             	 	yPos[0] = ev.y;
             	 	pos = getWorldPointToScreen(new DirectPosition2D(points.get(point_no+1).getCoordinate().x,points.get(point_no+1).getCoordinate().y));
             	 	xPos[1] = (int)pos.x;
             	 	yPos[1] = (int)pos.y;
             	 	xPos[2] = 0;
             	 	yPos[2] = 0;
             	 	lineSize = 2;
             	 	return 0;
            	 } else if (total_points == (point_no+1)) {
            		pos = getWorldPointToScreen(new DirectPosition2D(points.get(point_no-1).getCoordinate().x,points.get(point_no-1).getCoordinate().y));
             	 	xPos[0] = (int)pos.x;
             	 	yPos[0] = (int)pos.y;
					xPos[1] = ev.x;
             	 	yPos[1] = ev.y;
             	 	xPos[2] = 0;
             	 	yPos[2] = 0;
             	 	lineSize = 2;
             	 	return 1;
				} else {
					pos = getWorldPointToScreen(new DirectPosition2D(points.get(point_no-1).getCoordinate().x,points.get(point_no-1).getCoordinate().y));
            	 	xPos[0] = (int)pos.x;
            	 	yPos[0] = (int)pos.y;
            	 	xPos[1] = ev.x;
            	 	yPos[1] = ev.y;
            	 	pos = getWorldPointToScreen(new DirectPosition2D(points.get(point_no+1).getCoordinate().x,points.get(point_no+1).getCoordinate().y));
            	 	xPos[2] = (int)pos.x;
            	 	yPos[2] = (int)pos.y;
            	 	lineSize = 3;
            	 	return 1;
				}
             }

             /**
              * Updates required point that used in drawing.
              *
              * @param point_code
              * @param ev
              */
             private void updateLinePoints(int point_code, java.awt.Point ev) {
            	 switch (point_code) {
				case 0:
					xPos[0] = ev.x;
					yPos[0] = ev.y;
					break;

				case 1:
					xPos[1] = ev.x;
					yPos[1] = ev.y;
					break;
				}
             }

             /**
              * Updates table
              *
              * @param world_pos
              * @param point_no
              */
             private void updateTable(DirectPosition2D world_pos, int point_no, int index) {
            	 tableModel.setValueAt(world_pos.y,point_no,index);
            	 tableModel.setValueAt(world_pos.x,point_no,index+1);
             }
         }));
	}
}
