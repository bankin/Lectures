package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import orm.strategies.SchemaInitializationStrategy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Simona Simeonova on 9/15/2017.
 */
public class EntityManager implements DBContext {
    private SchemaInitializationStrategy strategy;
    private String dataSource;
    private Connection connection;

    public EntityManager(Connection connection, String dataSource, SchemaInitializationStrategy strategy) throws SQLException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        this.connection = connection;
        this.dataSource = dataSource;
        this.strategy = strategy;

        strategy.execute();
    }

    public <E> boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primary = this.getId(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        if(value==null || (Integer)value <= 0){
            return this.doInsert(entity,primary);
        }
        return this.doUpdate(entity, primary);
    }

    @Override
    public <E> Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public <E> Iterable<E> find(Class<E> table, String where) throws SQLException {
        return null;
    }

    public <E> E findFirst(Class<E> table) {
        return null;
    }

    public <E> E findFirst(Class<E> table, String where) throws SQLException, IllegalAccessException, InstantiationException {
        Statement stmt = this.connection.createStatement();
        String query = "SELECT * FROM " + this.getTableName(table) + " WHERE 1 " + (where!=null ? " AND " + where : "") + " LIMIT 1";
        ResultSet rs = stmt.executeQuery(query);
        E entity = table.newInstance();
        rs.next();
        this.fillEntity(table, entity,rs);
        return entity;
    }

    private void fillEntity(Class table, Object entity, ResultSet rs) throws SQLException,
            IllegalAccessException {
        for (int i = 0; i < table.getDeclaredFields().length; i++) {
            Field field = table.getDeclaredFields()[i];
            field.setAccessible(true);
            fillField(field, entity, rs, field.getAnnotation(Column.class).name());
        }
    }

    private static void fillField(Field field, Object instance, ResultSet rs, String fieldName)
            throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == int.class || field.getType() == Integer.class) {
            field.set(instance, rs.getInt(fieldName));
        } else if (field.getType() == long.class || field.getType() == Long.class) {
            field.set(instance, rs.getLong(fieldName));
        } else if (field.getType() == double.class || field.getType() == Double.class) {
            field.set(instance, rs.getDouble(fieldName));
        } else if (field.getType() == String.class) {
            field.set(instance, rs.getString(fieldName));
        } else if (field.getType() == Date.class) {
            field.set(instance, rs.getDate(fieldName));
        }
    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields()).filter(field ->
                field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Cannot operate with entity without primary key"));
    }

    private String getTableName(Class<?> entity) {
        String tableName = "";
        if(entity.isAnnotationPresent(Entity.class)){
            Entity annotation = entity.getAnnotation(Entity.class);
            tableName = annotation.name();
        }
        if(tableName.equals("")){
            tableName = entity.getSimpleName();
        }
        return tableName;
    }

    private String getFieldName(Field field) {
        String fieldName = "";

        if (field.isAnnotationPresent(Column.class)) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            fieldName = columnAnnotation.name();
        }

        if (fieldName.equals("")) {
            fieldName = field.getName();
        }

        return fieldName;
    }

    private <E> boolean doUpdate(E entity, Field primary) throws SQLException, IllegalAccessException {
        String query = "UPDATE " + this.getTableName(entity.getClass()) + " SET ";
        String fieldsNamesAndValues = "";
        String where = "";

        Field[] fields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++){
            Field currField = fields[i];
            currField.setAccessible(true);

            if (currField.getName().equals(primary.getName())){
                where += " WHERE `" + currField.getAnnotation(Column.class).name() + "` = " + currField.get(entity);
                continue;
            }else{
                if(currField.get(entity) instanceof Date){
                    fieldsNamesAndValues += "`" + currField.getAnnotation(Column.class).name() + "` = '" + new SimpleDateFormat("yyyy-MM-dd").format(currField.get(entity)) + "'";
                }else{
                    fieldsNamesAndValues += "`" + currField.getAnnotation(Column.class).name() + "` = '" + currField.get(entity) + "'";
                }
            }

            if(i < fields.length-1){
                fieldsNamesAndValues += ",";
            }
        }
        query += fieldsNamesAndValues + where;
        return this.connection.prepareStatement(query).execute();
    }

    private <E> boolean doInsert(E entity, Field primary) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        if (!this.checkIfTableExists(tableName)) {
            this.doCreate(entity);
        }
        String query = "INSERT INTO " + tableName + " (";
        String columns = "";
        String values = "";
        Field[] fields = entity.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if(!field.getName().equals(primary.getName())){
                columns += '`' + this.getFieldName(field) + '`';

                Column columnAnnotation = field.getAnnotation(Column.class);
                if(!this.checkIfFieldExists(tableName, columnAnnotation.name())){
                    this.doAlter(tableName, field);
                }

                Object value = field.get(entity);
                if(value instanceof Date){
                    values += "'" + new SimpleDateFormat("yyyy-MM-dd").format(value) + "'";
                }else{
                    values += "\'" + value + "\'";
                }

                if (i < fields.length - 1) {
                    columns += ", ";
                    values += ", ";
                }
            }
        }
        query += columns + ") " + "VALUES(" + values +")";
        return connection.prepareStatement(query).execute();
    }

    private void doAlter(String tableName, Field field) throws SQLException {
        Column columnAnnonation = field.getAnnotation(Column.class);
        String query = "ALTER TABLE " + tableName + " ADD COLUMN "
                + columnAnnonation.name() + " " + this.getDbType(field);
        this.connection.prepareStatement(query).execute();
    }

    private <E> boolean doCreate(E entity) throws SQLException {
        String query = "CREATE TABLE " + this.getTableName(entity.getClass()) + "( ";

        Field[] fields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field currentField = fields[i];
            currentField.setAccessible(true);

            query += " `" + currentField.getAnnotation(Column.class).name()
                    + "` " + this.getDbType(currentField);

            if(currentField.isAnnotationPresent(Id.class)){
                query += " PRIMARY KEY AUTO_INCREMENT";
            }

            if(i < fields.length -1){
                query += ',';
            }

        }
        query += ")";
        return this.connection.prepareStatement(query).execute();
    }

    private String getDbType(Field field){
        String mySQLType = "";
        switch (field.getType().getSimpleName()){
            case "int":
        case "Integer":
            mySQLType = "INT";
            break;
        case "String":
            mySQLType = "VARCHAR(50)";
            break;
        case "Date":
            mySQLType = "DATETIME";
            break;
        }
        return mySQLType;
    }

    public void doDelete(Class<?> table, String criteria) throws Exception {
        String tableName = table.getAnnotation(Entity.class).name();
        if(!this.checkIfTableExists(tableName)){
            throw new Exception("Table doesn't exist.");
        }
        String query = "DELETE FROM " + tableName + " WHERE " + criteria;
        this.connection.prepareStatement(query).execute();
    }

    private boolean checkIfTableExists(String tableName) throws SQLException {
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'orm_db' " +
                "AND table_name = '" + tableName + "' LIMIT 1;";
        ResultSet rs = this.connection.prepareStatement(query).executeQuery();
        //rs.getString("table_name").equals("")
        if(!rs.first()){
            return false;
        }
        return true;
    }

    private <E> boolean checkIfFieldExists(String tableName,String fieldName) throws SQLException {
        String query = "SELECT COLUMN_NAME FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = 'orm_db'" +
                " AND TABLE_NAME = '" + tableName +
                "' AND COLUMN_NAME = '" + fieldName + "'";
        ResultSet rs = this.connection.prepareStatement(query).executeQuery();
        if(!rs.first()){
            return false;
        }
        return true;
    }

    public Connection getConnection() {
        return connection;
    }
}
