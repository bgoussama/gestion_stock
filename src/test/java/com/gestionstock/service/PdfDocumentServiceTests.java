package com.gestionstock.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.gestionstock.model.CommercialDocument;
import com.gestionstock.model.DocumentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PdfDocumentServiceTests {
    private final PdfDocumentService pdfDocumentService = new PdfDocumentService();

    @Test
    void generatesProfessionalPdf() {
        CommercialDocument document = new CommercialDocument();
        document.setId(1L);
        document.setType(DocumentType.FACTURE);
        document.setNumber("FAC-001");
        document.setCustomerName("Client Demo");
        document.setDescription("Document de test pour verifier le cachet, la signature et le modele.");
        document.setCreatedAt(LocalDateTime.now());

        byte[] pdf = pdfDocumentService.generate(document);

        assertThat(pdf).startsWith("%PDF".getBytes());
        assertThat(pdf.length).isGreaterThan(1000);
    }
}
