package service.impl;

import dao.impl.FixedAssetsDaoImpl;



public class FixedAssetsServiceImpl{

	//���ݷ��ʲ����
	FixedAssetsDaoImpl fixedAssetsDao=new FixedAssetsDaoImpl();
	//����
	public String saveFixedAsset(String json){ 

		return fixedAssetsDao.saveFixedAsset(json);
	}
		

}
