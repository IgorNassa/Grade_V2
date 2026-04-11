package br.sistema.view;

import br.sistema.entity.Disciplina;
import br.sistema.entity.Professor;
import br.sistema.entity.Turma;
import br.sistema.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner scanner;
    private DisciplinaService disciplinaService;
    private ProfessorService professorService;
    private TurmaService turmaService;
    private AulaService aulaService; // Certifica-te que criaste o AulaService

    public MenuPrincipal(DisciplinaService ds, ProfessorService ps, TurnoService tns, TurmaService ts, AulaService as) {
        this.scanner = new Scanner(System.in);
        this.disciplinaService = ds;
        this.professorService = ps;
        this.turnoService = tns;
        this.turmaService = ts;
        this.aulaService = as;
    }

    public void iniciar() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE GERAÇÃO DE GRADE (SGDG)");
            System.out.println("========================================");
            System.out.println("1. Gerenciar Disciplinas");
            System.out.println("2. Gerenciar Professores");
            System.out.println("3. Gerenciar Turnos");
            System.out.println("4. Gerenciar Turmas");
            System.out.println("5. Gerenciar Aulas (Gerador de Grade)");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1: menuDisciplinas(); break;
                    case 2: menuProfessores(); break;
                    case 3: menuTurnos(); break;
                    case 4: menuTurmas(); break;
                    case 5: menuAulas(); break;
                    case 0: System.out.println("Encerrando o sistema..."); break;
                    default: System.out.println("[AVISO] Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite apenas números.");
            } catch (Exception e) {
                System.out.println("[ERRO CRÍTICO] " + e.getMessage());
            }
        }
    }

    // =========================================================
    // MENU DISCIPLINAS
    // =========================================================
    private void menuDisciplinas() {
        System.out.println("\n--- MENU DISCIPLINAS ---");
        System.out.println("1. Cadastrar | 2. Ver Todas");
        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Nome da Disciplina: ");
            String nome = scanner.nextLine();
            Disciplina d = new Disciplina();
            d.setNome(nome);
            try {
                disciplinaService.salvar(d);
                System.out.println("Disciplina salva com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } else if (opcao == 2) {
            List<Disciplina> lista = disciplinaService.verTodos();
            if(lista != null) {
                lista.forEach(d -> System.out.println("ID: " + d.getId() + " - " + d.getNome()));
            }
        }
    }

    // =========================================================
    // MENU PROFESSORES (Exemplo de Integração)
    // =========================================================
    private void menuProfessores() {
        System.out.println("\n--- MENU PROFESSORES ---");
        System.out.println("1. Cadastrar | 2. Ver Todos");
        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Nome do Professor: ");
            String nome = scanner.nextLine();

            System.out.print("Nome da Disciplina que ele leciona: ");
            String nomeDisc = scanner.nextLine();

            // Aqui buscamos a disciplina no banco para vincular ao professor
            // Lembre-se de criar o método buscarPorNome no DisciplinaService
            Disciplina disc = null; // disciplinaService.buscarPorNome(nomeDisc);

            if(disc == null) {
                System.err.println("Disciplina não encontrada. Cadastre a disciplina primeiro.");
                return;
            }

            Professor p = new Professor();
            p.setNome(nome);
            p.setDisciplina(disc);

            try {
                professorService.save(p);
                System.out.println("Professor salvo com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
        // Lógica de ver todos omitida para brevidade...
    }

    // =========================================================
    // MENUS TURNOS E TURMAS (Omitidos - Seguem a mesma lógica)
    // =========================================================
    private void menuTurnos() { System.out.println("Funcionalidade delegada ao Pedro..."); }
    private void menuTurmas() { System.out.println("Funcionalidade delegada ao Allan..."); }


    // =========================================================
    // MENU AULAS (O Gerador de Grade)
    // =========================================================
    private void menuAulas() {
        System.out.println("\n--- GERADOR DE GRADE ---");
        System.out.println("1. Gerar Grade para uma Turma");
        System.out.println("2. Ver Grade de uma Turma");
        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Qual o nome da Turma para gerar a grade? ");
            String nomeTurma = scanner.nextLine();

            // Simulação da busca (Vocês precisam criar esse método no TurmaService)
            // Turma turma = turmaService.buscarPorNome(nomeTurma);
            Turma turma = new Turma(); // Substituir pela busca real no banco

            if (turma == null) {
                System.out.println("Turma não encontrada.");
                return;
            }

            // O parâmetro dinâmico vem direto do turno atrelado à turma!
            int maxHorarios = 5; // Substituir por: turma.getTurno().getMaxHorariosPorDia();

            System.out.println("\n-- Definição da Carga Horária Semanal --");
            Map<Disciplina, Integer> cargaHoraria = new HashMap<>();

            List<Disciplina> disciplinasDisponiveis = disciplinaService.verTodos();
            for (Disciplina d : disciplinasDisponiveis) {
                System.out.print("Quantas aulas semanais de " + d.getNome() + " (0 para nenhuma)? ");
                int qtd = Integer.parseInt(scanner.nextLine());
                if (qtd > 0) {
                    cargaHoraria.put(d, qtd);
                }
            }

            System.out.println("\nProcessando o Algoritmo Guloso de grade...");
            try {
                // Chama o algoritmo que criamos antes!
                aulaService.gerarGrade(turma, cargaHoraria, maxHorarios);
            } catch (Exception e) {
                System.err.println("Falha ao gerar grade: " + e.getMessage());
            }

        } else if (opcao == 2) {
            // Lógica para ler as aulas do banco e imprimir na tela
            System.out.println("Em desenvolvimento...");
        }
    }
}