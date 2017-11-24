package services;

import java.sql.ResultSet;

public class Authentication {

    private static Credential credential;

    public static Boolean authenticate(String userType, String username, String password) {
        if(isValid(userType, username, password)) {
            credential = new Credential(userType, username, password);
            return true;
        }
        else return false;
    }

    public static Boolean verifyAuthentication() {
        return (credential != null) && isValid(credential.userType, credential.username, credential.password);
    }

    private static Boolean isValid(String userType, String username, String password) {

        try {

            String tableName = (userType == "Owner") ? "AdminLogin" : "Login";

            String query = "select * from " + tableName + " where Username=? and Password=?";

            ResultSet resultSet = QueryExecutor.executeQuery(query,  new String[]{username, password});
            int count = 0;
            while (resultSet.next()) {
                count = count+1;
            }
            resultSet.close();

            return count == 1;

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static Boolean isAuthenticated() {
        return credential != null;
    }

    public static String getUserType() {
        return credential.userType;
    }

    public static String getUsername() {
        return credential.username;
    }

    public static Boolean match(String password) {
        return password.equals(credential.password);
    }

}

class Credential {
    String username, password, userType;
    Credential(String _userType, String _username, String _password) {
        username = _username;
        password = _password;
        userType = _userType;
    }
}

