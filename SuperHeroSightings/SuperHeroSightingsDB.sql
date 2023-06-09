DROP DATABASE IF EXISTS asuperherosightingsdb ;

CREATE DATABASE asuperherosightingsdb ;

USE asuperherosightingsdb ;
-- ====================== MAIN TABLES ===========================
CREATE TABLE Super(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    super_image blob
);

CREATE TABLE location(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    address VARCHAR(50) NOT NULL,
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL
);

CREATE TABLE power(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50)    
);

CREATE TABLE organization(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    address VARCHAR(50),
    contact VARCHAR(50)
);

-- ====================== BRIDGE TABLES ===========================
CREATE TABLE sighting(
	id INT PRIMARY KEY AUTO_INCREMENT,
	super_id INT NOT NULL,
    location_id INT NOT NULL,
    FOREIGN KEY (super_id) REFERENCES super(id),
    FOREIGN KEY (location_id) REFERENCES location(id),
    date DATE NOT NULL
);

CREATE TABLE super_power(
	super_id INT NOT NULL,
    power_id INT NOT NULL,
    PRIMARY KEY (super_id, power_id),
    FOREIGN KEY (super_id) REFERENCES super(id),
    FOREIGN KEY (power_id) REFERENCES power(id)
);

CREATE TABLE super_organization(
	super_id INT NOT NULL,
    organization_id INT NOT NULL,
    PRIMARY KEY (super_id, organization_id),
    FOREIGN KEY (super_id) REFERENCES super(id),
    FOREIGN KEY (organization_id) REFERENCES organization(id)
);
