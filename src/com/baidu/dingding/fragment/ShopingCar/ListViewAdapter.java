package com.baidu.dingding.fragment.ShopingCar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.biz.BitmapCache;
import com.baidu.dingding.until.LogUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/15.
 */
public class ListViewAdapter extends BaseExpandableListAdapter {
    private List<Node> dataList;
    private LayoutInflater inflater;
    //以选中的子列表项
    private List<String> checkedChildren = new ArrayList<String>();
    //父列表项的选中状态：value值为1（选中）、2（部分选中）、3（未选中）
    private Map<String, Integer> groupCheckedStateMap = new HashMap<String, Integer>();
    Context mContext;

    public ListViewAdapter(Context context, List<Node> dataList) {
        this.mContext=context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public int getGroupCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        final Node node = dataList.get(groupPosition);
        if (node == null || node.getChildNodeList() == null
                || node.getChildNodeList().isEmpty()) {
            return 0;
        }
        return node.getChildNodeList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (dataList == null) {
            return null;
        }
        return dataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        final Node node = dataList.get(groupPosition);
        if (node == null || node.getChildNodeList() == null
                || node.getChildNodeList().isEmpty()) {
            return null;
        }
        return node.getChildNodeList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    final static class NodeViewHolder {
        TextView NodeName;
        CheckBox NodecheckBox;
        Button NodeButton;
        Integer groupPosition;
    }


    final static class ChildNodeViewHolder {
        CheckBox childrenCB;
        NetworkImageView networkImageView;
        TextView goodsName,price,goodsCount;
        TagFlowLayout tagFlowLayout;
        Integer childPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        try {
            Node node = dataList.get(groupPosition);
            NodeViewHolder nodeViewHolder=null;
            if(convertView==null||((NodeViewHolder) convertView.getTag()).groupPosition != groupPosition){
               // nodeViewHolder.groupPosition=groupPosition;
                nodeViewHolder = new NodeViewHolder();
                convertView = inflater.inflate(R.layout.shopingcarnode_item, null);
                nodeViewHolder.NodeName= (TextView) convertView.findViewById(R.id.name);
                nodeViewHolder.NodeButton= (Button) convertView.findViewById(R.id.btn_bianji);
                nodeViewHolder.NodecheckBox= (CheckBox) convertView.findViewById(R.id.shopping_checkbox1);
                convertView.setTag(nodeViewHolder);
            }else{
                nodeViewHolder= (NodeViewHolder) convertView.getTag();
            }
            nodeViewHolder.NodeName.setText(node.getName());
            nodeViewHolder.NodecheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LogUtil.i("Node","isChecked="+isChecked);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    RequestQueue mQueue = TApplication.getInstance().getRequestQueue();
    ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildNode childNode= (ChildNode) getChild(groupPosition,childPosition);
        ChildNodeViewHolder childNodeViewHolder=null;
        if(convertView==null||((NodeViewHolder) convertView.getTag()).groupPosition != groupPosition) {
        childNodeViewHolder.childPosition=childPosition;
            childNodeViewHolder=new ChildNodeViewHolder();
            convertView=inflater.inflate(R.layout.gouwu_list_item,null);
            childNodeViewHolder.childrenCB= (CheckBox) convertView.findViewById(R.id.shopping_checkbox2);
            childNodeViewHolder.networkImageView= (NetworkImageView) convertView.findViewById(R.id.networkImageView);
            childNodeViewHolder.goodsName= (TextView) convertView.findViewById(R.id.textname);
            childNodeViewHolder.price= (TextView) convertView.findViewById(R.id.jiage);
            childNodeViewHolder.goodsCount= (TextView) convertView.findViewById(R.id.count);
            childNodeViewHolder.tagFlowLayout= (TagFlowLayout) convertView.findViewById(R.id.TagFlowLayout);
            convertView.setTag(childNodeViewHolder);
        }else{
           childNodeViewHolder= (ChildNodeViewHolder) convertView.getTag();
        }
        List<String> stringList = new ArrayList<String>();
        try {
            Map<String, String> goodsSpecificationContactStr = (Map<String, String>) childNode.getGoodsSpecificationContactStr();
            LogUtil.i("有多少个子控件", "goodsSpecificationContactStr=" + goodsSpecificationContactStr.getClass().getName());
            for (String key : goodsSpecificationContactStr.keySet()) {
                LogUtil.i("解析mapok", "key=" + key);
                String value = goodsSpecificationContactStr.get(key);
                LogUtil.i("解析mapok", "value=" + value);
                String string = key + ":" + value;
                stringList.add(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        childNodeViewHolder.childrenCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtil.i("child","isChecked="+isChecked);
            }
        });
        childNodeViewHolder.networkImageView.setImageUrl(childNode.getLogpath(), imageLoader);
        childNodeViewHolder.goodsName.setText(childNode.getGoodsName());
        childNodeViewHolder.price.setText("¥"+childNode.getPrice());
        childNodeViewHolder.goodsCount.setText("X"+childNode.getGoodsCount());
        final ChildNodeViewHolder finalChildNodeViewHolder = childNodeViewHolder;
        childNodeViewHolder.tagFlowLayout.setAdapter(new TagAdapter(stringList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                LayoutInflater inflater = LayoutInflater.from(mContext);

                TextView tv = (TextView) inflater.inflate(R.layout.gouwutv,
                        finalChildNodeViewHolder.tagFlowLayout, false);
                tv.setText(o.toString());
                return tv;
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
