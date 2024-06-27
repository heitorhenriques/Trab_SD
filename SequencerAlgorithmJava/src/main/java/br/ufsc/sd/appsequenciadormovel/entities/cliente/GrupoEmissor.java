package br.ufsc.sd.appsequenciadormovel.entities.cliente;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel.ProcessoSequenciador;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;

public class GrupoEmissor {

	private static List<ProcessoCliente> listaDeProcessosCliente;

	public GrupoEmissor() {
		listaDeProcessosCliente = new ArrayList<>();
		listaDeProcessosCliente.add(new ProcessoCliente(1));
		listaDeProcessosCliente.add(new ProcessoCliente(2));
		listaDeProcessosCliente.add(new ProcessoCliente(3));
	}

	public void enviarMsgs() {
		ProcessoSequenciador processoDestino = AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider();

		List<Mensagem> mensagens = new ArrayList<>();
		mensagens.add(new Mensagem(listaDeProcessosCliente.get(0), "M1", processoDestino));
		mensagens.add(new Mensagem(listaDeProcessosCliente.get(1), "M2", processoDestino));
		mensagens.add(new Mensagem(listaDeProcessosCliente.get(2), "M3", processoDestino));

		String etapa = "Etapa 1: Emissão das msgs nos processos do grupo emissor";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);
		for (Mensagem msg : mensagens) {
			String resultado = msg.getProcessoEmissor().getName() + " enviou a msg " + msg.getMensagem() + " p/ " + msg.getProcessoDestino().getName();
			System.out.println(resultado);
			AppMain.DOCUMENTO_PDF.addNormalText(resultado, false);
			AppMain.TABELA.addMensagemEnviada(msg.getProcessoEmissor(), msg.getMensagem());
		}
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		AppMain.GRUPO_SEQUENCIADOR_MOVEL.processarMsgsDoCliente(mensagens);
	}

	public void receberMsgs(List<Mensagem> msgs) {
		String sequenciador = AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName();
		for (Mensagem msg : msgs) {
			//Processo Sequenciador 1 recebeu a mensagem M3_OK enviada por Processo Servidor 3
			String resultado = msg.getProcessoDestino().getName() + " recebeu a msg " + msg.getMensagem() + " enviada por " + sequenciador;
			System.out.println(resultado);
			AppMain.DOCUMENTO_PDF.addNormalText(resultado, false);
			AppMain.TABELA.addMensagemRecebida(msg.getProcessoDestino(), msg.getMensagem());
		}

		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();
		AppMain.GRUPO_SEQUENCIADOR_MOVEL.trocarLider();
	}
}
