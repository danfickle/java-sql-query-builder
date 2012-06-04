package com.github.danfickle.javasqlquerybuilder.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.danfickle.javasqlquerybuilder.QbField;
import com.github.danfickle.javasqlquerybuilder.QbWhere;

class QbWhereImp implements QbWhere
{
	private boolean m_having;
	QbWhereImp(boolean having, int placeholderOffset) 
	{
		m_having = having;
		m_placeholderCnt = placeholderOffset;
	}

	private class WhereInfo
	{
		QbField m_field;
		QbWhereOperator m_op;
		boolean m_orClause; /* True if this clause should be preceded by OR. */
		String m_custom;

		boolean m_startBracket;
		boolean m_endBracket;
		
		int m_inCount;   /* The number of placeholders we have to create for an IN clause. */
		boolean m_notIn; /* True if it is a NOT IN clause. */

		WhereInfo() { }
		
		WhereInfo(QbField field, QbWhereOperator op)
		{
			m_field = field;
			m_op = op;
		}
	}

	private List<WhereInfo> m_whereInfo = new ArrayList<WhereInfo>(4);
	private Map<String, Integer> m_placeholders = new HashMap<String, Integer>();
	private int m_placeholderCnt = 1;

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(m_having ? " HAVING " : " WHERE ");
		
		int fieldCnt = 0;
		for (WhereInfo whereInfo : m_whereInfo)
		{
			if (whereInfo.m_startBracket)
			{
				builder.append('(');
				continue;
			}
			
			if (whereInfo.m_endBracket)
			{
				builder.append(')');
				continue;
			}

			if (whereInfo.m_custom != null)
			{
				builder.append(whereInfo.m_custom);
				fieldCnt++;
				continue;
			}
			
			if (fieldCnt != 0)
			{
				builder.append(whereInfo.m_orClause ? " OR " : " AND ");
			}
			fieldCnt++;
			
			builder.append(whereInfo.m_field.toString());
			
			if (whereInfo.m_inCount != 0)
			{
				builder.append(whereInfo.m_notIn ? " NOT IN (" : " IN (");
				QbCommonImp.createPlaceholders(builder, whereInfo.m_inCount);
				builder.append(')');
				continue;
			}
			
			builder.append(' ');
			builder.append(whereInfo.m_op.toString());
			builder.append(" ?");
		}
		
		return builder.toString();
	}
	
	
	
	@Override
	public QbWhere where(QbField field, String placeholder)
	{
		WhereInfo whereInfo = new WhereInfo(field, QbWhereOperator.EQUALS);
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt++;
		return this;
	}

	@Override
	public QbWhere where(QbField field, QbWhereOperator op, String placeholder)
	{
		WhereInfo whereInfo = new WhereInfo(field, op);
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt++;
		return this;
	}

	@Override
	public QbWhere orWhere(QbField field, String placeholder)
	{
		WhereInfo whereInfo = new WhereInfo(field, QbWhereOperator.EQUALS);
		whereInfo.m_orClause = true;
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt++;
		return this;
	}

	@Override
	public QbWhere orWhere(QbField field, QbWhereOperator op, String placeholder)
	{
		WhereInfo whereInfo = new WhereInfo(field, op);
		whereInfo.m_orClause = true;
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt++;
		return this;
	}

	@Override
	public QbWhere where(String custom)
	{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.m_custom = custom;
		m_whereInfo.add(whereInfo);
		return this;
	}

	@Override
	public QbWhere whereIn(QbField field, String placeholder, int count)
	{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.m_field = field;
		whereInfo.m_inCount = count;
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt += count;
		return this;
	}

	@Override
	public QbWhere orWhereIn(QbField field, String placeholder, int count)
	{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.m_field = field;
		whereInfo.m_inCount = count;
		whereInfo.m_orClause = true;
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt += count;
		return this;
	}

	@Override
	public QbWhere whereNotIn(QbField field, String placeholder, int count)
	{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.m_field = field;
		whereInfo.m_inCount = count;
		whereInfo.m_notIn = true;
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt += count;
		return this;
	}

	@Override
	public QbWhere orWhereNotIn(QbField field, String placeholder, int count)
	{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.m_field = field;
		whereInfo.m_inCount = count;
		whereInfo.m_notIn = true;
		whereInfo.m_orClause = true;
		m_whereInfo.add(whereInfo);
		m_placeholders.put(placeholder, m_placeholderCnt);
		m_placeholderCnt += count;
		return this;
	}

	@Override
	public QbWhere startBracket()
	{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.m_startBracket = true;
		m_whereInfo.add(whereInfo);
		return this;
	}

	@Override
	public QbWhere endBracket() 
	{
		WhereInfo whereInfo = new WhereInfo();
		whereInfo.m_endBracket = true;
		m_whereInfo.add(whereInfo);
		return this;
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		Integer idx = m_placeholders.get(placeholderName);
		
		if (idx == null)
			return 0;
		else
			return idx;
	}
	
	@Override
	public int getPlaceholderCount()
	{
		return m_placeholders.size();
	}
}