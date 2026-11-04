package com.example.service;

import com.example.model.Prestamo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@Service
public class ExcelService {

    public byte[] exportarPrestamos(List<Prestamo> prestamos) {
        try (XSSFWorkbook libro = new XSSFWorkbook();
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {

            Sheet hoja = libro.createSheet("Prestamos");

            // Estilo del encabezado (negrita)
            Font negrita = libro.createFont();
            negrita.setBold(true);
            CellStyle estiloEncabezado = libro.createCellStyle();
            estiloEncabezado.setFont(negrita);

            String[] titulos = {"ID Préstamo", "ID Usuario", "ID Libro",
                    "Fecha de préstamo", "Fecha de entrega", "Cantidad de libros"};

            Row encabezado = hoja.createRow(0);
            for (int i = 0; i < titulos.length; i++) {
                Cell celda = encabezado.createCell(i);
                celda.setCellValue(titulos[i]);
                celda.setCellStyle(estiloEncabezado);
            }

            int fila = 1;
            for (Prestamo p : prestamos) {
                Row r = hoja.createRow(fila++);
                r.createCell(0).setCellValue(texto(p.getIdPrestamo()));
                r.createCell(1).setCellValue(texto(p.getIdUsuario()));
                r.createCell(2).setCellValue(texto(p.getIdLibro()));
                r.createCell(3).setCellValue(texto(p.getFechaPrestamo()));
                r.createCell(4).setCellValue(texto(p.getFechaEntrega()));
                r.createCell(5).setCellValue(texto(p.getCantidadLibros()));
            }

            for (int i = 0; i < titulos.length; i++) {
                hoja.autoSizeColumn(i);
            }

            libro.write(salida);
            return salida.toByteArray();

        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo generar el Excel", e);
        }
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }
}
