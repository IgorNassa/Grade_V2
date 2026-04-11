CREATE TABLE aula (
                      id SERIAL PRIMARY KEY,
                      turma_id INT NOT NULL,
                      professor_id INT NOT NULL,
                      disciplina_id INT NOT NULL,
                      dia_da_semana VARCHAR(20) NOT NULL,
                      slot_horario INT NOT NULL,

                      CONSTRAINT fk_aula_turma FOREIGN KEY (turma_id) REFERENCES turma (id),
                      CONSTRAINT fk_aula_professor FOREIGN KEY (professor_id) REFERENCES professor (id),
                      CONSTRAINT fk_aula_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplina (id)
);