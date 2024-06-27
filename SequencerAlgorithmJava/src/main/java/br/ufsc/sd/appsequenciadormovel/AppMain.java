package br.ufsc.sd.appsequenciadormovel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.ufsc.sd.appsequenciadormovel.entities.cliente.GrupoEmissor;
import br.ufsc.sd.appsequenciadormovel.common.DocPDF;
import br.ufsc.sd.appsequenciadormovel.entities.sequenciadormovel.GrupoSequenciadorMovel;
import br.ufsc.sd.appsequenciadormovel.common.TabelaPadrao;
import br.ufsc.sd.appsequenciadormovel.entities.servidor.GrupoServidor;

@SpringBootApplication
public class AppMain {

	public static final DocPDF DOCUMENTO_PDF = new DocPDF();
	public static TabelaPadrao TABELA;
	public static final GrupoEmissor GRUPO_EMISSOR = new GrupoEmissor();
	public static final GrupoSequenciadorMovel GRUPO_SEQUENCIADOR_MOVEL = new GrupoSequenciadorMovel();
	public static final GrupoServidor GRUPO_SERVIDOR = new GrupoServidor();

	public static void main(String[] args) {
		SpringApplication.run(AppMain.class, args);

		for (int i = 0; i < GRUPO_SEQUENCIADOR_MOVEL.getQuantidadeDeProcessosSequenciadores(); i++) {
			TABELA = new TabelaPadrao();
			String output = GRUPO_SEQUENCIADOR_MOVEL.getProcessoSequenciadorLider().getName() + " é o Lider dos processos sequenciadores";
			AppMain.DOCUMENTO_PDF.addNormalText(output, false);
			System.out.println(output);
			GRUPO_EMISSOR.enviarMsgs();
		}

		DOCUMENTO_PDF.saveDoc();
	}

}
