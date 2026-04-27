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
                throw new IllegalArgumentException("Nome do professor obrigatório.");
            }

            Professor existe = professorRepository.findByName(professor.getNome());
            if (existe != null) {
                throw new RuntimeException("Professor já cadastrado!");
            }

            professorRepository.save(professor);
        } catch (Exception e) {
            System.err.println("[ERRO SERVICE] " + e.getMessage());
            throw e;
        }
    }

    public void update(Professor professor) {
        try {
            if (professor.getNome() == null || professor.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome inválido para atualização.");
            }

            Professor professorExiste = professorRepository.findByName(professor.getNome().toLowerCase().trim());
            if (professorExiste == null) {
                throw new RuntimeException("Professor não encontrado para atualizar.");
            }

            professorRepository.update(professor);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação update: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o professor.", e);
        }
    }

    public void delete(Professor professor) {
        try {
            // 1. Busca o professor real no banco pelo nome (usando o ignore case que aplicamos no repo)
            Professor professorNoBanco = professorRepository.findByName(professor.getNome().trim());

            if (professorNoBanco == null) {
                throw new RuntimeException("Professor não encontrado para exclusão: " + professor.getNome());
            }

            // 2. Passa o objeto "anexado" (managed) para o repositório deletar
            professorRepository.delete(professorNoBanco);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação delete: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Professor> findAll() {
        try {
            return professorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar a lista de professores.", e);
        }
    }

    public Professor findByName(String nome) {
        try {
            return professorRepository.findByName(nome.toLowerCase().trim());
        } catch (Exception e) {
            return null;
        }
    }
}