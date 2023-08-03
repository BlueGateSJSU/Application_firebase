package com.bluegate.autodoor.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluegate.autodoor.R;
import com.bluegate.autodoor.Uploader;
import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {
    ImageButton back_btn;

    //프로필 사진
    ImageButton profile_btn;
    ImageView profile_img;

    //이름
    EditText edit_name;

    //학습 사진
    ImageView images;
    ImageButton images_btn;

    //등록
    Button register_btn;

    //프로필 이미지
    private Uri selectedImageUri;
    //학습 사진
    //private ArrayList<Uri> selectedImageUris = new ArrayList<Uri>(); ;

    private static final int GALLERY_REQUEST_CODE_1 = 1001; //프로필 사진 등록
    //private static final int GALLERY_REQUEST_CODE_2 = 1002; //학습 사진 등록

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //뒤로가기
        back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //프로필 이미지 등록
        profile_btn = findViewById(R.id.plus_button);
        profile_img = findViewById(R.id.image_register);

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE_1);
            }
        });

        //이름
        edit_name = findViewById(R.id.text_name);

//        //학습할 사진 등록
//        images_btn = findViewById(R.id.upload_button);
//        images = findViewById(R.id.upload_images);
//        images_btn.setVisibility(View.VISIBLE);
//        images_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE_2);
//            }
//        });

        //펫 등록
        register_btn = findViewById(R.id.upload_complete);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edit_name.getText().toString();
                Uploader uploader = new Uploader(name, profile_img);
                uploader.uploadPet();
            }
        });
    }

    //갤러리 사진 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == GALLERY_REQUEST_CODE_1) {
                // 선택된 이미지 가져오기
                selectedImageUri = data.getData();
                try {
                    // Uri에서 Bitmap으로 변환
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    // 이미지 뷰에 설정
                    profile_img.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            } else if (requestCode == GALLERY_REQUEST_CODE_2) {
//                if (selectedImageUris == null) {
//                    selectedImageUris = new ArrayList<Uri>();
//                } else {
//                    selectedImageUris.clear();
//                }
//
//                // 선택한 이미지 URI 가져오기 (다중 선택)
//                if (data.getClipData() != null) {
//                    int count = data.getClipData().getItemCount();
//                    for (int i = 0; i < count; i++) {
//                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        selectedImageUris.add(imageUri);
//                    }
//                } else if (data.getData() != null) {
//                    // 하나만 선택한 경우
//                    Uri imageUri = data.getData();
//                    selectedImageUris.add(imageUri);
//                }
//
//                //이미지 뷰에 설정
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUris.get(4)); //가운데 사진을 대표 사진으로 가져옴
//                    images.setImageBitmap(bitmap);
//                    images_btn.setVisibility(View.INVISIBLE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }
}
