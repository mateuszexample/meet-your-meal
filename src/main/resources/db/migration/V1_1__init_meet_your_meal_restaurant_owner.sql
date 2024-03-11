CREATE TABLE restaurant_owner
(
    restaurant_owner_id     SERIAL      NOT NULL,
    name                    VARCHAR(32) NOT NULL,
    surname                 VARCHAR(32) NOT NULL,
    email                   VARCHAR(32) NOT NULL,
    PRIMARY KEY (restaurant_owner_id )
);