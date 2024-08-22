CREATE TABLE restaurant
(
    restaurant_id           SERIAL        NOT NULL,
    name                    VARCHAR(32)   NOT NULL,
    image_data              BYTEA,
    address_id              INT           NOT NULL,
    PRIMARY KEY (restaurant_id),
    UNIQUE (name),
    UNIQUE (address_id),
    CONSTRAINT fk_restaurant_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);
