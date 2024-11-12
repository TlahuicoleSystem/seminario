package com.example.seminario.agenda.Utils;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang3.StringUtils;

import com.example.seminario.agenda.Utils.exception.AppException;
import com.example.seminario.agenda.Utils.exception.AppException400BadRequest;
import com.example.seminario.agenda.Utils.exception.AppException401Unauthorized;
import com.example.seminario.agenda.Utils.exception.AppException403Forbidden;
import com.example.seminario.agenda.Utils.exception.AppException404NotFound;
import com.example.seminario.agenda.Utils.exception.AppException500InternalServerError;

public class Utils {
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

	private Utils() {

	}

	///////////////// >>>>>>
	////// Validation >>>>>>
	///////////////// >>>>>>

	public static boolean isPresent(Object o) {
		return o != null && StringUtils.isNotBlank(String.valueOf(o));
	}

	public static boolean isObjectValid(Object o) {
		boolean valid = true;
		if (o == null) {
			valid = false;
		} else {
			if (o instanceof String) {
				valid = StringUtils.isNotBlank((String) o);
			} else if (o instanceof Map) {
				valid = !((Map) o).isEmpty();
			} else if (o instanceof List) {
				valid = !((List) o).isEmpty();
			}
		}
		return valid;
	}


	public static void validate404NotFound(Object obj, String msg) throws AppException404NotFound {
		if (!Utils.isObjectValid(obj)) {
			throw new AppException404NotFound(msg);
		}
	}

	public static void validate400BadRequest(Object obj, String msg) throws AppException400BadRequest {
		if (!Utils.isObjectValid(obj)) {
			throw new AppException400BadRequest(msg + " (requerido)");
		}
	}

	public static void validateMulti400BadRequest(Object... args) throws AppException400BadRequest {
		List<String> invalids = new ArrayList<>();
		for (int i = 0; i < args.length; i = i + 2) {
			if (!Utils.isObjectValid(args[i])) {
				invalids.add(args[i + 1] + " (requerido)");
			}
		}
		if (!invalids.isEmpty()) {
			throw new AppException400BadRequest(String.join("<br/>", invalids));
		}
	}

