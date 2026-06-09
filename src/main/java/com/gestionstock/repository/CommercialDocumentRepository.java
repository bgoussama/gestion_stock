package com.gestionstock.repository;

import com.gestionstock.model.CommercialDocument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialDocumentRepository extends JpaRepository<CommercialDocument, Long> {
    List<CommercialDocument> findAllByOrderByCreatedAtDesc();
}
