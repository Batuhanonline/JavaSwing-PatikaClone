CREATE TABLE patikaRegister (
	id SERIAL PRIMARY KEY,
	student_id INTEGER REFERENCES users(id),
	patika_id INTEGER REFERENCES patika(id)
);

