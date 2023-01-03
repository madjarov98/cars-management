CREATE TABLE brands
(
    id          INTEGER PRIMARY KEY auto_increment,
    name        VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);

CREATE TABLE fuels
(
    id          INTEGER PRIMARY KEY auto_increment,
    name        VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);

CREATE TABLE cars
(
    id           INTEGER PRIMARY KEY auto_increment,
    model        VARCHAR NOT NULL,
    release_year INTEGER NOT NULL,
    horse_power  INTEGER NOT NULL,
    brand_id     INTEGER,
    fuel_id      INTEGER,
    foreign key (brand_id) references brands (id),
    foreign key (fuel_id) references fuels (id)
);