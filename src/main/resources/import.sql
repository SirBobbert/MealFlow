-- Disable foreign keys for the whole seed run
SET FOREIGN_KEY_CHECKS = 0;

-- Clear existing data (order does not matter while FK checks are off)
TRUNCATE TABLE meal_plan_entries;
TRUNCATE TABLE shopping_list_items;
TRUNCATE TABLE meal_plan;
TRUNCATE TABLE recipe_ingredient;
TRUNCATE TABLE recipe;
TRUNCATE TABLE ingredient;
TRUNCATE TABLE `user`;

-- Users
-- password is BCrypt hash for "Password123!"
INSERT INTO `user` (id, name, email, password, created_at, role) VALUES
(1, 'Admin User', 'admin@email.com',
 '$2a$12$bL50IdCtFEShxhH8tA7JeOoQxmhTleMQw1eqacZr1jNasyC42vYde',
 NOW(), 'ADMIN'),
(2, 'Test User', 'test@email.com',
 '$2a$12$bL50IdCtFEShxhH8tA7JeOoQxmhTleMQw1eqacZr1jNasyC42vYde',
 NOW(), 'USER');

-- Ingredients
INSERT INTO ingredient (id, name, category) VALUES
(1,  'Carrot',          'VEGETABLE'),
(2,  'Onion',           'VEGETABLE'),
(3,  'Garlic',          'VEGETABLE'),
(4,  'Olive oil',       'OTHER'),
(5,  'Spaghetti',       'GRAIN'),
(6,  'Minced beef',     'PROTEIN'),
(7,  'Tomato passata',  'VEGETABLE'),
(8,  'Salt',            'SPICE'),
(9,  'Black pepper',    'SPICE'),
(10, 'Parmesan cheese', 'DAIRY'),
(11, 'Chicken breast',  'PROTEIN'),
(12, 'Rice',            'GRAIN'),
(13, 'Red bell pepper', 'VEGETABLE'),
(14, 'Curry powder',    'SPICE'),
(15, 'Coconut milk',    'OTHER'),
(16, 'Lettuce',         'VEGETABLE'),
(17, 'Cucumber',        'VEGETABLE'),
(18, 'Croutons',        'GRAIN'),
(19, 'Lemon juice',     'OTHER'),
(20, 'Yoghurt',         'DAIRY');

-- Recipes
INSERT INTO recipe (id, user_id, title, description, instructions,
                    servings, prep_time, created_at, updated_at) VALUES
(1, 2,
 'Spaghetti Bolognese',
 'Simple meat sauce with spaghetti',
 '1) Chop onion and garlic. 2) Brown minced beef. 3) Add passata and simmer. 4) Boil spaghetti. 5) Combine and serve.',
 4, 30, NOW(), NULL),
(2, 2,
 'Garlic Parmesan Pasta',
 'Quick creamy garlic pasta',
 '1) Boil pasta. 2) Fry garlic in olive oil. 3) Add pasta, parmesan, and a bit of cooking water. 4) Season and serve.',
 2, 20, NOW(), NULL),
(3, 2,
 'Chicken Curry with Rice',
 'Mild chicken curry with vegetables and rice',
 '1) Cook rice. 2) Brown chicken. 3) Add onion, pepper, curry powder and garlic. 4) Add coconut milk and simmer. 5) Serve over rice.',
 4, 35, NOW(), NULL),
(4, 2,
 'Veggie Stir Fry',
 'Quick vegetable stir fry with rice',
 '1) Cook rice. 2) Stir fry carrot, onion and pepper. 3) Add garlic, salt and pepper. 4) Serve with rice.',
 2, 20, NOW(), NULL),
(5, 2,
 'Simple Caesar Style Salad',
 'Green salad with yoghurt-lemon dressing',
 '1) Chop lettuce and cucumber. 2) Mix yoghurt, lemon juice, salt and pepper. 3) Toss salad with dressing and croutons.',
 3, 15, NOW(), NULL);

-- Recipe ingredients

-- Spaghetti Bolognese (recipe 1)
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES
(1,  1, 5, 500, 'g'),        -- Spaghetti
(2,  1, 6, 400, 'g'),        -- Minced beef
(3,  1, 2,   1, 'pcs'),      -- Onion
(4,  1, 3,   2, 'cloves'),   -- Garlic
(5,  1, 7, 500, 'ml'),       -- Tomato passata
(6,  1, 4,   2, 'tbsp'),     -- Olive oil
(7,  1, 8,   1, 'tsp'),      -- Salt
(8,  1, 9,   1, 'tsp');      -- Black pepper

-- Garlic Parmesan Pasta (recipe 2)
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES
(9,  2, 5,  200, 'g'),       -- Spaghetti
(10, 2, 3,    2, 'cloves'),  -- Garlic
(11, 2, 4,    1, 'tbsp'),    -- Olive oil
(12, 2, 10,  50, 'g'),       -- Parmesan cheese
(13, 2, 8,    1, 'tsp'),     -- Salt
(14, 2, 9,    1, 'tsp');     -- Black pepper

