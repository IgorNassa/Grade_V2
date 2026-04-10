package br.sistema.repository;

import br.sistema.entity.Disciplina;
import br.sistema.entity.Turma;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TurmaRepository {

    private EntityManager em;

    public TurmaRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar (Turma turma) {
        em.getTransaction().begin();
        em.persist(turma);
        em.getTransaction().commit();
        return;
    }

    public List<Turma> findAll(){
        return em.createQuery(
                "from Turma", Turma.class
        ).getResultList();
    }

    public void update (Turma turma){
        em.getTransaction().begin();
        em.merge(turma);
        em.getTransaction().commit();
    }

    public void delete (Turma turma) {
        em.getTransaction().begin();
        em.remove(turma);
        em.getTransaction().commit();
    }

    public Turma findById(long id) {
        return em.createQuery("SELECT t FROM Turma t WHERE t.id = :id", Turma.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    public Turma findByName(String nome) {
        return em.createQuery("SELECT t FROM Turma t WHERE t.nome = :nome", Turma.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }
}
