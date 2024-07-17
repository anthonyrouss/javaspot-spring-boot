insert into roles (name)
values ('student');

-- STUDENT -> username: student1, password: student1!
insert into users (created_on, password, username, role_id)
values (now(), '$2a$10$F63DNy8at3SLJtWL9RjocOlmQtrQKpilekC4S9W8z8a.3fOh1rZ6O', 'student1', 1);