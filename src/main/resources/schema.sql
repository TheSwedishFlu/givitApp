DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(25) NOT NULL ,
   orgnr INT NOT NULL,
   city VARCHAR(25) NOT NULL,
   email VARCHAR(25) NOT NULL,
   password VARCHAR(25) NOT NULL
);

DROP TABLE IF EXISTS items;

create table Item(
  ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  name nchar(50) not null,
  description nchar(600) not null,
  category varchar(50) null,
  location nchar(50) not null,
  qty bigint not null,
  delivery_type varchar(50) not null,
  image varchar(100)not null,
  orgnr bigint not null


);