package objects;

public class APISuccessObject {
    private boolean success;
    private Chows chow;

    public APISuccessObject(boolean success, Chows chow) {
        this.success = success;
        this.chow = chow;
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
}
