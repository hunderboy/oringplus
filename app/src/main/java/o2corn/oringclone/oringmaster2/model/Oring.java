package o2corn.oringclone.oringmaster2.model;

// 오링 데이터 클래스
public class Oring {

    int ListNo;
    String STANDARD;
    String No;
    double ID;
    double CS;
    double tolernce_id;
    double tolernce_cs;


    public Oring(int ListNo, String standard, String no, double id, double cs, double tolernce_id, double tolernce_cs) {
        this.ListNo = ListNo;
        this.STANDARD = standard;
        this.No = no;
        this.ID = id;
        this.CS = cs;
        this.tolernce_id = tolernce_id;
        this.tolernce_cs = tolernce_cs;
    }

    public int getListNo() {
        return ListNo;
    }
    public String getSTANDARD() {
        return STANDARD;
    }
    public String getNo() {
        return No;
    }
    public double getID() {
        return ID;
    }
    public double getCS() {
        return CS;
    }
    public double getTolernce_id() {
        return tolernce_id;
    }
    public double getTolernce_cs() {
        return tolernce_cs;
    }






}
