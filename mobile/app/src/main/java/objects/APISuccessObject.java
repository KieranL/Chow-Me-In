package objects;

import java.util.List;

public class APISuccessObject {
    private boolean success;
    private Chows chow;
    private List<Chows> listOfChows;

    public APISuccessObject() {
    }

    public APISuccessObject(boolean success, Chows chow, List<Chows> listOfChows) {
        this.success = success;
        this.chow = chow;
        this.listOfChows = listOfChows;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Chows getChow() {
        return chow;
    }

    public void setChow(Chows chow) {
        this.chow = chow;
    }

    public List<Chows> getListOfChows() {
        return listOfChows;
    }

    public void setListOfChows(List<Chows> listOfChows) {
        this.listOfChows = listOfChows;
    }
}
