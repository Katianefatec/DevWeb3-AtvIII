package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.*;
import com.autobots.automanager.modelos.AdicionadorLinkEmpresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/empresa")
public class EmpresaControle {

    private final EmpresaRepositorio empresaRepositorio;
    private final AdicionadorLinkEmpresa adicionadorLinkEmpresa;

    @GetMapping("/empresa/{id}")
    public ResponseEntity<EntityModel<Empresa>> obterEmpresa(@PathVariable Long id) {
        return empresaRepositorio.findById(id)
                .map(empresa -> {
                    adicionadorLinkEmpresa.adicionarLink(empresa);
                    return ResponseEntity.ok(EntityModel.of(empresa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/empresas")
    public ResponseEntity<CollectionModel<EntityModel<Empresa>>> obterEmpresas() {
        List<Empresa> empresas = empresaRepositorio.findAll();
        if (empresas.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Empresa>> empresasComLinks = new ArrayList<>();
            for (Empresa empresa : empresas) {
                adicionadorLinkEmpresa.adicionarLink(empresa);
                empresasComLinks.add(EntityModel.of(empresa));
            }
            return ResponseEntity.ok(CollectionModel.of(empresasComLinks));
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
        if (empresa.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        empresaRepositorio.save(empresa);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        return empresaRepositorio.findById(id)
                .map(empresaExistente -> {
                    empresa.setId(id);
                    empresaRepositorio.save(empresa);
                    return new ResponseEntity<>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEmpresa(@PathVariable Long id) {
        if (!empresaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresaRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/usuarios/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> obterUsuariosDaEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id " + id));

        List<Usuario> usuarios = new ArrayList<>(empresa.getUsuarios());
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Usuario>> usuariosComLinks = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                usuariosComLinks.add(EntityModel.of(usuario));
            }
            return ResponseEntity.ok(CollectionModel.of(usuariosComLinks));
        }
    }


    @GetMapping("/mercadorias/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Mercadoria>>> obterMercadoriasDaEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id " + id));

        List<Mercadoria> mercadorias = new ArrayList<>(empresa.getMercadorias());
        if (mercadorias.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Mercadoria>> mercadoriasComLinks = new ArrayList<>();
            for (Mercadoria mercadoria : mercadorias) {
                mercadoriasComLinks.add(EntityModel.of(mercadoria));
            }
            return ResponseEntity.ok(CollectionModel.of(mercadoriasComLinks));
        }
    }

    @GetMapping("/servicos/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Servico>>> obterServicosDaEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id " + id));

        List<Servico> servicos = new ArrayList<>(empresa.getServicos());
        if (servicos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Servico>> servicosComLinks = new ArrayList<>();
            for (Servico servico : servicos) {
                servicosComLinks.add(EntityModel.of(servico));
            }
            return ResponseEntity.ok(CollectionModel.of(servicosComLinks));
        }
    }


    @GetMapping("/vendas/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Venda>>>obterVendasDaEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id " + id));

        List<Venda> vendas = new ArrayList<>(empresa.getVendas());
        if (vendas.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Venda>> vendasComLinks = new ArrayList<>();
            for (Venda venda : vendas) {
                vendasComLinks.add(EntityModel.of(venda));
            }
            return ResponseEntity.ok(CollectionModel.of(vendasComLinks));
        }
    }
}




