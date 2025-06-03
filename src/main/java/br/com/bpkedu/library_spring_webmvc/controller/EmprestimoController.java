package br.com.bpkedu.library_spring_webmvc.controller;

import br.com.bpkedu.library_spring_webmvc.domain.Emprestimo;
import br.com.bpkedu.library_spring_webmvc.service.EmprestimoService;
import br.com.bpkedu.library_spring_webmvc.service.LivroService;
import br.com.bpkedu.library_spring_webmvc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public String listarEmprestimos(Model model) {
        model.addAttribute("emprestimos", emprestimoService.listarTodos());
        return "emprestimos/listar";
    }

    @GetMapping("/novo")
    public String formularioNovoEmprestimo(Model model) {
        model.addAttribute("livrosDisponiveis", livroService.listarDisponiveis());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("emprestimo", new Emprestimo()); // Para o form-backing object, embora usemos parâmetros
        return "emprestimos/novo";
    }

    @PostMapping("/salvar")
    public String salvarEmprestimo(@RequestParam("livroId") Long livroId,
                                   @RequestParam("usuarioId") Long usuarioId,
                                   @RequestParam("dataDevolucaoPrevista") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoPrevista,
                                   RedirectAttributes redirectAttributes) {
        try {
            emprestimoService.registrarEmprestimo(livroId, usuarioId, dataDevolucaoPrevista);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Empréstimo registrado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/emprestimos/novo"; // Volta para o formulário em caso de erro
        }
        return "redirect:/emprestimos/listar";
    }

    @PostMapping("/devolver/{id}")
    public String devolverLivro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            emprestimoService.registrarDevolucao(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Devolução registrada com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/emprestimos/listar";
    }
}