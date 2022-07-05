package urjc.exception;

public class ResponseError {

  private final int status;
  private final String message;

  public ResponseError(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return this.status;
  }

  public String getMessage() {
    return this.message;
  }

@Override
public String toString() {
	return "ResponseError [status=" + status + ", message=" + message + "]";
}

}
