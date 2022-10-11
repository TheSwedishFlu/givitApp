create table Item(
  ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  name nchar(50) not null,
  description nchar(600) not null,
  category varchar(50) not null,
  location nchar(50) not null,
  qty bigint not null,
  deliveryType varchar(50) not null,
  image nchar(100)not null

);

create table Users(
  ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) not null,
    orgnr bigint not null,
    city varchar(50) not null,
    email nchar (60) not null,
    password nchar (60) not null

)