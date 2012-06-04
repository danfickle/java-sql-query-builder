package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;

/**
 * Immutable class to implement qualified fields.
 * @author DanFickle
 */
class QbQualifiedFieldImp implements QbField
{
	private final String m_table;
	private final String m_field;
	
	QbQualifiedFieldImp(String table, String field) 
	{
		m_table = table;
		m_field = field;
	}
	
	@Override
	public String toString()
	{
		return QbCommonImp.protectTableName(m_table) + ".`" + m_field + '`';
	}
}