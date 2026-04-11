package br.sistema.repository;

import br.sistema.entity.Disciplina;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DisciplinaRepository {

    private EntityManager em;

    public DisciplinaRepository(EntityManager em) {
        this.em = em;
    }

    //*C*RUD - Função de inserir no banco
    public void save (Disciplina disciplina){
        em.getTransaction().begin();
        em.persist(disciplina);
        em.getTransaction().commit();
    }

    //C*R*UD - Função de listar todos do banco
    public List<Disciplina> findAll(){
        return em.createQuery(
                "from Disciplina", Disciplina.class
        ).getResultList();
    }

    //CR*U*D - Função de atualizar dados do banco
    public void update (Disciplina discplina){
        em.getTransaction().begin();
        em.merge(discplina);
        em.getTransaction().commit();
    }

    //CRU*D* - Função de deletar dados do banco
    public void delete (Disciplina disciplina){
        em.getTransaction().begin();
        em.remove(disciplina);
        em.getTransaction().commit();
    }

    //C*R*UD - Função de listar por nome do banco
    public Disciplina findByName(String nome){
        return em.createQuery("SELECT d FROM Disciplina d WHERE d.nome = :nome", Disciplina.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }
}
