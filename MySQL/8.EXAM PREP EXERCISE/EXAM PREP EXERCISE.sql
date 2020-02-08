#1.Table Design
CREATE TABLE `users` (
  `id` INT  AUTO_INCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `categories` (
  `id` INT  AUTO_INCREMENT,
  `category` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `articles` (
  `id` INT AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `content` TEXT NOT NULL,
  `category_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_articles_categories`
    FOREIGN KEY (`category_id`)
    REFERENCES `categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE `users_articles` (
  `user_id` INT ,
  `article_id` INT ,
  CONSTRAINT `fk_UA_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UA_article`
    FOREIGN KEY (`article_id`)
    REFERENCES `articles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE `comments` (
  `id` INT  AUTO_INCREMENT,
  `comment` VARCHAR(255) NOT NULL,
  `article_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_COMMENTS_ARTICLE`
    FOREIGN KEY (`article_id`)
    REFERENCES `articles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMMENTS_USERS`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE `likes` (
  `id` INT  AUTO_INCREMENT,
  `article_id` INT NULL,
  `comment_id` INT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_LIKES_ARTICLES`
    FOREIGN KEY (`article_id`)
    REFERENCES `articles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_LIKES_COMMENTS`
    FOREIGN KEY (`comment_id`)
    REFERENCES `comments` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_LIKES_USERS`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    #2. Insert
    INSERT INTO likes (article_id,comment_id,user_id)
     SELECT IF(u.id % 2 = 0,CHAR_LENGTH(u.username),null),
            IF(u.id % 2 = 1,CHAR_LENGTH(u.email),null),u.id FROM users as u
            WHERE u.id BETWEEN 16 AND 20;
            
	#3. Update
    UPDATE `comments` as c
SET c.comment=
(CASE 
WHEN id % 2 =0 THEN 'Very good article.'
WHEN id % 3 =0 THEN 'This is interesting.'
WHEN id % 5 =0 THEN 'I definitely will read the article again.'
WHEN id % 7 =0 THEN 'The universe is such an amazing thing.'
ELSE c.comment
END)
WHERE id BETWEEN 1 AND 15;

#4. Delete
DELETE FROM `articles`
WHERE category_id IS NULL;

#5. 


		


  