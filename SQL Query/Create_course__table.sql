CREATE TABLE course (
	id SERIAL PRIMARY KEY,
	user_id INTEGER REFERENCES users(id),
	patika_id INTEGER REFERENCES patika(id),
	name VARCHAR(255),
	programing_lang VARCHAR(255)
);

