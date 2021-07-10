package quesmanagement.entity;

import quesmanagement.utils.ZeroException;
import java.util.Date;


public class Questionnaire {
   private String questionnarieID;
   private Date fillTime;
   private String title;
   private String filler;
   private String analyResult;
   private String questionTitle;
   private String answer;
   private Date modTime;
   private String distID;
   private String recvID;
   private String analyID;
   private Integer userID;
   private String orderID;

   public Questionnaire(String questionnarieID, Integer userID) {
      this.questionnarieID = questionnarieID;
      setUserID(userID);
   }

   public Questionnaire() {
   }

   /**
    *
    * @param questionnarieID
    * @param orderID
    * @param distID
    * @param recvID
    * @param analyID
    * @param userID
    */
   public Questionnaire(String questionnarieID, String orderID, String distID,
                        String recvID, String analyID, Integer userID){
      setQuestionnarieID(questionnarieID);
      setDistID(distID);
      setOrderID(orderID);
      setRecvID(recvID);
      setUserID(userID);
      setAnalyID(analyID);
   }

   public Questionnaire(String questionnarieID, String title, String questionTitle, Integer userID) {
      this.questionnarieID = questionnarieID;
      this.title = title;
      this.questionTitle = questionTitle;
      setUserID(userID);
   }

   public Questionnaire(String questionnarieID,String orderID , Integer userID) {
      this.questionnarieID = questionnarieID;
      setUserID(userID);
      this.orderID = orderID;
   }

   public Questionnaire(String questionnarieID, Date fillTime, String filler, String analyResult, String answer, String distID, String recvID, String analyID, Integer userID, String orderID) {
      this.questionnarieID = questionnarieID;
      this.fillTime = fillTime;
      this.filler = filler;
      this.analyResult = analyResult;
      this.answer = answer;
      this.distID = distID;
      this.recvID = recvID;
      this.analyID = analyID;
      setUserID(userID);
      this.orderID = orderID;
   }


   public String getQuestionnarieID() {
      return questionnarieID;
   }

   public void setQuestionnarieID(String questionnarieID) {
      this.questionnarieID = questionnarieID;
   }

   public Date getFillTime() {
      return fillTime;
   }

   public void setFillTime(Date fillTime) {
      this.fillTime = fillTime;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getFiller() {
      return filler;
   }

   public void setFiller(String filler) {
      this.filler = filler;
   }

   public String getAnalyResult() {
      return analyResult;
   }

   public void setAnalyResult(String analyResult) {
      this.analyResult = analyResult;
   }

   public String getQuestionTitle() {
      return questionTitle;
   }

   public void setQuestionTitle(String questionTitle) {
      this.questionTitle = questionTitle;
   }

   public String getAnswer() {
      return answer;
   }

   public void setAnswer(String answer) {
      this.answer = answer;
   }

   public Date getModTime() {
      return modTime;
   }

   public void setModTime(Date modTime) {
      this.modTime = modTime;
   }

   public String getDistID() {
      return distID;
   }

   public void setDistID(String distID) {
      this.distID = distID;
   }

   public String getRecvID() {
      return recvID;
   }

   public void setRecvID(String recvID) {
      this.recvID = recvID;
   }

   public String getAnalyID() {
      return analyID;
   }

   public void setAnalyID(String analyID) {
      this.analyID = analyID;
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