	/**
	 * @author jtezva value | class | required | min | max | collection | name...
	 */
	public static void validateComplex400BadRequest(Object... args) throws AppException400BadRequest {
		List<String> invalids = new ArrayList<>();
		Object value;
		Class clazz;
		Boolean required;
		Object min;
		Object max;
		Set<String> collec;
		String name;

		boolean present;
		boolean format;
		String sVal = null;
		double doubleVal = 0d;
		long longVal = 0l;
		int intVal = 0;

		for (int i = 0; i < args.length; i = i + 7) {
			value = args[i];
			clazz = (Class) args[i + 1];
			required = args[i + 2] != null ? (Boolean) args[i + 2] : null;
			min = args[i + 3];
			max = args[i + 4];
			collec = args[i + 5] != null ? (HashSet<String>) args[i + 5] : null;
			name = (String) args[i + 6];

			present = Utils.isPresent(value);

			// required
			if (Boolean.TRUE.equals(required) && !present) {
				invalids.add(name + " (requerido)");
			}

			// format
			format = false;
			if (present) {
				sVal = String.valueOf(value);
				try {
					if (clazz.equals(Double.class)) {
						doubleVal = Double.parseDouble(sVal);
					} else if (clazz.equals(Long.class)) {
						longVal = Long.parseLong(sVal);
					} else if (clazz.equals(Integer.class)) {
						intVal = Integer.parseInt(sVal);
					}
					format = true;
				} catch (Exception e) {
					LOG.warn("El valor [{}] no es [{}]", sVal, clazz.getSimpleName());
					invalids.add(name + " (formato)");
				}
			}

			if (present && format) {
				// min
				if (min != null) {
					if (min instanceof String) { // length (min is String)
						if (sVal.length() < Integer.parseInt((String) min)) {
							invalids.add(name + " (longitud m\u00ednima: " + ((String) min) + ")");
						}
					} else { // value (min is double, long, or int)
						if (clazz.equals(Double.class)) {
							if (doubleVal < (double) min) {
								invalids.add(name + " (valor m\u00ednimo: " + String.valueOf(min) + ")");
							}
						} else if (clazz.equals(Long.class)) {
							if (longVal < (long) min) {
								invalids.add(name + " (valor m\u00ednimo: " + String.valueOf(min) + ")");
							}
						} else if (clazz.equals(Integer.class)) {
							if (intVal < (int) min) {
								invalids.add(name + " (valor m\u00ednimo: " + String.valueOf(min) + ")");
							}
						} else if (clazz.equals(String.class)) { // length (min is int)
							if (sVal.length() < (int) min) {
								invalids.add(name + " (longitud m\u00ednima: " + String.valueOf(min) + ")");
							}
						}
					}
				}

				// max
				if (max != null) {
					if (max instanceof String) { // length (max is String)
						if (sVal.length() > Integer.parseInt((String) max)) {
							invalids.add(name + " (longitud m\u00e1xima: " + ((String) max) + ")");
						}
					} else { // value (max is double, long, or int)
						if (clazz.equals(Double.class)) {
							if (doubleVal > (double) max) {
								invalids.add(name + " (valor m\u00e1ximo: " + String.valueOf(max) + ")");
							}
						} else if (clazz.equals(Long.class)) {
							if (longVal > (long) max) {
								invalids.add(name + " (valor m\u00e1ximo: " + String.valueOf(max) + ")");
							}
						} else if (clazz.equals(Integer.class)) {
							if (intVal > (int) max) {
								invalids.add(name + " (valor m\u00e1ximo: " + String.valueOf(max) + ")");
							}
						} else if (clazz.equals(String.class)) { // length (max is int)
							if (sVal.length() > (int) max) {
								invalids.add(name + " (longitud m\u00e1xima: " + String.valueOf(max) + ")");
							}
						}
					}
				}

				// collection
				if (collec != null) {
					if (!collec.contains(sVal)) {
						invalids.add(name + " (valor incorrecto)");
					}
				}
			}
		}
		if (!invalids.isEmpty()) {
			throw new AppException400BadRequest(String.join("<br/>", invalids));
		}
	}


	///////////////////////// >>>>>>
	////// Exception handling >>>>>>
	///////////////////////// >>>>>>
	public static void raise(Exception e, String msg) throws AppException {
		if (e instanceof AppException) {
			if (!((AppException) e).isLogged()) {
				LOG.error("APP-EXCEPTION {}: {}", e.getClass().getSimpleName(), e.getMessage());
				((AppException) e).setLogged(true);
			}
			throw (AppException) e;
		} else {
			if (StringUtils.isNotBlank(msg)) {
				LOG.error(msg, e);
				throw new AppException500InternalServerError(msg, e, true);
			}
			LOG.error("Without message", e);
			throw new AppException500InternalServerError("-nomessage-", e, true);
		}
	}

