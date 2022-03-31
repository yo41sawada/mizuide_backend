package org.springframework.samples.petclinic.owner;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PetTypeController {

	private final OwnerRepository owners;

	public PetTypeController(OwnerRepository owners) {
		this.owners = owners;
	}

	@GetMapping("/petType")
	@ResponseBody
	public List<PetType> getPetType() {
		return this.owners.findPetTypes();
	}

}
