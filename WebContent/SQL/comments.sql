create table comments (
	id int auto_increment primary key,
	user_id int not null,
	messages_id int not null,
	text varchar(500) not null,
	insert_date timestamp not null
);