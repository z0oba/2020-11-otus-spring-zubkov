insert into authors(id, `name`) values (1, 'Dovlatov');
insert into authors(id, `name`) values (2, 'Lermontov');
insert into authors(id, `name`) values (3, 'Bulgakov');

insert into genres(id, `name`) values (1, 'Story');
insert into genres(id, `name`) values (2, 'Poems');
insert into genres(id, `name`) values (3, 'Novel');

insert into books (id, `name`,  author_id, genre_id) values (1, 'Conservancy area', 1, 1);
insert into books (id, `name`,  author_id, genre_id) values (2, 'Mtsyri', 2, 2);
insert into books (id, `name`,  author_id, genre_id) values (3, 'Master and Margarita', 3, 3);

insert into comments (id, `text`, book_id) values (1, 'It`s ok!', 1);
insert into comments (id, `text`, book_id) values (2, 'Not bad', 2);
insert into comments (id, `text`, book_id) values (3, 'Awesome', 3);