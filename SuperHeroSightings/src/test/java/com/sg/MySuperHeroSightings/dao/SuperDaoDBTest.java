/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.MySuperHeroSightings.dao;

import com.sg.MySuperHeroSightings.entity.Location;
import com.sg.MySuperHeroSightings.entity.Organization;
import com.sg.MySuperHeroSightings.entity.Power;
import com.sg.MySuperHeroSightings.entity.Sighting;
import com.sg.MySuperHeroSightings.entity.Super;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Bonie F
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperDaoDBTest {
    
    //Autowire in the DAO classes:
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    PowerDao powerDao;
    
    @Autowired
    SightingDao sightingDao;
            
    @Autowired
    SuperDao superDao;
    
    public SuperDaoDBTest() {  
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        
        List<Power> powers = powerDao.getAllPowers();
        for(Power power : powers) {
            powerDao.deletePowerById(power.getId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
        
        List<Super> supers = superDao.getAllSupers();
        for(Super hero_villain : supers) {
            superDao.deleteSuperById(hero_villain.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    
    /**     --------------------------- TESTS ---------------------------.     */
    /**
     * Test of addAndGetSuperById method, of class SuperDaoDB.
     */
    @Test
    public void testAddAndGetSuperById() {
        Organization organization = new Organization(); //Create Organization
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization); //save organization
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Power power = new Power(); //Create Power
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power); //save power
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        Location location = new Location(); //Create a location
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(45.501690);
        location.setLongitude(-73.567253);
        location = locationDao.addLocation(location); //save location
        List<Location> locations = new ArrayList<>();
        locations.add(location);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");  
        heroVillain.setLocations(locations); //add locations list
        heroVillain.setOrganizations(organizations); //add organizations list
        heroVillain.setPowers(powers); //add powers list
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        Sighting sighting = new Sighting(); //Create a sighting
        sighting.setDate(LocalDate.now());
        sighting.setLocationId(location.getId());
        sighting.setSuperId(heroVillain.getId());        
        sightingDao.addSighting(sighting); //save sighting
        
        Super heroVillainFromDao = superDao.getSuperById(heroVillain.getId()); //get the saved super from db
        assertEquals(heroVillain, heroVillainFromDao); 
    }

    /**
     * Test of getLocationsForSuper method, of class SuperDaoDB.
     */
    @Test
    public void testGetLocationsForSuper() {          
        Location location = new Location(); //Create location1
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(45.501690);
        location.setLongitude(-73.567253);
        location = locationDao.addLocation(location); //save location
        
        Location location2 = new Location(); //Create location2
        location2.setName("Test Location Name");
        location2.setDescription("Test Location Description");
        location2.setAddress("Test Location Address");
        location2.setLatitude(45.501690);
        location2.setLongitude(-73.567253);
        location2 = locationDao.addLocation(location2); //save location
        
        List<Location> locations = new ArrayList<>();
        locations.add(location);
        locations.add(location2);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");    
        heroVillain.setLocations(locations); //add locations list
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        Sighting sighting = new Sighting(); //Create sighting
        sighting.setDate(LocalDate.now());
        sighting.setLocationId(location.getId());
        sighting.setSuperId(heroVillain.getId());        
        sightingDao.addSighting(sighting); //save sighting
        
        Sighting sighting2 = new Sighting(); //Create sighting2
        sighting2.setDate(LocalDate.now());
        sighting2.setLocationId(location2.getId());
        sighting2.setSuperId(heroVillain.getId());        
        sightingDao.addSighting(sighting2); //save sighting2
        
        List<Location> locationsForSuperFromDao = superDao.getLocationsForSuper(heroVillain.getId()); //get the locations for super from db
                
        assertEquals(2, locationsForSuperFromDao.size());
        assertTrue(locationsForSuperFromDao.contains(location));
        assertTrue(locationsForSuperFromDao.contains(location2));
    }

    /**
     * Test of getPowersForSuper method, of class SuperDaoDB.
     */
    @Test
    public void testGetPowersForSuper() {
        Power power = new Power(); //Create Power
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power); //save power
        
        Power power2 = new Power(); //Create Power2
        power2.setName("Test Name");
        power2.setDescription("Test Description");
        power2 = powerDao.addPower(power2); //save power2
        
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        powers.add(power2);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");   
        heroVillain.setPowers(powers); //add powers list
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        List<Power> powersForSuperFromDao = superDao.getPowersForSuper(heroVillain.getId()); //get the powers for super from db
                
        assertEquals(2, powersForSuperFromDao.size());
        assertTrue(powersForSuperFromDao.contains(power));
        assertTrue(powersForSuperFromDao.contains(power2));
    }

    /**
     * Test of getOrganizationsForSuper method, of class SuperDaoDB.
     */
    @Test
    public void testGetOrganizationsForSuper() {
        Organization organization = new Organization(); //Create Organization
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization); //save organization
        
        Organization organization2 = new Organization(); //Create Organization2
        organization2.setName("Test Name");
        organization2.setDescription("Test Description");
        organization2.setAddress("Test Address");
        organization2.setContact("Test Contact");
        organization2 = organizationDao.addOrganization(organization2); //save organization2
        
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        organizations.add(organization2);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description"); 
        heroVillain.setOrganizations(organizations); //add organizations list
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        List<Organization> organizationsForSuperFromDao = superDao.getOrganizationsForSuper(heroVillain.getId()); //get the organizations for super from db
        
        assertEquals(2, organizationsForSuperFromDao.size());
        assertTrue(organizationsForSuperFromDao.contains(organization));
        assertTrue(organizationsForSuperFromDao.contains(organization2));
    }

    /**
     * Test of getAllSupers method, of class SuperDaoDB.
     */
    @Test
    public void testGetAllSupers() {
        Super heroVillain = new Super(); //Create super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description"); 
        heroVillain.setLocations(new ArrayList());
        heroVillain.setPowers(new ArrayList());
        heroVillain.setOrganizations(new ArrayList());
        heroVillain = superDao.addSuper(heroVillain); //save super

        
        Super heroVillain2 = new Super(); //Create super2
        heroVillain2.setName("Test Super2 Name");
        heroVillain2.setDescription("Test Super2 Description"); 
        heroVillain2.setLocations(new ArrayList());
        heroVillain2.setPowers(new ArrayList());
        heroVillain2.setOrganizations(new ArrayList());
        heroVillain2 = superDao.addSuper(heroVillain2); //save super2
       
        
        List<Super> supersFromDao = superDao.getAllSupers();
        
        assertEquals(2, supersFromDao.size());
        assertTrue(supersFromDao.contains(heroVillain));
        assertTrue(supersFromDao.contains(heroVillain2));
    }

    /**
     * Test of updateSuper method, of class SuperDaoDB.
     */
    @Test
    public void testUpdateSuper() {
        Super heroVillain = new Super(); //Create super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");   
        heroVillain.setLocations(new ArrayList());
        heroVillain.setPowers(new ArrayList());
        heroVillain.setOrganizations(new ArrayList());
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        Super superFromDao = superDao.getSuperById(heroVillain.getId());
        assertEquals(heroVillain, superFromDao);
        
        heroVillain.setName("New Name");
        superDao.updateSuper(heroVillain);
        
        assertNotEquals(heroVillain, superFromDao);
        
        superFromDao = superDao.getSuperById(heroVillain.getId());
        
        assertEquals(heroVillain, superFromDao);
    }

    /**
     * Test of deleteSuperById method, of class SuperDaoDB.
     */
    @Test
    public void testDeleteSuperById() {
        Organization organization = new Organization(); //Create Organization
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization); //save organization
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Power power = new Power(); //Create Power
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power); //save power
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        Location location = new Location(); //Create a location
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(45.501690);
        location.setLongitude(-73.567253);
        location = locationDao.addLocation(location); //save location
        List<Location> locations = new ArrayList<>();
        locations.add(location);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");  
        heroVillain.setLocations(locations); //add locations list
        heroVillain.setOrganizations(organizations); //add organizations list
        heroVillain.setPowers(powers); //add powers list
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        Sighting sighting = new Sighting(); //Create a sighting
        sighting.setDate(LocalDate.now());
        sighting.setLocationId(location.getId());
        sighting.setSuperId(heroVillain.getId());        
        sightingDao.addSighting(sighting); //save sighting               
        
        Super savedSuper = superDao.getSuperById(heroVillain.getId()); //get the saved super from db
        assertEquals(heroVillain, savedSuper);
        
        superDao.deleteSuperById(heroVillain.getId());
        
        savedSuper = superDao.getSuperById(heroVillain.getId());
        assertNull(savedSuper); //Assert that the power was deleted 
    }

    /**
     * Test of getSupersByLocation method, of class SuperDaoDB.
     */
    @Test
    public void testGetSupersByLocation() {
        Location location = new Location(); //Create location1
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(45.501690);
        location.setLongitude(-73.567253);
        location = locationDao.addLocation(location); //save location
        
        List<Location> locations = new ArrayList<>();
        locations.add(location);

        Super heroVillain = new Super(); //Create super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");  
        heroVillain.setLocations(locations); //add locations list
        heroVillain.setPowers(new ArrayList());
        heroVillain.setOrganizations(new ArrayList());
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        Super heroVillain2 = new Super(); //Create super2
        heroVillain2.setName("Test Super Name");
        heroVillain2.setDescription("Test Super Description"); 
        heroVillain2.setLocations(locations); //add locations list
        heroVillain2.setPowers(new ArrayList());
        heroVillain2.setOrganizations(new ArrayList());
        heroVillain2 = superDao.addSuper(heroVillain2); //save super2
        
        Sighting sighting = new Sighting(); //Create sighting
        sighting.setDate(LocalDate.now());
        sighting.setLocationId(location.getId());
        sighting.setSuperId(heroVillain.getId());        
        sightingDao.addSighting(sighting); //save sighting
        
        Sighting sighting2 = new Sighting(); //Create sighting2
        sighting2.setDate(LocalDate.now());
        sighting2.setLocationId(location.getId());
        sighting2.setSuperId(heroVillain2.getId());        
        sightingDao.addSighting(sighting2); //save sighting2
        
        List<Super> supersByLocationFromDao = superDao.getSupersByLocation(location); //get the saved supers by location from db
        
        assertEquals(2, supersByLocationFromDao.size());
        assertTrue(supersByLocationFromDao.contains(heroVillain));
        assertTrue(supersByLocationFromDao.contains(heroVillain2));
    }

    /**
     * Test of getSupersByOrganization method, of class SuperDaoDB.
     */
    @Test
    public void testGetSupersByOrganization() {
        Organization organization = new Organization(); //Create Organization
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization); //save organization
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Super heroVillain = new Super(); //Create super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");   
        heroVillain.setLocations(new ArrayList()); //set empty list
        heroVillain.setPowers(new ArrayList()); //set empty list
        heroVillain.setOrganizations(organizations); //add organizations list
        heroVillain = superDao.addSuper(heroVillain); //save super
        
        Super heroVillain2 = new Super(); //Create super2
        heroVillain2.setName("Test Super Name");
        heroVillain2.setDescription("Test Super Description");  
        heroVillain2.setLocations(new ArrayList()); //set empty list
        heroVillain2.setPowers(new ArrayList()); //set empty list
        heroVillain2.setOrganizations(organizations); //add organizations list
        heroVillain2 = superDao.addSuper(heroVillain2); //save super2
        
        List<Super> supersByOrganizationFromDao = superDao.getSupersByOrganization(organization); //get the saved supers by organization from db
        
        assertEquals(2, supersByOrganizationFromDao.size());
        assertTrue(supersByOrganizationFromDao.contains(heroVillain));
        assertTrue(supersByOrganizationFromDao.contains(heroVillain2));
    }
    
    /**
     * Test of removePowerForSuper method, of class SuperDaoDB.
     */
    @Test
    public void testRemovePowerForSuper() {
        Power power = new Power(); //Create a power
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power); //save power
        List<Power> powers = new ArrayList<>();
        powers.add(power);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description"); 
        heroVillain.setPowers(powers); //add powers list to super
        superDao.addSuper(heroVillain); //save super  
        
        Super savedSuper = superDao.getSuperById(heroVillain.getId());
        assertEquals(savedSuper.getPowers().size(), 1); //There is 1 power in the list
        
        superDao.removePowerForSuper(heroVillain.getId(), power.getId()); //Call function to delete link between super and power
        
        savedSuper = superDao.getSuperById(heroVillain.getId());
        assertEquals(savedSuper.getPowers().size(), 0); //There is no power in the list, the link was deleted
    
        Power savedPower = powerDao.getPowerById(power.getId());
        assertEquals(power, savedPower); //Assert that the power still exists
    }

    /**
     * Test of removeOrganizationForSuper method, of class SuperDaoDB.
     */
    @Test
    public void testRemoveOrganizationForSuper() {
        Organization organization = new Organization(); //Create organization
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization); //save organization
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");    
        heroVillain.setOrganizations(organizations); //add organizations list to super
        superDao.addSuper(heroVillain); //save super  
        
        Super savedSuper = superDao.getSuperById(heroVillain.getId());
        assertEquals(savedSuper.getOrganizations().size(), 1); //should have only 1 organization in the list
    
        superDao.removeOrganizationForSuper(heroVillain.getId(), organization.getId()); //Call function to delete link between super and organization
        
        savedSuper = superDao.getSuperById(heroVillain.getId());
        assertEquals(savedSuper.getOrganizations().size(), 0); //organization is empty
    
        Organization savedOrganization = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, savedOrganization); //Assert that the organization still exists
    }
}
