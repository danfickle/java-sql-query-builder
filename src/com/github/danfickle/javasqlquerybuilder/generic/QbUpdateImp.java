package com.github.danfickle.javasqlquerybuilder.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.danfickle.javasqlquerybuilder.QbField;
import com.github.danfickle.javasqlquerybuilder.QbUpdate;
import com.github.danfickle.javasqlquerybuilder.QbWhere;

class QbUpdateImp implements QbUpdate
{
	private String m_table;
	private Map<String, Integer> m_placeholders;
	private List<QbField> m_fields;
	private boolean m_all;
	private QbWhere m_where;
	
	QbUpdateImp() { }
	
	@Override
	public String getQueryString()
	{
		if (m_fields == null || m_table == null || m_placeholders == null)
			throw new IllegalStateException("Table name or fields missing");

		if (m_where == null && m_all == false)
			throw new IllegalStateException("Must call all() to update all records");
		
		StringBuilder builder = new StringBuilder("UPDATE ");
		builder.append(QbCommonImp.protectTableName(m_table));
		builder.append(" SET ");
		
		int fieldCnt = 0;
		for (QbField field : m_fields)
		{
			builder.append(field.toString());
			builder.append(" = ?");

			if (fieldCnt != m_fields.size() - 1)
				builder.append(", ");
			fieldCnt++;
		}
		
		if (m_where != null)
			builder.append(m_where.toString());

		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		if (m_placeholders == null)
			throw new IllegalArgumentException("Placeholder doesn't exist");
		
		Integer idx = m_placeholders.get(placeholderName);
		
		if (idx == null)
		{
			idx = m_where.getPlaceholderIndex(placeholderName);
			if (idx == 0)
				throw new IllegalArgumentException("Placeholder doesn't exist");
		}
		return idx;
	}

	@Override
	public QbUpdate set(QbField field, String placeholder)
	{
		if (m_fields == null)
			m_fields = new ArrayList<QbField>();
		if (m_placeholders == null)
			m_placeholders = new HashMap<String, Integer>();
		
		if (m_placeholders.containsKey(placeholder))
			throw new IllegalArgumentException("Duplicate placeholder");
		
		m_fields.add(field);
		m_placeholders.put(placeholder, m_placeholders.size() + 1);
		return this;
	}

	@Override
	public QbWhere where()
	{
		m_where = new QbWhereImp(false, m_placeholders == null ? 1 : m_placeholders.size() + 1);
		return m_where;
	}

	@Override
	public QbUpdate all() {
		m_all = true;
		return this;
	}

	@Override
	public QbUpdate inTable(String table) {
		m_table = table;
		return this;
	}

}
