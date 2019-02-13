package org.xowl.satellites.pror;

public class Requirement {
	
	private String id, description;
	
	public Requirement() {
		this.id = null;
		this.description = null;
	}

	public Requirement(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		sb.append("\"" + this.id + "\", \"properties\": [");
		sb.append("{\"property\": \"identifier\", \"values\": [");
		sb.append("\"" + this.id + "\"");
		sb.append("]},");
		sb.append("{\"property\": \"description\", \"values\": [");
		sb.append("\"" + this.description.replace('\n', ' ') + "\"");
		sb.append("]}");
		sb.append("]}");
		return sb.toString();
	}

}
