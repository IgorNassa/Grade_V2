package br.sistema.service;

import br.sistema.entity.Turno;
import br.sistema.repository.TurnoRepository;
import jakarta.persistence.NoResultException;

import java.util.List;

public class TurnoService {

    private TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public void salvar(Turno turno) {
        if (turno.getNomeTurno() == null) {
            throw new IllegalArgumentException("Nome invalido.");
        }

        if (turno.getInicioTurno() == null || turno.getFimTurno() == null) {
            throw new IllegalArgumentException("Horario vazio.");
        }

        if (turno.getInicioTurno().isAfter(turno.getFimTurno())) {
            throw new IllegalArgumentException("Hora de início não pode ser maior que a hora fim.");
        }

        if (turno.getTempoAula() == null || turno.getTempoAula() <= 0) {
            throw new IllegalArgumentException("Tempo de aula inválido.");
        }

        if (turno.getAulasTurno() == null || turno.getAulasTurno() <= 0) {
            throw new IllegalArgumentException("Quantidade de aulas inválida.");
        }

        if (turno.getAulasAntesIntervalo() == null || turno.getAulasAntesIntervalo() <= 0) {
            throw new IllegalArgumentException("Quantidade de aulas antes do intervalo inválida.");
        }

        if (turno.getAulasAntesIntervalo() > turno.getAulasTurno()) {
            throw new IllegalArgumentException("Aulas antes do intervalo não pode ser maior que a quantidade total de aulas.");
        }

        try {
            turnoRepository.save(turno);
            System.out.println("Turno salvo com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar turno: " + e.getMessage());
        }
    }

    public void alterar(Turno turno) {
        if (turno.getNomeTurno() == null) {
            throw new IllegalArgumentException("Nome do invalido.");
        }

        if (turno.getInicioTurno() == null || turno.getFimTurno() == null) {
            throw new IllegalArgumentException("Horário de início e fim são obrigatórios.");
        }

        if (turno.getInicioTurno().isAfter(turno.getFimTurno())) {
            throw new IllegalArgumentException("Hora de início não pode ser maior que a hora fim.");
        }

        try {
            turnoRepository.update(turno);
            System.out.println("Turno atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar turno: " + e.getMessage());
        }
    }

    public void excluir(Turno turno) {
        try {
            turnoRepository.delete(turno);
            System.out.println("Turno excluído com sucesso!");
        } catch (NoResultException e) {
            System.err.println("Turno não encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao excluir turno: " + e.getMessage());
        }
    }

    public List<Turno> verTodos() {
        try {
            List<Turno> turnos = turnoRepository.findAll();

            if (turnos == null || turnos.isEmpty()) {
                System.out.println("Lista de turnos vazia.");
                return null;
            }

            return turnos;
        } catch (Exception e) {
            System.err.println("Erro ao listar turnos: " + e.getMessage());
            throw e;
        }
    }
}