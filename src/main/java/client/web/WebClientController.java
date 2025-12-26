package client.web;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import client.proxies.IEcoembesServiceProxy;

@Controller
public class WebClientController {
    private final IEcoembesServiceProxy ecoembesServiceProxy;
    private String token;
    
    public WebClientController(IEcoembesServiceProxy ecoembesServiceProxy) {
        this.ecoembesServiceProxy = ecoembesServiceProxy;
    }
    
    @GetMapping("/")
    public String index() {
        return "login"; // login.html
    }
    
    @GetMapping("/login")
    public String showLogin() {
        return "login"; // login.html
    }

    // Added mapping for /index so redirect after successful login resolves to a controller view
    @GetMapping("/index")
    public String showIndex(Model model) {
        if (token == null || token.isEmpty()) {
            return "redirect:/login";
        }
        return "index"; // index.html
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        Optional<String> tokenOpt = ecoembesServiceProxy.login(email, password);
        if (tokenOpt.isPresent()) {
            this.token = tokenOpt.get();
            return "redirect:/index"; // Redirect to dashboard after login
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login"; // Show login page with error
        }
    }
    
    
    @GetMapping("/dumpsters/add")
    public String showAddDumpster() {
        return "add_dumpster"; 
    }
    
    @PostMapping("/dumpsters/add")
    public String addDumpster(@RequestParam("location") String location, @RequestParam("postalCode") String postalCode, 
            @RequestParam("capacity") int capacity, @RequestParam("containerNumber") int containerNumber, Model model) {
        if (token == null || token.isEmpty()) {
            return "redirect:/login";
        }
        boolean success = ecoembesServiceProxy.addDumpster(token, location, postalCode, capacity, containerNumber);
        if (success) {
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Failed to add dumpster");
            return "add_dumpster";
        }
    }
    
    @GetMapping("/assign")
    public String showAssignment() {
        return "assignment"; 
    }
    
    @PostMapping("/assign")
    public String assignDumpsters(@RequestParam("dumpsterIds") String dumpsterIds, @RequestParam("plantId") Long plantId, Model model) {
        if (token == null || token.isEmpty()) {
            return "redirect:/login";
        }
        
        try {
            // Convert comma-separated string to List<Long>
            List<Long> ids = Arrays.stream(dumpsterIds.split(","))
                                   .map(String::trim)
                                   .map(Long::parseLong)
                                   .collect(Collectors.toList());
            
            boolean success = ecoembesServiceProxy.assignDumpsterToPlant(token, ids, plantId);
            if (success) {
                model.addAttribute("message", "Assignment done!");
                return "redirect:/index";
            } else {
                model.addAttribute("error", "Failed to assign dumpsters");
                return "assignment";
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid dumpster IDs format");
            return "assignment";
        }
    }

    // Simple view endpoints to avoid 404 when clicking links on the dashboard
    @GetMapping("/dumpsters/view")
    public String viewDumpsters() {
        if (token == null || token.isEmpty()) return "redirect:/login";
        return "index"; // placeholder - no dedicated template provided
    }

    @GetMapping("/plants/view")
    public String viewPlants() {
        if (token == null || token.isEmpty()) return "redirect:/login";
        return "index"; // placeholder
    }

    @GetMapping("/allocations/view")
    public String viewAllocations() {
        if (token == null || token.isEmpty()) return "redirect:/login";
        return "index"; // placeholder
    }

    @GetMapping("/logout")
    public String logout() {
        if (token != null && !token.isEmpty()) {
            ecoembesServiceProxy.logout(token);
            token = null;
        }
        return "redirect:/login";
    }
    
}