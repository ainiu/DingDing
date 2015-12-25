#叮叮工作日志 
##记录人  王茜茜

[目录]
=========================================================================
## 2015/12/13
    - 修改购物车不能单选
    - 重写 <b>Viewhode</b>
代码
=========================================================================
``` java 
  tagFlowLayout.setMaxSelectCount(1); //在xml中定义数量，不能实现
  
```

## 2015/12/14
      - 记录购物车用户的选择
      - 解析json数据插入到数据库
  代码
=========================================================================
  ``` java 
    
    
  ```

## 2015/12/15
      - 重写ExpandbleListView的BaseExpandableListAdapter实现购物车
      - 购物车流程写到笔记本上
      - 重写数据
  代码
=========================================================================
    -设置ExpandableListView 默认是展开的:
    先实例化 exListView
  ``` java 
       exListView.setAdapter(exlvAdapter);
       //遍历所有group,将所有项设置成默认展开
       int groupCount = exListView.getCount();
       for (int i=0; i<groupCount; i++) {
           exListView.expandGroup(i);
           };
        settingLists.setGroupIndicator(null)//去掉箭头
        
  ```
  
  
高级用法
==============================================
表格
  
  |Related |Method             |
  |------:|-------------------|
  |android:childDivider |指定各组内子类表项之间的分隔条 ①|
  |android:childIndicator   |显示在子列表旁边的Drawable对象②|
 | android:childIndicatorLeft| 子列表项指示符的左边约束位置③|
  |android:childIndicatorRight|子列表项指示符的右边约束位置④|
  |android:groupIndicator|显示在组列表旁边的Drawable对象⑤ |
  
  
  
  
####禁止ViewPager滑动 
=========================================================================
     ``` java 
     public class CustomViewPager extends ViewPager {  
       
         private boolean isCanScroll = true;  
       
         public CustomViewPager(Context context) {  
             super(context);  
         }  
       
         public CustomViewPager(Context context, AttributeSet attrs) {  
             super(context, attrs);  
         }  
       
         public void setScanScroll(boolean isCanScroll){  
             this.isCanScroll = isCanScroll;  
         }  
       
       
         @Override  
         public void scrollTo(int x, int y){  
             if (isCanScroll){  
                 super.scrollTo(x, y);  
             }  
         }
           
     ```
     
     
     
============================================================================================= 
####Fragment嵌套Fragment
  //shopingPagerAdapter = new ShopingPagerAdapter(getChildFragmentManager(), fragmentList);
   
  
   
## 2015/12/16
      - 实现购物车
      - 购物车流程写到笔记本上
  代码
=========================================================================
    -设置ExpandableListView 默认是展开的:
    先实例化 exListView
  ``` java 
       exListView.setAdapter(exlvAdapter);
       //遍历所有group,将所有项设置成默认展开
       int groupCount = exListView.getCount();
       for (int i=0; i<groupCount; i++) {
           exListView.expandGroup(i);
           };
        settingLists.setGroupIndicator(null)//去掉箭头
        
  ```
  
  
## 2015/12/17
        - 实现购物车
        - 调试不能删除
    代码
  =========================================================================
      -设置ExpandableListView 默认是展开的:
      先实例化 exListView
    ``` java 
         //删除
                 delete.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         isTokennull();
                         List canglist = new ArrayList();
                         for (int i = 0; i < shopingSelectAdapter.getCount(); i++) {
                             GouwucheBean gouwucheBean = (GouwucheBean) shopingSelectAdapter.getBeanList().get(i);
                             if (gouwucheBean.getId() || gouwucheBean.getPid()) {
                                 canglist.add(gouwucheBean.getShoppingCarId());
                             }
                         }
                         if (canglist.size() >2) {
                             String string=listToString(canglist, ',');
                             LogUtil.i("删除商品id","string="+string);
                             deleteShopingModel.findConsultList(string,token,userNumber);
                             dialog.show();
                         }
                         if(canglist.size()==1){
                             String s=listToString(canglist,',');
                             deleteShopingModel.findConsultList(userNumber,token,s);
                             dialog.show();
                         }
                         if (canglist.size()==0){
                             Toasttool.MyToast(getContext(),"请选择你要删除的商品");
                         }
                         shopingSelectAdapter.notifyDataSetChanged();
                     }
                 });
          
    ```
    
## 2015/12/17
            - 实现购物车
            - 调试不能删除
        代码=========================================================================
          -设置ExpandableListView 默认是展开的:
          先实例化 exListView
        ``` java 
             1)	将dinpayPlugin.jar复制到工程目录libs 下。
             2)	工程的res/drawable目录下如果存在data.bin则删除
             3)	将data.bin文件夹复制到工程目录assets/目录下，已存在则替换。
             
             4)	将armeabi文件夹复制到工程目录libs 下，如果工程libs目录下已有armeabi文件夹，只需将libDinpayEntryKey.so与libentryexstd.so复制到其中即可。
             
             5)	将armeabi-v7a文件夹复制到工程目录libs 下，如果工程libs目录下已有armeabi-v7a文件夹，只需将libDinpayEntryKey.so与libentryexstd.so复制到其中即可。
             
             6)	将mips文件夹复制到工程目录libs 下，如果工程libs目录下已有mips文件夹，只需将libDinpayEntryKey.so与libentryexstd.so复制到其中即可。
             
             7)	将x86文件夹复制到工程目录libs 下，如果工程libs目录下已有x86文件夹，只需将libDinpayEntryKey.so与libentryexstd.so复制到其中即可。
             
             
             8)	在工程目录values 中的styles.xml文件中添加银联控件所需的银联控件样式。具体样式在styles.xml文件中，将其复制进去即可。内容如下：
              
             
             9)	工程目录下libs的基本结构如图：

                  
              
        ```