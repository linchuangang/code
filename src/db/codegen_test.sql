-- 创建代码生成工具测试表

drop table if exists t_test_code_gen;
create table t_test_code_gen (
   id int auto_increment primary key,
   name varchar(40),
   user_id char(32),
   is_enable boolean,
   a_small_num smallint,
   a_medium_num mediumint,
   a_tiny_num tinyint,
   a_normal_num int,
   a_unsign_num int unsigned,
   a_big_num bigint,
   a_date date,
   a_datetime datetime,
   a_float_num float,
   a_double_num decimal(10, 2),
   a_double_num2 double,
   a_decimal decimal(10),
   modify_time timestamp,
   creation_time timestamp
);

drop table if exists demo_test_code_gen_1;
create table demo_test_code_gen_1 (
   id int auto_increment primary key,
   name varchar(40),
   user_id char(32),
   is_enable boolean,
   a_small_num smallint,
   a_medium_num mediumint,
   a_tiny_num tinyint,
   a_normal_num int,
   a_unsign_num int unsigned,
   a_big_num bigint,
   a_date date,
   a_datetime datetime,
   a_double_num decimal(10, 2),
   a_decimal decimal(10),
   modify_time timestamp,
   creation_time timestamp
);
