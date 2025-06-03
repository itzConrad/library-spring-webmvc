package br.com.bpkedu.library_spring_webmvc.service;

import br.com.bpkedu.library_spring_webmvc.domain.Emprestimo;
import br.com.bpkedu.library_spring_webmvc.domain.Livro;
import br.com.bpkedu.library_spring_webmvc.domain.Usuario;
import br.com.bpkedu.library_spring_webmvc.repository.EmprestimoRepository;
import br.com.bpkedu.library_spring_webmvc.repository.LivroRepository;
import br.com.bpkedu.library_spring_webmvc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Emprestimo registrarEmprestimo(Long livroId, Long usuarioId, LocalDate dataDevolucaoPrevista) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!livro.isDisponivel()) {
            throw new IllegalStateException("Livro não está disponível para empréstimo");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(dataDevolucaoPrevista);

        livro.setDisponivel(false);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo registrarDevolucao(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (emprestimo.getDataDevolucaoReal() != null) {
            throw new IllegalStateException("Este livro já foi devolvido.");
        }

        emprestimo.setDataDevolucaoReal(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }
}