package br.ufsc.sd.appsequenciadormovel.common;

import java.util.Arrays;

import br.ufsc.sd.appsequenciadormovel.entities.cliente.ProcessoCliente;
import br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel.ProcessoSequenciador;
import br.ufsc.sd.appsequenciadormovel.entities.servidor.ProcessoServidor;

/**
 * Classe que representa uma tabela usada para armazenar mensagens enviadas e recebidas por cada processo.
 */
public class TabelaPadrao {

	// Array bidimensional que representa a tabela
	private String[][] tableData;

	/**
	 * Construtor da classe, que inicializa a tabela com os nomes das colunas e células vazias.
	 */
	public TabelaPadrao() {
		// Inicializa a tabela com 3 linhas e 10 colunas
		tableData = new String[3][10];

		// Preenche a tabela com células vazias
		for (int i = 0; i < tableData.length; i++) {
			Arrays.fill(tableData[i], "");
		}

		// Prenche o nome das linhas e colunas
		tableData[1][0] = "Msgs Enviadas";
		tableData[2][0] = "Msgs Recebidas";

		tableData[0][1] = "P1 - Emissor";
		tableData[0][2] = "P2 - Emissor";
		tableData[0][3] = "P3 - Emissor";

		tableData[0][4] = "P1 - Sequenciador";
		tableData[0][5] = "P2 - Sequenciador";
		tableData[0][6] = "P3 - Sequenciador";

		tableData[0][7] = "P1 - Servidor";
		tableData[0][8] = "P2 - Servidor";
		tableData[0][9] = "P3 - Servidor";
	}


	/**
	 * Método para adicionar mensagem enviada por um processo emissor específico
	 * @param processoEmissor O processo emissor da mensagem
	 * @param mensagem A mensagem enviada
	 */
	public void addMensagemEnviada(ProcessoGenerico processoEmissor, String mensagem) {
		// Obtém o índice da coluna do processo receptor
		int colIndex = getColIndexByProcesso(processoEmissor);

		// Se o índice for válido, adiciona a mensagem enviada
		if (colIndex != -1) {

			// Se a célula estiver vazia, adiciona a mensagem
			if (tableData[1][colIndex].isEmpty()) {
				tableData[1][colIndex] = mensagem;
			} else {
				// Se a célula não estiver vazia, adiciona a mensagem em uma nova linha
				tableData[1][colIndex] += "\n" + mensagem;
			}
		}
	}

	/**
	 * Método para adicionar mensagem recebida por um processo receptor específico
	 * @param processoReceptor O processo receptor da mensagem
	 * @param mensagem A mensagem recebida
	 */
	public void addMensagemRecebida(ProcessoGenerico processoReceptor, String mensagem) {
		// Obtém o índice da coluna do processo receptor
		int colIndex = getColIndexByProcesso(processoReceptor);

		// Se o índice for válido, adiciona a mensagem recebida
		if (colIndex != -1) {

			// Se a célula estiver vazia, adiciona a mensagem
			if (tableData[2][colIndex].isEmpty()) {
				tableData[2][colIndex] = mensagem;
			} else {
				// Se a célula não estiver vazia, adiciona a mensagem em uma nova linha
				tableData[2][colIndex] += "\n" + mensagem;
			}
		}
	}

	/**
	 * Método para obter o índice da coluna de um processo na tabela
	 * @param processo Processo cujo índice da coluna será obtido
	 * @return O índice da coluna do processo na tabela
	 */
	private int getColIndexByProcesso(ProcessoGenerico processo) {
		// Obtém o índice do processo
		int index = processo.getId();

		// Se o processo for um cliente, retorna o próprio índice
		if (processo instanceof ProcessoCliente) {
			return index;
		}

		// Se o processo for um sequenciador, retorna o índice do processo + 3
		if (processo instanceof ProcessoSequenciador) {
			return 3 + index;
		}

		// Se o processo for um servidor, retorna o índice do processo + 6
		if (processo instanceof ProcessoServidor) {
			return 6 + index;
		}

		// Se o processo não for nenhum dos anteriores, retorna -1
		return -1;

	}

	// Método para obter os dados da tabela
	public String[][] getTableData() {
		return tableData;
	}



}
