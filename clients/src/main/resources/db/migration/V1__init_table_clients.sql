drop table if exists clients;
create table clients(
                      id bigint not null auto_increment,
                      first_name varchar(50) not null,
                      last_name varchar(50) not null,
                      email varchar(30) not null,
                      password varchar(100) not null,
                      status varchar(50) not null,
                      ticket varchar(50) not null,
                      role varchar(50) not null,
    PRIMARY KEY (id)
);