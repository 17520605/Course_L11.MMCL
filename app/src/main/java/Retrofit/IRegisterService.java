package Retrofit;

import Model.User;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IRegisterService {
    @POST("register")
    @FormUrlEncoded
    Call<User> register(@Field("name") String name,
                        @Field("password") String password,
                        @Field("phone") String phone,
                        @Field("address") String address,
                        @Field("description") String description,
                        @Field("gender") String gender,
                        @Field("email") String email);
}
