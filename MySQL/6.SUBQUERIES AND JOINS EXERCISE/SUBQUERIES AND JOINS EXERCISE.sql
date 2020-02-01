# 1. Employee Address
SELECT employee_id,job_title,a.address_id,a.address_text FROM employees
AS e JOIN addresses AS a ON e.address_id = a.address_id ORDER BY a.address_id LIMIT 5;

# 2. Addresses with Towns
SELECT e.first_name,e.last_name,t.name as town,a.address_text
FROM addresses AS a JOIN employees AS e ON e.address_id = a.address_id JOIN towns AS t ON t.town_id = a.town_id
ORDER BY e.first_name ASC,e.last_name LIMIT 5 ;
