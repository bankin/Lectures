package orm.strategies;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UpdateStrategy extends AbstractStrategy {

    @Override
    public void execute() throws SQLException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        // FIXME: Delete tables for non existing entities
        List<Class> allEntities = getAllEntities();

        for (Class entity : allEntities) {
            Field primary = this.getId(entity);
            primary.setAccessible(true);

            updateTable(entity, primary);
        }
    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields()).filter(field ->
                field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Cannot operate with entity without primary key"));
    }

    private <E> boolean updateTable(Class entity, Field primary) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity);
        if (!this.checkIfTableExists(tableName)) {
            this.doCreate(entity);
        }

        Field[] fields = entity.getDeclaredFields();
        // FIXME: Delete fields not present in the entity
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            Column columnAnnotation = field.getAnnotation(Column.class);
            if(!this.checkIfFieldExists(tableName, columnAnnotation.name())){
                this.doAlter(tableName, field);
            }
        }

        return false;
    }

    private void doAlter(String tableName, Field field) throws SQLException {
        Column columnAnnonation = field.getAnnotation(Column.class);
        String query = "ALTER TABLE " + this.dataSource + "." + tableName + " ADD COLUMN "
                + columnAnnonation.name() + " " + this.getDbType(field);
        this.connection.prepareStatement(query).execute();
    }

    private boolean checkIfTableExists(String tableName) throws SQLException {
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + this.dataSource
                + "' AND table_name = '" + tableName + "' LIMIT 1;";
        ResultSet rs = this.connection.prepareStatement(query).executeQuery();
        //rs.getString("table_name").equals("")
        if(!rs.first()){
            return false;
        }
        return true;
    }

    private <E> boolean checkIfFieldExists(String tableName,String fieldName) throws SQLException {
        String query = "SELECT COLUMN_NAME FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = '" + this.dataSource + "'" +
                " AND TABLE_NAME = '" + tableName +
                "' AND COLUMN_NAME = '" + fieldName + "'";
        ResultSet rs = this.connection.prepareStatement(query).executeQuery();
        if(!rs.first()){
            return false;
        }
        return true;
    }

    public void doDelete(Class<?> table, String criteria) throws Exception {
        String tableName = table.getAnnotation(Entity.class).name();
        if(!this.checkIfTableExists(tableName)){
            throw new Exception("Table doesn't exist.");
        }
        String query = "DELETE FROM " + this.dataSource + "." + tableName + " WHERE " + criteria;
        this.connection.prepareStatement(query).execute();
    }
}
