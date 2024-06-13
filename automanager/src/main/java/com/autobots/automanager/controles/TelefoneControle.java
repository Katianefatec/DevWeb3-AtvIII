package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.modelos.TelefoneSelecionador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/telefone")

public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio repositorio;
    @Autowired
    private TelefoneSelecionador selecionador;

    @GetMapping("/telefone/{id}")
    public Telefone obterTelefone(@PathVariable long id) {
        List<Telefone> telefones = repositorio.findAll();
        return selecionador.selecionar(telefones.toArray(new Telefone[0]), id);
    }

    @GetMapping("/telefones")
    public List<Telefone> obterTelefones() {
        List<Telefone> telefones = repositorio.findAll();
        return telefones;
    }

    @PostMapping("/cadastro")
    public void cadastrarTelefone(@RequestBody Telefone telefone) {
        repositorio.save(telefone);
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarTelefone(@PathVariable Long id, @RequestBody Telefone atualizacao) {
        List<Telefone> telefones = repositorio.findAll();
        Telefone telefone = selecionador.selecionar(telefones.toArray(new Telefone[0]), id);
        TelefoneAtualizador atualizador = new TelefoneAtualizador();
        if (telefone != null) {
            atualizador.atualizar(telefone, atualizacao);
            repositorio.save(telefone);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirTelefone(@PathVariable Long id) {
        Optional<Telefone> telefone = repositorio.findById(id);
        if (telefone.isPresent()) {
            repositorio.delete(telefone.get());
        }
    }

}
