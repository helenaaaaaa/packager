package eu.unite.recruiting.packager.handlers;

import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.dtos.ErrorDto;
import eu.unite.recruiting.packager.exceptions.DataValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
		extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handlerIllegalArgumentException(@Nonnull final IllegalArgumentException exception,
			@Nonnull @SuppressWarnings("unused") final WebRequest request) {
		final ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(exception.getMessage());

		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(DataValidationException.class)
	protected ResponseEntity<Object> handlerIllegalArgumentException(@Nonnull final DataValidationException exception,
			@Nonnull @SuppressWarnings("unused") final WebRequest request) {
		final ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(exception.getMessage());

		return ResponseEntity.badRequest().body(errorDto);
	}

}
