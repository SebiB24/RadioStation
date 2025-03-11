package Repo;

import Domain.Piesa;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class SQLRepository extends Repository<Piesa> {
    protected String URL = "jdbc:sqlite:C:/Users/sebyc/OneDrive/Documents/GitHub/Radio-ModelPractic/src/main/java/Resources/Piese.db";
    protected Connection conn = null;

    public SQLRepository() throws SQLException {
        openConnection();
        createSchema();
        initTables();
        initRepo();
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    protected void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS piese(id int, formatie varchar(50), titlu varchar(50), gen varchar(50), durata varchar(10));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    public void createList(String nume){
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + nume + "(id int, formatie varchar(50), titlu varchar(50), gen varchar(50), durata varchar(10));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    protected void initTables() {

        try {
            try (PreparedStatement statement1 = conn.prepareStatement("SELECT COUNT(*) from piese")){
                statement1.executeQuery().next();
                if (statement1.executeQuery().getInt(1) == 0) {
                    try (PreparedStatement statement = conn.prepareStatement("INSERT INTO piese VALUES(?, ?, ?, ?, ?)")) {
                        statement.setInt(1, 1);
                        statement.setString(2, "a");
                        statement.setString(3, "a");
                        statement.setString(4, "a");
                        statement.setString(5, "3:00");
                        statement.executeUpdate();
                    }
                    try (PreparedStatement statement = conn.prepareStatement("INSERT INTO piese VALUES(?, ?, ?, ?, ?)")) {
                        statement.setInt(1, 2);
                        statement.setString(2, "b");
                        statement.setString(3, "b");
                        statement.setString(4, "b");
                        statement.setString(5, "3:00");
                        statement.executeUpdate();
                    }
                    try (PreparedStatement statement = conn.prepareStatement("INSERT INTO piese VALUES(?, ?, ?, ?, ?)")) {
                        statement.setInt(1, 3);
                        statement.setString(2, "c");
                        statement.setString(3, "c");
                        statement.setString(4, "c");
                        statement.setString(5, "3:00");
                        statement.executeUpdate();
                    }
                    try (PreparedStatement statement = conn.prepareStatement("INSERT INTO piese VALUES(?, ?, ?, ?, ?)")) {
                        statement.setInt(1, 4);
                        statement.setString(2, "d");
                        statement.setString(3, "d");
                        statement.setString(4, "d");
                        statement.setString(5, "3:00");
                        statement.executeUpdate();
                    }
                    try (PreparedStatement statement = conn.prepareStatement("INSERT INTO piese VALUES(?, ?, ?, ?, ?)")) {
                        statement.setInt(1, 5);
                        statement.setString(2, "e");
                        statement.setString(3, "e");
                        statement.setString(4, "e");
                        statement.setString(5, "3:00");
                        statement.executeUpdate();
                    }
                }
            }
        }catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }
    }

    protected void initRepo(){
        ArrayList<Piesa> piese = getAll();
        for(Piesa piesa : piese) {
            super.add(piesa);
        }
    }

    public ArrayList<Piesa> getAll() {
        ArrayList<Piesa> piese = new ArrayList<>();

        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from piese"); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Piesa p = new Piesa(rs.getInt("id"), rs.getString("formatie")
                            , rs.getString("titlu"), rs.getString("gen"), rs.getString("durata"));
                    piese.add(p);
                }
            }
        } catch (SQLException ex) {
            System.err.println("[ERROR] getAll : " + ex.getMessage());
        }

        return piese;
    }

    @Override
    public void add(Piesa p){
        super.add(p);
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO piese VALUES(?, ?, ?, ?, ?)")) {
                statement.setInt(1, p.getId());
                statement.setString(2, p.getFormatie());
                statement.setString(3, p.getTitlu());
                statement.setString(4, p.getGen());
                statement.setString(5, p.getDurata());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }
    }

    @Override
    public void removeByID(int id){
        super.removeByID(id);
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM piese WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] delete : " + e.getMessage());
        }
    }

    @Override
    public void update(Piesa p){
        super.update(p);
        try {
            try (PreparedStatement statement = conn.prepareStatement(
                    "UPDATE piese SET formatie = ?, titlu = ?, gen = ?, durata = ?  WHERE id = ?")) {
                statement.setString(1, p.getFormatie());
                statement.setString(2, p.getTitlu());
                statement.setString(3, p.getGen());
                statement.setString (4, p.getDurata());
                statement.setInt(5, p.getId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }

    }

    public void addToList(Piesa p, String nume){
        super.add(p);
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO "+nume+" VALUES(?, ?, ?, ?, ?)")) {
                statement.setInt(1, p.getId());
                statement.setString(2, p.getFormatie());
                statement.setString(3, p.getTitlu());
                statement.setString(4, p.getGen());
                statement.setString(5, p.getDurata());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }
    }
}

