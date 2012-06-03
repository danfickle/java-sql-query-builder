package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;

public class QbAllTableFieldImp implements QbField
{
	private final String m_table;
	
	QbAllTableFieldImp(String table)
	{
		m_table = table;
	}
	
	@Override
	public String toString() 
	{
		return QbCommonImp.protectTableName(m_table) + ".*";
	}
}