package br.sistema.service;

import br.sistema.entity.Professor;
import br.sistema.repository.ProfessorRepository;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ProfessorService {

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
        }
    }

    public void alterar (Professor professor){

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
        }
    }

    public void excluir (Professor professor){

        Professor professorExiste = professorRepository.findByName(professor.getNome());

        if (professorExiste == null) {
            throw new RuntimeException("Professor não existe");
        }

        try {
            professorRepository.delete(professor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir professor", e);
        }
    }

    public List<Professor> verTodos () {

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