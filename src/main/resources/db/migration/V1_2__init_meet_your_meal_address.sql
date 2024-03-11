CREATE TABLE address
(
    address_id          SERIAL      NOT NULL,
    country             VARCHAR(32) NOT NULL,
    city                VARCHAR(32) NOT NULL,
    postal_code         VARCHAR(32) NOT NULL,
    address             VARCHAR(32) NOT NULL,
    building_number     VARCHAR(32) NOT NULL,
    unit_number         VARCHAR(32),
    PRIMARY KEY (address_id)
);