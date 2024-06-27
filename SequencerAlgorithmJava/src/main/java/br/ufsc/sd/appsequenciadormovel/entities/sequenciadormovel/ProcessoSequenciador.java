package br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel;

import java.util.Collections;
import java.util.List;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;
import br.ufsc.sd.appsequenciadormovel.common.ProcessoGenerico;
import br.ufsc.sd.appsequenciadormovel.entities.servidor.ProcessoServidor;

/**
 * Classe que representa um processo sequenciador.
 */
public class ProcessoSequenciador extends ProcessoGenerico {

	// Indica se o processo é líder
	private boolean lider;

	// Próximo processo sequenciador
	private ProcessoSequenciador proximo;


	/**
	 * Construtor da classe.
	 * @param id O identificador do processo.
	 * @param lider Indica se o processo é líder.
	 */
	public ProcessoSequenciador(int id, boolean lider) {
		this.id = id;
		this.lider = lider;
		name = "Processo Sequenciador " + id;
	}

	/**
	 * Construtor da classe.
	 * @param id O identificador do processo.
	 */
	public ProcessoSequenciador(int id) {
		this.id = id;
		this.lider = false;
		name = "Processo Sequenciador " + id;
	}

	/**
	 * Indica se o processo é líder.
	 * @return True se o processo é líder, false caso contrário.
	 */
	public boolean isLider() {
		return lider;
	}

	/**
	 * Setter do atributo lider.
	 * @param lider True se o processo é líder, false caso contrário.
	 */
	public void setLider(boolean lider) {
		this.lider = lider;
	}

	/**
	 * Setter do próximo processo sequenciador.
	 * @param proximo O próximo processo sequenciador.
	 */
	public void setProximo(ProcessoSequenciador proximo) {
		this.proximo = proximo;
	}

	/**
	 * Getter do próximo processo sequenciador.
	 * @return O próximo processo sequenciador.
	 */
	public ProcessoSequenciador getProximo() {
		return proximo;
	}

	/**
	 * Define o processo como líder.
	 */
	public void setAsLider() {
		this.lider = true;
	}

	/**
	 * Método para receber mensagens.
	 * @param msgs Lista de mensagens recebidas.
	 * @return A lista de mensagens recebidas.
	 */
	public String receberMsgs(List<Mensagem> msgs) {

		// For para percorrer as mensagens recebidas
		for (Mensagem msg : msgs) {

			// String que contém que o processo recebeu a mensagem, a mensagem em si e quem enviou a mensagem
			String resultado = this.getName() + " recebeu a mensagem " + msg.getMensagem() + " enviada por " + msg.getProcessoEmissor().getName();
			
			// Printa a mensagem no console
			System.out.println(resultado);

			// Adiciona a mensagem ao documento PDF
			AppMain.DOCUMENTO_PDF.addNormalText(resultado, false);
		}

		// Retorna a lista de mensagens recebidas em forma de string unica
		return Mensagem.printListaDeMsgs(msgs);
	}

	/**
	 * Método para ordenar mensagens de forma aleatória.
	 * @param msgs Lista de mensagens a serem ordenadas.
	 */
	public void ordenarMsgsDeFormaAleatoria(List<Mensagem> msgs) {
		Collections.shuffle(msgs);
	}

	/**
	 * Método para enviar mensagens aos servidores.
	 * @param msgs Lista de mensagens a serem enviadas.
	 */
	public void enviarMsgsAosServidores(List<Mensagem> msgs) {

		// Transforma a lista de mensagens em uma string única
		String result = Mensagem.printListaDeMsgs(msgs);

		// For para percorrer cada processo servidor
		for (ProcessoServidor processoServidor : AppMain.GRUPO_SERVIDOR.getProcessoServidores()) {

			// String que contém que o processo enviou a mensagem, a mensagem em si e o destino da mensagem
			String printedmsg = this.getName() + " enviou a msg " + result + " p/ " + processoServidor.getName();

			// Printa a mensagem no console
			System.out.println(printedmsg);

			// Adiciona a mensagem ao documento PDF
			AppMain.DOCUMENTO_PDF.addNormalText(printedmsg, false);
		}
		
		// Adiciona a mensagem enviada à tabela
		AppMain.TABELA.addMensagemEnviada(this, result);
	}

	/**
	 * Método para enviar mensagens aos clientes.
	 * @param msgs Lista de mensagens a serem enviadas.
	 */
	public void enviarMsgsAosClientes(List<Mensagem> msgs) {

		// Transforma a lista de mensagens em uma string única
		String result = Mensagem.printListaDeMsgs(msgs);

		// For para percorrer cada mensagem a ser enviada.
		for (Mensagem msg : msgs) {

			// String que contém que o processo enviou a mensagem, a mensagem em si e o destino da mensagem
			String printedmsg = this.getName() + " enviou a msg " + msg.getMensagem() + " p/ " + msg.getProcessoDestino().getName();

			// Printa a mensagem no console
			System.out.println(printedmsg);

			// Adiciona a mensagem ao documento PDF
			AppMain.DOCUMENTO_PDF.addNormalText(printedmsg, false);
		}

		// Adiciona a mensagem enviada à tabela
		AppMain.TABELA.addMensagemEnviada(this, result);
	}
}
