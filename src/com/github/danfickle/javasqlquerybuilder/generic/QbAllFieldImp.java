package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;

class QbAllFieldImp implements QbField
{
	QbAllFieldImp() { }
	
	@Override
	public String toString()
	{
		return "*";
	}
}
