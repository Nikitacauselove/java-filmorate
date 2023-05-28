INSERT INTO films (film_id, name, description, release_date, duration, mpa_id) VALUES (1, 'Film Updated', 'New film update decription', '1989-04-17', 190, 5);
INSERT INTO films (film_id, name, description, release_date, duration, mpa_id) VALUES (2, 'New film', 'New film about friends', '1999-04-30', 120, 5);

INSERT INTO film_genre (film_id, genre_id) VALUES (2, 1);
INSERT INTO film_genre (film_id, genre_id) VALUES (2, 2);

INSERT INTO users (user_id, email, login, name, birthday) VALUES (1, 'mail@yandex.ru', 'doloreUpdate', 'est adipisicing', '1976-09-20');
INSERT INTO users (user_id, email, login, name, birthday) VALUES (2, 'friend@mail.ru', 'friend', 'friend adipisicing', '1976-08-20');
INSERT INTO users (user_id, email, login, name, birthday) VALUES (3, 'friend@common.ru', 'common', 'common', '2000-08-20');

INSERT INTO friendship (requesting_user_id, receiving_user_id) VALUES (1, 3);
INSERT INTO friendship (requesting_user_id, receiving_user_id) VALUES (2, 3);