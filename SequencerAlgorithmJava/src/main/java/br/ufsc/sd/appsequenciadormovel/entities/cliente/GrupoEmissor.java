package br.ufsc.sd.appsequenciadormovel.entities.cliente;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.sd.appsequenciadormovel.AppMain;
import br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel.ProcessoSequenciador;
import br.ufsc.sd.appsequenciadormovel.common.Mensagem;

/*
 * Classe que representa o grupo de processos emissores de mensagens.
 */
public class GrupoEmissor {

	// Lista de processos clientes.
	private static List<ProcessoCliente> listaDeProcessosCliente;

	// Construtor da classe, instanciando 3 processos clientes e adicionando-os à lista de processos clientes.
	public GrupoEmissor() {
		listaDeProcessosCliente = new ArrayList<>();
		listaDeProcessosCliente.add(new ProcessoCliente(1));
		listaDeProcessosCliente.add(new ProcessoCliente(2));
		listaDeProcessosCliente.add(new ProcessoCliente(3));
	}

	// Método que inicia o processo de envio de mensagens por parte dos processos clientes.
	public void enviarMsgs() {
		// Obtém o processo sequenciador móvel líder, que será o destino das mensagens.
		ProcessoSequenciador processoDestino = AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider();

		// Lista de mensagens que serão enviadas pelos processos clientes.
		List<Mensagem> mensagens = new ArrayList<>();

		// Adiciona as mensagens à lista de mensagens.
		mensagens.add(new Mensagem(listaDeProcessosCliente.get(0), "M1", processoDestino));
		mensagens.add(new Mensagem(listaDeProcessosCliente.get(1), "M2", processoDestino));
		mensagens.add(new Mensagem(listaDeProcessosCliente.get(2), "M3", processoDestino));

		// String que representa a etapa 1 do processo de envio de mensagens. Essa string é printada no console e adicionada ao documento PDF.
		String etapa = "Etapa 1: Emissão das msgs nos processos do grupo emissor";
		// Printa a string no console.
		System.out.println(etapa);

		// Adiciona a string ao documento PDF.
		AppMain.DOCUMENTO_PDF.addNormalText(etapa, false);

		// For que percorre a lista de mensagens e printa no console e adiciona ao documento PDF as mensagens enviadas.
		for (Mensagem msg : mensagens) {

			// String que demonstra qual processo enviou a mensagem, a mensagem enviada e o processo de destino.
			String resultado = msg.getProcessoEmissor().getName() + " enviou a msg " + msg.getMensagem() + " p/ " + msg.getProcessoDestino().getName();

			// Printa a string no console.
			System.out.println(resultado);

			// Adiciona a string ao documento PDF.
			AppMain.DOCUMENTO_PDF.addNormalText(resultado, false);

			// Adiciona na tabela que o processo enviou a mensagem.
			AppMain.TABELA.addMensagemEnviada(msg.getProcessoEmissor(), msg.getMensagem());
		}

		// Adiciona a tabela ao documento PDF.
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		// Chama o método de processar mensagens do grupo de sequenciadores móveis.
		AppMain.GRUPO_SEQUENCIADOR_MOVEL.processarMsgsDoCliente(mensagens);
	}


	// Método que recebe as mensagens enviadas pelos processos sequenciadores móveis.
	public void receberMsgs(List<Mensagem> msgs) {

		// Pega o nome do sequenciador móvel que enviou as mensagens.
		String sequenciador = AppMain.GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName();

		// For que percorre a lista de mensagens recebidas e printa no console e adiciona ao documento PDF as mensagens recebidas.
		for (Mensagem msg : msgs) {

			// String que demonstra qual processo recebeu a mensagem, a mensagem recebida e o processo de origem.
			String resultado = msg.getProcessoDestino().getName() + " recebeu a msg " + msg.getMensagem() + " enviada por " + sequenciador;

			// Printa a string no console.
			System.out.println(resultado);

			// Adiciona a string ao documento PDF.
			AppMain.DOCUMENTO_PDF.addNormalText(resultado, false);

			// Adiciona na tabela que o processo recebeu a mensagem.
			AppMain.TABELA.addMensagemRecebida(msg.getProcessoDestino(), msg.getMensagem());
		}

		// Adiciona a tabela ao documento PDF.
		AppMain.DOCUMENTO_PDF.addTableToPdfUsingAppTable();

		// Chama o método de trocar líder do grupo de sequenciadores móveis, afinal, a iteração foi finalizada.
		AppMain.GRUPO_SEQUENCIADOR_MOVEL.trocarLider();
	}
}
