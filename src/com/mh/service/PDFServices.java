package com.mh.service;

import static com.mh.model.HMConstants.a4Height;
import static com.mh.model.HMConstants.a4Width;
import static com.mh.model.HMConstants.tempDirectoryPath;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFServices {

	//public final static int a4Height = 297;
	//public final static int a4Width = 210;
	//public final static String tempDirectoryPath = "E:\\temp";
	
	public String convertImageToPdf(String imagePath) {
		// TODO Auto-generated method stub
		//String imagePath = fileInfo.get("FileName");
		int totelNoOfFiles = new File(tempDirectoryPath).listFiles().length;
		try {
			File f = new File(imagePath);
			BufferedImage image = ImageIO.read(f);
			int height = image.getHeight();
			int width = image.getWidth();
			System.out.println("Height : "+ height);
			System.out.println("Width : "+ width);
			if(width>(a4Width-20)){
				BufferedImage resizeImageJpg = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH,
			               a4Width-20, Scalr.OP_ANTIALIAS);
				String newPath = tempDirectoryPath+File.separator+"HM_"+totelNoOfFiles+".jpg";
				ImageIO.write(resizeImageJpg, "jpg", new File(newPath));
				imagePath = newPath;
			}
			
		/*	if(height>a4Height||width>a4Width){
				System.out.println("Going to resize Image");
				Dimension resultDim = getScaledDimension(height,width);
				//BufferedImage image = ImageIO.read(new File(imagePath));
				int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
				BufferedImage resizeImageJpg = resizeImage(image, type, resultDim);
				
				String newPath = tempDirectoryPath+"\\HM_"+totelNoOfFiles+".jpg";
				ImageIO.write(resizeImageJpg, "jpg", new File(newPath));
				imagePath = newPath;
			}*/
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return imageConvertToPdf(imagePath, tempDirectoryPath, totelNoOfFiles);
	}
	private BufferedImage resizeImage(BufferedImage originalImage, int type, Dimension resultDim) {
		BufferedImage resizedImage = new BufferedImage((int)resultDim.getWidth(), (int)resultDim.getHeight(), type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, (int)resultDim.getWidth(), (int)resultDim.getHeight(), null);
		g.dispose();
	 
		return resizedImage;
	    }
	private Dimension getScaledDimension(int original_height, int original_width) {
		System.out.println("Original Height"+original_height+"Original Width"+original_width);
		int new_width = 0;
		int new_height = 0;
		if (original_width > a4Width) {
	        //ale width to fit
	        new_width = a4Width;
	        //scale height to maintain aspect ratio
	        new_height = (new_width*original_height)/original_width;
	    }else{
	    	new_width=original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > a4Height) {
	        //scale height to fit instead
	        new_height = a4Height;
	        //scale width to maintain aspect ratio
	        new_width = (new_height*new_width)/original_height;
	    }
	    System.out.println("New Height"+new_height+"New Width"+new_width);
		return new Dimension(new_width, new_height);
	}
	public String imageConvertToPdf(String imagePath, String tempDirectoryPath, int totelNoOfFiles){
		String newPath = tempDirectoryPath+File.separator+"HM_"+totelNoOfFiles+".pdf";
        try {
        	
            OutputStream file = new FileOutputStream(newPath);
            String inputImage = imagePath;
 
            Document document = new Document(PageSize.A2, 10, 10, 10, 10);
            PdfWriter.getInstance(document, file);
 
            document.open();
          //  document.add(new Paragraph("Hello World, iText"));
            document.add(new Paragraph(new Date().toString()));
            Image image2 = Image.getInstance(inputImage);
            document.add(image2);
            document.close();
            file.close();
 
        } catch (Exception e) {
 
            e.printStackTrace();
        }
        return newPath;
    
	}
	 public String mergeFiles(String tempPath) {
		 String outPath=null;
		 FileInputStream fin=null;
		 OutputStream output=null;
	        try {
	            List<InputStream> pdfs = new ArrayList<InputStream>();
	            File tempPathFiles = new File(tempPath);
	            tempPathFiles.mkdir();
	            if(tempPathFiles.list()!=null){
		            for(int i=0;i<tempPathFiles.list().length;i++)
		            {
		            	fin =new FileInputStream(tempPath+File.separator+"_"+(i+1)+".pdf");
		            	pdfs.add(fin);
		            	
		            }
	            }
	            
	            outPath = tempPath+File.separator+"resultPdf.pdf";
	            output = new FileOutputStream(outPath);
	            concatPDFs(pdfs, output, true);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return outPath;
	    }
	 
	    public void concatPDFs(List<InputStream> streamOfPDFFiles,
	            OutputStream outputStream, boolean paginate) {
	 
	        Document document = new Document(PageSize.A2, 10, 10, 10, 10);
	        try {
	            List<InputStream> pdfs = streamOfPDFFiles;
	            List<PdfReader> readers = new ArrayList<PdfReader>();
	            int totalPages = 0;
	            Iterator<InputStream> iteratorPDFs = pdfs.iterator();
	 
	            // Create Readers for the pdfs.
	            while (iteratorPDFs.hasNext()) {
	                InputStream pdf = iteratorPDFs.next();
	                PdfReader pdfReader = new PdfReader(pdf);
	                readers.add(pdfReader);
	                totalPages += pdfReader.getNumberOfPages();
	            }
	            // Create a writer for the outputstream
	            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
	 
	            document.open();
	            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
	                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
	            // data
	 
	            PdfImportedPage page;
	            int currentPageNumber = 0;
	            int pageOfCurrentReaderPDF = 0;
	            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
	 
	            // Loop through the PDF files and add to the output.
	            while (iteratorPDFReader.hasNext()) {
	                PdfReader pdfReader = iteratorPDFReader.next();
	 
	                // Create a new page in the target for each source page.
	                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
	                    document.newPage();
	                    pageOfCurrentReaderPDF++;
	                    currentPageNumber++;
	                    page = writer.getImportedPage(pdfReader,
	                            pageOfCurrentReaderPDF);
	                    cb.addTemplate(page, 0, 0);
	 
	                    // Code for pagination.
	                    if (paginate) {
	                        cb.beginText();
	                        cb.setFontAndSize(bf, 9);
	                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
	                                + currentPageNumber + " of " + totalPages, 520,
	                                5, 0);
	                        cb.endText();
	                    }
	                }
	                pageOfCurrentReaderPDF = 0;
	            }
	            outputStream.flush();
	            document.close();
	            outputStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (document.isOpen())
	                document.close();
	            try {
	                if (outputStream != null)
	                    outputStream.close();
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
	        }
	    }

}
