package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	//1.������̬��������ֵ��
	//���ݿ�����������λ��
	private static final String DRIVER="oracle.jdbc.driver.OracleDriver";
	//���ݿ��½�û���
	private static final String USERNAME="hsoa";
	//���ݿ��½����
	private static final String PASSWORD="password";
	//���ݿ����������·�����������ݿ�����IP�˿ںż����ݿ���
	private static final String URL="jdbc:oracle:thin:@192.168.3.174:1521/hsoa";
	
	
	
	
	
	//2.����������,ʹ��class��forname����������Ϊ������Driver
	static{
		try {
			//�������ݿ�������
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			//���������ش���ʱ���׳��������ݿ�ERROR�쳣
			throw new ExceptionInInitializerError();
		}
	}
	
	
	
	
	
	//3.��ȡ���ݿ����Ӷ���Connection��ʹ��DriverManager��ȡ
	public Connection getConnection() throws SQLException{
		//ͨ�����������࣬��ȡ���ݿ����Ӷ���Connection
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	
	
	
	
	
	//4.����ִ��SQL����Ԥ�������PreparedStatement
	public PreparedStatement createPreparedStatement(Connection conn,String sql,Object...params) throws SQLException{
		//��1��Ԥ����sql��䲢����ִ�ж���
		PreparedStatement pstmt=conn.prepareStatement(sql);
		//��2���ж��Ƿ��ж�̬������Ҫ�󶨣����û�ж�̬������Ҫ�󶨣���ֱ�ӷ���PreparedStatement����
		if(params==null||params.length==0){
			return pstmt;
		}else{
			//�����Ҫ�󶨶�̬��������ѭ���󶨣���ռλ����λ�õĲ�����ѭ������Ϊ��̬������������λ�ô�1��ʼ������������0��ʼ
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1,params[i]);
			}
			//���ذ���Ϻ��ִ�ж���
			return pstmt;
		}
		
	}
	
	
	
	 
	
	
	//5.����������DML���ִ�з�������ɾ�ģ�������Ϊ��Ҫƴ����ɵ�SQL��估��̬�������飬����ֵΪӰ������
	public int executeUpdates(String sql, Object[] params) {
		//��1���������ݿ����Ӷ���
		Connection conn=null;
		//��2���������ݿ�ִ�ж���
		PreparedStatement pstmt=null;
		int result=-1;//��3������Ĭ��ִ��DML���ʱ�����ؽ��Ϊʧ��
		
		try {
			//��ȡ���ݿ����ӣ������Զ����getconnection����
			conn = getConnection();
			//��ȡִ�ж��󣬵����Զ����createpreparedstatement����
			pstmt = createPreparedStatement(conn, sql,params);
			//��ȡִ�н��������ֵΪSQL����Ӱ������
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}finally{
			closeAll(conn,pstmt,null);
		}
		
		
		return result;
	}





	//6.�ر����ݿ����Ӷ������ݿ�ִ�ж������ݿ�������ִ��˳��Ϊ�ȿ����
	public void closeAll(Connection conn, PreparedStatement pstmt,
			ResultSet rs) {
		//���ж��Ƿ��н���������������رգ��������Ƿ�رճɹ�������ȥ���Խ�����һ��ִ�ж���رղ���
		
			try {
				if(rs!=null){
				rs.close();
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				//���ж��Ƿ���ִ�ж����������رգ��������Ƿ�رճɹ�������ȥ���Խ�����һ�����ݿ����Ӷ���رղ���
				
					try {
						if(pstmt!=null){
						pstmt.close();
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}finally{
						//���ж��Ƿ������ݿ����Ӷ����������ر�
						
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


