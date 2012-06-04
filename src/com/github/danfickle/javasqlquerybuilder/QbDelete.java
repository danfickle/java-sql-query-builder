package com.github.danfickle.javasqlquerybuilder;

/**
 * An interface to build DELETE queries. 
 * @author DanFickle.
 */
public interface QbDelete extends QbQuery
{
	/**
	 * Sets the WHERE clause.
	 * @return A QbWhere object which is bound to this query.
	 */
	public QbWhere where();
	
	/**
	 * Marks all records for deletion. Either this method
	 * or where must be called. 
	 * @return This query builder.
	 */
	public QbDelete all();
	
	/**
	 * The table you want to delete from. Must be called to make
	 * a valid delete statement.
	 * @param table - A table name without backticks.
	 * @return This query builder.
	 */
	public QbDelete from(String table);
}
