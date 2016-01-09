import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Provided By Sabbir Sir, Faculty CS, AIUB
 *
 * a bit modified for custom use
 *
 */
class ConnectorModified {

    public ResultSet getTableData() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/bloodbank", "root", "");
            st = conn.createStatement();
            String sql = "SELECT * FROM donor";
            ResultSet rs = st.executeQuery(sql);

            return rs;

        } catch (Exception ex) {
            Main.cout(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }

    /**
     * Excutes single query
     *
     * @param query
     */
    public void theQuery(String query) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/bloodbank", "root", "");
            st = conn.createStatement();
            st.executeUpdate(query);
            PopUpWindow.error_popUp("Success!");
            Main.cout("Congratulations!! The operation done successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Main.cout(ex.getMessage());
        }
    }

    /**
     * return the result set
     * @param sql
     * @return
     */
    public ResultSet getSingleRow(String sql) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/bloodbank", "root", "");
            st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);
            return rs;

        } catch (Exception ex) {
            Main.cout(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }

}
