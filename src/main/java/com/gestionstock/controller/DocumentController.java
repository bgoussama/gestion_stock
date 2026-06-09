package com.gestionstock.controller;

import com.gestionstock.model.CommercialDocument;
import com.gestionstock.model.DocumentType;
import com.gestionstock.repository.CommercialDocumentRepository;
import com.gestionstock.service.PdfDocumentService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documents")
public class DocumentController {
    private final CommercialDocumentRepository documentRepository;
    private final PdfDocumentService pdfDocumentService;

    public DocumentController(CommercialDocumentRepository documentRepository, PdfDocumentService pdfDocumentService) {
        this.documentRepository = documentRepository;
        this.pdfDocumentService = pdfDocumentService;
    }

    @GetMapping
    String list(Model model) {
        model.addAttribute("documents", documentRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("document", new CommercialDocument());
        model.addAttribute("types", DocumentType.values());
        return "documents/list";
    }

    @PostMapping
    String save(CommercialDocument document) {
        documentRepository.save(document);
        return "redirect:/documents";
    }

    @GetMapping("/{id}/pdf")
    ResponseEntity<byte[]> pdf(@PathVariable Long id) {
        var document = documentRepository.findById(id).orElseThrow();
        var content = pdfDocumentService.generate(document);
        var filename = "document-" + document.getId() + ".pdf";
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(filename).build().toString())
                .body(content);
    }
}
