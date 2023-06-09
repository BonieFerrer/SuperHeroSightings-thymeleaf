/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.MySuperHeroSightings.service;

import com.sg.MySuperHeroSightings.dao.LocationDao;
import com.sg.MySuperHeroSightings.entity.Location;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bonie F
 */
@Service
public class LocationService {
    
    
    
    private final LocationDao locationDao;
    
    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
    
    
    public Location getLocationById(int id) {
        Location location = locationDao.getLocationById(id);
        return location;
    }
    
    public List<Location> getAllLocations() {
        List<Location> locations = locationDao.getAllLocations();
        return locations;
    }
    
    public Location addLocation(Location location) {
        Location newLocation = locationDao.addLocation(location);
        return newLocation;
    }
    
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }
    
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }
    
    
}