	public static <T> ResponseEntity<ResponseBean<T>> handle(Exception e, String msg) {
		ResponseEntity<ResponseBean<T>> response = null;
		ResponseBean<T> body = null;
		HttpStatus status = null;

		if (e instanceof AppException && !((AppException) e).isLogged()) {
			LOG.error("APP-EXCEPTION {}: {}", e.getClass().getSimpleName(), e.getMessage());
		}

		if (e instanceof AppException400BadRequest) {
			body = new ResponseBean<>(false, "Revisar los datos:<br/>" + e.getMessage().toLowerCase(),
					e.getMessage().toLowerCase().split("<br/>"));
			status = HttpStatus.BAD_REQUEST;
		} else if (e instanceof AppException401Unauthorized) {
			body = new ResponseBean<>(false, "Usuario no autenticado");
			status = HttpStatus.UNAUTHORIZED;
		} else if (e instanceof AppException403Forbidden) {
			body = new ResponseBean<>(false, "Permiso denegado");
			status = HttpStatus.FORBIDDEN;
		} else if (e instanceof AppException404NotFound) {
			body = new ResponseBean<>(false, e.getMessage());
			status = HttpStatus.NOT_FOUND;
		} else if (e instanceof AppException500InternalServerError) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			if ("-nomessage-".equals(e.getMessage())) {
				body = new ResponseBean<>(false, "Error de sistema", e);
			} else {
				body = new ResponseBean<>(false, "Error en el proceso: " + e.getMessage().toLowerCase(), e);
			}
		} else if (e instanceof AppException) {
			body = new ResponseBean<>(false, e.getMessage());
			status = HttpStatus.OK;
		}
		if (status != null) {
			response = new ResponseEntity<>(body, status);
		} else {
			try {
				Utils.raise(e, msg);
			} catch (Exception e2) {
				response = Utils.handle(e2, msg);
			}
		}
		return response;
	}
	// <<<<<< /////////////////////////
	// <<<<<< Exception handling //////
	// <<<<<< /////////////////////////

	//////////////////////// >>>>>>
	////// Response handling >>>>>>
	//////////////////////// >>>>>>
	// 200 success true
	public static <T> ResponseEntity<ResponseBean<T>> response200OK() {
		return Utils.<T>response(HttpStatus.OK, null, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(String msg) {
		return Utils.<T>response(HttpStatus.OK, msg, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(T data) {
		return Utils.<T>response(HttpStatus.OK, null, data);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(T data, int total) {
		return Utils.<T>response(HttpStatus.OK, null, data, total);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(String msg, T data) {
		return Utils.<T>response(HttpStatus.OK, msg, data);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(String msg, T data, int total) {
		return Utils.<T>response(HttpStatus.OK, msg, data, total);
	}

	// 200 success false
	public static <T> ResponseEntity<ResponseBean<T>> response200OK(boolean success) {
		return Utils.<T>response(HttpStatus.OK, success, null, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(boolean success, String msg) {
		return Utils.<T>response(HttpStatus.OK, success, msg, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(boolean success, T data) {
		return Utils.<T>response(HttpStatus.OK, success, null, data);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response200OK(boolean success, String msg, T data) {
		return Utils.<T>response(HttpStatus.OK, success, msg, data);
	}

	// 201
	public static <T> ResponseEntity<ResponseBean<T>> response201Created() {
		return Utils.<T>response(HttpStatus.CREATED, null, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response201Created(String msg) {
		return Utils.<T>response(HttpStatus.CREATED, msg, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response201Created(T data) {
		return Utils.<T>response(HttpStatus.CREATED, null, data);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response201Created(String msg, T data) {
		return Utils.<T>response(HttpStatus.CREATED, msg, data);
	}

	// 202
	public static <T> ResponseEntity<ResponseBean<T>> response202Accepted() {
		return Utils.<T>response(HttpStatus.ACCEPTED, null, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response202Accepted(String msg) {
		return Utils.<T>response(HttpStatus.ACCEPTED, msg, null);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response202Accepted(T data) {
		return Utils.<T>response(HttpStatus.ACCEPTED, null, data);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response202Accepted(String msg, T data) {
		return Utils.<T>response(HttpStatus.ACCEPTED, msg, data);
	}

	// general
	// -success
	public static <T> ResponseEntity<ResponseBean<T>> response(HttpStatus status, String msg, T data) {
		ResponseBean<T> body = new ResponseBean<>(msg, data);
		return new ResponseEntity<>(body, status);
	}

	public static <T> ResponseEntity<ResponseBean<T>> response(HttpStatus status, String msg, T data, int total) {
		ResponseBean<T> body = new ResponseBean<>(msg, data, total);
		return new ResponseEntity<>(body, status);
	}

	// -failure
	public static <T> ResponseEntity<ResponseBean<T>> response(HttpStatus status, boolean success, String msg, T data) {
		ResponseBean<T> body = new ResponseBean<>(success, msg, data);
		return new ResponseEntity<>(body, status);
	}
	// <<<<<< ////////////////////////
	// <<<<<< Response handling //////
	// <<<<<< ////////////////////////
}
