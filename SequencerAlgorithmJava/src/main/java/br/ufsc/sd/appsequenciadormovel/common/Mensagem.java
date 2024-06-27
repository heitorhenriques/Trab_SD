package br.ufsc.sd.appsequenciadormovel.common;

import java.util.List;

import br.ufsc.sd.appsequenciadormovel.entities.servidor.ProcessoServidor;

public class Mensagem {
	private ProcessoGenerico processoEmissor;
	private String mensagem;
	private ProcessoGenerico processoDestino;

	public Mensagem(ProcessoGenerico processoEmissor, String mensagem) {
		this.processoEmissor = processoEmissor;
		this.mensagem = mensagem;
	}

	public Mensagem(ProcessoGenerico processoEmissor, String mensagem, ProcessoGenerico processoDestino) {
		this.processoEmissor = processoEmissor;
		this.mensagem = mensagem;
		this.processoDestino = processoDestino;
	}

	public void setProcessoDestino(ProcessoGenerico processoDestino) {
		this.processoDestino = processoDestino;
	}

	public ProcessoGenerico getProcessoEmissor() {
		return processoEmissor;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setProcessoEmissor(ProcessoGenerico processoEmissor) {
		this.processoEmissor = processoEmissor;
	}

	public ProcessoGenerico getProcessoDestino() {
		return processoDestino;
	}

	public static String printListaDeMsgs(List<Mensagem> msgs) {
		StringBuilder resultado = new StringBuilder();
		for (int i = 0; i < msgs.size(); i++) {
			resultado.append(msgs.get(i).getMensagem());
			if (i < msgs.size() - 1) {
				resultado.append(",");
			}
		}
		return resultado.toString();
	}

}
