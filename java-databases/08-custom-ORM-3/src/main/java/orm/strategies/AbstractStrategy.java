package orm.strategies;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import orm.scanner.EntityScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractStrategy implements SchemaInitializationStrategy {

    String dataSource;
    Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    List<Class> getAllEntities() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return EntityScanner.getAllEntities(System.getProperty("user.dir"));
    }

    boolean doCreate(Class entity) throws SQLException {
        String query = "CREATE TABLE " + this.dataSource + "." + this.getTableName(entity) + "( ";

        Field[] fields = entity.getDeclaredFields();
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

    String getTableName(Class<?> entity) {
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

    String getDbType(Field field){
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
}
