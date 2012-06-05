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

	private String joinTypeToString(QbJoinType joinType)
	{
		switch (joinType)
		{
		case DEFAULT:
			return "";
		case LEFT_OUTER:
			return "LEFT OUTER";
		case RIGHT_OUTER:
			return "RIGHT OUTER";
		case INNER:
		case OUTER:
		case LEFT:
		case RIGHT:
		default:
			return joinType.toString();
		}
	}
	
	
	@Override
	public String getQueryString()
	{
		StringBuilder builder = new StringBuilder("SELECT ");
		
		if (m_table == null)
			throw new IllegalStateException("Must specify table");
		
		if (m_selectFields == null)
			throw new IllegalStateException("Must specify some fields");
		
		if (m_distinct)
			builder.append("DISTINCT ");
		
		QbCommonImp.joinFieldNames(builder, m_selectFields);
		builder.append(" FROM ");
		builder.append(QbCommonImp.protectTableName(m_table));
		builder.append(' ');
		
	
		if (m_joinList != null)
		{
			for (JoinInfo join : m_joinList)
			{
				builder.append(joinTypeToString(join.joinType));
				builder.append(" JOIN ");
				builder.append(QbCommonImp.protectTableName(join.table));
				builder.append(" ON ");
				builder.append(join.leftSide.toString());
				builder.append(" = ");
				builder.append(join.rightSide.toString());
			}
		}

		if (m_where != null)
			builder.append(m_where.toString());
		
		if (m_groupBy != null)
		{
			builder.append(" GROUP BY ");
			QbCommonImp.joinFieldNames(builder, m_groupBy);
		}

		if (m_havingClause != null)
			builder.append(m_havingClause.toString());
		
		if (m_orderBy != null)
		{
			builder.append(" ORDER BY ");
			QbCommonImp.joinFieldNames(builder, m_orderBy);
			builder.append(' ');
			builder.append(m_orderByOrder.toString());
		}
		
		if (m_haveLimit)
		{
			builder.append(" LIMIT ");
			builder.append(m_offset);
			builder.append(", ");
			builder.append(m_limit);
		}
			
		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		int idx = 0;
		if (m_havingClause != null)
			idx = m_havingClause.getPlaceholderIndex(placeholderName);
		
		if (idx == 0 && m_where != null)
			idx = m_where.getPlaceholderIndex(placeholderName);
		
		if (idx == 0)
			throw new IllegalArgumentException("Placeholder doesn't exist");
		
		return idx;
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
	public QbWhere where()
	{
		m_where = new QbWhereImp(false, 1);
		return m_where;
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
	public QbWhere having()
	{
		m_havingClause = new QbWhereImp(true, m_where == null ? 1 : m_where.getPlaceholderCount() + 1);
		return m_havingClause;
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