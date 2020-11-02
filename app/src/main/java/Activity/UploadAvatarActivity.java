package Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;

import model.User;
import model.UserAccount;
import Retrofit.IUserService;
import Retrofit.AppPreference;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class UploadAvatarActivity extends AppCompatActivity {

    private UserAccount user;
    private IUserService service;
    private boolean flag=false,flag2=false;
    private Bitmap imgsource;
    private ImageView avatar;
    private ImageView uploadfromgallery;
    private ImageView uploadfromcamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);
        user = AppPreference.GetUserAccount(getApplicationContext());
        initUIs();
        initEventHandles();
        Sync();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1000:{
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1000);
                }
                else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
            case 100:{
                //  Toast.makeText(this, "asd: "+ PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1001);
                }
                else
                {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            final InputStream imageStream;
            File file = new File(imageUri.getPath().substring(4)); // bo "/raw"
            UploadImage(file);

        }else {
            Toast.makeText(UploadAvatarActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void initUIs() {
        avatar = findViewById(R.id.changeavatar_avatar_img);
        uploadfromgallery = findViewById(R.id.changeavatar_uploadfromgallery_img);
        uploadfromcamera = findViewById(R.id.changeavatar_uploadfromcamera_img);
    }

    private void initEventHandles(){
        uploadfromgallery.setOnClickListener(v -> {
            OpenGallery();
        });

        uploadfromcamera.setOnClickListener(v -> {
            OpenCamera();
        });

    }

    private void Sync(){
        service = new Retrofit.Builder()
                .baseUrl("http://149.28.24.98:9000/") // API base url
                .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                .build()
                .create(IUserService.class);

        service.login(user.mail, user.matkhau).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(user == null) user = new UserAccount();
                    user.ava = response.body().getImage();
                    ReloadContent();
                }
                else{
                    //Toast.makeText(getActivity(), "Co van khi lay du lieu", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //Toast.makeText(getActivity(), "Loi ket noi may chu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ReloadContent(){
        if(user != null && !user.ava.isEmpty()){
            Picasso.get().load("http://149.28.24.98:9000/upload/user_image/" + user.ava)
                    .placeholder(R.drawable.noavatar)
                    .error(R.drawable.logo)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(avatar);
        }
    }

    private void OpenGallery(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //permission not granted, request it.
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup for runtime permission
                requestPermissions(permissions, 1000);
            }
            else {
                //permission already granted
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1000);
            }
        }
        else {
            //system os is less then marshmallow
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1000);
        }
    }

    private void OpenCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //show popup for runtime permission
                requestPermissions(permissions, 100);
            }
            else {
                //permission already granted
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);
            }
        }
        else {
            //system os is less then marshmallow
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 100);
        }
    }

    private void UploadImage(File file){
        RequestBody fileReqBody = RequestBody.create( MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileReqBody);
        Toast.makeText(UploadAvatarActivity.this, file.getPath(), Toast.LENGTH_LONG).show();
        service = new Retrofit.Builder()
                .baseUrl("http://149.28.24.98:9000/") // API base url
                .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
                .build()
                .create(IUserService.class);

        service.changeavatar(part, user.token)
               .enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       if(response.isSuccessful()){
                           Toast.makeText(UploadAvatarActivity.this, "Thanh cong", Toast.LENGTH_LONG).show();
                           Sync();
                       }
                       else{
                           Toast.makeText(UploadAvatarActivity.this, "That bai", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<ResponseBody> call, Throwable t) {
                       Toast.makeText(UploadAvatarActivity.this, "Loi he thong", Toast.LENGTH_LONG).show();
                   }
               });
    }

}