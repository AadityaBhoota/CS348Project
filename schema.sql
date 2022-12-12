--
-- TABLE STRUCTURE FOR: buyers
--

CREATE TABLE buyers (
  username varchar(100) NOT NULL,
  password varchar(30) NOT NULL,
  PRIMARY KEY (username)
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
  PRIMARY KEY (buyer_id, seller_id, product_id, price)
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

DELIMITER //
CREATE PROCEDURE `warehouse_quantity`(IN productId INT, IN sellerId VARCHAR(30), IN qty INT, IN buyer VARCHAR(30))
BEGIN
	declare prc DEC(10,2);
    select price into prc from warehouse w where w.product_id = productId and w.seller_id = sellerId;

	UPDATE warehouse
    SET quantity = quantity - qty,
		items_sold = items_sold + 1
    WHERE product_id = productId AND seller_id = sellerId;


    insert into orders values(buyer, sellerId, productID, prc, 1);

    DELETE FROM warehouse
    WHERE product_id = productId AND seller_id = sellerId AND quantity = 0;

	DELETE FROM cart
    WHERE buyer_id = buyer;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE `sp1`(IN prod_name varchar(30), IN quantitySelected int, IN category varchar(30), IN seller_id varchar(30), IN priceSelected float)
BEGIN
	DECLARE product_exists int;
    DECLARE prod_id int;
	DECLARE que_cur CURSOR for
        select COUNT(name) from product where name like CONCAT("%", prod_name, "%");
	DECLARE prod_id_cur CURSOR for
		Select product_id from product where name = prod_name;
	DECLARE max_prod_id_cur CURSOR for
		Select MAX(product_id)+1 from product;

	open que_cur;
	FETCH que_cur into product_exists;

	IF product_exists > 0 THEN   -- Which means there is a product with this name
		open prod_id_cur;
        fetch prod_id_cur into prod_id;
        insert into warehouse values (seller_id, prod_id, quantitySelected, priceSelected, 0);
        close prod_id_cur;
	ELSE
		open max_prod_id_cur;
        fetch max_prod_id_cur into prod_id;
        insert into product values (prod_id, prod_name, category, category);
        insert into warehouse values (seller_id, prod_id, quantitySelected, priceSelected, 0);
        close max_prod_id_cur;
	END IF;

	close que_cur;
END //

DELIMITER ;
