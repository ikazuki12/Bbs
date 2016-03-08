create table users (
	id int auto_increment primary key,
	login_id varchar(20) not null,
	password varchar(255) not null,
	name varchar(10) not null,
	branch_code int not null,
	Department int not null
);