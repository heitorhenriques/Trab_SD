package br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;

public class GrupoSequenciadorMovel {

	private static List<ProcessoSequenciador> processosSequenciadores;
	private ProcessoSequenciador processoSequenciadorLider;

	public GrupoSequenciadorMovel() {
		processosSequenciadores = new ArrayList<>();

		processosSequenciadores.add(new ProcessoSequenciador(1));
		processosSequenciadores.add(new ProcessoSequenciador(2));
		processosSequenciadores.add(new ProcessoSequenciador(3));

		processosSequenciadores.get(0).setProximo(processosSequenciadores.get(1));
		processosSequenciadores.get(1).setProximo(processosSequenciadores.get(2));
		processosSequenciadores.get(2).setProximo(processosSequenciadores.get(0));

		processoSequenciadorLider = processosSequenciadores.get(new Random().nextInt(processosSequenciadores.size()));
		processoSequenciadorLider.setAsLider();

	}

	public ProcessoSequenciador getProcessoSequenciadorLider() {
		return processoSequenciadorLider;
	}

	public int getQuantidadeDeProcessosSequenciadores() {
		return processosSequenciadores.size();
	}

	public void processarMsgsDoCliente(List<Mensagem> msgs) {
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 2: Recebimento das msgs no Lider dos Sequenciadores (" + processoSequenciadorLider.getName() + ")";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		processarMsgs(msgs);

		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		etapa = "Etapa 3: Ordenando as Msgs de forma aleatória e difundir entre os processos servidores";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		processoSequenciadorLider.ordenarMsgsDeFormaAleatoria(msgs);
		String sequenciaDeMsgs = Mensagem.printListaDeMsgs(msgs);
		String output = "Sequencia definida pelo " + processoSequenciadorLider.getName() + ": " + sequenciaDeMsgs;
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);

		output = "Difundindo mensagens entre os processos servidores";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);

		processoSequenciadorLider.enviarMsgsAosServidores(msgs);
		output = "Os demais processos sequenciadores, por não serem lideres, não enviaram a mensagem (N/A)";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);
		for (ProcessoSequenciador processoSequenciador : processosSequenciadores) {
			if(!processoSequenciador.isLider()) {
				AppMain.TABELA.addMensagemEnviada(processoSequenciador, "N/A");
			}
		}
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();
		AppMain.GRUPO_SERVIDOR.receberBroadcast(msgs);
	}

	public void processarMsgsDoServidor(List<Mensagem> msgs) {
		processarMsgs(msgs);

		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 7: Repassar as msgs aos clientes";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		this.processoSequenciadorLider.enviarMsgsAosClientes(msgs);

		String output = "Os demais processos sequenciadores, por não serem lideres, não enviaram a mensagem (N/A)";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);
		for (ProcessoSequenciador processoSequenciador : processosSequenciadores) {
			if(!processoSequenciador.isLider()) {
				AppMain.TABELA.addMensagemEnviada(processoSequenciador, "N/A");
			}
		}

		AppMain.GRUPO_EMISSOR.receberMsgs(msgs);
	}

	private void processarMsgs(List<Mensagem> msgs) {
		String result = processoSequenciadorLider.receberMsgs(msgs);

		String output = "Os demais processos sequenciadores, por não serem lideres, não receberam a mensagem (N/A)";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);
		for (ProcessoSequenciador processoSequenciador : processosSequenciadores) {
			if(!processoSequenciador.isLider()) {
				AppMain.TABELA.addMensagemRecebida(processoSequenciador, "N/A");
			}
		}

		AppMain.TABELA.addMensagemRecebida(processoSequenciadorLider, result);
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();
	}

	public void trocarLider() {
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 8: Trocar o processo sequenciador lider para o proximo no anel";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);
		etapa = "Atual Processo sequenciador Líder: " + processoSequenciadorLider.getName();
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		processoSequenciadorLider.setLider(false);
		processoSequenciadorLider = processoSequenciadorLider.getProximo();
		etapa = "Novo processo sequenciador líder: " + processoSequenciadorLider.getName();
		processoSequenciadorLider.setAsLider();
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
	}
}
