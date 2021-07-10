package quesmanagement.entity;


import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.util.Date;

public class Pay {
   
  private String PayID;
  private BigDecimal amount;
  private Date PayTime;
  private Date modTime;
  private String accID;
  private String empID;
  private String empName;
  private String orderID;
  private Integer userID;

    public Pay(String payID, String orderID,Integer userID) {
        setPayID(payID);
        setOrderID(orderID);
        setUserID(userID);
    }

    public Pay(String payID, BigDecimal amount, Date payTime, String accID,
               String empID, String orderID, Integer userID) {
        PayID = payID;
        this.amount = amount;
        PayTime = payTime;
        this.accID = accID;
        this.empID = empID;
        this.orderID = orderID;
        setUserID(userID);
    }

    public String getPayID() {
      return PayID;
   }

   public void setPayID(String payID) {
      PayID = payID;
   }

   public Date getPayTime() {
      return PayTime;
   }

   public void setPayTime(Date payTime) {
      PayTime = payTime;
   }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Pay() {
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public void setAmount(BigDecimal amount) {
      this.amount = amount;
   }

   public Date getModTime() {
      return modTime;
   }

   public void setModTime(Date modTime) {
      this.modTime = modTime;
   }

   public String getAccID() {
      return accID;
   }

   public void setAccID(String accID) {
      this.accID = accID;
   }

   public String getEmpID() {
      return empID;
   }

   public void setEmpID(String empID) {
      this.empID = empID;
   }

   public String getOrderID() {
      return orderID;
   }

   public void setOrderID(String orderID) {
      this.orderID = orderID;
   }

   public Integer getUserID() {
      return userID;
   }

   public void setUserID (Integer userID) throws ZeroException {
      if(userID>0){
         this.userID = userID;
      }else{
         throw new ZeroException("userID cannot less than zero");
      }
   }
}