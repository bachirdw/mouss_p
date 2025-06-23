drop table if exists vehicule;
drop table if exists users;

create table users (
    id bigint primary key auto_increment,
    name varchar(255),
    email varchar(255) not null unique,
    password varchar(255),
    role varchar(255)
);

create table vehicule (
    id bigint primary key auto_increment,
    immatriculation varchar(255),
    marque varchar(255),
    modele varchar(255),
    prix_par_jour double,
    disponible boolean not null
);
