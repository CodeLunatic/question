package entity;

/**
 * 操作结果
 * 【状态：已测试2018年12月1日09:40:40】
 * By CY
 * Date 2018/11/29 13:34
 */
public class Result {

    // 操作是否成功
    private boolean success;
    // 提示信息
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
