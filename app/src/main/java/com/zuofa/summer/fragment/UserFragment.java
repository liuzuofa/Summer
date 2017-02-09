package com.zuofa.summer.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyPagerAdapter;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.utils.L;
import com.zuofa.summer.view.CustomDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.ProgressCallback;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener{

    private View view;
    private CircleImageView user_photo;
    private CustomDialog customDialog;
    private TextView takePhoto;
    private TextView choosePhoto;
    private TextView btn_cancel;

    private BmobFile bmobBackFile;
    private User user;

    public static UserFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString("argument", argument);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        return view;
    }

    private void initView() {
        Intent intent = this.getActivity().getIntent();
        user = (User) intent.getSerializableExtra("user");
        user_photo = (CircleImageView) view.findViewById(R.id.user_photo);
        user_photo.setOnClickListener(this);

        customDialog = new CustomDialog(getActivity(), 100, 100,
                R.layout.dialog_photo, R.style.ActionSheetDialogStyle, Gravity.BOTTOM, R.style.ActionSheetDialogAnimation);
        customDialog.setCancelable(false);

        //dialog初始化
        takePhoto = (TextView) customDialog.findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(this);
        choosePhoto = (TextView) customDialog.findViewById(R.id.choosePhoto);
        choosePhoto.setOnClickListener(this);
        btn_cancel = (TextView) customDialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_photo:
                customDialog.show();
                break;
            case R.id.btn_cancel:
                customDialog.dismiss();
                break;
            case R.id.takePhoto:
                toCamera();
                break;
            case R.id.choosePhoto:
                toPhoto();
                break;
        }

    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        customDialog.dismiss();
    }

    //跳转相册
    private void toPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        customDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            //将bitmap保存到本地
            File file = new File(Environment.getExternalStorageDirectory(),"profile.jpeg");
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                L.e("已经保存");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {

                }
            });
            Toast.makeText(UserFragment.this.getContext(),
                    "上传文件成功:" + bmobFile.getUrl(),Toast.LENGTH_SHORT).show();

            L.e("上传文件成功");
            user_photo.setImageBitmap(bitmap);
        }
    }

    private void updateDate(String date) {
        user.setProfile(date);
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    L.e("更新成功");
                }else{
                    L.e("更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }


}