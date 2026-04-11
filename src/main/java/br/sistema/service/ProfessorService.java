package br.sistema.service;

import br.sistema.entity.Professor;
import br.sistema.repository.ProfessorRepository;
import jakarta.persistence.NoResultException;
<<<<<<< HEAD
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.internal.SQLStateConversionDelegate;
import org.hibernate.exception.internal.StandardSQLExceptionConverter;

import java.sql.SQLOutput;
=======

>>>>>>> ea40ec5380f1bc1674d4e4ded225256ba8802962
import java.util.List;

public class ProfessorService {

<<<<<<< HEAD
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
=======
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }

    public void salvar(Professor professor) {

        if (professor.getNome() == null || professor.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        Professor professorExiste = professorRepository.findByName(professor.getNome());

        if (professorExiste != null) {
            throw new RuntimeException("Professor já existe");
        }

        try {
            professorRepository.save(professor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar professor", e);
>>>>>>> ea40ec5380f1bc1674d4e4ded225256ba8802962
        }
    }

    public void alterar (Professor professor){
<<<<<<< HEAD
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
=======

        if (professor.getNome() == null || professor.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("Nome inválido");
        }

        Professor professorExiste = professorRepository.findByName(professor.getNome());

        if (professorExiste == null) {
            throw new RuntimeException("Professor não existe");
        }

        try {
            professorRepository.update(professor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar professor", e);
>>>>>>> ea40ec5380f1bc1674d4e4ded225256ba8802962
        }
    }

    public void excluir (Professor professor){
<<<<<<< HEAD
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
=======

        Professor professorExiste = professorRepository.findByName(professor.getNome());

        if (professorExiste == null) {
            throw new RuntimeException("Professor não existe");
        }

        try {
            professorRepository.delete(professor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir professor", e);
>>>>>>> ea40ec5380f1bc1674d4e4ded225256ba8802962
        }
    }

    public List<Professor> verTodos () {
<<<<<<< HEAD
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
=======

        try {
            List<Professor> professores = professorRepository.findAll();

            if (professores.isEmpty()) {
                System.out.println("Nenhum professor cadastrado");
            }

            return professores;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar professores", e);
        }
    }
}
>>>>>>> ea40ec5380f1bc1674d4e4ded225256ba8802962
