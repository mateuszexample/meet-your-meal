CREATE TABLE restaurant
(
    restaurant_id           SERIAL        NOT NULL,
    name                    VARCHAR(32)   NOT NULL,
    address_id              INT           NOT NULL,
    PRIMARY KEY (restaurant_id),
    UNIQUE (address_id),
    CONSTRAINT fk_restaurant_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);

CREATE TABLE delivery_area
(
    delivery_area_id    SERIAL      NOT NULL,
    country             VARCHAR(32) NOT NULL,
    city                VARCHAR(32) NOT NULL,
    postal_code         VARCHAR(32) NOT NULL,
    street              VARCHAR(32) NOT NULL,
    PRIMARY KEY (delivery_area_id)
);

CREATE TABLE restaurant_delivery_area
(
    restaurant_id       INT      NOT NULL,
    delivery_area_id    INT      NOT NULL,
    PRIMARY KEY (restaurant_id, delivery_area_id),
    CONSTRAINT fk_restaurant_delivery_area_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
    CONSTRAINT fk_restaurant_delivery_area_delivery_area
        FOREIGN KEY (delivery_area_id)
            REFERENCES delivery_area (delivery_area_id)
);