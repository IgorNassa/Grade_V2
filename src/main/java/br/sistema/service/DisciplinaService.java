package br.sistema.service;

import br.sistema.entity.Disciplina;
import br.sistema.repository.DisciplinaRepository;

import java.util.List;

public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository){
        this.disciplinaRepository = disciplinaRepository;
    }

    public void save(Disciplina disciplina) {
        try {
            if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()){
                throw new IllegalArgumentException("O nome da disciplina não pode ser vazio.");
            }

            Disciplina disciplinaExiste = disciplinaRepository.findByName(disciplina.getNome());

            if (disciplinaExiste != null) {
                throw new RuntimeException("Já existe uma disciplina cadastrada com este nome: " + disciplina.getNome());
            }

            disciplinaRepository.save(disciplina);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação salvar: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar a disciplina.", e);
        }
    }

    public void update(Disciplina disciplina) {
        try {
            if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()){
                throw new IllegalArgumentException("Nome inválido para atualização.");
            }

            Disciplina disciplinaExiste = disciplinaRepository.findByName(disciplina.getNome());

            if (disciplinaExiste == null) {
                throw new RuntimeException("Disciplina não encontrada na base de dados para atualizar.");
            }

            disciplinaRepository.update(disciplina);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação update: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar a disciplina.", e);
        }
    }

    public void delete(Disciplina disciplina) {
        try {
            Disciplina disciplinaExiste = disciplinaRepository.findByName(disciplina.getNome());

            if (disciplinaExiste == null) {
                throw new RuntimeException("Disciplina não encontrada na base de dados para exclusão.");
            }

            disciplinaRepository.delete(disciplina);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação delete: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir a disciplina.", e);
        }
    }

    public List<Disciplina> findAll() {
        try {
            List<Disciplina> disciplinas = disciplinaRepository.findAll();

            if (disciplinas == null || disciplinas.isEmpty()) {
                System.out.println("Nenhuma disciplina cadastrada no momento.");
            }

            return disciplinas;

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação findAll: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar a lista de disciplinas.", e);
        }
    }

    // ==========================================
    // NOVO MÉTODO: FIND BY NAME
    // ==========================================
    public Disciplina findByName(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("O nome para busca não pode ser vazio.");
            }

            return disciplinaRepository.findByName(nome);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação findByName: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar a disciplina pelo nome.", e);
        }
    }
}