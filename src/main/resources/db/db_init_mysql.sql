SET GLOBAL time_zone = '+3:00';

create table if not exists manager
(
    id int primary key auto_increment,
    manager_name varchar(45) not null,
    login varchar(30),
    password varchar(140),
    enabled boolean
);

create table if not exists ingredient
(
    id int primary key auto_increment,
    ingredient_name varchar(60) not null,
    quantity decimal,
    last_supply_date date
);

create table if not exists dish
(
    id int primary key auto_increment,
    dish_name varchar(60) not null,
    use_coef decimal
);

create table if not exists dish_ingredient
(
    dish_id int,
    ingredient_id int,
    ingredient_quantity decimal
);

create table if not exists supplier
(
    id int primary key auto_increment,
    supplier_name varchar(45) not null,
    time_weekdays_begin time,
    time_weekdays_end time,
    time_weekends_begin time,
    time_weekends_end time
);

create table if not exists supplier_ingredient
(
    supplier_id int,
    ingredient_id int
);

create table if not exists supply
(
    supplier_id int,
    user_id int,
    supply_date int
);

create table if not exists supply_ingredient
(
    supply_id int,
    ingredient_id int,
    ingredient_quantity decimal
);