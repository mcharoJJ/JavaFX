package db.table;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * We had a Hard Time implementing a Table in Javafx
 * we tried till the last moment
 * we had no other choice to use the given jTable in the sample Gui project although its in Swing
 * we wanted to implement and embed the TableView in Donor Panel and Admin Control Panel
 * we were successful impleneting the table, but we had issues with the observable datas
 * hope you will consider this.
 */
public class DbTable extends javax.swing.JFrame {
    public static boolean donorFlag = false;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableUsers;


    public DbTable() {
        initComponents();
        getTableData();
        this.add(jScrollPane1);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DbTable();
            }
        });
    }

    private void getTableData() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/bloodbank", "root", "");
            st = conn.createStatement();
            if (donorFlag) {
                String sql = "SELECT * FROM DONOR";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"ID", "Name", "Blood Group", "Contact", "Last Date", "Active", "Location"}, 0);
                while (rs.next()) {
                    String col1 = rs.getString("DID");
                    String col2 = rs.getString("DNAME");
                    String col3 = rs.getString("DBGROUP");
                    String col4 = rs.getString("DCONTACT");
                    String col5 = rs.getString("DLASTDATE");
                    String col6 = rs.getString("DREADY");
                    String col7 = rs.getString("DLOCATION");
                    model.addRow(new Object[]{col1, col2, col3, col4, col5, col6, col7});
                }
                jTableUsers.setModel(model);

            } else if (!donorFlag) {

                String sql = "SELECT * FROM PATIENT";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"ID", "Name", "Blood Group", "Contact"}, 0);
                while (rs.next()) {
                    String col1 = rs.getString("PID");
                    String col2 = rs.getString("PNAME");
                    String col3 = rs.getString("PBGROUP");
                    String col4 = rs.getString("PCONTACT");
                    model.addRow(new Object[]{col1, col2, col3, col4});

                }
                jTableUsers.setModel(model);
            }


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * initialization of components
     */

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUsers = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableUsers.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{

                }
        ));
        jScrollPane1.setViewportView(jTableUsers);

    }

    /**
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
            JOptionPane.showMessageDialog(null, "Congratulations!! The operation done successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public ResultSet getTableDataQuery() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/bloodbank", "root", "");
            st = conn.createStatement();
            String sql = "SELECT * FROM donor";

            ResultSet rs = st.executeQuery(sql);

            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Blood Group", "contact",
                    "Last given date", "Ready", "Location"}, 0);

            return rs;

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param query
     */
    public void theUpdateQuery(String query) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/bloodbank", "root", "");
            st = conn.createStatement();
            st.executeUpdate(query);
            } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ;
        }
    }

    /**
     *
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
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }
}
