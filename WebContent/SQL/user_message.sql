create view user_message as
select
	messages.id
	, subject
	, messages.text
	, category
	, name
	, messages.insert_date
from
	users, messages
where
	users.id = messages.user_id