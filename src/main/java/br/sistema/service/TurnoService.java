package br.sistema.service;

import br.sistema.entity.Turno;
import br.sistema.repository.TurnoRepository;
import java.util.List;

public class TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public void save(Turno turno) {
        try {
            validarTurno(turno);

            String nomeStr = turno.getNomeTurno().toString();
            Turno existe = turnoRepository.findByName(nomeStr);
            if (existe != null) {
                throw new RuntimeException("Já existe um turno cadastrado como " + nomeStr);
            }

            turnoRepository.save(turno);
        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação salvar: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Turno turno) {
        try {
            validarTurno(turno);
            turnoRepository.update(turno);
        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação update: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(Turno turno) {
        try {
            Turno existe = turnoRepository.findByName(turno.getNomeTurno().toString());
            if (existe == null) {
                throw new RuntimeException("Turno não encontrado para exclusão.");
            }
            turnoRepository.delete(existe);
        } catch (Exception e) {
            System.err.println("[ERRO] Falha na operação delete: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    public Turno findByName(String nome) {
        return turnoRepository.findByName(nome.toUpperCase().trim());
    }

    private void validarTurno(Turno t) {
        if (t.getNomeTurno() == null) throw new IllegalArgumentException("Nome inválido.");
        if (t.getInicioTurno() == null || t.getFimTurno() == null) throw new IllegalArgumentException("Horário vazio.");
        if (t.getInicioTurno().isAfter(t.getFimTurno())) throw new IllegalArgumentException("Início após o fim.");
        if (t.getTempoAula() == null || t.getTempoAula() <= 0) throw new IllegalArgumentException("Tempo de aula inválido.");
        if (t.getAulasTurno() == null || t.getAulasTurno() <= 0) throw new IllegalArgumentException("Qtd aulas inválida.");
    }
}