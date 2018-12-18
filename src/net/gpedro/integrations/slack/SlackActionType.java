package net.gpedro.integrations.slack;

/**
 * @author Galimov Ruslan
 */
public enum SlackActionType {
	BUTTON("button"), SELECT("select");

	private String code;

	SlackActionType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
