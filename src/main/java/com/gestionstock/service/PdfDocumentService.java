package com.gestionstock.service;

import com.gestionstock.model.CommercialDocument;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;

@Service
public class PdfDocumentService {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final float MARGIN = 50;

    public byte[] generate(CommercialDocument document) {
        try (PDDocument pdf = new PDDocument(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            pdf.addPage(page);
            try (PDPageContentStream content = new PDPageContentStream(pdf, page)) {
                var regularFont = font(Standard14Fonts.FontName.HELVETICA);
                var boldFont = font(Standard14Fonts.FontName.HELVETICA_BOLD);
                drawDocument(content, page, document, regularFont, boldFont);
            }
            pdf.save(output);
            return output.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("Impossible de generer le PDF", exception);
        }
    }

    private void drawDocument(PDPageContentStream content, PDPage page, CommercialDocument document,
            PDType1Font regularFont, PDType1Font boldFont) throws IOException {
        float width = page.getMediaBox().getWidth();
        float height = page.getMediaBox().getHeight();
        float right = width - MARGIN;
        float y = height - 54;

        drawHeader(content, regularFont, boldFont, width, y, document);
        y -= 105;
        drawInfoBlocks(content, document, regularFont, boldFont, y, right);
        y -= 150;
        drawItemsTable(content, document, regularFont, boldFont, y, right);
        drawLegalNote(content, regularFont, boldFont, right);
        drawStampAndSignature(content, regularFont, boldFont, right);
        drawFooter(content, regularFont, width);
    }

    private void drawHeader(PDPageContentStream content, PDType1Font regularFont, PDType1Font boldFont, float width,
            float y, CommercialDocument document) throws IOException {
        fillColor(content, 20, 53, 37);
        content.addRect(0, y - 34, width, 88);
        content.fill();

        fillColor(content, 223, 243, 232);
        content.addRect(MARGIN, y - 12, 42, 42);
        content.fill();
        text(content, boldFont, 14, MARGIN + 11, y + 3, "SP", 20, 53, 37);

        text(content, boldFont, 18, 104, y + 14, "StockPilot", 255, 255, 255);
        text(content, regularFont, 9, 104, y - 3, "Gestion de stock et documents commerciaux", 223, 243, 232);
        text(content, boldFont, 22, width - 240, y + 10, documentTitle(document), 255, 255, 255);
        text(content, regularFont, 9, width - 240, y - 8,
                "Document genere le " + formatDate(LocalDateTime.now()), 223, 243, 232);
    }

    private void drawInfoBlocks(PDPageContentStream content, CommercialDocument document, PDType1Font regularFont,
            PDType1Font boldFont, float y, float right) throws IOException {
        float leftWidth = 240;
        float rightWidth = 230;
        box(content, MARGIN, y - 86, leftWidth, 96, 248, 250, 248);
        box(content, right - rightWidth, y - 86, rightWidth, 96, 248, 250, 248);

        text(content, boldFont, 11, MARGIN + 14, y - 8, "Emetteur");
        text(content, regularFont, 10, MARGIN + 14, y - 28, "StockPilot");
        text(content, regularFont, 10, MARGIN + 14, y - 44, "Service gestion de stock");
        text(content, regularFont, 10, MARGIN + 14, y - 60, "Email: contact@stockpilot.local");
        text(content, regularFont, 10, MARGIN + 14, y - 76, "Telephone: +212 000 000 000");

        text(content, boldFont, 11, right - rightWidth + 14, y - 8, "Document");
        text(content, regularFont, 10, right - rightWidth + 14, y - 28, "Numero: " + documentNumber(document));
        text(content, regularFont, 10, right - rightWidth + 14, y - 44, "Type: " + documentTitle(document));
        text(content, regularFont, 10, right - rightWidth + 14, y - 60, "Date: " + formatDate(document.getCreatedAt()));
        text(content, regularFont, 10, right - rightWidth + 14, y - 76,
                "Destinataire: " + nullSafe(document.getCustomerName()));
    }

    private void drawItemsTable(PDPageContentStream content, CommercialDocument document, PDType1Font regularFont,
            PDType1Font boldFont, float y, float right) throws IOException {
        float tableWidth = right - MARGIN;
        float headerHeight = 28;
        float rowHeight = 118;

        fillColor(content, 20, 53, 37);
        content.addRect(MARGIN, y, tableWidth, headerHeight);
        content.fill();

        text(content, boldFont, 10, MARGIN + 14, y + 10, "Reference", 255, 255, 255);
        text(content, boldFont, 10, MARGIN + 120, y + 10, "Designation", 255, 255, 255);
        text(content, boldFont, 10, MARGIN + 310, y + 10, "Details", 255, 255, 255);
        text(content, boldFont, 10, right - 56, y + 10, "Qte", 255, 255, 255);

        box(content, MARGIN, y - rowHeight, tableWidth, rowHeight, 255, 255, 255);
        text(content, regularFont, 10, MARGIN + 14, y - 22, documentNumber(document));
        text(content, boldFont, 10, MARGIN + 120, y - 22, documentTitle(document));

        List<String> lines = wrap(nullSafe(document.getDescription()), regularFont, 10, 190);
        if (lines.isEmpty()) {
            lines.add("-");
        }
        float lineY = y - 22;
        for (String line : lines.subList(0, Math.min(lines.size(), 5))) {
            text(content, regularFont, 10, MARGIN + 310, lineY, line);
            lineY -= 15;
        }
        text(content, regularFont, 10, right - 44, y - 22, "1");

        strokeColor(content, 220, 228, 223);
        content.moveTo(MARGIN + 104, y);
        content.lineTo(MARGIN + 104, y - rowHeight);
        content.moveTo(MARGIN + 294, y);
        content.lineTo(MARGIN + 294, y - rowHeight);
        content.moveTo(right - 72, y);
        content.lineTo(right - 72, y - rowHeight);
        content.stroke();
    }

    private void drawLegalNote(PDPageContentStream content, PDType1Font regularFont, PDType1Font boldFont, float right)
            throws IOException {
        float y = 300;
        box(content, MARGIN, y - 74, right - MARGIN, 74, 248, 250, 248);
        text(content, boldFont, 11, MARGIN + 14, y - 18, "Observations");
        text(content, regularFont, 9, MARGIN + 14, y - 38,
                "Ce document est genere par StockPilot et doit etre verifie avant validation finale.");
        text(content, regularFont, 9, MARGIN + 14, y - 54,
                "Toute modification manuscrite doit etre approuvee par le responsable habilite.");
    }

    private void drawStampAndSignature(PDPageContentStream content, PDType1Font regularFont, PDType1Font boldFont,
            float right) throws IOException {
        float y = 190;
        float boxWidth = 200;
        box(content, MARGIN, y - 92, boxWidth, 92, 255, 255, 255);
        box(content, right - boxWidth, y - 92, boxWidth, 92, 255, 255, 255);

        text(content, boldFont, 11, MARGIN + 14, y - 18, "Cachet de l'entreprise");
        strokeColor(content, 34, 84, 61);
        content.addRect(MARGIN + 44, y - 76, 112, 42);
        content.stroke();
        text(content, boldFont, 13, MARGIN + 72, y - 50, "CACHET");

        text(content, boldFont, 11, right - boxWidth + 14, y - 18, "Signature autorisee");
        strokeColor(content, 90, 104, 97);
        content.moveTo(right - boxWidth + 14, y - 68);
        content.lineTo(right - 14, y - 68);
        content.stroke();
        text(content, regularFont, 8, right - boxWidth + 14, y - 82, "Nom, fonction et signature");
    }

    private void drawFooter(PDPageContentStream content, PDType1Font regularFont, float width) throws IOException {
        strokeColor(content, 220, 228, 223);
        content.moveTo(MARGIN, 60);
        content.lineTo(width - MARGIN, 60);
        content.stroke();
        text(content, regularFont, 8, MARGIN, 44, "StockPilot - Document commercial genere automatiquement");
        text(content, regularFont, 8, width - 168, 44, "Page 1 / 1");
    }

    private void box(PDPageContentStream content, float x, float y, float width, float height, int red, int green,
            int blue) throws IOException {
        fillColor(content, red, green, blue);
        content.addRect(x, y, width, height);
        content.fill();
        strokeColor(content, 220, 228, 223);
        content.addRect(x, y, width, height);
        content.stroke();
    }

    private void text(PDPageContentStream content, PDType1Font font, int size, float x, float y, String text)
            throws IOException {
        text(content, font, size, x, y, text, 22, 32, 27);
    }

    private void text(PDPageContentStream content, PDType1Font font, int size, float x, float y, String text,
            int red, int green, int blue) throws IOException {
        fillColor(content, red, green, blue);
        content.beginText();
        content.setFont(font, size);
        content.newLineAtOffset(x, y);
        content.showText(safeText(text));
        content.endText();
    }

    private List<String> wrap(String text, PDType1Font font, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (String word : safeText(text).split("\\s+")) {
            String candidate = current.length() == 0 ? word : current + " " + word;
            if (font.getStringWidth(candidate) / 1000 * fontSize <= maxWidth) {
                current = new StringBuilder(candidate);
            } else {
                if (current.length() > 0) {
                    lines.add(current.toString());
                }
                current = new StringBuilder(word);
            }
        }
        if (current.length() > 0) {
            lines.add(current.toString());
        }
        return lines;
    }

    private void fillColor(PDPageContentStream content, int red, int green, int blue) throws IOException {
        content.setNonStrokingColor(red / 255f, green / 255f, blue / 255f);
    }

    private void strokeColor(PDPageContentStream content, int red, int green, int blue) throws IOException {
        content.setStrokingColor(red / 255f, green / 255f, blue / 255f);
    }

    private PDType1Font font(Standard14Fonts.FontName fontName) {
        return new PDType1Font(fontName);
    }

    private String documentTitle(CommercialDocument document) {
        return document.getType() == null ? "DOCUMENT" : document.getType().name().replace('_', ' ');
    }

    private String documentNumber(CommercialDocument document) {
        return document.getNumber() == null || document.getNumber().isBlank()
                ? "DOC-" + (document.getId() == null ? "N/A" : document.getId())
                : document.getNumber();
    }

    private String formatDate(LocalDateTime date) {
        return date == null ? "-" : date.format(DATE_FORMAT);
    }

    private String nullSafe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    private String safeText(String value) {
        String normalized = Normalizer.normalize(nullSafe(value), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalized.replaceAll("[^\\x20-\\x7E]", " ");
    }
}
