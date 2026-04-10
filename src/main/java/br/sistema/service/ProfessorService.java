package br.sistema.service;

import br.sistema.entity.Professor;
import br.sistema.repository.ProfessorRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.internal.SQLStateConversionDelegate;
import org.hibernate.exception.internal.StandardSQLExceptionConverter;

import java.sql.SQLOutput;
import java.util.List;

public class ProfessorService {

    private ProfessorRepository ProfessorRepository;

    public ProfessorService(ProfessorRepository professorRepository){
        this.ProfessorRepository = ProfessorRepository;
    }

    public void salvar(Professor professor) throws PersistenceException {
        if (professor.getNome() == null || professor.getNome().trim().isEmpty()){
            throw new IllegalArgumentException();
        }
        else {
            try {
                Professor professorExiste = ProfessorRepository.findByName(professor.getNome());
                if (professorExiste != null) {
                    System.err.println("Professor já existe");
                }
                ProfessorRepository.save(professor);

            } catch (ConstraintViolationException e ) {
                System.out.println("Erro ao salvar" + e);
            } catch (Exception e) {
                System.out.println("Erro" + e);
            }
        }
    }

    public void alterar (Professor professor){
        if (professor.getNome() == null || professor.getNome().trim().isEmpty()){
            throw new IllegalArgumentException();
        }
        try {
            Professor professorExiste = ProfessorRepository.findByName(professor.getNome());
            if (professorExiste == null) {
                System.err.println("Professor não existe!");
            }

            ProfessorRepository.update(professor);

        } catch (ConstraintViolationException e ) {
            System.out.println("Erro ao atualizar" + e);
        } catch (Exception e) {
            System.out.println("Erro" + e);
        }
    }

    public void excluir (Professor professor){
        try {
            Professor professorExiste = ProfessorRepository.findByName(professor.getNome());

            if (professorExiste == null) {
                System.err.println("Professor não existe!");
            }
            ProfessorRepository.delete(professor);

        } catch (NoResultException e ) {
            System.err.println("[ERROR] Professor não encontrado: " + e);
        } catch (Exception e) {
            System.err.println("[ERROR] Alguma coisa deu erro: " + e);
        }
    }

    public List<Professor> verTodos () {
        try {
            List<Professor> professor = ProfessorRepository.findAll();

            if(professor == null) {
                System.out.println("Lista vazia");
                return null;
            }
            return professor;

        } catch (Exception e) {
            System.err.println("Erro" + e);
            throw e;
        }
    }
}
