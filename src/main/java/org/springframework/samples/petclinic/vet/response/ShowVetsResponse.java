package org.springframework.samples.petclinic.vet.response;

import java.util.List;

import org.springframework.samples.petclinic.vet.Vet;

public class ShowVetsResponse {

	private Integer currentPage;

	private Integer totalPages;

	private Long totalItems;

	private List<Vet> listVets;

	public List<Vet> getListVets() {
		return listVets;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public void setListVets(List<Vet> listVets) {
		this.listVets = listVets;
	}

}
