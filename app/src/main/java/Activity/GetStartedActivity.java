package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorial_v1.R;

import java.util.List;

import Model.Category;
import Model.Course;
import Retrofit.ICourseService;
import Retrofit.ICategoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetStartedActivity extends AppCompatActivity {
    Button get_started;
    //ICategoryService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
        InitUIs();
        InitEvents();
    }

    private void InitUIs() {
        get_started=findViewById(R.id.changepasswordsuccessfull_backtologin_btn);
    }

    private void InitEvents(){
//        get_started.setOnClickListener(v -> {
//            service = new Retrofit.Builder()
//                    .baseUrl("http://149.28.24.98:9000/") // API base url
//                    .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
//                    .build()
//                    .create(ICourseService.class);
//
//            service.GetCourseById("course/getbyid/5fa4ad06b4e3807bf40fed23")
//                   .enqueue(new Callback<Course>() {
//                       @Override
//                       public void onResponse(Call<Course> call, Response<Course> response) {
//                           Toast.makeText(GetStartedActivity.this, response.body().getCreated_at().toString(), Toast.LENGTH_LONG).show();
//                       }
//
//                       @Override
//                       public void onFailure(Call<Course> call, Throwable t) {
//                           Toast.makeText(GetStartedActivity.this, "Failed", Toast.LENGTH_LONG).show();
//                       }
//                   });
//        });

//        get_started.setOnClickListener(v -> {
//            ICourseService service = new Retrofit.Builder()
//                    .baseUrl("http://149.28.24.98:9000/") // API base url
//                    .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
//                    .build()
//                    .create(ICourseService.class);
//
//            service.GetCoursesOnTop("course/get-top")
//                   .enqueue(new Callback<List<Course>>() {
//                       @Override
//                       public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
//                           Toast.makeText(GetStartedActivity.this, "OK", Toast.LENGTH_LONG).show();
//                       }
//
//                       @Override
//                       public void onFailure(Call<List<Course>> call, Throwable t) {
//                           Toast.makeText(GetStartedActivity.this, "Failed", Toast.LENGTH_LONG).show();
//                       }
//                   });
//        });

//        get_started.setOnClickListener(v -> {
//           ICategoryService service = new Retrofit.Builder()
//                    .baseUrl("http://149.28.24.98:9000/") // API base url
//                    .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format trả về
//                    .build()
//                    .create(ICategoryService.class);
//            service.GetAllCategories("category/get-all-category")
//                    .enqueue(new Callback<List<Category>>() {
//                        @Override
//                        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
//                            Toast.makeText(GetStartedActivity.this, String.valueOf(response.body().size()), Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Category>> call, Throwable t) {
//                            Toast.makeText(GetStartedActivity.this, "Failed", Toast.LENGTH_LONG).show();
//                        }
//                    });
//        });
    }

}