package br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel;

import java.util.Collections;
import java.util.List;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;
import br.ufsc.sd.appsequenciadormovel.common.ProcessoGenerico;
import br.ufsc.sd.appsequenciadormovel.entities.servidor.ProcessoServidor;

public class ProcessoSequenciador extends ProcessoGenerico {

	private boolean lider;
	private ProcessoSequenciador proximo;


	public ProcessoSequenciador(int id, boolean lider) {
		this.id = id;
		this.lider = lider;
		name = "Processo Sequenciador " + id;
	}

	public ProcessoSequenciador(int id) {
		this.id = id;
		this.lider = false;
		name = "Processo Sequenciador " + id;
	}

	public boolean isLider() {
		return lider;
	}

	public void setLider(boolean lider) {
		this.lider = lider;
	}

	public void setProximo(ProcessoSequenciador proximo) {
		this.proximo = proximo;
	}

	public ProcessoSequenciador getProximo() {
		return proximo;
	}

	public String receberMsgs(List<Mensagem> msgs) {
		for (Mensagem msg : msgs) {
			String resultado = this.getName() + " recebeu a mensagem " + msg.getMensagem() + " enviada por " + msg.getProcessoEmissor().getName();
			System.out.println(resultado);
			AppMain.DOCUMENTO_PDF.addNormalText(resultado, false);
		}
		return Mensagem.printListaDeMsgs(msgs);
	}

	public void ordenarMsgsDeFormaAleatoria(List<Mensagem> msgs) {
		Collections.shuffle(msgs);
	}

	public void setAsLider() {
		this.lider = true;
	}

	public void enviarMsgsAosServidores(List<Mensagem> msgs) {
		String result = Mensagem.printListaDeMsgs(msgs);
		for (ProcessoServidor processoServidor : AppMain.GRUPO_SERVIDOR.getProcessoServidores()) {
			String printedmsg = this.getName() + " enviou a msg " + result + " p/ " + processoServidor.getName();
			System.out.println(printedmsg);
			AppMain.DOCUMENTO_PDF.addNormalText(printedmsg, false);
		}

		AppMain.TABELA.addMensagemEnviada(this, result);
	}

	public void enviarMsgsAosClientes(List<Mensagem> msgs) {
		String result = Mensagem.printListaDeMsgs(msgs);

		for (Mensagem msg : msgs) {
			String printedmsg = this.getName() + " enviou a msg " + msg.getMensagem() + " p/ " + msg.getProcessoDestino().getName();
			System.out.println(printedmsg);
			AppMain.DOCUMENTO_PDF.addNormalText(printedmsg, false);
		}

		AppMain.TABELA.addMensagemEnviada(this, result);
	}
}
