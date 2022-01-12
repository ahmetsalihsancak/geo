package missionapi.start;

import java.awt.EventQueue;
import java.io.File;

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
				MissionAPIMap.StartApp(file);
			}
		});
	}
}
