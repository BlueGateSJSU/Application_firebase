package com.bluegate.autodoor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Uploader {
    private String name;
    private ImageView profile_img;
    private List<Uri> images;

    public Uploader(String name, ImageView profile_img) {
        this.name = name;
        this.profile_img = profile_img;
        //this.images = selectedImageUri;
    }

    // Firebase Storage 인스턴스 가져오기
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    // 이미지를 저장할 폴더 경로 지정 (예: "images/my_folder")
    String folderPath = "pets/";

    // 이미지 파일 경로 가져오기 (예시로 선택한 이미지 URI를 사용)
    Uri imageUri; // 선택한 이미지의 Uri를 가져와야 합니다.

    public void uploadPet() {
        // 폴더 내에 이미지 업로드
        StorageReference folderReference = storageRef.child(folderPath + name);

        // 프로필 사진
        Uri profile_uri = getImageUriFromImageView(profile_img.getContext(), profile_img);
        StorageReference ir = folderReference.child("profile");
        imageUri = profile_uri;
        uploadToFireBase(ir);

//        // 학습용 사진 10장
//        int counter = 0;
//        for (Uri uri : images) {
//            StorageReference imageReference = folderReference.child(name + "" + counter);
//            imageUri = uri;
//            uploadToFireBase(imageReference);
//            counter++;
//        }
    }

    public static Uri getImageUriFromImageView(Context context, ImageView imageView) {
        // Get the Drawable from the ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable == null) {
            return null;
        }

        // Convert the Drawable to a Bitmap
        Bitmap bitmap = drawable.getBitmap();

        // Save the Bitmap to a temporary file
        File tempFile = saveBitmapToTempFile(context, bitmap);
        if (tempFile == null) {
            return null;
        }

        // Get the Uri from the temporary file
        return Uri.fromFile(tempFile);
    }

    private static File saveBitmapToTempFile(Context context, Bitmap bitmap) {
        File tempFile = null;
        try {
            // Create a temporary file in the external storage directory
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            tempFile = File.createTempFile("temp_image", ".jpg", storageDir);

            // Compress the bitmap and save it to the temporary file
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public void uploadToFireBase(StorageReference imageReference) {
        imageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // 이미지 업로드 성공 시
                    // taskSnapshot.getMetadata()를 사용하여 업로드된 이미지의 정보를 얻을 수 있습니다.
                })
                .addOnFailureListener(e -> {
                    // 이미지 업로드 실패 시
                    // 오류 처리 로직을 추가하세요.
                });
    }

}
