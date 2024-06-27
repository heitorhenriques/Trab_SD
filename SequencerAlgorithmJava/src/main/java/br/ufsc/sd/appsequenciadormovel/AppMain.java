package br.ufsc.sd.appsequenciadormovel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.ufsc.sd.appsequenciadormovel.entities.cliente.GrupoEmissor;
import br.ufsc.sd.appsequenciadormovel.common.DocPDF;
import br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel.GrupoSequenciadorMovel;
import br.ufsc.sd.appsequenciadormovel.common.TabelaPadrao;
import br.ufsc.sd.appsequenciadormovel.entities.servidor.GrupoServidor;

/**
 * Classe principal da aplicação que inicia o sistema.
 */
@SpringBootApplication
public class AppMain {

	// Documento PDF que será gerado ao final da execução do sistema.
	public static final DocPDF DOCUMENTO_PDF = new DocPDF();

	// Tabela que será utilizada para armazenar os dados dos processos, como mensagens enviadas e recebidas.
	public static TabelaPadrao TABELA;

	// Grupos de entidades que serão utilizados na aplicação.
	// Grupo de processos emissores de mensagens.
	public static final GrupoEmissor GRUPO_EMISSOR = new GrupoEmissor();
	// Grupo de processos sequenciadores móveis.
	public static final GrupoSequenciadorMovel GRUPO_SEQUENCIADOR_MOVEL = new GrupoSequenciadorMovel();
	// Grupo de processos servidores.
	public static final GrupoServidor GRUPO_SERVIDOR = new GrupoServidor();

	/*
	 * Método principal da aplicação que inicia o sistema.
	 */
	public static void main(String[] args) {
		// Inicia a aplicação Spring Boot.
		SpringApplication.run(AppMain.class, args);

		// For que percorre a quantidade de processos sequenciadores móveis.
		for (int i = 0; i < GRUPO_SEQUENCIADOR_MOVEL.getQuantidadeDeProcessosSequenciadores(); i++) {
			// Instancia uma nova tabela para armazenar os dados dos processos.
			TABELA = new TabelaPadrao();

			// String que armazena o nome do processo sequenciador móvel que é o líder. Essa string é printada no console e adicionada ao documento PDF.
			String output = GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName() + " é o Lider dos processos sequenciadores";
			
			// Adiciona a string ao documento PDF.
			AppMain.DOCUMENTO_PDF.addNormalText(output, false);

			// Printa a string no console.
			System.out.println(output);

			// Inicia o processo, chamando o método de enviar mensagens do grupo de emissores.
			GRUPO_EMISSOR.enviarMsgs();
		}

		// Salva o documento PDF ao final da execução do sistema.
		DOCUMENTO_PDF.saveDoc();
	}

}
