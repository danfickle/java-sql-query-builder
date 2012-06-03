package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;
import com.github.danfickle.javasqlquerybuilder.QbWhere;

class QbWhereImp implements QbWhere
{
	QbWhereImp() { }
	
	@Override
	public QbWhere where(QbField field, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere where(QbField field, QbWhereOperator op, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere orWhere(QbField field, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere orWhere(QbField field, QbWhereOperator op, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere where(String custom)
	{
		return this;
	}

	@Override
	public QbWhere whereIn(QbField field, String placeholder, int count)
	{
		return this;
	}

	@Override
	public QbWhere orWhereIn(QbField field, String placeholder, int count)
	{
		return this;
	}

	@Override
	public QbWhere whereNotIn(QbField field, String placeholder, int count)
	{
		return this;
	}

	@Override
	public QbWhere orWhereNotIn(QbField field, String placeholder, int count)
	{
		return this;
	}

	@Override
	public QbWhere like(QbField field, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere orLike(QbField field, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere notLike(QbField field, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere orNotLike(QbField field, String placeholder)
	{
		return this;
	}

	@Override
	public QbWhere startBracket()
	{
		return this;
	}

	@Override
	public QbWhere endBracket() 
	{
		return this;
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		return 0;
	}

}
