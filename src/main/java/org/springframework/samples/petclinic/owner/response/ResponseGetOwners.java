package org.springframework.samples.petclinic.owner.response;

import java.util.List;

import org.springframework.samples.petclinic.owner.Owner;

public class ResponseGetOwners {

	private int currentPage;

	private int totalPages;

	private long totalItems;

	private List<Owner> listOwners;

	public List<Owner> getListOwners() {
		return listOwners;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setListOwners(List<Owner> ownersList) {
		this.listOwners = ownersList;
	}

}
