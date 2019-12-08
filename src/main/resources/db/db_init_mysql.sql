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
    quantity float
);

create table if not exists dish
(
    id int primary key auto_increment,
    dish_name varchar(60) not null,
    use_coef float
);

create table dish_item
(
    id int primary key auto_increment,
    ingredient_id int,
    dish_id int,
    quantity float,
    foreign key (ingredient_id) references ingredient (id),
    foreign key (dish_id) references dish (id)
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
    ingredient_id int,
    foreign key (supplier_id) references supplier (id),
    foreign key (ingredient_id) references ingredient (id)
);

create table if not exists supply
(
    id int primary key auto_increment,
    supplier_id int,
    manager_id int,
    created_timestamp timestamp,
    published_timestamp timestamp,
    foreign key (supplier_id) references supplier (id),
    foreign key (manager_id) references manager (id)
);

create table if not exists supply_item
(
    id int primary key auto_increment,
    supply_id int,
    ingredient_id int,
    ingredient_quantity float,
    published boolean,
    foreign key (supply_id) references supply (id),
    foreign key (ingredient_id) references ingredient (id)
);