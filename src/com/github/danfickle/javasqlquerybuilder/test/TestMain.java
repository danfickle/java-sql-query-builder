package com.github.danfickle.javasqlquerybuilder.test;

import com.github.danfickle.javasqlquerybuilder.QbFactory;
import com.github.danfickle.javasqlquerybuilder.QbInsert;
import com.github.danfickle.javasqlquerybuilder.generic.QbFactoryImp;

public class TestMain
{
	public static void main(String... args)
	{
		QbFactory fac = new QbFactoryImp();
		QbInsert insert = fac.newInsertQuery();

		insert.set(fac.newStdField("col"), "one");
		insert.set(fac.newStdField("col2"), "two");
		insert.inTable("myTable");
		System.out.println(insert.getQueryString());
	}
}
