package app.responses;

public class Response {

    private boolean ok;

    private Object data;

    public Response() {

        this.ok = false;
        this.data = null;

    }

    public Response(boolean ok, Object data) {
        this.ok = ok;
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}