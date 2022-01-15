package rest.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

	public List<MenuItem> icecekList;
	
	public Menu() {
		icecekList = new ArrayList<>();
	}
	
	public void fillMenu(File file) {
		try {
			int counter = 0;
			String name = null;
			float price = 0;
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String scannerN = scanner.next();
				if (counter == 0) {
					name = scannerN;
					counter++;
				} else {
					price = Float.parseFloat(scannerN);
					icecekList.add(new MenuItem(name, price));
					counter = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
