package br.ufsc.sd.appsequenciadormovel.entities.servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;

/**
 * Classe que representa um grupo de processos servidores.
 */
public class GrupoServidor {

	// Lista de processos servidores
	private static List<ProcessoServidor> processosServidores;
	
	// Processo servidor vencedor
	private ProcessoServidor processoVencedor = null;

	/**
	 * Construtor da classe.
	 */
	public GrupoServidor() {
		// Inicializa a lista de processos servidores
		processosServidores = new ArrayList<>();

		// Adiciona os processos servidores
		processosServidores.add(new ProcessoServidor(1));
		processosServidores.add(new ProcessoServidor(2));
		processosServidores.add(new ProcessoServidor(3));
	}

	/**
	 * Getter da lista de processos servidores.
	 * @return A lista de processos servidores.
	 */
	public List<ProcessoServidor> getProcessoServidores() {
		return processosServidores;
	}

	/**
	 * Getter do processo servidor vencedor.
	 * @return O processo servidor vencedor.
	 */
	public ProcessoServidor getProcessoVencedor() {
		return processoVencedor;
	}

	/**
	 * Método que recebe mensagens do processo sequenciador líder.
	 * @param msgs Lista de mensagens recebidas.
	 */
	public void receberBroadcast(List<Mensagem> msgs) {

		// Printa a etapa no console e adiciona ao documento PDF
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 4: Recebendo as msgs nos processos servidores";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// For para percorrer cada processo servidor e chamar o método de receber mensagens
		for (ProcessoServidor processoServidor : processosServidores) {
			processoServidor.receberMsgs(msgs);
		}

		// Adiciona a tabela ao documento PDF
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		// Chama o método de processar as mensagens recebidas
		processarBroadcast(msgs);
	}

	/**
	 * Método para processar as mensagens recebidas.
	 * @param msgs Lista de mensagens recebidas.
	 */
	void processarBroadcast(List<Mensagem> msgs) {

		// Printa a etapa no console e adiciona ao documento PDF
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 5: Processando a requisição de cada mensagem";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// For para percorrer cada processo servidor e chamar o método de processar requisições
		for (ProcessoServidor processoServidor : processosServidores) {
			processoServidor.processarRequisicoes(msgs);
		}

		// Define o processo vencedor aleatoriamente
		processoVencedor = processosServidores.get(new Random().nextInt(processosServidores.size()));

		// Printa o processo vencedor no console e adiciona ao documento PDF
		etapa = "O " + processoVencedor.getName() + " foi o primeiro a finalizar o processamento." +
				" Este processo enviará OK aos demais processos servidores. " +
		"Além disso, os demais processos servidores não enviarão mensagem (N/A)";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// Chama o método de enviar OK do processo vencedor
		processoVencedor.enviarOk();

		// Adiciona na tabela que o processo vencedor enviou OK
		AppMain.TABELA.addMensagemEnviada(processoVencedor, "OK");

		// For para percorrer cada processo servidor e chamar o método de receber OK (exceto para o processo vencedor)
		for (ProcessoServidor ps : processosServidores) {
			if(!ps.equals(processoVencedor)) {

				// Como os demais processos servidores não enviaram mensagem, adiciona "N/A" na tabela
				AppMain.TABELA.addMensagemEnviada(ps, "N/A");

				// Chama o método de receber OK do processo servidor vencedor
				ps.receberOk(processoVencedor);

				// Adiciona na tabela que o processo servidor recebeu OK
				AppMain.TABELA.addMensagemRecebida(ps, "OK");
			}
		}

		// Adiciona a tabela ao documento PDF
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		// Printa a etapa no console e adiciona ao documento PDF, informando que o processo vencedor 
		// vai processar as respostas das requisições e enviar a resposta ao grupo sequenciador móvel,
		// especificamente ao processo sequenciador líder
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		etapa = "Etapa 6: O " + processoVencedor.getName() + " vai processar as respostas das requisições, enviando a resposta ao "
		+ AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName();
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// Chama o método de responder requisição do processo vencedor
		processoVencedor.responderRequisicao(msgs);

		// Chama o método que processa a resposta do servidor no grupo sequenciador móvel
		AppMain.GRUPO_SEQUENCIADOR_MOVEL.processarMsgsDoServidor(msgs);

	}


}
