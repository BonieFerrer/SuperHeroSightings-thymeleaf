/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.MySuperHeroSightings.dao;

import com.sg.MySuperHeroSightings.entity.Location;
import com.sg.MySuperHeroSightings.entity.Organization;
import com.sg.MySuperHeroSightings.entity.Power;
import com.sg.MySuperHeroSightings.entity.Super;
import java.util.List;

/**
 *
 * @author Bonie F
 */
public interface SuperDao {
    
    Super getSuperById(int id);
    List<Super> getAllSupers();
    Super addSuper(Super heroVillain);
    void updateSuper(Super heroVillain);
    void deleteSuperById(int id);
    
    List<Super> getSupersByLocation(Location location);
    List<Super> getSupersByOrganization(Organization organization);
    
    List<Location> getLocationsForSuper(int id);
    List<Power> getPowersForSuper(int id);
    List<Organization> getOrganizationsForSuper(int id);
    
    void removePowerForSuper(int superId, int powerId);
    void removeOrganizationForSuper(int superId, int organizationId);
    
}
