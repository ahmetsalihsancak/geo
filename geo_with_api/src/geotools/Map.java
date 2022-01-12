package geotools;

import java.io.File;

import org.geotools.data.DataUtilities;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.collection.SpatialIndexFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;

/**
 * Prompts the user for a shapefile and displays the contents on the screen in a map frame.
 *
 * <p>This is the GeoTools Quickstart application used in documentationa and tutorials. *
 */
public class Map {

    public static MapContent map = new MapContent();
	public static JMapFrame mapFrame = new JMapFrame(map);

	public static void StartMap(File file) throws Exception {

        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();
        SimpleFeatureSource cachedSource =
                DataUtilities.source(
                        new SpatialIndexFeatureCollection(featureSource.getFeatures()));

        map.setTitle("Map");

        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        Layer layer = new FeatureLayer(cachedSource, style);
        map.addLayer(layer);

        setDragToolDelay(mapFrame, 100);

        showMap(mapFrame, 1200, 800);
    }
	
    public static void StartMap(String shpPath) throws Exception {

    	File file = new File(shpPath);
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();
        SimpleFeatureSource cachedSource =
                DataUtilities.source(
                        new SpatialIndexFeatureCollection(featureSource.getFeatures()));

        map.setTitle("Map");

        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        Layer layer = new FeatureLayer(cachedSource, style);
        map.addLayer(layer);

        setDragToolDelay(mapFrame, 100);

        showMap(mapFrame, 1200, 800);
    }

     private static void showMap(JMapFrame mapFrame, int width, int height) {
         mapFrame.enableStatusBar(true);
         mapFrame.enableToolBar(true);
         mapFrame.initComponents();
         mapFrame.setSize(width, height);
         mapFrame.setVisible(true);
     }

     private static void setDragToolDelay(JMapFrame mapFrame, int val) {
     	mapFrame.getMapPane().setDragDelay(val);
     }

}