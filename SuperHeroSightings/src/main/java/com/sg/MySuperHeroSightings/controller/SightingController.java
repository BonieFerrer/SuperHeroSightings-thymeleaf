/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.MySuperHeroSightings.controller;

import com.sg.MySuperHeroSightings.dao.SightingDao;
import com.sg.MySuperHeroSightings.entity.Location;
import com.sg.MySuperHeroSightings.entity.Sighting;
import com.sg.MySuperHeroSightings.entity.Super;
import com.sg.MySuperHeroSightings.service.LocationService;
import com.sg.MySuperHeroSightings.service.SightingService;
import com.sg.MySuperHeroSightings.service.SuperService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Bonie F
 */
@Controller
public class SightingController {
    
    private final SightingDao sightingDao;
    private final SuperService superService;
    private final LocationService locationService;
    private final SightingService sightingService;

    public SightingController(SightingDao sightingDao, SuperService superService, LocationService locationService, SightingService sightingService) {
        this.sightingDao = sightingDao;
        this.superService = superService;
        this.locationService = locationService;
        this.sightingService = sightingService;
    }
    
 @GetMapping("sightings") //Go to sightings html page
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingService.getAllSightings();
        List<Super> supers = superService.getAllSupers();
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locations);

        model.addAttribute("sighting", model.getAttribute("sighting") != null ? model.getAttribute("sighting") : new Sighting());
        
        return "sightings"; //returning "sightings" means we will need a sightings.html file to push our data to
    }
    
    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {        
        String superId = request.getParameter("super_id");
        String locationId = request.getParameter("location_id");        
        sighting.setSuperId(Integer.parseInt(superId));
        sighting.setLocationId(Integer.parseInt(locationId)); 
        
        if(result.hasErrors()){    
            return displaySightings(model);
        }
        sightingDao.addSighting(sighting);        
        return "redirect:/sightings";
    }
    
    @GetMapping("detailSighting") //Go to detailSighting html page
    public String detailSighting(Integer id, Model model) {
        Sighting sighting = sightingService.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "detailSighting";
    }
    
    @GetMapping("displayDeleteSighting") //Go to deleteSighting html page for confirmation
    public String displayDeleteSighting(Integer id, Model model) { 
        Sighting sighting = sightingService.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "deleteSighting";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingService.deleteSightingById(id);
        return "redirect:/sightings";
    }  
        
    @GetMapping("editSighting") //Go to editSighting html page
    public String editSighting(Integer id, Model model) {
        List<Super> supers = superService.getAllSupers();
        List<Location> locations = locationService.getAllLocations();
        Sighting sighting = sightingService.getSightingById(id);
        
        if(model.getAttribute("sighting") != null) {
            ((Sighting) model.getAttribute("sighting")).setSuperId(sighting.getSuperId());
            ((Sighting) model.getAttribute("sighting")).setLocationId(sighting.getLocationId());
        }
        model.addAttribute("sighting", model.getAttribute("sighting") != null ? model.getAttribute("sighting") : sighting);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locations); 
        model.addAttribute("currentDate", sighting.date);            
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) { 
        String superId = request.getParameter("super_id");
        String locationId = request.getParameter("location_id");        
        sighting.setSuperId(Integer.parseInt(superId));
        sighting.setLocationId(Integer.parseInt(locationId)); 
        
        if(result.hasErrors()){  
            return editSighting(sighting.getId(), model);
        } 
        
        sightingService.updateSighting(sighting);
        return "redirect:/detailSighting?id=" + sighting.getId();
    }
    
//  INDEX PAGE - DISPLAY 10 MOST RECENT SIGHTINGS
    @GetMapping("/") //Go to index html page
    public String recentSightings(Model model) {
        List<Sighting> sightings = sightingService.getAllSightings();      
        
        List<Sighting> recentSightings = sightings.stream()
                .sorted(Comparator.comparing(Sighting::getDate).reversed()) //order by date from most recent to oldest
                .limit(10) //get the 10 first sightings
                .collect(Collectors.toList());        

        model.addAttribute("sightings", recentSightings);
        
        return "index.html"; //returning "sightings" means we will need a sightings.html file to push our data to
    }
    
    
     
    
}
