import orm.Connector;
import orm.EntityManager;
import orm.EntityManagerBuilder;
import orm.strategies.DropCreateStrategy;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static orm.scanner.EntityScanner.getAllEntities;

/**
 * Created by Simona Simeonova on 9/15/2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        List<Class> allEntities = getAllEntities(System.getProperty("user.dir"));

        Connector c = new Connector(new EntityManagerBuilder());
//
//        EntityManager e = new EntityManagerBuilder()
//                .configureConnectionString()
//                    .setDriver("jdbc")
//                    .setAdapter("mysql")
//                    .setHost("localhost")
//                    .setPort("3306")
//                    .setPass("")
//                    .setUser("root")
//                    .createConnection()
//                .configureCreationType()
//                    .setUpdateStrategy()
//                .setDataSource("asd")
//                .build();

//        Connector connector = new Connector();
//        connector.createConnection("root", "1234", "orm_db");
//        EntityManager em = new EntityManager(con);
//
//        entities.ExampleEntity example = new entities.ExampleEntity("First Last", "Sofia");
//        em.persist(example);
//        em.doDelete(entities.ExampleEntity.class, " full_name LIKE \"First Last\"");
    }
}