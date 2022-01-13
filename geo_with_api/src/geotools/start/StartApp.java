package geotools.start;

import java.awt.EventQueue;
import java.io.File;

import geotools.APIMapConfig;

public class StartApp {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(classLoader.getResource("ne_10m_admin_0_countries.shp").getFile());
				File rasterFile = new File(classLoader.getResource("MOS_CZ_GK3_250.tif").getFile());
				APIMapConfig.StartApp(file, rasterFile);
			}
		});
	}
}
