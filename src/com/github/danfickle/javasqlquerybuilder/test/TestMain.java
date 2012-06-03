package com.github.danfickle.javasqlquerybuilder.test;

import com.github.danfickle.javasqlquerybuilder.QbFactory;
import com.github.danfickle.javasqlquerybuilder.QbInsert;
import com.github.danfickle.javasqlquerybuilder.generic.QbFactoryImp;

public class TestMain
{
	public static void main(String... args)
	{
		QbFactory factory = new QbFactoryImp();
		QbInsert insert = factory.newInsertQuery();

		insert.set(factory.newStdField("col"), "one");
		insert.inTable("myTable");
		System.out.println(insert.getQueryString());
	}
	
	
	
	
	
}
