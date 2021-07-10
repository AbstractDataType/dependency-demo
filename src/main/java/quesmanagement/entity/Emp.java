package quesmanagement.entity;

import quesmanagement.utils.ZeroException;

import java.util.Date;

public class Emp {
   private String empID;
   private String empName;
   private Date modTime;
   private Integer userID;

   public Emp() {
   }

   public Emp(String empID, String empName, Integer userID) {
      this.empID = empID;
      this.empName = empName;
      setUserID(userID);
   }

   public Emp(String empID, Integer userID) {
      this.empID = empID;
      setUserID(userID);
   }


   public String getEmpID() {
      return empID;
   }

   public void setEmpID(String empID) {
      this.empID = empID;
   }

   public String getEmpName() {
      return empName;
   }

   public void setEmpName(String empName) {
      this.empName = empName;
   }

   public Date getModTime() {
      return modTime;
   }

   public void setModTime(Date modTime) {
      this.modTime = modTime;
   }

   public Integer getUserID() {
      return userID;
   }

   public void setUserID (int userID) throws ZeroException {
      if(userID>0){
         this.userID = userID;
      }else{
         throw new ZeroException("userID cannot be zero");
      }
   }
}

