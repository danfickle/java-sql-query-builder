package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;

/**
 * Immutable class to implement all fields.
 * @author DanFickle
 */
class QbAllFieldImp implements QbField
{
	QbAllFieldImp() { }
	
	@Override
	public String toString()
	{
		return "*";
	}
}
