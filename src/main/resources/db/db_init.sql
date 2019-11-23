create table if not exists manager
(
    id uuid primary key not null,
    manager_name varchar(45) not null,
    login varchar(30),
    password varchar(36),
    enabled boolean
);

create table if not exists ingredient
(
    id uuid primary key not null,
    ingredient_name varchar(60) not null,
    quantity decimal,
    last_supply_date date
);

create table if not exists dish
(
    id uuid primary key not null,
    dish_name varchar(60) not null,
    use_coef decimal
);

create table if not exists dish_ingredient
(
    dish_id uuid,
    ingredient_id uuid,
    ingredient_quantity decimal
);

create table if not exists supplier
(
    id uuid primary key not null,
    supplier_name varchar(45) not null,
    time_weekdays_begin time,
    time_weekdays_end time,
    time_weekends_begin time,
    time_weekends_end time
);

create table if not exists supplier_ingredient
(
    supplier_id uuid,
    ingredient_id uuid
);

create table if not exists supply
(
    supplier_id uuid,
    user_id uuid,
    supply_date uuid
);

create table if not exists supply_ingredient
(
    supply_id uuid,
    ingredient_id uuid,
    ingredient_quantity decimal
);