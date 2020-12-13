package Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial_v1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import Adapter.SearchResultAdapter;
import Fragment.CartFragment;
import Fragment.HomeFragment;
import Fragment.NotificationFragment;
import Fragment.SearchFragment;
import Fragment.SettingFragment;
import Model.Course;
import Model.UserAccount;
import Retrofit.ICourseService;
import Retrofit.ISearchService;
import Retrofit.ServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryActivity extends AppCompatActivity {

    public RecyclerView courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setUIReference();
        Load();

    }

    private void setUIReference() {
        courses = findViewById(R.id.category_courses);
    }

    private void Load(){
        String categoryId = getIntent().getStringExtra("CATEGORY_ID");
        ICourseService service = ServiceClient.getInstance().create(ICourseService.class);
        service.GetCoursesByCategory("course/getby-category/" + categoryId)
                .enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                        SearchResultAdapter adapter = new SearchResultAdapter(response.body());
                        courses.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Course>> call, Throwable t) {
                        Toast.makeText(CategoryActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }
}