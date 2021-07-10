package quesmanagement.entity;

import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class Analy {
   
   private String analyID;
   private Date analyTime;
   private int unit;
   private BigDecimal unitPrice;
   private Date modTime;
   private Collection<Questionnaire> questionnaires;
   private String orderID;
   private Integer userID;
   private String empID;
   private String empName;

   public Analy(String analyID, Date analyTime, int unit,
                BigDecimal unitPrice, Collection<Questionnaire> questionnaires,
                String orderID, Integer userID, String empID) {
      this.analyID = analyID;
      this.analyTime = analyTime;
      this.unit = unit;
      this.unitPrice = unitPrice;
      this.questionnaires = questionnaires;
      this.orderID = orderID;
      setUserID(userID);
      this.empID = empID;
   }

   public String getEmpID() {
      return empID;
   }

   public void setEmpID(String empID) {
      this.empID = empID;
   }

   public Analy() {
   }

   public Analy(String analyID, Integer userID) {
      this.analyID = analyID;
      setUserID(userID);
   }

   public Analy(String analyID, String orderID, Integer userID) {
      this.analyID = analyID;
      this.orderID = orderID;
      setUserID(userID);
   }

   public String getAnalyID() {
      return analyID;
   }

   public void setAnalyID(String analyID) {
      this.analyID = analyID;
   }

   public Date getAnalyTime() {
      return analyTime;
   }

   public void setAnalyTime(Date analyTime) {
      this.analyTime = analyTime;
   }

   public int getUnit() {
      return unit;
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

   public Date getModTime() {
      return modTime;
   }

   public void setModTime(Date modTime) {
      this.modTime = modTime;
   }

   public Collection<Questionnaire> getQuestionnaires() {
      return questionnaires;
   }

   public void setQuestionnaires(Collection<Questionnaire> questionnaires) {
      this.questionnaires = questionnaires;
   }

   public String getEmpName() {
      return empName;
   }

   public void setEmpName(String empName) {
      this.empName = empName;
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

   public String getOrderID() {
      return orderID;
   }

   public void setOrderID(String orderID) {
      this.orderID = orderID;
   }

}