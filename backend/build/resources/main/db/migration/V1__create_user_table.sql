
CREATE TABLE user
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,
    email VARCHAR(50),
    password VARCHAR(255),
    user_name VARCHAR(50),
    first_name VARCHAR(50) DEFAULT '',
    last_name VARCHAR(50) DEFAULT '',
    role VARCHAR(5) DEFAULT 'USER',
    created_timestamp TIMESTAMP NOT NULL,
    last_login TIMESTAMP NULL
);

CREATE UNIQUE INDEX UK_user_name ON user ( user_name );
CREATE UNIQUE INDEX UK_user_email ON user ( email );

