--
-- File generated with SQLiteStudio v3.3.3 on Fri May 28 00:19:20 2021
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: assets
DROP TABLE IF EXISTS assets;

CREATE TABLE assets (
    asset_id          INT           NOT NULL,
    asset_name        VARCHAR (16)  NOT NULL,
    asset_description VARCHAR (256) DEFAULT NULL,
    PRIMARY KEY (
        asset_id
    )
);


-- Table: orders
DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    order_id          INTEGER      NOT NULL
                                   PRIMARY KEY AUTOINCREMENT,
    order_type        VARCHAR (4)  NOT NULL,
    organisation_id   INT          NOT NULL,
    asset_id          INT          NOT NULL,
    placed_quantity   INT          NOT NULL
                                   DEFAULT 0,
    resolved_quantity INT          NOT NULL
                                   DEFAULT 0,
    price             DECIMAL (2)  NOT NULL,
    order_date        VARCHAR (50) NOT NULL
                                   DEFAULT CURRENT_TIMESTAMP,
    finished_date     VARCHAR (50) DEFAULT NULL,
    status            VARCHAR (10) NOT NULL
                                   DEFAULT ('placed') 
);

INSERT INTO orders (
                       order_id,
                       order_type,
                       organisation_id,
                       asset_id,
                       placed_quantity,
                       resolved_quantity,
                       price,
                       order_date,
                       finished_date,
                       status
                   )
                   VALUES (
                       1,
                       'BUY',
                       0,
                       9,
                       99,
                       0,
                       10,
                       '2021-05-06T16:52:00',
                       '2021-05-06T16:52:00',
                       'PENDING'
                   );


-- Table: organisationalUnits
DROP TABLE IF EXISTS organisationalUnits;

CREATE TABLE organisationalUnits (
    organisation_id   INTEGER      PRIMARY KEY AUTOINCREMENT,
    organisation_name VARCHAR (16) NOT NULL,
    credits           DECIMAL (2)  NOT NULL
                                   DEFAULT 0
);

INSERT INTO organisationalUnits (
                                    organisation_id,
                                    organisation_name,
                                    credits
                                )
                                VALUES (
                                    0,
                                    'QUTT',
                                    9900
                                );


-- Table: stock
DROP TABLE IF EXISTS stock;

CREATE TABLE stock (
    organisation_id INT NOT NULL,
    asset_id        INT NOT NULL,
    asset_quantity  INT NOT NULL
                        DEFAULT 0,
    PRIMARY KEY (
        organisation_id,
        asset_id
    )
);


-- Table: users
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id         INT          NOT NULL,
    fullname        VARCHAR (50) NOT NULL,
    username        VARCHAR (20) NOT NULL,
    password        VARCHAR (32) NOT NULL,
    user_type       VARCHAR (5)  NOT NULL
                                 DEFAULT 'user',
    organisation_id INT          DEFAULT NULL,
    PRIMARY KEY (
        username
    ),
    CONSTRAINT user_organisaion
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
