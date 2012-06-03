package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbDelete;
import com.github.danfickle.javasqlquerybuilder.QbWhere;

class QbDeleteImp implements QbDelete
{
	private String m_table;
	private boolean m_all;
	private QbWhere m_where;
	
	@Override
	public String getQueryString() 
	{
		if (m_table == null)
			throw new IllegalStateException("Must specify a table");

		if (m_all == false && m_where == null)
			throw new IllegalStateException("Must call all() to delete all records");
		
		StringBuilder builder = new StringBuilder("DELETE FROM ");
		builder.append(QbCommonImp.protectTableName(m_table));

		if (m_where != null)
			builder.append(m_where.toString());

		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		if (m_where != null)
			return m_where.getPlaceholderIndex(placeholderName);
		else
			throw new IllegalArgumentException("Placeholder doesn't exist");
	}

	@Override
	public QbWhere where()
	{
		m_where = new QbWhereImp(false, 1);
		return m_where;
	}

	@Override
	public QbDelete all()
	{
		m_all = true;
		return this;
	}

	@Override
	public QbDelete from(String table) 
	{
		m_table = table;
		return this;
	}
}
