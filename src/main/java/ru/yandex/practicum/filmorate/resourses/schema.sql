DROP TABLE IF EXISTS user;
CREATE TABLE users (
login varchar not null,
name varchar,
email varchar,
birthday date
)