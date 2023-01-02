insert into role(id,nome) values (1, 'ROLE_USER');
insert into role(id,nome) values (2, 'ROLE_ADMIN');

insert into user(id,nome,email,login,senha) values (1,'Admin','admin@gmail.com','admin','$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe');
insert into user(id,nome,email,login,senha) values (2,'User','user@gmail.com','user','$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe');

insert into user_roles(user_id,role_id) values(1, 2);
insert into user_roles(user_id,role_id) values(2, 1);

INSERT INTO produto (id, nome, preco, calorias, vegano, estoque) VALUES
(1, 'Frango com batata', 19.90, 800, 0, 100),
(2, 'Salada de batata', 12.90, 400, 1, 100),
(3, 'Escondidinho de batata com carne', 21.90, 700, 0, 100),
(4, 'Batata Frita', 12.90, 400, 1, 100),
(5, 'Purê de batata', 11.90, 450, 1, 100);