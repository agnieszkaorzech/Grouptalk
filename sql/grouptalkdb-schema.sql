drop database if exists grouptalkdb;
create database grouptalkdb;

use grouptalkdb;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registered','administrator'),
    FOREIGN KEY (userid) REFERENCES users (id) on delete cascade,
    PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users (id) on delete cascade,
    PRIMARY KEY (token)
);

CREATE TABLE groups (
    id BINARY(16) NOT NULL,
    theme VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE comments(
    id BINARY(16) NOT NULL,
    groupid BINARY(16) NOT NULL,
    creator BINARY(16) NOT NULL,
    title VARCHAR(100) NOT NULL,
    comment VARCHAR(500) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    FOREIGN Key (groupid) REFERENCES groups(id) on delete cascade,
    FOREIGN KEY (creator) REFERENCES users (id) on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE response(
    id BINARY(16) NOT NULL,
    commentid BINARY(16) NOT NULL,
    FOREIGN KEY (commentsid) REFERENCES coments (id) on delete cascade,
    PRIMARY KEY (id)    
    
);

CREATE TABLE users_groups(
    userid BINARY(16) NOT NULL,
    groupid BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users (id) on delete cascade,
    FOREIGN KEY (groupid) REFERENCES groups (id) on delete cascade,
    PRIMARY KEY (userid, groupid)
);

insert into users (id, loginid, password, email, fullname) values (unhex(replace(uuid(),'-','')), 'admin', unhex(md5('1234')), 'a@a', 'A');
select @idadmin:=id from users where loginid='admin';
insert into user_roles (userid, role) values (@idadmin, 'administrator');
insert into auth_tokens (userid, token) values (@idadmin, unhex('11111111111111111111111111111111'));