package milo.probell.Model.ProductoModel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import milo.probell.Model.ClienteModel.Cliente;


public class PDFGenerator {

    private static final String TAG = "PDFGenerator";
    private Context context;

    public PDFGenerator(Context context) {
        this.context = context;
    }


    public void createPdfProductos(List<Producto> productosSeleccionados) throws IOException {
        File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "productosSeleccionados.pdf");
        OutputStream outputStream = new FileOutputStream(pdfFile);
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        // Título del PDF
        Paragraph titulo = new Paragraph("PROBELL")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLACK);
        document.add(titulo);

        // Crear una tabla con 1 columna
        Table table = new Table(1); // Definimos dos columnas
        table.setWidth(UnitValue.createPercentValue(100));

        // Agregar cada producto a la tabla en una fila de dos columnas
        for (Producto producto : productosSeleccionados) {
            // Crear un contenedor para el producto
            Table productTable = new Table(UnitValue.createPercentValue(100).getUnitType());

            // Agregar imagen del producto
            byte[] imagenBytes = producto.getImagen();
            if (imagenBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
                File tempFile = File.createTempFile("tempImage", ".jpg", context.getCacheDir());
                FileOutputStream fos = new FileOutputStream(tempFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                ImageData imageData = ImageDataFactory.create(tempFile.getAbsolutePath());
                Image img = new Image(imageData);
                img.setWidth(150); // Tamaño ajustado de la imagen
                img.setHeight(150);
                productTable.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));
            } else {
                productTable.addCell(new Cell().add(new Paragraph("No hay imagen disponible.")).setBorder(Border.NO_BORDER));
            }

            // Agregar detalles del producto
            productTable.addCell(new Cell().add(new Paragraph(producto.getNombre()).setFontSize(12)).setBorder(Border.NO_BORDER));
            productTable.addCell(new Cell().add(new Paragraph("Precio: $" + producto.getPrecio()).setFontSize(12)).setBorder(Border.NO_BORDER));

            // Añadir el producto en una celda en la tabla de dos columnas
            table.addCell(new Cell().add(productTable).setMargin(10));
        }

        document.add(table);
        document.close();
    }


    public void compartirCatalogo(List<Producto> productosSeleccionados) {
        try {
            // Ruta del archivo PDF
            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "productosSeleccionados.pdf");
            createPdfProductos(productosSeleccionados);

            Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", pdfFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Aquí está el PDF con los productos seleccionados.");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Intent para WhatsApp
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("application/pdf");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Aquí está el PDF con los productos seleccionados.");
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Verifica si WhatsApp está instalado en el dispositivo
            if (whatsappIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(whatsappIntent);
            } else {
                // Si WhatsApp no está instalado, abre el selector de aplicaciones
                context.startActivity(Intent.createChooser(shareIntent, "Compartir catálogo"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createFacturaPdf(Cliente cliente, int numeroFactura, String montoTotal, List<Vector<Object>> listaDetalle, boolean incluirDelivery) throws IOException {
        // Ruta del archivo PDF
        File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "factura.pdf");
        OutputStream outputStream = new FileOutputStream(pdfFile);

        // Crear el documento PDF
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        // Agrega el título al principio del documento
        Paragraph titulo = new Paragraph("FACTURA")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLACK);
        document.add(titulo);

        // Información del emisor y del cliente
        document.add(new Paragraph("av. 16 de Julio, Radial 10, Calle 4, 39 Santa Cruz, Bolivia")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT));

        document.add(new Paragraph("PARA: " + cliente.getNombre() + "\n"+"Cedula: " + cliente.getCedula() + "\n"+"Celular: " + cliente.getCelular() )
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginTop(10));

        // Información de la factura
        LocalDate fechaActual = LocalDate.now();
        document.add(new Paragraph("N° de factura: " + numeroFactura + "\nFecha: " + fechaActual + "\nVencimiento: " + fechaActual + "\nFecha de entrega: " + fechaActual)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(-50));  // Ajustar la posición para que esté en la esquina superior derecha

        // Crear la tabla con los detalles de la factura
        float[] columnWidths = {5, 2, 2, 2}; // Anchuras de las columnas
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Encabezado de la tabla
        table.addHeaderCell(new Cell().add(new Paragraph("NOMBRE DEL PRODUCTO").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("CANTIDAD").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("PRECIO/U (Bs)").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("TOTAL (Bs)").setBold()));

        // Llenar la tabla con los detalles de la factura
        for (Vector detalle : listaDetalle) {
            table.addCell(new Cell().add(new Paragraph(detalle.get(1).toString()))); // NOMBRE DEL PRODUCTO
            table.addCell(new Cell().add(new Paragraph(detalle.get(2).toString()))); // CANTIDAD
            table.addCell(new Cell().add(new Paragraph(detalle.get(3).toString()))); // PRECIO UNITARIO
            table.addCell(new Cell().add(new Paragraph(detalle.get(4).toString()))); // TOTAL
        }

        document.add(table);

        // Mostrar el costo de delivery si se ha seleccionado
        if (incluirDelivery) {
            document.add(new Paragraph("Costo de Delivery (Bs): " + 15)
                    .setFontSize(12)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(20));

            // Sumar el costo de delivery al total
            double totalConDelivery = Double.parseDouble(montoTotal) ;
            document.add(new Paragraph("TOTAL A PAGAR (Bs): " + totalConDelivery)
                    .setFontSize(12)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT));
        } else {
            // Mostrar solo el total si no hay delivery
            document.add(new Paragraph("TOTAL A PAGAR (Bs): " + montoTotal)
                    .setFontSize(12)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT));
        }

        document.close();
    }
    public void generateAndShareFacturaPdf(Context context, Cliente persona, int numeroFactura, String montoTotal, List<Vector<Object>> listaDetalle, boolean incluirDelivery) {
        try {
            // Ruta del archivo PDF
            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "factura.pdf");
            PDFGenerator pdfGenerator = new PDFGenerator(context);
            pdfGenerator.createFacturaPdf(persona, numeroFactura, montoTotal, listaDetalle, incluirDelivery);

            // Obtener URI para el archivo PDF
            Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", pdfFile);

            // Intent para compartir el PDF con la primera persona
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra("jid", "591" + persona.getCelular() + "@s.whatsapp.net"); // Número de la persona
            shareIntent.setPackage("com.whatsapp"); // Especificar WhatsApp
            context.startActivity(shareIntent); // Enviar a la persona seleccionada

            if (incluirDelivery) {
                // Crear un intent para enviar la ubicación al delivery
                Intent deliveryLocationIntent = new Intent(Intent.ACTION_SEND);
                deliveryLocationIntent.setType("text/plain");
                deliveryLocationIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                deliveryLocationIntent.putExtra(Intent.EXTRA_TEXT, "Dirección de entrega: " + persona.getDireccion());

                // Crear el chooser para seleccionar la aplicación para el delivery
                Intent chooserLocation = Intent.createChooser(deliveryLocationIntent, "Compartir ubicación a través de");
                context.startActivity(chooserLocation);

                // Crear intent para enviar el PDF al delivery
                Intent deliveryPdfIntent = new Intent(Intent.ACTION_SEND);
                deliveryPdfIntent.setType("application/pdf");
                deliveryPdfIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
                deliveryPdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Crear el chooser para seleccionar la aplicación para compartir el PDF
                Intent chooserPdf = Intent.createChooser(deliveryPdfIntent, "Compartir PDF de entrega a través de");
                context.startActivity(chooserPdf);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
