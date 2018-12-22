package cn.zhou.accidence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.extractor.POIOLE2TextExtractor;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.hdgf.extractor.VisioTextExtractor;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.extractor.ExtractorFactory;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

/*
 * poi 是用来操作各种microsoft文档或者转成其他格式文档的api
 * 
 */
public class PoiAccidence {

	public static void main(String[] args) {

		try {
			HWPFDocument wordDocument = new HWPFDocument(new FileInputStream("/home/zhou/a.docx"));

			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
					DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

			FileOutputStream fos = new FileOutputStream("/home/zhou/poi.html");
			// byte b=wordToHtmlConverter.get
			String s = wordToHtmlConverter.toString();
			byte[] b = s.getBytes();

			fos.write(b);
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void poifs() {

	}

	@Test
	public void testReadByExtractor() throws Exception {

		InputStream is = new FileInputStream("/home/zhou/a.doc");
		WordExtractor extractor = new WordExtractor(is);

		// 输出word文档所有的文本
		System.out.println(extractor.getText());

		System.out.println(extractor.getTextFromPieces());

		// 输出页眉的内容
		System.out.println("页眉：" + extractor.getHeaderText());
		// 输出页脚的内容
		System.out.println("页脚：" + extractor.getFooterText());
		// 输出当前word文档的元数据信息，包括作者、文档的修改时间等。
		System.out.println(extractor.getMetadataTextExtractor().getText());
		// 获取各个段落的文本
		String paraTexts[] = extractor.getParagraphText();
		for (int i = 0; i < paraTexts.length; i++) {
			System.out.println("Paragraph " + (i + 1) + " : " + paraTexts[i]);
		}
		// 输出当前word的一些信息
		// printInfo(extractor.getSummaryInformation());
		// 输出当前word的一些信息
		// this.printInfo(extractor.getDocSummaryInformation());
		// this.closeStream(is);
	}

	@Test
	public void word() {
		
		 WordExtractor wordExtractor =（WordExtractor）textExtractor;
	      String [] paragraphText = wordExtractor.getParagraphText（）;
	      for（String paragraph：paragraphText）{
	         的System.out.println（段落）;
	      }
	      //显示文档的页眉和页脚文本
	      System.out.println（“Footer text：”+ wordExtractor.getFooterText（））;
	      System.out.println（“Header text：”+ wordExtractor.getHeaderText（））;
	}

	@Test
	public void word2() {
		FileInputStream fis = new FileInputStream("");
		POIFSFileSystem fileSystem = new POIFSFileSystem(fis);
		// Firstly, get an extractor for the Workbook
		POIOLE2TextExtractor oleTextExtractor = 
				ExtractorFactory.createExtractor(fileSystem);
		// Then a List of extractors for any embedded Excel, Word, PowerPoint
		// or Visio objects embedded into it.
		POITextExtractor[] embeddedExtractors = ExtractorFactory.getEmbededDocsTextExtractors(oleTextExtractor);
		for (POITextExtractor textExtractor : embeddedExtractors) {
			// If the embedded object was an Excel spreadsheet.
			if (textExtractor instanceof ExcelExtractor) {
				ExcelExtractor excelExtractor = (ExcelExtractor) textExtractor;
				System.out.println(excelExtractor.getText());
			}
			// A Word Document
			else if (textExtractor instanceof WordExtractor) {
				WordExtractor wordExtractor = (WordExtractor) textExtractor;
				String[] paragraphText = wordExtractor.getParagraphText();
				for (String paragraph : paragraphText) {
					System.out.println(paragraph);
				}
				// Display the document's header and footer text
				System.out.println("Footer text: " + wordExtractor.getFooterText());
				System.out.println("Header text: " + wordExtractor.getHeaderText());
			}
			// PowerPoint Presentation.
			else if (textExtractor instanceof PowerPointExtractor) {
				PowerPointExtractor powerPointExtractor = (PowerPointExtractor) textExtractor;
				System.out.println("Text: " + powerPointExtractor.getText());
				System.out.println("Notes: " + powerPointExtractor.getNotes());
			}
			// Visio Drawing
			else if (textExtractor instanceof VisioTextExtractor) {
				VisioTextExtractor visioTextExtractor = (VisioTextExtractor) textExtractor;
				System.out.println("Text: " + visioTextExtractor.getText());
			}
		}

	}

}
