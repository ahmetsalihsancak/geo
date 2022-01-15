package rest.customer;

import java.util.ArrayList;
import java.util.List;

import rest.menu.MenuItem;

public class Customer {

	int no;
	List<MenuItem> itemList;
	float totalPrice;
	
	public Customer(int no) {
		this.no = no;
		itemList = new ArrayList<MenuItem>();
		totalPrice = 0;
	}
	
	public void addMenuItem(MenuItem item) {
		itemList.add(item);
	}
	
	private float calculatePrice() {
		for (MenuItem i : itemList) {
			totalPrice = totalPrice + i.getPrice();
		}
		return totalPrice;
	}
	
	public float getTotalPrice() {
		return calculatePrice();
	}
}
