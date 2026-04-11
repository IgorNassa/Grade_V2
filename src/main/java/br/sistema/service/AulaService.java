package br.sistema.service;

import br.sistema.entity.Aula;
import br.sistema.entity.Disciplina;
import br.sistema.entity.Professor;
import br.sistema.entity.Turma;
import br.sistema.repository.AulaRepository;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.util.*;

public class AulaService {

    private AulaRepository aulaRepository;
    private EntityManager em;

    public AulaService(AulaRepository aulaRepository, EntityManager em) {
        this.aulaRepository = aulaRepository;
        this.em = em;
    }

    private final List<DayOfWeek> DIAS_LETIVOS = Arrays.asList(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    );

    public void gerarGrade(Turma turma, Map<Disciplina, Integer> cargaHoraria) {
        int maxSlotsPorDia = 5; // Quantidade fixa de horários diários da turma

        try {
            em.getTransaction().begin();

            // 1. "Desempacota" o Map em uma lista corrida.
            // Exemplo: Se Matemática tem 2 aulas, ela aparece 2 vezes na lista.
            List<Disciplina> aulasPendentes = new ArrayList<>();
            for (Map.Entry<Disciplina, Integer> entry : cargaHoraria.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    aulasPendentes.add(entry.getKey());
                }
            }

            // 2. Passa como um trator pela semana inteira
            for (DayOfWeek dia : DIAS_LETIVOS) {
                for (int slot = 1; slot <= maxSlotsPorDia; slot++) {

                    Aula novaAula = new Aula();
                    novaAula.setTurma(turma);
                    novaAula.setDiaDaSemana(dia);
                    novaAula.setSlotHorario(slot);

                    boolean buracoPreenchido = false;

                    // 3. Tenta achar alguma disciplina da lista pendente que sirva neste horário
                    for (int i = 0; i < aulasPendentes.size(); i++) {
                        Disciplina disciplinaTentativa = aulasPendentes.get(i);
                        Professor professor = new Professor(); // Aqui você busca o professor dessa disciplina

                        // Se o professor NÃO estiver ocupado dando aula para outra turma neste dia/horário:
                        if (!aulaRepository.professorOcupadoNoBanco(professor, dia, slot)) {

                            novaAula.setDisciplina(disciplinaTentativa);
                            novaAula.setProfessor(professor);

                            aulasPendentes.remove(i); // Tira da lista, pois a aula já foi alocada
                            buracoPreenchido = true;
                            break; // Para de testar disciplinas e avança para o próximo slot(horário)
                        }
                    }

                    // 4. Se tentou todas as disciplinas e deu conflito para todas,
                    // OU se a lista de pendentes já zerou, o horário fica sem nada.
                    if (!buracoPreenchido) {
                        novaAula.setDisciplina(null);
                        novaAula.setProfessor(null);
                    }

                    // Salva no banco instantaneamente (seja com disciplina ou como Vaga)
                    aulaRepository.save(novaAula);
                }
            }

            // 5. Verificação de segurança: se sobrou aula na lista, significa que a
            // semana acabou antes de conseguirmos alocar tudo.
            if (!aulasPendentes.isEmpty()) {
                throw new RuntimeException("Não houve horários suficientes sem conflito para alocar toda a carga horária.");
            }

            em.getTransaction().commit();
            System.out.println("Grade gerada! Horários vazios foram marcados como Vaga.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro ao gerar grade: " + e.getMessage());
        }
    }
}