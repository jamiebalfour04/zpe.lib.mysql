import jamiebalfour.generic.JBBinarySearchTree;
import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;
import jamiebalfour.zpe.types.ZPEBoolean;
import jamiebalfour.zpe.types.ZPEString;

import java.sql.SQLException;

public class ZPEMySQLObject extends ZPEStructure {
  
  private static final long serialVersionUID = 5811011685776858084L;

  MySQLAccess sql;
  ZPEMySQLObject _this = this;

  public ZPEMySQLObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper parent) {
    super(z, parent, "ZPEMySQLConnection");
    System.out.println("MySQL loaded");
    addNativeMethod("connect", new connect_Command());
    addNativeMethod("query", new query_Command());
    addNativeMethod("prepare", new prepare_Command());
    addNativeMethod("get_columns", new get_columns_Command());
    addNativeMethod("get_tables", new get_tables_Command());
    addNativeMethod("query_to_json", new query_to_json_Command());
  }

  public boolean connect(String host, int port, String db, String user, String password) {
    try {
      sql = new MySQLAccess();
    } catch(Exception e) {
      ZPECore.log(e.getMessage());
      System.err.println("Cannot create MySQL connection. Please refer to ZPE log for more information.");
      return false;
    }

    try {
      sql.connect(host, port, db, user, password);
      return true;
    } catch (SQLException e) {
      ZPECore.log(e.getMessage());
      System.err.println("Cannot connect to MySQL database. Please refer to ZPE log for more information.");
    }

    return false;

  }

  static class connect_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


    @Override
    public String[] getParameterNames() {
      String[] params = new String[5];
      params[0] = "host";
      params[1] = "database";
      params[2] = "user";
      params[3] = "password";
      params[4] = "port";

      return params;
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string", "string", "string", "string", "number"};
    }

    @Override
    public ZPEType MainMethod(JBBinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {
      try {
        String host = parameters.get("host").toString();
        int port = jamiebalfour.HelperFunctions.stringToInteger(parameters.get("port").toString());
        String database = parameters.get("database").toString();
        String username = parameters.get("user").toString();
        String password = parameters.get("password").toString();

        return new ZPEBoolean(((ZPEMySQLObject) parent).connect(host, port, database, username, password));
      } catch (Exception e) {
        return new ZPEBoolean(false);
      }
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    @Override
    public String getName() {
      return "connect";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.BOOLEAN_TYPE};
    }

  }


  class get_tables_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[0];
    }

    @Override
    public ZPEType MainMethod(JBBinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      try {
        return sql.getTableNames();
      } catch (Exception e) {
        return new ZPEBoolean(false);
      }
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 3;
    }

    @Override
    public String getName() {
      return "get_tables";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.LIST_TYPE, YASSByteCodes.BOOLEAN_TYPE};
    }


  }

  class query_to_json_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"query_str"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType MainMethod(JBBinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      try {
        jamiebalfour.zpe.types.ZPEList l = sql.query(parameters.get("query_str").toString());

        jamiebalfour.parsers.json.ZenithJSONParser parser = new jamiebalfour.parsers.json.ZenithJSONParser();

        return new ZPEString(parser.jsonEncode(l));
      } catch (Exception e) {
        return new ZPEBoolean(false);
      }
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    @Override
    public String getName() {
      return "query_to_json";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.STRING_TYPE, YASSByteCodes.BOOLEAN_TYPE};
    }


  }

  class get_columns_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"table"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType MainMethod(JBBinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      try {
        return sql.getColumnNames(parameters.get("table").toString());
      } catch (Exception e) {
        return new ZPEBoolean(false);
      }
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    @Override
    public String getName() {
      return "get_columns";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.LIST_TYPE, YASSByteCodes.BOOLEAN_TYPE};
    }


  }

  class query_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"query_str"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType MainMethod(JBBinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      try {
        return sql.query(parameters.get("query_str").toString());
      } catch (Exception e) {
        return new ZPEBoolean(false);
      }
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    @Override
    public String getName() {
      return "query";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.LIST_TYPE, YASSByteCodes.BOOLEAN_TYPE};
    }

  }

  class prepare_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"query_str"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType MainMethod(JBBinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      try {
        ZPEMySQLPreparedStatementObject prep = new ZPEMySQLPreparedStatementObject(getRuntime(), parent);
        prep.sqlConn = _this;

        prep.prepare(parameters.get("query_str").toString());

        return prep;
      } catch (Exception e) {
        return new ZPEBoolean(false);
      }
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    @Override
    public String getName() {
      return "prepare";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE, YASSByteCodes.BOOLEAN_TYPE};
    }

  }

}