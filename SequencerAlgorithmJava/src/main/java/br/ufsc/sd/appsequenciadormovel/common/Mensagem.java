package br.ufsc.sd.appsequenciadormovel.common;

import java.util.List;

/**
 * Classe que representa uma mensagem enviada por um processo.
 */
public class Mensagem {

	// Processo que enviou a mensagem
	private ProcessoGenerico processoEmissor;
	
	// Mensagem enviada
	private String mensagem;
	
	// Processo que deve receber a mensagem
	private ProcessoGenerico processoDestino;

	/**
	 * Construtor da classe.
	 * @param processoEmissor O processo que enviou a mensagem.
	 * @param mensagem A mensagem enviada.
	 */
	public Mensagem(ProcessoGenerico processoEmissor, String mensagem) {
		this.processoEmissor = processoEmissor;
		this.mensagem = mensagem;
	}

	/**
	 * Construtor da classe.
	 * @param processoEmissor O processo que enviou a mensagem.
	 * @param mensagem A mensagem enviada.
	 * @param processoDestino O processo que deve receber a mensagem.
	 */
	public Mensagem(ProcessoGenerico processoEmissor, String mensagem, ProcessoGenerico processoDestino) {
		this.processoEmissor = processoEmissor;
		this.mensagem = mensagem;
		this.processoDestino = processoDestino;
	}

	/**
	 * Setter do processo que deve receber a mensagem.
	 * @param processoDestino O processo que deve receber a mensagem.
	 */
	public void setProcessoDestino(ProcessoGenerico processoDestino) {
		this.processoDestino = processoDestino;
	}

	/**
	 * Getter do processo que enviou a mensagem.
	 * @return O processo que enviou a mensagem.
	 */
	public ProcessoGenerico getProcessoEmissor() {
		return processoEmissor;
	}

	/**
	 * Getter da mensagem enviada.
	 * @return A mensagem enviada.
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Setter da mensagem enviada.
	 * @param mensagem A mensagem enviada.
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * Setter do processo que enviou a mensagem.
	 * @param processoEmissor O processo que enviou a mensagem.
	 */
	public void setProcessoEmissor(ProcessoGenerico processoEmissor) {
		this.processoEmissor = processoEmissor;
	}

	/**
	 * Getter do processo que deve receber a mensagem.
	 * @return O processo que deve receber a mensagem.
	 */
	public ProcessoGenerico getProcessoDestino() {
		return processoDestino;
	}

	/**
	 * Método que imprime uma lista de mensagens no formato de uma única string, exibindo as mensagens separadas por vírgula, ex: "M1,M2,M3".
	 * @param msgs A lista de mensagens a ser impressa.
	 * @return A lista de mensagens impressa no formato de uma única string.
	 */
	public static String printListaDeMsgs(List<Mensagem> msgs) {
		// Cria um StringBuilder para armazenar o resultado
		StringBuilder resultado = new StringBuilder();

		// Adiciona cada mensagem ao resultado, separando-as por vírgula
		for (int i = 0; i < msgs.size(); i++) {

			// Adiciona a mensagem ao resultado
			resultado.append(msgs.get(i).getMensagem());

			if (i < msgs.size() - 1) {
				// Adiciona uma vírgula, se não for a última mensagem
				resultado.append(",");
			}
		}

		// Retorna o resultado como uma string
		return resultado.toString();
	}

}
