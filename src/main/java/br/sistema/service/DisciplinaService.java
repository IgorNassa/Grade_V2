package br.sistema.service;

import br.sistema.entity.Disciplina;
import br.sistema.repository.DisciplinaRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.internal.SQLStateConversionDelegate;
import org.hibernate.exception.internal.StandardSQLExceptionConverter;

import java.sql.SQLOutput;
import java.util.List;

public class DisciplinaService {

    private DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository){
        this.disciplinaRepository = disciplinaRepository;
    }

    public void salvar(Disciplina disciplina) throws PersistenceException {
        if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()){
            throw new IllegalArgumentException();
        }
        else {
            try {
                Disciplina disciplinaExiste = disciplinaRepository.findByName(disciplina.getNome());
                if (disciplinaExiste != null) {
                    throw new RuntimeException("Já existe um registo com o nome: " + disciplina.getNome());
                }
                disciplinaRepository.save(disciplina);

            } catch (ConstraintViolationException e ) {
                System.out.println("Erro ao salvar" + e);
            } catch (Exception e) {
                System.out.println("Erro" + e);
            }
        }
    }

    public void alterar (Disciplina disciplina){
        if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()){
            throw new IllegalArgumentException();
        }
        try {
            Disciplina disciplinaExiste = disciplinaRepository.findByName(disciplina.getNome());
            if (disciplinaExiste == null) {
                System.err.println("Disciplina não existe!");
            }

            disciplinaRepository.update(disciplina);

        } catch (ConstraintViolationException e ) {
                System.out.println("Erro ao atualizar" + e);
        } catch (Exception e) {
                System.out.println("Erro" + e);
        }
    }

    public void excluir (Disciplina disciplina){
        try {
            Disciplina disciplinaExiste = disciplinaRepository.findByName(disciplina.getNome());

            if (disciplinaExiste == null) {
                System.err.println("Disciplina não existe!");
            }
            disciplinaRepository.delete(disciplina);

        } catch (NoResultException e ) {
            System.err.println("[ERROR] Disciplina não encontrada: " + e);
        } catch (Exception e) {
            System.err.println("[ERROR] Alguma coisa deu erro: " + e);
        }
    }

    public List<Disciplina> verTodos () {
        try {
            List<Disciplina> disciplinas = disciplinaRepository.findAll();

            if(disciplinas == null) {
                System.out.println("Lista vazia");
                return null;
            }
            return disciplinas;

        } catch (Exception e) {
            System.err.println("Erro" + e);
            throw e;
        }
    }
}
