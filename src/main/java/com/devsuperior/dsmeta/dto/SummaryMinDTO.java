package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SummaryMinDTO {
	
	private String sellerName;
	private Double total;
	
	public SummaryMinDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}
	
	public SummaryMinDTO(Sale entity) {
		sellerName = entity.getSeller().getName();
		total = entity.getAmount();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
}
