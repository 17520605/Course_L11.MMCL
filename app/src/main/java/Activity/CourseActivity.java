package Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial_v1.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import Adapter.CourseAdapter;
import Model.Course;
import Retrofit.ICourseService;
import Retrofit.ServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Ultilities.Ultitities.FortmatDateString;


public class CourseActivity extends AppCompatActivity {
    private Course model;
    private ImageView image;
    private TextView title;
    private TextView author;
    private TextView updatedate;
    private TextView goal;
    private TextView description;
    private RatingBar rating;
    private TextView totalvote;
    private RecyclerView comments;
    private RecyclerView suggestedcourses;
    private TextView price;
    private Button addtocard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initUIs();
        initEvents();
        Sync();
    }

    private void initUIs() {
        image = findViewById(R.id.course_image);
        title = findViewById(R.id.course_title);
        author = findViewById(R.id.course_author);
        updatedate = findViewById(R.id.course_updatedate);
        goal = findViewById(R.id.course_goal);
        description = findViewById(R.id.course_description);
        rating = findViewById(R.id.course_rating);
        totalvote = findViewById(R.id.course_totalvote);
        comments = findViewById(R.id.courseComment);
        suggestedcourses = findViewById(R.id.recommendCourses);
        price = findViewById(R.id.course_price);
        addtocard = findViewById(R.id.course_addtocard);
    }

    private void initEvents(){
        addtocard.setOnClickListener(v -> {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if(pref.contains("idsoncart")){
                String ids = pref.getString("idsoncart", "default");
                ids = ids.concat(model.get_id()+",");
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("idsoncart");
                editor.putString("idsoncart", ids);
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("idsoncart", model.get_id()+",");
                editor.commit();
            }
            addtocard.setText("ADDED");


        });
    }

    private void Sync(){
        String courseId = getIntent().getStringExtra("COURSE_ID");
        ICourseService service = ServiceClient.getInstance().create(ICourseService.class);
        service.GetCourseById("course/getbyid/" + courseId).enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> call, Response<Course> response) {
                        model = response.body();
                        Load();
                    }

                    @Override
                    public void onFailure(Call<Course> call, Throwable t) {
                        Toast.makeText(CourseActivity.this, "Loi he thong", Toast.LENGTH_SHORT).show();
                    }
        });
    }

    private void Load(){
        if(model != null){
            Picasso.get().load("http://149.28.24.98:9000/upload/course_image/" + model.getImage())
                    .placeholder(R.drawable.noavatar)
                    .error(R.drawable.noavatar)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(image);
            title.setText(model.getName());
            updatedate.setText( FortmatDateString(model.getCreated_at()));
            goal.setText(model.getGoal());
            description.setText(model.getDescription());
            rating.setRating(Float.valueOf(model.getVote().getEVGVote()));
            totalvote.setText(model.getVote().getTotalVote());
            price.setText(model.getPrice().compareTo("0") == 0 ? "FREE" : model.getPrice() + " đ");

            //Load suggested courses
            ICourseService courseService = ServiceClient.getInstance().create(ICourseService.class);
            courseService.GetTopCourses().enqueue(new Callback<List<Course>>() {
                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                    CourseAdapter adapter = new CourseAdapter(response.body());
                    suggestedcourses.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {

                }
            });

            //
            if(CheckOnCart()){
                addtocard.setText("ADDED");
                addtocard.setEnabled(false);
            }

        }
    }

    private boolean CheckOnCart(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String str = pref.getString("idsoncart", "default");
        if(str.compareTo("default")!=0){
            return str.contains(model.get_id());
        }
        return false;
    }
}