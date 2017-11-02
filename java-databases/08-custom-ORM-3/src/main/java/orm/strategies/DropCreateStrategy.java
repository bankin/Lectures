package orm.strategies;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class DropCreateStrategy extends AbstractStrategy {
    private final String DROP_DATABASE_QUERY = "DROP DATABASE IF EXISTS `%s`;";
    private final String CREATE_DATABASE_QUERY = "CREATE DATABASE `%s`;";

    public DropCreateStrategy() {}

    @Override
    public void execute() throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String dropQuery = String.format(DROP_DATABASE_QUERY, this.dataSource);
        this.connection.prepareStatement(dropQuery).execute();

        String createQuery = String.format(CREATE_DATABASE_QUERY, this.dataSource);
        this.connection.prepareStatement(createQuery).execute();

        List<Class> entities = this.getAllEntities();

        for (Class entity : entities) {
            this.doCreate(entity);
        }
    }
}
