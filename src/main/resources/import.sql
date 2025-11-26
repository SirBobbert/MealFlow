-- Disable foreign keys for the whole seed run
SET FOREIGN_KEY_CHECKS = 0;

-- Clear existing data in correct dependency order
TRUNCATE TABLE recipe_ingredient;
TRUNCATE TABLE recipe;
TRUNCATE TABLE ingredient;
TRUNCATE TABLE `user`;

-- Users
-- password is BCrypt hash for "Password123!"
INSERT INTO `user` (id, name, email, password, created_at, role) VALUES (1, 'Admin User', 'admin@email.com', '$2a$12$bL50IdCtFEShxhH8tA7JeOoQxmhTleMQw1eqacZr1jNasyC42vYde', NOW(), 'ADMIN');
INSERT INTO `user` (id, name, email, password, created_at, role) VALUES (2, 'Test User', 'test@email.com', '$2a$12$bL50IdCtFEShxhH8tA7JeOoQxmhTleMQw1eqacZr1jNasyC42vYde', NOW(), 'USER');

-- Ingredients
INSERT INTO ingredient (id, name, category) VALUES (1,  'Carrot',          'VEGETABLE');
INSERT INTO ingredient (id, name, category) VALUES (2,  'Onion',           'VEGETABLE');
INSERT INTO ingredient (id, name, category) VALUES (3,  'Garlic',          'VEGETABLE');
INSERT INTO ingredient (id, name, category) VALUES (4,  'Olive oil',       'OTHER');
INSERT INTO ingredient (id, name, category) VALUES (5,  'Spaghetti',       'GRAIN');
INSERT INTO ingredient (id, name, category) VALUES (6,  'Minced beef',     'PROTEIN');
INSERT INTO ingredient (id, name, category) VALUES (7,  'Tomato passata',  'VEGETABLE');
INSERT INTO ingredient (id, name, category) VALUES (8,  'Salt',            'SPICE');
INSERT INTO ingredient (id, name, category) VALUES (9,  'Black pepper',    'SPICE');
INSERT INTO ingredient (id, name, category) VALUES (10, 'Parmesan cheese', 'DAIRY');

-- Recipes
INSERT INTO recipe (id, user_id, title, description, instructions, servings, prep_time, created_at, updated_at) VALUES (1, 2, 'Spaghetti Bolognese', 'Simple meat sauce with spaghetti', '1) Chop onion and garlic. 2) Brown minced beef. 3) Add passata and simmer. 4) Boil spaghetti. 5) Combine and serve.', 4, 30, NOW(), NULL);
INSERT INTO recipe (id, user_id, title, description, instructions, servings, prep_time, created_at, updated_at) VALUES (2, 2, 'Garlic Parmesan Pasta', 'Quick creamy garlic pasta', '1) Boil pasta. 2) Fry garlic in olive oil. 3) Add pasta, parmesan, and a bit of cooking water. 4) Season and serve.', 2, 20, NOW(), NULL);

-- Recipe ingredients

-- Spaghetti Bolognese (recipe 1)
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (1, 1, 5, 500, 'g');        -- Spaghetti
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (2, 1, 6, 400, 'g');        -- Minced beef
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (3, 1, 2, 1,   'pcs');      -- Onion
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (4, 1, 3, 2,   'cloves');   -- Garlic
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (5, 1, 7, 500, 'ml');       -- Tomato passata
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (6, 1, 4, 2,   'tbsp');     -- Olive oil
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (7, 1, 8, 1,   'tsp');      -- Salt
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (8, 1, 9, 1,   'tsp');      -- Black pepper

-- Garlic Parmesan Pasta (recipe 2)
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (9,  2, 5,  200, 'g');      -- Spaghetti
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (10, 2, 3,  2,   'cloves'); -- Garlic
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (11, 2, 4,  1,   'tbsp');   -- Olive oil
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (12, 2, 10, 50,  'g');      -- Parmesan cheese
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (13, 2, 8,  1,   'tsp');    -- Salt
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES (14, 2, 9,  1,   'tsp');    -- Black pepper

-- Re-enable foreign keys after seeding
SET FOREIGN_KEY_CHECKS = 1;
