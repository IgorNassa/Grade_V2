package br.sistema.entity;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (length = 20, nullable = false)
    private String nome;

    @Column(nullable = false)
    private Boolean eMedio;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "turma_disciplina",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinas;

    public Turma(Long id, String nome, Boolean eMedio) {
        this.id = id;
        this.nome = nome;
        this.eMedio = eMedio;
    }

    public Turma(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean geteMedio() {
        return eMedio;
    }

    public void seteMedio(Boolean eMedio) {
        this.eMedio = eMedio;
    }
}
