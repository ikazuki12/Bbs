create table messages (
	id int auto_increment primary key,
	user_id int not null,
	subject varchar(50) not null,
	text varchar(1000) not null,
	category varchar(10) not null,
	insert_date timestamp not null
);