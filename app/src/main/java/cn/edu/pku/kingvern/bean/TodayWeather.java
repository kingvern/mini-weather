package cn.edu.pku.kingvern.bean;

/**
 * Created by kingvern on 10/11/17.
 */

public class TodayWeather {
    private String city;
    private String updatetime;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private String fengxiang;
    private String fengli;
    private String date;
    private String high;
    private String low;
    private String type;
    private String[] ffengxiang;
    private String[] ffengli;
    private String[] fdate;
    private String[] fhigh;
    private String[] flow;
    private String[] ftype;

    public void setFfengxiang(String[] ffengxiang) {
        this.ffengxiang = ffengxiang;
    }

    public void setFfengli(String[] ffengli) {
        this.ffengli = ffengli;
    }

    public void setFdate(String[] fdate) {
        this.fdate = fdate;
    }

    public void setFhigh(String[] fhigh) {
        this.fhigh = fhigh;
    }

    public void setFlow(String[] flow) {
        this.flow = flow;
    }

    public void setFtype(String[] ftype) {
        this.ftype = ftype;
    }

    public String[] getFfengxiang() {

        return ffengxiang;
    }

    public String[] getFfengli() {
        return ffengli;
    }

    public String[] getFdate() {
        return fdate;
    }

    public String[] getFhigh() {
        return fhigh;
    }

    public String[] getFlow() {
        return flow;
    }

    public String[] getFtype() {
        return ftype;
    }


    public String getCity() {
        return city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getQuality() {
        return quality;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public String getDate() {
        return date;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", quality='" + quality + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli + '\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                ", type='" + ftype[0] + '\'' +
                ", type='" + fdate[0] + '\'' +
                ", type='" + fhigh[0] + '\'' +
                '}';
    }
}
