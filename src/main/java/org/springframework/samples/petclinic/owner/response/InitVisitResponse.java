package org.springframework.samples.petclinic.owner.response;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.Visit;

public class InitVisitResponse {

	private Owner owner;

	private Pet pet;

	private Visit visit;

	public Owner getOwner() {
		return owner;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

}
