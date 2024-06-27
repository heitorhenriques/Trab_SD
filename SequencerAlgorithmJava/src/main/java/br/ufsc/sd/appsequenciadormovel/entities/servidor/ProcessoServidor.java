package br.ufsc.sd.appsequenciadormovel.entities.servidor;

import java.util.List;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;
import br.ufsc.sd.appsequenciadormovel.common.ProcessoGenerico;

/**
 * Classe que representa um processo servidor.
 */
public class ProcessoServidor extends ProcessoGenerico {

	/**
	 * Construtor da classe.
	 * @param id O identificador do processo.
	 */
	public ProcessoServidor(int id) {
		this.id = id;
		name = "Processo Servidor " + id;
	}

	/**
	 * Método que recebe mensagens.
	 * @param msgs Lista de mensagens recebidas.
	 */
	public void receberMsgs(List<Mensagem> msgs) {

		// Transforma a lista de mensagens em uma string unica
		String listMsgs = Mensagem.printListaDeMsgs(msgs);

		// String que informa o processo que recebeu as mensagens, quem enviou e quais mensagens foram enviadas
		String recebeu = this.getName() + " recebeu " + listMsgs + " enviada por " +
				AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName();

		// Printa a string no console
		System.out.println(recebeu);

		// Adiciona a string ao documento PDF
		AppMain.DOCUMENTO_PDF.addNormalText(recebeu, false);

		// Adiciona na tabela que o processo recebeu as mensagens
		AppMain.TABELA.addMensagemRecebida(this, listMsgs);
	}

	/**
	 * Método para processar requisições.
	 * @param msgs Lista de mensagens a serem processadas.
	 */
	public void processarRequisicoes(List<Mensagem> msgs) {

		// Transforma a lista de mensagens em uma string unica
		String listMsgs = Mensagem.printListaDeMsgs(msgs);

		// String que informa o processo que está processando as mensagens
		String processando = this.getName() + " está processando " + listMsgs;

		// Printa a string no console e adiciona ao documento PDF
		System.out.println(processando);
		AppMain.DOCUMENTO_PDF.addNormalText(processando, false);
	}

	/**
	 * Método que envia OK para os outros processos servidores.
	 */
	public void enviarOk() {

		// For para percorrer cada processo servidor, enviando OK para todos (exceto para si mesmo)
		for (ProcessoServidor ps : AppMain.GRUPO_SERVIDOR.getProcessoServidores()) {
			if(!ps.equals(this)) {

				// String que informa que o processo enviou OK para outro processo servidor
				String msg = this.getName() + " enviou a mensagem OK p/ o " + ps.getName();

				// Printa a string no console e adiciona ao documento PDF
				System.out.println(msg);
				AppMain.DOCUMENTO_PDF.addNormalText(msg, false);
			}
		}
	}

	/**
	 * Método que recebe OK de outro processo servidor.
	 * @param processoVencedor O processo que enviou o OK.
	 */
	public void receberOk(ProcessoServidor processoVencedor) {

		// String que informa que o processo recebeu OK de outro processo servidor
		String msg = this.getName() + " recebeu o OK enviado por " + processoVencedor.getName();

		// Printa a string no console e adiciona ao documento PDF
		System.out.println(msg);
		AppMain.DOCUMENTO_PDF.addNormalText(msg, false);
	}

	/**
	 * Método que responde requisições.
	 * @param msgs Lista de mensagens a serem respondidas.
	 */
	public void responderRequisicao(List<Mensagem> msgs) {

			// Para cada mensagem enviada
			for (Mensagem msg : msgs) {

				// Adiciona "_OK" ao final da mensagem, informando que foi respondida
				msg.setMensagem(msg.getMensagem() + "_OK");

				// String que informa que o processo enviou a mensagem, a mensagem em si e o destino da mensagem
				String output = this.getName() + " enviou a mensagem " + msg.getMensagem() + " p/ o " + msg.getProcessoDestino().getName();

				// Printa a string no console e adiciona ao documento PDF
				System.out.println(output);
				AppMain.DOCUMENTO_PDF.addNormalText(output, false);

				// Define o processo destino como o processo emissor e o processo emissor como o processo atual
				// O processo emissor, anteriormente, era o processo cliente, enquanto o processo destino era o processo sequenciador líder
				msg.setProcessoDestino(msg.getProcessoEmissor());
				msg.setProcessoEmissor(this);
			}

			// Adiciona as mensagens enviadas ao documento PDF
			AppMain.TABELA.addMensagemEnviada(this, Mensagem.printListaDeMsgs(msgs));
	}
}
