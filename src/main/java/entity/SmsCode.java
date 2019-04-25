package entity;

public class SmsCode {
    private String msg;
    private boolean isValid;
    public SmsCode(){}

    public SmsCode(boolean isValid,String msg){
        this.msg = msg;
        this.isValid = isValid;
    }




    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
