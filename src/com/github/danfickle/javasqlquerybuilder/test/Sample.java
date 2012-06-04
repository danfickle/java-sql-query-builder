package com.github.danfickle.javasqlquerybuilder.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.danfickle.javasqlquerybuilder.QbDelete;
import com.github.danfickle.javasqlquerybuilder.QbFactory;
import com.github.danfickle.javasqlquerybuilder.QbInsert;
import com.github.danfickle.javasqlquerybuilder.QbSelect;
import com.github.danfickle.javasqlquerybuilder.QbSelect.QbJoinType;
import com.github.danfickle.javasqlquerybuilder.QbUpdate;
import com.github.danfickle.javasqlquerybuilder.QbWhere.QbWhereOperator;
import com.github.danfickle.javasqlquerybuilder.generic.QbFactoryImp;

public class Sample 
{
	/**
	 * Sample of using java-sql-query-builder with various queries.
	 * IMPORTANT:
	 *   For clarity exception handling and cleanup is left out.
	 *   You should make sure all sql objects are closed.
	 */
	static void sample() throws SQLException, ClassNotFoundException
	{
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/YourDatabase", "root", "your_password");		
//		PreparedStatement stmt;
		
		QbFactory f = new QbFactoryImp();

		// Update first_name, last_name and age for a given id...
		QbUpdate up = f.newUpdateQuery();
		up.set(f.newStdField("first_name"), ":first_name")
		  .set(f.newStdField("last_name"), ":last_name")
		  .set(f.newStdField("age"), ":age")
		  .inTable("myTable")
		  .where()
		  .where(f.newStdField("id"), ":id");
		assert(up.getQueryString().equals("UPDATE `myTable` SET `first_name` = ?, `last_name` = ?, `age` = ? WHERE `id` = ?"));
//		stmt = conn.prepareStatement(up.getQueryString());
//		stmt.setString(up.getPlaceholderIndex(":first_name"), "John");
//		stmt.setString(up.getPlaceholderIndex(":last_name"), "Citizen");
//		stmt.setInt(up.getPlaceholderIndex(":age"), 32);
//		stmt.setInt(up.getPlaceholderIndex(":id"), 1024);
//		stmt.executeUpdate();
		
		// Delete all records with a given age value...
		QbDelete del = f.newDeleteQuery();
		del.from("myTable")
		   .where()
		   .where(f.newStdField("age"), ":age");
		assert(del.getQueryString().equals("DELETE FROM `myTable` WHERE `age` = ?"));
//		stmt = conn.prepareStatement(del.getQueryString());
//		stmt.setInt(del.getPlaceholderIndex(":age"), 32);
//		stmt.executeUpdate();
		
		// Insert a record with the egg_hatched field set to true...
		QbInsert in = f.newInsertQuery();
		in.set(f.newStdField("egg_hatched"), ":egg_hatched")
		  .inTable("myTable");
		assert(in.getQueryString().equals("INSERT INTO `myTable` (`egg_hatched`) VALUES (?)"));
//		stmt = conn.prepareStatement(in.getQueryString());
//		stmt.setBoolean(in.getPlaceholderIndex(":egg_hatched"), true);
//		stmt.executeUpdate();
		
		// Simple select query, retrieves id, age and last_name for records which
		// don't have the given id...
		QbSelect sel = f.newSelectQuery();
		sel.select(f.newStdField("id"), f.newStdField("age"), f.newStdField("last_name"))
		   .from("myTable")
		   .where()
		   .where(f.newStdField("id"), QbWhereOperator.NOT_EQUALS, ":id");
		assert(sel.getQueryString().equals("SELECT `id`, `age`, `last_name` FROM `myTable`  WHERE `id` <> ?"));
//		stmt = conn.prepareStatement(sel.getQueryString());
//		stmt.setInt(sel.getPlaceholderIndex(":id"), 1024);
//		ResultSet rs = stmt.executeQuery();
//		
//		while (rs.next())
//		{
//			System.out.println(rs.getInt("id") + ":" + rs.getInt("age") + ":" + rs.getString("last_name"));
//		}
		
		// Complicated select query with join...
		sel = f.newSelectQuery();
		sel.select(f.newQualifiedField("t1", "id"), f.newQualifiedField("t2", "id"))
		   .from("t1")
		   .join("t2", f.newQualifiedField("t2", "id"), f.newQualifiedField("t1", "t1_id"), QbJoinType.LEFT)
		   .where()
		   .where(f.newStdField("age"), ":age");
		assert(sel.getQueryString().equals("SELECT `t1`.`id`, `t2`.`id` FROM `t1` LEFT JOIN `t2` ON `t2`.`id` = `t1`.`t1_id` WHERE `age` = ?"));
//		stmt = conn.prepareStatement(sel.getQueryString());
//		stmt.setInt(sel.getPlaceholderIndex(":age"), 45);
//		rs = stmt.executeQuery();
		// Record enumeration left out...
		
		
		// Select query with group by...
		sel = f.newSelectQuery();
		sel.select(f.newAvg(f.newStdField("age"), "avg_age"))
		   .from("myTable")
		   .groupBy(f.newStdField("first_name"))
		   .having()
		   .where(f.newStdField("avg_age"), QbWhereOperator.GREATER_THAN, ":avg_age");
		assert(sel.getQueryString().equals("SELECT AVG(`age`) AS avg_age FROM `myTable`  GROUP BY `first_name` HAVING `avg_age` > ?"));
//		stmt = conn.prepareStatement(sel.getQueryString());
//		stmt.setInt(sel.getPlaceholderIndex(":avg_age"), 50);
//		rs = stmt.executeQuery();
		// Record enumeration left out...
	}
}
