package orm;

import java.sql.SQLException;

public interface DBContext
{
     <E> boolean persist(E entity) throws IllegalAccessException, SQLException;
     <E> Iterable<E> find(Class<E> table);
     <E> Iterable<E> find(Class<E> table, String where) throws SQLException;
     <E> E findFirst(Class<E> table);
     <E> E findFirst(Class<E> table, String where) throws SQLException, IllegalAccessException, InstantiationException;
}
