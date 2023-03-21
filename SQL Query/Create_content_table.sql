CREATE TABLE content(
	id SERIAL PRIMARY KEY,
	course_id INTEGER REFERENCES course(id),
	title VARCHAR(255),
	explanation VARCHAR(255),
	link VARCHAR(255)
);

