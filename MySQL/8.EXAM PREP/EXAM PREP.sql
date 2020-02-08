# 1. Table Design
CREATE TABLE branches (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE employees (
id int Primary Key AUTO_INCREMENT,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
salary DECIMAL(10,2) NOT NULL,
started_on DATE NOT NULL,
branch_id INT NOT NULL,
CONSTRAINT fk_customers_cities
FOREIGN KEY (branch_id)
REFERENCES branches (id)
);

CREATE TABLE `clients` (
  `id` INT AUTO_INCREMENT,
  `full_name` VARCHAR(50) NOT NULL,
  `age` INT NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `employees_clients` (
  `employee_id` INT NULL,
  `client_id` INT NULL,
  CONSTRAINT `fk_employees_clients_employees`
    FOREIGN KEY (`employee_id`)
    REFERENCES `employees` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_clients_clients`
    FOREIGN KEY (`client_id`)
    REFERENCES `clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE `bank_accounts` (
  `id` INT  AUTO_INCREMENT,
  `account_number` VARCHAR(10) NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  `client_id` INT NOT NULL UNIQUE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bank_acount_clients`
    FOREIGN KEY (`client_id`)
    REFERENCES `clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE `cards` (
  `id` INT AUTO_INCREMENT,
  `card_number` VARCHAR(19) NOT NULL,
  `card_status` VARCHAR(7) NOT NULL,
  `bank_account_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cards_bank_accounts`
    FOREIGN KEY (`bank_account_id`)
    REFERENCES `bank_accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
# 2. Insert
INSERT INTO cards (card_number,card_status,bank_account_id)
(SELECT REVERSE(full_name),'Active',id
FROM clients
WHERE id BETWEEN 191 AND 200);

# 3. Update
UPDATE  employees_clients AS ec 
SET
ec.employee_id = (SELECT ecc.employee_id  FROM 
(SELECT *FROM employees_clients) AS ecc GROUP BY employee_id 
ORDER BY COUNT(ecc.client_id),employee_id ASC LIMIT 1)
WHERE  ec.employee_id = ec.client_id;

# 4. Delete
DELETE e FROM employees AS e LEFT JOIN employees_clients AS ec
 ON e.id = ec.employee_id WHERE client_id IS NULL;
 
DELETE FROM employees WHERE id = 
(SELECT employee_id FROM (SELECT * FROM employees) AS e 
LEFT JOIN employees_clients AS ec
ON e.id = ec.employee_id WHERE client_id IS NULL);

# 5. Clients
SELECT id,full_name FROM clients ORDER BY id ASC;

# 6. Newbies
SELECT id ,CONCAT(first_name,' ',last_name) AS full_name,CONCAT('$',salary) AS salary,started_on
FROM employees WHERE salary >= 100000 AND started_on >=20180101 ORDER BY salary DESC,id;

# 7. Cards against Humanity
SELECT ca.id AS id,CONCAT(ca.card_number,' : ',cl.full_name)as card_token 
FROM cards AS ca JOIN bank_accounts AS ba ON ca.bank_account_id = ba.id 
JOIN clients AS cl ON cl.id = ba.client_id
ORDER BY ca.id DESC;

# 8.Top 5 Employees
SELECT concat(em.first_name,' ',em.last_name) AS name,em.started_on,COUNT(EC.client_id) as count_of_clients
 FROM employees AS em 
JOIN employees_clients AS ec ON em.id = ec.employee_id
GROUP BY ec.employee_id
ORDER BY count_of_clients DESC, employee_id ASC LIMIT 5;

# 9. Branch cards
SELECT b.name,COUNT(C.ID) AS count_of_cards FROM branches AS b 
 LEFT JOIN employees AS e ON b.id = e.branch_id 
 LEFT JOIN employees_clients AS ec ON e.id = ec.employee_id
 LEFT JOIN bank_accounts AS ba ON  ba.client_id = ec.client_id
 LEFT JOIN cards AS c ON ba.id = c.bank_account_id
 GROUP BY b.id ORDER BY count_of_cards DESC,b.name ASC;
 
# 10. Extract client cards count
 DELIMITER $$
 CREATE FUNCTION udf_client_cards_count(name VARCHAR(30)) 
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE numOfCards INT;
  SET numOfCards :=  (SELECT COUNT(ca.id) AS cards FROM clients AS c 
JOIN bank_accounts AS ba ON c.id = ba.client_id
JOIN carDs AS ca ON ba.id = ca.bank_account_id
WHERE c.full_name LIKE name
group by c.id);
  
	RETURN numOfCards;
END $$

# 11. Extract Client Info
DELIMITER $$
 CREATE PROCEDURE udp_clientinfo  (full_name VARCHAR(50))
 BEGIN
 SELECT c.full_name ,c.age,ba.account_number,Concat('$',ba.balance) AS balance
 FROM clients AS c JOIN bank_accounts AS ba ON c.id = ba.client_id WHERE c.full_name LIKE full_name ;
 
 END $$
 
 CALL udp_clientinfo ('Hunter Wesgate');







