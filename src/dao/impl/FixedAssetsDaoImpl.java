package dao.impl;

import java.util.Date;

import utils.BaseDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class FixedAssetsDaoImpl extends BaseDao{
	//����json�����ʲ���ź͹�˾Ϊ��������ɾ����ɾ������в���
	public String saveFixedAsset(String json){
		JSONObject jsonObject=JSONObject.fromObject(json);	
		JSONArray jsonArray=jsonObject.getJSONArray("data");
		String result="";
		System.out.println("��ʼʱ��:"+new Date());
		if(jsonArray.size()>0){
			 for(int i=0;i<jsonArray.size();i++){
				 JSONObject job = jsonArray.getJSONObject(i);  // ���� jsonarray ���飬��ÿһ������ת�� json ����
				  //����assetid��company����ɾ��
				  int results=delFixedAsset(job);
				if(results>=0){
				  int rs= saveFix(job); 
				  if(rs>0){
					  result="true";
				  }else{
					  result="false";
				  }
				}else{
					 result="false";
				}
			
			 }
			
					
				}
		System.out.println("����ʱ��:"+new Date());
				return result;
			  }
			 
					
		
			  
		
			
		
	//ɾ������
	private int delFixedAsset(JSONObject job) {
	//��д�ɱ�Ԥ�����sql���
	String sql="delete from SAP_FIXEDASSET where asset_id=? and company=?";
	//��װ��̬�����б�����
	//Object[] params={fixedAssets.getAssetId(),fixedAssets.getAssetName(),fixedAssets.getSpec(),fixedAssets.getDeviceNo(),fixedAssets.getAccountDate(),fixedAssets.getOriginalValue(),fixedAssets.getDepreciationValue()};
	Object[] params={job.getString("assetId"),job.getString("company")};
	//����BaseDao�й��е�DML���ִ�з���executeUpdate
				
		return executeUpdates(sql, params);
		
	}
	
	//�������
		private int saveFix(JSONObject job) {
		//��д�ɱ�Ԥ�����sql���
		String sql="insert into SAP_FIXEDASSET(asset_id,company,asset_name,spec,device_no,account_date,original_value,depreciation_value,insert_date) values (?,?,?,?,?,to_date(?,'yyyy-MM-dd'),?,?,SYSDATE)";
		//��װ��̬�����б�����
		//Object[] params={fixedAssets.getAssetId(),fixedAssets.getAssetName(),fixedAssets.getSpec(),fixedAssets.getDeviceNo(),fixedAssets.getAccountDate(),fixedAssets.getOriginalValue(),fixedAssets.getDepreciationValue()};
		Object[] params={job.getString("assetId"),job.getString("company"),job.getString("assetName"),job.getString("spec"),job.getString("deviceNo"),job.getString("accountDate"),Float.parseFloat(job.getString("originalValue")),Float.parseFloat(job.getString("depreciationValue"))};
		//����BaseDao�й��е�DML���ִ�з���executeUpdate
					
			return executeUpdates(sql, params);
			
		}
		/*//�޸�����
		private int updateMaster(JSONObject job) {
			//��д�ɱ�Ԥ�����sql���
			String sql="update SAP_FIXEDASSET set asset_name =?,spec=?,device_no=?,account_date=to_date(?,'yyyy-MM-dd'),original_value=?,depreciation_value=?,update_date=SYSDATE where asset_id=?";
			//��װ��̬�����б�����
			Object[] params={job.getString("assetName"),job.getString("spec"),job.getString("deviceNo"),job.getString("accountDate"),Float.parseFloat(job.getString("originalValue")),Float.parseFloat(job.getString("depreciationValue")),job.getString("assetId")};
			//����BaseDao�й��е�DML���ִ�з���executeUpdate
			return executeUpdates(sql, params);
		}
		//�޸�ǰ��ѯ
		private int selectById(String id) {
			//ƴ��SQL���
			String sql="select count(1)  from SAP_FIXEDASSET where asset_id=?";
			//��װ��������
			Object[] params={id};
			//�������ݿ����Ӷ���
			Connection conn = null;
			//�������ݿ�ִ�ж���
			PreparedStatement pstmt = null;
			ResultSet rs= null;
			int  row = 0;
			try {
				conn = super.getConnection();
				pstmt = super.createPreparedStatement(conn, sql,params);
				rs = pstmt.executeQuery();
			
				while(rs.next()){
					row=rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				super.closeAll(conn, pstmt, rs);
			}
			return row;
			
		}*/
}
