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
		sb.append("\"subject\":");
		sb.append("\"sys:" + this.id + "\", \"properties\": [");
		sb.append("{\"property\": \"sys:identifier\", \"values\": [");
		sb.append("\"" + this.id + "\"");
		sb.append("]},");
		sb.append("{\"property\": \"sys:name\", \"values\": [");
		sb.append("\"" + this.name + "\"");
		sb.append("]},");
		sb.append("{\"property\": \"sys:description\", \"values\": [");
		sb.append("\"" + this.description + "\"");
		sb.append("]}");
		sb.append("]}");
		return sb.toString();
	}

}
