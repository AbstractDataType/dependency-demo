package quesmanagement.entity;


import quesmanagement.utils.ZeroException;

import java.util.Date;

public class Recv {
   
   private String recvID;
   private Date recvTime;
   private Date modTime;
   private Questionnaire questionnaire;
   private String empID;
   private String empName;
   private String orderID;
   private Integer userID;

   public Recv(String recvID, String orderID, Integer userID) {
      this.recvID = recvID;
      this.orderID = orderID;
      setUserID(userID);
   }

   public Recv() {
   }

   public Recv(String recvID, Integer userID) {
      this.recvID = recvID;
      setUserID(userID);
   }

   public Recv(String recvID, Date recvTime, Questionnaire questionnaire, String empID, Integer userID,String orderID) {
      this.recvID = recvID;
      this.recvTime = recvTime;
      this.questionnaire = questionnaire;
      this.empID = empID;
      setUserID(userID);
      this.orderID=orderID;
   }

   public String getEmpName() {
      return empName;
   }

   public void setEmpName(String empName) {
      this.empName = empName;
   }

   public String getOrderID() {
      return orderID;
   }

   public void setOrderID(String orderID) {
      this.orderID = orderID;
   }

   public String getRecvID() {
      return recvID;
   }

   public void setRecvID(String recvID) {
      this.recvID = recvID;
   }

   public Date getRecvTime() {
      return recvTime;
   }

   public void setRecvTime(Date recvTime) {
      this.recvTime = recvTime;
   }

   public Date getModTime() {
      return modTime;
   }

   public void setModTime(Date modTime) {
      this.modTime = modTime;
   }

   public Questionnaire getQuestionnaire() {
      return questionnaire;
   }

   public void setQuestionnaire(Questionnaire questionnaire) {
      this.questionnaire = questionnaire;
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

   public void setUserID (Integer userID) throws ZeroException {
      if(userID>0){
         this.userID = userID;
      }else{
         throw new ZeroException("userID cannot less than zero");
      }
   }
}