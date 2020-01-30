# 1.One-To-One Relationship
CREATE TABLE `passports` (
  `passport_id` INT NOT NULL AUTO_INCREMENT UNIQUE,	
  `passport_number` VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY (`passport_id`)
  );
  CREATE TABLE `persons` (
  `person_id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `first_name` VARCHAR(45) NOT NULL,
  `salary` DECIMAL(10,2) NOT NULL,
  `passport_id` INT NULL UNIQUE,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `fk_persons_passports`
    FOREIGN KEY (`passport_id`)
    REFERENCES `passports` (`passport_id`));
    
INSERT INTO `passports`
(`passport_id`,`passport_number`)
VALUES
(101,'N34FG21B'),
(102,'K65LO4R7'),
(103,'ZE657QP2');
INSERT INTO `persons`
(`person_id`,`first_name`,`salary`,`passport_id`)
VALUES
(1,'Roberto',43300.00,102),
(2,'Tom',56100.00,103),
(3,'Yana',60200.00,101);

# 2.One-To-Many Relationship
CREATE TABLE manufacturers (
manufacturer_id INT NOT NULL ,
name VARCHAR(50) NOT NULL,
established_on DATE NOT NULL);

CREATE TABLE models(
model_id INT NOT NULL ,
name VARCHAR(50) NOT NULL,
manufacturer_id INT NOT NULL);
    
ALTER TABLE `manufacturers`
ADD CONSTRAINT PK_manufacturersId
PRIMARY KEY  (manufacturer_id);

ALTER TABLE `models`
ADD CONSTRAINT PK_ModelsId
PRIMARY KEY (Model_id);

ALTER TABLE `models`
ADD CONSTRAINT fk_models_manufacturers
    FOREIGN KEY (manufacturer_id)
    REFERENCES manufacturers (manufacturer_id);
    
    INSERT INTO manufacturers (`manufacturer_id`,`name`,`established_on`)
VALUES (1,'BMW','1916-03-01'),
 (2,'Tesla','2003-01-01'),
 (3,'Lada','1966-05-01');
 
 INSERT INTO models (`model_id`,`name`,`manufacturer_id`)
VALUES (101,'X1',1),
 (102,'i6',1),
 (103,'Model S',2),
 (104,'Model X',2),
 (105,'Model 3',2),
 (106,'Nova',3);

# 3. Many-To-Many Relationship
CREATE TABLE exams (
exam_id INT NOT NULL,
name VARCHAR(50)
);

CREATE TABLE students (
student_id INT NOT NULL,
name VARCHAR(50)
);

ALTER TABLE exams
ADD CONSTRAINT PK_exam_id
PRIMARY KEY (exam_id);

ALTER TABLE students
ADD CONSTRAINT PK_student_id
PRIMARY KEY (student_id);

CREATE TABLE students_exams(
student_id INT ,
exam_id INT 
);

ALTER TABLE students_exams
ADD CONSTRAINT PK_student_exam
PRIMARY KEY (student_id,exam_id);

ALTER TABLE students_exams
ADD CONSTRAINT fk_students_exams_students
 FOREIGN KEY (student_id)
    REFERENCES students (student_id);
    
ALTER TABLE students_exams
ADD CONSTRAINT fk_students_exams_exams
 FOREIGN KEY (exam_id)
    REFERENCES exams (exam_id);
    
INSERT INTO students(student_id,name)
VALUES(1,'Mila'),                                   
(2,'Toni'),
(3,'Ron');

INSERT INTO exams(exam_id,name)
VALUES(101,'Spring MVC'),                                   
(102,'Neo4j'),
(103,'Oracle 11g');

INSERT INTO students_exams(student_id,exam_id)
VALUES
(1,'101'),                                   
(1,'102'),
(2,'101'),
(3,'103'),
(2,'102'),
(2,'103');

# 4. Self-Referencing
CREATE TABLE teachers (teacher_id INT NOT NULL,
name VARCHAR(50),
manager_id INT
 );
 
 ALTER TABLE teachers
ADD CONSTRAINT PK_TeacherId
PRIMARY KEY(teacher_id);

 ALTER TABLE teachers
 ADD CONSTRAINT fk_teachers_teachers
FOREIGN KEY (`manager_id`)
 REFERENCES `teachers`(`teacher_id`);
 
INSERT INTO teachers(teacher_id,name) VALUES
(101,'John'),
(102,'Maya'),
(103,'Silvia'),
(104,'Ted'),
(105,'Mark'),
(106,'Greta');

UPDATE `teachers`SET `manager_id` = 106 WHERE `name` ='Maya';
UPDATE `teachers`SET `manager_id` = 106 WHERE `name` ='Silvia';
UPDATE `teachers`SET `manager_id` = 105 WHERE `name` ='Ted';
UPDATE `teachers`SET `manager_id` = 101 WHERE `name` ='Mark';
UPDATE `teachers`SET `manager_id` = 101 WHERE `name` ='Greta';

# 5. Online Store Database
CREATE TABLE cities(
city_id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(50)
);

CREATE TABLE customers(
customer_id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(50),
birthday DATE,
city_id INT,
CONSTRAINT fk_customers_cities
FOREIGN KEY (city_id)
REFERENCES cities (city_id)
);

CREATE TABLE orders(
order_id INT AUTO_INCREMENT PRIMARY KEY,
customer_id INT,
CONSTRAINT fk_orders_customers
FOREIGN KEY (customer_id)
REFERENCES customers(customer_id)
);

CREATE TABLE item_types(
item_type_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50)
);

CREATE TABLE items (
item_id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(50),
item_type_id INT,
CONSTRAINT fk_itmes_item_types
FOREIGN KEY (item_type_id)
REFERENCES item_types(item_type_id)
);

CREATE TABLE order_items(
 order_id INT,
 item_id INT,
CONSTRAINT pk_order_item
 PRIMARY KEY (order_id,item_id),
CONSTRAINT fk_order_items_orders
 FOREIGN KEY (order_id)
 REFERENCES orders(order_id),
CONSTRAINT fk_order_items_item
 FOREIGN KEY (item_id)
 REFERENCES items(item_id)
);

# 6. University Database
CREATE TABLE majors(
major_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50)
);

CREATE TABLE `students` (
  `student_id` INT NOT NULL AUTO_INCREMENT,
  `student_number` VARCHAR(12) NULL,
  `student_name` VARCHAR(50) NULL,
  `major_id` INT NULL,
  PRIMARY KEY (`student_id`),
  CONSTRAINT `fk_students_majors`
    FOREIGN KEY (`major_id`)
    REFERENCES `majors` (`major_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE payments(
payment_id INT AUTO_INCREMENT PRIMARY KEY,
payment_date DATE,
payment_amount DECIMAL(8,2),
student_id INT,
CONSTRAINT fk_payments_students
FOREIGN KEY (student_id)
REFERENCES `students` (`student_id`)
);

CREATE TABLE `subjects` (
  `subject_id` INT NOT NULL AUTO_INCREMENT,
  `subject_name` VARCHAR(50) NULL,
  PRIMARY KEY (`subject_id`));
  
CREATE TABLE `agenda` (
  `student_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`student_id`, `subject_id`),
  CONSTRAINT `fk_agenda_students`
    FOREIGN KEY (`student_id`)
    REFERENCES `students` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_agenda_subjects`
    FOREIGN KEY (`subject_id`)
    REFERENCES `subjects` (`subject_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
# 9. Peaks in Rila
SELECT mountain_range,peak_name,elevation AS `peak_elevation` FROM mountains AS m
JOIN peaks AS p
ON m.id = p.mountain_id
WHERE mountain_range = 'Rila'
ORDER BY peak_elevation DESC;










