insert into usr (id, username, password, active)
values(
	(select next_val from hibernate_sequence),
	'admin',
	'$2a$08$zxdeW7cEtIH636uiFX9Y8uUnomQLpVcpnKOnObATiv7gOlTOv.q4m',
	true
);
-- pass admin123

insert into user_role (user_id, roles)
values ((select id from usr where username = 'admin'), 'CLIENT');

insert into user_role (user_id, roles)
values ((select id from usr where username = 'admin'), 'ADMIN');

update hibernate_sequence
set next_val = (next_val + 1);