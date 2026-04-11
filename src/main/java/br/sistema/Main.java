package br.sistema;

<<<<<<< HEAD
public class Main {
    public static void main(String[] args) {

=======
import br.sistema.config.JpaUtil;
import br.sistema.entity.Disciplina;
import br.sistema.repository.DisciplinaRepository;
import jakarta.persistence.EntityManager;
import org.flywaydb.core.Flyway;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        // 1. Você precisa abrir a sua conexão (EntityManager)
        EntityManager em = JpaUtil.getEntityManager();

        DisciplinaRepository disciplinaRepository = new DisciplinaRepository(em);

        System.out.println("Executando migrações do banco de dados...");
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/grade", "postgres", "1234")
                .load();

        flyway.migrate();
        System.out.println("Migrações concluídas com sucesso!");



        System.out.println("Sistema iniciado.");

        //Disciplina disciplina = new Disciplina();
        //disciplina.setNome(null);
        List<Disciplina> teste = disciplinaRepository.findAll();


        JpaUtil.getEntityManager().close();
>>>>>>> ea40ec5380f1bc1674d4e4ded225256ba8802962
    }
}