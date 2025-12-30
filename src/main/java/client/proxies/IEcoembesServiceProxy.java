package client.proxies;

import java.util.List;
import java.util.Optional;

import client.data.AllocationDTO;
import client.data.DumpsterDTO;
import client.data.RecyclingPlantDTO;

public interface IEcoembesServiceProxy {
	/* AUTH CONTROLLER - Ecoembes */
	Optional<String> login(String username, String password);
	boolean logout(String token);
	
	/* DUMPSTER CONTROLLER - Ecoembes */
	List<DumpsterDTO> getAllDumpsters(String token);
	DumpsterDTO getDumpsterById(String token, Long id);
	boolean addDumpster(String token, String location, String postalCode, int capacity, int containerNumber);
	List<DumpsterDTO> getDumpstersByPostalCode(String token, String postalCode);
	
	/* ECOEMBES CONTROLLER - Ecoembes */
	List<RecyclingPlantDTO> getAllRecyclingPlants(String token);
	Float getPlantCapacity(String token, String plantName);
	boolean assignDumpsterToPlant(String token, List<Long> dumpsterIds, String plantName);
    RecyclingPlantDTO getPlantByName(String token, String plantName);
	List<AllocationDTO> getAllAllocations(String token);
}
