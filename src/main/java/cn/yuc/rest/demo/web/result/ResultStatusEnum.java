package cn.yuc.rest.demo.web.result;

public enum ResultStatusEnum {
    OK(0),
    ERROR(1);

    private final int value;

    private ResultStatusEnum(int value){
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
