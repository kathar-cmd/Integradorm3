package com.example.service;

import com.example.model.Libro;
import com.example.model.Prestamo;
import com.example.model.Usuario;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

@Service
public class PdfService {

    public byte[] generarComprobante(Prestamo prestamo, Usuario usuario, Libro libro) {
        try (PDDocument documento = new PDDocument();
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {

            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            PDType1Font negrita = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font normal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            try (PDPageContentStream cs = new PDPageContentStream(documento, pagina)) {
                cs.beginText();
                cs.setFont(negrita, 18);
                cs.newLineAtOffset(50, 750);
                cs.showText("Comprobante de prestamo - Biblioteca CESDE");

                cs.setFont(normal, 12);
                cs.newLineAtOffset(0, -40);
                cs.showText("N de prestamo: " + prestamo.getIdPrestamo());

                cs.newLineAtOffset(0, -25);
                cs.showText("Usuario: " + limpiar(usuario != null ? usuario.getNombre() : "No encontrado"));

                cs.newLineAtOffset(0, -20);
                cs.showText("Libro: " + limpiar(libro != null ? libro.getNombre() : "No encontrado"));

                cs.newLineAtOffset(0, -20);
                cs.showText("Cantidad de libros: " + prestamo.getCantidadLibros());

                cs.newLineAtOffset(0, -20);
                cs.showText("Fecha de prestamo: " + prestamo.getFechaPrestamo());

                cs.newLineAtOffset(0, -20);
                cs.showText("Fecha de entrega: " + prestamo.getFechaEntrega());
                cs.endText();
            }

            documento.save(salida);
            return salida.toByteArray();

        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo generar el PDF", e);
        }
    }

    private String limpiar(String texto) {
        if (texto == null) return "";
        return texto.replaceAll("[\\r\\n\\t]", " ")
                    .replaceAll("[^\\x20-\\x7E\\u00A0-\\u00FF]", "?");
    }
}
