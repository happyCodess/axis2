package dao.impl;

import java.util.Date;

import utils.BaseDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class FixedAssetsDaoImpl extends BaseDao{
	//解析json后以资产编号和公司为条件进行删除，删除后进行插入
	public String saveFixedAsset(String json){
		JSONObject jsonObject=JSONObject.fromObject(json);	
		JSONArray jsonArray=jsonObject.getJSONArray("data");
		String result="";
		System.out.println("开始时间:"+new Date());
		if(jsonArray.size()>0){
			 for(int i=0;i<jsonArray.size();i++){
				 JSONObject job = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				  //根据assetid和company进行删除
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
		System.out.println("结束时间:"+new Date());
				return result;
			  }
			 
					
		
			  
		
			
		
	//删除数据
	private int delFixedAsset(JSONObject job) {
	//编写可被预编译的sql语句
	String sql="delete from SAP_FIXEDASSET where asset_id=? and company=?";
	//组装动态参数列表数组
	//Object[] params={fixedAssets.getAssetId(),fixedAssets.getAssetName(),fixedAssets.getSpec(),fixedAssets.getDeviceNo(),fixedAssets.getAccountDate(),fixedAssets.getOriginalValue(),fixedAssets.getDepreciationValue()};
	Object[] params={job.getString("assetId"),job.getString("company")};
	//调用BaseDao中公有的DML语句执行方法executeUpdate
				
		return executeUpdates(sql, params);
		
	}
	
	//添加数据
		private int saveFix(JSONObject job) {
		//编写可被预编译的sql语句
		String sql="insert into SAP_FIXEDASSET(asset_id,company,asset_name,spec,device_no,account_date,original_value,depreciation_value,insert_date) values (?,?,?,?,?,to_date(?,'yyyy-MM-dd'),?,?,SYSDATE)";
		//组装动态参数列表数组
		//Object[] params={fixedAssets.getAssetId(),fixedAssets.getAssetName(),fixedAssets.getSpec(),fixedAssets.getDeviceNo(),fixedAssets.getAccountDate(),fixedAssets.getOriginalValue(),fixedAssets.getDepreciationValue()};
		Object[] params={job.getString("assetId"),job.getString("company"),job.getString("assetName"),job.getString("spec"),job.getString("deviceNo"),job.getString("accountDate"),Float.parseFloat(job.getString("originalValue")),Float.parseFloat(job.getString("depreciationValue"))};
		//调用BaseDao中公有的DML语句执行方法executeUpdate
					
			return executeUpdates(sql, params);
			
		}
		/*//修改数据
		private int updateMaster(JSONObject job) {
			//编写可被预编译的sql语句
			String sql="update SAP_FIXEDASSET set asset_name =?,spec=?,device_no=?,account_date=to_date(?,'yyyy-MM-dd'),original_value=?,depreciation_value=?,update_date=SYSDATE where asset_id=?";
			//组装动态参数列表数组
			Object[] params={job.getString("assetName"),job.getString("spec"),job.getString("deviceNo"),job.getString("accountDate"),Float.parseFloat(job.getString("originalValue")),Float.parseFloat(job.getString("depreciationValue")),job.getString("assetId")};
			//调用BaseDao中公有的DML语句执行方法executeUpdate
			return executeUpdates(sql, params);
		}
		//修改前查询
		private int selectById(String id) {
			//拼接SQL语句
			String sql="select count(1)  from SAP_FIXEDASSET where asset_id=?";
			//组装参数数组
			Object[] params={id};
			//创建数据库连接对象
			Connection conn = null;
			//创建数据库执行对象
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
