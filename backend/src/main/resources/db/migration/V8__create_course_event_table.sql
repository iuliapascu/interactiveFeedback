
CREATE TABLE course_event
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,
    name VARCHAR(50),
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    course_id BIGINT
);

ALTER TABLE course_event ADD
    CONSTRAINT FK_course_event_course
    FOREIGN KEY(course_id)
    REFERENCES course(id);