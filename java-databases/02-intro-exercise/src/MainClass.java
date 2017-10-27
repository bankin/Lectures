import java.sql.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainClass {

    private static final String CHECK_EXISTING_SQL = "SELECT * FROM %s WHERE %s = ?";

    public static void main(String[] args) throws SQLException {
        Connection connection = connect("root", "");

        //getVillainNames(connection);
        //getMinionNames(connection);
//        addMinion(connection);
//        removeVillain(connection);
//        updateMinionAge(connection);

        updateMinionsAge(connection);
        connection.close();
    }

    private static void updateMinionsAge(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] stringIds = line.split(" ");
        List<Integer> ids = Arrays.stream(stringIds)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

        for (Integer id : ids) {
            updateMinionAgeQuery(connection, id);
        }

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM minions");
        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            String name = rs.getString("name");
            Integer id = rs.getInt("id");
            if (ids.contains(id)) {
                name = Arrays.stream(name.split(" "))
                    .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1))
                    .collect(Collectors.joining(" "));

            }
            System.out.printf("%s %d\n", name, rs.getInt("age"));
        }
    }

    private static void updateMinionAgeQuery(Connection connection, Integer minionId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("CALL usp_get_older(?)");

        stmt.setInt(1, minionId);
        stmt.executeQuery();
    }

    private static void updateMinionAge(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Integer minionId = Integer.parseInt(sc.nextLine());

        PreparedStatement stmt = connection.prepareStatement("CALL usp_get_older(?)");

        stmt.setInt(1, minionId);
        stmt.executeQuery();

        stmt = connection.prepareStatement(String.format(CHECK_EXISTING_SQL, "minions", "id"));

        stmt.setInt(1, minionId);
        ResultSet rs = stmt.executeQuery();

        rs.next();

        System.out.printf("%s is now %d years old", rs.getString("name"), rs.getInt("age"));
    }

    private static void removeVillain(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Integer villainId = Integer.parseInt(sc.nextLine());

        try {
            connection.setAutoCommit(false);

            if (!exists(connection, "villains", "id", villainId.toString())) {
                System.out.println("No villain with ID " + villainId + " exists in the database.");
                return;
            }

            PreparedStatement stmt = connection.prepareStatement("SELECT " +
                    "COUNT(minion_id) AS `count` " +
                    "FROM minions_villains WHERE villain_id = ?");
            stmt.setInt(1, villainId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            Integer minionsCount = rs.getInt("count");

            stmt = connection.prepareStatement("DELETE " +
                    "FROM minions_villains WHERE villain_id = ?");
            stmt.setInt(1, villainId);

            stmt.executeUpdate();

            stmt = connection.prepareStatement("DELETE " +
                    "FROM villains WHERE id = ?");
            stmt.setInt(1, villainId);

            stmt.executeUpdate();

            System.out.println(minionsCount + " minion(s) was/were released.");

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }


    }

    private static Connection connect(String user, String password) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);
    }

    private static void addMinion(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String minionData = sc.nextLine();
        String villainData = sc.nextLine();

        String[] minionDataParts = minionData.split(" ");
        String minionName = minionDataParts[1];
        Integer minionAge = Integer.parseInt(minionDataParts[2]);
        String minionTownName = minionDataParts[3];

        String[] villainDataParts = villainData.split(" ");
        String villainName = villainDataParts[1];

        insertTownIfMissing(connection, minionTownName);

        insertMinion(connection, minionName, minionAge, minionTownName);

        insertVillainIfMissing(connection, villainName);

        relateMinionToVillain(connection, minionName, villainName);
    }

    private static void relateMinionToVillain(Connection connection, String minionName, String villainName) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO minions_villains " +
                "VALUES(" +
                "(SELECT id FROM minions WHERE name = ? LIMIT 1), " +
                "(SELECT id FROM villains WHERE name = ? LIMIT 1))");
        stmt.setString(1, minionName);
        stmt.setString(2, villainName);

        stmt.executeUpdate();

        System.out.println("Added minion " + minionName + " to villain " + villainName);
    }

    private static void insertVillainIfMissing(Connection connection, String villainName) throws SQLException {

        if (exists(connection, "villain", "name", villainName)) {
            return;
        }
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO villains(name, evilness_factor) " +
                "VALUES(?, 'evil')");
        stmt.setString(1, villainName);

        stmt.executeUpdate();

        System.out.println("Added villain " + villainName + ".");
    }

    private static void insertMinion(Connection connection, String minionName, Integer minionAge, String minionTownName) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO minions(name, age, town_id)" +
                " VALUES(?, ?, " +
                "(SELECT id FROM towns WHERE name = ? LIMIT 1))");
        stmt.setString(1, minionName);
        stmt.setInt(2, minionAge);
        stmt.setString(3, minionTownName);

        stmt.executeUpdate();

        System.out.println("Added minion " + minionName + ".");
    }

    private static void insertTownIfMissing(Connection connection, String minionTownName) throws SQLException {

        if (exists(connection, "towns", "name", minionTownName)) {
            return;
        }
        PreparedStatement stmt;stmt = connection.prepareStatement("INSERT INTO towns(name) VALUES(?)");
        stmt.setString(1, minionTownName);

        stmt.executeUpdate();

        System.out.println("Added town " + minionTownName + ".");
    }

    private static boolean exists(Connection connection, String table, String field, String value) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(String.format(CHECK_EXISTING_SQL, table, field));

        stmt.setString(1, value);
        ResultSet rs = stmt.executeQuery();

        return rs.first();
    }

    private static void getMinionNames(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Integer villainId = Integer.parseInt(sc.nextLine());

        PreparedStatement stmt =
            connection.prepareStatement("SELECT * FROM villains WHERE id = ?");

        stmt.setInt(1, villainId);

        ResultSet rs = stmt.executeQuery();

        if (!rs.first()) {
            System.out.println("No villain with ID " + villainId + " exists in the database.");
            return;
        }

        System.out.printf("Villain: %s\n", rs.getString("name"));

        stmt = connection.prepareStatement("SELECT * FROM minions AS m " +
                "JOIN minions_villains AS mv ON mv.minion_id = m.id " +
                "WHERE mv.villain_id = ?");

        stmt.setInt(1, villainId);
        rs = stmt.executeQuery();

        if (!rs.first()) {
            System.out.println("<no minions>");
            return;
        }

        for (int i = 1; rs.next(); i++) {
            System.out.printf("%d. %s %s\n", i, rs.getString("name"), rs.getString("age"));
        }

//        int i = 1;
//        while(rs.next()){
//            System.out.printf("%d. %s %s\n", i, rs.getString("name"), rs.getString("age"));
//            i++;
//        }
    }

    private static void getVillainNames(Connection connection) throws SQLException {
        PreparedStatement stmt =
            connection.prepareStatement("" +
                    "SELECT v.name, COUNT(mv.minion_id) AS `minion_count` " +
                    "FROM villains AS v " +
                    "JOIN minions_villains AS mv ON v.id = mv.villain_id " +
                    "GROUP BY mv.villain_id " +
                    "HAVING `minion_count` > 1");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            System.out.print(rs.getString("name"));
            System.out.print(" -> ");
            System.out.print(rs.getString("minion_count"));
            System.out.println();
        }

    }


}