-- Chicken Curry with Rice (recipe 3)
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES
(15, 3, 11, 400, 'g'),       -- Chicken breast
(16, 3, 2,    1, 'pcs'),     -- Onion
(17, 3, 13,   1, 'pcs'),     -- Red bell pepper
(18, 3, 3,    2, 'cloves'),  -- Garlic
(19, 3, 14,   2, 'tsp'),     -- Curry powder
(20, 3, 15, 400, 'ml'),      -- Coconut milk
(21, 3, 8,    1, 'tsp'),     -- Salt
(22, 3, 9,    1, 'tsp');     -- Black pepper

-- Veggie Stir Fry (recipe 4)
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES
(23, 4, 1,  150, 'g'),       -- Carrot
(24, 4, 2,    1, 'pcs'),     -- Onion
(25, 4, 13,   1, 'pcs'),     -- Red bell pepper
(26, 4, 3,    2, 'cloves'),  -- Garlic
(27, 4, 4,    1, 'tbsp'),    -- Olive oil
(28, 4, 8,    1, 'tsp'),     -- Salt
(29, 4, 9,    1, 'tsp'),     -- Black pepper
(30, 4, 12, 200, 'g');       -- Rice

-- Simple Caesar Style Salad (recipe 5)
INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, unit) VALUES
(31, 5, 16, 150, 'g'),       -- Lettuce
(32, 5, 17, 100, 'g'),       -- Cucumber
(33, 5, 20, 100, 'g'),       -- Yoghurt
(34, 5, 19,  10, 'ml'),      -- Lemon juice
(35, 5, 18,  30, 'g'),       -- Croutons
(36, 5, 8,    1, 'tsp'),     -- Salt
(37, 5, 9,    1, 'tsp');     -- Black pepper

-- Meal plans
INSERT INTO meal_plan (id, user_id, name) VALUES
(1, 2, 'Simple Week Plan'),
(2, 2, 'Protein Focused Plan');

-- Meal plan entries for plan 1 (Simple Week Plan)
INSERT INTO meal_plan_entries
(id, meal_plan_id, recipe_id, created_at, meal_type, servings_override, day_of_week) VALUES
(1, 1, 1, NOW(), 'DINNER',   4, 'MONDAY'),
(2, 1, 2, NOW(), 'DINNER',   2, 'TUESDAY'),
(3, 1, 3, NOW(), 'DINNER',   4, 'WEDNESDAY'),
(4, 1, 4, NOW(), 'DINNER',   2, 'THURSDAY'),
(5, 1, 1, NOW(), 'DINNER',   4, 'FRIDAY'),
(6, 1, 5, NOW(), 'LUNCH',    3, 'SATURDAY'),
(7, 1, 5, NOW(), 'LUNCH',    3, 'SUNDAY');

-- Meal plan entries for plan 2 (Protein Focused Plan)
INSERT INTO meal_plan_entries
(id, meal_plan_id, recipe_id, created_at, meal_type, servings_override, day_of_week) VALUES
(8,  2, 3, NOW(), 'DINNER',   4, 'MONDAY'),
(9,  2, 1, NOW(), 'DINNER',   4, 'TUESDAY'),
(10, 2, 3, NOW(), 'DINNER',   4, 'THURSDAY'),
(11, 2, 5, NOW(), 'LUNCH',    2, 'FRIDAY');

-- Shopping list items for plan 1 (aggregated roughly by ingredients)
INSERT INTO shopping_list_items
(id, meal_plan_id, ingredient_id, amount, unit, checked) VALUES
(1, 1, 5, 1200, 'g', 0),     -- Spaghetti total
(2, 1, 6,  800, 'g', 0),     -- Minced beef total
(3, 1, 7, 1000, 'ml', 0),    -- Tomato passata
(4, 1, 11, 400, 'g', 0),     -- Chicken breast
(5, 1, 12, 400, 'g', 0),     -- Rice
(6, 1, 16, 150, 'g', 0),     -- Lettuce
(7, 1, 17, 100, 'g', 0),     -- Cucumber
(8, 1, 18,  30, 'g', 0),     -- Croutons
(9, 1, 20, 100, 'g', 0);     -- Yoghurt

-- Shopping list items for plan 2
INSERT INTO shopping_list_items
(id, meal_plan_id, ingredient_id, amount, unit, checked) VALUES
(10, 2, 11, 800, 'g', 0),    -- Chicken breast
(11, 2, 6,  400, 'g', 0),    -- Minced beef
(12, 2, 12, 400, 'g', 0),    -- Rice
(13, 2, 7,  500, 'ml', 0),   -- Tomato passata
(14, 2, 5,  500, 'g', 0);    -- Spaghetti

-- Re-enable foreign keys after seeding
SET FOREIGN_KEY_CHECKS = 1;
