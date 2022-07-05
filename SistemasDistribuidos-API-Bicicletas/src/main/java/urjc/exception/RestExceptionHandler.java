package urjc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({BadRequest.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<ResponseError> badRequestException(Exception ex,
      WebRequest request) {
	 ResponseError response= new ResponseError(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    return new ResponseEntity<ResponseError>(response,HttpStatus.BAD_REQUEST);
  }

}
