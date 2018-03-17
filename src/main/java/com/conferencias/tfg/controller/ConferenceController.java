package com.conferencias.tfg.controller;


import java.util.List;

import com.conferencias.tfg.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.conferencias.tfg.domain.Conference;
import com.conferencias.tfg.utilities.Views.Detailed;
import com.conferencias.tfg.utilities.Views.Shorted;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("conference")
public class ConferenceController {

    private ConferenceRepository conferenceRepository;

	@Autowired
    public ConferenceController(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

	/** Recibe los valores de todos los atributos de todas las conferencias */
	@GetMapping("/detailed")
	@JsonView(Detailed.class)
	public ResponseEntity<?> getAllDetailed() {
		List<Conference> conferences = conferenceRepository.findAll();
		return new ResponseEntity<Object>(conferences, HttpStatus.OK);
	}

	/** Recibe los valores de ciertos atributos de todas las conferencias */
	@GetMapping("/short")
	@JsonView(Shorted.class)
	public ResponseEntity<?> getAllShort() {
		List<Conference> conferences = conferenceRepository.findAll();
		return new ResponseEntity<Object>(conferences, HttpStatus.OK);
	}

	/** Recibe los valores de todos los atributos de una conferencia en concreto */
	@GetMapping(value = "/detailed/{id}")
	@JsonView(Detailed.class)
	public ResponseEntity<?> getDetailed(@PathVariable("id") long id) {
		Conference conference = conferenceRepository.findOne(id);

		if (conference == null) {
			return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(conference, HttpStatus.OK);
	}

	/** Recibe los valores de todos los atributos de una conferencia en concreto */
	@GetMapping(value = "/short/{id}")
	@JsonView(Shorted.class)
	public ResponseEntity<?> getShort(@PathVariable("id") long id) {
		Conference conference = conferenceRepository.findOne(id);

		if (conference == null) {
			return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(conference, HttpStatus.OK);
	}

	/** Crea una conferencia según los valores que se envien en el método POST */
    @PostMapping("/create")
    @JsonView(Detailed.class)
    public ResponseEntity<?> createConference(@RequestBody Conference conference, UriComponentsBuilder ucBuilder) {

		if (this.conferenceExist(conference)) {
			return new ResponseEntity<Error>(HttpStatus.CONFLICT);
		}

        conferenceRepository.save(conference);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/conference/create/{id}").buildAndExpand(conference.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /** Modifica una conferencia con los campos que se indiquen */
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> editConference(@PathVariable("id") long id, @RequestBody Conference conference) {
		Conference currentConference = conferenceRepository.findOne(id);

		if (currentConference == null) {
			return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
		}

		currentConference.setDuration(conference.getDuration());
		currentConference.setFinish(conference.getFinish());
		currentConference.setName(conference.getName());
		currentConference.setPlace(conference.getPlace());
		currentConference.setStart(conference.getStart());
		currentConference.setPrice(conference.getPrice());
		currentConference.setTheme(conference.getTheme());

		conferenceRepository.save(conference);
		return new ResponseEntity<>(conference, HttpStatus.OK);
	}

	/** Borra a todos las conferencias */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<Conference> deleteAll() {
		conferenceRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/** Borra a una conferencia en concreto */
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		Conference conference = conferenceRepository.findOne(id);
		if (conference == null) {
			return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
		}
		conferenceRepository.delete(id);
		return new ResponseEntity<Conference>(HttpStatus.NO_CONTENT);
	}
	
	// ---------------------------------------------------------------------------------------------------------------//
	// ----------------------------------------------- Métodos auxiliares --------------------------------------------//
	// ---------------------------------------------------------------------------------------------------------------//
	
	/** Dada una conferencia, comprueba si existe una igual */
	private Boolean conferenceExist(Conference conference){
		Boolean res = false;

		for (Conference c : conferenceRepository.findAll()){
			if(conference.equals(c)){
				res = true;
				break;
			}
		}
		return res;
	}
}
