package cn.edu.pku.kingvern.bean;

/**
 * Created by kingvern on 17/11/29.
 */

public class FutureWeather {
    private String date;
    private String type;
    private String fengli;
    private String high;
    private String low;
    private String fengxiang;

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengxiang() {

        return fengxiang;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getFengli() {
        return fengli;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }
}
