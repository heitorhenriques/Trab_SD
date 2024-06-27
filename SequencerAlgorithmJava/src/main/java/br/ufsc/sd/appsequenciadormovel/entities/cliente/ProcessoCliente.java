package br.ufsc.sd.appsequenciadormovel.entities.cliente;

import br.ufsc.sd.appsequenciadormovel.common.ProcessoGenerico;

public class ProcessoCliente extends ProcessoGenerico {

	public ProcessoCliente(int id) {
		this.id = id;
		this.name = "Processo Emissor " + id;
	}
}
