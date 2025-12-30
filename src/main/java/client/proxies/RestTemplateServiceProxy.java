package client.proxies;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import client.data.AllocationDTO;
import client.data.CredentialsDTO;
import client.data.DumpsterDTO;
import client.data.RecyclingPlantDTO;

@Component
public class RestTemplateServiceProxy implements IEcoembesServiceProxy {
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${ecoembes.service.url}")
	private String serviceUrl;
	
	private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }
	
	@Override
    public Optional<String> login(String email, String password) {
        String url = serviceUrl + "/auth/login";
        CredentialsDTO credentials = new CredentialsDTO(email, password);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, credentials, String.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

	@Override
	public boolean logout(String token) {
	    String url = serviceUrl + "/auth/logout";
	    HttpEntity<Void> entity = new HttpEntity<>(createHeaders(token));
	    try {
	        restTemplate.postForEntity(url, entity, Void.class);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}

	@Override
	public List<DumpsterDTO> getAllDumpsters(String token) {
		String url = serviceUrl + "/dumpsters/all";
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(token));
        ResponseEntity<DumpsterDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, DumpsterDTO[].class);
        return Arrays.asList(response.getBody());
	}

	@Override
	public DumpsterDTO getDumpsterById(String token, Long id) {
		String url = serviceUrl + "/dumpsters/" + id;
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(token));
		ResponseEntity<DumpsterDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, DumpsterDTO.class);
		return response.getBody();
	}

	@Override
	public boolean addDumpster(String token, String location, String postalCode, int capacity, int containerNumber) {
		String url = serviceUrl + "/dumpsters/add/";
        //UriComponentsBuilder because the server uses @RequestParam
        String finalUrl = UriComponentsBuilder.fromUriString(url)
                .queryParam("location", location)
                .queryParam("postal_code", postalCode)
                .queryParam("capacity", capacity)
                .queryParam("container_number", containerNumber)
                .build()
                .toUriString();

        HttpEntity<Void> entity = new HttpEntity<>(createHeaders(token));
        try {
            restTemplate.postForEntity(finalUrl, entity, Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

	@Override
	public List<DumpsterDTO> getDumpstersByPostalCode(String token, String postalCode) {
		String url = serviceUrl + "/dumpsters/dumpster/" + postalCode;
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(token));
		ResponseEntity<DumpsterDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, DumpsterDTO[].class);
		return Arrays.asList(response.getBody());
	}

	@Override
	public List<RecyclingPlantDTO> getAllRecyclingPlants(String token) {
		String url = serviceUrl + "/ecoembes/plants";
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(token));
		ResponseEntity<RecyclingPlantDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, RecyclingPlantDTO[].class);
		return Arrays.asList(response.getBody());
	}

	@Override
	public Float getPlantCapacity(String token, String plantName) {
	 	String url = serviceUrl + "/ecoembes/plants/" + plantName + "/current_capacity";
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(token));
		ResponseEntity<Float> response = restTemplate.exchange(url, HttpMethod.GET, entity, Float.class);
		return response.getBody();
	}

	@Override
	public boolean assignDumpsterToPlant(String token, List<Long> dumpsterIds, String plantName) {
	    List<RecyclingPlantDTO> plants = getAllRecyclingPlants(token);
	    Long plantId = null;
	    
	    for (RecyclingPlantDTO plant : plants) {
	        if (plant.getPlant_name().equals(plantName)) {
	            plantId = plant.getPlant_id();
	            break;
	        }
	    }
	    
	    if (plantId == null) {
	        System.err.println("Plant not found: " + plantName);
	        return false;
	    }
	    
	    String url = serviceUrl + "/ecoembes/plants/" + plantId + "/assign";
	    
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
	    dumpsterIds.forEach(id -> builder.queryParam("dumpster_ids", id));
	    
	    String finalUrl = builder.build().toUriString();
	    
	    HttpEntity<Void> entity = new HttpEntity<>(createHeaders(token));
	    try {
	        ResponseEntity<Void> response = restTemplate.postForEntity(finalUrl, entity, Void.class);
	        return response.getStatusCode().is2xxSuccessful();
	    } catch (Exception e) {
	        System.err.println("Assign dumpster error: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

    @Override
    public RecyclingPlantDTO getPlantByName(String token, String plantName) {
        List<RecyclingPlantDTO> plants = getAllRecyclingPlants(token);
        for (RecyclingPlantDTO plant : plants) {
            if (plant.getPlant_name().equals(plantName)) {
                return plant;
            }
        }
        return null;
    }

	@Override
	public List<AllocationDTO> getAllAllocations(String token) {
		String url = serviceUrl + "/ecoembes/allocations";
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(token));
		ResponseEntity<AllocationDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, AllocationDTO[].class);
		return Arrays.asList(response.getBody());
	}
	
}