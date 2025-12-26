package client.data;

import java.time.LocalDate;

public class AllocationDTO {
	private Long allocationId;
	private Long dumpsterId;
	private Long plantId;
	private LocalDate date;
	private Long employeeId;
	
	public AllocationDTO() {
	}

	public AllocationDTO(Long allocationId, Long dumpsterId, Long plantId, LocalDate date, Long employeeId) {
		super();
		this.allocationId = allocationId;
		this.dumpsterId = dumpsterId;
		this.plantId = plantId;
		this.date = date;
		this.employeeId = employeeId;
	}


	public Long getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(Long allocationId) {
		this.allocationId = allocationId;
	}

	public Long getDumpsterId() {
		return dumpsterId;
	}

	public void setDumpsterId(Long dumpsterId) {
		this.dumpsterId = dumpsterId;
	}

	public Long getPlantId() {
		return plantId;
	}

	public void setPlantId(Long plantId) {
		this.plantId = plantId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
