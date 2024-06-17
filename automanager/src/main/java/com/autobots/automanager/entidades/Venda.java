package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "cliente", "funcionario", "veiculo" })
@Entity
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false, unique = true)
	private String identificacao;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Usuario cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Usuario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "veiculo_id")
	@JsonBackReference
	private Veiculo veiculo;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonBackReference
	@JoinTable(
			name = "venda_mercadoria",
			joinColumns = @JoinColumn(name = "venda_id"),
			inverseJoinColumns = @JoinColumn(name = "mercadoria_id")
	)
	private Set<Mercadoria> mercadorias = new HashSet<>();
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(
			name = "venda_servico",
			joinColumns = @JoinColumn(name = "venda_id"),
			inverseJoinColumns = @JoinColumn(name = "servico_id")
	)
	private Set<Servico> servicos = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	@JsonBackReference
	private Empresa empresa;
}