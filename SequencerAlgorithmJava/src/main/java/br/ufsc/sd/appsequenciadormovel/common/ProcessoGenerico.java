package br.ufsc.sd.appsequenciadormovel.common;

/**
 * Classe que representa um processo genérico.
 */
public abstract class ProcessoGenerico {
	// Identificador e nome do processo
	protected int id;
	protected String name;

	/**
	 * Getter do identificador do processo.
	 * @return O identificador do processo.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter do nome do processo.
	 * @return O nome do processo.
	 */
	public String getName() {
		return name;
	}

}
