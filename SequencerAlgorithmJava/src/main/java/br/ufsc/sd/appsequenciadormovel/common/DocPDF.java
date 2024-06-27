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

public class DocPDF {
	// Define o caminho do arquivo PDF
	private static final String filePath = System.getProperty("user.dir") + "/Relatorio.pdf";
	private Document document;
	private Font smallFont = new Font(Font.FontFamily.HELVETICA, 4);

	public DocPDF() {
		document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
		} catch (DocumentException | FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public void addNormalText(String text, boolean blankLine) {
		try {
			// Adicionando um texto normal
			Paragraph paragraph = new Paragraph(text, smallFont);
			document.add(paragraph);

			if(blankLine) {
				document.add(new Paragraph(" ", smallFont));
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void addTableToPdfUsingAppTable() {
		try {
			// Adicionando uma linha em branco
			document.add(new Paragraph(" ", smallFont));

			String[][] tableData = AppMain.TABELA.getTableData();

			PdfPTable table = new PdfPTable(tableData[0].length);
			table.setWidthPercentage(100);

			// Adicionando linhas à tabela
			for (int i = 0; i < tableData.length; i++) { // Percorre as linhas
				for (int j = 0; j < tableData[i].length; j++) { // Percorre as colunas
					//table.addCell(new Cell().add(tableData[i][j]));
					table.addCell(new PdfPCell(new Phrase(tableData[i][j], smallFont)));
				}
			}

			document.add(table);

		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

	public void saveDoc() {
		document.close();
	}


}
