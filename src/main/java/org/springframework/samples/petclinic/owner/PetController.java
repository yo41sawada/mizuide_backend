/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import javax.validation.Valid;

import org.springframework.samples.petclinic.owner.request.NewPetsRequest;
import org.springframework.samples.petclinic.owner.response.InitOwnersResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */

@RestController
@RequestMapping("/owners/{ownerId}")
class PetController {

	private final OwnerRepository owners;

	public PetController(OwnerRepository owners) {
		this.owners = owners;
	}

	@GetMapping("/")
	@ResponseBody
	public InitOwnersResponse init(@PathVariable("ownerId") int ownerId) {
		InitOwnersResponse res = new InitOwnersResponse();
		res.setPetTypes(this.owners.findPetTypes());
		res.setOwner(this.owners.findById(ownerId));
		return res;
	}

	@PostMapping("/pets/new")
	public Integer processCreationForm(@RequestBody @Valid NewPetsRequest req) {
		Owner owner = this.owners.findById(req.getOwnerId());
		Pet pet = req.getPet();
		if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
			throw new Error("duplicate pet name");
		}
		owner.addPet(pet);
		this.owners.save(owner);
		return owner.getId();
	}

	@PostMapping("/pets/{petId}/edit")
	public Integer processUpdateForm(@RequestBody @Valid NewPetsRequest req) {
		System.out.println(req.getPet().getName());
		Owner owner = this.owners.findById(req.getOwnerId());
		owner.addPet(req.getPet());
		this.owners.save(owner);
		return owner.getId();

	}

}
