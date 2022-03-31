package org.springframework.samples.petclinic.owner.request;

import org.springframework.samples.petclinic.owner.Pet;

public class NewPetsRequest {

	private Integer ownerId;

	private Pet pet;

	public Integer getOwnerId() {
		return ownerId;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

}
