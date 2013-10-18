    create table TEST.Customer (
        CUSTOMER_NO varchar(32) not null,
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        CUSTOMER_NAME varchar(128),
        TAX_ID varchar(16),
        primary key (CUSTOMER_NO)
    );

    create table TEST.CustomerAddress (
        ADDRESS_NO INTEGER not null,
        CUSTOMER_NO varchar(32) not null,
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        STREET varchar(128),
        STREET_NO varchar(8),
        primary key (ADDRESS_NO, CUSTOMER_NO)
    );

    create table TEST.Invoice (
        INVOICE_NO varchar(32) not null,
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        INVOICE_DATE date,
        BARCODE varchar(100),
        primary key (INVOICE_NO)
    );

    create table TEST.InvoiceCustomer (
        INVOICE_NO varchar(32) not null,
        CUSTOMER_NO varchar(32),
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        ROLE_ID INTEGER,
        ADDRESS_NO INTEGER,
        primary key (INVOICE_NO)
    );

    create table TEST.InvoiceLine (
        LINE_NO INTEGER not null,
        INVOICE_NO varchar(32) not null,
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        TYPE INTEGER,
        AMOUNT DOUBLE,
        CUSTOMER_NO varchar(32),
        primary key (LINE_NO, INVOICE_NO)
    );
    
    create table TEST.InvoiceSubLine (
    	SUB_LINE_NO INTEGER not null,
        LINE_NO INTEGER not null,
        INVOICE_NO varchar(32) not null,
        RULE_CD INTEGER,
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        NAME varchar(32),
        primary key (SUB_LINE_NO, LINE_NO, INVOICE_NO)
    );
    
    create table TEST.InvoiceRule (
        RULE_CD INTEGER not null,
        INVOICE_NO varchar(32) not null,
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        RULENAME varchar(32),
        primary key (RULE_CD, INVOICE_NO)
    );
    
    create table TEST.InvoiceSubRule (
    	SUBRULE_CD INTEGER not null,
        RULE_CD INTEGER not null,
        INVOICE_NO varchar(32) not null,
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        SUBRULENAME varchar(32),
        primary key (SUBRULE_CD, RULE_CD, INVOICE_NO)
    );
    
    create table TEST.CompanyUser (
        ID INTEGER not null,
        ROLE_CD INTEGER,
        SEX INTEGER,
        ADDITIONAL_ROLES varchar(32),
        LAST_MODIFIED timestamp not null,
        LAST_MODIFIED_BY varchar(128),
        primary key (ID)
    );
    
    create table TEST.FOOBAR_TPCH (
        ID INTEGER not null,
        DISCR varchar(8),
        FOO varchar(8),
        BAR varchar(8),
        primary key (ID)
    );
    
    create table TEST.FOOBAR_TPSCH (
        ID INTEGER not null,
        DISCR varchar(8),
        primary key (ID)
    );
    
    create table TEST.BAR_TPSCH (
    	BAR_ID INTEGER not null,
        BAR varchar(10) not null,
        primary key (BAR_ID)
    );
    
    create table TEST.FOO_TPSCH (
    	FOO_ID INTEGER not null,
        FOO varchar(10) not null,
        primary key (FOO_ID)
    );
    
    alter table TEST.BAR_TPSCH 
        add constraint FK_BAR_ID_2_ID 
        foreign key (BAR_ID) 
        references TEST.FOOBAR_TPSCH;
        
    alter table TEST.FOO_TPSCH 
        add constraint FK_FOO_ID_2_ID 
        foreign key (FOO_ID) 
        references TEST.FOOBAR_TPSCH;

    alter table TEST.CustomerAddress 
        add constraint FK_CUST_ADDR_2_CUST 
        foreign key (CUSTOMER_NO) 
        references TEST.Customer;

    alter table TEST.InvoiceLine 
        add constraint FK_INV_LINE_2_INV
        foreign key (INVOICE_NO) 
        references TEST.Invoice;
        
    alter table TEST.InvoiceLine 
        add constraint FK_INV_LINE_2_CUST
        foreign key (CUSTOMER_NO) 
        references TEST.Customer;

    alter table TEST.InvoiceRule 
        add constraint FK_INV_RULE_2_INV
        foreign key (INVOICE_NO) 
        references TEST.Invoice;
        
	alter table TEST.InvoiceSubLine 
        add constraint FK_INV_SLINE_2_LINE
        foreign key (LINE_NO, INVOICE_NO) 
        references TEST.InvoiceLine;
        
    alter table TEST.InvoiceSubLine 
        add constraint FK_INV_SLINE_2_RULE 
        foreign key (RULE_CD, INVOICE_NO) 
        references TEST.InvoiceRule;

    alter table TEST.InvoiceSubRule 
        add constraint FK_INV_SRULE_2_RULE
        foreign key (RULE_CD, INVOICE_NO) 
        references TEST.InvoiceRule;
        
    alter table TEST.InvoiceCustomer 
        add constraint FK_INV_CUST_2_CUST
        foreign key (CUSTOMER_NO) 
        references TEST.Customer;
  
    alter table TEST.InvoiceCustomer 
        add constraint FK_INV_CUST_2_INV
        foreign key (INVOICE_NO) 
        references TEST.Invoice;
        
--Users, UserProfiles, Roles

        CREATE TABLE "TEST"."ROLES"
		(
		   ID int PRIMARY KEY NOT NULL,
		   ROLE_DSC varchar(90) DEFAULT '' NOT NULL
		);
        
        CREATE TABLE "TEST"."USERS"
		(
		   ID int PRIMARY KEY NOT NULL,
		   USR_ID char(8) DEFAULT '' NOT NULL,
		   USR_NM char(30) DEFAULT '' NOT NULL,
		   ROLE_ID int DEFAULT 0
		);
		
		ALTER TABLE "TEST"."USERS"
		ADD CONSTRAINT FK_USERROLE
		FOREIGN KEY (ROLE_ID)
		REFERENCES "TEST"."ROLES"(ID);
		
		CREATE TABLE "TEST"."USERPROFILE"
		(
		   ID int NOT NULL,
		   PROFILE_ID int NOT NULL,
		   PROF_NM char(30) DEFAULT '' NOT NULL
		);
		
		alter table TEST.USERPROFILE 
        add constraint FK_PROFILE_2_USER
        foreign key (ID) 
        references TEST.USERS;
