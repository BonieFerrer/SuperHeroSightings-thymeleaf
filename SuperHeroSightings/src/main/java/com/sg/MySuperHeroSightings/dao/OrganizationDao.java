/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.MySuperHeroSightings.dao;

import com.sg.MySuperHeroSightings.entity.Organization;
import java.util.List;

/**
 *
 * @author BonieF
 */
public interface OrganizationDao {
    
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization organization);
    void updateOrganization(Organization organization);
    void deleteOrganizationById(int id);    
    
    
}
