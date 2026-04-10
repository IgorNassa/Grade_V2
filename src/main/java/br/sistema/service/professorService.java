package br.sistema.service;

import br.sistema.entity.Professor;
import br.sistema.repository.ProfessorRepository;


public class ProfessorService {

    private ProfessorRepository repository;

    public ProfessorService(ProfessorRepository Repository){
        this.repository = repository;
    }

    public void cadastrarProfessor(Professor professor){

        Professor existente = repository.findByNome(professor.getNome());

        if(existente != null){            throw new RuntimeException("Ja existe um professor cadastrado com esse nome.");
        }
        repository.save(professor);
    }
}
