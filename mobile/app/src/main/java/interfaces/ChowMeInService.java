package interfaces;

import java.util.List;

import io.reactivex.Observable;
import objects.APISuccessObject;
import objects.Chows;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChowMeInService {
    @GET("/chow")
    Observable<List<Chows>> listChows();

    @GET("chow/{id}")
    Observable<Chows> listSelectChows(@Path("id") int id);

    @GET("chow/joined")
    Call<List<Chows>> getJoinedChows(@Header("Access-Token") String token);

    @GET("chow/poster")
    Call<List<Chows>>  getPostedChows(@Header("Access-Token") String token);

    @POST("chow/{id}/join")
    Call<APISuccessObject> joinChow(@Header("Access-Token") String token, @Path("id") int id);


    @POST("/chow")
    Observable<APISuccessObject> createChows(@Body Chows chow);

    @POST("chow/{id}")
    Observable<APISuccessObject> updateSelectChows(@Path("id") int id, @Body Chows chow);

    @DELETE("chow/{id}")
    Observable<APISuccessObject> deleteSelectChows(@Path("id") int id);


}

/*
@GET("/users/{user}/repos")
Observable<List<GitHubRepo>> getReposOfUser(@Path("user") String user);
}
 */