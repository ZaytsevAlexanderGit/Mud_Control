
package application;

import java.sql.*;


public class LoginModal {
Connection connection;

public Connection connectWBM(){
    connection=SqLiteConnection.ConnectorWBM();
    if (connection==null)    System.exit(1);
    return connection;
}

public Connection connectOBM(){
    connection=SqLiteConnection.ConnectorOBM();
    if (connection==null)    System.exit(1);
    return connection;
}

public boolean isDbConnected(){
    try{
        return !connection.isClosed();
    }
    catch (SQLException e)
            {
                return false;
            }
}
}
