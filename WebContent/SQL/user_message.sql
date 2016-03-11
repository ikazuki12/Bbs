create view user_message as
select
	messages.id as user_id
	, users.id as message_id
	, subject
	, messages.text
	, category
	, name
	, messages.insert_date
from
	users, messages
where
	users.id = messages.user_id