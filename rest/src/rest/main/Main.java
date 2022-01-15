package rest.main;

import java.io.File;

import rest.file.RestFile;
import rest.frame.AppWindow;
import rest.menu.MenuItem;
import rest.menu.Menu;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestFile restFile = new RestFile("deneme.txt");
		File file = restFile.getFile();
		restFile.writeFile(file, "Ahmet\nSalih\nSancak");
		restFile.readFileScannerLine(file);
		Menu menu = new Menu();
		menu.fillMenu(new File("menu.txt"));
		for (MenuItem i : menu.icecekList) {
			System.out.println("isim: " + i.getName() + "	fiyat: " + i.getPrice());
		}
		AppWindow appWindow = new AppWindow();
	}

}
