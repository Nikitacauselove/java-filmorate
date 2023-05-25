drop table friendship if exists;
drop table film_like if exists;
drop table users if exists;
drop table film_genre if exists;
drop table films if exists;
drop table mpa if exists;
drop table genre if exists;

create table if not exists mpa (
    mpa_id int primary key,
    name varchar(16) not null unique
);

create table if not exists genre (
    genre_id int primary key,
    name varchar(16) not null unique
);

create table if not exists films (
    film_id int primary key,
    name varchar(64) not null,
    description varchar(64) not null,
    release_date date not null,
    duration int not null,
    mpa_id int references mpa(mpa_id)
);

create table if not exists film_genre (
    film_id int references films(film_id),
    genre_id int references genre(genre_id),
    primary key (film_id, genre_id)
);

create table if not exists users (
    user_id int primary key,
    email varchar(64) not null,
    login varchar(64) not null,
    name varchar(64),
    birthday date not null
);

create table if not exists film_like (
    film_id int references films(film_id),
    user_id int references users(user_id),
    primary key (film_id, user_id)
);

create table if not exists friendship (
    requesting_user_id int references users(user_id),
    receiving_user_id int references users(user_id),
    primary key (requesting_user_id, receiving_user_id)
);