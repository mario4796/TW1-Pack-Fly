package com.tallerwebi.config;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
@Service
public class PdfGenerator {
    public static byte[] generarComprobante(String cliente, String detalle, double monto) throws Exception {
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(doc, out);

        doc.open();
        doc.add(new Paragraph("Comprobante de Pago", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Cliente: " + cliente));
        doc.add(new Paragraph("Detalle: " + detalle));
        doc.add(new Paragraph("Monto: $" + monto));
        doc.add(new Paragraph("Fecha: " + java.time.LocalDate.now()));
        doc.close();

        return out.toByteArray();
    }
}
