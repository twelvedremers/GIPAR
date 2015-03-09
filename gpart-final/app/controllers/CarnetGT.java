package controllers;

import models.*;
import com.itextpdf.text.BaseColor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CarnetGT {
	
	private static Font fontBold = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);
    private static Font fontNormal = new Font(Font.FontFamily.COURIER, 5, Font.NORMAL);
    

    public static void generarCarnet( String titulo, Miembro persona) throws IOException, DocumentException {
    	
     	Document document = getDocument();
     	PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream("public/carnet/"+persona.cedula+".pdf"));
     	document.open();
     	
     	PdfPTable table = getTable();
  		
  		document.add(getHeader(titulo));
                document.add(getInformation(" "));
                document.add(getInformation(" "));
                document.add(getInformation("    CARNET GIPAR"));
  		
                //Direccion de las dos imagenes
                 Image logo = Image.getInstance("public/images/logo solo.png");
                 Image foto=null;
                 if(persona.foto_url!=null)
               		foto = Image.getInstance("public/"+persona.foto_url);
           		else
           	 		foto = Image.getInstance("public/fotos_perfil/defecto.png");
           
                
           
                foto.scaleAbsolute(40f, 55f);
                logo.scaleAbsolute(95f, 75f);
                foto.setAbsolutePosition(170f, 70f);
                logo.setAbsolutePosition(5f, 60f);
                
                foto.setBorder(Image.BOX);
              
                foto.setBorderWidth(10);             
                
               foto.setBorderColor(BaseColor.BLUE);
               document.add(foto);
               document.add(logo);
                
       
        table.addCell(getCell(" "));
        table.addCell(getCell(" "));
        table.addCell(getCell(" "));
        table.addCell(getCell(" "));
        table.addCell(getCell("NOMBRE: "+persona.nombre));
        table.addCell(getCell("APELLIDO: "+persona.apellidos));
        table.addCell(getCell("CEDULA: "+persona.cedula));
        table.addCell(getCell("Nivel acceso: "+persona.nivel_A.toString()));
         
        document.add(table);
        document.add(getBarcode(document, pw, ""+persona.cod_G,persona.cedula));
        
     	document.close();
     	
     }
     
     private static Document getDocument(){
    	Document document = new Document(new Rectangle( getConvertCmsToPoints(8), getConvertCmsToPoints(5)));
      	document.setMargins(1, 1, 1, 1);
     
      	return document;
     }
     
     private static Paragraph getHeader(String header) {
    	Paragraph paragraph = new Paragraph();
  		Chunk chunk = new Chunk();
		paragraph.setAlignment(Element.ALIGN_CENTER);
  		chunk.setFont(fontBold);
  		paragraph.add(chunk);
  		return paragraph;
     }
     
     private static Paragraph getInformation(String informacion) {
    	Paragraph paragraph = new Paragraph();
    	Chunk chunk = new Chunk();
  		paragraph.setAlignment(Element.ALIGN_CENTER);
  		chunk.append(informacion);
  		chunk.setFont(fontBold);
  		paragraph.add(chunk);
   		return paragraph;
      }
     
     
  
     private static PdfPTable getTable() throws DocumentException {
     	PdfPTable table = new PdfPTable(1);
     	table.setWidths(new int[]{17});
		return table;
     }
     
     private static PdfPCell getCell(String text) throws DocumentException, IOException {
     	Chunk chunk = new Chunk();
      	chunk.append(text);
     	chunk.setFont(fontNormal);
     	PdfPCell cell = new PdfPCell(new Paragraph(chunk));
                
 		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
 		cell.setBorder(Rectangle.NO_BORDER);
 		return cell;
     }
     
     private static float getConvertCmsToPoints(float cm) {
     	return cm * 28.4527559067f;
     }
     
    
     
    private static Image getBarcode(Document document,  PdfWriter pdfWriter, String servicio,String  codigoTransaccion){
	 	PdfContentByte cimg = pdfWriter.getDirectContent();
	   	Barcode128 code128 = new Barcode128();
	   	code128.setCode(servicio + addZeroLeft(codigoTransaccion));
	   	code128.setCodeType(Barcode128.CODE128);
		code128.setTextAlignment(Element.ALIGN_CENTER);
		Image image = code128.createImageWithBarcode(cimg, null, null);
		float scaler = ((document.getPageSize().getWidth() - document.leftMargin()  - document.rightMargin() - 0) / image.getWidth()) * 30;
		image.scalePercent(scaler);
                image.setAbsolutePosition(150f, 10f);
		return image;
	}
    
    private static String addZeroLeft(String num) {
    	NumberFormat formatter = new DecimalFormat("0000000");
     	return formatter.format((num != null) ? Integer.parseInt(num) : 0000000);
	}

	public static void imprimirFactura(){
    	
    	Desktop d=Desktop.getDesktop();
    	try {
    		if(Desktop.isDesktopSupported()){
    			d.print(new File("public/carnet/ejemplo.pdf"));
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}