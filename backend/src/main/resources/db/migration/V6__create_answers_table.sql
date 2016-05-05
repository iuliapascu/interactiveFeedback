
CREATE TABLE answer
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    version INTEGER NOT NULL,
    text VARCHAR(500),
    is_correct BIT NOT NULL DEFAULT 0,
    position INTEGER NOT NULL DEFAULT 0,
    question_id BIGINT
);

ALTER TABLE answer ADD
    CONSTRAINT FK_answer_question
    FOREIGN KEY(question_id)
    REFERENCES question(id);