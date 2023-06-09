/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.MySuperHeroSightings.dao;

import com.sg.MySuperHeroSightings.entity.Power;
import com.sg.MySuperHeroSightings.entity.Super;
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
 * @author Bonie f
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerDaoDBTest {
    
    //Autowire in the DAO classes:
    @Autowired
    PowerDao powerDao;
    
    @Autowired
    SuperDao superDao;
    
    
    public PowerDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Power> powers = powerDao.getAllPowers();
        for(Power power : powers) {
            powerDao.deletePowerById(power.getId());
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
     * Test of testAddAndGetPower method, of class PowerDaoDB.
     */
    @Test
    public void testAddAndGetPowerower() {                
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power);
        
        Power powerFromDao = powerDao.getPowerById(power.getId());
        
        assertEquals(power, powerFromDao);
    }
    
    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power power1 = new Power();
        power1.setName("Test Name1");
        power1.setDescription("Test Description");
        power1 = powerDao.addPower(power1);
        
        Power power2 = new Power();
        power2.setName("Test Name");
        power2.setDescription("Test Description");
        power2 = powerDao.addPower(power2);
        
        List<Power> powers = powerDao.getAllPowers();
        
        assertEquals(2, powers.size());
        assertTrue(powers.contains(power1));
        assertTrue(powers.contains(power2));
    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power);
        
        Power powerFromDao = powerDao.getPowerById(power.getId());
        assertEquals(power, powerFromDao);
        
        power.setName("New Name");
        powerDao.updatePower(power);
        
        assertNotEquals(power, powerFromDao);
        
        powerFromDao = powerDao.getPowerById(power.getId());
        
        assertEquals(power, powerFromDao);
    }

    /**
     * Test of deletePowerById method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePowerById() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description"); 
        heroVillain.setPowers(powers); //add powers list to super
        superDao.addSuper(heroVillain); //save super 
        
        Super savedSuper = superDao.getSuperById(heroVillain.getId());
        
        assertEquals(savedSuper.getPowers().get(0).getId(), power.getId()); //Assert that the power on the savedSuper's powers list is the power we created
        
        Power powerFromDao = powerDao.getPowerById(power.getId()); //get the saved power from db
        assertEquals(power, powerFromDao);
        
        powerDao.deletePowerById(power.getId());
        
        powerFromDao = powerDao.getPowerById(power.getId());
        assertNull(powerFromDao); //Assert that the power was deleted
        
        Super superWithoutPower = superDao.getSuperById(heroVillain.getId());
        
        assertEquals(superWithoutPower.getPowers().size(), 0); //Assert that this super doesn't have a power on it's powers list
    }
    
}