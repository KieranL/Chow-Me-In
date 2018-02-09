package interfaces;

import java.util.List;

import objects.Chows;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChowMeInService {
    @GET("chow")
    Call<List<Chows>> listChows();

    @GET("chow/{id}")
    Call<Chows> listSelectChows(@Path("chow") int ID);
}

/*
public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}
 */