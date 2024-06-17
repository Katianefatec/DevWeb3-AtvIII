package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.entidades.Empresa;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa>{

    public void adicionarLink(Empresa empresa) {
        Link selfLink = linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresa.getId())).withSelfRel();
        empresa.add(selfLink);

        Link usuariosLink = linkTo(methodOn(EmpresaControle.class).obterUsuariosDaEmpresa(empresa.getId())).withRel("usuarios");
        empresa.add(usuariosLink);

        Link mercadoriasLink = linkTo(methodOn(EmpresaControle.class).obterMercadoriasDaEmpresa(empresa.getId())).withRel("mercadorias");
        empresa.add(mercadoriasLink);

        Link servicosLink = linkTo(methodOn(EmpresaControle.class).obterServicosDaEmpresa(empresa.getId())).withRel("servicos");
        empresa.add(servicosLink);

        Link vendasLink = linkTo(methodOn(EmpresaControle.class).obterVendasDaEmpresa(empresa.getId())).withRel("vendas");
        empresa.add(vendasLink);
    }

    @Override
    public void adicionarLink(List<Empresa> empresas) {
        for (Empresa empresa : empresas) {
            adicionarLink(empresa);
        }
    }
}
