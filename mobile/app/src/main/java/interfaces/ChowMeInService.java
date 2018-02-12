package interfaces;

import java.util.List;

import objects.Chows;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChowMeInService {
    @GET("/chow")
    Observable<List<Chows>> listChows();

    @GET("chow/{id}")
    Observable<Chows> listSelectChows(@Path("id") int id);
}

/*
@GET("/users/{user}/repos")
Observable<List<GitHubRepo>> getReposOfUser(@Path("user") String user);
}
 */