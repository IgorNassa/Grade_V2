package br.sistema.service;

import br.sistema.entity.Professor;
import br.sistema.repository.ProfessorRepository;
import java.util.List;

public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void save(Professor professor) {
        try {
            if (professor.getNome() == null || professor.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("O nome do professor não pode ser vazio.");
            }

            Professor professorExiste = professorRepository.findByName(professor.getNome());

            if (professorExiste != null) {
                throw new RuntimeException("Já existe um professor cadastrado com este nome.");
            }

            professorRepository.save(professor);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação salvar: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar o professor.", e);
        }
    }

    public void update(Professor professor) {
        try {
            if (professor.getNome() == null || professor.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome inválido para atualização.");
            }

            Professor professorExiste = professorRepository.findByName(professor.getNome());

            if (professorExiste == null) {
                throw new RuntimeException("Professor não encontrado na base de dados para atualizar.");
            }

            professorRepository.update(professor);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação update: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o professor.", e);
        }
    }

    public void delete(Professor professor) {
        try {
            Professor professorExiste = professorRepository.findByName(professor.getNome());

            if (professorExiste == null) {
                throw new RuntimeException("Professor não encontrado na base de dados para exclusão.");
            }

            professorRepository.delete(professor);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação delete: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir o professor.", e);
        }
    }

    public List<Professor> findAll() {
        try {
            List<Professor> professores = professorRepository.findAll();

            if (professores == null || professores.isEmpty()) {
                System.out.println("Nenhum professor cadastrado no momento.");
            }

            return professores;

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação findAll: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar a lista de professores.", e);
        }
    }

    public Professor findByName(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("O nome para busca não pode ser vazio.");
            }

            return professorRepository.findByName(nome);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação findByName: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar o professor pelo nome.", e);
        }
    }
}