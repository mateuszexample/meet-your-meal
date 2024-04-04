CREATE TABLE restaurant_owner
(
    restaurant_owner_id     SERIAL      NOT NULL,
    name                    VARCHAR(32) NOT NULL,
    surname                 VARCHAR(32) NOT NULL,
    pesel                   VARCHAR(32) NOT NULL,
    restaurant_id           INT,
    PRIMARY KEY (restaurant_owner_id),
    UNIQUE (pesel),
    CONSTRAINT fk_restaurant_owner_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id)
);