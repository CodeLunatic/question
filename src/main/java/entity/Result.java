package entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 操作结果
 * 【状态：已测试2018年12月1日09:40:40】
 * By CY
 * Date 2018/11/29 13:34
 */
@ApiModel(description = "操作结果")
public class Result {

    @ApiModelProperty(value = "操作是否成功", name = "success", required = true, example = "true")
    private boolean success;

    @ApiModelProperty(value = "提示信息", name = "message", required = true, example = "操作成功")
    private String message;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
