package service.impl;

import dao.impl.FixedAssetsDaoImpl;



public class FixedAssetsServiceImpl{

	//数据访问层对象
	FixedAssetsDaoImpl fixedAssetsDao=new FixedAssetsDaoImpl();
	//保存
	public String saveFixedAsset(String json){ 

		return fixedAssetsDao.saveFixedAsset(json);
	}
		

}
