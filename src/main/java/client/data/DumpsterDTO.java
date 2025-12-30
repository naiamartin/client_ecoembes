package client.data;

public class DumpsterDTO {
    private Long dumpster_id;
    private String location;
    private String postal_code;
    private FillLevel fill_level;
    private int container_number;
    private int capacity; 

    public enum FillLevel {
        GREEN, ORANGE, RED
    }
    
    public DumpsterDTO() {}

    public DumpsterDTO(Long dumpster_id, String location, String postal_code, FillLevel fill_level, int container_number, int capacity) {
        this.dumpster_id = dumpster_id;
        this.location = location;
        this.postal_code = postal_code;
        this.fill_level = fill_level;
        this.container_number = container_number;
        this.capacity = capacity;
    }

    public Long getDumpster_id() {
        return dumpster_id;
    }

    public void setDumpster_id(Long dumpster_id) {
        this.dumpster_id = dumpster_id;
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

    public FillLevel getFill_level() {
        return fill_level;
    }

    public void setFill_level(FillLevel fill_level) {
        this.fill_level = fill_level;
    }

    public int getContainer_number() {
        return container_number;
    }

    public void setContainer_number(int container_number) {
        this.container_number = container_number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}