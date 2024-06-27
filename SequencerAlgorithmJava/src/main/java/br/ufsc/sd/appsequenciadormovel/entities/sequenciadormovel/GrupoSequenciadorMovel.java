package br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;

/**
 * Classe que representa um grupo de processos sequenciadores.
 */
public class GrupoSequenciadorMovel {

	// Lista de processos sequenciadores
	private static List<ProcessoSequenciador> processosSequenciadores;
	
	// Processo sequenciador líder
	private ProcessoSequenciador processoSequenciadorLider;

	/**
	 * Construtor da classe.
	 */
	public GrupoSequenciadorMovel() {
		// Inicializa a lista de processos sequenciadores
		processosSequenciadores = new ArrayList<>();

		// Adiciona os processos sequenciadores
		processosSequenciadores.add(new ProcessoSequenciador(1));
		processosSequenciadores.add(new ProcessoSequenciador(2));
		processosSequenciadores.add(new ProcessoSequenciador(3));

		// Define a ordem dos processos sequenciadores
		processosSequenciadores.get(0).setProximo(processosSequenciadores.get(1));
		processosSequenciadores.get(1).setProximo(processosSequenciadores.get(2));
		processosSequenciadores.get(2).setProximo(processosSequenciadores.get(0));

		// Define o líder aleatoriamente
		processoSequenciadorLider = processosSequenciadores.get(new Random().nextInt(processosSequenciadores.size()));
		processoSequenciadorLider.setAsLider();
	}

	/**
	 * Getter do processo sequenciador líder.
	 * @return O processo sequenciador líder.
	 */
	public ProcessoSequenciador getProcessoSequenciadorLider() {
		return processoSequenciadorLider;
	}

	/**
	 * Getter da quantidade de processos sequenciadores.
	 * @return A quantidade de processos sequenciadores.
	 */
	public int getQuantidadeDeProcessosSequenciadores() {
		return processosSequenciadores.size();
	}


	/**
	 * Método para processar as mensagens recebidas do cliente.
	 * @param msgs Lista de mensagens recebidas.
	 */
	public void processarMsgsDoCliente(List<Mensagem> msgs) {

		// Adiciona a etapa abaixo ao documento PDF e printa no console
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 2: Recebimento das msgs no Lider dos Sequenciadores (" + processoSequenciadorLider.getName() + ")";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// Faz o processamento inicial das mensagens recebidas usando o método processarMsgs
		processarMsgs(msgs);

		// Adiciona a etapa abaixo ao documento PDF e printa no console
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		etapa = "Etapa 3: Ordenando as Msgs de forma aleatória e difundir entre os processos servidores";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// Ordena as mensagens de forma aleatória
		processoSequenciadorLider.ordenarMsgsDeFormaAleatoria(msgs);

		// Printa a sequência de mensagens no console e adiciona ao documento PDF, usando o método printListaDeMsgs da classe Mensagem que retorna a sequência de mensagens em forma de string única
		String sequenciaDeMsgs = Mensagem.printListaDeMsgs(msgs);
		String output = "Sequencia definida pelo " + processoSequenciadorLider.getName() + ": " + sequenciaDeMsgs;
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);

