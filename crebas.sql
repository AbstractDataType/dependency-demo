create database pidb;
use pidb;

create table userr
(
    userID   int         not null
        primary key,
    userName varchar(16) null,
    userPass varchar(16) null
);

create table account
(
    accID         char(30)    not null
        primary key,
    userID        int         not null,
    bank          varchar(50) null,
    bankAccountID bigint      null,
    balance       float(8, 2) null,
    modTime       datetime    null,
    constraint FK_user_account
        foreign key (userID) references userr (userID)
);

create table customer
(
    custID   char(30)     not null
        primary key,
    userID   int          not null,
    custName varchar(30)  null,
    address  varchar(100) null,
    zip      int          null,
    email    varchar(30)  null,
    modTime  datetime     null,
    constraint FK_user_cust
        foreign key (userID) references userr (userID)
);

create table emp
(
    empID   char(30)    not null
        primary key,
    userID  int         not null,
    empName varchar(30) null,
    modTime datetime    null,
    constraint FK_user_emp
        foreign key (userID) references userr (userID)
);

create table orderr
(
    orderID   char(30)    not null
        primary key,
    custID    char(30)    null,
    userID    int         not null,
    empID     char(30)    not null,
    orderTime date        null,
    unit      int         null,
    unitPrice float(8, 2) null,
    type      tinyint(1)  null,
    modTime   datetime    null,
    constraint FK_cust_order
        foreign key (custID) references customer (custID),
    constraint FK_emp
        foreign key (empID) references emp (empID),
    constraint FK_user_order
        foreign key (userID) references userr (userID)
);

create table analy
(
    analyID   char(30)    not null
        primary key,
    orderID   char(30)    not null,
    empID     char(30)    not null,
    userID    int         not null,
    analyTime date        null,
    unit      int         null,
    unitPrice float(8, 2) null,
    modTime   datetime    null,
    constraint FK_emp_analy
        foreign key (empID) references emp (empID),
    constraint FK_order_analy
        foreign key (orderID) references orderr (orderID),
    constraint FK_user_analy
        foreign key (userID) references userr (userID)
);

create table dist
(
    distID   char(30) not null
        primary key,
    orderID  char(30) not null,
    empID    char(30) not null,
    userID   int      not null,
    distTime date     null,
    unit     int      null,
    modTime  datetime null,
    constraint FK_emp_dist
        foreign key (empID) references emp (empID),
    constraint FK_order_dist
        foreign key (orderID) references orderr (orderID),
    constraint FK_user_dist
        foreign key (userID) references userr (userID)
);

create table pay
(
    payID   char(30)    not null
        primary key,
    orderID char(30)    not null,
    userID  int         not null,
    accID   char(30)    not null,
    empID   char(30)    not null,
    amount  float(8, 2) null,
    payTime date        null,
    modTime datetime    null,
    constraint FK_emp_prepay
        foreign key (empID) references emp (empID),
    constraint FK_prepay_acc
        foreign key (accID) references account (accID),
    constraint FK_prepay_bill
        foreign key (orderID) references orderr (orderID),
    constraint FK_user_prepay
        foreign key (userID) references userr (userID)
);

create table recv
(
    recvID   char(30) not null
        primary key,
    orderID  char(30) not null,
    userID   int      not null,
    empID    char(30) not null,
    recvTime date     null,
    modTime  datetime null,
    constraint FK_emp_recv
        foreign key (empID) references emp (empID),
    constraint FK_order_recv
        foreign key (orderID) references orderr (orderID),
    constraint FK_user_recv
        foreign key (userID) references userr (userID)
);

create table questionnaires
(
    questionnarieID char(30)    not null
        primary key,
    orderID         char(30)    null,
    recvID          char(30)    null,
    analyID         char(30)    null,
    userID          int         not null,
    distID          char(30)    null,
    fillTime        date        null,
    title           varchar(50) null,
    filler          varchar(30) null,
    analyResult     text        null,
    modTime         datetime    null,
    questionTitle   text        null,
    answer          text        null,
    constraint FK_analy_que
        foreign key (analyID) references analy (analyID),
    constraint FK_dist_que
        foreign key (distID) references dist (distID),
    constraint FK_order_ques
        foreign key (orderID) references orderr (orderID),
    constraint FK_recv_que
        foreign key (recvID) references recv (recvID),
    constraint FK_user_questionaires
        foreign key (userID) references userr (userID)
);

