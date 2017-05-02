package vn.com.nsmv.javabean;

import java.util.ArrayList;
import java.util.List;

import vn.com.nsmv.entity.Item;

public class Order {
	private List<Item> allItems = new ArrayList<Item>();

	public List<Item> getAllItems() {
		return allItems;
	}

	public void setAllItems(List<Item> allItems) {
		this.allItems = allItems;
	}
	
	public void addItem(Item item) {
		this.allItems.add(item);
	}
	
	public void removeItem(Item item) {
		this.allItems.remove(item);
	}
}
