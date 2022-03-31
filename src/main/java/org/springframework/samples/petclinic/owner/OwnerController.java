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

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.owner.response.ResponseGetOwners;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@RestController
class OwnerController {

	private final OwnerRepository owners;

	public OwnerController(OwnerRepository clinicService) {
		this.owners = clinicService;
	}

	@PostMapping("/owners/new")
	@ResponseBody
	public Owner processCreationForm(@Valid @RequestBody Owner owner) {
		this.owners.save(owner);
		return owner;
	}

	@GetMapping("/owners/find")
	@ResponseBody
	public Owner initFindForm(Map<String, Object> model) {
		return new Owner();
	}

	@GetMapping("/owners")
	@ResponseBody
	public ResponseGetOwners processFindForm(@RequestParam(defaultValue = "1") int page,
			@RequestParam String lastName) {

		// allow parameterless GET request for /owners to return all records
		if (lastName == null) {
			lastName = ""; // empty string signifies broadest possible search
		}
		// TODO:画面のルーティングはReactで行う
		Page<Owner> ownersResults = findPaginatedForOwnersLastName(page, lastName);
		return addPaginationModel(page, lastName, ownersResults);
	}

	private ResponseGetOwners addPaginationModel(int page, String lastName, Page<Owner> paginated) {
		ResponseGetOwners res = new ResponseGetOwners();
		List<Owner> listOwners = paginated.getContent();
		res.setCurrentPage(page);
		res.setTotalPages(paginated.getTotalPages());
		res.setTotalItems(paginated.getTotalElements());
		res.setListOwners(listOwners);
		return res;
	}

	private Page<Owner> findPaginatedForOwnersLastName(int page, String lastname) {

		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return owners.findByLastName(lastname, pageable);

	}

	@GetMapping("/owners/{ownerId}/edit")
	public Owner initUpdateOwnerForm(@PathVariable("ownerId") int ownerId) {
		Owner owner = this.owners.findById(ownerId);
		return owner;
	}

	@PostMapping("/owners/{ownerId}/edit")
	public Integer processUpdateOwnerForm(@Valid @RequestBody Owner owner, @PathVariable("ownerId") int ownerId) {
		owner.setId(ownerId);
		this.owners.save(owner);
		return ownerId;
	}

	@GetMapping("/owners/{ownerId}")
	@ResponseBody
	public Owner showOwner(@PathVariable("ownerId") int ownerId) {
		Owner owner = this.owners.findById(ownerId);
		return owner;
	}

}
