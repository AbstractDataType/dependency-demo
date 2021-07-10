package quesmanagement.entity;

import quesmanagement.utils.ZeroException;

import java.math.BigDecimal;
import java.util.Date;


public class Account {

   private String accID;
   private String bank;
   private Long bankAccountID;
   private BigDecimal balance;
   private Date modTime;
   private Integer userID;

   public Account(String accID, String bank, Long bankAccountID, BigDecimal balance, Integer userID) {
      this.accID = accID;
      this.bank = bank;
      this.bankAccountID = bankAccountID;
      this.balance = balance;
      setUserID(userID);
   }

   public Account() {
   }

   public Account(String accID, Integer userID) {
      this.accID = accID;
      setUserID(userID);
   }

   public Account(String accID, String bank, Long bankAccountID, Integer userID) {
      this.accID = accID;
      this.bank = bank;
      this.bankAccountID = bankAccountID;
      setUserID(userID);
   }


   public String getAccID() {
      return accID;
   }

   public void setAccID(String accID) {
      this.accID = accID;
   }

   public String getBank() {
      return bank;
   }

   public void setBank(String bank) {
      this.bank = bank;
   }

   public Long getBankAccountID() {
      return bankAccountID;
   }

   public void setBankAccountID(Long bankAccountID) throws ZeroException {
      if ( bankAccountID!=null && bankAccountID>0 ){
      this.bankAccountID = bankAccountID;
   }else{
         throw new ZeroException("bankAccountID cannot less than zero");
      }
   }

   public BigDecimal getBalance() {
      return balance;
   }

   public void setBalance(BigDecimal balance) {
      this.balance = balance;
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