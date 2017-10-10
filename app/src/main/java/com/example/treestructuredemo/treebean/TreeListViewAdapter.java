package com.example.treestructuredemo.treebean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<Node> mNodes;
    protected LayoutInflater mInflater;
    /**
     * 存储所有的Node
     */
    protected List<Node> mAllNodes;

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;

    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position, View view);
    }

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeListViewAdapter(ListView mTree, Context context, List<T> datas,
                               int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        mContext = context;
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        setLastChild();
        /**
         * 过滤出可见的Node
         */
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        mInflater = LayoutInflater.from(context);
    }


    public void updateView( List<T> datas)throws IllegalArgumentException,
            IllegalAccessException {
        List<Node> newData=TreeHelper.getSortedNodes(datas, 0);
        for(Node node:newData){
            if(node.isRoot()){
                if(node.getName().getParentCode()!=null){
                    node.setpId(node.getName().getParentCode());
                }
            }
            node.getChildren().clear();
        }

        for(int i=0;i<newData.size();i++){
            for(int j=0;j<mAllNodes.size();j++){
                if(newData.get(i).getpId().equals(mAllNodes.get(j).getId())){
                    newData.get(i).setParent(mAllNodes.get(j));
                    newData.get(i).setLevel(mAllNodes.get(j).getLevel()+1);
                    int index=getIndex(mAllNodes.get(j).getChildren());
                    if(j+index+1>=mAllNodes.size()){
                        mAllNodes.add(newData.get(i));
                    }else{
                        mAllNodes.add(j+index+1,newData.get(i));
                    }
                    mAllNodes.get(j).getChildren().add(newData.get(i));

                    break;
                }else{
                    if(j==mAllNodes.size()-1){
                        mAllNodes.add(newData.get(i));
                        break;
                    }
                }
            }
        }
        setLastChild();
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        notifyDataSetChanged();
    }

    public void setLastChild(){
        for(Node node:mAllNodes){
            node.setLastChild(false);
        }
        for(int i=0;i<mAllNodes.size();i++){
            if(mAllNodes.get(i).getChildren().size()>0){
                 int count=getLastChild(mAllNodes.get(i));
                 mAllNodes.get(i+count).setLastChild(true);
            }
        }
    }

    public int getLastChild(Node parentNode){
       int count=parentNode.getChildren().size();
        for(int i=0;i<parentNode.getChildren().size()-1;i++){
            count=count+getAllChild(parentNode.getChildren().get(i));
        }
        return count;
    }

    public int getAllChild(Node parentNode){
        int count=parentNode.getChildren().size();
        for(int i=0;i<parentNode.getChildren().size();i++){
            count=count+getAllChild(parentNode.getChildren().get(i));
        }
        return count;
    }

    /**
     * @param list
     * 删除一组node
     */
    public void Delete(List<String> list){
        for(String str:list){
            for(Node node:mAllNodes){
                if(node.getId().equals(str)){
                    if(node.getParent()!=null){
                        node.getParent().getChildren().remove(node);
                    }
                    mAllNodes.remove(node);
                    break;
                }

            }
        }
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        notifyDataSetChanged();
    }

    /**
     * @param code
     * @param name
     * 改变文件名称
     */
    public void Edit(String code,String name){
        for(Node node:mAllNodes){
            if(node.getId().equals(code)){
                node.getName().setName(name);
                break;
            }
        }
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        notifyDataSetChanged();

    }


    /**
     * @param nodes
     * 返回一个node下的所有子类，包括子类的子类
     */
    public int getIndex(List<Node> nodes){
        int count=nodes.size();
        for (Node node:nodes){
            count =count +getIndex(node.getChildren());
        }
        return count;

    }
    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        Node n = mNodes.get(position);

        if (n != null)// 排除传入参数错误异常
        {
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();// 刷新视图
            }
        }
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = mNodes.get(position);
        convertView = getConvertView(node,mNodes, position, convertView, parent);
        return convertView;
    }

    public abstract View getConvertView(Node node,List<Node> mNodes, int position,
                                        View convertView, ViewGroup parent);


}
