package com.ghsh.activity;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.ghsh.Constants;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TShop;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.ShopModel;
import com.ghsh.R;

/**
 * 高德 店铺地图
 * */
public class MapGaoDeActivity extends BaseActivity implements DResponseListener,OnClickListener,RouteSearch.OnRouteSearchListener,AMapLocationListener, LocationSource{

	private TextView titleView;
	private ShopModel shopModel;
	private AMap aMap;
	private MapView mMapView = null;
	private RouteSearch routeSearch;
	private LocationManagerProxy mLocationManagerProxy;//定位
	private OnLocationChangedListener mListener;
	private Button busDefaultButton,drivingDefaultButton,walkDefaultButton;
	private LatLonPoint startPoint, endPoint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_gaode);
		MapsInitializer.sdcardDir =Constants.getGaoDeSdCacheDir(this);
		mMapView = (MapView) findViewById(R.id.gaode_map);
		mMapView.onCreate(savedInstanceState);// 必须要写
		this.initView();
		this.initListener();
		shopModel=new ShopModel(this);
		shopModel.addResponseListener(this);
		shopModel.getCoordinate();
	}

	private void initView() {
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText("店铺地图");
		
		busDefaultButton= (Button) this.findViewById(R.id.button_busDefault);
		drivingDefaultButton= (Button) this.findViewById(R.id.button_drivingDefault);
		walkDefaultButton= (Button) this.findViewById(R.id.button_walkDefault);
		
		aMap = mMapView.getMap();
		aMap.setLocationSource(this);// 设置定位监听
	    aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
	    aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
	        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
	    aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	        
		routeSearch = new RouteSearch(this);
		routeSearch.setRouteSearchListener(this);
		
		 //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法     
        //其中如果间隔时间为-1，则定位只定一次
//		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
//		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60*1000, 15, this);
//	    mLocationManagerProxy.setGpsEnable(false);
		
	}
	
	private void initListener(){
		busDefaultButton.setOnClickListener(this);
		drivingDefaultButton.setOnClickListener(this);
		walkDefaultButton.setOnClickListener(this);
	}
	
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_GET){
				//获取经纬度信息成功
				TShop shop=(TShop)bean.getObject();
				if(shop==null){
					this.showShortToast("定位失败，请重新操作！");
					return;
				}
				
				LatLng latng=new LatLng((Float.valueOf(shop.getLatitude())), (Float.valueOf(shop.getLongitude())));
				Marker startMk = aMap.addMarker(new MarkerOptions()
													.anchor(0.5f, 1)
													.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_baidu_location_icon))
													.position(latng)
													.title(shop.getShopName())
													.snippet(shop.getDesc()));
				startMk.showInfoWindow();
				aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latng, 18, 0, 30)), 1000, null);
				endPoint= new LatLonPoint(latng.latitude, latng.longitude);
				return;
			}
		}
		this.showShortToast(message);
	}


	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		this.deactivate();
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v) {
		if(startPoint==null){
			this.showShortToast("定位失败，请重新定位!");
			return;
		}
//		this.showShortToast(startPoint+"=="+endPoint);
//		Log.i("=================", startPoint+"=="+endPoint);
		
//		startPoint=new LatLonPoint(Double.parseDouble("40.106901"), Double.parseDouble("116.413933"));
//		endPoint=new LatLonPoint(Double.parseDouble("40.034073"), Double.parseDouble("116.469551"));
		if(busDefaultButton==v){
			//公交
			this.searchRouteResult(RouteSearch.BusDefault,startPoint,endPoint);
		}else if(drivingDefaultButton==v){
			//驾车
			this.searchRouteResult(RouteSearch.DrivingDefault,startPoint,endPoint);
		}else if(walkDefaultButton==v){
			//步行
			this.searchRouteResult(RouteSearch.WalkDefault,startPoint,endPoint);
		}

	}
	//开始搜索路径规划方案
	public void searchRouteResult(int searchType,LatLonPoint startPoint, LatLonPoint endPoint) {
		showProgressDialog();
		final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
		if (searchType == RouteSearch.BusDefault) {// 公交路径规划
			BusRouteQuery query = new BusRouteQuery(fromAndTo, RouteSearch.BusDefault, "北京", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
			routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
		} else if (searchType == RouteSearch.DrivingDefault) {// 驾车路径规划
			DriveRouteQuery query = new DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault,null, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
			routeSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
		} else if (searchType == RouteSearch.WalkDefault) {// 步行路径规划
			WalkRouteQuery query = new WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
			routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
		}
	}
	private ProgressDialog progDialog = null;// 搜索时进度条
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在搜索");
		progDialog.show();
	}
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	
	@Override
	public void onBusRouteSearched(BusRouteResult result, int rCode) {
		//公交路线查询回调
		dissmissProgressDialog();
		if (rCode == 0) {
			if (result != null && result.getPaths() != null&& result.getPaths().size() > 0) {
				BusPath busPath = result.getPaths().get(0);
				aMap.clear();// 清理地图上的所有覆盖物
				BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap,busPath, result.getStartPos(),result.getTargetPos());
				routeOverlay.removeFromMap();
				routeOverlay.addToMap();
				routeOverlay.zoomToSpan();
			} else {
				this.showShortToast("没有搜索到相关数据!");
			}
		} else if (rCode == 27) {
			this.showShortToast("搜索失败,请检查网络连接！");
		} else if (rCode == 32) {
			this.showShortToast("key验证无效！");
		} else {
			this.showShortToast("未知错误，请稍后重试!错误码为"+ rCode);
		}
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
		//驾车结果回调
		dissmissProgressDialog();
		if (rCode == 0) {
			if (result != null && result.getPaths() != null&& result.getPaths().size() > 0) {
				DrivePath drivePath = result.getPaths().get(0);
				aMap.clear();// 清理地图上的所有覆盖物
				DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(this, aMap, drivePath, result.getStartPos(),result.getTargetPos());
				drivingRouteOverlay.removeFromMap();
				drivingRouteOverlay.addToMap();
				drivingRouteOverlay.zoomToSpan();
			} else {
				this.showShortToast("没有搜索到相关数据!");
			}
		} else if (rCode == 27) {
			this.showShortToast("搜索失败,请检查网络连接！");
		} else if (rCode == 32) {
			this.showShortToast("key验证无效！");
		} else {
			this.showShortToast("未知错误，请稍后重试!错误码为"+ rCode);
		}
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
		//步行路线结果回调
		dissmissProgressDialog();
		if (rCode == 0) {
			if (result != null && result.getPaths() != null&& result.getPaths().size() > 0) {
				WalkPath walkPath = result.getPaths().get(0);
				aMap.clear();// 清理地图上的所有覆盖物
				WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this,aMap, walkPath, result.getStartPos(),result.getTargetPos());
				walkRouteOverlay.removeFromMap();
				walkRouteOverlay.addToMap();
				walkRouteOverlay.zoomToSpan();
			} else if (rCode == 27) {
				this.showShortToast("搜索失败,请检查网络连接！");
			} else if (rCode == 32) {
				this.showShortToast("key验证无效！");
			} else {
				this.showShortToast("未知错误，请稍后重试!错误码为"+ rCode);
			}
		}
	}

	
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                startPoint=new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
            }
        }
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		//激活定位
		 mListener = listener;
        if (mLocationManagerProxy == null) {
        	mLocationManagerProxy = LocationManagerProxy.getInstance(this);
            //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
            //在定位结束后，在合适的生命周期调用destroy()方法     
            //其中如果间隔时间为-1，则定位只定一次
            mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
        }
	}
	
	@Override
	public void deactivate() {
		//停止定位
		 mListener = null;
        if (mLocationManagerProxy != null) {
        	mLocationManagerProxy.removeUpdates(this);
        	mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
	}
	@Override
	public void onLocationChanged(Location amapLocation) {
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	@Override
	public void onProviderEnabled(String provider) {
	}
	@Override
	public void onProviderDisabled(String provider) {
	}
}
