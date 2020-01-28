# 1. Mountains and Peaks
CREATE TABLE `mountains` (
  `id` INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL);
  
  CREATE TABLE `peaks` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  `name` VARCHAR(45) NOT NULL,
  `mountain_id` INT NOT NULL,
  CONSTRAINT `fk_mountains_peaks`
    FOREIGN KEY (`mountain_id`)
    REFERENCES `mountains` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
# 2.Trip Organization
SELECT driver_id,vehicle_type,CONCAT(first_name,' ',last_name) AS driver_name FROM campers AS c
JOIN vehicles AS v ON c.id = v.driver_id ;

# 3. SoftUni Hiking
SELECT starting_point as route_starting_point,end_point as route_ending_point,leader_id,
CONCAT(first_name,' ',last_name) AS leader_name FROM campers AS c JOIN routes AS r
ON c.id = r.leader_id;

# 4. Delete Mountains
CREATE TABLE `mountains`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20) NOT NULL
);
CREATE TABLE `peaks`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20) NOT NULL,
`mountain_id` INT,
CONSTRAINT `fk_mountain_id` 
FOREIGN KEY(`mountain_id`)
REFERENCES `mountains`(`id`)
ON DELETE CASCADE
);
  
  

