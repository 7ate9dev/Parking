package karan.parking;

import android.content.Context;

import com.orm.SugarApp;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by karan on 11/10/15.
 */
public class LocationHistory extends SugarRecord<LocationHistory> {
    private String address;
    private String city;
    private Date date;

    public LocationHistory(){

    }
    public void saveRecord(String address,String city,Date date)
    {
        this.address=address;
        this.city=city;
        this.date=date;
    }
    public String getAddress()
    {
        return address+" "+city;
    }
    public String getDate()
    {
        return date.toString();
    }

}
