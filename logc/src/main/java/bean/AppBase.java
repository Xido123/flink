package bean;

public class AppBase {
    private String mid; // (String) 设备唯一标识
    private String uid; // (String) 用户uid
    private String vc;  // (String) versionCode，程序版本号
    private String vn;  // (String) versionName，程序版本名
    private String l;   // (String) 系统语言
    private String sr;  // (String) 渠道号，应用从哪个渠道来的。
    private String os;  // (String) Android系统版本
    private String ar;  // (String) 区域
    private String md;  // (String) 手机型号
    private String ba;  // (String) 手机品牌
    private String sv;  // (String) sdkVersion
    private String g;   // (String) gmail
    private String hw;  // (String) heightXwidth，屏幕宽高
    private String t;   // (String) 客户端日志产生时的时间
    private String nw;  // (String) 网络模式
    private String ln;  // (double) lng经度
    private String la;  // (double) lat 纬度

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public void setL(String l) {
        this.l = l;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public void setBa(String ba) {
        this.ba = ba;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public void setG(String g) {
        this.g = g;
    }

    public void setHw(String hw) {
        this.hw = hw;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void setNw(String nw) {
        this.nw = nw;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public void setLa(String la) {
        this.la = la;
    }

    public String getMid() {
        return mid;
    }

    public String getUid() {
        return uid;
    }

    public String getVc() {
        return vc;
    }

    public String getVn() {
        return vn;
    }

    public String getL() {
        return l;
    }

    public String getSr() {
        return sr;
    }

    public String getOs() {
        return os;
    }

    public String getAr() {
        return ar;
    }

    public String getMd() {
        return md;
    }

    public String getBa() {
        return ba;
    }

    public String getSv() {
        return sv;
    }

    public String getG() {
        return g;
    }

    public String getHw() {
        return hw;
    }

    public String getT() {
        return t;
    }

    public String getNw() {
        return nw;
    }

    public String getLn() {
        return ln;
    }

    public String getLa() {
        return la;
    }
}
