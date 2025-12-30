package client.data;

public class RecyclingPlantDTO {
	private Long plant_id;
	private String plant_name;
	private float current_capacity;
	private float total_capacity;
	
	public RecyclingPlantDTO() {
	}
	
	public RecyclingPlantDTO(Long plant_id,String plant_name, float current_capacity,float total_capacity) {
		super();
		this.plant_name = plant_name;
		this.current_capacity = current_capacity;
		this.total_capacity = total_capacity;
		this.plant_id = plant_id;
	}

	public String getPlant_name() {
		return plant_name;
	}

	public void setPlant_name(String plant_name) {
		this.plant_name = plant_name;
	}

	public float getCurrent_capacity() {
		return current_capacity;
	}

	public void setCurrent_capacity(float current_capacity) {
		this.current_capacity = current_capacity;
	}
	public float getTotal_capacity() {
		return total_capacity;
	}
	public void setTotal_capacity(float total_capacity) {
		this.total_capacity = total_capacity;
	}
	public Long getPlant_id() {
		return plant_id;
	}
	public void setPlant_id(Long plant_id) {
		this.plant_id = plant_id;
	}
}