create procedure get_acc_by_conditions(IN accID char(30), IN bank varchar(50), IN bankAccountID int, IN userID int)
begin
    select *
    from `pidb`.account
    where (account.`accID` = accID or accID is null)
      and (account.`bank` = bank or bank is null)
      and (account.`bankAccountID` = bankAccountID or bankAccountID is null)
      and account.`userID` = userID;
end;

create procedure get_analy_by_conditions(IN recvID_in char(30), IN orderID_in char(30), IN empID_in char(30),
                                         IN userID_in int)
begin
    select pidb.analy.*, pidb.emp.empName
    from `pidb`.`analy`,
         pidb.emp
    where analy.empID = emp.empID
      and (analy.`analyID` = recvID_in or recvID_in is null)
      and (analy.`orderID` = orderID_in or orderID_in is null)
      and (analy.`empID` = empID_in or empID_in is null)
      and analy.`userID` = userID_in;
end;

create procedure get_cust_by_conditions(IN custID char(30), IN custName varchar(30), IN address varchar(100),
                                        IN zip int, IN email varchar(30), IN userID int)
begin
    select *
    from `pidb`.`customer`
    where (customer.`custID` = custID or custID is null)
      and (customer.`custName` = custName or custName is null)
      and (customer.`address` = address or address is null)
      and (customer.`zip` = zip or zip is null)
      and (customer.`email` = email or email is null)
      and customer.`userID` = userID;
end;

create procedure get_dist_by_conditions(IN distID_in char(30), IN orderID_in char(30), IN empID_in char(30),
                                        IN userID_in int)
begin
    select pidb.dist.*, pidb.emp.empName
    from `pidb`.`dist`,
         pidb.emp
    where dist.empID = emp.empID
      and (dist.`distID` = distID_in or distID_in is null)
      and (dist.`orderID` = orderID_in or orderID_in is null)
      and (dist.`empID` = empID_in or empID_in is null)
      and dist.`userID` = userID_in;
end;

create procedure get_emp_by_conditions(IN empID char(30), IN empName varchar(30), IN userID int)
begin
    select *
    from pidb.`emp`
    where (emp.`empID` = empID or empID is null)
      and (emp.`empName` = empName or empName is null)
      and emp.`userID` = userID;
end;

create procedure get_order_by_conditions(IN orderID_in char(30), IN custID_in char(30), IN empID_in char(30),
                                         IN userID_in int)
begin
    select pidb.orderr.*, pidb.emp.empName, pidb.customer.custName
    from `pidb`.`orderr`,
         pidb.emp,
         pidb.customer
    where pidb.orderr.custID = pidb.customer.custID
      and pidb.orderr.empID = pidb.emp.empID
      and (orderr.`orderID` = orderID_in or orderID_in is null)
      and (orderr.`custID` = custID_in or custID_in is null)
      and (orderr.`empID` = empID_in or empID_in is null)
      and orderr.`userID` = userID_in;
end;

create procedure get_pay_by_conditions(IN payID_in char(30), IN orderID_in char(30), IN accID_in char(30),
                                       IN empID_in char(30), IN userID_in int)
begin
    select pidb.pay.*, pidb.emp.empName
    from pidb.`pay`,
         pidb.emp
    where pay.empID = emp.empID
      and (pay.`payID` = payID_in or payID_in is null)
      and (pay.`orderID` = orderID_in or orderID_in is null)
      and (pay.`accID` = accID_in or accID_in is null)
      and (pay.`empID` = empID_in or empID_in is null)
      and pay.`userID` = userID_in;
end;

create procedure get_ques_by_conditions(IN questionnarieID char(30), IN orderID char(30), IN recvID char(30),
                                        IN analyID char(30), IN distID char(30), IN userID int)
begin
    select *
    from `pidb`.`questionnaires`
    where (questionnaires.`questionnarieID` = questionnarieID or questionnarieID is null)
      and (questionnaires.`orderID` = orderID or orderID is null)
      and (questionnaires.`recvID` = recvID or recvID is null)
      and (questionnaires.`analyID` = analyID or analyID is null)
      and (questionnaires.`distID` = distID or distID is null)
      and questionnaires.`userID` = userID;
end;

create procedure get_recv_by_conditions(IN recvID_in char(30), IN orderID_in char(30), IN empID_in char(30),
                                        IN userID_in int)
begin
    select pidb.recv.*, pidb.emp.empName
    from `pidb`.`recv`,
         pidb.emp
    where recv.empID = emp.empID
      and (recv.`recvID` = recvID_in or recvID_in is null)
      and (recv.`orderID` = orderID_in or orderID_in is null)
      and (recv.`empID` = empID_in or empID_in is null)
      and recv.`userID` = userID_in;
end;

