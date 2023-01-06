package com.huihui.memo.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StringType;

import java.sql.Types;

/**
 * A class facilitating the interaction of the program with the database.
 */
public class SQLiteDialect extends Dialect {

    /**
     * Construct an instance of SQLiteDialect and set all of the column types and functions.
     */
    public SQLiteDialect() {
        registerColumnType(Types.BIT, "integer");
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.FLOAT, "float");
        registerColumnType(Types.REAL, "real");
        registerColumnType(Types.DOUBLE, "double");
        registerColumnType(Types.NUMERIC, "numeric");
        registerColumnType(Types.DECIMAL, "decimal");
        registerColumnType(Types.CHAR, "char");
        registerColumnType(Types.VARCHAR, "varchar");
        registerColumnType(Types.LONGVARCHAR, "longvarchar");
        registerColumnType(Types.DATE, "date");
        registerColumnType(Types.TIME, "time");
        registerColumnType(Types.TIMESTAMP, "timestamp");
        registerColumnType(Types.BINARY, "blob");
        registerColumnType(Types.VARBINARY, "blob");
        registerColumnType(Types.LONGVARBINARY, "blob");
        registerColumnType(Types.BLOB, "blob");
        registerColumnType(Types.CLOB, "clob");
        registerColumnType(Types.BOOLEAN, "integer");

        registerFunction("concat", new VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""));
        registerFunction("mod", new SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"));
        registerFunction("substr", new StandardSQLFunction("substr", StringType.INSTANCE));
        registerFunction("substring", new StandardSQLFunction("substr", StringType.INSTANCE));
    }

    /**
     * Get whether the database supports identity column key generation.
     *
     * @return boolean representing whether the database supports identity column key generation
     */
    public boolean supportsIdentityColumns() {
        return true;
    }

    /**
     * Get whether this SQLiteDialect instance has a separate identity data type  or an Identity clause added to
     * the data type.
     *
     * @return boolean representing whether this SQLiteDialect instance has a separate identity data type (false) or an
     * Identity clause added to the data type (true)
     */
    public boolean hasDataTypeInIdentityColumn() {
        return false;
    }

    /**
     * Get a string representing the identity column.
     *
     * @return String representation of identity column
     */
    public String getIdentityColumnString() {
        return "integer";
    }

    /**
     * Get SELECT command applicable to retrieve last identity value generated.
     *
     * @return String SELECT command that can be used to retrieve last generated identify value
     */
    public String getIdentitySelectString() {
        return "select last_insert_rowid()";
    }

    /**
     * Get whether this SQLiteDialect instance can handle putting a limit on query results with an SQL clause.
     *
     * @return boolean representing if SQLiteDialect instance is able to handle having a limit on query results with
     * an SQL clause
     */
    public boolean supportsLimit() {
        return true;
    }

    /**
     * Applies and returns a modified query that has a limiting clause applied.
     *
     * @param query     String SQL query that is to have the limit clause applied
     * @param hasOffset boolean whether the query requests an offset
     * @return the modified String query with the limiting clause applied
     */
    protected String getLimitString(String query, boolean hasOffset) {
        return new StringBuffer(query.length() + 20).append(query).append(hasOffset ? " limit ? offset ?" : " limit ?")
                .toString();
    }

    /**
     * Get whether this SQLiteDialect instance supports temporary tables.
     *
     * @return boolean representing whether this SQLiteDialect instance supports temporary tables
     */
    public boolean supportsTemporaryTables() {
        return true;
    }

    /**
     * Get command to create a new temporary table.
     *
     * @return String command used to create a temporary table
     */
    public String getCreateTemporaryTableString() {
        return "create temporary table if not exists";
    }

    /**
     * Get if temporary table must be dropped after use.
     *
     * @return boolean representing whether temporary table should be dropped after it is used
     */
    public boolean dropTemporaryTableAfterUse() {
        return false;
    }

    /**
     * Get if the database's current timestamp can be retrieved by this SQLiteDialect instance.
     *
     * @return boolean representing whether this SQLiteDialect instance has functionality to retrieve the database's
     * current time
     */
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    /**
     * Get if the value returned by method isCurrentTimestampSelectString() is to be treated as callable.
     *
     * @return whether the boolean returned by isCurrentTimestampSelectString() should be treated as callable
     */
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    /**
     * Get the SQL command with the ability to retrieve the current timestamp of the database.
     *
     * @return String SQL command that can be used to retrieve the database's current timestamp
     */
    public String getCurrentTimestampSelectString() {
        return "select current_timestamp";
    }

    /**
     * Get if UNION ALL command is supported.
     *
     * @return boolean whether this SQLiteDialect instance supports the SQL command UNION ALL
     */
    public boolean supportsUnionAll() {
        return true;
    }

    /**
     * Get if ALTER TABLE command is supported.
     *
     * @return boolean whether this SQLiteDialect instance supports the SQL command ALTER TABLE
     */
    public boolean hasAlterTable() {
        return false;
    }

    /**
     * Get if restraints much be dropped before tables can be dropped in this SQLDialect instance.
     *
     * @return boolean whether if restraints much be dropped before tables can be dropped
     */
    public boolean dropConstraints() {
        return false;
    }

    /**
     * Get the SQL command that can add a column to the table.
     *
     * @return String SQL command used to add a column to the table
     */
    public String getAddColumnString() {
        return "add column";
    }

    /**
     * Get the String that must be added to SELECT commands to retrieve locks for this SQLiteDialect.
     *
     * @return String that must be added to SELECT commands to get locks for this SQLiteDialect or an empty String
     * if none are required
     */
    public String getForUpdateString() {
        return "";
    }

    /**
     * Get whether SQL command FOR UPDATE can be used on outer joined rows in this SQLiteDialect instance.
     *
     * @return boolean representing if this SQLiteDialect instance supports the use of command FOR UPDATE on
     * outer joined rows
     */
    public boolean supportsOuterJoinForUpdate() {
        return false;
    }

    /**
     * Get syntax for dropping foreign key for this SQLiteDialect instance.
     *
     * @return String syntax to drop foreign key in this SQLiteDialect instance
     * @throws UnsupportedOperationException this is not supported in this implementation
     */
    public String getDropForeignKeyString() {
        throw new UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect");
    }

    /**
     * Get syntax able to be used to add to a table a foreign key constraint in this SQLiteDialect instance.
     *
     * @param constraintName       foreign key constraint name
     * @param foreignKey           names of columns making up the foreign key
     * @param referencedTable      table reference by the foreign key
     * @param primaryKey           columns in the table referenced by the foreign key
     * @param referencesPrimaryKey boolean whether primary key is referenced
     * @return String syntax able to be used to add to a table a foreign key constraint
     * @throws UnsupportedOperationException this is not supported in this implementation
     */
    public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
                                                   String[] primaryKey, boolean referencesPrimaryKey) {
        throw new UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect");
    }

    /**
     * Get the syntax that can be used in this SQLiteDialect instance to add a primary key constraint.
     *
     * @param constraintName name of the primary key constraint
     * @return String syntax to add primary key constraint
     * @throws UnsupportedOperationException this is not supported in this implementation
     */
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        throw new UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect");
    }

    /**
     * Get whether there is support for using the command IF EXISTS in this SQLiteDialect instance.
     *
     * @return boolean whether command IF EXISTS is supported
     */
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    /**
     * Get whether there is support for using the cascade delete command in this SQLiteDialect instance.
     *
     * @return boolean whether command cascade delete is supported
     */
    public boolean supportsCascadeDelete() {
        return false;
    }
}