package com.github.danfickle.javasqlquerybuilder.test;

import com.github.danfickle.javasqlquerybuilder.QbDelete;
import com.github.danfickle.javasqlquerybuilder.QbFactory;
import com.github.danfickle.javasqlquerybuilder.QbInsert;
import com.github.danfickle.javasqlquerybuilder.QbSelect;
import com.github.danfickle.javasqlquerybuilder.generic.QbFactoryImp;

/**
 * Please make sure you have assertions enabled to run these tests.
 * @author DanFickle
 */
public class TestMain
{
	public static void main(String... args)
	{
		QbFactory fac = new QbFactoryImp();
		insertTests(fac);
		deleteTests(fac);
		selectTests(fac);
	}

	static void insertTests(QbFactory fac)
	{
		// Test a simple insert...
		QbInsert insert = fac.newInsertQuery();
		insert.set(fac.newStdField("col1"), "one");
		insert.set(fac.newStdField("col2"), "two");
		insert.inTable("myTable");
		assert(insert.getQueryString().equals("INSERT INTO `myTable` (`col1`, `col2`) VALUES (?, ?)"));

		// Test an insert with a qualified field...
		insert = fac.newInsertQuery();
		insert.set(fac.newQualifiedField("myTable", "col1"), "one");
		insert.inTable("myTable");
		assert(insert.getQueryString().equals("INSERT INTO `myTable` (`myTable`.`col1`) VALUES (?)"));
	}

	static void deleteTests(QbFactory fac)
	{
		// Test a delete of everything...
		QbDelete delete = fac.newDeleteQuery();
		delete.all();
		delete.from("myTable");
		assert(delete.getQueryString().equals("DELETE FROM `myTable`"));
		
		// Test a delete with where clause...
		delete = fac.newDeleteQuery();
		delete.where().where(fac.newStdField("test"), "one");
		delete.from("myTable");
		assert(delete.getQueryString().equals("DELETE FROM `myTable` WHERE `test` = ?"));
	}
	
	static void selectTests(QbFactory fac)
	{
		// Test a select of everything...
		QbSelect select = fac.newSelectQuery();
		select.select(fac.newAllField());
		select.from("myTable");
		assert(select.getQueryString().equals("SELECT * FROM `myTable` "));
		
		
		System.out.println(select.getQueryString());
	}


}
