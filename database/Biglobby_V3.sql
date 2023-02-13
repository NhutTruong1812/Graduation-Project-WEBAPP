USE MASTER
GO

IF EXISTS (SELECT NAME FROM SYS.databases WHERE NAME ='Biglobby')
    DROP DATABASE Biglobby
GO

USE MASTER 
CREATE DATABASE Biglobby
GO
USE Biglobby

GO
--TABLE ROLE
CREATE TABLE tbl_role(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	[role] NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(500) NULL
);

GO

--TABLE ACTION
CREATE TABLE tbl_action(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	tbl_action NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(500) NULL
);

GO
--TABLE STATUS
CREATE TABLE tbl_status(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	[status] NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(500) NULL
);

GO
--TABLE PROBLEM
CREATE TABLE tbl_problem(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	problem NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(500) NULL
);

GO
--TABLE USER
CREATE TABLE tbl_user(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	username NVARCHAR(50) UNIQUE NOT NULL,
	[password] NVARCHAR(500) NOT NULL,
	fullname NVARCHAR(50) NOT NULL,
	email NVARCHAR(50) NOT NULL,
	gender BIT NOT NULL,
	phonenum NVARCHAR(10) NOT NULL,
	avatar NVARCHAR(100) NULL,
	blocked BIT DEFAULT 1 NOT NULL,
	last_login SMALLDATETIME NOT NULL
);
GO
--TABLE REGISTERACTIVE
CREATE TABLE tbl_register_active(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT UNIQUE NOT NULL,
	register_date SMALLDATETIME NOT NULL,
	actived BIT DEFAULT 0 NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE AUTHORITY
CREATE TABLE tbl_authority(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_role BIGINT NOT NULL,
	authorize_date SMALLDATETIME NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_role) REFERENCES tbl_role(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE MYBCOIN
CREATE TABLE tbl_my_bcoin(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT UNIQUE NOT NULL,
	coinnum INT NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE BCOINHISTORY
CREATE TABLE tbl_bcoin_history(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	quantity INT NOT NULL,
	id_action BIGINT NOT NULL,
	note NVARCHAR(500) NOT NULL,
	coin_date SMALLDATETIME NOT NULL,
	act_by BIGINT NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_action) REFERENCES tbl_action(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (act_by) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE BSERVICE
CREATE TABLE tbl_bservice(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	bservice NVARCHAR(100) NOT NULL,
	banner NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(500) NOT NULL,
	blocked BIT DEFAULT 0
);

GO
--TABLE MYBSERVICE
CREATE TABLE tbl_mybservice(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_bservice BIGINT NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_bservice) REFERENCES tbl_bservice(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

--TABLE BSERVICEHISTORY
CREATE TABLE tbl_bservice_history(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_bservice BIGINT NOT NULL,
	act_date SMALLDATETIME NOT NULL,
	use_days INT NOT NULL,
	act_by BIGINT NOT NULL,
	id_action BIGINT NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_bservice) REFERENCES tbl_bservice(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (act_by) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_action) REFERENCES tbl_action(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE BSERVICEPRICE
CREATE TABLE tbl_bservice_price(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	price FLOAT NOT NULL,
	price_date SMALLDATETIME NOT NULL,
	price_by BIGINT NOT NULL,
	id_bservice BIGINT NOT NULL,
	note NVARCHAR(500) NOT NULL,
	FOREIGN KEY (price_by) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_bservice) REFERENCES tbl_bservice(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TALBE MYSHOP
CREATE TABLE tbl_myshop(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT UNIQUE NOT NULL,
	intro NVARCHAR(100) NOT NULL,
	[location] NVARCHAR(500) NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE ADDRESS
CREATE TABLE tbl_address(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	fullname NVARCHAR(50) NOT NULL,
	phonenum NVARCHAR(10) NOT NULL,
	[address] NVARCHAR(500) NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE FOLLOWSHOP
CREATE TABLE tbl_follow_shop(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user_follow BIGINT NOT NULL,
	id_shop BIGINT NOT NULL,
	follow_date SMALLDATETIME NOT NULL,
	FOREIGN KEY (id_user_follow) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_shop) REFERENCES tbl_myshop(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

GO
--TABLE DISPLAYFEE
CREATE TABLE tbl_displayfee(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	price_from FLOAT NOT NULL,
	price_to FLOAT NOT NULL,
	fixed_fee FLOAT NOT NULL,
	percent_fee FLOAT NOT NULL,
	[description] NVARCHAR(500) NOT NULL,
);
GO
--TABLE DISPLAYFEEHISTORY
CREATE TABLE tbl_displayfee_history(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	price_from FLOAT NOT NULL,
	price_to FLOAT NOT NULL,
	fixed_fee FLOAT NOT NULL,
	percent_fee FLOAT NOT NULL,
	act_date SMALLDATETIME NOT NULL,
	act_by BIGINT NOT NULL,
	id_displayfee BIGINT NOT NULL,
	id_action BIGINT NOT NULL,
	FOREIGN KEY (act_by) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_displayfee) REFERENCES tbl_displayfee(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_action ) REFERENCES tbl_action(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

GO
--TABLE NEWS
CREATE TABLE tbl_news(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_sent BIGINT NOT NULL,
	id_got BIGINT NOT NULL,
	[content] NVARCHAR(500) NOT NULL,
	news_date SMALLDATETIME NOT NULL,
	[hidden] BIT DEFAULT 0 NOT NULL,
	FOREIGN KEY (id_sent) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_got) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE CATEGORY
CREATE TABLE tbl_category(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	category NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(500) NOT NULL,
	blocked BIT DEFAULT 0 NOT NULL,
	add_by BIGINT NOT NULL,
	id_status BIGINT NOT NULL,
	add_date SMALLDATETIME NOT NULL,
	FOREIGN KEY (add_by) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_status) REFERENCES tbl_status(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE CARD 
CREATE TABLE tbl_card(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	is_product BIT NOT NULL,
	id_status BIGINT NOT NULL,
	[hidden] BIT DEFAULT 0 NOT NULL,
	post_at SMALLDATETIME NOT NULL DEFAULT GETDATE(),
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_status) REFERENCES tbl_status(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE POST
CREATE TABLE tbl_post(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_card BIGINT UNIQUE NOT NULL,
	title NVARCHAR(100) NOT NULL,
	[content] NVARCHAR(500) NOT NULL,
	banner NVARCHAR(100) NULL,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE PRODUCT
CREATE TABLE tbl_product(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_card BIGINT UNIQUE NOT NULL,
	product NVARCHAR(100) NOT NULL,
	banner NVARCHAR(100) NOT NULL,
	available INT NOT NULL,
	[description] NVARCHAR(500) NULL,
	price FLOAT NOT NULL,
	price_percent FLOAT NOT NULL,
	id_category BIGINT NOT NULL,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_category) REFERENCES tbl_category(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE REVIEW
CREATE TABLE tbl_review(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_product BIGINT NOT NULL,
	id_user BIGINT NOT NULL,
	rate FLOAT NOT NULL,
	content NVARCHAR(500) NOT NULL,
	review_at SMALLDATETIME DEFAULT GETDATE(),
	FOREIGN KEY (id_product) REFERENCES tbl_product(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE REP_REVIEW
CREATE TABLE tbl_rep_review(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_review BIGINT NOT NULL,
	id_user BIGINT NOT NULL,
	content NVARCHAR(500) NOT NULL,
	rep_review_at SMALLDATETIME DEFAULT GETDATE(),
	FOREIGN KEY (id_review) REFERENCES tbl_review(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

GO
--TABLE PRODUCTHISTORY 
CREATE TABLE tbl_product_history(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_product BIGINT NOT NULL,
	price FLOAT NOT NULL,
	quantity INT NOT NULL,
	act_date SMALLDATETIME NOT NULL,
	act_by BIGINT NOT NULL,
	id_action BIGINT NOT NULL,
	FOREIGN KEY (id_product) REFERENCES tbl_product(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (act_by) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_action) REFERENCES tbl_action(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

GO
--TABLE CARDSUBBANNER
CREATE TABLE tbl_card_subbanner(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_card BIGINT NOT NULL,
	subbanner NVARCHAR(100) NOT NULL,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE BSERVICESUBBANNER
CREATE TABLE tbl_bservice_subbanner(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_bservice BIGINT NOT NULL,
	subbanner NVARCHAR(100) NOT NULL,
	FOREIGN KEY (id_bservice) REFERENCES tbl_bservice(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE ORDER
CREATE TABLE tbl_order(
	 id BIGINT IDENTITY(1,1) PRIMARY KEY,
	 id_buyer BIGINT NOT NULL,
	 fullname NVARCHAR(100) NOT NULL,
	 phonenum NVARCHAR(10) NOT NULL,
	 [address] NVARCHAR(500) NOT NULL,
	 add_date SMALLDATETIME NOT NULL,
	 note_buyer NVARCHAR(500) NULL,
	 note_saler NVARCHAR(500) NULL,
	 id_status BIGINT NOT NULL,
	 id_saler BIGINT NOT NULL,
	 FOREIGN KEY (id_buyer) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	 FOREIGN KEY (id_status) REFERENCES tbl_status(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	 FOREIGN KEY (id_saler) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
 GO
 --TABLE ORDERDETAIL
 CREATE TABLE tbl_orderdetail(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_order BIGINT NOT NULL,
	id_product BIGINT NOT NULL,
	quantity INT NOT NULL,
	price FLOAT NOT NULL,
	FOREIGN KEY (id_order) REFERENCES tbl_order(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_product) REFERENCES tbl_product(id) ON DELETE NO ACTION ON UPDATE NO ACTION
 );
 GO
 --TABLE CART
 CREATE TABLE tbl_cart(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_product BIGINT NOT NULL,
	quantity INT NOT NULL,
	cart_date SMALLDATETIME NOT NULL, 
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_product) REFERENCES tbl_product(id) ON DELETE NO ACTION ON UPDATE NO ACTION
 );
 GO
 --TABLE LOVECARD
 CREATE TABLE tbl_lovecard(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_card BIGINT NOT NULL,
	love_date SMALLDATETIME NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION
 );
 GO
 --TABLE COMMENTCARD
CREATE TABLE tbl_commentcard(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_card BIGINT NOT NULL,
	comment_date SMALLDATETIME NOT NULL,
	[content] NVARCHAR(500) NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
);
GO
--TABLE REPCOMMENT
CREATE TABLE tbl_repcomment(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_comment BIGINT NOT NULL,
	id_user BIGINT NOT NULL,
	rep_comment_date SMALLDATETIME NOT NULL,
	[content] NVARCHAR(500) NOT NULL,
	FOREIGN KEY (id_comment) REFERENCES tbl_commentcard(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE SHARECARD
CREATE TABLE tbl_sharecard(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_card BIGINT NOT NULL,
	share_date SMALLDATETIME NOT NULL,
	content NVARCHAR(500) NULL,
	[hidden] BIT DEFAULT 0,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE HIDECARD
CREATE TABLE tbl_hidecard(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_card BIGINT NOT NULL,
	hide_date SMALLDATETIME NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE REPORTCARD
CREATE TABLE tbl_reportcard(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_user BIGINT NOT NULL,
	id_card BIGINT NOT NULL,
	report_date SMALLDATETIME NOT NULL,
	id_problem BIGINT NOT NULL,
	note NVARCHAR(500) NULL,
	id_status BIGINT NOT NULL,
	FOREIGN KEY (id_user) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_problem) REFERENCES tbl_problem(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (id_status) REFERENCES tbl_status(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO
--TABLE FAILHISTORY
CREATE TABLE tbl_fail_history(
	id BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_card BIGINT NOT NULL,
	fail_reason NVARCHAR(500) NOT NULL,
	fail_date SMALLDATETIME NOT NULL,
	fail_by BIGINT NOT NULL,
	FOREIGN KEY (id_card) REFERENCES tbl_card(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (fail_by) REFERENCES tbl_user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

--DONE


USE [Biglobby]
GO
SET IDENTITY_INSERT [dbo].[tbl_status] ON 

INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (1, N'DANGDUYET', N'Đang duyệt')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (2, N'VIPHAM', N'Vi phạm')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (3, N'SANSANG', N'Sẵn sàng')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (4, N'HETHANG', N'Hết hàng')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (5, N'DANGXACNHAN', N'Đang xác nhận, khi người mua đã đặt hàng')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (6, N'DONGYGIAODICH', N'Đồng ý giao dịch, sau khi trao đổi qua tin nhắn giữa người bán và người mua')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (7, N'THANHCONG', N'Thành công, đơn hàng đã được người mua và người bán xác nhận hoàn tất')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (8, N'HUYMUA', N'Hùy mua, lý do chủ quan của người mua')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (9, N'HUYBAN', N'Hủy bán, lý do chủ quan của người bán')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (10, N'TRUNGLAP', N'Trùng lập')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (11, N'CHANDEXUAT', N'Cấm đề xuất')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (12, N'DANGBAOCAO', N'Đang báo cáo chờ xác thực vi phạm')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (13, N'DUYETKHONGVIPHAM', N'Duyệt báo cáo, không vi phạm')
INSERT [dbo].[tbl_Status] ([id], [status], [description]) VALUES (14, N'DUYETVIPHAM', N'Duyệt báo cáo vi phạm')
SET IDENTITY_INSERT [dbo].[tbl_Status] OFF
GO

SET IDENTITY_INSERT [dbo].[tbl_user] ON 

INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (1, N'haotn', N'123456', N'Nhựt Hào', N'haotndde@gmail.com', 1, N'', N'haotn.png', 1, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (3, N'admin', N'111111', N'gfhfghffghfg', N'adminqewd@gmail.com', 0, N'', N'admin.png', 1, CAST(N'2022-10-09T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (5, N'nhatquangfssddsfdsf', N'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', N'huỳnh nhật quang deptrai', N'huynhnhatqua@gmail.com', 1, N'0399846140', N'images\img\a722ed6a.jpg', 0, CAST(N'2022-10-28T12:52:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (6, N'quangquang', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'huỳnh nhật quang', N'quangquang@gmail.com', 1, N'0399846143', N'images\img\17b2deed.jpg', 0, CAST(N'2022-10-29T22:33:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (7, N'nhinhi', N'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', N'lê linh nhi', N'nhi@gmail.com', 0, N'0929192874', NULL, 1, CAST(N'2022-11-04T15:57:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (8, N'nhile', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'lê hoàng linh nhi daubui', N'nhile281@gmail.com', 0, N'0399846140', N'images\img\6e85f2ce.jpg', 0, CAST(N'2022-11-04T16:17:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (11, N'truongbui', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'nguyen truong', N'nguyentruong@gmail.com', 1, N'0921784933', N'images\img\ce86e88f.jpg', 1, CAST(N'2022-11-04T16:25:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (14, N'nmbnmm', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'bnmbnmb', N'dsfdff@gmail.com', 1, N'0967854323', N'images\img\557e2b11.jpg', 1, CAST(N'2022-11-04T16:32:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (15, N'ôppoop', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'ôppopoppop', N'pppopop@gmail.com', 1, N'0921784933', N'images\img\2d77cbee.jpg', 1, CAST(N'2022-11-04T23:40:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (16, N'nbmmn', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'nmnmmbmm', N'bmbnmnmm@gmail.com', 1, N'0399846140', N'images\img\145e8efc.jpg', 1, CAST(N'2022-11-04T23:42:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (17, N'bmbnffgg', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'fghgfjhjg', N'gjgghjhgj@gmail.com', 1, N'0399846140', N'images\img\7046dde1.jpg', 1, CAST(N'2022-11-04T23:43:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (19, N'nmnmn', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'nnmnmmn', N'nmnmm@gmail.com', 1, N'0921784933', N'images\img\d24d6a2f.jpg', 1, CAST(N'2022-11-07T20:47:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (20, N'kllkk', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'klkll', N'klklkl@gmail.com', 1, N'0399846140', N'images\img\6000ca9e.png', 1, CAST(N'2022-11-07T21:06:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (21, N'ccvcvvc', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'cvcvcvv', N'cccvcvv@gmail.com', 1, N'0921784933', N'images\img\75a4a310.jpg', 1, CAST(N'2022-11-07T21:07:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (22, N'jjkjkkj', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'jkjkjkjk', N'jkjkkj@gamil.com', 1, N'0921784933', N'images\img\ea7d959d.jpg', 1, CAST(N'2022-11-07T21:10:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (23, N'ffsdfdf', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'sdfdsfdsfsd', N'dfsffd@gmail.com', 1, N'0399846140', N'images\img\9c59058b.jpg', 1, CAST(N'2022-11-07T21:13:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (24, N'kjlkljkl', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'jkljljljkl', N'hgjgg@gmail.com', 1, N'0921784933', N'images\img\5209ce34.jpg', 1, CAST(N'2022-11-07T21:13:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (25, N'dfdgdg', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'fdgdfgfdg', N'dfgdffdg@gmail.com', 1, N'0399846140', N'images\img\39aa8a02.jpg', 1, CAST(N'2022-11-07T21:15:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (26, N'vbnvbnn', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'bvnvbnv', N'vnbnvb@gmail.com', 1, N'0399846140', N'images\img\b16c4e52.jpg', 1, CAST(N'2022-11-07T21:16:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (27, N'fgdgdfg', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'gfdgfg', N'dfgdfgfg@gmail.com', 1, N'0399846140', N'images\img\3f9075db.jpg', 1, CAST(N'2022-11-07T21:19:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (28, N'dgfghg', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'gfhgfhfh', N'hkkhjkjk@gmail.com', 1, N'0921784933', N'images\img\ebe96f0f.jpg', 1, CAST(N'2022-11-07T21:20:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (29, N'ljkjlkl', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'ghjgjfgj', N'gjfggjfg@gmail.com', 1, N'0921784933', N'images\img\b9cee79e.jpg', 1, CAST(N'2022-11-07T21:22:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (30, N',mnbvcx', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'jhgfgdfsds', N'jmhnvb@gmail.com', 1, N'0921784933', N'images\img\b8870b99.jpg', 1, CAST(N'2022-11-07T21:38:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (33, N'dsfdfdf', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'sdfdfsfsdf', N'sdfsdff@gmail.com', 1, N'0921784933', N'images\img\7cdafa57.jpg', 1, CAST(N'2022-11-07T21:42:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (34, N'lkjhgfds', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'sfdgfhg', N'dsghdfh@gmai.com', 0, N'0921784933', N'images\img\8b808d60.jpg', 1, CAST(N'2022-11-07T21:46:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (35, N'nbvcxzx', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'xczxvzvx', N'vncfsrht@gmail.com', 1, N'0921784933', N'images\img\b2267d7b.jpg', 1, CAST(N'2022-11-07T21:48:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (36, N'qưeeqwe', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'qưeqwerewrwer', N'ewrwerwr@gmail.com', 1, N'0921784933', N'images\img\53ab4b66.jpg', 1, CAST(N'2022-11-07T21:52:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (37, N'ửteytry', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'rtuytutyu', N'mcnfdge@gmail.com', 1, N'0921784933', N'images\img\39078c66.jpg', 1, CAST(N'2022-11-07T21:53:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (38, N'sdffsdff', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'dsfsdfdf', N'sdffsddf@gmail.com', 1, N'0399846140', N'images\img\fc3dbf00.jpg', 1, CAST(N'2022-11-07T22:08:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (39, N'áddsdgfdg', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'fdgfgjgh', N'dsfsdfsfsd@gmail.com', 1, N'0399846140', N'images\img\44b30056.jpg', 1, CAST(N'2022-11-07T22:09:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (41, N'shdkfjslhdsf', N'8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', N'dsfsdfhsbfksdf;l', N'fdsf@gmail.com', 1, N'0987654321', N'images\img\6b3728ab.jpg', 1, CAST(N'2022-11-07T22:16:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (43, N'fdgkkgfskdflgsgg', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'qewresgdhgr', N'hfjhfdf@gmail.com', 1, N'0399846140', N'images\img\a72b08b5.jpg', 1, CAST(N'2022-11-07T22:20:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (44, N'fhgjkkjkj', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'hgghgjjkkl/kk', N'gfhjhkjlk@gmail.com', 1, N'0399846140', N'images\img\911e242f.jpg', 1, CAST(N'2022-11-07T22:22:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (48, N'qửqq', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'qưewq', N'qeeqw@gmail.com', 1, N'0399846140', N'images\img\b8fd924d.jpg', 1, CAST(N'2022-11-09T13:58:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (49, N'sdfsdf', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'gfhgfhfgh', N'fhghgjhk@gmail.com', 1, N'0399846140', N'images\img\d3c14ddd.jpg', 1, CAST(N'2022-11-09T13:59:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (53, N'fdsfsd', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'sdfsdff', N'sdfdfsf@gmail.com', 1, N'0399846140', N'images\img\37bb3c41.jpg', 1, CAST(N'2022-11-09T14:35:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (54, N'mnbvcxz', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'zxcvbn', N'xcvb@gmail.com', 1, N'0399846140', N'images\img\1f908462.jpg', 1, CAST(N'2022-11-09T14:38:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (66, N'kljjkfggd', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'sffsdger', N'mhvjxzcj@gmail.com', 1, N'0399846140', N'images\img\d9f0ae2b.jpg', 1, CAST(N'2022-11-09T21:31:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (67, N'kjhgnbfvdcs', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'bvnmbvbcv', N'mnczxcxzc@gmail.com', 1, N'0399846140', N'images\img\f696e1a5.jpg', 1, CAST(N'2022-11-09T21:32:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (69, N'ljkjghjghj', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'ytuyerterw', N'dfgfdgdgg@gmail.com', 1, N'0399846140', N'images\img\d1404ff9.jpg', 1, CAST(N'2022-11-09T21:36:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (70, N'lkjkljkljiopiopiop', N'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', N'jljuiuyujujj', N'jyujuyuiyiu@gmail.com', 1, N'0399846156', N'images\img\485fef41.jpg', 1, CAST(N'2022-11-09T21:39:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (71, N'nvbnvbn', N'8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', N'bvnvbnbvnvbn', N'bnmbmvbnvncvbc@gmail.com', 1, N'0989271866', N'images\img\5ca8d944.jpg', 1, CAST(N'2022-11-09T21:41:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (72, N'iopuioyuio', N'8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', N'ipuyiuuou', N'iouioouityr@gmail.com', 1, N'0399846140', N'images\img\cdb1454e.jpg', 1, CAST(N'2022-11-09T21:44:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (73, N'jljklljkl', N'8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', N'iopippio8ou', N'khjkhjkhjkyuiy@gmail.com', 1, N'0921784933', N'images\img\de0e0eeb.jpg', 1, CAST(N'2022-11-09T21:45:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (74, N'fghdghgfh', N'8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', N'dghgfhfhghfh', N'ewrwerwetrete@gmail.com', 1, N'0927187238', NULL, 1, CAST(N'2022-11-09T21:47:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (75, N'hjghjh', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'ghjhgjhgjg', N'hgjghjgjh@gmail.com', 1, N'0399846140', NULL, 1, CAST(N'2022-11-09T21:48:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (76, N'hjhgjghjgh', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'hgjhgjhgj', N'hgjhgjghjghj@gmail.com', 1, N'0399846140', NULL, 1, CAST(N'2022-11-09T21:50:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (77, N'fsdfsdfsdf', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'sdfdsfsdfsdfdsf', N'fdsfsdf@gmail.com', 1, N'0399846140', NULL, 1, CAST(N'2022-11-09T21:53:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (78, N'sadad', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'ádsadasd', N'dfgfhgjghk@gmail.com', 1, N'0399846140', N'images\img\ba954605.jpg', 1, CAST(N'2022-11-09T21:55:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (80, N'pierpewuroyi', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'gkewrwerewrw', N'ququququququ@gmail.com', 1, N'0399846140', N'images\img\67659281.jpg', 1, CAST(N'2022-11-09T21:59:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (81, N'hgfhgfgfhjbv', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'bnmvbn', N'bvnbnewr@gmail.com', 1, N'0399846140', N'images\img\8670db85.jpg', 1, CAST(N'2022-11-09T22:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (82, N'hkfgfdhgsdsdg', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'dfgfdgdgdfgd', N'dsfsdmklhvcbb@gmail.com', 1, N'0399846140', N'images\img\4e29ac6f.jpg', 1, CAST(N'2022-11-09T22:05:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (85, N'fsdf', N'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855', N'fsdfsdf', N'dsfsdff', 1, N'54345', NULL, 1, CAST(N'2022-11-09T22:09:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (86, N'liuoutryerwr', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'ưererytytruyt', N'rtytyrt@gmail.com', 1, N'0399846140', N'images\img\76b2064d.jpg', 1, CAST(N'2022-11-09T22:09:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (87, N'popqwqwqw', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'lkjhg', N'mnbzxc@gmail.com', 1, N'0399846140', N'images\img\5f3824ad.jpg', 0, CAST(N'2022-11-09T23:17:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (89, N'ưddasdasd', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'sadasd', N'sdfdgfdg@gmail.com', 1, N'0399846140', N'images\img\a6a9beb5.jpg', 1, CAST(N'2022-11-09T23:47:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (93, N'oiyutyretwrqew', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'ửetrytuyiu', N'mnbvncxvc@gmail.com', 1, N'0399846140', N'images\img\2701c680.jpg', 1, CAST(N'2022-11-09T23:56:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (121, N'qưewqeqw', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'sdfdsfdf', N'huynhnhatquang4asdasdada11@gmail.com', 1, N'0399846140', N'images\img\b22eea49.jpg', 1, CAST(N'2022-11-10T21:22:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10123, N'dsfsdf', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'sdsdfdf', N'sdfsdfdf@gmail.com', 1, N'0399846140', N'images\img\ec634669.jpg', 0, CAST(N'2022-11-15T13:51:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10124, N'sdf', N'3ea87a56da3844b420ec2925ae922bc731ec16a4fc44dcbeafdad49b0e61d39c', N'hgfhf', N'gfhfghfgh@gmail.com', 1, N'0921784933', N'images\img\ec5ebe34.jpg', 0, CAST(N'2022-11-15T13:52:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10125, N'sdasd', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'dfgdfgfdgdf', N'dfgdffsds@gmail.com', 1, N'0921784933', N'images\img\7e69af0f.jpg', 0, CAST(N'2022-11-15T14:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10126, N'nhatqussdfsdfsdfang', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'pôppopooppop', N'dfgdfdfg@gmail.com', 0, N'', N'images\img\57272fba.jpg', 1, CAST(N'2022-11-15T15:40:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10127, N'hjghjghj', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'fhhjjgj', N'sdaasd@gmail.com', 1, N'0399846140', N'images\img\d3d4730b.jpg', 0, CAST(N'2022-11-15T20:49:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10128, N'sdffghjhgjhg', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'jfghfghfhgf', N'fghfghghf@gmail.com', 1, N'0399846140', N'images\img\d5ff85af.jpg', 0, CAST(N'2022-11-15T20:52:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10130, N'ljklkjljkl', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'dfgdfgdg', N'huynhnhatquangcxvcvxcv@gmail.com', 1, N'0399846140', N'images\img\7162130d.jpg', 0, CAST(N'2022-11-15T20:55:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10131, N'ljkljljhljk', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'tuyuyuyti', N'huynhnhatquasdfsdfsds1281@gmail.com', 1, N'0399846140', N'images\img\f50d9598.jpg', 1, CAST(N'2022-11-15T21:02:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10132, N'nbmnbvc', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'xzcvnbmn', N'huynhnhatqusddsdfsdf1@gmail.com', 1, N'0399846140', N'images\img\1621de62.jpg', 1, CAST(N'2022-11-15T21:03:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10133, N'hgsjkfhklfjlsdf', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'ffhfreyudykstu', N'huynhnhatquang41sdfdssdf1281@gmail.com', 1, N'0399846140', N'images\img\d7e14331.jpg', 1, CAST(N'2022-11-15T21:06:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10134, N'mbnmnmn', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'nbmbnmbn', N'sdfsdfsdg@gmail.com', 1, N'0921784933', N'images\img\55d84552.jpg', 0, CAST(N'2022-11-15T22:34:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10136, N'nhatquang', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'huỳnh nhật quang', N'huynhnhatquang2815645656@gmail.com', 1, N'0399846140', N'images\img\c55013a1.jpg', 0, CAST(N'2022-11-16T10:31:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10137, N'nhuttruong', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'nguyen truong', N'huynhnhatquang41128545561@gmail.com', 1, N'0399846140', N'images\img\84d35524.jpg', 0, CAST(N'2022-11-16T10:53:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10138, N'vmbnv', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'vbnvnbn', N'huynhnhatquang28dsadsd1@gmail.com', 1, N'0399846140', N'images\img\6aabe974.jpg', 0, CAST(N'2022-11-16T10:58:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10139, N'bnvnbvn', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'vbnvbnv', N'huynhnhatquang4asdasd11281@gmail.com', 1, N'0399846140', N'images\img\94fbd579.jpg', 0, CAST(N'2022-11-16T10:59:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10140, N'hgfhghjg', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'hjkhjkhjk', N'hjkhjkhjk@gmail.com', 1, N'0921784933', N'images\img\220c31ea.jpg', 0, CAST(N'2022-11-16T11:06:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10141, N'dsgfgjhjg', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'hjkjhgfsad', N'dfdhjhk@gmail.com', 1, N'0922817644', N'images\img\38764dc5.jpg', 0, CAST(N'2022-11-16T11:08:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10142, N'hgkjjklkjhgf', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'dsfghjk', N'huynhnhatquang41asdasd1@gmail.com', 1, N'0399846140', N'images\img\64ce4147.jpg', 0, CAST(N'2022-11-16T11:09:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10143, N'kjlhjkdgfgd', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'rẻoiiipuoipt', N'huynhnhatquang41dasdsad1281@gmail.com', 1, N'0399846140', N'images\img\644426e8.jpg', 0, CAST(N'2022-11-16T11:11:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10144, N'poiuwqei', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'oipirotkgm', N'huynhnhatquangsdfsdf281@gmail.com', 1, N'0399846140', N'images\img\a2424f1d.jpg', 0, CAST(N'2022-11-16T11:17:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10145, N'oiueperwer', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'ioeryowp', N'huynhnhatquangasdsada411@gmail.com', 1, N'0399846140', N'images\img\d43f198d.jpg', 0, CAST(N'2022-11-16T11:18:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10146, N'iopuerwepri', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'regjkjhk', N'huynhnhatquang41sdfsdfsdf1281@gmail.com', 1, N'0399846140', N'images\img\915473c9.jpg', 0, CAST(N'2022-11-16T11:28:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10147, N'jfgkdlfl;jhjk', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'ghkghk', N'huynhnhatquansdfsdfsdfg28asdasd1@gmail.com', 1, N'0399846140', N'images\img\588bfda9.jpg', 0, CAST(N'2022-11-16T11:34:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10148, N'sdfshghjgkjh', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'hfdgsc', N'huynhnhatqujhgjasdassaang411@gmail.com', 1, N'0399846140', N'images\img\2f09ebe3.jpg', 0, CAST(N'2022-11-16T11:36:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10149, N'lkjlhgkhjh', N'96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', N'vcbxvcsddhfjg', N'huynhnhatquaasdadsang411281@gmail.com', 0, N'0921784933', N'images\img\9d80b953.jpg', 0, CAST(N'2022-11-16T14:01:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (10151, N'pôppo', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'qưewqe', N'huynhnhatquang281@gmail.com', 0, N'0399846140', N'images\img\adf4f4d0.jpg', 0, CAST(N'2022-11-18T13:18:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_user] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_category] ON 

INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (1, N'Đồ gia dụng', N'tỷtyrtytryryty', 0, 1, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (3, N'Đồ công nghệ', N'rytyryty', 0, 1, 3, CAST(N'2022-11-11T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (15, N'sdfsdf', N'sdfsdf', 0, 10136, 3, CAST(N'2022-11-17T13:53:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (38, N'iii', N'iiii', 0, 1, 1, CAST(N'2022-11-17T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (39, N'hhhh', N'hhhh', 0, 1, 1, CAST(N'2022-11-17T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (41, N'sfdssdfsdfsf', N'popoppopopop', 0, 10136, 3, CAST(N'2022-11-23T09:47:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_category] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_card] ON 

INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (1, 1, 1, 1, 0, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (2, 3, 1, 1, 0, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (3, 1, 1, 1, 0, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (4, 1, 1, 1, 0, CAST(N'2022-10-27T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (5, 1, 1, 1, 0, CAST(N'2022-10-25T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (6, 1, 1, 1, 0, CAST(N'2022-10-27T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (7, 1, 1, 1, 0, CAST(N'2022-10-25T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (8, 7, 0, 1, 0, CAST(N'2022-11-10T23:16:00' AS SmallDateTime))
INSERT [dbo].[tbl_card] ([id], [id_user], [is_product], [id_status], [hidden], [post_at]) VALUES (9, 7, 0, 1, 0, CAST(N'2022-11-10T23:17:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_card] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_product] ON 

INSERT [dbo].[tbl_product] ([id], [id_card], [product], [banner], [available], [description], [price], [price_percent], [id_category]) VALUES (1, 1, N'Nồi cơm điện ', N'p1png', 1, N'Nồi cơm điện đã qua sử dụng', 400000, 2, 1)
INSERT [dbo].[tbl_product] ([id], [id_card], [product], [banner], [available], [description], [price], [price_percent], [id_category]) VALUES (3, 2, N'Bàn là hơi nước', N'p2.png', 1, N'Bàn là hơi nước', 20000, 5, 1)
INSERT [dbo].[tbl_product] ([id], [id_card], [product], [banner], [available], [description], [price], [price_percent], [id_category]) VALUES (5, 3, N'Robot hút bụi', N'p3.png', 1, N'Robot hút bụi thông minh', 300000, 8, 3)
SET IDENTITY_INSERT [dbo].[tbl_product] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_commentcard] ON 

INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (1, 1, 1, CAST(N'2022-10-26T00:00:00' AS SmallDateTime), N'Vui lòng đặt hàng thông qua website')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (2, 3, 1, CAST(N'2022-10-26T00:00:00' AS SmallDateTime), N'Kiểm tra lại quy tắc cộng đồng.')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (3, 7, 4, CAST(N'2022-11-09T16:05:00' AS SmallDateTime), N'I like it')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (4, 7, 4, CAST(N'2022-11-09T16:06:00' AS SmallDateTime), N'Some thing else ')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10003, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 1')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10004, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 2')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10005, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 3')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10006, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 4')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10007, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 5')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10008, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 6')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10009, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 7')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10010, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 8')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10011, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 9')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10012, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 9')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10013, 7, 4, CAST(N'2022-11-10T15:03:00' AS SmallDateTime), N'Comment 10')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10014, 7, 4, CAST(N'2022-11-10T15:05:00' AS SmallDateTime), N'Comment 11')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10015, 7, 4, CAST(N'2022-11-10T15:05:00' AS SmallDateTime), N'Comment 12')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10016, 7, 4, CAST(N'2022-11-10T15:06:00' AS SmallDateTime), N'Comment 13')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10017, 7, 4, CAST(N'2022-11-10T15:07:00' AS SmallDateTime), N'Comment 14')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10018, 7, 4, CAST(N'2022-11-10T16:19:00' AS SmallDateTime), N'New comment 15')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10019, 7, 4, CAST(N'2022-11-10T16:24:00' AS SmallDateTime), N'New comment 15')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10020, 7, 4, CAST(N'2022-11-10T16:25:00' AS SmallDateTime), N'Comment 16')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10021, 7, 4, CAST(N'2022-11-10T16:26:00' AS SmallDateTime), N'Comment 16')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10022, 7, 4, CAST(N'2022-11-10T16:28:00' AS SmallDateTime), N'Comment 17')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10023, 7, 4, CAST(N'2022-11-10T21:18:00' AS SmallDateTime), N'Add a new comment')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10024, 7, 4, CAST(N'2022-11-10T21:19:00' AS SmallDateTime), N'Test show comment')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10025, 7, 4, CAST(N'2022-11-10T23:02:00' AS SmallDateTime), N'Test new func insert')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10026, 7, 4, CAST(N'2022-11-10T23:05:00' AS SmallDateTime), N'Test new func')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10027, 7, 4, CAST(N'2022-11-10T23:07:00' AS SmallDateTime), N'Test new func')
INSERT [dbo].[tbl_commentcard] ([id], [id_user], [id_card], [comment_date], [content]) VALUES (10028, 7, 4, CAST(N'2022-11-10T23:07:00' AS SmallDateTime), N'Test new func')
SET IDENTITY_INSERT [dbo].[tbl_commentcard] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_repcomment] ON 

INSERT [dbo].[tbl_repcomment] ([id], [id_comment], [id_user], [rep_comment_date], [content]) VALUES (2, 3, 7, CAST(N'2022-11-14T21:31:00' AS SmallDateTime), N'Me too')
INSERT [dbo].[tbl_repcomment] ([id], [id_comment], [id_user], [rep_comment_date], [content]) VALUES (3, 3, 7, CAST(N'2022-11-14T21:33:00' AS SmallDateTime), N'Me too bro')
INSERT [dbo].[tbl_repcomment] ([id], [id_comment], [id_user], [rep_comment_date], [content]) VALUES (4, 3, 7, CAST(N'2022-11-14T21:34:00' AS SmallDateTime), N'M t b')
SET IDENTITY_INSERT [dbo].[tbl_repcomment] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_role] ON 

INSERT [dbo].[tbl_role] ([id], [role], [description]) VALUES (1, N'PARTNER', N'Đối tác (người dùng cơ bản, có thể mua và bán sản phẩm)')
INSERT [dbo].[tbl_role] ([id], [role], [description]) VALUES (2, N'ADMIN', N'Quản trị viên')
INSERT [dbo].[tbl_role] ([id], [role], [description]) VALUES (3, N'OWNER', N'Chủ sở hữu')
SET IDENTITY_INSERT [dbo].[tbl_role] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_authority] ON 

INSERT [dbo].[tbl_authority] ([id], [id_user], [id_role], [authorize_date]) VALUES (1, 1, 3, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_authority] ([id], [id_user], [id_role], [authorize_date]) VALUES (4, 3, 2, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_authority] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_action] ON 

INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (1, N'NAP', N'Nạp')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (2, N'MUADICHVU', N'Mua dịch vụ')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (3, N'NANGCAPTAIKHOAN', N'Nâng cấp tài khoản')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (4, N'TRAPHITRUNGBAY', N'Trả phí trưng bày')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (5, N'THEM', N'Thêm')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (6, N'SUA', N'Sửa')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (7, N'MUA', N'Mua')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (8, N'GIAHAN', N'Gia hạn')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (9, N'KHOITAO', N'Khởi tạo')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (10, N'TANGGIA', N'Tăng giá')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (11, N'TANGSOLUONG', N'Tăng số lượng')
INSERT [dbo].[tbl_action] ([id], [tbl_action], [description]) VALUES (12, N'TANGGIAVASOLUONG', N'Tăng giá và số lượng')
SET IDENTITY_INSERT [dbo].[tbl_action] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_bservice] ON 

INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (59, N'hfhhffgh', N'images\img\b0b136eb.jpg', N'ppppkjkjnjkn', 0)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (60, N'mbnmbn', N'images\img\97492b4c.jpg', N'vxcvcxvcxzczcxc', 0)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (61, N'sdasasdd', N'images\img\24a7af4b.jpg', N'dgdfgghfg', 1)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (62, N'xxcvxcvcx', N'images\img\18338e82.jpg', N'dgfhj', 1)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (63, N'opipipiop', N'images\img\f9a1cf6b.jpg', N'', 1)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (64, N'dssfdf', N'images\img\55118409.jpg', N'hgjghjgjksasdsa', 0)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (65, N'sdasdsd', N'images\img\7a6eb974.jpg', N'ggnhnvbnvbn', 0)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (66, N'sdfdff', N'images\img\78cddc8e.jpg', N';kjkhgffdsds', 0)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (68, N'sadaasds', N'images\img\1ceebe73.jpg', N'sdfsfsdfsdf', 0)
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description], [blocked]) VALUES (70, N'ertertert', N'images\img\3c5235b4.jpg', N'ooooooo', 0)
SET IDENTITY_INSERT [dbo].[tbl_bservice] OFF

GO
SET IDENTITY_INSERT [dbo].[tbl_bservice_history] ON 

INSERT [dbo].[tbl_bservice_history] ([id], [id_user], [id_bservice], [act_date], [use_days], [act_by], [id_action]) VALUES (4, 10137, 59, CAST(N'2022-11-23T00:00:00' AS SmallDateTime), 30, 10136, 1)
INSERT [dbo].[tbl_bservice_history] ([id], [id_user], [id_bservice], [act_date], [use_days], [act_by], [id_action]) VALUES (6, 10138, 64, CAST(N'2022-11-23T00:00:00' AS SmallDateTime), 30, 10136, 2)
INSERT [dbo].[tbl_bservice_history] ([id], [id_user], [id_bservice], [act_date], [use_days], [act_by], [id_action]) VALUES (7, 10138, 65, CAST(N'2022-11-23T00:00:00' AS SmallDateTime), 30, 10136, 3)
SET IDENTITY_INSERT [dbo].[tbl_bservice_history] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_displayfee] ON 

INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (1, 0, 50000, 0, 11111, N'Miễn phí trưng bày cho sản phẩm dưới 50000')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (2, 50000, 100000, 1500, 23, N'Phí trưng bày 3000 cho sản phẩm từ trên 5000 - 10000 hoặc 2%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (3, 100000, 150000, 3000, 3, N'Phí trưng bày 3000 hoặc 3%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (4, 150000, 200000, 4000, 4, N'Phí trưng bày 4000 hoặc 4%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (5, 200000, 300000, 5000, 5.5, N'Phí trưng bày 5000 hoặc 5.5%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (6, 300000, 400000, 7000, 7, N'Phí trưng bày 7000 hoặc 7%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (7, 400000, 500000, 10000, 9.5, N'Phí trưng bày 10000 hoặc 9.5%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (8, 500000, 600000, 12000, 11.2, N'Phí trưng bày 12000 hoặc 11.2%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (9, 600000, 800000, 14000, 12.5, N'Phí trưng bày 14000 hoặc 12.5%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (10, 800000, 1000000, 18000, 14, N'Phí trưng bày 18000 hoặc 14%')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (14, 4444444444, 56655555555, 323, 2323, N'ấdfasdf')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (15, 44444444444444448, 56655555555, 323, 2323, N'ấdfasdf')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (16, 44444444444444448, 5.6655555555555551E+21, 323, 2323, N'ấdfasdf')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (17, 10000000, 11000000, 32, 323, N'sdfadsf')
INSERT [dbo].[tbl_displayfee] ([id], [price_from], [price_to], [fixed_fee], [percent_fee], [description]) VALUES (18, 55555555, 555555555, 34, 34, N'fsdf')
SET IDENTITY_INSERT [dbo].[tbl_displayfee] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_problem] ON 

INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (1, N'LUADAO', N'Lừa đảo')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (2, N'KHIEUDAM', N'Khiêu dâm')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (3, N'BAOLUC', N'Bạo lực')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (4, N'QUAYROI', N'Quấy rối')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (5, N'TENAN', N'Tệ nạn')
SET IDENTITY_INSERT [dbo].[tbl_problem] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_cart] ON 

INSERT [dbo].[tbl_cart] ([id], [id_user], [id_product], [quantity], [cart_date]) VALUES (4, 1, 1, 5, CAST(N'2022-10-10T07:00:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_cart] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_lovecard] ON 

INSERT [dbo].[tbl_lovecard] ([id], [id_user], [id_card], [love_date]) VALUES (8, 7, 7, CAST(N'2022-11-08T16:04:00' AS SmallDateTime))
INSERT [dbo].[tbl_lovecard] ([id], [id_user], [id_card], [love_date]) VALUES (10099, 7, 4, CAST(N'2022-11-14T21:47:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_lovecard] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_register_active] ON 

INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (1, 1, CAST(N'2022-10-10T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (2, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (3, 10126, CAST(N'2022-11-15T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (4, 10127, CAST(N'2022-11-15T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (5, 10128, CAST(N'2022-11-15T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (6, 10130, CAST(N'2022-11-15T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (7, 10131, CAST(N'2022-11-15T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (8, 10132, CAST(N'2022-11-15T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (9, 10133, CAST(N'2022-11-15T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (12, 10136, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (13, 10137, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (14, 10138, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (15, 10139, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (16, 10140, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (17, 10141, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (18, 10142, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (19, 10143, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (20, 10144, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (21, 10145, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (22, 10146, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 0)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (23, 10147, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (24, 10148, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (25, 10149, CAST(N'2022-11-16T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (26, 10151, CAST(N'2022-11-18T00:00:00' AS SmallDateTime), 0)
SET IDENTITY_INSERT [dbo].[tbl_register_active] OFF
GO

GO
SET IDENTITY_INSERT [dbo].[tbl_bservice_price] ON 

INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (64, 5453, CAST(N'2022-11-21T17:32:00' AS SmallDateTime), 10136, 59, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (65, 44334543, CAST(N'2022-11-21T17:43:00' AS SmallDateTime), 10136, 60, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (66, 443345434323424, CAST(N'2022-11-21T17:44:00' AS SmallDateTime), 10136, 60, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (67, 2344234, CAST(N'2022-11-21T17:46:00' AS SmallDateTime), 10136, 61, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (68, 89765645, CAST(N'2022-11-21T17:46:00' AS SmallDateTime), 10136, 62, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (69, 5458778888, CAST(N'2022-11-22T13:24:00' AS SmallDateTime), 10136, 59, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (70, 46566, CAST(N'2022-11-22T13:38:00' AS SmallDateTime), 10136, 63, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (71, 346456768, CAST(N'2022-11-22T13:39:00' AS SmallDateTime), 10136, 64, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (72, 324324234, CAST(N'2022-11-22T14:29:00' AS SmallDateTime), 10136, 65, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (73, 5477890, CAST(N'2022-11-22T14:38:00' AS SmallDateTime), 10136, 66, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (74, 34535345, CAST(N'2022-11-22T17:19:00' AS SmallDateTime), 10136, 68, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (75, 34543534, CAST(N'2022-11-23T09:37:00' AS SmallDateTime), 10136, 70, N'Khởi tạo giá dịch vụ')
SET IDENTITY_INSERT [dbo].[tbl_bservice_price] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_address] ON 

INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (1, 1, N'Nhựt Hào', N'0973648273', N'Cần Thơ')
INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (2, 1, N'Lê Nam', N'0398092417', N'Hậu Giang')
INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (3, 1, N'Trần Thanh Hưng', N'0792094758', N'An Giang')
INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (4, 3, N'Nguyễn Thanh Hà', N'0398209485', N'Sóc Trăng')
SET IDENTITY_INSERT [dbo].[tbl_address] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_bservice_subbanner] ON 

INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (1, 59, N's1.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (2, 60, N's2.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (3, 61, N's3.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (4, 62, N's4.png')
SET IDENTITY_INSERT [dbo].[tbl_bservice_subbanner] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_post] ON 

INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (1, 1, N'Nồi cơm điện 99%', N'Nồi cơm điện đã qua sử dụng một lần, độ mới còn ~99%', N'b1.png')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (2, 2, N'Bàn là hơi nước', N'Bàn là hơi nước đã qua sử dụng, độ mới 95%', N'b2.png')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (3, 3, N'Robot hút bụi', N'Robot hút bụi đã qua sử dụng, tình trạng hoạt động tốt, độ mới 97%', N'b3.png')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (4, 4, N'Máy Massage cổ còn nguyên box', N'Máy massage cổ mới mua chưa sử dụng.', N'b4')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (5, 5, N'Giày Nike mới 99%', N'Giày vừa mua đi đúng một lần, không vừa nên pass lại.', N'b5')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (6, 6, N'Áo thun puma ', N'Áo thun puma ', N'b6')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (7, 7, N'Quần jean ', N'Quần mới 98%', N'b7')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (8, 8, N'adfasdfa', N'dasdfasdfasdfasd', N'test.png')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (9, 9, N'adfasdfa', N'dasdfasdfasdfasd', N'test.png')
SET IDENTITY_INSERT [dbo].[tbl_post] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_card_subbanner] ON 

INSERT [dbo].[tbl_card_subbanner] ([id], [id_card], [subbanner]) VALUES (1, 1, N'c1.png')
INSERT [dbo].[tbl_card_subbanner] ([id], [id_card], [subbanner]) VALUES (2, 2, N'c2.png')
INSERT [dbo].[tbl_card_subbanner] ([id], [id_card], [subbanner]) VALUES (3, 3, N'c3.png')
SET IDENTITY_INSERT [dbo].[tbl_card_subbanner] OFF
GO

SET IDENTITY_INSERT [dbo].[tbl_order] ON 

INSERT [dbo].[tbl_order] ([id], [id_buyer], [fullname], [phonenum], [address], [add_date], [note_buyer], [note_saler], [id_status], [id_saler]) VALUES (1, 10136, N'shop đồ đẹp', N'0922910977', N'cần thơ', CAST(N'2022-10-28T00:00:00' AS SmallDateTime), N'abc', N'bán đồ nè', 7, 3)
INSERT [dbo].[tbl_order] ([id], [id_buyer], [fullname], [phonenum], [address], [add_date], [note_buyer], [note_saler], [id_status], [id_saler]) VALUES (9, 10136, N'shop đồ mới', N'0911928377', N'cần thơ', CAST(N'2022-10-29T00:00:00' AS SmallDateTime), N'yfj', N'bán đồ đây', 8, 5)
INSERT [dbo].[tbl_order] ([id], [id_buyer], [fullname], [phonenum], [address], [add_date], [note_buyer], [note_saler], [id_status], [id_saler]) VALUES (11, 10136, N'shop abc', N'0399819203', N'cần thơ', CAST(N'2022-10-30T00:00:00' AS SmallDateTime), N'nnn', N'bán đồ', 6, 3)
INSERT [dbo].[tbl_order] ([id], [id_buyer], [fullname], [phonenum], [address], [add_date], [note_buyer], [note_saler], [id_status], [id_saler]) VALUES (13, 10136, N'shop second', N'0916289284', N'cần thơ', CAST(N'2022-10-30T00:00:00' AS SmallDateTime), N'ppp', N'bán đồ cũ', 8, 1)
INSERT [dbo].[tbl_order] ([id], [id_buyer], [fullname], [phonenum], [address], [add_date], [note_buyer], [note_saler], [id_status], [id_saler]) VALUES (14, 1, N'Lê Nam', N'0398092417', N'Hậu Giang', CAST(N'2022-11-15T15:55:00' AS SmallDateTime), N'quang đẹp trai', NULL, 7, 1)
INSERT [dbo].[tbl_order] ([id], [id_buyer], [fullname], [phonenum], [address], [add_date], [note_buyer], [note_saler], [id_status], [id_saler]) VALUES (15, 10138, N'tessssstts', N'0911784933', N'dsfsffd', CAST(N'2022-11-23T00:00:00' AS SmallDateTime), N'teststststs', N'testst', 7, 3)
SET IDENTITY_INSERT [dbo].[tbl_order] OFF


GO
SET IDENTITY_INSERT [dbo].[tbl_orderdetail] ON 

INSERT [dbo].[tbl_orderdetail] ([id], [id_order], [id_product], [quantity], [price]) VALUES (1, 1, 1, 3, 200000)
INSERT [dbo].[tbl_orderdetail] ([id], [id_order], [id_product], [quantity], [price]) VALUES (4, 9, 3, 2, 100000)
INSERT [dbo].[tbl_orderdetail] ([id], [id_order], [id_product], [quantity], [price]) VALUES (5, 11, 5, 1, 200000)
INSERT [dbo].[tbl_orderdetail] ([id], [id_order], [id_product], [quantity], [price]) VALUES (6, 11, 1, 2, 300000)
INSERT [dbo].[tbl_orderdetail] ([id], [id_order], [id_product], [quantity], [price]) VALUES (7, 13, 5, 4, 100000)
INSERT [dbo].[tbl_orderdetail] ([id], [id_order], [id_product], [quantity], [price]) VALUES (8, 14, 1, 5, 400000)
INSERT [dbo].[tbl_orderdetail] ([id], [id_order], [id_product], [quantity], [price]) VALUES (9, 15, 5, 10, 200000)
SET IDENTITY_INSERT [dbo].[tbl_orderdetail] OFF















-- pro thống kê
GO
create proc usp_tkbanhang
	@idsaler bigint, @batdau smalldatetime, @ketthuc smalldatetime
	as
		begin
			-- xét 
			if @batdau = '' or @batdau is null
				begin
					set @batdau = GETDATE()-(365*1)
				end
			if @ketthuc = '' or @ketthuc is null
				begin
					set @ketthuc = GETDATE()
				end
			--khaibao
			declare @myTable table
			(osum int, o7 int, p1 int, rvn float, ad float, fee float, prf float, p0 int
			,usum int, u2 int, u1 int, flsum int, fl1 int)

			declare @tempTable1 table
			(id_bsh bigint, id_bsp bigint)
			--
			declare @osum int, @o7 int, @p1 int, @rvn float, @ad float, @fee float, @prf float, @p0 int
			,@usum int, @u2 int, @u1 int, @flsum int, @fl1 int
			--ganbien
			select @osum = count(*) from tbl_order o
				where o.id_saler = @idsaler and o.add_date between @batdau and @ketthuc
			select @o7 = count(*) from tbl_order o
				where o.id_saler = @idsaler and o.add_date between @batdau and @ketthuc and o.id_status = 7
			select @p1 = sum(od.quantity), @rvn = sum(od.quantity * od.price) from tbl_orderdetail od
				join tbl_order o on o.id = od.id_order
				where o.id_saler = @idsaler and o.add_date between @batdau and @ketthuc and o.id_status = 7
			select @p0 = sum(p.available) from tbl_product p
				join tbl_card ca on ca.id = p.id_card
				where ca.id_user = @idsaler
			select @usum = count(distinct o.id_buyer) from tbl_order o
				where o.id_saler = @idsaler and o.add_date between @batdau and @ketthuc
			select @u2 = count(distinct o.id_buyer) from tbl_order o
				where o.id_saler = @idsaler and o.add_date between @batdau and @ketthuc and o.id_buyer in (
					select o.id_buyer from tbl_order o where o.id_saler = @idsaler and o.add_date < @batdau)
			set @u1 = @usum - @u2
			select @flsum = count(distinct f.id_user_follow) from tbl_follow_shop f
				where f.id_shop = @idsaler
			select @flsum = count(distinct f.id_user_follow) from tbl_follow_shop f
				where f.id_shop = @idsaler and f.follow_date between @batdau and @ketthuc
			--phí ad
			insert into @tempTable1
				select  bsh.id, max(bsp1.id_bsp1) as [bsp1max] from tbl_bservice_history bsh
					left outer join (
						select bsp1.id_bservice, max(bsp1.id) as id_bsp1, bsp1.price, max(bsp1.price_date) as date_bsp1 from tbl_bservice_price bsp1 
						where bsp1.price_date <= @ketthuc
						group by bsp1.id_bservice, bsp1.price 
					) bsp1 on bsp1.id_bservice = bsh.id_bservice
				where bsh.id_user = @idsaler and bsh.act_date >= bsp1.date_bsp1
				group by bsh.id
			select @ad = iif(sum(bsp.price) is null, 0, sum(bsp.price)) from @tempTable1 temp
			left outer join tbl_bservice_price bsp on bsp.id = temp.id_bsp
			print 'demo: ' + cast(@ad as nvarchar(max))
			--phí trưng bày
			-- set @fee = 10
			--select @fee = sum(bch.quantity)*1000 from tbl_bcoin_history bch
			--where bch.id_user = @idsaler and bch.coin_date between @batdau and @ketthuc and bch.id_action = 4
			select @fee = iif(sum(bch.quantity) is null, 0, sum(bch.quantity)) from tbl_bcoin_history bch
			where bch.id_user = @idsaler and bch.coin_date between @batdau and @ketthuc and bch.id_action = 4
			--lợi nhuận
			set @prf = @rvn - @ad - @fee
			--taobang
			insert into @myTable values 
			(@osum, @o7, @p1, @rvn, @ad, @fee, @prf
			, @p0, @usum, @u2, @u1, @flsum, @fl1
			)
			--
			select iif(tb.osum is null, 0, tb.osum) as N'tổng đơn bán', iif(tb.o7 is null, 0, tb.o7) as N'đơn thành công'
			, iif(tb.p1 is null, 0, tb.p1) as N'hàng bán được', iif(tb.rvn is null, 0, tb.rvn) as N'doanhthu'
			, iif(tb.ad is null, 0, tb.ad) as N'phí quảng cáo', iif(tb.fee is null, 0, tb.fee) as N'phí trưng bày'
			, iif(tb.prf is null, 0, tb.prf) as N'lợi nhuận'
			, iif(tb.p0 is null, 0, tb.p0) as N'hàng tồn', iif(tb.usum is null, 0, tb.usum) as N'tổng khách hàng'
			, iif(tb.u2 is null, 0, tb.u2) as N'khách hàng quay lại', iif(tb.u1 is null, 0, tb.u1) as N'khách hàng mới'
			, iif(tb.flsum is null, 0, tb.flsum) as N'tổng theo dõi', iif(tb.fl1 is null, 0, tb.fl1) as N'theo dõi mới' 
			
			from @myTable tb
		end









-- trigger xóa product and post

--trigger xóa postcard
GO
create trigger trg_DeletePost on tbl_post
instead of delete
as
	begin
		declare @id_card bigint
		select @id_card = id_card from deleted
		--1 subbaner
		delete tbl_card_subbanner where id_card = @id_card
		--2 comentcard > repcmt
		declare @id_cmts table(id_cmt bigint)
		insert into @id_cmts
		select id from tbl_commentcard where id_card = @id_card
		--
		delete tbl_repcomment where id_comment in (select id_cmt from @id_cmts)
		delete tbl_commentcard where id_card = @id_card
		--3 lovecard
		delete tbl_lovecard where id_card = @id_card
		--4 sharecard
		delete tbl_sharecard where id_card = @id_card
		--5 hidecard
		delete tbl_hidecard where id_card = @id_card
		--6 fail history
		delete tbl_fail_history where id_card = @id_card
		--7 report card
		delete tbl_reportcard where id_card = @id_card
		--8 xóa post
		delete tbl_post where id_card = @id_card
		--9 xóa card
		delete tbl_card where id = @id_card
		--
		print N'id postcard '+ cast(@id_card as nvarchar(max));
	end
	go

--trigger xóa productcard
create trigger trg_DeleteProduct on tbl_product
instead of delete
as
	begin
		declare @id_card bigint
		select @id_card = id_card from deleted
		--1 subbaner
		delete tbl_card_subbanner where id_card = @id_card
		--2 comentcard > repcmt
		declare @id_cmts table(id_cmt bigint)
		insert into @id_cmts
		select id from tbl_commentcard where id_card = @id_card
		--
		delete tbl_repcomment where id_comment in (select id_cmt from @id_cmts)
		delete tbl_commentcard where id_card = @id_card
		--3 lovecard
		delete tbl_lovecard where id_card = @id_card
		--4 sharecard
		delete tbl_sharecard where id_card = @id_card
		--5 hidecard
		delete tbl_hidecard where id_card = @id_card
		--6 fail history
		delete tbl_fail_history where id_card = @id_card
		--7 report card
		delete tbl_reportcard where id_card = @id_card
		--7.1 order > order detail
		declare @id_product bigint
		select @id_product = id from tbl_product where id_card = @id_card
		--
		delete tbl_orderdetail where id_product = @id_product
		--8 xóa product
		delete tbl_product where id_card = @id_card
		--9 xóa card
		delete tbl_card where id = @id_card
		--
		print N'id product '+ cast(@id_card as nvarchar(max));
	end


GO

create proc usp_shopinfo
@iduser bigint
as
	begin
		declare @sumfs bigint, @sumav bigint, @sumqt bigint

		select @sumfs = COUNT(fs.id) from tbl_follow_shop fs
		where fs.id_shop = @iduser
		
		select @sumav = count(p.id_card) from tbl_product p
		where p.id_card in (
			select cad.id from tbl_card cad where cad.id_user = @iduser and cad.is_product = 1 and cad.hidden = 0)

		select @sumqt = count(distinct od.id_product) from tbl_orderdetail od
		where od.id_order in (
			select o.id from tbl_order o where o.id_saler = @iduser and o.id_status = 7)

		declare @mytable table(sumfs bigint, sumav bigint, sumqt bigint)
		insert into @mytable values (@sumfs, @sumav, @sumqt)
		select iif(my.sumfs is null, 0, my.sumfs) as tongfollow, iif(my.sumav is null, 0, my.sumav) as spdangban, iif(my.sumqt is null, 0, my.sumqt) as spdaban from @mytable my

	end