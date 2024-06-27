package br.ufsc.sd.appsequenciadormovel.entities.servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;

public class GrupoServidor {

	private static List<ProcessoServidor> processosServidores;
	private ProcessoServidor processoVencedor = null;

	public GrupoServidor() {
		processosServidores = new ArrayList<>();

		processosServidores.add(new ProcessoServidor(1));
		processosServidores.add(new ProcessoServidor(2));
		processosServidores.add(new ProcessoServidor(3));
	}

	public List<ProcessoServidor> getProcessoServidores() {
		return processosServidores;
	}

	public ProcessoServidor getProcessoVencedor() {
		return processoVencedor;
	}

	public void receberBroadcast(List<Mensagem> msgs) {
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 4: Recebendo as msgs nos processos servidores";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		for (ProcessoServidor processoServidor : processosServidores) {
			processoServidor.receberMsgs(msgs);
		}
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		processarBroadcast(msgs);
	}

	void processarBroadcast(List<Mensagem> msgs) {
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 5: Processando a requisição de cada mensagem";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		for (ProcessoServidor processoServidor : processosServidores) {
			processoServidor.processarRequisicoes(msgs);
		}

		processoVencedor = processosServidores.get(new Random().nextInt(processosServidores.size()));
		etapa = "O " + processoVencedor.getName() + " foi o primeiro a finalizar o processamento." +
				" Este processo enviará OK aos demais processos servidores. " +
		"Além disso, os demais processos servidores não enviarão mensagem (N/A)";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		processoVencedor.enviarOk();
		AppMain.TABELA.addMensagemEnviada(processoVencedor, "OK");

		for (ProcessoServidor ps : processosServidores) {
			if(!ps.equals(processoVencedor)) {
				AppMain.TABELA.addMensagemEnviada(ps, "N/A");
				ps.receberOk(processoVencedor);
				AppMain.TABELA.addMensagemRecebida(ps, "OK");
			}
		}

		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		etapa = "Etapa 6: O " + processoVencedor.getName() + " vai processar as respostas das requisições, enviando a resposta ao "
		+ AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName();
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);
		processoVencedor.responderRequisicao(msgs);

		AppMain.GRUPO_SEQUENCIADOR_MOVEL.processarMsgsDoServidor(msgs);

	}


}
