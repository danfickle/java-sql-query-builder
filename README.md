# Java SQL Query Builder #

This project is an SQL query builder for Java. Currently it is a work in progress and only supports MySql. However, it programs to interfaces so should be easy to add other SQL dialects.

The project is licensed under the permissive BSD license.

Using this project looks something like this:

		QbSelect sel = fac.newSelectQuery();
		sel.select(fac.newStdField("test"))
			.from("myTable")
			.where()
			.where(fac.newStdField("test"), QbWhereOperator.NOT_EQUALS, "1")
			.orWhere(fac.newStdField("test2"), "2");
		assert(sel.getQueryString().equals("SELECT `test` FROM `myTable`  WHERE `test` <> ? OR `test2` = ?"));
		assert(sel.getPlaceholderIndex("2") == 2);

The query once built returns two pieces of information. The first is the query that can be passed to Connection.prepareStatement. The second is the placeholders that can be looked up to be used with the PreparedStatement.set* functions.
