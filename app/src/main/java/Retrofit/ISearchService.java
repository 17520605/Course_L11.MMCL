package Retrofit;

import java.util.List;

import Model.Course;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ISearchService {
    @GET
    Call<List<Course>> SearchCourses(@Url String url);
}
