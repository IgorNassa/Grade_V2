package br.sistema.repository;

import br.sistema.entity.Professor;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProfessorRepository {

    private EntityManager em;

    public ProfessorRepository(EntityManager em){
        this.em = em;
    }

    public void save (Professor professor ){
        em.getTransaction().begin();
        em.persist(professor);//salva no banco (o professor)
        em.getTransaction().commit();

    }

    public List<Professor> findAll(){
        return em.createQuery("from Professor", Professor.class).getResultList();
    }

    public void update (Professor professor ){
        em.getTransaction().begin();
        em.merge(professor);
        em.getTransaction().commit();
    }

    public void delete (Professor professor ){
        em.getTransaction().begin();
        em.remove(em.contains(professor)?professor: em.merge(professor));//se o professor esta presente remove , senao ele adiciona e depois remove
        em.getTransaction().commit();
    }

    public Professor findByName(String nome){
        List<Professor> lista = em
                .createQuery("from Professor p where p.nome = :nome",Professor.class)
                .setParameter("nome", nome)
                .getResultList();
        return lista.isEmpty() ? null : lista.get(0);
    }
}

