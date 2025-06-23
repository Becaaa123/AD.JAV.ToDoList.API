package br.com.rebeca.ToDoList.Base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected static final String ERROR = "error";
    protected static final String SUCCESS = "success";

    protected ResponseEntity<BaseResponseDTO> ok(Object data) {
        return success(HttpStatus.OK.value(), data);
    }

    protected ResponseEntity<BaseResponseDTO> error(Object data) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), data);
    }

    protected ResponseEntity<BaseResponseDTO> errorWithStatusCode(Object data, HttpStatus httpStatus) {
        return error(httpStatus.value(), data);
    }

    protected ResponseEntity<BaseResponseDTO> success(Integer codeStatus, Object data) {
        BaseResponseDTO response = new BaseResponseDTO();
        response.setCode(codeStatus);
        response.setData(data);
        response.setMessage(SUCCESS);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<BaseResponseDTO> error(Integer codeStatus, Object data) {
        BaseResponseDTO response = new BaseResponseDTO();
        response.setCode(codeStatus);
        response.setData(data);
        response.setMessage(ERROR);
        return ResponseEntity.status(codeStatus).body(response);
    }

    protected ResponseEntity<BaseResponseDTO> response(HttpStatus status, Object object, String message, String success) {
        BaseResponseDTO response = BaseResponseDTO.builder()
                .message(message)
                .data(object)
                .code(status.value())
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
