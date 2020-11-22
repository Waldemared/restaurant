create table if not exists manager
(
    id serial primary key,
    manager_name varchar(45) not null,
    login varchar(30),
    password varchar(140),
    enabled boolean
);

create table if not exists ingredient
(
    id serial primary key,
    ingredient_name varchar(60) not null,
    quantity float
);

create table if not exists dish
(
    id serial primary key,
    dish_name varchar(60) not null,
    use_coef float
);

create table dish_item
(
    id serial primary key,
    ingredient_id int,
    dish_id int,
    quantity float,
    foreign key (ingredient_id) references ingredient (id),
    foreign key (dish_id) references dish (id)
);

create table if not exists supplier
(
    id serial primary key,
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
    id serial primary key,
    supplier_id int,
    manager_id int,
    created_timestamp timestamp,
    published_timestamp timestamp,
    published boolean,
    foreign key (supplier_id) references supplier (id),
    foreign key (manager_id) references manager (id)
);

create table if not exists supply_item
(
    id serial primary key,
    supply_id int,
    ingredient_id int,
    ingredient_quantity float,
    foreign key (supply_id) references supply (id),
    foreign key (ingredient_id) references ingredient (id)
);