DROP TABLE PRODUCTS;
CREATE TABLE PRODUCTS AS SELECT * FROM CSVREAD('jcpenney_com-ecommerce_sample.csv');