CREATE TABLE topic_question
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    topic_id BIGINT,
    question_id BIGINT,
    version INTEGER NOT NULL
);

ALTER TABLE topic_question ADD
    CONSTRAINT FK_tq_topic
    FOREIGN KEY(topic_id)
    REFERENCES topic(id);

ALTER TABLE topic_question ADD
    CONSTRAINT FK_tq_question
    FOREIGN KEY(question_id)
    REFERENCES question(id);