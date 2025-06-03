package br.com.bpkedu.library_spring_webmvc.service;

import br.com.bpkedu.library_spring_webmvc.domain.Livro;
import br.com.bpkedu.library_spring_webmvc.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;


    public List<Livro> listarTodos() {

        List<Livro> livros = livroRepository.findAll();

        return livros;
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }

    // NOVO MÉTODO
    public List<Livro> listarDisponiveis() {
        return livroRepository.findAll().stream()
                .filter(Livro::isDisponivel)
                .collect(Collectors.toList());
    }
}