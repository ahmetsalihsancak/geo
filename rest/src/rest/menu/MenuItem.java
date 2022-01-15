package rest.menu;

public class MenuItem {

	private String name;
	private float price;
	
	public MenuItem(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	
	public float getPrice() {
		return price;
	}
	
}
