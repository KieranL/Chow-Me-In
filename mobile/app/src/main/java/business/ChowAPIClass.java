package business;

import java.util.List;

import interfaces.ChowMeInService;
import objects.Chows;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ChowAPIClass {
    public Call<List<Chows>> getChowMeInService() {
        final String BASEURL = "";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL).build();

        ChowMeInService service = retrofit.create(ChowMeInService.class);
        return service.listChows();
    }

    public Call<Chows> getChows(int chowID) {
        final String BASEURL = "";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL).build();

        ChowMeInService service = retrofit.create(ChowMeInService.class);
        return service.listSelectChows(chowID);
    }
}
