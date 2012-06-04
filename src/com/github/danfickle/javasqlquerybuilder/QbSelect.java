package com.github.danfickle.javasqlquerybuilder;

/**
 * Interface to generate a SELECT query.
 * @author DanFickle
 */
public interface QbSelect extends QbQuery
{
	/**
	 * Sets the list of fields to select from. Call only once per
	 * QbSelect object.
	 * @param fields
	 * @return This query object.
	 */
	public QbSelect select(QbField...fields);

	/**
	 * Which table to get records from? Call only once per
	 * QbSelect object. 
	 * @param table
	 * @return This query object.
	 */
	public QbSelect from(String table);
	
	/**
	 * Creates the where clause builder. This must be called before any
	 * call to having(). Call only once per QbSelect object.
	 * @return A mutable QbWhere that is bound to this query.
	 */
	public QbWhere where();
	
	/**
	 * Adds the DISTINCT keyword.
	 * @return This query object.
	 */
	public QbSelect distinct();
	
	/**
	 * An enumaration of the different join types.
	 */
	public enum QbJoinType
	{
		LEFT,
		RIGHT,
		OUTER,
		INNER,
		LEFT_OUTER,
		RIGHT_OUTER,
		DEFAULT
	}

	/**
	 * Join the table to this query. May be called multiple times.
	 * @param table - A table name without backticks.
	 * @param field1 - A field to join on.
	 * @param field2 - The second field to join on.
	 * @param joinType - Join type.
	 * @return This query object.
	 */
	public QbSelect join(String table, QbField field1, QbField field2, QbJoinType joinType);
	
	/**
	 * Similar to {@link #join(String, QbField, QbField, QbJoinType) join}
	 * but uses the db's default join type. May be called multiple times.
	 * @param table - A table name without backticks.
	 * @param field1 - A field to join on.
	 * @param field2 - The second field to join on.
	 * @return This query object.
	 */
	public QbSelect join(String table, QbField field1, QbField field2);

	/**
	 * Takes a list of fields to group by. Call only once per
	 * QbSelect object.
	 * @param fields - The fields to group by.
	 * @return This query object.
	 */
	public QbSelect groupBy(QbField... fields);
	
	/**
	 * The having clause for use with group_by. This MUST be called after any
	 * call to where(). Call only once per QbSelect object.
	 * @return A QbWhere object bound to the having clause of this query.
	 */
	public QbWhere having();
	
	/**
	 * Order ascending or descending. Use this rather than a boolean
	 * to make code more readable.
	 */
	public enum QbOrderBy
	{
		ASC,
		DESC
	}
	
	/**
	 * Used to make the ORDER BY clause. Call only once
	 * per QbSelect object.
	 * @param order - ASC or DESC.
	 * @param fields - A list of fields to order by.
	 * @return This query object.
	 */
	public QbSelect orderBy(QbOrderBy order, QbField... fields);
	
	/**
	 * Allows the provision of an offset and limit.
	 * @param offset - The record offset, starting at zero.
	 * @param count - The number of records to return.
	 * @return This query object.
	 */
	public QbSelect limit(int offset, int count);
}
