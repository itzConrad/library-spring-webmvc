package br.com.bpkedu.library_spring_webmvc.repository;

import br.com.bpkedu.library_spring_webmvc.domain.Emprestimo;
import br.com.bpkedu.library_spring_webmvc.domain.Livro;
import br.com.bpkedu.library_spring_webmvc.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuario(Usuario usuario);
    List<Emprestimo> findByLivro(Livro livro);
    Optional<Emprestimo> findByLivroAndDataDevolucaoRealIsNull(Livro livro); // Encontrar empréstimo ativo de um livro
}