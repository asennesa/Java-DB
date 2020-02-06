 # 1. Employees with Salary Above 35000
 DELIMITER $$
 CREATE PROCEDURE usp_get_employees_salary_above_35000 ()
 BEGIN
 SELECT first_name,last_name FROM employees WHERE salary > 35000
 ORDER BY first_name,last_name,employee_id ASC;
 END $$
 
 CALL usp_get_employees_salary_above_35000;
 
 # 2. Employees with Salary Above Number
	DELIMITER $$
 CREATE PROCEDURE usp_get_employees_salary_above (given_salary decimal(10,4))
 BEGIN
 SELECT first_name,last_name FROM employees WHERE salary >= given_salary
 ORDER BY first_name,last_name,employee_id ASC;
 END $$
 
 CALL usp_get_employees_salary_above(48100);
 
# 3. Town Names Starting With
DELIMITER $$
 CREATE PROCEDURE usp_get_towns_starting_with (aString VARCHAR(50))
 BEGIN
 SELECT name FROM towns WHERE name LIKE concat(aString ,'%') ORDER BY name ASC;
 END $$
 
 CALL usp_get_towns_starting_with('sof');
 
 
 # 4. Employees from Town
 DELIMITER $$
 CREATE PROCEDURE usp_get_employees_from_town (town_name VARCHAR(50))
 BEGIN
 SELECT first_name,last_name FROM employees as e 
 JOIN addresses as a ON e.address_id = a.address_id
 JOIN towns as t ON t.town_id = a.town_id  
 WHERE t.name LIKE town_name ORDER BY e.first_name,e.last_name,e.employee_id ASC;
 END $$
 
 # 5. Salary Level Function
 DELIMITER $$
 CREATE FUNCTION ufn_get_salary_level (emp_salary DECIMAL)
RETURNS VARCHAR(50) 
DETERMINISTIC
BEGIN
	DECLARE salary_level VARCHAR(50);
    CASE
    WHEN emp_salary < 30000  THEN SET salary_level := 'Low';
    WHEN emp_salary BETWEEN 30000 AND 50000 THEN SET salary_level := 'Average';
    ELSE  SET salary_level := 'High';
    END CASE;
	RETURN salary_level;
END $$

SELECT ufn_get_salary_level(125500.00);

# 6. Employees by Salary Level
DELIMITER $$
 CREATE PROCEDURE usp_get_employees_by_salary_level (sal_lvl VARCHAR(50))
 BEGIN
 SELECT first_name,last_name FROM employees
 WHERE ufn_get_salary_level(salary) = sal_lvl
 ORDER BY first_name DESC,last_name DESC;
 END $$
 
 # 8. Find Full Name

DELIMITER $$
 CREATE PROCEDURE usp_get_holders_full_name ()
 BEGIN
 SELECT concat(first_name,' ',last_name) AS full_name 
 FROM account_holders ORDER BY full_name ASC,id ASC;
 END $$

CALL usp_get_holders_full_name;

# 9. People with Balance Higher Than
DELIMITER $$
 CREATE PROCEDURE usp_get_holders_with_balance_higher_than(num INT)
 BEGIN
 SELECT first_name,last_name FROM account_holders AS ah JOIN accounts AS a ON ah.id =a.account_holder_id 
GROUP BY ah.id HAVING SUM(a.balance) > num ORDER BY account_holder_id ASC;

 END $$
 
# 10.Future Value Function
 DELIMITER $$
 CREATE FUNCTION ufn_calculate_future_value (sum DOUBLE(19,11) ,rate DOUBLE(19,11) ,years INT)
RETURNS DOUBLE
DETERMINISTIC
BEGIN
DECLARE result DOUBLE(19,11) ;
SET result:=sum * POWER(1 + rate ,years);
	RETURn result;
END $$

SELECT ufn_calculate_future_value (1000,0.1, 5);
