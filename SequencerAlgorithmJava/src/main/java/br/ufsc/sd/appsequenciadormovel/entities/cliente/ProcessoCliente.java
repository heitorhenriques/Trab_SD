package br.ufsc.sd.appsequenciadormovel.entities.cliente;

import br.ufsc.sd.appsequenciadormovel.common.ProcessoGenerico;

/**
 * Classe que representa um processo do cliente.
 */
public class ProcessoCliente extends ProcessoGenerico {

	// Construtor da classe, que recebe um id e o nomeia como "Processo Emissor " + id.
	public ProcessoCliente(int id) {
		this.id = id;
		this.name = "Processo Emissor " + id;
	}
}
