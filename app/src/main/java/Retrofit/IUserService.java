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
import retrofit2.http.PUT;

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


    @PUT("change-profile")
    @FormUrlEncoded
    Call<ChangeProfileResponeModel> change(@Field("name") String name,
                                           @Field("phone") String phone,
                                           @Field("address") String address,
                                           @Field("description") String description,
                                           @Field("gender") String gender,
                                           @Header("auth-token") String token);


    @POST("active-account")
    @FormUrlEncoded
    Call<String> active( @Field("email") String email,
                         @Field("activeToken") String activeToken);



    @PUT("change-password")
    @FormUrlEncoded
    Call<ChangeProfileResponeModel> changepassword ( @Field("oldpassword") String oldpass,
                                                     @Field("newpassword") String newpass,
                                                     @Header("auth-token") String authtoken);

}
