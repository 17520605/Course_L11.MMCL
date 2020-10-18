package Retrofit;

import Model.ChangeProfileResponeModel;
import Model.User;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IUserService {
    @POST("register")
    @FormUrlEncoded
    Call<User> register(@Field("name") String name,
                        @Field("password") String password,
                        @Field("phone") String phone,
                        @Field("address") String address,
                        @Field("description") String description,
                        @Field("gender") String gender,
                        @Field("email") String email);

    @POST("login")
    @FormUrlEncoded
    Call<User> login(@Field("email") String email,
                     @Field("password") String password);


    @POST("change-profile")
    @FormUrlEncoded
    Call<Response<String>> change(@Header("auth-token") String token,
                                           @Field("name") String name,
                                           @Field("phone") String phone,
                                           @Field("address") String address,
                                           @Field("description") String description,
                                           @Field("gender") String gender);

}
