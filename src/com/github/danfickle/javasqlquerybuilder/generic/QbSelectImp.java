package com.github.danfickle.javasqlquerybuilder.generic;

import java.util.ArrayList;
import java.util.List;

import com.github.danfickle.javasqlquerybuilder.QbField;
import com.github.danfickle.javasqlquerybuilder.QbSelect;
import com.github.danfickle.javasqlquerybuilder.QbWhere;

class QbSelectImp implements QbSelect
{
	private int m_offset;
	private int m_limit;
	private boolean m_haveLimit;
	private QbField[] m_orderBy;
	private QbOrderBy m_orderByOrder;
	private QbWhere m_havingClause;
	private QbWhere m_where;
	private QbField[] m_groupBy;

	QbSelectImp() { }
	
	private class JoinInfo
	{
		final QbField leftSide;
		final QbField rightSide;
		final QbJoinType joinType;
		final String table;

		JoinInfo(QbField left, QbField right, String tabl, QbJoinType type)
		{
			leftSide = left;
			rightSide = right;
			joinType = type;
			table = tabl;
		}
	}
	
	private List<JoinInfo> m_joinList;
	private QbField[] m_selectFields;
	private boolean m_distinct;
	private String m_table;

	@Override
	public String getQueryString()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public QbSelect select(QbField... fields)
	{
		m_selectFields = fields;
		return this;
	}

	@Override
	public QbSelect from(String table)
	{
		m_table = table;
		return this;
	}

	@Override
	public QbSelect where(QbWhere where)
	{
		m_where = where;
		return this;
	}

	@Override
	public QbSelect distinct()
	{
		m_distinct = true;
		return this;
	}

	@Override
	public QbSelect join(String table, QbField field1, QbField field2,
			QbJoinType joinType) 
	{
		if (m_joinList == null)
			m_joinList = new ArrayList<JoinInfo>(2);
		
		m_joinList.add(new JoinInfo(field1, field2, table, joinType));
		return this;
	}

	@Override
	public QbSelect join(String table, QbField field1, QbField field2)
	{
		if (m_joinList == null)
			m_joinList = new ArrayList<JoinInfo>(2);
		
		m_joinList.add(new JoinInfo(field1, field2, table, QbJoinType.DEFAULT));
		return this;
	}

	@Override
	public QbSelect groupBy(QbField... fields)
	{
		m_groupBy = fields;
		return this;
	}

	@Override
	public QbSelect having(QbWhere havingClause)
	{
		m_havingClause = havingClause;
		return this;
	}

	@Override
	public QbSelect orderBy(QbOrderBy order, QbField... fields)
	{
		m_orderBy = fields;
		m_orderByOrder = order;
		return this;
	}

	@Override
	public QbSelect limit(int offset, int count)
	{
		m_offset = offset;
		m_limit = count;
		m_haveLimit = true;
		return this;
	}
}