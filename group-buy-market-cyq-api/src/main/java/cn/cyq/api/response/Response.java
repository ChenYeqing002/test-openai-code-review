package cn.cyq.api.response;

import cn.cyq.types.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 7000723935764546321L;

    private String code;
    private String info;
    private T data;

    public static <T> Response<T> success(ResponseCode responseCode) {
        return Response.<T>builder()
                .code(responseCode.getCode())
                .info(responseCode.getInfo())
                .build();
    }

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(data)
                .build();
    }

    public static <T> Response<T> error(ResponseCode responseCode) {
        return Response.<T>builder()
                .code(responseCode.getCode())
                .info(responseCode.getInfo())
                .build();
    }

}
