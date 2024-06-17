package com.autobots.automanager.controles;

import com.autobots.automanager.dtos.UsuarioDTO;
import com.autobots.automanager.dtos.VeiculoDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioControle {
    @Autowired
    private AdicionadorLinkVeiculo adicionadorLinkVeiculo;
    private UsuarioRepositorio repositorio;
    private ModelMapper modelMapper;
    private AdicionadorLinkUsuario adicionadorLink;
    private UsuarioRepositorio UsuarioRepositorio;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> obterUsuario(@PathVariable long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        adicionadorLink.adicionarLink(usuarioDTO);
        return ResponseEntity.ok(EntityModel.of(usuarioDTO));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        if (usuarios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum usuário encontrado");
        }
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(usuariosDTO);
        return ResponseEntity.ok(usuariosDTO);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        repositorio.save(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (usuario.getId() != null) {
            repositorio.save(usuario);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id) {
        if (!UsuarioRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        UsuarioRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}/veiculos")
    public ResponseEntity<CollectionModel<EntityModel<VeiculoDTO>>> obterVeiculosDoUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        List<EntityModel<VeiculoDTO>> veiculosDTO = usuario.getVeiculos().stream()
                .map(veiculo -> {
                    VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);
                    adicionadorLinkVeiculo.adicionarLink(dto);
                    return EntityModel.of(dto); // Embrulha o DTO em EntityModel
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(veiculosDTO,
                linkTo(methodOn(UsuarioControle.class).obterUsuarios()).withRel("usuarios")));
    }

}
