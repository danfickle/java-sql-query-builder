package com.github.danfickle.javasqlquerybuilder;

/**
 * An interface to generate INSERT queries.
 * @author DanFickle
 */
public interface QbInsert extends QbQuery
{
	/**
	 * Sets a database column placeholder.
	 * @param field
	 * @param placeholder
	 * @return This query builder.
	 */
	public QbInsert set(QbField field, String placeholder);
	
	/**
	 * Sets the table to insert into. Must be called to make a valid insert
	 * statement.
	 * @param table
	 * @return This query builder.
	 */
	public QbInsert inTable(String table); 
}