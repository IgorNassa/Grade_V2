package br.sistema.repository;

import br.sistema.entity.Turno;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TurnoRepository {

    private EntityManager em;

    public TurnoRepository(EntityManager em) { this.em = em;}

    // *C*RUD - Função de inserir no banco
    public void save(Turno turno) {
        em.getTransaction().begin();
        em.persist(turno);
        em.getTransaction().commit();
    }

    // C*R*UD - Função de listar todos do banco
    public List<Turno> findAll() {
        return em.createQuery(
                "select t from Turno t", Turno.class
        ).getResultList();
    }

    // CR*U*D - Função de atualizar dados do banco
    public void update(Turno turno) {
        em.getTransaction().begin();
        em.merge(turno);
        em.getTransaction().commit();
    }

    // CRU*D* - Função de deletar dados do banco
    public void delete(Turno turno) {
        em.getTransaction().begin();
        em.remove(em.contains(turno) ? turno : em.merge(turno));
        em.getTransaction().commit();
    }
}