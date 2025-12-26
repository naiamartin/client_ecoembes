package client.data;

public class DumpsterDTO {
	private String location;
	private String postal_code;
	private int capacity;
	private int container_number;
	
	public DumpsterDTO() {
	}
	
	public DumpsterDTO(String location, String postal_code, int capacity, int container_number) {
		super();
		this.location = location;
		this.postal_code = postal_code;
		this.capacity = capacity;
		this.container_number = container_number;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getContainer_number() {
		return container_number;
	}
	public void setContainer_number(int container_number) {
		this.container_number = container_number;
	}
	
	
}
