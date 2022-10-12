DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(25) NULL ,
   orgnr INT NOT NULL,
   city VARCHAR(25) NULL,
   email VARCHAR(25) NULL,
   password VARCHAR(25) NULL
);

DROP TABLE IF EXISTS items;

create table Item(
  ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  name nchar(50) not null,
  description nchar(600) not null,
  category varchar(50) not null,
  location nchar(50) not null,
  qty bigint not null,
  delivery_type varchar(50) not null,
  image nchar(100)not null

);