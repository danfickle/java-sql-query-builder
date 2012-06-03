package com.github.danfickle.javasqlquerybuilder;

/**
 * Interface to generate an update query.
 * @author DanFickle.
 */
public interface QbUpdate extends QbQuery
{
	/**
	 * Sets a field to the specified placeholder.
	 * @param field
	 * @param placeholder
	 * @return This query builder.
	 */
	public QbUpdate set(QbField field, String placeholder);
	
	/**
	 * Adds a where clause. Very important.
	 * @return A QbWhere object that is bound to this query.
	 */
	public QbWhere where();
	
	/**
	 * Signals that you want to update all records. If neither
	 * where or all is called the query builder will throw an exception.
	 * @return This query builder.
	 */
	public QbUpdate all();
	
	/**
	 * Which table to update. Table name should not contain
	 * backticks.
	 * @param table
	 * @return This query builder.
	 */
	public QbUpdate inTable(String table);
}