package quesmanagement.entity;


import quesmanagement.utils.ZeroException;

public class User {
   private Integer userID;
   private String userName;
   private String userPass;

   public User(String userName, String userPass) {
      this.userName = userName;
      this.userPass = userPass;
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

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getUserPass() {
      return userPass;
   }

   public void setUserPass(String userPass) {
      this.userPass = userPass;
   }

   public User(Integer userID, String userName, String userPass) {
      setUserID(userID);
      this.userName = userName;
      this.userPass = userPass;
   }
   public User() {
   }
}