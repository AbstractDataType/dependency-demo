package quesmanagement.utils;

import quesmanagement.dao.UserDao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class genID {
    static String lastD;
    static int lastS;
    private genID(){}
    public static synchronized String genID(String prefix){
        Date date=new Date();
        SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddHHmmss");
        String d=sd.format(date);
        if ((lastD==null) || (!lastD.equals(d)) ){
            lastS = 0;
            lastD = d;
            int s=lastS+1;
            return prefix+d+new DecimalFormat("0000").format(s);
        }else{
            int s=lastS+1;
            lastS = s;
            return prefix+d+new DecimalFormat("0000").format(s);
        }


    }
    public static synchronized int genUserID(){
        return UserDao.maxID()+1;
    }
}
