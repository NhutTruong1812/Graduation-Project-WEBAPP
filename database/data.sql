USE [Biglobby]
GO
SET IDENTITY_INSERT [dbo].[tbl_Status] ON 

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
SET IDENTITY_INSERT [dbo].[tbl_Status] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_user] ON 

INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (1, N'haotn', N'123456', N'Nhựt Hào', N'haotndde@gmail.com', 1, N'0938475637', N'haotn.png', 0, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (3, N'admin', N'111111', N'Quản trị viên', N'adminqewd@gmail.com', 1, N'0389847550', N'admin.png', 0, CAST(N'2022-10-09T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [gender], [phonenum], [avatar], [blocked], [last_login]) VALUES (7, N'haotndev', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'Nhut Hao', N'haotndev@gmail.com', 1, N'0963254121', NULL, 0, CAST(N'2022-11-08T15:44:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_user] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_category] ON 

INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (1, N'Đồ gia dụng', N'Đồ gia dụng ', 0, 1, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (2, N'Thời trang ', N'Thời trang', 0, 1, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (3, N'Đồ công nghệ', N'Đồ công nghệ', 0, 1, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
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

INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description]) VALUES (2, N'Tăng hạng gợi ý tìm kiếm', N's1.png', N'Tăng điểm gợi ý tìm kiếm shop bán hàng cùng loại')
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description]) VALUES (3, N'Tăng tương tác bài viết', N's2.png', N'Tăng lượt love bài viết')
INSERT [dbo].[tbl_bservice] ([id], [bservice], [banner], [description]) VALUES (4, N'Tăng bình luận', N's3.png', N'Tăng bình luận cho bài viết')
SET IDENTITY_INSERT [dbo].[tbl_bservice] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_bservice_history] ON 

INSERT [dbo].[tbl_bservice_history] ([id], [id_user], [id_bservice], [act_date], [use_days], [act_by], [id_action]) VALUES (1, 3, 2, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 30, 1, 2)
INSERT [dbo].[tbl_bservice_history] ([id], [id_user], [id_bservice], [act_date], [use_days], [act_by], [id_action]) VALUES (2, 3, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 30, 1, 2)
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
SET IDENTITY_INSERT [dbo].[tbl_register_active] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_bservice_price] ON 

INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (3, 2000000, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 1, 2, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (4, 2200000, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 1, 3, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (5, 2500000, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 3, 4, N'Khởi tạo giá dịch vụ')
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

INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (1, 2, N's1.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (2, 2, N's2.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (3, 3, N's3.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (4, 4, N's4.png')
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
