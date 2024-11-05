package br.com.fiap.gestaocondo.tarefa;

import br.com.fiap.gestaocondo.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "{tarefa.titulo.notnull}")
    @Size(min = 4, max = 255, message = "{tarefa.titulo.size}")
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @NotNull(message = "{tarefa.descricao.notnull}")
    @Size(min = 4, max = 255, message = "{tarefa.descricao.size}")
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @NotNull(message = "{tarefa.score.notnull}")
    @Min(value = 1, message = "{tarefa.score.min}")
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull(message = "{tarefa.status.notnull}")
    @Min(value = 1, message = "{tarefa.status.min}")
    @Max(value = 100, message = "{tarefa.status.max}")
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "id_user_fk")
    )
    private User user;
}
