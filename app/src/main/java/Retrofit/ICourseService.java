package Retrofit;

import java.util.List;

import Model.Category;
import Model.ChangeProfileResponeModel;
import Model.Course;
import Model.User;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ICourseService {
    @GET
    Call<List<Course>> GetAllCourses(@Url String url);

    @GET
    Call<Course> GetCourseById(@Url String url);

    @GET
    Call<Course> GetJoinedCourses(@Url String url);


    @GET
    Call<Course> GetCoursesByUser(@Url String url);

    @GET
    Call<List<Course>> GetCoursesByCategory(@Url String url);

    @GET("course/get-free")
    Call<List<Course>> GetFreeCourses();

    @GET("course/get-top")
    Call<List<Course>> GetTopCourses();

}
