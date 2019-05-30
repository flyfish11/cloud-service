package com.cloud.model.common;

        import lombok.Data;
        import lombok.Getter;
        import lombok.Setter;

        import java.io.Serializable;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * @author: flyfish
 * @create: 2018-06-19 11:27
 * @description:
 */
@Data
public class Result implements Serializable {

    private Integer code;
    private boolean success;
    private String msg;
    private int total;
    private Object data;
    private long count;
    private Object rows;

}