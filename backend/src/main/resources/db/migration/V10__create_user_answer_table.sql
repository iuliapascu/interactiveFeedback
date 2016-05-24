
CREATE TABLE user_answer
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,
    text VARCHAR(1000),
    percentage INTEGER NOT NULL DEFAULT 0,
    question_id BIGINT,
    course_event_id BIGINT
);

ALTER TABLE user_answer ADD
    CONSTRAINT FK_user_answer_question
    FOREIGN KEY(question_id)
    REFERENCES question(id);

ALTER TABLE user_answer ADD
    CONSTRAINT FK_user_answer_cevent
    FOREIGN KEY(course_event_id)
    REFERENCES course_event(id);