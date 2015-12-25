package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.Constants;
import com.ghsh.code.bean.TGoodsCategory;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.util.FileUtils;
/**
 * 商品分类
 * */
public class GoodsCategoryModel extends DVolleyModel{
	private final String goodsCategory_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=category&m=findAll");
	private GoodsCategoryListResponse goodsCategoryListResponse=new GoodsCategoryListResponse(this);
	
	private String cacheDataPath;//本地缓存数据
	
	public GoodsCategoryModel(Context context) {
		super(context);
		cacheDataPath=Constants.getCachePath(context)+"/"+"goodsCategoryData.data";
	}
	/**查询所有分类*/
	public void findGoodsCategoryList(){
		Map<String, String> params = this.newParams();  
		DVolley.get(goodsCategory_list_URL, params,goodsCategoryListResponse,true,cacheDataPath);
	}
	private class GoodsCategoryListResponse extends DResponseService{
		public GoodsCategoryListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TGoodsCategory> categoryList=new ArrayList<TGoodsCategory>();
			JSONArray array=callResult.getContentArray();
			if(array!=null&&array.length()!=0){
				for(int i=0;i<array.length();i++){
					JSONObject categoryObject=array.getJSONObject(i);
					String categoryID=JSONUtil.getString(categoryObject,"cat_id");//分类编号
					String name=JSONUtil.getString(categoryObject,"cat_name");//分类名称
					String desc=JSONUtil.getString(categoryObject,"item_desc");//是否显示
					String imagePath=JSONUtil.getString(categoryObject,"cat_ico");//分类图片文件夹
					JSONArray itemCategory=JSONUtil.getJSONArray(categoryObject,"item_category");//分类图片文件夹
					
					TGoodsCategory category =new TGoodsCategory();
					category.setCategoryID(categoryID);
					category.setName(name);
					category.setDesc(desc);
					category.setImagePath(DVolleyConstans.getServiceHost(imagePath));
					
					if(itemCategory!=null&&itemCategory.length()!=0){
						for(int j=0;j<itemCategory.length();j++){
							JSONObject categoryItemObject=itemCategory.getJSONObject(j);
							String categoryIDItem=JSONUtil.getString(categoryItemObject,"cat_id");//分类编号
							String nameItem=JSONUtil.getString(categoryItemObject,"cat_name");//分类名称
							String descItem=JSONUtil.getString(categoryItemObject,"item_desc");//是否显示
							String imagePathItem=JSONUtil.getString(categoryItemObject,"cat_ico");//分类图片文件夹
							
							TGoodsCategory categoryItem =new TGoodsCategory();
							categoryItem.setCategoryID(categoryIDItem);
							categoryItem.setName(nameItem);
							categoryItem.setDesc(descItem);
							categoryItem.setImagePath(DVolleyConstans.getServiceHost(imagePathItem));
							category.getItemsList().add(categoryItem);
						}
					}
					categoryList.add(category);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(categoryList);
			
			if(!callResult.isCache()){
				//不是缓存，保存在本地
				FileUtils.writeStr(cacheDataPath, callResult.getResponse()+"");
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
