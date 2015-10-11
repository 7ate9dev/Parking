package karan.parking;

/**
 * Created by karan on 11/10/15.
 */
public class Record {
    String adddress;

    public Record(String adddress,String city) {
        this.adddress = adddress+" "+city;
    }

    public String getAdddress() {
        return adddress;
    }

    public void setAdddress(String adddress,String city) {
        this.adddress = adddress+" "+city;
    }
}
