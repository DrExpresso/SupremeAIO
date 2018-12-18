package net.gpedro.integrations.slack;

/**
 * @author Galimov Ruslan
 */
public enum SlackActionStyle {
	DEFAULT("default"), PRIMARY("primary"), DANGER("danger");

	private String code;

	SlackActionStyle(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
