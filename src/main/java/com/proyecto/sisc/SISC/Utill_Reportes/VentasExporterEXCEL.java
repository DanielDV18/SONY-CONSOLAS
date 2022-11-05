package com.proyecto.sisc.SISC.Utill_Reportes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.proyecto.sisc.SISC.model.Venta;

public class VentasExporterEXCEL {
    private XSSFWorkbook libro;
    private XSSFSheet hoja;


    private List<Venta> listaVentas;

    public VentasExporterEXCEL(List<Venta> listaVentas){
        this.listaVentas = listaVentas;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Ventas");
    }
    private void escribirCabeceraTabla(){
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("Numero");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Fecha");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Total");
        celda.setCellStyle(estilo);
    }

    private void escribirDatosDeLaTabla(){
        int numeroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for(Venta venta : listaVentas){
            Row fila = hoja.createRow(numeroFilas ++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(venta.getNumero());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

             celda = fila.createCell(1);
            celda.setCellValue(venta.getFechaCreacion().toString());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(venta.getTotal());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);
        }
      
    }

    public void exportar(HttpServletResponse response) throws IOException{
        escribirCabeceraTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);

        libro.close();
        outputStream.close();
    }
}
