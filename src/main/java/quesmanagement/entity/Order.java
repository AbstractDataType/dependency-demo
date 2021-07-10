package quesmanagement.entity;


import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class Order {
   
   private String orderID;
   private Date orderTime;
   private int unit;
   private BigDecimal unitPrice;
   private boolean type; //True -> prepaid
   private Date modTime;
   private String custID;
   private String custName;
   private String empID;
   private String empname;
   private Integer userID;
   private Collection<Pay> pays;
   private Collection<Questionnaire> questionnaires;
   private Collection<Dist> dists;
   private Collection<Recv> recvs;
   private Collection<Analy> analys;

   public Order(String orderID, Date orderTime,  BigDecimal unitPrice,
                boolean type, String custID, String empID, Integer userID,
                Collection<Questionnaire> questionnaires) {
      this.orderID = orderID;
      this.orderTime = orderTime;
      this.unitPrice = unitPrice;
      this.type = type;
      this.custID = custID;
      this.empID = empID;
      setUserID(userID);
      this.questionnaires=questionnaires;
   }

   public void setRecvs(Collection<Recv> recvs) {
      this.recvs = recvs;
   }

   public Collection<Analy> getAnalys() {
      return analys;
   }

   public void setAnalys(Collection<Analy> analys) {
      this.analys = analys;
   }

   public Order(String orderID, Integer userID) {
      this.orderID = orderID;
      setUserID(userID);
   }

   public Order() {
   }

   public String getOrderID() {
      return orderID;
   }

   public void setOrderID(String orderID) {
      this.orderID = orderID;
   }

   public Date getOrderTime() {
      return orderTime;
   }

   public void setOrderTime(Date orderTime) {
      this.orderTime = orderTime;
   }

   public int getUnit() {
      if (unit > 0) {
         return unit;
      } else {
         throw new ZeroException("unit cannot less than zero");
      }
   }


   public void setUnit(int unit) {
      this.unit = unit;
   }

   public BigDecimal getUnitPrice() {
      return unitPrice;
   }

   public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
   }

   public boolean getType() {
      return type;
   }

   public void setType(boolean type) {
      this.type = type;
   }

   public Date getModTime() {
      return modTime;
   }

   public void setModTime(Date modTime) {
      this.modTime = modTime;
   }

   public String getCustID() {
      return custID;
   }

   public void setCustID(String custID) {
      this.custID = custID;
   }

   public String getEmpID() {
      return empID;
   }

   public void setEmpID(String empID) {
      this.empID = empID;
   }

   public Integer getUserID() {
      return userID;
   }

   public Collection<Pay> getPays() {
      return pays;
   }

   public void setPays(Collection<Pay> pays) {
      this.pays = pays;
   }

   public Collection<Questionnaire> getQuestionnaires() {
      return questionnaires;
   }

   public void setQuestionnaires(Collection<Questionnaire> questionnaires) {
      this.questionnaires = questionnaires;
   }

   public void setUserID (Integer userID) throws ZeroException {
      if(userID>0){
         this.userID = userID;
      }else{
         throw new ZeroException("userID cannot less than zero");
      }
   }
   public boolean isType() {
      return type;
   }

   public String getCustName() {
      return custName;
   }

   public void setCustName(String custName) {
      this.custName = custName;
   }

   public String getEmpname() {
      return empname;
   }

   public void setEmpname(String empname) {
      this.empname = empname;
   }
   public Collection<Dist> getDists() {
      return dists;
   }

   public void setDists(Collection<Dist> dists) {
      this.dists = dists;
   }

   public Collection<Recv> getRecvs() {
      return recvs;
   }

}