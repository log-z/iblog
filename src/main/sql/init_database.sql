-- 数据库
create database blog
character set utf8mb4;

use blog;

-- 管理员表
create table admin (
    admin_id int(11) unsigned zerofill primary key auto_increment,
    admin_name varchar(20) not null,
    admin_email varchar(40) unique not null,
    admin_password char(50) not null
);

-- 用户表
create table user (
    user_id int(11) unsigned zerofill primary key auto_increment,
    user_name varchar(20) not null,
    user_email varchar(40) unique not null,
    user_password char(60) not null
);

-- 文章表
create table article (
    article_id varchar(32) primary key,
    author_id int(11) unsigned zerofill not null,
    title varchar(40) not null,
    content varchar(1000),
    image varchar(38),
    create_time datetime not null,
    constraint fk_author_user foreign key (author_id) references user(user_id)
);
