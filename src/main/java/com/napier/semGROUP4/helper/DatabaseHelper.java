package com.napier.semGROUP4.helper;
import java.sql.*;

/** DatabaseHelper class
 * this class is used to create a connection and to stop a connection to a database
 *
 * @method: connectDB()
 * @method: closeDB()
 */
public class DatabaseHelper {

    // initializing the connection to null
    private Connection con = null;
    boolean connected = false;


    /** @constructor: empty
     *
     * @return: void
     */
    public DatabaseHelper() {
    }

    /** @method: connectDB()
     *
     * used to connect to database by creating a sql driver
     * runs whithin a while loop
     */
    public void connectDB() {

        // tries to load sql driver for job
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // loop for trying to connect to database
        while (!connected) {
            System.out.println("Trying to connect to database...");
            try {

                // waits 3 seconds before trying to assign connection
                Thread.sleep(3000);

                // assigns user and password to connection to connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "semgroup4");
                System.out.println("Connected to database!");
                connected = true;
                // wait 100ms to exit this trial
                Thread.sleep(100);

            }
            catch (SQLException sqle) {
                System.out.println("Failed to connect to database...");
            }
            catch (InterruptedException ie) {
                System.out.println("Interrupted? Check code.");
            }
        }
    }


    /** @method: closeDB()
     * used to close a database connection
     * checks if connections is not null
     */
    public void closeDB() {

        // if our connection is null we try to close it
        if (con != null) {
            try {
                con.close();
                connected = false;
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection " + e);
            }
        }
    }

    /** @method: getConnection()
     * used to get the connection value of the object calling this
     * @return Connection
     */
    public Connection getConnection() {
        return this.con;
    }

}
