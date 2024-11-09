CREATE TABLE cart
(
    cart_id             SERIAL              NOT NULL,
    total_price         NUMERIC(19, 2)      NOT NULL,
    customer_id         INT                 NOT NULL,
    restaurant_id       INT                 NOT NULL,
    PRIMARY KEY (cart_id),
    CONSTRAINT fk_cart_customer
            FOREIGN KEY (customer_id)
                REFERENCES customer (customer_id),
    CONSTRAINT fk_cart_restaurant
            FOREIGN KEY (restaurant_id)
                REFERENCES restaurant (restaurant_id)
);

CREATE TABLE order_details
(
    order_details_id    SERIAL                                          NOT NULL,
    order_number        VARCHAR(64)                                     NOT NULL,
    received_time       TIMESTAMP WITH TIME ZONE                        NOT NULL,
    to_cancel_time      TIMESTAMP WITH TIME ZONE                        NOT NULL,
    order_value         NUMERIC(19, 2)                                  NOT NULL,
    order_status        VARCHAR                                         NOT NULL,
    customer_id         INT                                             NOT NULL,
    restaurant_id       INT                                             NOT NULL,
    PRIMARY KEY (order_details_id),
    UNIQUE (order_number),
    CONSTRAINT fk_order_details_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_order_details_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id)
);

CREATE TABLE order_item
(
    order_item_id       SERIAL              NOT NULL,
    quantity            INT                 NOT NULL,
    order_item_value    NUMERIC(19, 2)      NOT NULL,
    food_id             INT                 NOT NULL,
    cart_id             INT,
    order_details_id    INT,
    PRIMARY KEY (order_item_id),
    CONSTRAINT fk_order_item_food
        FOREIGN KEY (food_id)
            REFERENCES food (food_id),
    CONSTRAINT fk_order_item_cart
        FOREIGN KEY (cart_id)
            REFERENCES cart (cart_id),
    CONSTRAINT fk_order_item_order_details
        FOREIGN KEY (order_details_id)
            REFERENCES order_details (order_details_id)
);