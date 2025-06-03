package br.com.bpkedu.library_spring_webmvc.controller;

import br.com.bpkedu.library_spring_webmvc.domain.Livro;
import br.com.bpkedu.library_spring_webmvc.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

    @GetMapping("/listar")
    public String listarLivros(Model model) {
        model.addAttribute("livros", livroService.listarTodos());
        return "livros/listar";
    }

    @GetMapping("/{id:\\d+}")
    public String detalharLivro(@PathVariable Long id, Model model) {
        model.addAttribute("livro", livroService.buscarPorId(id));
        return "livros/detalhes"; // MODIFICADO: de "detalhar" para "detalhes"
    }

    @GetMapping("/novo")
    public String formularioNovoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/novo";
    }

    @PostMapping("/salvar")
    public String salvarLivro(@ModelAttribute Livro livro) {
        livroService.salvar(livro);
        return "redirect:/livros/listar";
    }

    @GetMapping("/editar/{id:\\d+}")
    public String formularioEditarLivro(@PathVariable Long id, Model model) {
        Livro livro = livroService.buscarPorId(id);
        if (livro == null) {
            return "redirect:/livros/listar";
        }
        model.addAttribute("livro", livro);
        return "livros/editar";
    }

    @GetMapping("/deletar/{id:\\d+}")
    public String deletarLivro(@PathVariable Long id) {
        livroService.deletar(id);
        return "redirect:/livros/listar";
    }

    @PostMapping("/editar/{id}")
    public String editarLivro(@PathVariable Long id, @ModelAttribute Livro livro) {
        livro.setId(id);
        livroService.salvar(livro);
        return "redirect:/livros/listar";
    }
}