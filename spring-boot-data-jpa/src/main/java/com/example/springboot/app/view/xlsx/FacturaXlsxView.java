package com.example.springboot.app.view.xlsx;

import com.example.springboot.app.models.entity.Invoice;
import com.example.springboot.app.models.entity.ItemInvoice;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        //Establecer nombre del archivo
        response.setHeader("Content-Disposition","attachment; filename=\"factura.xlsx\"");

        Invoice factura = (Invoice) model.get("factura");
        //nombre de la hoja
        Sheet sheet = workbook.createSheet("Factura");
        //Forma 1 de hacerlo celda por celda
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Datos del cliente");

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getNombre() + " "+ factura.getCliente().getApellido());

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getEmail());

        //forma 2 de hacerlo por encadenación de métodos
        sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
        sheet.createRow(5).createCell(0).setCellValue("folio: "+ factura.getId());
        sheet.createRow(6).createCell(0).setCellValue("Descripcion: "+ factura.getDescripcion());
        sheet.createRow(7).createCell(0).setCellValue("Fecha:"+factura.getCreateAt());

        CellStyle theaderStyle = workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);

        //Creación de tabla con detalle de productos
        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue("Producto");
        header.createCell(1).setCellValue("Precio");
        header.createCell(2).setCellValue("Cantidad");
        header.createCell(3).setCellValue("Total");
        //Se aplica estilo al thead
        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);

        int rows = 10;

        for (ItemInvoice item : factura.getItemsFactura()){
                Row fila = sheet.createRow(rows ++);

                cell = fila.createCell(0);
                cell.setCellValue(item.getProducto().getNombre());
                cell.setCellStyle(tbodyStyle);

                cell = fila.createCell(1);
                cell.setCellValue(item.getProducto().getPrecio());
                cell.setCellStyle(tbodyStyle);

                cell = fila.createCell(2);
                cell.setCellValue(item.getCantidad());
                cell.setCellStyle(tbodyStyle);

                cell = fila.createCell(3);
                cell.setCellValue(item.getImporte());
                cell.setCellStyle(tbodyStyle);
        }

        Row filaTotal = sheet.createRow(rows);
        filaTotal.createCell(2).setCellValue("TOTAL:");
        filaTotal.createCell(3).setCellValue(factura.getTotal());
    }
}
