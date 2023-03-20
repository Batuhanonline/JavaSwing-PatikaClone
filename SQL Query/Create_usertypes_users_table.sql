CREATE TABLE user_types (
	id SERIAL PRIMARY KEY,
	type VARCHAR(55) UNIQUE
);

INSERT INTO user_types 
(type) VALUES 
	('student'),
	('operator'),
	('educator');
	
	
CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	fullname VARCHAR(255),
	username VARCHAR(255),
	pass VARCHAR(255),
	user_type VARCHAR REFERENCES user_types(type)
);