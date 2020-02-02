# 1. Employee Address
SELECT employee_id,job_title,a.address_id,a.address_text FROM employees
AS e JOIN addresses AS a ON e.address_id = a.address_id ORDER BY a.address_id LIMIT 5;

# 2. Addresses with Towns
SELECT e.first_name,e.last_name,t.name as town,a.address_text
FROM addresses AS a JOIN employees AS e ON e.address_id = a.address_id JOIN towns AS t ON t.town_id = a.town_id
ORDER BY e.first_name ASC,e.last_name LIMIT 5 ;

# 3. Sales Employee
SELECT e.employee_id,e.first_name,e.last_name,d.name AS department_name
FROM employees AS e JOIN departments AS d ON e.department_id = d.department_id
WHERE d.name = 'Sales'
ORDER BY employee_id DESC;

# 4. Employee Departments
SELECT e.employee_id,e.first_name,e.salary,d.name AS department_name
FROM employees AS e JOIN departments AS d on e.department_id = d.department_id
WHERE e.salary > 15000 ORDER BY e.department_id DESC LIMIT 5;

# 5. Employees Without Project
SELECT e.employee_id,e.first_name FROM employees AS e  LEFT JOIN employees_projects AS ep
ON e.employee_id = ep.employee_id WHERE ep.employee_id IS NULL
ORDER BY e.employee_id DESC LIMIT 3;

# 6. Employees Hired After
SELECT 	e.first_name,e.last_name,e.hire_date,d.name AS dept_name FROM employees AS e JOIN departments AS d
ON e.department_id = d.department_id  WHERE DATE(e.hire_date) > '1999-1-1' AND d.name IN ('Sales','Finance')
ORDER BY  e.hire_date ASC;

# 7. Employees with Project
SELECT e.employee_id,e.first_name,p.name AS project_name FROM employees AS e JOIN employees_projects
 AS ep ON e.employee_id = ep.employee_id JOIN projects AS p ON ep.project_id = p.project_id 
 WHERE DATE(p.start_date) >20020813 AND p.end_date IS NULL ORDER BY e.first_name,p.name ASC LIMIT 5;
 
# 8. Employee 24
SELECT e.employee_id,e.first_name,(CASE WHEN YEAR (p.start_date) >= 2005 THEN NULL ELSE p.name END) 
AS project_name FROM employees AS e JOIN employees_projects
AS ep ON e.employee_id = ep.employee_id JOIN projects AS p ON ep.project_id = p.project_id 
WHERE e.employee_id = 24 ORDER BY project_name ASC;

# 9. Employee Manager
SELECT e.employee_id,e.first_name,e.manager_id,m.first_name AS manager_name FROM employees AS e
JOIN employees AS m ON e.manager_id = m.employee_id WHERE e.manager_id IN (3,7) ORDER BY e.first_name;

# 10. Employee Summary
 SELECT e.employee_id,CONCAT(e.first_name,' ',e.last_name) AS employee_name,
 CONCAT(m.first_name,' ',m.last_name) AS manager_name, d.name
 FROM employees AS m JOIN employees AS e 
 ON e.manager_id = m.employee_id
 JOIN departments AS d ON d.department_id = e.department_id
 WHERE e.manager_id IS NOT NULL
 ORDER BY e.employee_id LIMIT 5;
 
 # 11. Min Average Salary
 SELECT AVG(salary) AS min_average_salary FROM employees GROUP BY department_id ORDER BY min_average_salary LIMIT 1;

 # 12. Highest Peaks in Bulgaria
 SELECT mc.country_code,m.mountain_range,p.peak_name,p.elevation FROM mountains_countries AS mc JOIN mountains AS m ON
 mc.mountain_id = m.id JOIN peaks AS p ON p.mountain_id = mc.mountain_id WHERE mc.country_code LIKE 'BG' AND p.elevation > 2835
 ORDER BY p.elevation DESC;
 
 # 13. Count Mountain Ranges
 SELECT  mc.country_code,COUNT(mc.mountain_id) AS mountain_range
 FROM mountains_countries AS mc  WHERE mc.country_code IN('US','BG','RU')
 GROUP BY mc.country_code
 ORDER BY mountain_range DESC;





