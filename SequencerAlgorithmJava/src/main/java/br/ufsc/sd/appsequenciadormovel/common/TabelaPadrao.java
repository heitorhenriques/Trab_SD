package br.ufsc.sd.appsequenciadormovel.common;

import java.util.Arrays;

import br.ufsc.sd.appsequenciadormovel.entities.cliente.ProcessoCliente;
import br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel.ProcessoSequenciador;
import br.ufsc.sd.appsequenciadormovel.entities.servidor.ProcessoServidor;

public class TabelaPadrao {

	private String[][] tableData;

	public TabelaPadrao() {
		tableData = new String[3][10];

		// Preenche todas as células da tabela com " - "
		for (int i = 0; i < tableData.length; i++) {
			Arrays.fill(tableData[i], "");
		}

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


	// Método para adicionar mensagem enviada por um processo emissor específico
	public void addMensagemEnviada(ProcessoGenerico processoEmissor, String mensagem) {
		int colIndex = getColIndexByProcesso(processoEmissor);
		if (colIndex != -1) {
			if (tableData[1][colIndex].isEmpty()) {
				tableData[1][colIndex] = mensagem;
			} else {
				tableData[1][colIndex] += "\n" + mensagem;
			}
		}
	}

	// Método para adicionar mensagem recebida por um processo receptor específico
	public void addMensagemRecebida(ProcessoGenerico processoReceptor, String mensagem) {
		int colIndex = getColIndexByProcesso(processoReceptor);
		if (colIndex != -1) {
			if (tableData[2][colIndex].isEmpty()) {
				tableData[2][colIndex] = mensagem;
			} else {
				tableData[2][colIndex] += "\n" + mensagem;
			}
		}
	}

	// Método para obter o índice da coluna correspondente a um processo específico
	private int getColIndexByProcesso(ProcessoGenerico processo) {
		int index = processo.getId();
		if (processo instanceof ProcessoCliente) {
			return index; // Ajuste para a coluna correta no array (considerando o índice 1)
		}
		if (processo instanceof ProcessoSequenciador) {
			return 3 + index; // Ajuste para a coluna correta no array (considerando o índice 1)
		}
		if (processo instanceof ProcessoServidor) {
			return 6 + index;
		}

		return -1;

	}

	// Método para obter os dados da tabela
	public String[][] getTableData() {
		return tableData;
	}



}
