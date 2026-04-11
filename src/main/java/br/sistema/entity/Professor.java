package br.sistema.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nome", nullable = false)

    private Long id;
    private String nome;



    @ManyToMany
    @JoinTable (
            name = "professor_disciplina",
            joinColumns = @JoinColumn(name = "professor_id"),//so define como as duas fks da tabela funcionam
            inverseJoinColumns = @JoinColumn (name = "disciplina_id")
    )
    private List<Disciplina> disciplinas = new ArrayList<>();

    public Professor() {
    }

    public  Professor(Long id, String nome, List<Disciplina> disciplinas) {
        this.id = id;
        this.nome = nome;
        this.disciplinas = disciplinas;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

}


