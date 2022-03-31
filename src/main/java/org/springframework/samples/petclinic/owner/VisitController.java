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

import org.springframework.samples.petclinic.owner.response.InitVisitResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
class VisitController {

	private final OwnerRepository owners;

	public VisitController(OwnerRepository owners) {
		this.owners = owners;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
	// called
	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	@ResponseBody
	public InitVisitResponse initNewVisitForm(@PathVariable("ownerId") int ownerId, @PathVariable("petId") int petId) {
		Owner owner = this.owners.findById(ownerId);
		Pet pet = owner.getPet(petId);
		Visit visit = new Visit();
		pet.addVisit(visit);
		InitVisitResponse res = new InitVisitResponse();
		res.setOwner(owner);
		res.setPet(pet);
		res.setVisit(visit);
		return res;
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
	// called
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public Integer processNewVisitForm(@PathVariable("ownerId") int ownerId, @PathVariable("petId") int petId,
			@RequestBody @Valid Visit visit) {
		Owner owner = this.owners.findById(ownerId);
		owner.addVisit(petId, visit);
		this.owners.save(owner);
		return ownerId;

	}

}
