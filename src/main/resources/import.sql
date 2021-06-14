<<<<<<< HEAD
--INSERT INTO users (username, password, email, role) VALUES ('rtaylor', 'password', 'richard@gmail.com', 'BASIC_USER');
--INSERT INTO users (username, password, email, role) VALUES ('username', 'password', 'mock@admins.net', 'ADMIN');
--
--INSERT INTO recipes (label, calories, yield, url, image) VALUES ('chicken', 200, 3, 'https://www.google.com', 'https://img2.mashed.com/img/gallery/mistakes-youre-making-when-cooking-a-heritage-chicken/cook-heritage-chicken-low-and-slow-1597170193.jpg');
--INSERT INTO recipes (label, calories, yield, url, image) VALUES ('pork', 200, 3, 'https://www.google.com', 'https://www.meatpoultry.com/ext/resources/MPImages/07-2019/072219/tyson-pork-small.jpg?1563801864');
--INSERT INTO recipes (label, calories, yield, url, image) VALUES ('beef', 200, 3, 'https://www.google.com', 'https://www.teacher-chef.com/wp-content/uploads/2012/05/4-27-close-up-corned-beef1.jpg');
--INSERT INTO recipes (label, calories, yield, url, image) VALUES ('fish', 200, 3, 'https://www.google.com', 'https://www.cbc.ca/stevenandchris/content/images/fish-frying-pan.jpg');
--
--INSERT INTO user_favorite_recipes(user_id, recipe_id, times_prepared, favorite) VALUES (2, 1, 0, true);

INSERT INTO users (username, password, email, role) VALUES ('rtaylor', 'password', 'richard@gmail.com', 'BASIC_USER');
INSERT INTO users (username, password, email, role) VALUES ('username', 'password', 'mock@admins.net', 'ADMIN');

INSERT INTO recipes (label, calories, yield, url, image) VALUES ('chicken', 200, 3, 'https://www.google.com', 'https://img2.mashed.com/img/gallery/mistakes-youre-making-when-cooking-a-heritage-chicken/cook-heritage-chicken-low-and-slow-1597170193.jpg');
INSERT INTO recipes (label, calories, yield, url, image) VALUES ('pork', 200, 3, 'https://www.google.com', 'https://www.meatpoultry.com/ext/resources/MPImages/07-2019/072219/tyson-pork-small.jpg?1563801864');
INSERT INTO recipes (label, calories, yield, url, image) VALUES ('beef', 200, 3, 'https://www.google.com', 'https://www.teacher-chef.com/wp-content/uploads/2012/05/4-27-close-up-corned-beef1.jpg');
INSERT INTO recipes (label, calories, yield, url, image) VALUES ('fish', 200, 3, 'https://www.google.com', 'https://www.cbc.ca/stevenandchris/content/images/fish-frying-pan.jpg');

INSERT INTO user_favorite_recipes(user_id, recipe_id, times_prepared, favorite) VALUES (2, 1, 0, true);






--Inserting meal time test
INSERT INTO users (username, password, email) values('jane.doe', 'password', 'jane@doe.com');
--Inserting recipes
INSERT INTO recipes (label, url, calories, yield, image) values('pork', 'httsdcsacdsa', 200, 3, 'jcld;jsklcdsa');
INSERT INTO recipes (label, url, calories, yield, image) values('chicken', 'httsdcsacdsa', 300, 3, 'jcld;jsklcdsa');
INSERT INTO recipes (label, url, calories, yield, image) values('fish', 'httsdcsacdsa', 100, 2, 'jcld;jsklcdsa');
--Inserting mealtimes
insert into meal_times (meal_date, meal_time, recipe_id) values ('2021-06-08', 'breakfast', 1);
insert into meal_times (meal_date, meal_time, recipe_id) values ('2021-06-08', 'lunch', 2);
insert into meal_times (meal_date, meal_time, recipe_id) values ('2021-06-08', 'dinner',3);
insert into meal_times (meal_date, meal_time, recipe_id) values ('2021-06-09', 'breakfast', 1);
insert into meal_times (meal_date, meal_time, recipe_id) values ('2021-06-09', 'lunch', 2);
insert into meal_times (meal_date, meal_time, recipe_id) values ('2021-06-09', 'dinner',3);
--Inserting testing user_meal_time
insert into user_meal_times(user_id, meal_time_id) values (1,1);
insert into user_meal_times(user_id, meal_time_id) values (1,2);
insert into user_meal_times(user_id, meal_time_id) values (1,3);
insert into user_meal_times(user_id, meal_time_id) values (1,4);
insert into user_meal_times(user_id, meal_time_id) values (1,5);
insert into user_meal_times(user_id, meal_time_id) values (1,6);

--Insert into user_favorite_recipes
INSERT INTO user_favorite_recipes(user_id, recipe_id, times_prepared, favorite) VALUES (1, 1, 0, true);
