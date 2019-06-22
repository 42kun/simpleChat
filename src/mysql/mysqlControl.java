package mysql;

import java.sql.*;



public class mysqlControl {
	private String url="jdbc:mysql://localhost:3306/work?useSSL=false&serverTimezone=GMT";
	private String user="root";
	private String password="SHANGji@aaa123www";
	//连接数据库
	public Connection getConnection() {
		Connection con = null;                                                                                                           
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
		}catch (Exception e) {
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}
		return con;
	}

	//插入值，通过Object数组
	public void insertValue(String tableName,Object[] data) {
		Connection con = getConnection();
		
		int len = data.length;
		String sql = "insert into "+tableName+" values(";
		for(int i=0;i<len;i++) {
			sql += "?,";
		}
		sql = sql.substring(0,sql.length()-1)+");";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			for(int i=1;i<=len;i++) {
				String str = ""; //注意，如果出现空值，那么它最好是string类型，否则大概率报错
				if(data[i-1]!=null) {
					str = data[i-1].toString();
				}
				pst.setString(i, str);
			}
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("通过Object数组插入值错误");
			e.printStackTrace();
		}
	}
	
	//获取值，输入表名主键名主键值，返回string数组(若主键值不存在，则返回空)
	public String[] getValueArr(String tableName,String primaryKey,String primaryKeyValue) {
		Connection con = getConnection();
		String[] resultArray = null;
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from "+tableName+" where "+primaryKey+"='"+primaryKeyValue+"';";
			ResultSet resultSet = stmt.executeQuery(sql);
			if(resultSet.next()) {
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int colCount = rsmd.getColumnCount();
				resultArray = new String[colCount];
				for(int i=1;i<=colCount;i++) {
					resultArray[i-1] = resultSet.getString(i); 
				}
			}else {
				System.out.println("数据不存在");
				System.out.println("tableName:"+tableName);
				System.out.println("primaryKey:"+primaryKey);
				System.out.println("primaryKeyValue:"+primaryKeyValue);
				System.out.println("/数据不存在");
			}
			stmt.close();
			con.close();
		}catch (Exception e) {
			System.out.println("获取值，输入表名主键名主键值，返回string数组(若主键值不存在，则返回空)发生错误");
			e.printStackTrace();
		}
		return resultArray;
	}
	
	//验证某个表内是否存在某个值
	public boolean checkValue(String tableName,String key,String value) {
		Connection con = getConnection();
		boolean result=false;
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from "+tableName+" where "+key+"='"+value+"';";
			ResultSet resultSet = stmt.executeQuery(sql);
			if(resultSet.next()) {
				result=true;
			}
			System.out.println("my:连接关闭");
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("验证表内是否有值发生错误");
			e.printStackTrace();
		}
		return result;
	}
	
	//通过表名，主键名获取某个其他的值
	public String getValue(String tableName,String primaryKey,String primaryKeyValue,String keyName) {
		Connection con = getConnection();
		String value=null;
		try {
			Statement statement = con.createStatement();
			String sql = "select * from "+tableName+" where "+primaryKey+"='"+primaryKeyValue+"';";
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				value = resultSet.getObject(keyName).toString();
			}else {
				value = null;
				System.out.println("数据不存在");
				System.out.println("tableName:"+tableName);
				System.out.println("primaryKey:"+primaryKey);
				System.out.println("primaryKeyValue:"+primaryKeyValue);
				System.out.println("keyName:"+keyName);
				System.out.println("/数据不存在");
			}
			statement.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("通过表名，主键名获取某个其他的值时发生错误");
			e.printStackTrace();
		}
		return value;
	}
	
	//修改某个值，通过表名，主键名，主键值，键值名,新键值
	public void alertValue(String tableName,String primaryKey,String primaryKeyValue,String keyName,String value) {
		Connection con = getConnection();
		try {
			String sql = "update "+tableName+" set "+keyName+"= ? where "+primaryKey+"=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt. setString(1, value);
			stmt.setString(2, primaryKeyValue);
			stmt.execute();
			con.close();
			stmt.close();
		}catch (Exception e) {
			System.out.println("修改某个值，通过表名，主键名，主键值，键值名,新键值时发生错误");
			e.printStackTrace();
		}
	}
	//获得查询到的所有结果的string数组，通过表名，键名，键值，所需键名
	public String[] getResList(String tableName,String keyName,String keyValue,String needKeyName){
		Connection con = getConnection();
		String[] res = null;
		try {
			String sql = "select "+needKeyName+" from "+tableName+" where "+keyName+"="+keyValue+";";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			res = new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()) {
				res[i] = rs.getString(1);
				i++;
			}
			rs.close();
			stmt.close();
			con.close();
		}catch (Exception e) {
			System.out.println("获得查询到的所有结果的string数组，通过表名，键名，键值，所需键名时发生错误");
			e.printStackTrace();
		}
		return res;
	}
	
	public String[] getResList(String tableName,String needKeyName){
		Connection con = getConnection();
		String[] res = null;
		try {
			String sql = "select "+needKeyName+" from "+tableName+";";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			res = new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()) {
				res[i] = rs.getString(1);
				i++;
			}
			rs.close();
			stmt.close();
			con.close();
		}catch (Exception e) {
			System.out.println("获得查询到的所有结果的string数组，通过表名，键名");
			e.printStackTrace();
		}
		return res;
	}
	
	//获取所有结果,通过判断sql语句,获取对应的所有搜索值的所有记录(返回二维String数组)
	public String[][] getResListSql(String tableName,String keySql){
		Connection connection = getConnection();
		String sql = "select * from "+tableName+" where "+keySql+";";
		System.out.println(sql);
		String[][] res = null;
		try{
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			int col = rs.getMetaData().getColumnCount();
			rs.last();
			int row = rs.getRow();
			rs.beforeFirst();
			res = new String[row][col];
			int i=0;
			while(rs.next()) {
				for(int j=0;j<col;j++) {
					res[i][j] = rs.getObject(j+1).toString(); 
				}
				i++;
			}
		}catch (Exception e) {
			System.out.println("获取所有结果,通过判断sql语句,获取对应的所有搜索值的所有记录(返回二维String数组)时发生错误");
			e.printStackTrace();
		}
		return res;
	}
}
