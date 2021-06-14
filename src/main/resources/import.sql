INSERT INTO users (username, password, email, role) VALUES ('rtaylor', 'password', 'richard@gmail.com', 'BASIC_USER');
INSERT INTO users (username, password, email, role) VALUES ('username', 'password', 'mock@admins.net', 'ADMIN');

INSERT INTO recipes (label, calories, yield, url, image) VALUES ('chicken', 200, 3, 'https://www.google.com', 'https://img2.mashed.com/img/gallery/mistakes-youre-making-when-cooking-a-heritage-chicken/cook-heritage-chicken-low-and-slow-1597170193.jpg');
INSERT INTO recipes (label, calories, yield, url, image) VALUES ('pork', 200, 3, 'https://www.google.com', 'https://www.meatpoultry.com/ext/resources/MPImages/07-2019/072219/tyson-pork-small.jpg?1563801864');
INSERT INTO recipes (label, calories, yield, url, image) VALUES ('beef', 200, 3, 'https://www.google.com', 'https://www.teacher-chef.com/wp-content/uploads/2012/05/4-27-close-up-corned-beef1.jpg');
INSERT INTO recipes (label, calories, yield, url, image) VALUES ('fish', 200, 3, 'https://www.google.com', 'https://www.cbc.ca/stevenandchris/content/images/fish-frying-pan.jpg');

INSERT INTO user_favorite_recipes(user_id, recipe_id, times_prepared, favorite) VALUES (2, 1, 0, true);
