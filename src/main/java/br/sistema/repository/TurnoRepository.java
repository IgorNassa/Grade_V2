package br.sistema.repository;

import br.sistema.entity.Turno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class TurnoRepository {

    private EntityManager em;

    public TurnoRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Turno turno) {
        em.getTransaction().begin();
        try {
            em.persist(turno);
            em.flush();
            em.refresh(turno);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public List<Turno> findAll() {
        return em.createQuery("select t from Turno t", Turno.class).getResultList();
    }

    public void update(Turno turno) {
        em.getTransaction().begin();
        try {
            em.merge(turno);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void delete(Turno turno) {
        em.getTransaction().begin();
        try {
            em.remove(em.contains(turno) ? turno : em.merge(turno));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public Turno findByName(String nome) {
        try {
            return em.createQuery("SELECT t FROM Turno t WHERE CAST(t.nomeTurno AS string) = :nome", Turno.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}