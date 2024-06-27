package br.ufsc.sd.appsequenciadormovel.entities.servidor;

import java.util.List;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.DocPDF;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;
import br.ufsc.sd.appsequenciadormovel.common.ProcessoGenerico;

public class ProcessoServidor extends ProcessoGenerico {

	private DocPDF docPDF = AppMain.DOCUMENTO_PDF;

	public ProcessoServidor(int id) {
		this.id = id;
		name = "Processo Servidor " + id;
	}

	public void receberMsgs(List<Mensagem> msgs) {
		String listMsgs = Mensagem.printListaDeMsgs(msgs);
		String recebeu = this.getName() + " recebeu " + listMsgs + " enviada por " +
				AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName();
		System.out.println(recebeu);
		AppMain.DOCUMENTO_PDF.addNormalText(recebeu, false);
		AppMain.TABELA.addMensagemRecebida(this, listMsgs);
	}

	public void processarRequisicoes(List<Mensagem> msgs) {
		String listMsgs = Mensagem.printListaDeMsgs(msgs);
		String processando = this.getName() + " está processando " + listMsgs;
		System.out.println(processando);
		AppMain.DOCUMENTO_PDF.addNormalText(processando, false);
	}

	public void enviarOk() {
		for (ProcessoServidor ps : AppMain.GRUPO_SERVIDOR.getProcessoServidores()) {
			if(!ps.equals(this)) {
				String msg = this.getName() + " enviou a mensagem OK p/ o " + ps.getName();
				System.out.println(msg);
				AppMain.DOCUMENTO_PDF.addNormalText(msg, false);
			}
		}
	}

	public void receberOk(ProcessoServidor processoVencedor) {
		String msg = this.getName() + " recebeu o OK enviado por " + processoVencedor.getName();
		System.out.println(msg);
		AppMain.DOCUMENTO_PDF.addNormalText(msg, false);
	}

	public void responderRequisicao(List<Mensagem> msgs) {
			for (Mensagem msg : msgs) {
				msg.setMensagem(msg.getMensagem() + "_OK");
				String output = this.getName() + " enviou a mensagem " + msg.getMensagem() + " p/ o " + msg.getProcessoDestino().getName();

				msg.setProcessoDestino(msg.getProcessoEmissor());
				msg.setProcessoEmissor(this);

				System.out.println(output);
				AppMain.DOCUMENTO_PDF.addNormalText(output, false);
			}

			AppMain.TABELA.addMensagemEnviada(this, Mensagem.printListaDeMsgs(msgs));
	}
}
