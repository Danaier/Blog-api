package dye.blogapi.utils;

import dye.blogapi.common.CommonResponse;
import dye.blogapi.common.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    //请求接口参数错误的异常处理
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleMissingParameterException(MissingServletRequestParameterException exception){
        return CommonResponse.createForError(ResponseCode.ARGUMENT_ILLEGAL.getCode(), ResponseCode.ARGUMENT_ILLEGAL.getDescription());
    }

    //非对象参数校验
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<Object> handleValidationException(ConstraintViolationException exception){
        return CommonResponse.createForError(exception.getMessage());
    }

    //对象参数校验
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleValidException(MethodArgumentNotValidException exception){
        return CommonResponse.createForError(
                ResponseCode.ARGUMENT_ILLEGAL.getCode(),formatValidErrorsMessage(exception.getBindingResult().getAllErrors()));
    }

    //处理MethodArgumentNotValidException异常的message不好用的问题，格式化之后返回
    private String formatValidErrorsMessage(List<ObjectError> errorList){
        StringBuffer errorMessage = new StringBuffer();
        errorList.forEach(error -> errorMessage.append(error.getDefaultMessage()).append(","));
        errorMessage.deleteCharAt(errorMessage.length()-1);
        return errorMessage.toString();
    }

}
