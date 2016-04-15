
CREATE TABLE topic
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,
    title VARCHAR(100),
    position INTEGER NOT NULL DEFAULT 0,
    course_id BIGINT
);

ALTER TABLE topic ADD
    CONSTRAINT FK_topic_course
    FOREIGN KEY(course_id)
    REFERENCES course(id);