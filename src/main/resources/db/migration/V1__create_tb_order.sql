create table tb_order(
     id_order int primary key auto_increment,
     order_value DECIMAL(10, 2),
     id_customer INT not null,
     creation_order timestamp default CURRENT_TIMESTAMP(),
     last_update_order timestamp default CURRENT_TIMESTAMP(),
     status varchar(20) not null,
     id_pay int,
     mercado_pago_id int,
     qr_code varchar(255),
     status_payment varchar(20)
);