-- create database
CREATE DATABASE seckill;
use seckill;

-- create table
create  TABLE seckill(
 `sec_id` bigint not null AUTO_INCREMENT COMMENT 'id',
 `sec_name` varchar(120) not null comment 'name',
 `sec_number` int not null comment 'number',
 `start_time` timestamp not null,
 `end_time` timestamp not null,
 `create_time` timestamp not null default current_timestamp ,
 PRIMARY KEY (sec_id),
 KEY idx_start_time(start_time),
 KEY idx_end_time(end_time)
)ENGING=InooDB AUTO_INCREMENT = 1000 DEFAULT CHARSET=utf8;

insert into
    seckill(sec_name, sec_number, start_time, end_time)
values
    ('1000元秒杀iphone11',10,'2019-10-01 00:00:00','2019-10-02 00:00:00'),
    ('2000元秒杀华为mate30',10,'2019-10-01 00:00:00','2019-10-02 00:00:00'),
    ('100元秒杀小米mix',10,'2019-10-01 00:00:00','2019-10-02 00:00:00'),
    ('50元秒杀三星',300,'2019-10-01 00:00:00','2019-10-02 00:00:00');

-- 秒杀成功明细表
create table success_killed(
    `sec_id` bigint not null,
    `user_phone` bigint not null,
    `state` tinyint not null default -1 comment '-1：无效；0：成功；1：已付款；2：已发货',
    `create_time` timestamp not null default current_timestamp ,
    primary key(sec_id,user_phone)/*联合主键,防止用户重复秒杀*/
);