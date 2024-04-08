CREATE TABLE food_category
(
    food_category_id    SERIAL      NOT NULL,
    category            VARCHAR(32) NOT NULL,
    PRIMARY KEY (food_category_id)
);

CREATE TABLE restaurant_food_category
(
    restaurant_id       INT      NOT NULL,
    food_category_id    INT      NOT NULL,
    PRIMARY KEY (restaurant_id, food_category_id),
    CONSTRAINT fk_restaurant_food_category_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
    CONSTRAINT fk_restaurant_food_category_food_category
        FOREIGN KEY (food_category_id)
            REFERENCES food_category (food_category_id)
);

CREATE TABLE food
(
    food_id             SERIAL      NOT NULL,
    name                VARCHAR(32) NOT NULL,
    price               VARCHAR(32) NOT NULL,
    description         VARCHAR(32) NOT NULL,
    food_category_id    INT         NOT NULL,
    image_url           VARCHAR(32),
    PRIMARY KEY (food_id),
    CONSTRAINT fk_food_food_category
        FOREIGN KEY (food_category_id)
            REFERENCES food_category (food_category_id)
);