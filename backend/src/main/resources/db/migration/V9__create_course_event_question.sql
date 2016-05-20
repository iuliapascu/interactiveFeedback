
CREATE TABLE course_event_question
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,
    question_state INTEGER NOT NULL,
    course_event_id BIGINT,
    question_id BIGINT
);

ALTER TABLE course_event_question ADD
    CONSTRAINT FK_ceq_course_event
    FOREIGN KEY(course_event_id)
    REFERENCES course_event(id);

ALTER TABLE course_event_question ADD
    CONSTRAINT FK_ceq_question
    FOREIGN KEY(question_id)
    REFERENCES question(id);