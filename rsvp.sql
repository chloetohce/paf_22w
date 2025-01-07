drop database if exists rsvp;
create database rsvp;
use rsvp;

create table rsvp (
    name varchar(64) default 'Blank',
    email varchar(64) not null,
    phone char(8) not null,
    confirmDate date,
    comments text,

    constraint pk_email primary key(email)
);

grant all privileges on rsvp.* to 'chloe'@'%';
flush privileges;