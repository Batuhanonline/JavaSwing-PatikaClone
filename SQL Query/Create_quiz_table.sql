CREATE TABLE quiz (
	id SERIAL PRIMARY KEY,
	content_id INTEGER REFERENCES content(id),
	question VARCHAR(1000),
	option1 VARCHAR(500),
	option2 VARCHAR(500),
	option3 VARCHAR(500),
	option4 VARCHAR(500),
	answer VARCHAR(1)
);