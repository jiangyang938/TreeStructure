package com.example.treestructuredemo;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.treestructuredemo.treebean.FileBean;
import com.example.treestructuredemo.treebean.Node;
import com.example.treestructuredemo.treebean.TreeListViewAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by mengzhuang on 17/8/14.
 * 项目文件列表
 */
public class FileListAdapter<T> extends TreeListViewAdapter<T> {
    private Context context;
    List<Node> mNodes;
    private List<FileBean> datas;

    private String shareURL;

    Dialog dlg;

    public int OnClickPosition;

    public ListView treeView;

    List<String> deleteList;
    String EditCode;//选中编辑的文件code
    String AgainName;//重命名字符串
    String RemarkStr;
    String newFileName;
    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public FileListAdapter(ListView mTree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
        treeView=mTree;
        this.datas= (List<FileBean>) datas;
        this.context = context;
    }

    private String projectCode;

    /**
     * @param datas
     * 增加一个或多个数据而不用重新设置数据源和默认显示层级
     */
    public void SetData(List<T> datas){
        this.datas.addAll((Collection<? extends FileBean>) datas);
        try {
            updateView(datas);
        } catch (IllegalAccessException e) {

        }
    }

    int clickPostion = -1;

    @Override
    public View getConvertView(final Node node, final List<Node> mNodes, final int position, View convertView,
                               ViewGroup parent) {
        this.mNodes = mNodes;
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_project_filelist,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.ivIcon1 = (ImageView) convertView.findViewById(R.id.img1);


            viewHolder.ivSelect = (CheckBox) convertView.findViewById(R.id.iv_select);
            viewHolder.ivFolder = (ImageView) convertView.findViewById(R.id.iv_folder);

            viewHolder.tvFileName = (TextView) convertView.findViewById(R.id.tv_file_name);

            viewHolder.re_layout = (RelativeLayout) convertView.findViewById(R.id.iv_visibi_gone);
            viewHolder.total_layout = (RelativeLayout) convertView.findViewById(R.id.total_layout);
            viewHolder.view1 = convertView.findViewById(R.id.view1);
            viewHolder.view2 = convertView.findViewById(R.id.view2);
            viewHolder.view3 = convertView.findViewById(R.id.view3);
            viewHolder.view4 = convertView.findViewById(R.id.view4);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mNodes.get(position).setPosition(position);
        final String name = node.getName().getName();
        RelativeLayout.LayoutParams p = null;
        p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < viewHolder.total_layout.getChildCount(); i++) {
            View view = viewHolder.total_layout.getChildAt(i);
            if (view.getTag() != null) {
                if (view.getTag().equals("add")) {
                    viewHolder.total_layout.removeView(view);
                    i--;
                }
            }
        }
        if (node.getLevel() > 2) {
            for (int i = 2; i < node.getLevel(); i++) {
                if(getLine(i,node)){
                    View view = new View(mContext);
                    view.setTag("add");
                    RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(1,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
                    view.setBackgroundColor(Color.parseColor("#4e9afd"));
                    vp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    vp.leftMargin = dp2px(9.7f) + (dp2px(15) + dp2px(9.7f)) * (i - 1);
                    viewHolder.total_layout.addView(view, vp);

                }
            }
        }
        if (node.getParent() != null) {
            p.leftMargin = dp2px(9.7f) + (dp2px(15) + dp2px(9.7f)) * (node.getLevel() - 1);
            viewHolder.re_layout.setLayoutParams(p);
            viewHolder.view1.setVisibility(View.VISIBLE);
            viewHolder.view3.setVisibility(View.VISIBLE);

        } else {
            p.leftMargin = 0;
            viewHolder.re_layout.setLayoutParams(p);
            viewHolder.view1.setVisibility(View.GONE);
            viewHolder.view3.setVisibility(View.GONE);

        }
        if (node.getChildren() == null || node.getChildren().size() <= 0) {
            viewHolder.ivIcon.setVisibility(View.INVISIBLE);
            viewHolder.ivIcon1.setVisibility(View.VISIBLE);
            viewHolder.view2.setVisibility(View.GONE);
        } else if (node.isExpand()) {
            viewHolder.ivIcon.setVisibility(View.VISIBLE);
            viewHolder.ivIcon1.setVisibility(View.GONE);
            viewHolder.ivIcon.setImageResource(R.mipmap.project_closed);
            viewHolder.view2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivIcon.setVisibility(View.VISIBLE);
            viewHolder.ivIcon1.setVisibility(View.GONE);
            viewHolder.ivIcon.setImageResource(R.mipmap.project_spread);
            viewHolder.view2.setVisibility(View.GONE);
        }

        if (position == mNodes.size() - 1) {
            viewHolder.view3.setVisibility(View.GONE);
           // viewHolder.view5.setVisibility(View.GONE);
        }
        if(node.getLevel()>0){
            if(node.isLastChild()){
                viewHolder.view3.setVisibility(View.GONE);
            }
        }

        // addShowLine((RelativeLayout) convertView, node, node);

        viewHolder.re_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
            }
        });

        viewHolder.ivSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    getFileBean(mNodes.get(position).getId()).setCheck(true);
                    onTrueClick(mNodes.get(position).getpId());
                }else{
                    if(onFalseClick(mNodes.get(position).getId()+"")){
                        getFileBean(mNodes.get(position).getId()).setCheck(false);
                    }else{
                        getFileBean(mNodes.get(position).getId()).setCheck(true);
                    }
                }
                notifyDataSetChanged();
            }
        });
        int count= datas.size();
        boolean isSelect = getFileBean(node.getId()).isCheck();
        viewHolder.ivSelect.setChecked(isSelect);
        viewHolder.tvFileName.setText(name);
        String fileName = node.getName().getName();
        if (node.getName().getIsFolder().equals("0")) {
            viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.folder));
        } else {
            if (name.contains(".")) {
                final String des = name.substring(name.lastIndexOf(".") + 1);
                Log.e(">>>>>>>>>>>>", des);
                switch (des) {
                    case "jpg":
                    case "png":
                        viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.png2x));
                        break;
                    case "doc":
                    case "docx":
                        viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.word2x));
                        break;
                    case "zip":
                        viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.zip2x));
                        break;
                    case "ppt":
                        viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ppt2x));
                        break;
                    case "xls":
                    case "xlsx":
                        viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.xls2x));
                        break;
                    case "pdf":
                        viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.pdf2x));
                        break;
                    default:
                        viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.other2x));
                        break;
                }
                viewHolder.tvFileName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            } else {
                viewHolder.tvFileName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                viewHolder.ivFolder.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.other2x));
            }
        }

        return convertView;
    }

    /**
     * @param code
     * @return 获得文件的所有子文件code，加上本身code,组成字符串，以“，”隔开
     */
    private void getAllChildCode(List<String> list, String code) {
        list.add(code);
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getParentId().equals(code)) {
                getAllChildCode(list,datas.get(i).get_id());
            }
        }


    }
    /**
     * @param i
     * @param node
     * @return
     * 判断该node是否显示父类同级的线
     */
    public boolean getLine(int i,Node node){
        int n=node.getLevel()-i;
        while (n!=1){
            node=node.getParent();
            n--;
        }
        if(node.getParent().isLastChild()){
            return false;
        }else{
            return  true;
        }

    }



    public String getSelectCodes(){
        String codes="";
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).isCheck()){
                if(codes.equals("")){
                    codes=datas.get(i).get_id()+"";
                }else{
                    codes=codes+","+datas.get(i).get_id();
                }

            }
        }
        return codes;
    }

    public  List<FileBean> getSelectBeans(){
        List<FileBean> beans=new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).isCheck()){
                if(datas.get(i).getName().getIsFolder().equals("1")){
                    beans.add(datas.get(i));
                }

            }
        }
        return beans;
    }


    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }

    boolean b = false;

    public Node hasChildren(Node node1, Node node2) {
        if (node2 == null) {
            return null;
        }
        if (node2.getParent() != null
                && node1.getName().equals(node2.getParent().getName())) {
            return node2;
        }
        return hasChildren(node1, node2.getParent());
    }

    int idNumber = 9999;

    private FileBean getFileBean(String id){
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).get_id().equals(id)){
                return  datas.get(i);
            }
        }
        return null;
    }

    //子文件点击选中时，直属父文件全选中
    private void onTrueClick(String pCode){
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).get_id().equals(pCode)){
                datas.get(i).setCheck(true);
                onTrueClick(datas.get(i).getParentId());
            }
        }
    }

    //点击取消选中时，判断子文件是否是选中状态，如果子文件是选中状态，不允许取消选中
    private boolean onFalseClick(String code){
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).getName().getParentCode().equals(code)){
                if(datas.get(i).isCheck()){
                    return  false;
                }
            }
        }
        return true;

    }



    class ViewHolder {
        RelativeLayout re_layout;
        RelativeLayout total_layout;
        View view1;
        View view2;
        View view3;
        View view4;
        ImageView ivIcon;
        ImageView ivIcon1;


        ImageView ivMore;

        ImageView ivLock;

        CheckBox ivSelect;

        TextView tvFileName;

        ImageView ivFolder;

    }

    private int leftMargin_ = 8;

    private int leftMargin = dp2px(leftMargin_);

    private int topMargin_ = 5;

    private int topMargin = dp2px(topMargin_);


    private int lineHeight_ = 20;
    private int lineHeight = dp2px(lineHeight_);
}
