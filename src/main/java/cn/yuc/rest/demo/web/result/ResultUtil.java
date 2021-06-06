package cn.yuc.rest.demo.web.result;

import java.util.Arrays;

public class ResultUtil<T> {

    /**
     * 有数据返回成功
     * @return
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(data,ResultStatusEnum.OK.value(),"succeed!");
    }

    /**
     * 无数据返回成功
     * @return
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 返回失败
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String errorMessage) {
        return new Result<>(null,ResultStatusEnum.ERROR.value(), errorMessage);
    }

    public static void main(String[] args) {
        System.out.println(ResultUtil.ok(Arrays.asList(new String[]{"abc","vcd"})));
    }

    public static class Result<T>{
        private T data;
        private int status;
        private String message;

        public Result(T data, int status, String message) {
            this.data = data;
            this.status = status;
            this.message = message;
        }
        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }


        @Override
        public String toString() {
            return "Result{" +
                    "data=" + data +
                    ", status=" + status +
                    ", message='" + message + '\'' +
                    '}';
        }

    }
}
