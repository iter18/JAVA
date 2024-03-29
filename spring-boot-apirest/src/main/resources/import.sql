INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('José Antonio','Ojeda','ti0911342o@gmail.com','2022-10-29');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('José Aarón','Ojeda','aaron@gmail.com','2022-10-29');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Viviana','Bonifacio','viviboes@gmail.com','2022-10-29');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Omar','Bonifacio','nac_72@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Rebeca','Ojeda Martínez','rebeca@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Luis Enrrique','Ojea','luis.ojeda@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Gustavo','Aparicio','gam@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Efren','Jaramillo','ejaramillo@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Ebenezer','Torres','ediaz@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Araceli','Sanches','ara.san@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Roman Armando','Alvarado','roman@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Arturo Jesus','Martinez','artur@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Jairo Jesus','Flores','jjesus@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Miguel','Lerma','mlm@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Alejandro','Macias','almac@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Ramiro Nicolas','Garcia','rng@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Luis Manuel','Garcia','lmga@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Julio Cesar','Garcia','jdg@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Mario','Iglesias','mim@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Héctor Alejandro','Gomez','hgm@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Alondra','Rodriguez','alor@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Gonzalo','Portillo','gpl@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Maritza','Padilla','mpg@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Ana esther','Garcia','agr@gmail.com','2022-11-04');
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES ('Martha Shantal','Diaztoris','msd@gmail.com','2022-11-04');


-- insercion de usuario y roles de prueba
INSERT INTO users (username,PASSWORD,enabled) VALUES ('tony','$2a$10$7wiDvuxQkv09PNaQk7RxY.cxfSR43d533hfzxuF2bd81.voQADZYu',1);
INSERT INTO users (username,PASSWORD,enabled) VALUES ('admin','$2a$10$sUY19fb.ZSih7k4NRa2yjOlLRTLD8OG23pEiYazWv69jUFFE8GvNy',1);

INSERT INTO authorities (user_id,authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id,authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id,authority) VALUES (2, 'ROLE_ADMIN');