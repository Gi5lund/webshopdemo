drop database if exists webshopdemo;
create schema webshopdemo;
use webshopdemo;
create table webshopdemo.products(
                                     id int not null AUTO_INCREMENT primary key,
                                     name varchar(30) not null,
                                     price decimal(13,2) null
);
insert into webshopdemo.products(name,price) values ('Tuborg','25');
insert into webshopdemo.products(name,price) values ('Carlsberg','25');
