package rest.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class RestFile {

	private File file;
	
	public RestFile(String fileName) {
		createFile(fileName);
	}
	
	private void createFile(String name) {

		file = new File(name);
		try {
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("dosya oluþtu	" + file.getName());
			} else {
				System.out.println("dosya var	" + file.getName());
			}
			System.out.println(file.getAbsolutePath());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void deleteFile() {
		
	}
	
	public void readFile(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void readFileScannerLine(File file) {
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void readFileScanner(File file) {
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				System.out.println(scanner.next());
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void writeFile(File file, String text) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(text.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public File getFile() {
		return this.file;
	}
}
