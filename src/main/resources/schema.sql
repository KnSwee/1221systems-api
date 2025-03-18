CREATE TABLE IF NOT EXISTS users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE,
                       age INTEGER,
                       weight DECIMAL,
                       height DECIMAL,
                       goal VARCHAR(20),
                       daily_calories_norm INTEGER
);

CREATE TABLE IF NOT EXISTS dishes (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        calories INTEGER,
                        proteins DECIMAL,
                        fats DECIMAL,
                        carbohydrates DECIMAL
);

CREATE TABLE IF NOT EXISTS meals (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER REFERENCES Users(id),
                       meal_datetime TIMESTAMP
);

CREATE TABLE IF NOT EXISTS meal_dishes (
                             meal_id INTEGER REFERENCES Meals(id),
                             dish_id INTEGER REFERENCES Dishes(id),
                             quantity INTEGER,
                             PRIMARY KEY (meal_id, dish_id)
);
