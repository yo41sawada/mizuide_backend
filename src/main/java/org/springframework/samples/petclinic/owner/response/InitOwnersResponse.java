package org.springframework.samples.petclinic.owner.response;

import java.util.Collection;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.PetType;

public class InitOwnersResponse {

	private Collection<PetType> PetTypes;

	private Owner owner;

	public void setPetTypes(Collection<PetType> petTypes) {
		this.PetTypes = petTypes;
	}

	public Collection<PetType> getPetTypes() {
		return PetTypes;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Owner getOwner() {
		return owner;
	}

}
