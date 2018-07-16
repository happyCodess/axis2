package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	//1.创建静态常量参数值：
	//数据库驱动类所在位置
	private static final String DRIVER="oracle.jdbc.driver.OracleDriver";
	//数据库登陆用户名
	private static final String USERNAME="hsoa";
	//数据库登陆密码
	private static final String PASSWORD="password";
	//数据库服务器所在路径，代表数据库所在IP端口号及数据库名
	private static final String URL="jdbc:oracle:thin:@192.168.3.174:1521/hsoa";
	
	
	
	
	
	//2.加载驱动类,使用class的forname方法。参数为驱动类Driver
	static{
		try {
			//加载数据库驱动包
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			//当驱动加载错误时，抛出加载数据库ERROR异常
			throw new ExceptionInInitializerError();
		}
	}
	
	
	
	
	
	//3.获取数据库连接对象Connection，使用DriverManager获取
	public Connection getConnection() throws SQLException{
		//通过驱动管理类，获取数据库连接对象Connection
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	
	
	
	
	
	//4.创建执行SQL语句的预编译对象PreparedStatement
	public PreparedStatement createPreparedStatement(Connection conn,String sql,Object...params) throws SQLException{
		//（1）预编译sql语句并生成执行对象
		PreparedStatement pstmt=conn.prepareStatement(sql);
		//（2）判断是否有动态参数需要绑定，如果没有动态参数需要绑定，则直接返回PreparedStatement对象
		if(params==null||params.length==0){
			return pstmt;
		}else{
			//如果需要绑定动态参数，则循环绑定，绑定占位符？位置的参数，循环次数为动态参数个数，？位置从1开始，数组索引从0开始
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1,params[i]);
			}
			//返回绑定完毕后的执行对象
			return pstmt;
		}
		
	}
	
	
	
	 
	
	
	//5.创建公共的DML语句执行方法（增删改），参数为需要拼接完成的SQL语句及动态参数数组，返回值为影响行数
	public int executeUpdates(String sql, Object[] params) {
		//（1）创建数据库连接对象
		Connection conn=null;
		//（2）创建数据库执行对象
		PreparedStatement pstmt=null;
		int result=-1;//（3）设置默认执行DML语句时，返回结果为失败
		
		try {
			//获取数据库连接，调用自定义的getconnection方法
			conn = getConnection();
			//获取执行对象，调用自定义的createpreparedstatement方法
			pstmt = createPreparedStatement(conn, sql,params);
			//获取执行结果，返回值为SQL语句的影响行数
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}finally{
			closeAll(conn,pstmt,null);
		}
		
		
		return result;
	}





	//6.关闭数据库连接对象，数据库执行对象，数据库结果集，执行顺序为先开后关
	public void closeAll(Connection conn, PreparedStatement pstmt,
			ResultSet rs) {
		//先判断是否有结果集对象，如果有则关闭，但不管是否关闭成功，都会去尝试进行下一步执行对象关闭步骤
		
			try {
				if(rs!=null){
				rs.close();
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				//先判断是否有执行对象，如果有则关闭，但不管是否关闭成功，都会去尝试进行下一步数据库连接对象关闭步骤
				
					try {
						if(pstmt!=null){
						pstmt.close();
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}finally{
						//先判断是否有数据库连接对象，如果有则关闭
						
							try {
								if(conn!=null){
								conn.close();
								}
							} catch (SQLException e) {
								
								e.printStackTrace();
							}
						}
					}
				}
			
		
		
	}


