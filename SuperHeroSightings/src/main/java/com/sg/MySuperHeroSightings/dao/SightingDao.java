/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.MySuperHeroSightings.dao;

import com.sg.MySuperHeroSightings.entity.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author BonieF
 */
public interface SightingDao {
    
     Sighting getSightingById(int id);
     List<Sighting>getAllSightings();
     Sighting addSighting(Sighting sighting);
     void updateSighting(Sighting sighting);
     void deleteSightingById(int id);
     
     
     List<Sighting>getSightingsByDate(LocalDate date);
    
}
