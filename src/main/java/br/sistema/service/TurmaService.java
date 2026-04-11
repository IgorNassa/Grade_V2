package br.sistema.service;

import br.sistema.entity.Turma;
import br.sistema.repository.TurmaRepository;
import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class TurmaService {

    private TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public void salvar(Turma turma){
        if (turma.getNome() == null || turma.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da turma obrigatório.");
        }

        try {
            Turma turmaExiste = turmaRepository.findByName(turma.getNome());
            if (turmaExiste != null) {
                System.err.println("Já existe uma Turma com este nome!");
                return;
            }
            turmaRepository.salvar(turma);

        } catch (ConstraintViolationException e) {
            System.err.println("Restrição no banco ao salvar turma: " + e);
        } catch (Exception e) {
            System.err.println("Rnesperado ao salvar: " + e);
        }
    }

    public List<Turma> verTodos() {
        try {
            List<Turma> turmas = turmaRepository.findAll();

            if (turmas == null || turmas.isEmpty()) {
                System.err.println("Lista de turmas vazia.");
                return null;
            }
            return turmas;

        } catch (Exception e) {
            System.err.println("Erro ao buscar turmas: " + e);
            throw e;
        }
    }

    public void alterar(Turma turma) {
        if (turma.getId() == null || turma.getNome() == null || turma.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Dados inválidos para atualização.");
        }

        try {
            Turma existente = turmaRepository.findById(turma.getId());
            if (existente == null) {
                System.err.println("Turma não existe para ser alterada!");
                return;
            }
            turmaRepository.update(turma);

        } catch (Exception e) {
            System.err.println("Erro ao atualizar turma: " + e);
        }
    }

    public void excluir(Turma turma) {
        try {
            Turma existente = turmaRepository.findById(turma.getId());

            if (existente == null) {
                System.err.println("Turma não existe!");
                return;
            }
            turmaRepository.delete(existente);

        } catch (NoResultException e) {
            System.err.println("Turma não encontrada: " + e);
        } catch (Exception e) {
            System.err.println("[ERROR] Erro ao excluir: " + e);
        }
    }
}