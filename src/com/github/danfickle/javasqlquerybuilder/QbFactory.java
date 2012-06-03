package com.github.danfickle.javasqlquerybuilder;

/**
 * Factory interface to create query-builder objects such as
 * fields and queries.
 * Concretely implemented by a class for each database. 
 * @author DanFickle
 */
public interface QbFactory
{
	/**
	 * Creates an all fields selector.
	 * @return QbField
	 */
	public QbField newAllField();
	
	/**
	 * Creates an all fields selector for the specified table.
	 * @param table - A table name without backticks.
	 * @return QbField
	 */
	public QbField newAllField(String table);
	
	/**
	 * Creates a standard database field. Will enclose the field
	 * in backticks in the final query.
	 * @param name - A table column without backticks.
	 * @return QbField
	 */
	public QbField newStdField(String name);

	/**
	 * Creates a qualified database field. Will enclose the field
	 * and table name in backticks in the final query.
	 * @param table - A table name without backticks.
	 * @param name - A table column without backticks.
	 * @return QbField
	 */
	public QbField newQualifiedField(String table, String name);
	
	/**
	 * Creates a SUM aggregate function on the specified field.
	 * @param field - Returned by newStdField.
	 * @param alias - A string to use with AS. May be null
	 * in which case no AS is used.
	 * @return QbField
	 */
	public QbField newSum(QbField field, String alias);

	/**
	 * Creates a count aggregate function on the specified field.
	 * @param field - Returned by newStdField.
	 * @param alias - A string to use with AS. May be null
	 * in which case no AS is used.
	 * @return Field
	 */
	public QbField newCount(QbField field, String alias);
	
	/**
	 * Creates an average aggregate function on the specified field.
	 * @param field - Returned by newStdField.
	 * @param alias - A string to use with AS. May be null
	 * in which case no AS is used.
	 * @return QbField
	 */
	public QbField newAvg(QbField field, String alias);
	
	/**
	 * Creates a minimum function on the specified field.
	 * @param field - Returned by newStdField.
	 * @param alias - A string to use with AS. May be null
	 * in which case no AS is used.
	 * @return QbField
	 */
	public QbField newMin(QbField field, String alias);
	
	/**
	 * Creates a count aggregate function on the specified field.
	 * @param field - Returned by newStdField.
	 * @param alias - A string to use with AS. May be null
	 * in which case no AS is used.
	 * @return QbField
	 */
	public QbField newMax(QbField field, String alias);
	
	/**
	 * Creates a custom field.
	 * @param custom - A custom string that will be used as is.
	 * @return QbField
	 */
	public QbField newCustomField(String custom);
	
	/**
	 * Creates a SELECT query. 
	 */
	public QbSelect newSelectQuery();

	/**
	 * Creates an UPDATE query.
	 */
	public QbUpdate newUpdateQuery();

	/**
	 * Creates a DELETE query.
	 */
	public QbDelete newDeleteQuery();

	/**
	 * Creates an INSERT query.
	 */
	public QbInsert newInsertQuery();
	
	/**
	 * Creates a query object with the given sql.
	 * Only use this if the query builder can not build an appropriate
	 * query.
	 * @param sql - A custom sql string.
	 * @return QbQuery
	 */
	public QbQuery newQuery(String sql);
}