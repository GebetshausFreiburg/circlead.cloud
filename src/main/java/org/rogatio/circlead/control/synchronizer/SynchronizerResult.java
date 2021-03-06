/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer;

/**
 * The Class SynchronizerResult. Allows qualified information if the
 * synchronizer works. Not only holds request-codes. Contains also handeld
 * content, response-message, ...
 * 
 * @author Matthias Wegner
 */
public class SynchronizerResult {

	/**  The code of the response. */
	private int code;

	/**  The message of the response (if available). */
	private String message;

	/** The used source. Could be a filepath, rest-url, ... */
	private String source;

	/**  The content of the result of the synchronizer. */
	private String content;

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		if (this.getCode() == 200) {
			return "Result [code = " + this.getCode() + ", msg=" + this.getMessage() + "]";
		} else {
			return "Result [code = " + this.getCode() + ", msg=" + this.getMessage() + "]: " + this.getContent();
		}
	}

}
