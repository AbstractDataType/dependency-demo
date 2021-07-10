package quesmanagement.entity;


import quesmanagement.utils.ZeroException;

import java.util.Date;

public class Customer {
   private String custID;
   private String custName;
   private String address;
   private Integer zip;
   private String email;
   private Date modtime;
   private Integer userID;

   public Customer(String custID,int userID) {
      this.custID = custID;
      setUserID(userID);
   }

   public Customer(String custID, String custName, String address, Integer zip, String email,int userID) {
      this.custID = custID;
      this.custName = custName;
      this.address = address;
      this.zip = zip;
      this.email = email;
      this.setUserID(userID);
   }

   public String getCustName() {
      return custName;
   }

   public void setCustName(String custName) {
      this.custName = custName;
   }


   public Integer getUserID() {
      return userID;
   }

   public void setUserID(Integer userID) {
      if (userID>0){
      this.userID = userID;
   }else{throw new ZeroException("userID cannot less than zero");
      }
   }

   public Customer() {
   }

   public String getCustID() {
      return custID;
   }

   public void setCustID(String custID) {
      this.custID = custID;
   }


   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public Integer getZip() {
      return zip;
   }

   public void setZip(int zip) {
      this.zip = zip;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Date getModtime() {
      return modtime;
   }

   public void setModtime(Date modtime) {
      this.modtime = modtime;
   }


}