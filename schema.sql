--
-- TABLE STRUCTURE FOR: buyers
--

CREATE TABLE buyers (
  username varchar(100) NOT NULL,
  password varchar(30) NOT NULL,
  PRIMARY KEY username (username)
);

--
-- TABLE STRUCTURE FOR: orders
--

CREATE TABLE orders (
  buyer_id varchar(30) NOT NULL,
  seller_id varchar(30) NOT NULL,
  product_id int(9) NOT NULL,
  price decimal(10,2) NOT NULL,
  quantity int(11) NOT NULL,
  zipcode int(9) NOT NULL
);

--
-- TABLE STRUCTURE FOR: product
--

CREATE TABLE product (
  product_id int(9) NOT NULL,
  name varchar(30) NOT NULL,
  category varchar(30) NOT NULL,
  brand varchar(30),
  PRIMARY KEY (product_id)
);

--
-- TABLE STRUCTURE FOR: sellers
--

CREATE TABLE sellers (
  username varchar(30) NOT NULL,
  password varchar(30) NOT NULL,
  date_of_creation date,
  PRIMARY KEY (username)
);

--
-- TABLE STRUCTURE FOR: warehouse
--

CREATE TABLE warehouse (
  seller_id varchar(30) NOT NULL,
  product_id int(9) NOT NULL,
  quantity int(11) NOT NULL,
  price decimal(10,2) NOT NULL,
  items_sold int(11),
  PRIMARY KEY (seller_id,product_id)
);

--
-- TABLE STRUCTURE FOR: wishlist
--

CREATE TABLE wishlist (
  buyer_id varchar(30) NOT NULL,
  product_id int(9) NOT NULL,
  price decimal(10,2) NOT NULL,
  PRIMARY KEY (buyer_id,product_id)
);

--
-- TABLE STRUCTURE FOR: cart
--

CREATE TABLE cart (
  buyer_id varchar(30) NOT NULL,
  product_id int(9) NOT NULL,
  price decimal(10,2) NOT NULL,
  quantity int(9) NOT NULL,
  PRIMARY KEY (buyer_id,product_id)
);
