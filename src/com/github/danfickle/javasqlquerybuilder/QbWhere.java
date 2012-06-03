package com.github.danfickle.javasqlquerybuilder;

/**
 * Generates a WHERE clause. For use in SELECT and UPDATE queries.
 * @author DanFickle
 */
public interface QbWhere
{
	/**
	 * The where operator.
	 */
	public enum QbWhereOperator
	{
		EQUALS,
		NOT_EQUALS,
		LESS_THAN,
		GREATER_THAN,
		LESS_THAN_EQUALS,
		GREATER_THAN_EQUALS
	}
	
	/**
	 * Constructs a where clause. If there are already where clauses
	 * in the query, its added as a AND WHERE.
	 * @param field
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere where(QbField field, String placeholder);

	/**
	 * Similar to {@link #where(QbField, String) where} but allows you 
	 * to specify a where operator.
	 * @param field
	 * @param op
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere where(QbField field, QbWhereOperator op, String placeholder); 

	/**
	 * Similar to {@link #where(QbField, String) where} but is joined by an OR WHERE.
	 * @param field
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere orWhere(QbField field, String placeholder); 

	/**
	 * Similar to {@link #orWhere(QbField, String) orWhere} but allows you to 
	 * specify a where operator.
	 * @param field
	 * @param op
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere orWhere(QbField field, QbWhereOperator op, String placeholder); 

	/**
	 * Allows you to specify a custom where clause. Not recommended for
	 * use unless an appropriate clause can't be constructed with
	 * other methods.
	 * @param custom
	 * @return This where builder.
	 */
	public QbWhere where(String custom);

	/**
	 * Generates an IN clause.
	 * @param field
	 * @param placeholder - Using this placeholder will return the index of the first placeholder.
	 * @param count - The number of placeholders to place in the query.
	 * @return This where builder.
	 */
	public QbWhere whereIn(QbField field, String placeholder, int count);

	/**
	 * Similar to {@link #whereIn(QbField, String, int) whereIn} but joined
	 * with an OR.
	 * @param field
	 * @param placeholder
	 * @param count
	 * @return This where builder.
	 */
	public QbWhere orWhereIn(QbField field, String placeholder, int count);

	/**
	 * Similar to {@link #whereIn(QbField, String, int) whereIn} but generates
	 * a NOT IN clause.
	 * @param field
	 * @param placeholder
	 * @param count
	 * @return This where builder.
	 */
	public QbWhere whereNotIn(QbField field, String placeholder, int count);

	/**
	 * Similar to {@link #whereNotIn(QbField, String, int) whereIn} but joined
	 * with an OR.
	 * @param field
	 * @param placeholder
	 * @param count
	 * @return This where builder.
	 */
	public QbWhere orWhereNotIn(QbField field, String placeholder, int count);

	/**
	 * Generates a like clause.
	 * @param field
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere like(QbField field, String placeholder);

	/**
	 * Generates a like clause but joined to the rest of the where
	 * clause with an OR.
	 * @param field
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere orLike(QbField field, String placeholder);

	/**
	 * Generates a NOT LIKE clause.
	 * @param field
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere notLike(QbField field, String placeholder);

	/**
	 * Generates a NOT LIKE clause joined to the rest of the clause
	 * with an OR.
	 * @param field
	 * @param placeholder
	 * @return This where builder.
	 */
	public QbWhere orNotLike(QbField field, String placeholder);
	
	/**
	 * Adds an opening bracket.
	 * @return This where builder.
	 */
	public QbWhere startBracket(); 

	/**
	 * Adds an ending bracket.
	 * @return This where builder.
	 */
	public QbWhere endBracket();
	
	/**
	 * Gets the placeholder index. Usually you don't call this
	 * directly and get the placeholder index from the QbQuery which 
	 * will check its own placeholders as well as those from any where clause.
	 * @param placeholderName
	 * @return The placeholder index.
	 */
	public int getPlaceholderIndex(String placeholderName);
}
