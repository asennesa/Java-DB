/*---1.	Find Book Titles---*/
/*By substring*/
SELECT title FROM books WHERE substring(`title`,1,3)= 'The';
/*By wildcard*/
SELECT title FROM books WHERE title LIKE 'The%' ORDER BY id;

/*---2.	Replace Titles---*/
SELECT REPLACE(`title`,'The','***') AS title FROM books WHERE title LIKE 'The%' ORDER BY id;

/*---3.	Sum Cost of All Books---*/
SELECT round(sum(cost),2) FROM books;

/*---4.	Days Lived---*/
SELECT concat(first_name,' ',last_name) AS 'Full Name',TIMESTAMPDIFF(day,born,died) AS 'Days Lived' FROM authors;

/*---5.	Harry Potter Books---*/
SELECT title FROM books WHERE title LIKE 'HARRY POTTER%';