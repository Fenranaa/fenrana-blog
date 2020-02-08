package cn.fenrana.blog.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ResultJson<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String msg;
    ResultJson(){
      this.code =  HttpStatus.OK.value();
      this.msg = "成功";
    }

    ResultJson(T data){
        this.code =  HttpStatus.OK.value();
        this.msg = "成功";
        this.data = data;
    }

    ResultJson(int code, String msg){
        this.code =  code;
        this.msg = msg;
    }
    public static <T> ResultJson<T> ok(){
        return new ResultJson<>();
    }

    public static <T> ResultJson<T> ok(T data){
        return new ResultJson<>(data);
    }

    public static <T> ResultJson<T> fail(){
        return new ResultJson<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部错误");
    }

    public static <T> ResultJson<T> fail(int code, String msg){
        return new ResultJson<>(code, msg);
    }


}
