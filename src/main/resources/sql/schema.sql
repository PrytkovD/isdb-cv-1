drop table if exists worker;

drop table if exists dish;

create table worker
(
    id       bigserial primary key,
    name     varchar(80),
    password varchar(80)
);

create table dish
(
    id    bigserial primary key,
    name  varchar(80),
    price int4
);