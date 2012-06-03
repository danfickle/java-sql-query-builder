package com.github.danfickle.javasqlquerybuilder.generic;

class QbCommonImp
{
	static String protectTableName(String table)
	{
		return '`' + table + '`';
	}
}
