set foreign_key_checks = 0;

drop table if exists authors;
create table authors(
id bigint auto_increment primary key,
name varchar(255) unique
);

drop table if exists genres;
create table genres(
id bigint auto_increment primary key,
name varchar(255) unique
);

drop table if exists books;
create table books(
id bigint primary key auto_increment,
name varchar(255),
author_id bigint,
constraint books_authors_fk
foreign key (author_id) references authors(id),
genre_id bigint,
constraint books_genre_fk
foreign key (genre_id) references genres(id)
);

drop table if exists comments;
create table comments(
id bigint primary key auto_increment,
text varchar(255),
book_id bigint,
constraint comments_books_fk
foreign key (book_id) references books(id)
on delete cascade
);

drop table if exists users;
create table users(
id bigint primary key auto_increment,
username varchar(255) unique,
password varchar(255)
);


drop table if exists user_roles;
create table user_roles(
user_id bigint,
roles varchar(255)
);

set foreign_key_checks = 1;