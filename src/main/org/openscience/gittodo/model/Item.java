/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

import java.util.Map;

public class Item {
	
	protected Item() throws Exception {
		this.priority = PRIORITY.UNSET;
		this.state = STATE.OPEN;
	}
	
	public Item(String creationDate, String text) throws Exception {
		this();

		if (creationDate == null) throw new IllegalArgumentException("The creation date must not be null.");
		if (text == null) throw new IllegalArgumentException("The text must not be null.");
		
		this.creationDate = creationDate;
		this.text = text;

		// trigger creation of a hashcode
		hashCode();
	}
	
	private Integer identifier;

	/** Allowed item states. */
	public static enum STATE {
		CLOSED,
		OPEN
	};
	
	private STATE state;
	
	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public static enum CONTEXT {
		HOME,
		WORK
	}
	
	private CONTEXT context;

	public CONTEXT getContext() {
		return context;
	}

	public void setContext(CONTEXT context) {
		this.context = context;
	}

	public static enum TYPE {
		TODO,
		TOREAD,
		TOPRINT,
		TOSCAN
	}
	
	private Map<TYPE, Boolean> types;
	
	public void set(TYPE type, boolean value) {
		types.put(type, value);
	}
	
	public boolean is(TYPE type) {
		return (types.containsKey(type) ? types.get(type) : false);
	}
	
	/** Allowed priorities sorted in decreasing priority. **/
	public static enum PRIORITY {
		NOW,
		HIGH,
		MEDIUM,
		UNSET,
		LOW,
		DELAYED
	}
	
	private PRIORITY priority;

	public PRIORITY getPriority() {
		return priority;
	}

	public void setPriority(PRIORITY priority) {
		this.priority = priority;
	}
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private String creationDate;

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	public int hashCode() {
		if (identifier == null) {
			identifier = Math.abs((creationDate + "##" + text).hashCode());
		}
		return identifier;
	}
	
	private String project;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	
	
}
