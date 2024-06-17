package com.autobots.automanager.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.autobots.automanager.enumeracoes.PerfilUsuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "mercadorias", "vendas", "veiculos" })
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column
	private String nomeSocial;
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<PerfilUsuario> perfis = new HashSet<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Telefone> telefones = new HashSet<>();
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Documento> documentos = new HashSet<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Email> emails = new HashSet<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Credencial> credenciais = new HashSet<>();
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Set<Mercadoria> mercadorias = new HashSet<>();
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Venda> vendas = new HashSet<>();
	@OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<Veiculo> veiculos = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JoinColumn(name = "empresa_id") // Nome da coluna que armazena o ID da empresa
	private Empresa empresa;
}