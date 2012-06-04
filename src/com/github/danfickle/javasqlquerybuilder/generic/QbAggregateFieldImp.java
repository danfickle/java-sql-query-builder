package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;

/**
 * Immutable class to implement aggregate functions.
 * @author DanFickle
 */
class QbAggregateFieldImp implements QbField
{
	private final QbField m_child;
	private final String m_func;
	private final String m_alias;

	QbAggregateFieldImp(QbField field, String func, String alias)
	{
		m_child = field;
		m_func = func;
		m_alias = alias;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(m_func);
		builder.append('(');
		builder.append(m_child.toString());
		builder.append(')');
		
		if (m_alias != null)
		{
			builder.append(" AS ");
			builder.append(m_alias);
		}
		return builder.toString();
	}
}