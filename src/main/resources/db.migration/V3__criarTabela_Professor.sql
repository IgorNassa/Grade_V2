CREATE TABLE professor (

    id SERIAL NOT NULL PRIMARY KEY ,
    nome VARCHAR(100) NOT NULL  ,
    CONSTRAINT unique_nome_professor UNIQUE (nome)
);

CREATE TABLE professor_disciplina(
    professor_id INT NOT NULL ,
    disciplina_id INT NOT NULL

    PRIMARY KEY (professor_id,disciplina_id),

    CONSTRAINT fk_professor
    FOREIGN KEY (professor_id)
    REFERENCES professor(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_disciplina
    FOREIGN KEY (disciplina_id)
    REFERENCES professor(id)
    ON DELETE CASCADE
)