		// Adiciona a etapa abaixo ao documento PDF e printa no console
		output = "Difundindo mensagens entre os processos servidores";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);

		// Chama o método enviarMsgsAosServidores do processo sequenciador líder
		processoSequenciadorLider.enviarMsgsAosServidores(msgs);

		// Informa que os demais processos sequenciadores não enviaram a mensagem, pois não são líderes, printando isso no console e adicionando ao documento PDF
		output = "Os demais processos sequenciadores, por não serem lideres, não enviaram a mensagem (N/A)";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);

		// Para cada processo sequenciador, se ele não for líder, adiciona a mensagem enviada como "N/A" na tabela.
		for (ProcessoSequenciador processoSequenciador : processosSequenciadores) {
			if(!processoSequenciador.isLider()) {
				AppMain.TABELA.addMensagemEnviada(processoSequenciador, "N/A");
			}
		}

		// Adiciona a tabela ao documento PDF
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		// Chama o método receberBroadcast do grupo servidor
		AppMain.GRUPO_SERVIDOR.receberBroadcast(msgs);
	}

	/**
	 * Método para processar as mensagens recebidas do servidor.
	 * @param msgs Lista de mensagens recebidas.
	 */
	public void processarMsgsDoServidor(List<Mensagem> msgs) {
		// Faz o processamento inicial das mensagens recebidas usando o método processarMsgs
		processarMsgs(msgs);

		// Adiciona a etapa abaixo ao documento PDF e printa no console
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 7: Repassar as msgs aos clientes";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// Chama o método enviarMsgsAosClientes do processo sequenciador líder
		this.processoSequenciadorLider.enviarMsgsAosClientes(msgs);

		// Informa que os demais processos sequenciadores não enviaram a mensagem, pois não são líderes, printando isso no console e adicionando ao documento PDF
		String output = "Os demais processos sequenciadores, por não serem lideres, não enviaram a mensagem (N/A)";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);

		// Para cada processo sequenciador, se ele não for líder, adiciona a mensagem enviada como "N/A" na tabela.
		for (ProcessoSequenciador processoSequenciador : processosSequenciadores) {
			if(!processoSequenciador.isLider()) {
				AppMain.TABELA.addMensagemEnviada(processoSequenciador, "N/A");
			}
		}

		// Chama o método receberMsgs do grupo emissor
		AppMain.GRUPO_EMISSOR.receberMsgs(msgs);
	}

	/**
	 * Método para processar as mensagens recebidas.
	 * @param msgs Lista de mensagens recebidas.
	 */
	private void processarMsgs(List<Mensagem> msgs) {

		// Faz o processo sequenciador líder receber as mensagens e como resultado recebe a lista de mensagens recebidas em forma de string única
		String result = processoSequenciadorLider.receberMsgs(msgs);

		// Informa que os demais processos sequenciadores não receberam a mensagem, pois não são líderes, printando isso no console e adicionando ao documento PDF
		String output = "Os demais processos sequenciadores, por não serem lideres, não receberam a mensagem (N/A)";
		AppMain.DOCUMENTO_PDF.addNormalText(output, false);
		System.out.println(output);

		// Para cada processo sequenciador, se ele não for líder, adiciona a mensagem recebida como "N/A" na tabela.
		for (ProcessoSequenciador processoSequenciador : processosSequenciadores) {
			if(!processoSequenciador.isLider()) {
				AppMain.TABELA.addMensagemRecebida(processoSequenciador, "N/A");
			}
		}

		// Adiciona na tabela que o processo sequenciador líder recebeu a mensagem
		AppMain.TABELA.addMensagemRecebida(processoSequenciadorLider, result);

		// Adiciona a tabela ao documento PDF
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();
	}

	/**
	 * Método para trocar o processo sequenciador líder para o próximo no anel.
	 */
	public void trocarLider() {

		// Adiciona a etapa ao documento PDF e printa no console
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		String etapa = "Etapa 8: Trocar o processo sequenciador lider para o proximo no anel";
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// Informa o processo sequenciador líder atual tanto no documento PDF quanto no console
		etapa = "Atual Processo sequenciador Líder: " + processoSequenciadorLider.getName();
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// Define o processo sequenciador líder como não líder
		processoSequenciadorLider.setLider(false);

		// Define o próximo processo sequenciador como o novo líder
		processoSequenciadorLider = processoSequenciadorLider.getProximo();
		processoSequenciadorLider.setAsLider();

		// Informa o novo processo sequenciador líder tanto no documento PDF quanto no console
		etapa = "Novo processo sequenciador líder: " + processoSequenciadorLider.getName();
		
		// Printa a etapa no console e também adiciona ao documento PDF
		System.out.println(etapa);
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
		AppMain.DOCUMENTO_PDF.addNormalText("", true);
	}
}
