package quesmanagement.entity;


import quesmanagement.utils.ZeroException;

import java.util.Collection;
import java.util.Date;

public class Dist {
   
   private String distID;
   private Date distTime;
   private int unit;
   private Date modTime;
   private Collection<Questionnaire> questionnaires;
   private String empID;
   private String empName;
   private String orderID;
   private Integer userID;

   public Dist(String distID, String orderID, Integer userID) {
      this.distID = distID;
      this.orderID = orderID;
      setUserID(userID);
   }

   public Dist(String distID, Integer userID) {
      this.distID = distID;
      setUserID(userID);
   }

   public Dist() {
   }

   public Dist(String distID, Date distTime, Collection<Questionnaire> questionnaires, String empID, String orderID, Integer userID) {
      this.distID = distID;
      this.distTime = distTime;
      this.questionnaires = questionnaires;
      this.empID = empID;
      this.orderID = orderID;
      setUserID(userID);
   }

   public String getDistID() {
      return distID;
   }

   public void setDistID(String distID) {
      this.distID = distID;
   }

   public Date getDistTime() {
      return distTime;
   }

   public void setDistTime(Date distTime) {
      this.distTime = distTime;
   }

   public int getUnit() {
      return unit;
   }

   public void setUnit(int unit) {
      this.unit = unit;
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

}