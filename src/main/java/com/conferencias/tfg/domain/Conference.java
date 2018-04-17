package com.conferencias.tfg.domain;

import com.conferencias.tfg.utilities.Views.Detailed;
import com.conferencias.tfg.utilities.Views.Shorted;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Document
public class Conference {

	@Id
	private String id;
	@NotBlank
	@JsonView(Shorted.class)
	private String name;
	@NotBlank
	@JsonView(Detailed.class)
	private String theme;
	@DecimalMin("0.0")
	@JsonView(Detailed.class)
	private Double price;
	@NotNull
	@JsonView(Shorted.class)
	private Double popularity;
    @NotBlank
    @Pattern(regexp = "^\\d{2}\\/\\d{2}\\/\\d{4}\\s*(?:\\d{2}:\\d{2}(?::\\d{2})?)?$")
    @JsonView(Shorted.class)
	private String start;
    @NotBlank
    @Pattern(regexp = "^\\d{2}\\/\\d{2}\\/\\d{4}\\s*(?:\\d{2}:\\d{2}(?::\\d{2})?)?$")
    @JsonView(Detailed.class)
	private String end;
	@NotBlank
	@Length(max = 50)
	@JsonView(Detailed.class)
	private String speakersNames;
	@NotBlank
	@JsonView(Detailed.class)
	private String description;

	public Conference() {
	}

    public Conference(String id, String name, String theme, Double price, String start, String end, String speakersNames, String description) {
        this.id = id;
	    this.name = name;
        this.theme = theme;
        this.price = price;
        this.popularity = 0.0;
        this.start = start;
        this.end = end;
        this.speakersNames = speakersNames;
        this.description = description;
        this.organizators = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Conference(String name, String theme, Double price, String start, String end, String speakersNames, String description) {
        this.name = name;
        this.theme = theme;
        this.price = price;
        this.popularity = 0.0;
        this.start = start;
        this.end = end;
        this.speakersNames = speakersNames;
        this.description = description;
        this.organizators = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSpeakersNames() {
        return speakersNames;
    }

    public void setSpeakersNames(String speakersNames) {
        this.speakersNames = speakersNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // -----------------------------------------------------------------------------------------------------------------

	@JsonView(Detailed.class)
	private List<String> events;
	@NotNull
	@JsonView(Detailed.class)
	private List<String> organizators;
    @NotNull
    @JsonView(Detailed.class)
    private List<String> comments;

	public List<String> getEvents() {
		return events;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public List<String> getOrganizators() {
		return organizators;
	}

	public void setOrganizators(List<String> organizators) {
		this.organizators = organizators;
	}

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
