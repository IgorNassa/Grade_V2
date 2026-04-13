package br.sistema.service;

import br.sistema.entity.Turma;
import br.sistema.repository.TurmaRepository;
import java.util.List;

public class TurmaService {

    private final TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public void save(Turma turma) {
        try {
            if (turma.getNome() == null || turma.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome da turma é obrigatório.");
            }

            // Busca para evitar duplicados (IgnoreCase)
            Turma existe = turmaRepository.findByName(turma.getNome().trim());
            if (existe != null) {
                throw new RuntimeException("Já existe uma turma com o nome: " + turma.getNome());
            }

            turmaRepository.save(turma);
        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação salvar: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Turma turma) {
        try {
            Turma existente = turmaRepository.findByName(turma.getNome());
            if (existente == null) {
                throw new RuntimeException("Turma não encontrada para atualização.");
            }
            turmaRepository.update(turma);
        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação update: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(Turma turma) {
        try {
            Turma existente = turmaRepository.findByName(turma.getNome());
            if (existente == null) {
                throw new RuntimeException("Turma não encontrada para exclusão.");
            }
            turmaRepository.delete(existente);
        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação delete: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Turma> findAll() {
        return turmaRepository.findAll();
    }

    public Turma findByName(String nome) {
        // No Repository, garanta que a query use LOWER(t.nome) = LOWER(:nome)
        return turmaRepository.findByName(nome.trim());
    }
}