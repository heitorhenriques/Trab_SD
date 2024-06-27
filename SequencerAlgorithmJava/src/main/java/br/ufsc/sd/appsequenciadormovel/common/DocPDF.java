package br.ufsc.sd.appsequenciadormovel.common;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import br.ufsc.sd.appsequenciadormovel.AppMain;

/**
 * Classe que representa um documento PDF.
 */
public class DocPDF {

	// Define o nome e o caminho do arquivo PDF (nesse caso, o caminho é o diretório atual do projeto).
	private static final String filePath = System.getProperty("user.dir") + "/Relatorio.pdf";

	// Define o documento PDF e a fonte do texto.
	private Document document;
	private Font smallFont = new Font(Font.FontFamily.HELVETICA, 4);

	/**
	 * Construtor da classe, que cria um documento PDF e o abre.
	 */
	public DocPDF() {
		document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
		} catch (DocumentException | FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adiciona um texto normal ao documento PDF.
	 * @param text O texto a ser adicionado.
	 * @param blankLine Se deve ser adicionada uma linha em branco após o texto.
	 */
	public void addNormalText(String text, boolean blankLine) {
		try {
			// Adicionando um texto normal, sem a linha em branco
			Paragraph paragraph = new Paragraph(text, smallFont);
			document.add(paragraph);

			// Adicionando uma linha em branco, se necessário
			if(blankLine) {
				document.add(new Paragraph(" ", smallFont));
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria uma tabela no documento PDF e a preenche com os dados da tabela da aplicação.
	 */
	public void addTableToPdfUsingAppTable() {
		try {
			// Adicionando uma linha em branco
			document.add(new Paragraph(" ", smallFont));

			// Pegando os dados da tabela da aplicação
			String[][] tableData = AppMain.TABELA.getTableData();

			// Criando a tabela no documento PDF
			PdfPTable table = new PdfPTable(tableData[0].length);
			table.setWidthPercentage(100);

			// Preenche a tabela com os dados da tabela da aplicação
			for (int i = 0; i < tableData.length; i++) { // Percorre as linhas
				for (int j = 0; j < tableData[i].length; j++) { // Percorre as colunas
					table.addCell(new PdfPCell(new Phrase(tableData[i][j], smallFont)));
				}
			}

			// Adiciona a tabela ao documento PDF
			document.add(table);

		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Fecha o documento PDF e consequentemente o salva.
	 */
	public void saveDoc() {
		document.close();
	}


}
