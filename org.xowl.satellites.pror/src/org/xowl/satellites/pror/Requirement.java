package org.xowl.satellites.pror;

public class Requirement {
	
	private String id, name, description;
	
	public Requirement() {
		this.id = null;
		this.name = null;
		this.description = null;
	}

	public Requirement(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":\"" + this.id + "\", ");
		sb.append("\"name\":\"" + this.name + "\", ");
		sb.append("\"description\":\"" + this.description + "\"");
		sb.append("}");
		return sb.toString();
	}

}
