/* Copyright 2008-2011  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.openscience.gittodo.io.ItemReader;

public class Item {
	
	private boolean hasChanged;

    @SuppressWarnings("deprecation")
    public Item() throws Exception {
        // set a creation date
        Date now = new Date(System.currentTimeMillis());
        String dateStr = "" + (now.getYear()+1900) + "-";
        if ((now.getMonth()+1) < 10) dateStr += "0";
        dateStr += (now.getMonth() + 1) + "-";
        if (now.getDate() < 10) dateStr += "0";
        dateStr += now.getDate();
        this.creationDate = dateStr;

        this.priority = PRIORITY.UNSET;
        this.state = STATE.OPEN;
        hasChanged = false;
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
	
	/**
	 * Method primarily used by the {@link ItemReader} to set values.
	 */
	public Item(String creationDate, String text, BOX box, STATE state, PRIORITY priority,
			CONTEXT context, Integer hashcode, String project, URL url,
			String deadline) {
		this.hasChanged = false;
		this.creationDate = creationDate;
		this.text = text;
		this.box = box;
		this.state = state;
		this.priority = priority;
		this.context = context;
		this.identifier = hashcode;
		this.project = project;
		this.url = url;
		this.deadline = deadline;
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
		if (this.state != state) {
			this.state = state;
			hasChanged = true;
		}
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
		failWhenItemClosed();
		if (this.context != context) {
			this.context = context;
			hasChanged = true;
		}
	}

	public static enum TYPE {
		TODO,
		TOREAD,
		TOPRINT,
		TOSCAN
	}
	
	private Map<TYPE, Boolean> types;
	
	public void set(TYPE type, boolean value) {
		failWhenItemClosed();
		types.put(type, value);
	}
	
	private void failWhenItemClosed() {
		if (getState() == STATE.CLOSED) {
			throw new RuntimeException(
				"You cannot modify a closed item."
			);
		}
	}

	public boolean is(TYPE type) {
		return (types.containsKey(type) ? types.get(type) : false);
	}
	
	/** Allowed priorities sorted in decreasing priority. **/
	public static enum PRIORITY {
		TODAY,
		URGENT,
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
		failWhenItemClosed();
		if (this.priority != priority && this.deadline == null) {
			this.priority = priority;
			hasChanged = true;
			if (PRIORITY.DELAYED == priority ||
				PRIORITY.LOW == priority) {
				setBox(BOX.WAITING);
			} else if (PRIORITY.UNSET == priority){
				setBox(BOX.INBOX);
			} else {
				setBox(BOX.ACTIVE);
			}
		}
	}
	
	/** Allowed priorities sorted in decreasing priority. **/
	public static enum BOX {
		INBOX,
		ACTIVE,
		WAITING,
		MAYBE
	}
	
	private BOX box;

	public BOX getBox() {
		return box;
	}

	public void setBox(BOX box) {
		failWhenItemClosed();
		if (this.box != box) {
			this.box = box;
			hasChanged = true;
		}
	}

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		failWhenItemClosed();
		if (this.text == null || !this.text.equals(text)) {
			this.text = text;
			hasChanged = true;
		}
	}

	private String deadline;

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		failWhenItemClosed();
		if (this.deadline == null || !this.deadline.equals(deadline)) {
			this.deadline = deadline;
			setPriority(PRIORITY.URGENT);
			hasChanged = true;
			setBox(BOX.ACTIVE);
		}
	}

	private String creationDate;

	public String getCreationDate() {
		return creationDate;
	}

	public int hashCode() {
		if (identifier == null) {
			identifier = Math.abs((creationDate + "##" + text).hashCode());
			this.hasChanged = true;
		}
		return identifier;
	}
	
	public Integer getIdentifier() {
	    return identifier;
	}
	
	private String project;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		failWhenItemClosed();
		if (this.project == null || !this.project.equals(project)) {
			this.project = project;
			hasChanged = true;
		}
	}
	
	public boolean isChanged() {
		return hasChanged;
	}
	
	private URL url;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		failWhenItemClosed();
		if (this.url == null || url == null || !this.url.toString().equals(url.toString())) {
			this.url = url;
			hasChanged = true;
		}
	}

}
