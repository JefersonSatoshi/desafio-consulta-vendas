package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportMinDTO> report(String minDateStr, String maxDateStr, String name, Pageable pageable) {
		
		LocalDate[] dates = stringToDate(minDateStr, maxDateStr);
		LocalDate minDate = dates[0];
	    LocalDate maxDate = dates[1];
		
		return repository.searchReport(minDate, maxDate, name, pageable);
	}

	public Page<SummaryMinDTO> summary(String minDateStr, String maxDateStr, Pageable pageable) {
		
		LocalDate[] dates = stringToDate(minDateStr, maxDateStr);
		LocalDate minDate = dates[0];
	    LocalDate maxDate = dates[1];
		
		return repository.searchSummary(minDate, maxDate, pageable);
	}
	
	private LocalDate[] stringToDate(String minDateStr, String maxDateStr) {
		LocalDate minDate = null;
		LocalDate maxDate = null;
		
		if (minDateStr != null) {
	        minDate = LocalDate.parse(minDateStr);
	    }
		
	    if (maxDateStr != null) {
	        maxDate = LocalDate.parse(maxDateStr);
	    }

	    if (minDate == null && maxDate != null) {
	        minDate = maxDate.minusYears(1L);
	    } else if (minDate != null && maxDate == null) {
	        maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	    } else if (minDate == null && maxDate == null) {
	        maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	        minDate = maxDate.minusYears(1L);
	    }
		
		return new LocalDate[] {minDate, maxDate};
	}
}
