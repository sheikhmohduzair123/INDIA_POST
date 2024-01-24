-- Active: 1663495604311@@127.0.0.1@5432@bnglpostserver@public
alter sequence public.postal_user_id_seq restart 2;
alter sequence public.zone_id_seq restart 2;
alter sequence public.division_id_seq restart 8;
alter sequence public.district_id_seq restart 54;
alter sequence public.thana_id_seq restart 402;
alter sequence public.master_address_id_seq restart 1016;
alter sequence public.postal_services_id_seq restart 39;
alter sequence public.config_id_seq restart 8;

alter sequence public.rate_table_id_seq restart 76;

/* INSERT QUERY */
INSERT INTO public.role(id, name, status, displayname) 
VALUES 
  (1, 'ROLE_ADMIN', 0, 'Admin');
/* INSERT QUERY */
INSERT INTO public.role(id, name, status, displayname) 
VALUES 
  (
    2, 'ROLE_FRONT_DESK_USER', 0, 'Front Desk User'
  );
/* INSERT QUERY */
INSERT INTO public.role(id, name, status, displayname) 
VALUES 
  (
    3, 'ROLE_PO_USER', 0, 'Post Office User'
  );
/* INSERT QUERY */
INSERT INTO public.role(id, name, status, displayname) 
VALUES 
  (4, 'ROLE_RMS_USER', 0, 'RMS User');

INSERT INTO public.postal_user(
  id, account_expired, account_locked, activationcode, credentials_expired, dob, email, account_enabled, first_login, identificationid, lastlogin, name, password, postalcode, role, username, status,createdOn,updatedOn,createdby,updatedby)
  VALUES (1, false, false,null,false, '1995-04-05', 'admin@gmail.com', true, false,null, null, 'Admin','$2a$04$LS6bwiRJ4QWIQnb0qb21HOJsa34sxWo/pLPxszAKnjwVpXI69AQra', 0,1, 'admin@gmail.com',0,'2020-01-31 07:00:00.487','2020-01-31 07:00:00.487',null,null);

insert into public.control (id,status,clientmasterdatasynctimestamp, servermasterdataupdatetimestamp) values (1,0,'2020-01-31 07:00:00.487','2020-01-31 07:00:00.487');

INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (1,'2020-01-23 14:00','2020-01-23 14:00','Guaranteed Express Post','GE',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (2,'2020-01-23 14:00','2020-01-23 14:00','General Letter Postage','GL',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (3,'2020-01-23 14:00','2020-01-23 14:00','Document Postage','DP',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (4,'2020-01-23 14:00','2020-01-23 14:00','Registeration Fee','RF',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (5,'2020-01-23 14:00','2020-01-23 14:00','GEP Postage','GP',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (6,'2020-01-23 14:00','2020-01-23 14:00','AD Charge','AC',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (7,'2020-01-23 14:00','2020-01-23 14:00','Registered Parcel Postage','RP',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (8,'2020-01-23 14:00','2020-01-23 14:00','Unregistered Packets','UP',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (9,'2020-01-23 14:00','2020-01-23 14:00','Insurance Fees','UPIF',8,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (10,'2020-01-23 14:00','2020-01-23 14:00','Invitation Cards/Greeting Cards','IC',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (11,'2020-01-23 14:00','2020-01-23 14:00','Insurance Fees','ICIF',10,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (12,'2020-01-23 14:00','2020-01-23 14:00','Cash On Delivery','COD',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (13,'2020-01-23 14:00','2020-01-23 14:00','Cash On Delivery','COD',8,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (14,'2020-01-23 14:00','2020-01-23 14:00','To Pay','TP',1,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (15,'2020-01-23 14:00','2020-01-23 14:00','To Pay','TP',8,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (16,'2020-01-23 14:00','2020-01-23 14:00','Postcard Single','PS',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (17,'2020-01-23 14:00','2020-01-23 14:00','Postcards Reply','PR',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (18,'2020-01-23 14:00','2020-01-23 14:00','Business Reply Envelopes','BRE',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (19,'2020-01-23 14:00','2020-01-23 14:00','Business Reply Cards','BRC',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (20,'2020-01-23 14:00','2020-01-23 14:00','Business Reply Enevelop & Cards Fee For Permit','BRECF',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (21,'2020-01-23 14:00','2020-01-23 14:00','Registered Newspapers Single Copy','RNSC',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (22,'2020-01-23 14:00','2020-01-23 14:00','Registered Newspapers Packets','RNP',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (23,'2020-01-23 14:00','2020-01-23 14:00','Blind Literature Packets','BLP',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (24,'2020-01-23 14:00','2020-01-23 14:00','Postal Identity Card','PIC',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (25,'2020-01-23 14:00','2020-01-23 14:00','Certificate of Posting','COP',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (26,'2020-01-23 14:00','2020-01-23 14:00','Every 3 Articles or Less','EAL',25,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (27,'2020-01-23 14:00','2020-01-23 14:00','Book of 50 forms of Certificate of Posting','BCP',25,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (28,'2020-01-23 14:00','2020-01-23 14:00','Enquiry Fee','EF',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (29,'2020-01-23 14:00','2020-01-23 14:00','Value Payable Articles','VPA',28,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (30,'2020-01-23 14:00','2020-01-23 14:00','Retention of V.P. articles beyond 7 dayes','RVPA',28,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (31,'2020-01-23 14:00','2020-01-23 14:00','Withdrawal From The Post','WFTP',28,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (32,'2020-01-23 14:00','2020-01-23 14:00','Correction or Alteration of Address  of Postal Art','CAAPA',28,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (33,'2020-01-23 14:00','2020-01-23 14:00','Rental Charges for Post Boxes and Bags','RCPBB',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (34,'2020-01-23 14:00','2020-01-23 14:00','Post Box or Bag Rented for One Year','PBBRY',33,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (35,'2020-01-23 14:00','2020-01-23 14:00','Post Box or Bag Rented for Six Month or Less','PBBRS',33,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (36,'2020-01-23 14:00','2020-01-23 14:00','Rental Charges for Metropolitan Areas','RCMA',NULL,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (37,'2020-01-23 14:00','2020-01-23 14:00','District Post Offices for One Year','DPOOY',36,0,1,1);
INSERT INTO postal_services(id,createdon, updatedon, postal_service_name,servicecode,parentserviceid,status,createdby_id,updatedby_id) VALUES (38,'2020-01-23 14:00','2020-01-23 14:00','District Post Offices for Six Months or Less','DPOSM',36,0,1,1);

/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    1, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, NULL, NULL, 
    0, 0, 1, NULL, NULL, NULL, NULL, NULL, 
    NULL, 0, NULL, NULL, NULL, NULL, NULL, 
    NULL, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    2, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 2, NULL, NULL, 0, NULL, 1, 100, 0, NULL, 
    0, 5, 20, NULL, 0, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    3, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 2, NULL, NULL, 0, NULL, 1, 100, 0, NULL, 
    0, 10, 50, NULL, 20, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    4, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 2, NULL, NULL, 0, NULL, 1, 100, 0, NULL, 
    0, 15, 100, NULL, 50, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    5, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 3, NULL, NULL, 0, NULL, 1, 2000, 0, 
    NULL, 0, 30, 500, NULL, 100, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    6, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 3, NULL, NULL, 0, NULL, 1, 2000, 0, 
    NULL, 0, 40, 1000, NULL, 500, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    7, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 3, NULL, NULL, 0, NULL, 1, 2000, 0, 
    NULL, 0, 50, 1500, NULL, 1000, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    8, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 3, NULL, NULL, 0, NULL, 1, 2000, 0, 
    NULL, 0, 60, 2000, NULL, 1500, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    9, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 50, 2500, NULL, 2000, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    10, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 60, 3000, NULL, 2500, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    11, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 65, 3500, NULL, 3000, 1,1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    12, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 70, 4000, NULL, 3500, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    13, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 75, 4500, NULL, 4000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    14, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 85, 5000, NULL, 4500, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    15, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 105, 6000, NULL, 5000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    16, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 115, 7000, NULL, 6000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    17, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 125, 8000, NULL, 7000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    18, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 135, 9000, NULL, 8000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    19, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 145, 10000, NULL, 9000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    20, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 220, 15000, NULL, 10000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    21, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 270, 20000, NULL, 15000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    22, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, NULL, 
    1, 7, NULL, NULL, 0, NULL, 1, 30000, 0, 
    NULL, 0, 310, 30000, NULL, 20000, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    23, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, NULL, 1, 5, 
    0, 4, NULL, NULL, NULL, NULL, NULL, 2000, 
    0, NULL, NULL, NULL, NULL, NULL, NULL, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    24, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, NULL, 1, 15, 
    0, 5, NULL, NULL, NULL, NULL, NULL, 30000, 
    0, NULL, NULL, NULL, NULL, NULL, NULL, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    25, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, NULL, 1, 5, 
    0, 6, NULL, NULL, NULL, NULL, NULL, 30000, 
    0, NULL, NULL, NULL, NULL, NULL, NULL, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    26, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, NULL, NULL, 
    1, 8, NULL, NULL, 0, NULL, 2, 1000, 0, 
    NULL, 0, 5, 100, NULL, 0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    27, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, NULL, NULL, 
    1, 8, NULL, NULL, 0, NULL, 2, 1000, 0, 
    NULL, 5, 3, 1000, 100, 100, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    28, NULL, NULL, NULL, NULL, NULL, 0, 25, 
    NULL, 0, 1000, 5, 0, 8, NULL, 1, 9, NULL, 
    NULL, 2, 10000, 0, NULL, 0, NULL, NULL, 
    NULL, NULL, NULL, NULL, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    29, NULL, NULL, NULL, NULL, NULL, 25, 
    10, 500, 1000, 10000, 5, 0, 8, NULL, 1, 
    9, NULL, NULL, 2, 10000, 0, NULL, 0, NULL, 
    NULL, NULL, NULL, NULL, NULL, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    30, NULL, 0, 1, 0, 1, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 5, 30, NULL, 
    0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    31, NULL, 0, 1, 0, 1, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 6, 50, NULL, 
    30, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    32, NULL, 0, 1, 0, 2, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 6, 30, NULL, 
    0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    33, NULL, 0, 1, 0, 2, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 7, 50, NULL, 
    30, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    34, NULL, 0, 1, 0, 3, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 6, 30, NULL, 
    0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    35, NULL, 0, 1, 0, 3, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 7, 50, NULL, 
    30, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    36, NULL, 0, 1, 0, 4, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 5, 30, NULL, 
    0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    37, NULL, 0, 1, 0, 4, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 6, 50, NULL, 
    30, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    38, NULL, 0, 1, 0, 5, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 6, 30, NULL, 
    0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    39, NULL, 0, 1, 0, 5, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 7, 50, NULL, 
    30, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    40, NULL, 0, 1, 0, 6, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 6, 30, NULL, 
    0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    41, NULL, 0, 1, 0, 6, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 7, 50, NULL, 
    30, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    42, NULL, 0, 1, 0, 7, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 6, 30, NULL, 
    0, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    43, NULL, 0, 1, 0, 7, NULL, NULL, NULL, 
    NULL, NULL, 5, 4, NULL, NULL, 1, 10, 5, 
    10, 0, NULL, 1, 50, 0, NULL, 0, 7, 50, NULL, 
    30, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    44, NULL, NULL, NULL, NULL, NULL, 0, 25, 
    NULL, 0, 1000, 5, 0, 10, NULL, 1, 11, NULL, 
    NULL, 2, 10000, 0, NULL, 0, NULL, NULL, 
    NULL, NULL, NULL, NULL, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    45, NULL, NULL, NULL, NULL, NULL, 25, 
    10, 500, 1000, 10000, 5, 0, 10, NULL, 
    1, 11, NULL, NULL, 2, 10000, 0, NULL, 
    0, NULL, NULL, NULL, NULL, NULL, NULL, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    46, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, 5, 0, 
    12, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, 
    NULL, NULL, NULL, NULL, NULL, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    49, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 8, 100, 
    0, 15, NULL, NULL, 0, NULL, 0, NULL, 0, 
    NULL, NULL, NULL, NULL, NULL, NULL, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    47, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 1, 100, 
    0, 14, NULL, NULL, 0, NULL, 0, NULL, 0, 
    NULL, NULL, NULL, NULL, NULL, NULL, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    48, NULL, NULL, NULL, NULL, NULL, NULL, 
    NULL, NULL, NULL, NULL, 5, 0, 8, 5, 0, 
    13, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, 
    NULL, NULL, NULL, NULL, NULL, 1, 1
  );
INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    50, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, 2, 0, 16, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    51, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, 4, 0, 17, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    52, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, NULL,  1, 18, NULL, NULL, 0, NULL, 2, 1000, 0, NULL, 0, 5, 20, NULL, 0, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    53, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, NULL, 1, 18, NULL, NULL, 0, NULL, 2, 1000, 0, NULL, 5, 5, 1000, 20, 20, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    54, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, 2, 0, 19, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    55, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, 400, 0, 20, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    56, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, NULL, 1, 21, NULL, NULL, 0, NULL, 2, 1000, 0, NULL, 0, 1, 100, NULL, 0, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
   57, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, NULL, 1, 21, NULL, NULL, 0, NULL, 2, 1000, 0, NULL,	1, 1, 1000, 100, 100, 1, 1
  );


INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
   58, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0,  NULL,  NULL, 1, 22,  NULL,  NULL, 0,  NULL, 2, 2000,  0,  NULL, 0, 1, 100,  NULL, 0,  1, 1
  );


INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    59, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, NULL, 1, 22, NULL, NULL, 0, NULL, 2, 2000,	0, NULL, 1, 1, 2000, 100, 100, 1, 1
  );


INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    60, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, 0, 0, 23, NULL, NULL, 0, NULL, 0, 8000, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    61, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, NULL, 50, 0, 24, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    62, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, NULL, NULL, 0, 0, 25, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    63, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 25, 3, 0, 26, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    64, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 25, 10, 0, 27, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    65, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, NULL, NULL, 0, 0, 28, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    66, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 28, 2, 0, 29, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    67, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 28, 2, 0, 30, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );


INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    68, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 28, 5, 0, 31, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );


INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    69, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 28, 5, 0, 32, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    70, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, NULL, NULL, 0, 0, 33, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    71, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 33, 300, 0, 34, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    72, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 33, 200, 0, 35, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    73, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 6, NULL, 0, 0, 36, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    74, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 36, 200, 0, 37, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

INSERT INTO public.rate_table(
  id, createdon, locationwiserate_baseprice, 
  locationwiserate_fromid, locationwiserate_price, 
  locationwiserate_toid, parcelvaluewiserate_baseprice, 
  parcelvaluewiserate_price, parcelvaluewiserate_valuefraction, 
  parcelvaluewiserate_valuestartrange, 
  parcelvaluewiserate_valueuptorange, 
  rateservicecategory_expecteddelivery, 
  rateservicecategory_locationdependency, 
  rateservicecategory_parentserviceid, 
  rateservicecategory_price, rateservicecategory_pricetype, 
  rateservicecategory_serviceid, 
  rateservicecategory_taxrate1, rateservicecategory_taxrate2, 
  rateservicecategory_valuedependency, 
  rateservicecategory_valuemaxlimit, 
  rateservicecategory_weightdependency, 
  rateservicecategory_weightmaxlimit, 
  status, updatedon, weightwiserate_baseprice, 
  weightwiserate_price, weightwiserate_weightendrange, 
  weightwiserate_weightfractionfactor, 
  weightwiserate_weightstartrange, 
  createdby_id, updatedby_id
) 
VALUES 
  (
    75, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5, 0, 36, 100, 0, 38, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1
  );

/* INSERT QUERY */
INSERT INTO public.zone(
  id, createdon, status, updatedon, 
  zone, createdby_id, updatedby_id
) 
VALUES 
  (
    1, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Zone1', 1, 1
  );


/* INSERT QUERY */
INSERT INTO public.division(
  id, createdon, status, updatedon, 
  division, zone_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    6, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.division(
  id, createdon, status, updatedon, 
  division, zone_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    3, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.division(
  id, createdon, status, updatedon, 
  division, zone_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    7, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.division(
  id, createdon, status, updatedon, 
  division, zone_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    1, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.division(
  id, createdon, status, updatedon, 
  division, zone_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    5, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.division(
  id, createdon, status, updatedon, 
  division, zone_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    2, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.division(
  id, createdon, status, updatedon, 
  division, zone_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    4, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 1, 1, 1
  );

/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    25, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 4, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    4, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    51, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    35, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 6, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    8, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    45, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 4, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    13, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    5, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 2, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    16, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    2, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 3, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    33, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    1, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    11, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 2, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    12, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    29, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    21, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    6, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    7, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 2, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    43, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    40, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    32, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    52, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 4, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    53, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 6, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    47, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    39, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    31, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    18, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    24, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 4, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    36, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    23, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    30, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    42, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    10, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 2, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    28, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    9, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    3, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    22, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Meherpur', 7, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    34, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 6, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    17, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 3, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    49, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    15, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    37, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 4, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    19, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    26, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 4, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    46, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 6, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    41, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 6, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    38, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 3, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    50, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    20, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Panchagarh', 4, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    27, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    48, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 3, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    14, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 5, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.district(
  id, createdon, status, updatedon, 
  district, division_id, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    44, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 4, 1, 1
  );

/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    1, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    21, 'Singra', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    2, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Chitalmari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    3, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Dupchachia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    4, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Pirganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    5, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Ulipur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    6, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Kalmakanda', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    7, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Muktagachha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    8, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    28, 'Doulatpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    9, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    2, 'Srimangal', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    10, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Tangail Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    11, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Nawabganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    12, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    1, 'Maksudpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    13, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    32, 'Rafayetpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    14, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Dhangora', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    15, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    33, 'Taala', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    16, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Kendua', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    17, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Bhairob', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    18, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    49, 'Bandar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    19, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    8, 'Sirajdikhan', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    20, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Dhanmondi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    21, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    5, 'Malandah', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    22, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    32, 'Kumarkhali', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    23, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    6, 'Kathalia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    24, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    34, 'Bhola Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    25, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Itna', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    26, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    35, 'Amtali', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    27, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Sonatola', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    28, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Trishal', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    29, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Susung Durgapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    30, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Kalihati', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    31, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    15, 'Belabo', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    32, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    3, 'Magura Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    33, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    29, 'Chuadanga Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    34, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    49, 'Rupganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    35, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Pakundia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    36, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Sariakandi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    37, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Gopalganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    38, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Birganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    39, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Durgapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    40, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    5, 'Islampur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    41, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    27, 'Bholahat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    42, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Dharmapasha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    43, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Noapara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    44, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Bera', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    45, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Basail', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    46, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    49, 'Baidder Bazar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    47, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Mahadebpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    48, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    28, 'Manikganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    49, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    21, 'Gopalpur UPO', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    50, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Bagerhat Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    51, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Fenchuganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    52, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    7, 'Bakshigonj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    53, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    33, 'kalaroa', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    54, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    34, 'Hatshoshiganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    55, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    28, 'Gheor', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    56, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    33, 'Satkhira Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    57, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Bhaluka', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    58, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Nabiganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    59, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Kurigram Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    60, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    33, 'Debbhata', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    61, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Badalgachhi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    62, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    20, 'Dabiganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    63, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Sarsa', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    64, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Chilmari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    65, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    51, 'Akkelpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    66, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Sirajganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    67, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    49, 'Araihazar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    68, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Bagharpara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    69, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Atpara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    70, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Agailzhara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    71, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Bhabaniganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    72, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    28, 'Singari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    73, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    32, 'Mirpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    74, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    15, 'Raypura', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    75, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    34, 'Doulatkhan', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    76, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Nitpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    77, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Balaganjgfhfghj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    78, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Azmireeganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    79, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Kashkaolia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    80, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Banwarinagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    81, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    9, 'Kotchandpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    82, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Prasadpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    83, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Kishoreganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    84, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Fulbaria', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    85, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Biral', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    86, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    24, 'Dimla', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    87, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    47, 'Jajira', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    88, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Phulpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    89, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Bianibazar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    90, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Niamatpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    91, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Rayenda', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    92, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Khilgaon', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    93, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Dinajpur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    94, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Nikli', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    95, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Boalmari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    96, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    18, 'Barhamganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    97, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    37, 'Gaibandha Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    98, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    23, 'Monnunagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    99, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Putia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    100, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Uzirpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    101, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    52, 'Rani Sankail', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    102, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Nageshwar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    103, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Bhuapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    104, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Dupchanchia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    105, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Naogaon Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    106, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Lalitganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    107, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Gabtoli', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    108, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Kaunia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    109, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    33, 'Kaliganj UPO', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    110, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Chalna Bazar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    111, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    49, 'Siddirganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    112, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Nagarpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    113, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    2, 'Rajnagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    114, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    30, 'Narail Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    115, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Gouranadi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    116, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Charghat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    117, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Bangla Hili', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    118, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    22, 'Gangni', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    119, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Mohanganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    120, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Tarial', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    121, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Ostagram', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    122, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Dhaka', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    123, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Bhanga', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    124, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    3, 'Arpara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    125, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Uttara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    126, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    6, 'Rajapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    127, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Mithamoin', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    128, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    18, 'kalkini', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    129, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Dhirai Chandpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    130, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    46, 'Nazirpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    131, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Ghatail', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    132, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Rangpur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    133, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Rajarhat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    134, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    40, 'Pangsha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    135, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Rampal', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    136, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Phulbari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    137, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    15, 'Narsingdi Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    138, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Tahirpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    139, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    7, 'Nakla', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    140, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    33, 'Ashashuni', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    141, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Kachua UPO', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    142, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Barishal Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    143, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    44, 'Patgram', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    144, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    23, 'Kaliganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    145, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Shriangan', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    146, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    37, 'Gobindaganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    147, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    23, 'Kapashia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    148, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    9, 'Naldanga', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    149, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Dhamuirhat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    150, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Bogura Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    151, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    51, 'kalai', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    152, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    51, 'Joypurhat Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    153, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    46, 'Banaripara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    154, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Bhangura', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    155, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Khulna Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    156, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    8, 'Munshiganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    157, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Ghoraghat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    158, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Faridpur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    159, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Mahendiganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    160, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Sapahar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    161, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Tejgaon', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    162, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    37, 'Sundarganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    163, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    15, 'Shibpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    164, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Barajalia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    165, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    47, 'Gosairhat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    166, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    37, 'Saadullapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    167, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Kanaighat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    168, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Maharajganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    169, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Ramna', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    170, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    29, 'Damurhuda', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    171, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    27, 'Rohanpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    172, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Gopalpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    173, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    1, 'Kashiani', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    174, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Pirgachha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    175, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Ishwardi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    176, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    52, 'Baliadangi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    177, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Sajiara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    178, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Baniachang', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    179, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Bajitpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    180, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    47, 'Bhedorganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    181, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    1, 'Gopalganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    182, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Bhurungamari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    183, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    21, 'Hatgurudaspur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    184, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    40, 'Rajbari Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    185, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    15, 'Monohordi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    186, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Habiganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    187, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Patnitala', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    188, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Birampur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    189, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    27, 'Chapai Nawabganj Sadar', 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    190, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Madhabpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    191, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Adamdighi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    192, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    51, 'Khetlal', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    193, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    53, 'Dashmina', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    194, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Khansama', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    195, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    18, 'Rajoir', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    196, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Gulshan', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    197, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Pabna Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    198, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    30, 'Mohajan', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    199, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    44, 'Tushbhandar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    200, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    7, 'Jhinaigati', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    201, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Phultala', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    202, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Sathia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    203, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    24, 'Nilphamari Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    204, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    2, 'Moulvibazar Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    205, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    53, 'Patuakhali Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    206, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    44, 'Hatibandha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    207, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    29, 'Doulatganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    208, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    32, 'Bheramara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    209, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    46, 'Pirojpur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    210, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Baiddya Jam Toil', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    211, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    30, 'Kalia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    212, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    28, 'Shibloya', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    213, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    6, 'Jhalokati Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    214, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    7, 'Nalitabari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    215, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    30, 'Laxmipasha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    216, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    46, 'Bhandaria', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    217, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Jhikargachha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    218, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Morelganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    219, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Bagha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    220, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    8, 'Sreenagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    221, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Nandigram', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    222, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Motijheel', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    223, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    44, 'Aditmari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    224, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Nandail', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    225, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Monirampur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    226, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Kazipur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    227, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    20, 'Chotto Dab', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    228, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Balaganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    229, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Karimganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    230, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    21, 'Laxman', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    231, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Kalauk', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    232, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Sutrapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    233, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Purbadhola', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    234, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Isshwargonj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    235, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    2, 'Kulaura', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    236, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Haluaghat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    237, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Madukhali', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    238, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    53, 'Khepupara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    239, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Jatrabari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    240, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Babuganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    241, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Chunarughat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    242, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    8, 'Gajaria', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    243, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Rajibpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    244, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    34, 'Lalmohan UPO', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    245, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    23, 'Gazipur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    246, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    32, 'Kushtia Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    247, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Sujanagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    248, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    53, 'Galachipa', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    249, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Mirzapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    250, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    35, 'Patharghata', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    251, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Mymensingh Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    252, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    22, 'Meherpur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    253, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    9, 'Maheshpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    254, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    37, 'Bonarpara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    255, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Sunamganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    256, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    47, 'Shariatpur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    257, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    49, 'Fatullah', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    258, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Chirirbandar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    259, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    7, 'Shribardi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    260, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Gouripur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    261, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    35, 'Bamna', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    262, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    52, 'Jibanpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    263, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Kuliarchar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    264, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Tanor', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    265, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Jakiganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    266, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    9, 'Shailakupa', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    267, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Batiaghat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    268, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Goainhat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    269, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    5, 'Madarganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    270, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Savar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    271, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    8, 'Tangibari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    272, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Chalna Ankorage', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    273, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Tarash', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    274, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    20, 'Tetulia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    275, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Ullapara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    276, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Bishwanath', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    277, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    18, 'Madaripur Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    278, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    28, 'Lechhraganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    279, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    23, 'Sripur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    280, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Ahsanganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    281, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Jaintapur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    282, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    46, 'Swarupkathi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    283, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    21, 'Harua', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    284, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    7, 'Sherpur Shadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    285, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    32, 'Janipur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    286, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    46, 'kaukhali', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    287, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Dhunat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    288, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    5, 'Shorishabari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    289, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    29, 'Alamdanga', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    290, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Madan', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    291, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Setabganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    292, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Sabujbag', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    293, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    27, 'Shibganj U.P.O', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    294, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Keraniganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    295, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Paikgachha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    296, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Belkuchi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    297, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Digalia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    298, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Keshabpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    299, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Sakhipur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    300, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Netrakona Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    301, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Jagnnathpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    302, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    28, 'Saturia', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    303, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Duara bazar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    304, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    15, 'Palash', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    305, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    40, 'Baliakandi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    306, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    5, 'Dewangonj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    307, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Katiadi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    308, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Fakirhat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    309, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Kahalu', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    310, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    52, 'Thakurgaon Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    311, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    51, 'panchbibi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    312, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Charbhadrasan', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    313, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Chaugachha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    314, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Lalbag', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    315, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Palton', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    316, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    3, 'Shripur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    317, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Chhatak', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    318, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    1, 'Tungipara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    319, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    47, 'Naria', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    320, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Khilkhet', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    321, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Aftabnagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    322, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    6, 'Nalchhiti', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    323, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Dhobaura', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    324, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Gangachara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    325, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Madhupur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    326, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    24, 'Saidpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    327, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Khod Mohanpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    328, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    9, 'Jhenaidah Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    329, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Muladi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    330, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Sherpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    331, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    20, 'Panchagra Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    332, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Parbatipur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    333, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    19, 'Raninagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    334, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    1, 'Kotalipara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    335, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Godagari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    336, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    34, 'Borhanuddin UPO', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    337, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    24, 'Domar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    338, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Sachna', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    339, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Chatmohar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    340, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Bishamsarpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    341, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    2, 'Baralekha', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    342, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    4, 'Hossenpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    343, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    2, 'Kamalganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    344, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    34, 'Charfashion', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    345, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    9, 'Harinakundu', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    346, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    35, 'Barguna Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    347, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    25, 'Roumari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    348, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    34, 'Hajirhat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    349, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    17, 'Bahubal', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    350, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Alaipur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    351, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    42, 'Shibganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    352, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    23, 'Kaliakaar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    353, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    44, 'Lalmonirhat Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    354, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Koyra', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    355, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    47, 'Damudhya', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    356, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Alfadanga', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    357, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    12, 'Jashore Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    358, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    16, 'Delduar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    359, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    50, 'Shahjadpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    360, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    14, 'Rajshahi Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    361, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    41, 'Bakerganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    362, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Barhatta', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    363, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    11, 'Gaforgaon', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    364, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Mohammadpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    365, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Dhamrai', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    366, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Badarganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    367, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Mithapukur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    368, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Tejgaon Industrial Area', 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    369, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    20, 'Boda', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    370, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    38, 'Ghungiar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    371, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    8, 'Lohajong', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    372, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    45, 'Taraganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    373, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Sylhet Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    374, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Khaliajuri', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    375, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    23, 'Sreepur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    376, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    24, 'Kishoriganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    377, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    46, 'Mathbaria', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    378, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    37, 'Phulchhari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    379, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    53, 'Subidkhali', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    380, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    21, 'Natore Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    381, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    53, 'Bauphal', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    382, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    13, 'Debottar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    383, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    37, 'Palashbari', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    384, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    31, 'Mollahat', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    385, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Sadarpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    386, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    43, 'Terakhada', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    387, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    27, 'Nachol', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    388, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    10, 'Moddhynagar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    389, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'New market', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    390, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    48, 'Kompanyganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    391, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Joypara', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    392, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    35, 'Betagi', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    393, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    49, 'Narayanganj Sadar', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    394, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    39, 'Nagarkanda', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    395, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    24, 'Jaldhaka', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    396, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    33, 'Nakipur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    397, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    26, 'Nawabganj', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    398, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    36, 'Mirpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    399, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    3, 'Mohammadpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    400, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    5, 'Jamalpur', 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.thana(
  id, createdon, status, updatedon, 
  district_id, thana, createdby_id, 
  updatedby_id
) 
VALUES 
  (
    401, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    52, 'Pirganj', 1, 1
  );

/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    624, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6311, 'Mandumala', 'Nachol', 387, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    628, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6341, 'Kansart', 'Shibganj U.P.O', 
    293, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    630, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6340, 'Shibganj U.P.O', 'Shibganj U.P.O', 
    293, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    621, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6300, 'Chapai Nawabganj Sadar', 
    'Chapai Nawabganj Sadar', 189, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    627, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6320, 'Rohanpur', 'Rohanpur', 171, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    629, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6342, 'Manaksha', 'Shibganj U.P.O', 
    293, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    622, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6301, 'Rajarampur', 'Chapai Nawabganj Sadar', 
    189, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    619, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6330, 'Bholahat', 'Bholahat', 41, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    518, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5280, 
    'Nawabganj', 'Nawabganj', 397, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    517, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5282, 
    'Gopalpur', 'Nawabganj', 397, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    516, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5281, 
    'Daudpur', 'Nawabganj', 397, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    19, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1216, 'Mirpur TSO', 
    'Mirpur', 398, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    972, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7630, 'Mohammadpur', 
    'Mohammadpur', 399, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    973, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7632, 'Nahata', 
    'Mohammadpur', 399, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    971, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7631, 'Binodpur', 
    'Mohammadpur', 399, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    668, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6450, 
    'Singra', 'Singra', 1, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    854, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9361, 
    'Barabaria', 'Chitalmari', 2, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    607, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5881, 
    'Talora', 'Dupchachia', 3, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    357, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2430, 'Kalmakanda', 'Kalmakanda', 
    6, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    420, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3212, 
    'Kalighat', 'Srimangal', 9, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    422, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3211, 
    'Narain Chora', 'Srimangal', 9, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    424, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3210, 
    'Srimangal', 'Srimangal', 9, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    421, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3213, 
    'Khejurichhara', 'Srimangal', 9, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    295, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1901, 'Kagmari', 
    'Tangail Sadar', 10, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    297, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1904, 'Purabari', 
    'Tangail Sadar', 10, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    26, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1322, 'Daudpur', 
    'Nawabganj', 11, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    968, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7051, 'Taragunia', 
    'Rafayetpur', 13, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    716, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6720, 
    'Dhangora', 'Dhangora', 14, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1015, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9420, 
    'Taala', 'Taala', 15, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    203, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1411, 
    'D.C Mills', 'Bandar', 18, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    204, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1414, 
    'Madanganj', 'Bandar', 18, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    174, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1542, 
    'Ichapur', 'Sirajdikhan', 19, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    176, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1543, 
    'Malkha Nagar', 'Sirajdikhan', 19, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    310, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2012, 
    'Malancha', 'Malandah', 21, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    311, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2010, 
    'Malandah', 'Malandah', 21, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    958, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7011, 'Panti', 
    'Kumarkhali', 22, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    957, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7010, 'Kumarkhali', 
    'Kumarkhali', 22, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    805, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8433, 
    'Shoulajalia', 'Kathalia', 23, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    804, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8432, 
    'Niamatee', 'Kathalia', 23, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    783, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8300, 'Bhola Sadar', 
    'Bhola Sadar', 24, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    280, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1971, 'Rajafair', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    273, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1973, 'Ballabazar', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    279, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1975, 'Palisha', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    277, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1976, 'Nagarbari SO', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    276, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1977, 'Nagarbari', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    274, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1974, 'Elinga', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    970, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7600, 'Magura Sadar', 
    'Magura Sadar', 32, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    210, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1461, 
    'Kanchan', 'Rupganj', 34, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    209, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1462, 
    'Bhulta', 'Rupganj', 34, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    211, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1464, 
    'Murapara', 'Rupganj', 34, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    612, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5831, 
    'Chandan Baisha', 'Sariakandi', 
    36, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    477, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3164, 'banigram', 
    'Gopalganj', 37, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    479, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3162, 'Dakkhin Bhadashore', 
    'Gopalganj', 37, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    303, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2022, 
    'Gilabari', 'Islampur', 40, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    897, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7462, 'Bhugilhat', 
    'Noapara', 43, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    899, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7461, 'Rajghat', 
    'Noapara', 43, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    256, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1920, 'Basail', 
    'Basail', 45, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    200, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1442, 
    'Barodi', 'Baidder Bazar', 46, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    198, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1440, 
    'Baidder Bazar', 'Baidder Bazar', 
    46, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    642, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6530, 
    'Mahadebpur', 'Mahadebpur', 47, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    146, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1803, 
    'Mahadebpur', 'Manikganj Sadar', 
    48, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    147, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1801, 
    'Manikganj Bazar', 'Manikganj Sadar', 
    48, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    145, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1802, 
    'Gorpara', 'Manikganj Sadar', 48, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    657, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6420, 
    'Gopalpur U.P.O', 'Gopalpur UPO', 
    49, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    658, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6421, 
    'Lalpur S.O', 'Gopalpur UPO', 49, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    656, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6422, 
    'Abdulpur', 'Gopalpur UPO', 49, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    850, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9301, 
    'P.C College', 'Bagerhat Sadar', 
    50, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    851, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9302, 
    'Rangdia', 'Bagerhat Sadar', 50, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    473, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3117, 'Fenchuganj SareKarkh', 
    'Fenchuganj', 51, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    472, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3116, 'Fenchuganj', 
    'Fenchuganj', 51, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    996, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9413, 
    'Hamidpur', 'kalaroa', 53, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    997, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9412, 
    'Jhaudanga', 'kalaroa', 53, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1000, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9410, 
    'Murarikati', 'kalaroa', 53, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1013, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9400, 
    'Satkhira Sadar', 'Satkhira Sadar', 
    56, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    320, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2240, 'Bhaluka', 'Bhaluka', 57, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    397, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3374, 
    'Inathganj', 'Nabiganj', 58, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    396, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3371, 
    'Goplarbazar', 'Nabiganj', 58, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    539, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5600, 
    'Kurigram Sadar', 'Kurigram Sadar', 
    59, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    541, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5680, 
    'Phulbari', 'Kurigram Sadar', 59, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    571, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Panchagarh', 20, 'Rangpur', 4, 5020, 
    'Dabiganj', 'Dabiganj', 62, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    903, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7430, 'Sarsa', 
    'Sarsa', 63, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    900, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7433, 'Bag Achra', 
    'Sarsa', 63, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    902, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7432, 'Jadabpur', 
    'Sarsa', 63, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    631, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 51, 'Rajshahi', 5, 5940, 
    'Akklepur', 'Akkelpur', 65, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    633, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 51, 'Rajshahi', 5, 5942, 
    'Tilakpur', 'Akkelpur', 65, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    883, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7470, 'Bagharpara', 
    'Bagharpara', 68, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    884, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7471, 'Gouranagar', 
    'Bagharpara', 68, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    741, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8240, 
    'Agailzhara', 'Agailzhara', 70, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    689, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6250, 
    'Bhabaniganj', 'Bhabaniganj', 71, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    155, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1821, 
    'Baira', 'Singari', 72, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    963, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7032, 'Amla Sadarpur', 
    'Mirpur', 73, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    964, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7030, 'Mirpur', 
    'Mirpur', 73, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    232, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1632, 
    'Radhaganj bazar', 'Raypura', 74, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    233, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1630, 
    'Raypura', 'Raypura', 74, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    231, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1631, 
    'Bazar Hasnabad', 'Raypura', 74, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    790, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8310, 'Doulatkhan', 
    'Doulatkhan', 75, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    791, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8311, 'Hajipur', 
    'Doulatkhan', 75, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    647, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6551, 
    'Porsa', 'Nitpur', 76, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    281, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1930, 'Kashkawlia', 
    'Kashkaolia', 79, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    669, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6650, 'Banwarinagar', 
    'Banwarinagar', 80, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    115, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2301, 
    'Kishoreganj S.Mills', 'Kishoreganj Sadar', 
    83, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    117, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2302, 
    'Maizhati', 'Kishoreganj Sadar', 
    83, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    116, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2300, 
    'Kishoreganj Sadar', 'Kishoreganj Sadar', 
    83, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    506, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5210, 
    'Biral', 'Biral', 85, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    559, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5351, 
    'Ghaga Kharibari', 'Dimla', 86, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    247, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8010, 
    'Jajira', 'Jajira', 87, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    344, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2251, 'Beltia', 'Phulpur', 88, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    345, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2250, 'Phulpur', 'Phulpur', 88, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    462, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3175, 'Churkai', 
    'Bianibazar', 89, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    464, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3173, 'Kurar bazar', 
    'Bianibazar', 89, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    463, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3171, 'jaldup', 
    'Bianibazar', 89, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    644, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6520, 
    'Niamatpur', 'Niamatpur', 90, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    874, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9330, 
    'Rayenda', 'Rayenda', 91, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    591, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5110, 
    'Pirganj', 'Pirganj', 401, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    511, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5201, 
    'Dinajpur Rajbari', 'Dinajpur Sadar', 
    93, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    512, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5200, 
    'Dinajpur Sadar', 'Dinajpur Sadar', 
    93, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    123, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2360, 
    'Nikli', 'Nikli', 94, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    130, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7933, 
    'Umedpur', 'Barhamganj', 96, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    129, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7931, 
    'Nilaksmibandar', 'Barhamganj', 
    96, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    80, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1710, 'Monnunagar', 
    'Monnunagar', 98, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    780, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8222, 
    'Jugirkanda', 'Uzirpur', 100, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    779, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8221, 
    'Dhamura', 'Uzirpur', 100, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    540, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5601, 
    'Pandul', 'Kurigram Sadar', 59, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    782, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8220, 
    'Uzirpur', 'Uzirpur', 100, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    593, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5121, 
    'Nekmarad', 'Rani Sankail', 101, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    542, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5660, 
    'Nageshwar', 'Nageshwar', 102, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    257, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1960, 'Bhuapur', 
    'Bhuapur', 103, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    606, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5880, 
    'Dupchanchia', 'Dupchanchia', 104, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    577, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5441, 
    'Haragachh', 'Kaunia', 108, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    918, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9272, 'Bajua', 
    'Chalna Bazar', 110, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    919, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9270, 'Chalna Bazar', 
    'Chalna Bazar', 110, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    921, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9273, 'Nalian', 
    'Chalna Bazar', 110, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    290, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1937, 'Dhuburia', 
    'Nagarpur', 112, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    292, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1938, 'Salimabad', 
    'Nagarpur', 112, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    291, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1936, 'Nagarpur', 
    'Nagarpur', 112, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    419, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3240, 
    'Rajnagar', 'Rajnagar', 113, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    989, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7500, 'Narail Sadar', 
    'Narail Sadar', 114, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    763, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8232, 
    'Kashemabad', 'Gouranadi', 115, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    761, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8233, 
    'Batajor', 'Gouranadi', 115, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    762, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8230, 
    'Gouranadi', 'Gouranadi', 115, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    764, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8231, 
    'Tarki Bandar', 'Gouranadi', 115, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    691, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6270, 
    'Charghat', 'Charghat', 116, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    505, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5270, 
    'Bangla Hili', 'Bangla Hili', 117, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    124, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2380, 
    'Ostagram', 'Ostagram', 121, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1206, 'Dhaka Cantonment--TSO', 
    'Dhaka', 122, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    51, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7830, 'Bhanga', 
    'Bhanga', 123, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    969, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7620, 'Arpara', 
    'Arpara', 124, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    49, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1230, 'Uttara Model TownTSO', 
    'Uttara', 125, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    122, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2370, 
    'MIthamoin', 'Mithamoin', 127, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    131, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7920, 
    'Kalkini', 'kalkini', 128, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    436, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3040, 
    'Dhirai Chandpur', 'Dhirai Chandpur', 
    129, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    437, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3041, 
    'Jagdal', 'Dhirai Chandpur', 129, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    840, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8540, 
    'Nazirpur', 'Nazirpur', 130, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    841, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8541, 
    'Sriramkathi', 'Nazirpur', 130, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    267, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1984, 'Lohani', 
    'Ghatail', 131, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    264, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1982, 'D. Pakutia', 
    'Ghatail', 131, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    266, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1980, 'Ghatial', 
    'Ghatail', 131, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    583, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5404, 
    'Rangpur Cadet Colleg', 'Rangpur Sadar', 
    132, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    581, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5402, 
    'Alamnagar', 'Rangpur Sadar', 132, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    586, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5401, 
    'Rangpur Upa-Shahar', 'Rangpur Sadar', 
    132, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    582, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5403, 
    'Mahiganj', 'Rangpur Sadar', 132, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    543, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5611, 
    'Nazimkhan', 'Rajarhat', 133, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    544, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5610, 
    'Rajarhat', 'Rajarhat', 133, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    239, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7721, 'Ramkol', 
    'Pangsha', 134, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    240, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7722, 'Ratandia', 
    'Pangsha', 134, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    238, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7720, 'Pangsha', 
    'Pangsha', 134, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    237, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7723, 'Mrigibazar', 
    'Pangsha', 134, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    872, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9340, 
    'Rampal', 'Rampal', 135, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    870, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9341, 
    'Foylahat', 'Rampal', 135, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    871, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9343, 
    'Gourambha', 'Rampal', 135, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    873, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9342, 
    'Sonatunia', 'Rampal', 135, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    522, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5260, 
    'Phulbari', 'Phulbari', 136, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    225, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1603, 
    'Panchdona', 'Narsingdi Sadar', 
    137, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    224, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1600, 
    'Narsingdi Sadar', 'Narsingdi Sadar', 
    137, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    222, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1604, 
    'Madhabdi', 'Narsingdi Sadar', 137, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    450, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3030, 
    'Tahirpur', 'Tahirpur', 138, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    372, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2150, 
    'Nakla', 'Nakla', 139, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    991, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9460, 
    'Ashashuni', 'Ashashuni', 140, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    757, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8205, 
    'Kashipur', 'Barishal Sadar', 142, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    759, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8202, 
    'Saheberhat', 'Barishal Sadar', 
    142, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    756, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8206, 
    'Jaguarhat', 'Barishal Sadar', 142, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    754, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8200, 
    'Chandramohon', 'Barishal Sadar', 
    142, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    760, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8203, 
    'Sugandia', 'Barishal Sadar', 142, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    555, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5542, 
    'Burimari', 'Patgram', 143, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    554, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5541, 
    'Baura', 'Patgram', 143, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    556, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5540, 
    'Patgram', 'Patgram', 143, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    74, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1720, 'Kaliganj', 
    'Kaliganj', 144, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    76, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1722, 'Santanpara', 
    'Kaliganj', 144, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    77, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1723, 'Vaoal Jamalpur', 
    'Kaliganj', 144, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    75, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1721, 'Pubail', 
    'Kaliganj', 144, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    66, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7804, 'Shriangan', 
    'Shriangan', 145, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    528, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5741, 
    'Mahimaganj', 'Gobindaganj', 146, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    527, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5740, 
    'Gobindhaganj', 'Gobindaganj', 146, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    78, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1730, 'kapashia', 
    'Kapashia', 147, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    909, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7351, 
    'Hatbar Bazar', 'Naldanga', 148, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    910, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7350, 
    'Naldanga', 'Naldanga', 148, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    641, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6580, 
    'Dhamuirhat', 'Dhamuirhat', 149, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    602, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5801, 
    'Bogura Canttonment', 'Bogura Sadar', 
    150, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    603, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5800, 
    'Bogura Sadar', 'Bogura Sadar', 
    150, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    635, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 51, 'Rajshahi', 5, 5930, 
    'kalai', 'kalai', 151, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    634, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 51, 'Rajshahi', 5, 5900, 
    'Joypurhat Sadar', 'Joypurhat Sadar', 
    152, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    824, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8530, 
    'Banaripara', 'Banaripara', 153, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    674, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6640, 'Bhangura', 
    'Bhangura', 154, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    927, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9207, 'Atra Shilpa Area', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    930, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9205, 'Jahanabad Canttonmen', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    936, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9206, 'Sonali Jute Mills', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    931, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9100, 'Khula Sadar', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    928, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9203, 'BIT Khulna', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    935, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9204, 'Siramani', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    932, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9000, 'Khulna G.P.O', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    519, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5291, 
    'Ghoraghat', 'Ghoraghat', 157, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    520, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5290, 
    'Osmanpur', 'Ghoraghat', 157, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    58, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7801, 'Kanaipur', 
    'Faridpur Sadar', 158, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    56, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7803, 'Baitulaman Politecni', 
    'Faridpur Sadar', 158, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    55, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7802, 'Ambikapur', 
    'Faridpur Sadar', 158, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    766, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8271, 
    'Laskarpur', 'Mahendiganj', 159, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    769, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8272, 
    'Ulania', 'Mahendiganj', 159, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    768, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8273, 
    'Nalgora', 'Mahendiganj', 159, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    765, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8274, 
    'Langutia', 'Mahendiganj', 159, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    654, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6561, 
    'Moduhil', 'Sapahar', 160, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    655, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6560, 
    'Sapahar', 'Sapahar', 160, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    47, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1215, 'Tejgaon TSO', 
    'Tejgaon', 161, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    534, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5721, 
    'Bamandanga', 'Sundarganj', 162, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    535, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5720, 
    'Sundarganj', 'Sundarganj', 162, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    234, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1620, 
    'Shibpur', 'Shibpur', 163, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    752, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8261, 
    'Osman Manjil', 'Barajalia', 164, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    246, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8050, 
    'Gosairhat', 'Gosairhat', 165, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    532, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5711, 
    'Naldanga', 'Saadullapur', 166, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    533, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5710, 
    'Saadullapur', 'Saadullapur', 166, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    487, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3183, 'Gachbari', 
    'Kanaighat', 167, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    489, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3182, 'Manikganj', 
    'Kanaighat', 167, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    486, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3181, 'Chatulbazar', 
    'Kanaighat', 167, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    515, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5226, 
    'Maharajganj', 'Maharajganj', 168, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    32, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1217, 'Shantinagr TSO', 
    'Ramna', 169, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    880, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7220, 
    'Damurhuda', 'Damurhuda', 170, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    881, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7221, 
    'Darshana', 'Damurhuda', 170, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    879, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7222, 
    'Andulbaria', 'Damurhuda', 170, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    272, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1991, 'Chatutia', 
    'Gopalpur', 172, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    269, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1990, 'Gopalpur', 
    'Gopalpur', 172, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    678, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6620, 'Ishwardi', 
    'Ishwardi', 175, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    679, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6622, 'Pakshi', 
    'Ishwardi', 175, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    677, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6621, 'Dhapari', 
    'Ishwardi', 175, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    680, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6623, 'Rajapur', 
    'Ishwardi', 175, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    588, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5140, 
    'Baliadangi', 'Baliadangi', 176, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    589, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5141, 
    'Lahiri', 'Baliadangi', 176, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    947, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9251, 'Ghonabanda', 
    'Sajiara', 177, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    948, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9250, 'Sajiara', 
    'Sajiara', 177, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    949, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9253, 'Shahapur', 
    'Sajiara', 177, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    379, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3350, 
    'Baniachang', 'Baniachang', 178, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    106, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2336, 
    'Bajitpur', 'Bajitpur', 179, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    107, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2338, 
    'Laksmipur', 'Bajitpur', 179, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    244, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8030, 
    'Bhedorganj', 'Bhedorganj', 180, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    92, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8102, 'Barfa', 
    'Gopalganj Sadar', 181, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    95, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8101, 'Ulpur', 
    'Gopalganj Sadar', 181, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    93, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8013, 'Chandradighalia', 
    'Gopalganj Sadar', 181, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    94, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8100, 'Gopalganj Sadar', 
    'Gopalganj Sadar', 181, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    241, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7710, 'Goalanda', 
    'Rajbari Sadar', 184, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    219, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1652, 
    'Katabaria', 'Monohordi', 185, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    218, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1651, 
    'Hatirdia', 'Monohordi', 185, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    387, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3301, 
    'Shaestaganj', 'Habiganj Sadar', 
    186, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    386, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3300, 
    'Habiganj Sadar', 'Habiganj Sadar', 
    186, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    648, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6540, 
    'Patnitala', 'Patnitala', 187, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    390, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3331, 
    'Itakhola', 'Madhabpur', 190, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    391, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3330, 
    'Madhabpur', 'Madhabpur', 190, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    392, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3333, 
    'Saihamnagar', 'Madhabpur', 190, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    393, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3332, 
    'Shahajibazar', 'Madhabpur', 190, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    599, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5890, 
    'Adamdighi', 'Adamdighi', 191, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    601, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5891, 
    'Santahar', 'Adamdighi', 191, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    600, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5892, 
    'Nasharatpur', 'Adamdighi', 191, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    636, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 51, 'Rajshahi', 5, 5920, 
    'Khetlal', 'Khetlal', 192, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    814, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8630, 
    'Dashmina', 'Dashmina', 193, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    514, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5231, 
    'Pakarhat', 'Khansama', 194, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    138, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7911, 
    'Khalia', 'Rajoir', 195, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    139, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7910, 
    'Rajoir', 'Rajoir', 195, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    5, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1213, 'Banani TSO', 
    'Gulshan', 196, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    7, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1212, 'Gulshan Model Town', 
    'Gulshan', 196, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    682, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6601, 'Kaliko Cotton Mills', 
    'Pabna Sadar', 197, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    681, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6602, 'Hamayetpur', 
    'Pabna Sadar', 197, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    683, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6600, 'Pabna Sadar', 
    'Pabna Sadar', 197, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    557, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5520, 
    'Tushbhandar', 'Tushbhandar', 199, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    370, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2120, 
    'Jhinaigati', 'Jhinaigati', 200, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    945, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9210, 'Phultala', 
    'Phultala', 201, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    684, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6670, 'Sathia', 
    'Sathia', 202, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    564, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5300, 
    'Nilphamari Sadar', 'Nilphamari Sadar', 
    203, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    415, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3203, 
    'Afrozganj', 'Moulvibazar Sadar', 
    204, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    418, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3200, 
    'Moulvibazar Sadar', 'Moulvibazar Sadar', 
    204, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    820, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8601, 
    'Moukaran', 'Patuakhali Sadar', 
    205, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    821, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8600, 
    'Patuakhali Sadar', 'Patuakhali Sadar', 
    205, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    550, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5530, 
    'Hatibandha', 'Hatibandha', 206, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    855, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9360, 
    'Chitalmari', 'Chitalmari', 2, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    859, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9310, 
    'Kachua', 'Kachua UPO', 141, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    860, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9311, 
    'Sonarkola', 'Kachua UPO', 141, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    507, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5266, 
    'Birampur', 'Birampur', 188, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    513, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5230, 
    'Khansama', 'Khansama', 194, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    882, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7230, 
    'Doulatganj', 'Doulatganj', 207, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    954, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7041, 'Ganges Bheramara', 
    'Bheramara', 208, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    953, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7040, 'Bheramara', 
    'Bheramara', 208, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    952, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7042, 'Allardarga', 
    'Bheramara', 208, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    844, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8500, 
    'Pirojpur Sadar', 'Pirojpur Sadar', 
    209, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    842, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8501, 
    'Hularhat', 'Pirojpur Sadar', 209, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    843, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8502, 
    'Parerhat', 'Pirojpur Sadar', 209, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    710, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6730, 
    'Baiddya Jam Toil', 'Baiddya Jam Toil', 
    210, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    982, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7520, 'Kalia', 
    'Kalia', 211, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    153, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1852, 
    'Tewta', 'Shibloya', 212, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    152, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1850, 
    'Shibaloy', 'Shibloya', 212, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    151, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1851, 
    'Aricha', 'Shibloya', 212, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    797, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8402, 
    'Baukathi', 'Jhalokati Sadar', 213, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    798, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8403, 
    'Gabha', 'Jhalokati Sadar', 213, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    801, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8404, 
    'Shekherhat', 'Jhalokati Sadar', 
    213, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    800, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8401, 
    'Nabagram', 'Jhalokati Sadar', 213, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    799, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8400, 
    'Jhalokati Sadar', 'Jhalokati Sadar', 
    213, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    374, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2110, 
    'Nalitabari', 'Nalitabari', 214, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    373, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2111, 
    'Hatibandha', 'Nalitabari', 214, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    983, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7514, 'Baradia', 
    'Laxmipasha', 215, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    986, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7511, 'Lohagora', 
    'Laxmipasha', 215, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    987, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7513, 'Naldi', 
    'Laxmipasha', 215, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    985, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7510, 'Laxmipasha', 
    'Laxmipasha', 215, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    828, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8551, 
    'Kanudashkathi', 'Bhandaria', 216, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    827, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8552, 
    'Dhaoa', 'Bhandaria', 216, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    826, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8550, 
    'Bhandaria', 'Bhandaria', 216, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    868, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9321, 
    'Sannasi Bazar', 'Morelganj', 218, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    867, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9320, 
    'Morelganj', 'Morelganj', 218, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    186, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1550, 
    'Sreenagar', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    181, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1558, 
    'Bhaggyakul', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    182, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1553, 
    'Hashara', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    185, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1552, 
    'Maijpara', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    187, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1556, 
    'Vaggyakul SO', 'Sreenagar', 220, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    184, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1555, 
    'Kumarbhog', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    183, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1554, 
    'Kolapara', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    611, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5860, 
    'Nandigram', 'Nandigram', 221, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    22, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1222, 'BangabhabanTSO', 
    'Motijheel', 222, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    549, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5510, 
    'Aditmari', 'Aditmari', 223, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    342, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2291, 'Gangail', 'Nandail', 224, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    343, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2290, 'Nandail', 'Nandail', 224, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    966, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7052, 'Khasmathurapur', 
    'Rafayetpur', 13, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    125, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2326, 
    'Pakundia', 'Pakundia', 35, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    672, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6681, 'Nakalia', 
    'Bera', 44, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    651, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6510, 
    'Prasadpur', 'Prasadpur', 82, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    990, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7501, 'Ratanganj', 
    'Narail Sadar', 114, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    825, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8531, 
    'Chakhar', 'Banaripara', 153, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    172, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1500, 
    'Munshiganj Sadar', 'Munshiganj Sadar', 
    156, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    536, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5670, 
    'Bhurungamari', 'Bhurungamari', 
    182, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    385, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3302, 
    'Gopaya', 'Habiganj Sadar', 186, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    154, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1853, 
    'Uthli', 'Shibloya', 212, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    896, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7440, 'Monirampur', 
    'Monirampur', 225, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    720, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6711, 
    'Shuvgachha', 'Kazipur', 226, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    718, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6712, 
    'Gandail', 'Kazipur', 226, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    570, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Panchagarh', 20, 'Rangpur', 4, 5041, 
    'Mirjapur', 'Chotto Dab', 227, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    569, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Panchagarh', 20, 'Rangpur', 4, 5040, 
    'Chotto Dab', 'Chotto Dab', 227, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    454, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3128, 'Gaharpur', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    455, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3124, 'Goala Bazar', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    457, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3127, 'Kathal Khair', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    452, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3125, 'Begumpur', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    453, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3122, 'Brahman Shashon', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    459, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3126, 'Omarpur', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    112, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2310, 
    'Karimganj', 'Karimganj', 229, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    663, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6410, 
    'Laxman', 'Laxman', 230, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    389, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3341, 
    'Lakhai', 'Kalauk', 231, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    388, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3340, 
    'Kalauk', 'Kalauk', 231, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    45, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1204, 'Gendaria TSO', 
    'Sutrapur', 232, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    44, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1100, 'Dhaka Sadar HO', 
    'Sutrapur', 232, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    46, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1203, 'Wari TSO', 
    'Sutrapur', 232, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    366, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2412, 'Jaria Jhanjhail', 'Purbadhola', 
    233, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    367, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2410, 'Purbadhola', 'Purbadhola', 
    233, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    332, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2282, 'Atharabari', 'Isshwargonj', 
    234, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    333, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2280, 'Isshwargonj', 'Isshwargonj', 
    234, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    334, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2281, 'Sohagi', 'Isshwargonj', 234, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    414, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3231, 
    'Tillagaon', 'Kulaura', 235, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    410, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3235, 
    'Karimpur', 'Kulaura', 235, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    408, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3237, 
    'Baramchal', 'Kulaura', 235, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    413, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3233, 
    'Prithimpasha', 'Kulaura', 235, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    409, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3234, 
    'Kajaldhara', 'Kulaura', 235, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    411, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3230, 
    'Kulaura', 'Kulaura', 235, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    330, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2260, 'Haluaghat', 'Haluaghat', 236, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    331, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2262, 'Munshirhat', 'Haluaghat', 
    236, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    329, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2261, 'Dhara', 'Haluaghat', 236, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    59, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7851, 'Kamarkali', 
    'Madukhali', 237, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    640, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6570, 
    'Badalgachhi', 'Badalgachhi', 61, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    727, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6700, 
    'Sirajganj Sadar', 'Sirajganj Sadar', 
    66, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    649, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6512, 
    'Balihar', 'Prasadpur', 82, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    662, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6440, 
    'Hatgurudaspur', 'Hatgurudaspur', 
    183, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    60, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7850, 'Madukhali', 
    'Madukhali', 237, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    8, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1236, 'Dhania TSO', 
    'Jatrabari', 239, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    745, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8216, 
    'Barishal Cadet', 'Babuganj', 240, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    750, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8214, 
    'Thakur Mallik', 'Babuganj', 240, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    747, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8213, 
    'Madhabpasha', 'Babuganj', 240, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    746, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8212, 
    'Chandpasha', 'Babuganj', 240, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    744, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8210, 
    'Babuganj', 'Babuganj', 240, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    748, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8215, 
    'Nizamuddin College', 'Babuganj', 
    240, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    749, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8211, 
    'Rahamatpur', 'Babuganj', 240, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    383, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3320, 
    'Chunarughat', 'Chunarughat', 241, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    382, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3321, 
    'Chandpurbagan', 'Chunarughat', 
    241, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    159, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1511, 
    'Hossendi', 'Gajaria', 242, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    158, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1510, 
    'Gajaria', 'Gajaria', 242, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    545, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5650, 
    'Rajibpur', 'Rajibpur', 243, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    795, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8332, 'Gazaria', 
    'Lalmohan UPO', 244, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    794, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8331, 'Daurihat', 
    'Lalmohan UPO', 244, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    796, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8330, 'Lalmohan UPO', 
    'Lalmohan UPO', 244, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    68, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1701, 'B.R.R', 
    'Gazipur Sadar', 245, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    67, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1703, 'B.O.F', 
    'Gazipur Sadar', 245, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    71, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1704, 'National University', 
    'Gazipur Sadar', 245, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    960, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7002, 'Jagati', 
    'Kushtia Sadar', 246, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    961, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7001, 'Kushtia Mohini', 
    'Kushtia Sadar', 246, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    962, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7000, 'Kushtia Sadar', 
    'Kushtia Sadar', 246, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    686, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6660, 'Sujanagar', 
    'Sujanagar', 247, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    685, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6661, 'Sagarkandi', 
    'Sujanagar', 247, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    815, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8640, 
    'Galachipa', 'Galachipa', 248, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    816, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8641, 
    'Gazipur Bandar', 'Galachipa', 248, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    285, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1944, 'Jarmuki', 
    'Mirzapur', 249, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    286, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1942, 'M.C. College', 
    'Mirzapur', 249, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    288, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1945, 'Mohera', 
    'Mirzapur', 249, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    284, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1941, 'Gorai', 
    'Mirzapur', 249, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    287, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1940, 'Mirzapur', 
    'Mirzapur', 249, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    739, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8721, 
    'Kakchira', 'Patharghata', 250, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    341, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2203, 'Shombhuganj', 'Mymensingh Sadar', 
    251, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    336, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2202, 'Agriculture Universi', 'Mymensingh Sadar', 
    251, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    337, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2204, 'Biddyaganj', 'Mymensingh Sadar', 
    251, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    980, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Meherpur', 22, 'Khulna', 7, 7100, 
    'Meherpur Sadar', 'Meherpur Sadar', 
    252, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    981, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Meherpur', 22, 'Khulna', 7, 7102, 
    'Mujib Nagar Complex', 'Meherpur Sadar', 
    252, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    978, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Meherpur', 22, 'Khulna', 7, 7101, 
    'Amjhupi', 'Meherpur Sadar', 252, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    979, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Meherpur', 22, 'Khulna', 7, 7152, 
    'Amjhupi', 'Meherpur Sadar', 252, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    908, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7340, 
    'Maheshpur', 'Maheshpur', 253, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    525, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5751, 
    'saghata', 'Bonarpara', 254, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    524, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5750, 
    'Bonarpara', 'Bonarpara', 254, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    448, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3002, 
    'Patharia', 'Sunamganj Sadar', 255, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    156, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1822, 
    'joymantop', 'Singari', 72, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    81, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1711, 'Nishat Nagar', 
    'Monnunagar', 98, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    243, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7700, 'Rajbari Sadar', 
    'Rajbari Sadar', 184, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    447, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3001, 
    'Pagla', 'Sunamganj Sadar', 255, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    449, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3000, 
    'Sunamganj Sadar', 'Sunamganj Sadar', 
    255, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    254, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8002, 
    'Chikandi', 'Shariatpur Sadar', 
    256, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    255, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8000, 
    'Shariatpur Sadar', 'Shariatpur Sadar', 
    256, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    253, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8001, 
    'Angaria', 'Shariatpur Sadar', 256, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    206, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1421, 
    'Fatulla Bazar', 'Fatullah', 257, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    207, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1420, 
    'Fatullah', 'Fatullah', 257, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    510, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5241, 
    'Ranirbandar', 'Chirirbandar', 258, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    509, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5240, 
    'Chirirbandar', 'Chirirbandar', 
    258, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    376, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2130, 
    'Shribardi', 'Shribardi', 259, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    327, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2270, 'Gouripur', 'Gouripur', 260, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    328, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2271, 'Ramgopalpur', 'Gouripur', 
    260, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    590, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5130, 
    'Jibanpur', 'Jibanpur', 262, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    120, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2340, 
    'Kuliarchar', 'Kuliarchar', 263, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    119, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2341, 
    'Chhoysuti', 'Kuliarchar', 263, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    709, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6230, 
    'Tanor', 'Tanor', 264, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    484, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3191, 'Ichhamati', 
    'Jakiganj', 265, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    485, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3190, 'Jakiganj', 
    'Jakiganj', 265, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    911, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7321, 
    'Kumiradaha', 'Shailakupa', 266, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    912, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7320, 
    'Shailakupa', 'Shailakupa', 266, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    917, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9261, 'Surkalee', 
    'Batiaghat', 267, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    916, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9260, 'Batiaghat', 
    'Batiaghat', 267, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    474, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3152, 'Chiknagul', 
    'Goainhat', 268, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    476, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3151, 'Jaflong', 
    'Goainhat', 268, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    315, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2051, 
    'Adarvita', 'Madarganj', 269, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    312, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2041, 
    'Balijhuri', 'Madarganj', 269, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    36, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1349, 'EPZ', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    34, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1348, 'Amin Bazar', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    37, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1342, 'Jahangirnagar University', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    38, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1346, 'Kashem Cotton Mills', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    43, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1345, 'Shimulia', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    39, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1347, 'Rajphulbaria', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    41, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1344, 'Savar Canttonment', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    40, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1340, 'Savar', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    42, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1343, 'Saver P.A.T.C', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    188, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1523, 
    'Bajrajugini', 'Tangibari', 271, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    193, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1527, 
    'Pura', 'Tangibari', 271, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    190, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1521, 
    'Betkahat', 'Tangibari', 271, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    194, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1526, 
    'Pura EDSO', 'Tangibari', 271, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    195, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1520, 
    'Tangibari', 'Tangibari', 271, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    189, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1522, 
    'Baligao', 'Tangibari', 271, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    853, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9351, 
    'Mongla Port', 'Chalna Ankorage', 
    272, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    852, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9350, 
    'Chalna Ankorage', 'Chalna Ankorage', 
    272, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    728, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6780, 
    'Tarash', 'Tarash', 273, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    573, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Panchagarh', 20, 'Rangpur', 4, 5030, 
    'Tetulia', 'Tetulia', 274, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    731, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6760, 
    'Ullapara', 'Ullapara', 275, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    729, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6762, 
    'Lahiri Mohanpur', 'Ullapara', 275, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    732, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6761, 
    'Ullapara R.S', 'Ullapara', 275, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    730, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6763, 
    'Salap', 'Ullapara', 275, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    469, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3133, 'Deokalas', 
    'Bishwanath', 276, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    468, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3131, 'Dashghar', 
    'Bishwanath', 276, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    137, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7904, 
    'Mustafapur', 'Madaripur Sadar', 
    277, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    136, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7900, 
    'Madaripur Sadar', 'Madaripur Sadar', 
    277, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    143, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1830, 
    'Lechhraganj', 'Lechhraganj', 278, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    127, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7932, 
    'Bahadurpur', 'Barhamganj', 96, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    698, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6211, 
    'Rajshahi Sugar Mills', 'Lalitganj', 
    106, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    134, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7903, 
    'Habiganj', 'Madaripur Sadar', 277, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    133, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7901, 
    'Charmugria', 'Madaripur Sadar', 
    277, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    142, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1831, 
    'Jhitka', 'Lechhraganj', 278, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    90, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1741, 'Rajendrapur', 
    'Sripur', 279, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    91, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1742, 'Rajendrapur Canttome', 
    'Sripur', 279, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    89, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1740, 'Tengra', 
    'Sripur', 279, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    639, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6597, 
    'Bandai', 'Ahsanganj', 280, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    638, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6596, 
    'Ahsanganj', 'Ahsanganj', 280, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    483, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3156, 'Jainthapur', 
    'Jaintapur', 281, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    845, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8521, 
    'Darus Sunnat', 'Swarupkathi', 282, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    846, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8523, 
    'Jalabari', 'Swarupkathi', 282, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    848, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8520, 
    'Swarupkathi', 'Swarupkathi', 282, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    847, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8522, 
    'Kaurikhara', 'Swarupkathi', 282, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    661, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6430, 
    'Harua', 'Harua', 283, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    956, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7021, 'Khoksa', 
    'Janipur', 285, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    955, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7020, 'Janipur', 
    'Janipur', 285, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    832, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8511, 
    'Keundia', 'kaukhali', 286, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    830, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8512, 
    'Joykul', 'kaukhali', 286, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    829, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8513, 
    'Jolagati', 'kaukhali', 286, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    605, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5851, 
    'Gosaibari', 'Dhunat', 287, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    604, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5850, 
    'Dhunat', 'Dhunat', 287, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    314, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2052, 
    'Bausee', 'Shorishabari', 288, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    316, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2053, 
    'Jagannath Ghat', 'Shorishabari', 
    288, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    318, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2054, 
    'Pingna', 'Shorishabari', 288, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    876, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7211, 
    'Hardi', 'Alamdanga', 289, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    875, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7210, 
    'Alamdanga', 'Alamdanga', 289, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    361, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2490, 'Madan', 'Madan', 290, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    33, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1214, 'Basabo TSO', 
    'Sabujbag', 292, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    14, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1313, 'Kalatia', 
    'Keraniganj', 294, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    15, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1310, 'Keraniganj', 
    'Keraniganj', 294, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    13, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1311, 'Dhaka Jute Mills', 
    'Keraniganj', 294, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    941, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9281, 'Godaipur', 
    'Paikgachha', 295, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    939, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9284, 'Chandkhali', 
    'Paikgachha', 295, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    942, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9282, 'Kapilmoni', 
    'Paikgachha', 295, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    940, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9285, 'Garaikhali', 
    'Paikgachha', 295, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    944, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9280, 'Paikgachha', 
    'Paikgachha', 295, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    711, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6740, 
    'Belkuchi', 'Belkuchi', 296, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    712, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6751, 
    'Enayetpur', 'Belkuchi', 296, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    715, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6752, 
    'Sthal', 'Belkuchi', 296, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    714, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6741, 
    'Sohagpur', 'Belkuchi', 296, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    924, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9224, 'Gazirhat', 
    'Digalia', 297, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    925, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9223, 'Ghoshghati', 
    'Digalia', 297, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    923, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9220, 'Digalia', 
    'Digalia', 297, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    895, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7450, 'Keshobpur', 
    'Keshabpur', 298, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    294, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1950, 'Sakhipur', 
    'Sakhipur', 299, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    293, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1951, 'Kochua', 
    'Sakhipur', 299, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    365, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2400, 'Netrakona Sadar', 'Netrakona Sadar', 
    300, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    444, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3065, 
    'Shiramsi', 'Jagnnathpur', 301, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    443, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3064, 
    'Rasulganj', 'Jagnnathpur', 301, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    441, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3063, 
    'Hasan Fatemapur', 'Jagnnathpur', 
    301, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    670, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6680, 'Bera', 
    'Bera', 44, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    223, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1602, 
    'Narsingdi College', 'Narsingdi Sadar', 
    137, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    817, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8650, 
    'Khepupara', 'Khepupara', 238, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    442, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3060, 
    'Jagnnathpur', 'Jagnnathpur', 301, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    445, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3061, 
    'Syedpur', 'Jagnnathpur', 301, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    440, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3062, 
    'Atuajan', 'Jagnnathpur', 301, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    150, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1810, 
    'Saturia', 'Saturia', 302, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    149, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1811, 
    'Baliati', 'Saturia', 302, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    229, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1611, 
    'Sarkarkhana', 'Palash', 304, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    227, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1612, 
    'Char Sindhur', 'Palash', 304, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    228, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1613, 
    'Ghorashal', 'Palash', 304, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    230, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1610, 
    'Palash', 'Palash', 304, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    235, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7730, 'Baliakandi', 
    'Baliakandi', 305, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    236, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7731, 'Nalia', 
    'Baliakandi', 305, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    300, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2030, 
    'Dewangonj', 'Dewangonj', 306, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    301, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2031, 
    'Dewangonj S. Mills', 'Dewangonj', 
    306, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    114, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2330, 
    'Katiadi', 'Katiadi', 307, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    113, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2331, 
    'Gochhihata', 'Katiadi', 307, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    857, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9370, 
    'Fakirhat', 'Fakirhat', 308, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    858, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9371, 
    'Mansa', 'Fakirhat', 308, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    856, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9372, 
    'Bhanganpar Bazar', 'Fakirhat', 
    308, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    610, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5870, 
    'Kahalu', 'Kahalu', 309, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    597, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5101, 
    'Thakurgaon Road', 'Thakurgaon Sadar', 
    310, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    595, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5103, 
    'Ruhia', 'Thakurgaon Sadar', 310, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    596, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5102, 
    'Shibganj', 'Thakurgaon Sadar', 
    310, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    598, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5100, 
    'Thakurgaon Sadar', 'Thakurgaon Sadar', 
    310, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    637, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 51, 'Rajshahi', 5, 5910, 
    'Panchbibi', 'panchbibi', 311, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    54, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7810, 'Charbadrashan', 
    'Charbhadrasan', 312, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    885, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7410, 'Chougachha', 
    'Chaugachha', 313, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    18, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1211, 'Posta TSO', 
    'Lalbag', 314, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    31, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1000, 'Dhaka GPO', 
    'Palton', 315, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    976, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7610, 'Shripur', 
    'Shripur', 316, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    974, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7611, 'Langalbadh', 
    'Shripur', 316, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    975, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Magura', 3, 'Khulna', 7, 7612, 'Nachol', 
    'Shripur', 316, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    430, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3083, 
    'Gabindaganj', 'Chhatak', 317, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    427, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3081, 
    'Chhatak Cement Facto', 'Chhatak', 
    317, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    429, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3893, 
    'Chourangi Bazar', 'Chhatak', 317, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    426, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3080, 
    'Chhatak', 'Chhatak', 317, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    434, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3085, 
    'Khurma', 'Chhatak', 317, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    433, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3087, 
    'jahidpur', 'Chhatak', 317, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    428, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3082, 
    'Chhatak Paper Mills', 'Chhatak', 
    317, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    432, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3088, 
    'Islamabad', 'Chhatak', 317, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    435, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3086, 
    'Moinpur', 'Chhatak', 317, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    431, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3084, 
    'Gabindaganj Natun Ba', 'Chhatak', 
    317, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    105, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8120, 'Tungipara', 
    'Tungipara', 318, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    104, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8121, 'Patgati', 
    'Tungipara', 318, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    251, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8020, 
    'Naria', 'Naria', 319, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    249, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8022, 
    'Gharisar', 'Naria', 319, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    252, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8023, 
    'Upshi', 'Naria', 319, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    248, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8021, 
    'Bhozeshwar', 'Naria', 319, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    250, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8024, 
    'Kartikpur', 'Naria', 319, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    17, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1229, 'KhilkhetTSO', 
    'Khilkhet', 320, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    488, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3180, 'Kanaighat', 
    'Kanaighat', 167, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    2, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 0, 'Aftabnagar', 
    'Aftabnagar', 321, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    806, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8421, 
    'Beerkathi', 'Nalchhiti', 322, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    807, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8420, 
    'Nalchhiti', 'Nalchhiti', 322, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    356, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2417, 'Sakoai', 'Dhobaura', 323, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    355, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2416, 'Dhobaura', 'Dhobaura', 323, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    576, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5410, 
    'Gangachara', 'Gangachara', 324, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    27, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1321, 'Hasnabad', 
    'Nawabganj', 11, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    994, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9431, 
    'Gurugram', 'Debbhata', 60, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    993, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9430, 
    'Debbhata', 'Debbhata', 60, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    157, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1820, 
    'Singair', 'Singari', 72, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    609, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5821, 
    'Sukhanpukur', 'Gabtoli', 107, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    226, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1601, 
    'UMC Jute Mills', 'Narsingdi Sadar', 
    137, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    929, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9202, 'Doulatpur', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    108, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2337, 
    'Sararchar', 'Bajitpur', 179, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    23, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1223, 'DilkushaTSO', 
    'Motijheel', 222, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    818, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8651, 
    'Mahipur', 'Khepupara', 238, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    831, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8510, 
    'Kaukhali', 'kaukhali', 286, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    283, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1996, 'Madhupur', 
    'Madhupur', 325, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    282, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1997, 'Dhobari', 
    'Madhupur', 325, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    567, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5311, 
    'Saidpur Upashahar', 'Saidpur', 
    326, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    566, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5310, 
    'Saidpur', 'Saidpur', 326, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    696, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6220, 
    'Khodmohanpur', 'Khod Mohanpur', 
    327, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    906, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7300, 
    'Jhenaidah Sadar', 'Jhenaidah Sadar', 
    328, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    905, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7301, 
    'Jhenaidah Cadet College', 'Jhenaidah Sadar', 
    328, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    770, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8252, 
    'Charkalekhan', 'Muladi', 329, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    771, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8251, 
    'Kazirchar', 'Muladi', 329, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    772, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8250, 
    'Muladi', 'Muladi', 329, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    616, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5840, 
    'Sherpur', 'Sherpur', 330, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    615, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5842, 
    'Palli Unnyan Accadem', 'Sherpur', 
    330, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    614, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5841, 
    'Chandaikona', 'Sherpur', 330, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    572, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Panchagarh', 20, 'Rangpur', 4, 5000, 
    'Panchagar Sadar', 'Panchagra Sadar', 
    331, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    521, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5250, 
    'Parbatipur', 'Parbatipur', 332, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    652, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6591, 
    'Kashimpur', 'Raninagar', 333, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    653, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6590, 
    'Raninagar', 'Raninagar', 333, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    100, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8110, 'Kotalipara', 
    'Kotalipara', 334, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    695, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6291, 
    'Premtoli', 'Godagari', 335, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    786, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8321, 'Mirzakalu', 
    'Borhanuddin UPO', 336, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    785, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8320, 'Borhanuddin UPO', 
    'Borhanuddin UPO', 336, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    560, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5341, 
    'Chilahati', 'Domar', 337, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    561, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5340, 
    'Domar', 'Domar', 337, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    675, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6630, 'Chatmohar', 
    'Chatmohar', 339, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    399, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3250, 
    'Baralekha', 'Baralekha', 341, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    402, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3253, 
    'Purbashahabajpur', 'Baralekha', 
    341, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    400, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3252, 
    'Dhakkhinbag', 'Baralekha', 341, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    110, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2320, 
    'Hossenpur', 'Hossenpur', 342, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    403, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3220, 
    'Kamalganj', 'Kamalganj', 343, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    405, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3224, 
    'Munshibazar', 'Kamalganj', 343, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    404, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3221, 
    'Keramatnaga', 'Kamalganj', 343, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    407, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3223, 
    'Shamsher Nagar', 'Kamalganj', 343, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    406, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3222, 
    'Patrakhola', 'Kamalganj', 343, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    788, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8341, 'Dularhat', 
    'Charfashion', 344, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    548, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5620, 
    'Ulipur', 'Ulipur', 5, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    140, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1860, 
    'Doulatpur', 'Doulatpur', 8, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    299, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1900, 'Tangail Sadar', 
    'Tangail Sadar', 10, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    481, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3160, 'Gopalgannj', 
    'Gopalganj', 37, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1012, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9401, 
    'Satkhira Islamia Acc', 'Satkhira Sadar', 
    56, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    16, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1219, 'KhilgaonTSO', 
    'Khilgaon', 92, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    171, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1502, 
    'Mirkadim', 'Munshiganj Sadar', 
    156, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    99, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8132, 'Ratoil', 
    'Kashiani', 173, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    96, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8133, 'Jonapur', 
    'Kashiani', 173, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    69, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1702, 'Chandna', 
    'Gazipur Sadar', 245, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    659, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6432, 
    'Baraigram', 'Harua', 283, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    789, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8342, 'Keramatganj', 
    'Charfashion', 344, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    787, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8340, 'Charfashion', 
    'Charfashion', 344, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    904, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7310, 
    'Harinakundu', 'Harinakundu', 345, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    546, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5640, 
    'Roumari', 'Roumari', 347, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    792, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8360, 'Hajirhat', 
    'Hajirhat', 348, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    378, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3310, 
    'Bahubal', 'Bahubal', 349, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    915, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9241, 'Rupsha', 
    'Alaipur', 350, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    913, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9240, 'Alaipur', 
    'Alaipur', 350, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    914, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9242, 'Belphulia', 
    'Alaipur', 350, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    617, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5810, 
    'Shibganj', 'Shibganj', 351, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    72, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1750, 'Kaliakaar', 
    'Kaliakaar', 352, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    73, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1751, 'Safipur', 
    'Kaliakaar', 352, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    552, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5500, 
    'Lalmonirhat Sadar', 'Lalmonirhat Sadar', 
    353, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    551, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5502, 
    'Kulaghat SO', 'Lalmonirhat Sadar', 
    353, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    553, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Lalmonirhat', 44, 'Rangpur', 4, 5501, 
    'Moghalhat', 'Lalmonirhat Sadar', 
    353, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    937, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9291, 'Amadee', 
    'Koyra', 354, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    50, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7870, 'Alfadanga', 
    'Alfadanga', 356, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    893, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7405, 'Rupdia', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    890, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7403, 'Jashore canttonment', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    891, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7400, 'Jashore Sadar', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    892, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7401, 'Jashore Upa-Shahar', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    888, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7407, 'Churamankathi', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    889, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7404, 'Jashore Airbach', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    886, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7406, 'Basundia', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    887, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7402, 'Chanchra', 
    'Jashore Sadar', 357, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    259, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1913, 'Elasin', 
    'Delduar', 358, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    263, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1912, 'Patharail', 
    'Delduar', 358, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    262, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1915, 'Lowhati', 
    'Delduar', 358, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    261, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1911, 'Jangalia', 
    'Delduar', 358, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    722, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6773, 
    'Kaijuri', 'Shahjadpur', 359, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    724, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6770, 
    'Shahjadpur', 'Shahjadpur', 359, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    723, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6771, 
    'Porjana', 'Shahjadpur', 359, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    721, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6772, 
    'Jamirta', 'Shahjadpur', 359, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    701, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6206, 
    'Binodpur Bazar', 'Rajshahi Sadar', 
    360, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    707, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6205, 
    'Rajshahi University', 'Rajshahi Sadar', 
    360, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    335, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2210, 'Muktagachha', 'Muktagachha', 
    7, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    28, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1324, 'Khalpar', 
    'Nawabganj', 11, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    202, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1413, 
    'BIDS', 'Bandar', 18, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    111, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2390, 
    'Itna', 'Itna', 25, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    349, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2222, 'Ram Amritaganj', 'Trishal', 
    28, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    217, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1640, 
    'Belabo', 'Belabo', 31, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    213, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1460, 
    'Rupganj', 'Rupganj', 34, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1010, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9403, 
    'Budhhat', 'Satkhira Sadar', 56, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    537, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5630, 
    'Chilmari', 'Chilmari', 64, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    725, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6701, 
    'Raipur', 'Sirajganj Sadar', 66, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1001, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9440, 
    'Kaliganj UPO', 'Kaliganj UPO', 
    109, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    126, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2316, 
    'Tarial', 'Tarial', 120, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    934, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9208, 'Khulna University', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    170, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1503, 
    'Kathakhali', 'Munshiganj Sadar', 
    156, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    767, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8270, 
    'Mahendiganj', 'Mahendiganj', 159, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    270, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1992, 'Hemnagar', 
    'Gopalpur', 172, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    97, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8130, 'Kashiani', 
    'Kashiani', 173, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    220, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1650, 
    'Monohordi', 'Monohordi', 185, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    988, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7521, 'Mohajan', 
    'Mohajan', 198, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    197, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1451, 
    'Gopaldi', 'Araihazar', 67, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    416, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3201, 
    'Barakapan', 'Moulvibazar Sadar', 
    204, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    984, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narail', 30, 'Khulna', 7, 7512, 'Itna', 
    'Laxmipasha', 215, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    869, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9322, 
    'Telisatee', 'Morelganj', 218, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    160, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1512, 
    'Rasulpur', 'Gajaria', 242, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    289, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1943, 'Warri paikpara', 
    'Mirzapur', 249, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    340, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2205, 'Pearpur', 'Mymensingh Sadar', 
    251, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    475, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3150, 'Goainhat', 
    'Goainhat', 268, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    192, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1524, 
    'Hasail', 'Tangibari', 271, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    191, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1525, 
    'Dighirpar', 'Tangibari', 271, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    298, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1902, 'Santosh', 
    'Tangail Sadar', 10, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    175, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1541, 
    'Kola', 'Sirajdikhan', 19, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    803, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8430, 
    'Kathalia', 'Kathalia', 23, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    304, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2020, 
    'Islampur', 'Islampur', 40, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1002, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9441, 
    'Nalta Mubaroknagar', 'Kaliganj UPO', 
    109, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    920, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9271, 'Dakup', 
    'Chalna Bazar', 110, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    265, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1983, 'Dhalapara', 
    'Ghatail', 131, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    268, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1981, 'Zahidganj', 
    'Ghatail', 131, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    584, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5405, 
    'Rangpur Carmiecal Col', 'Rangpur Sadar', 
    132, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    758, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8204, 
    'Patang', 'Barishal Sadar', 142, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    98, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8131, 'Ramdia College', 
    'Kashiani', 173, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    242, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajbari', 40, 'Dhaka', 1, 7711, 'Khankhanapur', 
    'Rajbari Sadar', 184, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    565, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5301, 
    'Nilphamari Sugar Mil', 'Nilphamari Sadar', 
    203, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    894, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7420, 'Jhikargachha', 
    'Jhikargachha', 217, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    70, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1700, 'Gazipur Sadar', 
    'Gazipur Sadar', 245, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    35, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1341, 'Dairy Farm', 
    'Savar', 270, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    375, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2100, 
    'Sherpur Shadar', 'Sherpur Shadar', 
    284, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    319, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2050, 
    'Shorishabari', 'Shorishabari', 
    288, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    922, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9221, 'Chandni Mahal', 
    'Digalia', 297, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    446, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3020, 
    'Sachna', 'Sachna', 338, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    938, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9290, 'Koyra', 
    'Koyra', 354, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    245, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Shariatpur', 47, 'Dhaka', 1, 8040, 
    'Damudhya', 'Damudhya', 355, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    260, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1914, 'Hinga Nagar', 
    'Delduar', 358, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    258, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1910, 'Delduar', 
    'Delduar', 358, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    708, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6203, 
    'Sapura', 'Rajshahi Sadar', 360, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    706, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6000, 
    'Rajshahi Sadar', 'Rajshahi Sadar', 
    360, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    704, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6202, 
    'Rajshahi Canttonment', 'Rajshahi Sadar', 
    360, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    703, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6204, 
    'Kazla', 'Rajshahi Sadar', 360, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    702, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6100, 
    'Ghuramara', 'Rajshahi Sadar', 360, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    705, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6201, 
    'Rajshahi Court', 'Rajshahi Sadar', 
    360, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    776, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8280, 
    'Luxmibardhan', 'Bakerganj', 361, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    774, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8284, 
    'kalaskati', 'Bakerganj', 361, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    351, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2420, 'Susnng Durgapur', 'Susung Durgapur', 
    29, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    354, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2450, 'Dharampasha', 'Dharmapasha', 
    42, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    144, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1804, 
    'Barangail', 'Manikganj Sadar', 
    48, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    352, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2470, 'Atpara', 'Atpara', 69, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    377, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3360, 
    'Azmireeganj', 'Azmireeganj', 78, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    697, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6210, 
    'Lalitganj', 'Lalitganj', 106, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    214, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1431, 
    'Adamjeenagar', 'Siddirganj', 111, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    363, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2446, 'Mohanganj', 'Mohanganj', 119, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    371, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2151, 
    'Gonopaddi', 'Nakla', 139, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    173, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1501, 
    'Rikabibazar', 'Munshiganj Sadar', 
    156, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    381, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3352, 
    'Kadirganj', 'Baniachang', 178, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    380, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3351, 
    'Jatrapasha', 'Baniachang', 178, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    412, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3232, 
    'Langla', 'Kulaura', 235, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    384, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3322, 
    'Narapati', 'Chunarughat', 241, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    364, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2401, 'Baikherhati', 'Netrakona Sadar', 
    300, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    401, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3251, 
    'Juri', 'Baralekha', 341, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    773, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8281, 
    'Charamandi', 'Bakerganj', 361, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    775, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8282, 
    'Padri Shibpur', 'Bakerganj', 361, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    777, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8283, 
    'Shialguni', 'Bakerganj', 361, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    353, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2440, 'Barhatta', 'Barhatta', 362, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    324, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2233, 'Kandipara', 'Gaforgaon', 363, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    325, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2231, 'Shibganj', 'Gaforgaon', 363, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    322, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2234, 'Duttarbazar', 'Gaforgaon', 
    363, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    323, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2230, 'Gaforgaon', 'Gaforgaon', 363, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    326, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2232, 'Usti', 'Gaforgaon', 363, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    20, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1207, 'Mohammadpur Housing', 
    'Mohammadpur', 364, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    21, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1225, 'Sangsad BhabanTSO', 
    'Mohammadpur', 364, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    3, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1351, 'Kalampur', 
    'Dhamrai', 365, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    575, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5431, 
    'Shyampur', 'Badarganj', 366, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    587, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5430, 
    'Badarganj', 'Badarganj', 366, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    579, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5460, 
    'Mithapukur', 'Mithapukur', 367, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    48, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1208, 'Dhaka Politechnic', 
    'Tejgaon Industrial Area', 368, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    568, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Panchagarh', 20, 'Rangpur', 4, 5010, 
    'Boda', 'Boda', 369, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    439, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3050, 
    'Ghungiar', 'Ghungiar', 370, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    25, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1325, 'Churain', 
    'Nawabganj', 11, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    482, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3163, 'Ranaping', 
    'Gopalganj', 37, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    199, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1441, 
    'Bara Nagar', 'Baidder Bazar', 46, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    369, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sherpur', 7, 'Mymensingh', 2, 2140, 
    'Bakshigonj', 'Bakshigonj', 52, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    346, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2252, 'Tarakanda', 'Phulpur', 88, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    466, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3174, 'Salia bazar', 
    'Bianibazar', 89, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    465, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3172, 'Mathiura', 
    'Bianibazar', 89, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    461, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3170, 'Bianibazar', 
    'Bianibazar', 89, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    79, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1712, 'Ershad Nagar', 
    'Monnunagar', 98, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    700, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6260, 
    'Putia', 'Putia', 99, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    608, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5820, 
    'Gabtoli', 'Gabtoli', 107, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1003, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9442, 
    'Ratanpur', 'Kaliganj UPO', 109, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    585, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5400, 
    'Rangpur Sadar', 'Rangpur Sadar', 
    132, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    819, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8602, 
    'Dumkee', 'Patuakhali Sadar', 205, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    180, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1551, 
    'Rarikhal', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    458, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3129, 'Natun Bazar', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    456, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3121, 'Karua', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    368, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2411, 'Shamgonj', 'Purbadhola', 233, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    959, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7003, 'Islami University', 
    'Kushtia Sadar', 246, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    339, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2200, 'Mymensingh Sadar', 'Mymensingh Sadar', 
    251, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    470, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3132, 'Doulathpur', 
    'Bishwanath', 276, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    467, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3130, 'Bishwanath', 
    'Bishwanath', 276, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    135, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7902, 
    'Kulpaddi', 'Madaripur Sadar', 277, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    317, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2055, 
    'Jamuna Sar Karkhana', 'Shorishabari', 
    288, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    926, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9222, 'Senhati', 
    'Digalia', 297, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    592, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5470, 
    'Pirganj', 'Pirganj', 4, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    103, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8140, 'Maksudpur', 
    'Maksudpur', 12, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    101, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8141, 'Batkiamari', 
    'Maksudpur', 12, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    967, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7050, 'Rafayetpur', 
    'Rafayetpur', 13, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    358, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2480, 'Kendua', 'Kendua', 16, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    308, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2011, 
    'Jamalpur', 'Malandah', 21, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    618, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5826, 
    'Sonatola', 'Sonatola', 27, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    348, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2223, 'Dhala', 'Trishal', 28, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    480, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3161, 'Dhaka Dakkhin', 
    'Gopalganj', 37, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    102, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gopalganj', 1, 'Dhaka', 1, 8142, 'Khandarpara', 
    'Maksudpur', 12, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    178, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1540, 
    'Sirajdikhan', 'Sirajdikhan', 19, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    4, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1209, 'Jigatala TSO', 
    'Dhanmondi', 20, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    999, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9414, 
    'Khordo', 'kalaroa', 53, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    395, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3372, 
    'Golduba', 'Nabiganj', 58, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    726, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6702, 
    'Rashidabad', 'Sirajganj Sadar', 
    66, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    53, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7861, 'Rupatpat', 
    'Boalmari', 95, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    699, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6212, 
    'Shyampur', 'Lalitganj', 106, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    121, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2371, 
    'Abdullahpur', 'Mithamoin', 127, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    221, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narsingdi', 15, 'Dhaka', 1, 1605, 
    'Karimpur', 'Narsingdi Sadar', 137, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    717, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6721, 
    'Malonga', 'Dhangora', 14, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    693, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6240, 
    'Durgapur', 'Durgapur', 39, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    743, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8242, 
    'Paisarhat', 'Agailzhara', 70, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    690, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6251, 
    'Taharpur', 'Bhabaniganj', 71, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    321, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2216, 'Fulbaria', 'Fulbaria', 84, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    781, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8224, 
    'Shikarpur', 'Uzirpur', 100, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    594, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Thakurgaon', 52, 'Rangpur', 4, 5120, 
    'Rani Sankail', 'Rani Sankail', 
    101, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    692, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6271, 
    'Sarda', 'Charghat', 116, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    755, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8201, 
    'Bukhainagar', 'Barishal Sadar', 
    142, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    751, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8260, 
    'Barajalia', 'Barajalia', 164, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    580, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5450, 
    'Pirgachha', 'Pirgachha', 174, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    688, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6280, 
    'Bagha', 'Bagha', 219, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    687, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6281, 
    'Arani', 'Bagha', 219, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    719, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6710, 
    'Kazipur', 'Kazipur', 226, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    740, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8720, 
    'Patharghata', 'Patharghata', 250, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    734, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8730, 
    'Bamna', 'Bamna', 261, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    694, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rajshahi', 14, 'Rajshahi', 5, 6290, 
    'Godagari', 'Godagari', 335, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    425, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3010, 
    'Bishamsapur', 'Bishamsarpur', 340, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    736, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8701, 
    'Nali Bandar', 'Barguna Sadar', 
    346, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    735, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8700, 
    'Barguna Sadar', 'Barguna Sadar', 
    346, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    164, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1333, 
    'Haridia', 'Lohajong', 371, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    166, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1531, 
    'Korhati', 'Lohajong', 371, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    167, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1530, 
    'Lohajang', 'Lohajong', 371, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    165, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1533, 
    'Haridia DESO', 'Lohajong', 371, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    168, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1335, 
    'Madini Mandal', 'Lohajong', 371, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    169, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1535, 
    'Medini Mandal EDSO', 'Lohajong', 
    371, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    163, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1532, 
    'Haldia SO', 'Lohajong', 371, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    161, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1334, 
    'Gouragonj', 'Lohajong', 371, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    162, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1534, 
    'Gouragonj', 'Lohajong', 371, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    574, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5420, 
    'Taraganj', 'Taraganj', 372, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    491, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3106, 'Birahimpur', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    499, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3109, 'Ranga Hajiganj', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    495, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3112, 'Kamalbazer', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    494, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3111, 'Kadamtali', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    500, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3114, 'Shahajalal Science &', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    502, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3100, 'Sylhet Sadar', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    492, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3107, 'Jalalabad', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    497, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3113, 'Lalbazar', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    547, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5621, 
    'Bozra hat', 'Ulipur', 5, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1014, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9421, 
    'Patkelghata', 'Taala', 15, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    109, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2350, 
    'Bhairab', 'Bhairob', 17, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    802, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8431, 
    'Amua', 'Kathalia', 23, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    784, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8301, 'Joynagar', 
    'Bhola Sadar', 24, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    347, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2221, 'Ahmadbad', 'Trishal', 28, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    673, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6683, 'Puran Bharenga', 
    'Bera', 44, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    849, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9300, 
    'Bagerhat Sadar', 'Bagerhat Sadar', 
    50, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    793, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bhola', 34, 'Barishal', 6, 8350, 'Hatshoshiganj', 
    'Hatshoshiganj', 54, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1011, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9402, 
    'Gunakar kati', 'Satkhira Sadar', 
    56, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    538, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kurigram', 25, 'Rangpur', 4, 5631, 
    'Jorgachh', 'Chilmari', 64, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    632, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Joypurhat', 51, 'Rajshahi', 5, 5941, 
    'jamalganj', 'Akkelpur', 65, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    650, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6511, 
    'Manda', 'Prasadpur', 82, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    52, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7860, 'Boalmari', 
    'Boalmari', 95, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    128, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7930, 
    'Barhamganj', 'Barhamganj', 96, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    578, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Rangpur', 45, 'Rangpur', 4, 5440, 
    'Kaunia', 'Kaunia', 108, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    977, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Meherpur', 22, 'Khulna', 7, 7110, 
    'Gangni', 'Gangni', 118, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    132, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Madaripur', 18, 'Dhaka', 1, 7921, 
    'Sahabrampur', 'kalkini', 128, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    733, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8710, 
    'Amtali', 'Amtali', 26, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    350, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2220, 'Trishal', 'Trishal', 28, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    278, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1972, 'Nagbari', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    877, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7200, 
    'Chuadanga Sadar', 'Chuadanga Sadar', 
    33, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    212, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1463, 
    'Nagri', 'Rupganj', 34, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    508, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5220, 
    'Birganj', 'Birganj', 38, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    898, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7460, 'Noapara', 
    'Noapara', 43, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    141, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1840, 
    'Gheor', 'Gheor', 55, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    394, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3373, 
    'Digalbak', 'Nabiganj', 58, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    901, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jashore', 12, 'Khulna', 7, 7431, 'Benapole', 
    'Sarsa', 63, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    196, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1450, 
    'Araihazar', 'Araihazar', 67, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    907, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhenaidah', 9, 'Khulna', 7, 7330, 
    'Kotchandpur', 'Kotchandpur', 81, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    118, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kishoreganj', 4, 'Dhaka', 1, 2303, 
    'Nilganj', 'Kishoreganj Sadar', 
    83, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    558, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5350, 
    'Dimla', 'Dimla', 86, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    423, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3214, 
    'Satgaon', 'Srimangal', 9, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    205, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1412, 
    'Nabiganj', 'Bandar', 18, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    275, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1970, 'Kalihati', 
    'Kalihati', 30, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    878, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chuadanga', 29, 'Khulna', 7, 7201, 
    'Munshiganj', 'Chuadanga Sadar', 
    33, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    613, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bogura', 42, 'Rajshahi', 5, 5830, 
    'Sariakandi', 'Sariakandi', 36, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    478, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3165, 'Chandanpur', 
    'Gopalganj', 37, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    302, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2021, 
    'Durmoot', 'Islampur', 40, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    148, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Manikganj', 28, 'Dhaka', 1, 1800, 
    'Manikganj Sadar', 'Manikganj Sadar', 
    48, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    995, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9415, 
    'Chandanpur', 'kalaroa', 53, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    398, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Habiganj', 17, 'Sylhet', 3, 3370, 
    'Nabiganj', 'Nabiganj', 58, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    965, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Kushtia', 32, 'Khulna', 7, 7031, 'Poradaha', 
    'Mirpur', 73, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    645, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6550, 
    'Nitpur', 'Nitpur', 76, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    646, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6552, 
    'Panguria', 'Nitpur', 76, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    460, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3123, 'Tajpur', 
    'Balaganjgfhfghj', 77, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    778, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8223, 
    'Dakuarhat', 'Uzirpur', 100, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    643, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Naogaon', 19, 'Rajshahi', 5, 6500, 
    'Naogaon Sadar', 'Naogaon Sadar', 
    105, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    215, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1432, 
    'LN Mills', 'Siddirganj', 111, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    671, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6682, 'Kashinathpur', 
    'Bera', 44, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    24, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1323, 'Agla', 
    'Nawabganj', 11, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    296, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Tangail', 16, 'Dhaka', 1, 1903, 'Korotia', 
    'Tangail Sadar', 10, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    29, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1320, 'Nawabganj', 
    'Nawabganj', 11, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    201, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1410, 
    'Bandar', 'Bandar', 18, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    177, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1544, 
    'Shekher Nagar', 'Sirajdikhan', 
    19, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    309, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2013, 
    'Mahmudpur', 'Malandah', 21, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    742, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barishal', 41, 'Barishal', 6, 8241, 
    'Gaila', 'Agailzhara', 70, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    526, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5700, 
    'Gaibandha Sadar', 'Gaibandha Sadar', 
    97, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    216, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1430, 
    'Siddirganj', 'Siddirganj', 111, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    808, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jhalokati', 6, 'Barishal', 6, 8410, 
    'Rajapur', 'Rajapur', 126, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    992, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9461, 
    'Baradal', 'Ashashuni', 140, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    933, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9201, 'Khulna Shipyard', 
    'Khulna Sadar', 155, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    57, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7800, 'Faridpursadar', 
    'Faridpur Sadar', 158, 'Zone1', 1, 
    1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    946, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9252, 'Chuknagar', 
    'Sajiara', 177, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    417, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Moulvibazar', 2, 'Sylhet', 3, 3202, 
    'Monumukh', 'Moulvibazar Sadar', 
    204, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    822, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8603, 
    'Rahimabad', 'Patuakhali Sadar', 
    205, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    179, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Munshiganj', 8, 'Dhaka', 1, 1557, 
    'Baghra', 'Sreenagar', 220, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    451, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3120, 'Balaganj', 
    'Balaganj', 228, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    338, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Mymensingh', 11, 'Mymensingh', 2, 
    2201, 'Kawatkhali', 'Mymensingh Sadar', 
    251, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    313, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2040, 
    'Madarganj', 'Madarganj', 269, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    471, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3134, 'Singer kanch', 
    'Bishwanath', 276, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    660, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6431, 
    'Dayarampur', 'Harua', 283, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    523, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dinajpur', 26, 'Rangpur', 4, 5216, 
    'Setabganj', 'Setabganj', 291, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    12, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1312, 'Ati', 
    'Keraniganj', 294, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    943, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9283, 'Katipara', 
    'Paikgachha', 295, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    713, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sirajganj', 50, 'Rajshahi', 5, 6742, 
    'Rajapur', 'Belkuchi', 296, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    438, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sunamganj', 38, 'Sylhet', 3, 3070, 
    'Duara bazar', 'Duara bazar', 303, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    498, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3108, 'Mogla', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    504, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3101, 'Sylhet Cadet Col', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    503, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3102, 'Sylhet Biman Bondar', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    501, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3105, 'Silam', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    493, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3104, 'Jalalabad Cantoment', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    496, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3103, 'Khadimnagar', 
    'Sylhet Sadar', 373, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    359, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2460, 'Khaliajhri', 'Khaliajuri', 
    374, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    360, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2462, 'Shaldigha', 'Khaliajuri', 
    374, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    84, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1748, 'Boubi', 
    'Sreepur', 375, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    85, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1745, 'Kawraid', 
    'Sreepur', 375, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    86, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1744, 'Satkhamair', 
    'Sreepur', 375, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    83, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1747, 'Bashamur', 
    'Sreepur', 375, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    82, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gazipur', 23, 'Dhaka', 1, 1743, 'Barmi', 
    'Sreepur', 375, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    563, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5320, 
    'Kishoriganj', 'Kishoriganj', 376, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    839, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8561, 
    'Tushkhali', 'Mathbaria', 377, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    834, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8563, 
    'Gulishakhali', 'Mathbaria', 377, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    837, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8566, 
    'Shilarganj', 'Mathbaria', 377, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    833, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8565, 
    'Betmor Natun Hat', 'Mathbaria', 
    377, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    835, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8562, 
    'Halta', 'Mathbaria', 377, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    838, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8564, 
    'Tiarkhali', 'Mathbaria', 377, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    836, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pirojpur', 46, 'Barishal', 6, 8560, 
    'Mathbaria', 'Mathbaria', 377, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    531, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5760, 
    'Phulchhari', 'Phulchhari', 378, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    530, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5761, 
    'Bharatkhali', 'Phulchhari', 378, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    823, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8610, 
    'Subidkhali', 'Subidkhali', 379, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    667, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6400, 
    'Natore Sadar', 'Natore Sadar', 
    380, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    664, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6402, 
    'Baiddyabal Gharia', 'Natore Sadar', 
    380, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    665, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6401, 
    'Digapatia', 'Natore Sadar', 380, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    666, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Natore', 21, 'Rajshahi', 5, 6403, 
    'Madhnagar', 'Natore Sadar', 380, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    813, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8623, 
    'Kalishari', 'Bauphal', 381, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    812, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8624, 
    'Kalaia', 'Bauphal', 381, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    811, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8622, 
    'Birpasha', 'Bauphal', 381, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    810, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8620, 
    'Bauphal', 'Bauphal', 381, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    809, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Patuakhali', 53, 'Barishal', 6, 8621, 
    'Bagabandar', 'Bauphal', 381, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    676, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Pabna', 13, 'Rajshahi', 5, 6610, 'Debottar', 
    'Debottar', 382, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    529, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Gaibandha', 37, 'Rangpur', 4, 5730, 
    'Palashbari', 'Palashbari', 383, 
    'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    866, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9385, 
    'Pak Gangni', 'Mollahat', 384, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    865, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9384, 
    'Nagarkandi', 'Mollahat', 384, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    861, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9383, 
    'Charkulia', 'Mollahat', 384, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    863, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9381, 
    'Kahalpur', 'Mollahat', 384, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    864, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9380, 
    'Mollahat', 'Mollahat', 384, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    862, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Bagerhat', 31, 'Khulna', 7, 9382, 
    'Dariala', 'Mollahat', 384, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    64, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7821, 'Hat Krishapur', 
    'Sadarpur', 385, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    65, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7820, 'Sadarpur', 
    'Sadarpur', 385, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    63, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7822, 'Bishwa jaker Manjil', 
    'Sadarpur', 385, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    950, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9231, 'Pak Barasat', 
    'Terakhada', 386, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    951, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Khulna', 43, 'Khulna', 7, 9230, 'Terakhada', 
    'Terakhada', 386, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    362, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Netrakona', 10, 'Mymensingh', 2, 
    2456, 'Moddoynagar', 'Moddhynagar', 
    388, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    30, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1205, 'New Market TSO', 
    'New market', 389, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    490, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Sylhet', 48, 'Sylhet', 3, 3140, 'Kompanyganj', 
    'Kompanyganj', 390, 'Zone1', 1, 1, 
    1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    9, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1330, 'Joypara', 
    'Joypara', 391, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    10, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1332, 'Narisha', 
    'Joypara', 391, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    11, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Dhaka', 36, 'Dhaka', 1, 1331, 'Palamganj', 
    'Joypara', 391, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    738, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8741, 
    'Darul Ulam', 'Betagi', 392, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    737, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Barguna', 35, 'Barishal', 6, 8740, 
    'Betagi', 'Betagi', 392, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    208, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Narayanganj', 49, 'Dhaka', 1, 1400, 
    'Narayanganj Sadar', 'Narayanganj Sadar', 
    393, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    62, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7841, 'Talma', 
    'Nagarkanda', 394, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    61, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Faridpur', 39, 'Dhaka', 1, 7840, 'Nagarkanda', 
    'Nagarkanda', 394, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    562, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Nilphamari', 24, 'Rangpur', 4, 5330, 
    'Jaldhaka', 'Jaldhaka', 395, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1007, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9450, 
    'Nakipur', 'Nakipur', 396, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1004, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9453, 
    'Buri Goalini', 'Nakipur', 396, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1009, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9451, 
    'Noornagar', 'Nakipur', 396, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1005, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9454, 
    'Gabura', 'Nakipur', 396, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1008, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9452, 
    'Naobeki', 'Nakipur', 396, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    1006, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Satkhira', 33, 'Khulna', 7, 9455, 
    'Habinagar', 'Nakipur', 396, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    620, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6303, 'Amnura', 'Chapai Nawabganj Sadar', 
    189, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    626, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6321, 'Gomashtapur', 'Rohanpur', 
    171, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    623, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6302, 'Ramchandrapur', 'Chapai Nawabganj Sadar', 
    189, 'Zone1', 1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    625, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Chapai Nawabganj', 27, 'Rajshahi', 
    5, 6310, 'Nachol', 'Nachol', 387, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    306, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2001, 
    'Nandina', 'Jamalpur', 400, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    305, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2000, 
    'Jamalpur', 'Jamalpur', 400, 'Zone1', 
    1, 1, 1
  );
/* INSERT QUERY */
INSERT INTO public.master_address(
  id, createdon, status, updatedon, district, 
  district_id, division, division_id, 
  postalcode, suboffice, thana, thanaid, 
  zone, zone_id, createdby_id, updatedby_id
) 
VALUES 
  (
    307, '2020-01-23 14:00', 0, '2020-01-23 14:00', 
    'Jamalpur', 5, 'Mymensingh', 2, 2002, 
    'Narundi', 'Jamalpur', 400, 'Zone1', 
    1, 1, 1
  );


INSERT INTO config(id, configtype, configvalue,createdby, createdon, status, updatedby, updatedon)
VALUES
(
1, 'VolumetricWeightFormula', 'dead_weight', NULL, '2020-07-17 14:55:30.335', 0, NULL, '2020-07-17 14:55:30.335'
);

INSERT INTO config(id, configtype, configvalue, createdby, createdon, status, updatedby, updatedon)
VALUES
(
2, 'SyncTime', '24', NULL, '2020-07-17 14:55:30.335', 0, NULL, '2020-07-17 14:55:30.335'
);

/* INSERT QUERY  FOR SCHEDULER */
INSERT INTO config(id, configtype, configvalue,createdby, createdon, status, updatedby, updatedon)
VALUES
(
3, 'schedulertimepattern', '0 1 15 * * ?',NULL,'2020-07-17 14:55:30.335', 0, NULL, '2020-07-17 14:55:30.335'
);

/* INSERT QUERY  FOR SCHEDULER */
INSERT INTO config(id, configtype, configvalue,createdby, createdon, status, updatedby, updatedon)
VALUES
(
4, 'cleanupdurationinmonth', '6',NULL,'2020-07-17 14:55:30.335', 0, NULL, '2020-07-17 14:55:30.335'
);

update rate_table set updatedon= '2020-01-23 14:00:00';
update rate_table set createdon= '2020-01-23 14:00:00';
update rate_table set createdby_id= 1;
update rate_table set updatedby_id= 1;
INSERT INTO config(id, configtype, configvalue, createdby, createdon, status, updatedby, updatedon)
VALUES
(
5, 'tax1', 'CGST', NULL, '2020-07-17 14:55:30.335', 0, NULL, '2020-07-17 14:55:30.335'
);

INSERT INTO config(id, configtype, configvalue, createdby, createdon, status, updatedby, updatedon)
VALUES
(
6, 'tax2', 'SGST', NULL, '2020-07-17 14:55:30.335', 0, NULL, '2020-07-17 14:55:30.335'
);

INSERT INTO config(id, configtype, configvalue, createdby, createdon, status, updatedby, updatedon)
VALUES
(
7, 'taxApplicable', 'YES', NULL, '2020-07-17 14:55:30.335', 0, NULL, '2020-07-17 14:55:30.335'
);
