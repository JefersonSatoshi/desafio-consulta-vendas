package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.ReportMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN :minDate AND :maxDate "
			+ "AND (:name IS NULL OR UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))) "
	        + "GROUP BY obj.id, obj.date, obj.seller.name")
	Page<ReportMinDTO> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

	@Query("SELECT new com.devsuperior.dsmeta.dto.SummaryMinDTO(obj.seller.name, SUM(obj.amount) AS total) "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN :minDate AND :maxDate "
			+ "GROUP BY obj.seller.name")
	Page<SummaryMinDTO> searchSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
