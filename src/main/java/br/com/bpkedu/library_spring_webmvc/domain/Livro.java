package br.com.bpkedu.library_spring_webmvc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;
    private String isbn;
    private String editora;

    @Column(nullable = false)
    private boolean disponivel = true;
}