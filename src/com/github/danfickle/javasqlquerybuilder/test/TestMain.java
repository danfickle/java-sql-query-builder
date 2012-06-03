package com.github.danfickle.javasqlquerybuilder.test;

import com.github.danfickle.javasqlquerybuilder.QbDelete;
import com.github.danfickle.javasqlquerybuilder.QbFactory;
import com.github.danfickle.javasqlquerybuilder.QbInsert;
import com.github.danfickle.javasqlquerybuilder.QbSelect;
import com.github.danfickle.javasqlquerybuilder.QbSelect.QbJoinType;
import com.github.danfickle.javasqlquerybuilder.QbSelect.QbOrderBy;
import com.github.danfickle.javasqlquerybuilder.QbWhere.QbWhereOperator;
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
		placeholderTests(fac);
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
		
		// Test a select of all of one table...
		select = fac.newSelectQuery();
		select.select(fac.newAllField("myTable"));
		select.from("myTable");
		assert(select.getQueryString().equals("SELECT `myTable`.* FROM `myTable` "));
		
		// Test a SUM select...
		select = fac.newSelectQuery();
		select.select(fac.newSum(fac.newStdField("col1"), "summy"));
		select.from("myTable");
		assert(select.getQueryString().equals("SELECT SUM(`col1`) AS summy FROM `myTable` "));
		
		// Test an AVG select with no alias...
		select = fac.newSelectQuery();
		select.select(fac.newAvg(fac.newStdField("col1"), null));
		select.from("myTable");
		assert(select.getQueryString().equals("SELECT AVG(`col1`) FROM `myTable` "));
		
		// Test a select with a qualified field...
		select = fac.newSelectQuery();
		select.select(fac.newQualifiedField("myTable", "col1"));
		select.from("myTable");
		assert(select.getQueryString().equals("SELECT `myTable`.`col1` FROM `myTable` "));
		
		// Test a MAX with a qualified field...
		select = fac.newSelectQuery();
		select.select(fac.newMax(fac.newQualifiedField("myTable", "col1"), "maxxy"));
		select.from("myTable");
		assert(select.getQueryString().equals("SELECT MAX(`myTable`.`col1`) AS maxxy FROM `myTable` "));

		// Test a default join...
		select = fac.newSelectQuery();
		select.select(fac.newQualifiedField("myTable", "id"), fac.newQualifiedField("myTable2", "id"));
		select.from("myTable");
		select.join("myTable2", fac.newStdField("foreign_key"), fac.newQualifiedField("myTable2", "id"));
		assert(select.getQueryString().equals("SELECT `myTable`.`id`, `myTable2`.`id` FROM `myTable`  JOIN `myTable2` ON `foreign_key` = `myTable2`.`id`"));

		// Test an INNER JOIN....
		select = fac.newSelectQuery();
		select.select(fac.newQualifiedField("myTable", "id"), fac.newQualifiedField("myTable2", "id"));
		select.from("myTable");
		select.join("myTable2", fac.newStdField("foreign_key"), fac.newQualifiedField("myTable2", "id"), QbJoinType.INNER);
		assert(select.getQueryString().equals("SELECT `myTable`.`id`, `myTable2`.`id` FROM `myTable` INNER JOIN `myTable2` ON `foreign_key` = `myTable2`.`id`"));
		
		// Test a three way join...
		select = fac.newSelectQuery();
		select.select(fac.newQualifiedField("myTable", "id"), fac.newQualifiedField("myTable2", "id"), fac.newQualifiedField("myTable3", "id"));
		select.from("myTable");
		select.join("myTable2", fac.newStdField("foreign_key"), fac.newQualifiedField("myTable2", "id"), QbJoinType.INNER);
		select.join("myTable3", fac.newStdField("foreign_key"), fac.newQualifiedField("myTable3", "id"));
		assert(select.getQueryString().equals("SELECT `myTable`.`id`, `myTable2`.`id`, `myTable3`.`id` FROM `myTable` INNER JOIN `myTable2` ON `foreign_key` = `myTable2`.`id` JOIN `myTable3` ON `foreign_key` = `myTable3`.`id`"));
		
		// Test DISTINCT keyword and method chaining...
		select = fac.newSelectQuery();
		select.distinct()
			.select(fac.newStdField("col1"))
			.from("myTable");
		assert(select.getQueryString().equals("SELECT DISTINCT `col1` FROM `myTable` "));
		
		// Test Group by with multiple fields...
		select = fac.newSelectQuery();
		select.select(fac.newMin(fac.newStdField("col1"), "minny"))
			.from("myTable")
			.groupBy(fac.newStdField("group1"), fac.newStdField("group2"));
		assert(select.getQueryString().equals("SELECT MIN(`col1`) AS minny FROM `myTable`  GROUP BY `group1`, `group2`"));
		
		// Test group by with one field...
		select = fac.newSelectQuery();
		select.select(fac.newMax(fac.newStdField("col1"), "maxxy"))
			.from("myTable")
			.groupBy(fac.newQualifiedField("myTable", "col1"));
		assert(select.getQueryString().equals("SELECT MAX(`col1`) AS maxxy FROM `myTable`  GROUP BY `myTable`.`col1`"));
		
		// Test the limit clause...
		select = fac.newSelectQuery()
				.distinct()
				.select(fac.newAllField())
				.from("myTable")
				.limit(0, 100);
		assert(select.getQueryString().equals("SELECT DISTINCT * FROM `myTable`  LIMIT 0, 100"));	
		
		// Test the having clause...
		select = fac.newSelectQuery();
		select.select(fac.newCount(fac.newStdField("col1"), "cnt"))
				.from("myTable")
				.groupBy(fac.newStdField("test"))
				.having()
				.where(fac.newStdField("cnt"), "cnt");
		assert(select.getQueryString().equals("SELECT COUNT(`col1`) AS cnt FROM `myTable`  GROUP BY `test` HAVING `cnt` = ?"));

		// Test the order by clause...
		select = fac.newSelectQuery()
				.select(fac.newStdField("col1"))
				.distinct()
				.from("myTable")
				.orderBy(QbOrderBy.ASC, fac.newStdField("col2"));
		assert(select.getQueryString().equals("SELECT DISTINCT `col1` FROM `myTable`  ORDER BY `col2` ASC"));
	}

	static void placeholderTests(QbFactory fac)
	{
		// Test the placeholder with a simple insert...
		QbInsert ins = fac.newInsertQuery();
		ins.set(fac.newStdField("test"), "test_placeholder");
		ins.inTable("myTable");
		assert(ins.getQueryString().equals("INSERT INTO `myTable` (`test`) VALUES (?)"));
		assert(ins.getPlaceholderIndex("test_placeholder") == 1);

		// Test the placeholders with multiple sets...
		ins = fac.newInsertQuery();
		ins.set(fac.newStdField("test1"), "holder1");
		ins.set(fac.newStdField("test2"), "holder2");
		ins.inTable("myTable");
		assert(ins.getQueryString().equals("INSERT INTO `myTable` (`test1`, `test2`) VALUES (?, ?)"));
		assert(ins.getPlaceholderIndex("holder1") == 1);
		assert(ins.getPlaceholderIndex("holder2") == 2);		
		
		// Test the placeholders with a delete query...
		QbDelete del = fac.newDeleteQuery();
		del.from("myTable")
			.where()
			.where(fac.newStdField("test1"), "1")
			.where(fac.newStdField("test2"), "2");
		assert(del.getQueryString().equals("DELETE FROM `myTable` WHERE `test1` = ? AND `test2` = ?"));
		assert(del.getPlaceholderIndex("1") == 1);
		assert(del.getPlaceholderIndex("2") == 2);
		
		// Test the placeholders with a select query...
		QbSelect sel = fac.newSelectQuery();
		sel.distinct()
			.select(fac.newStdField("test"))
			.from("myTable")
			.where()
			.whereIn(fac.newStdField("test"), "test_placeholder", 4);
		assert(sel.getQueryString().equals("SELECT DISTINCT `test` FROM `myTable`  WHERE `test` IN (?, ?, ?, ?)"));
		assert(sel.getPlaceholderIndex("test_placeholder") == 1);
		
		// Test the placeholders with a complex select...
		sel = fac.newSelectQuery();
		sel.select(fac.newCount(fac.newStdField("test"), "cnt"))
			.from("myTable")
			.where()
			.where(fac.newStdField("test"), QbWhereOperator.GREATER_THAN, "1");
		sel.having()
			.where(fac.newStdField("cnt"), QbWhereOperator.LESS_THAN, "2");
		assert(sel.getQueryString().equals("SELECT COUNT(`test`) AS cnt FROM `myTable`  WHERE `test` > ? HAVING `cnt` < ?"));
		assert(sel.getPlaceholderIndex("1") == 1);
		assert(sel.getPlaceholderIndex("2") == 2);
		
		System.out.println(sel.getQueryString() + sel.getPlaceholderIndex("2"));
	}
}
