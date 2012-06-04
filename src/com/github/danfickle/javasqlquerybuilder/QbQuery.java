package com.github.danfickle.javasqlquerybuilder;

/**
 * Parent interface for a select, update, insert or delete query.
 * QbQuery child interfaces should be created using a DB
 * specific Factory instance.
 * @author DanFickle
 */
public interface QbQuery
{
	/**
	 * Gets the created query string that needs to be passed to 
	 * Connection.prepareStatement(String).
	 * @return The query string.
	 */
	public String getQueryString();
	
	/**
	 * Gets the index of a placeholder.
	 * @param placeholderName - The name of the placeholder value.
	 * @return An integer (starting at 1) that can be passed to
	 * PreparedStatement::set* functions.
	 */
	public int getPlaceholderIndex(String placeholderName);
}