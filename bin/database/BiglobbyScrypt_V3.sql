USE [master]
GO
/****** Object:  Database [Biglobby]    Script Date: 10/12/2022 10:47:18 AM ******/
CREATE DATABASE [Biglobby]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Biglobby', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Biglobby.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Biglobby_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Biglobby_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Biglobby] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Biglobby].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Biglobby] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Biglobby] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Biglobby] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Biglobby] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Biglobby] SET ARITHABORT OFF 
GO
ALTER DATABASE [Biglobby] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Biglobby] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Biglobby] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Biglobby] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Biglobby] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Biglobby] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Biglobby] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Biglobby] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Biglobby] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Biglobby] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Biglobby] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Biglobby] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Biglobby] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Biglobby] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Biglobby] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Biglobby] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Biglobby] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Biglobby] SET RECOVERY FULL 
GO
ALTER DATABASE [Biglobby] SET  MULTI_USER 
GO
ALTER DATABASE [Biglobby] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Biglobby] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Biglobby] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Biglobby] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Biglobby] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Biglobby] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Biglobby', N'ON'
GO
ALTER DATABASE [Biglobby] SET QUERY_STORE = OFF
GO
USE [Biglobby]
GO
/****** Object:  Table [dbo].[tbl_action]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_action](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[tbl_action] [nvarchar](100) NOT NULL,
	[description] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_address]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_address](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[fullname] [nvarchar](50) NOT NULL,
	[phonenum] [nvarchar](10) NOT NULL,
	[address] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_authority]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_authority](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_role] [bigint] NOT NULL,
	[authorize_date] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_bcoin_history]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_bcoin_history](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[quantity] [int] NOT NULL,
	[id_action] [bigint] NOT NULL,
	[note] [nvarchar](500) NOT NULL,
	[coin_date] [smalldatetime] NOT NULL,
	[act_by] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_bservice]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_bservice](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[bservice] [nvarchar](100) NOT NULL,
	[banner] [nvarchar](100) NOT NULL,
	[description] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_bservice_history]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_bservice_history](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_bservice] [bigint] NOT NULL,
	[act_date] [smalldatetime] NOT NULL,
	[use_days] [int] NOT NULL,
	[act_by] [bigint] NOT NULL,
	[id_action] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_bservice_price]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_bservice_price](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[price] [float] NOT NULL,
	[price_date] [smalldatetime] NOT NULL,
	[price_by] [bigint] NOT NULL,
	[id_bservice] [bigint] NOT NULL,
	[note] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_bservice_subbanner]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_bservice_subbanner](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_bservice] [bigint] NOT NULL,
	[subbanner] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_card]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_card](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[is_product] [bit] NOT NULL,
	[id_status] [bigint] NOT NULL,
	[hidden] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_card_subbanner]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_card_subbanner](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_card] [bigint] NOT NULL,
	[subbanner] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_cart]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_cart](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_product] [bigint] NOT NULL,
	[quantity] [int] NOT NULL,
	[cart_date] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_category]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_category](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[category] [nvarchar](100) NOT NULL,
	[description] [nvarchar](500) NOT NULL,
	[blocked] [bit] NOT NULL,
	[add_by] [bigint] NOT NULL,
	[id_status] [bigint] NOT NULL,
	[add_date] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_commentcard]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_commentcard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_card] [bigint] NOT NULL,
	[comment_date] [smalldatetime] NOT NULL,
	[content] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_displayfee]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_displayfee](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[price_from] [float] NOT NULL,
	[price_to] [float] NOT NULL,
	[fixed_fee] [float] NOT NULL,
	[percent_fee] [float] NOT NULL,
	[description] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_displayfee_history]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_displayfee_history](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[price_from] [float] NOT NULL,
	[price_to] [float] NOT NULL,
	[fixed_fee] [float] NOT NULL,
	[percent_fee] [float] NOT NULL,
	[act_date] [smalldatetime] NOT NULL,
	[act_by] [bigint] NOT NULL,
	[id_displayfee] [bigint] NOT NULL,
	[id_action] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_fail_history]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_fail_history](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_card] [bigint] NOT NULL,
	[fail_reason] [nvarchar](500) NOT NULL,
	[fail_date] [smalldatetime] NOT NULL,
	[fail_by] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_follow_shop]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_follow_shop](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_userFollow] [bigint] NOT NULL,
	[id_shop] [bigint] NOT NULL,
	[followDate] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_hidecard]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_hidecard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_card] [bigint] NOT NULL,
	[hide_date] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_lovecard]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_lovecard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_card] [bigint] NOT NULL,
	[love_date] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_my_bcoin]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_my_bcoin](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[coinnum] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_mybservice]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_mybservice](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_bservice] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_myshop]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_myshop](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[intro] [nvarchar](100) NOT NULL,
	[location] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_news]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_news](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_sent] [bigint] NOT NULL,
	[id_got] [bigint] NOT NULL,
	[content] [nvarchar](500) NOT NULL,
	[news_date] [smalldatetime] NOT NULL,
	[hidden] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_order]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_order](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_buyer] [bigint] NOT NULL,
	[fullname] [nvarchar](100) NOT NULL,
	[phonenum] [nvarchar](10) NOT NULL,
	[address] [nvarchar](500) NOT NULL,
	[add_date] [smalldatetime] NOT NULL,
	[note_buyer] [nvarchar](500) NULL,
	[note_saler] [nvarchar](500) NULL,
	[id_status] [bigint] NOT NULL,
	[id_saler] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_orderdetail]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_orderdetail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_order] [bigint] NOT NULL,
	[id_product] [bigint] NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_post]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_post](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_card] [bigint] NOT NULL,
	[title] [nvarchar](100) NOT NULL,
	[content] [nvarchar](500) NOT NULL,
	[banner] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_problem]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_problem](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[problem] [nvarchar](100) NOT NULL,
	[description] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_product]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_product](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_card] [bigint] NOT NULL,
	[product] [nvarchar](100) NOT NULL,
	[banner] [nvarchar](100) NOT NULL,
	[available] [int] NOT NULL,
	[description] [nvarchar](500) NULL,
	[price] [float] NOT NULL,
	[price_percent] [float] NOT NULL,
	[id_category] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_product_history]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_product_history](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_product] [bigint] NOT NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[act_date] [smalldatetime] NOT NULL,
	[act_by] [bigint] NOT NULL,
	[id_action] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_register_active]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_register_active](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[register_date] [smalldatetime] NOT NULL,
	[actived] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_repcomment]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_repcomment](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_comment] [bigint] NOT NULL,
	[id_user] [bigint] NOT NULL,
	[rep_comment_date] [smalldatetime] NOT NULL,
	[content] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_reportcard]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_reportcard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_card] [bigint] NOT NULL,
	[report_date] [smalldatetime] NOT NULL,
	[id_problem] [bigint] NOT NULL,
	[note] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_role]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_role](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[role] [nvarchar](100) NOT NULL,
	[description] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_sharecard]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_sharecard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_user] [bigint] NOT NULL,
	[id_card] [bigint] NOT NULL,
	[share_date] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_status]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_status](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[status] [nvarchar](100) NOT NULL,
	[description] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_user]    Script Date: 10/12/2022 10:47:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_user](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](50) NOT NULL,
	[fullname] [nvarchar](50) NOT NULL,
	[email] [nvarchar](50) NOT NULL,
	[phonenum] [nvarchar](10) NOT NULL,
	[avatar] [nvarchar](100) NULL,
	[blocked] [bit] NOT NULL,
	[last_login] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
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
SET IDENTITY_INSERT [dbo].[tbl_address] ON 

INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (1, 1, N'Nhựt Hào', N'0973648273', N'Cần Thơ')
INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (2, 1, N'Lê Nam', N'0398092417', N'Hậu Giang')
INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (3, 1, N'Trần Thanh Hưng', N'0792094758', N'An Giang')
INSERT [dbo].[tbl_address] ([id], [id_user], [fullname], [phonenum], [address]) VALUES (4, 3, N'Nguyễn Thanh Hà', N'0398209485', N'Sóc Trăng')
SET IDENTITY_INSERT [dbo].[tbl_address] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_authority] ON 

INSERT [dbo].[tbl_authority] ([id], [id_user], [id_role], [authorize_date]) VALUES (1, 1, 3, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_authority] ([id], [id_user], [id_role], [authorize_date]) VALUES (4, 3, 2, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_authority] OFF
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
SET IDENTITY_INSERT [dbo].[tbl_bservice_price] ON 

INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (3, 2000000, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 1, 2, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (4, 2200000, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 1, 3, N'Khởi tạo giá dịch vụ')
INSERT [dbo].[tbl_bservice_price] ([id], [price], [price_date], [price_by], [id_bservice], [note]) VALUES (5, 2500000, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 3, 4, N'Khởi tạo giá dịch vụ')
SET IDENTITY_INSERT [dbo].[tbl_bservice_price] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_bservice_subbanner] ON 

INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (1, 2, N's1.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (2, 2, N's2.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (3, 3, N's3.png')
INSERT [dbo].[tbl_bservice_subbanner] ([id], [id_bservice], [subbanner]) VALUES (4, 4, N's4.png')
SET IDENTITY_INSERT [dbo].[tbl_bservice_subbanner] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_card] ON 

INSERT [dbo].[tbl_card] ([id], [is_product], [id_status], [hidden]) VALUES (1, 1, 1, 0)
INSERT [dbo].[tbl_card] ([id], [is_product], [id_status], [hidden]) VALUES (2, 1, 1, 0)
INSERT [dbo].[tbl_card] ([id], [is_product], [id_status], [hidden]) VALUES (3, 1, 1, 0)
SET IDENTITY_INSERT [dbo].[tbl_card] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_card_subbanner] ON 

INSERT [dbo].[tbl_card_subbanner] ([id], [id_card], [subbanner]) VALUES (1, 1, N'c1.png')
INSERT [dbo].[tbl_card_subbanner] ([id], [id_card], [subbanner]) VALUES (2, 2, N'c2.png')
INSERT [dbo].[tbl_card_subbanner] ([id], [id_card], [subbanner]) VALUES (3, 3, N'c3.png')
SET IDENTITY_INSERT [dbo].[tbl_card_subbanner] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_category] ON 

INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (1, N'Đồ gia dụng', N'Đồ gia dụng ', 0, 1, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (2, N'Thời trang ', N'Thời trang', 0, 1, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_category] ([id], [category], [description], [blocked], [add_by], [id_status], [add_date]) VALUES (3, N'Đồ công nghệ', N'Đồ công nghệ', 0, 1, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_category] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_post] ON 

INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (1, 1, N'Nồi cơm điện 99%', N'Nồi cơm điện đã qua sử dụng một lần, độ mới còn ~99%', N'b1.png')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (2, 2, N'Bàn là hơi nước', N'Bàn là hơi nước đã qua sử dụng, độ mới 95%', N'b2.png')
INSERT [dbo].[tbl_post] ([id], [id_card], [title], [content], [banner]) VALUES (3, 3, N'Robot hút bụi', N'Robot hút bụi đã qua sử dụng, tình trạng hoạt động tốt, độ mới 97%', N'b3.png')
SET IDENTITY_INSERT [dbo].[tbl_post] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_problem] ON 

INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (1, N'LUADAO', N'Lừa đảo')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (2, N'KHIEUDAM', N'Khiêu dâm')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (3, N'BAOLUC', N'Bạo lực')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (4, N'QUAYROI', N'Quấy rối')
INSERT [dbo].[tbl_problem] ([id], [problem], [description]) VALUES (5, N'TENAN', N'Tệ nạn')
SET IDENTITY_INSERT [dbo].[tbl_problem] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_product] ON 

INSERT [dbo].[tbl_product] ([id], [id_card], [product], [banner], [available], [description], [price], [price_percent], [id_category]) VALUES (1, 1, N'Nồi cơm điện ', N'p1png', 1, N'Nồi cơm điện đã qua sử dụng', 400000, 2, 1)
INSERT [dbo].[tbl_product] ([id], [id_card], [product], [banner], [available], [description], [price], [price_percent], [id_category]) VALUES (3, 2, N'Bàn là hơi nước', N'p2.png', 1, N'Bàn là hơi nước', 20000, 5, 1)
INSERT [dbo].[tbl_product] ([id], [id_card], [product], [banner], [available], [description], [price], [price_percent], [id_category]) VALUES (5, 3, N'Robot hút bụi', N'p3.png', 1, N'Robot hút bụi thông minh', 300000, 8, 3)
SET IDENTITY_INSERT [dbo].[tbl_product] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_register_active] ON 

INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (1, 1, CAST(N'2022-10-10T00:00:00' AS SmallDateTime), 1)
INSERT [dbo].[tbl_register_active] ([id], [id_user], [register_date], [actived]) VALUES (2, 3, CAST(N'2022-10-11T00:00:00' AS SmallDateTime), 1)
SET IDENTITY_INSERT [dbo].[tbl_register_active] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_role] ON 

INSERT [dbo].[tbl_role] ([id], [role], [description]) VALUES (1, N'PARTNER', N'Đối tác (người dùng cơ bản, có thể mua và bán sản phẩm)')
INSERT [dbo].[tbl_role] ([id], [role], [description]) VALUES (2, N'ADMIN', N'Quản trị viên')
INSERT [dbo].[tbl_role] ([id], [role], [description]) VALUES (3, N'OWNER', N'Chủ sở hữu')
SET IDENTITY_INSERT [dbo].[tbl_role] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_status] ON 

INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (1, N'DANGDUYET', N'Đang duyệt')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (2, N'VIPHAM', N'Vi phạm')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (3, N'SANSANG', N'Sẵn sàng')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (4, N'HETHANG', N'Hết hàng')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (5, N'DANGXACNHAN', N'Đang xác nhận, khi người mua đã đặt hàng')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (6, N'DONGYGIAODICH', N'Đồng ý giao dịch, sau khi trao đổi qua tin nhắn giữa người bán và người mua')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (7, N'THANHCONG', N'Thành công, đơn hàng đã được người mua và người bán xác nhận hoàn tất')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (8, N'HUYMUA', N'Hùy mua, lý do chủ quan của người mua')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (9, N'HUYBAN', N'Hủy bán, lý do chủ quan của người bán')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (10, N'TRUNGLAP', N'Trùng lập')
INSERT [dbo].[tbl_status] ([id], [status], [description]) VALUES (11, N'CHANDEXUAT', N'Cấm đề xuất')
SET IDENTITY_INSERT [dbo].[tbl_status] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_user] ON 

INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [phonenum], [avatar], [blocked], [last_login]) VALUES (1, N'haotn', N'123456', N'Nhựt Hào', N'haotndde@gmail.com', N'0938475637', N'haotn.png', 0, CAST(N'2022-10-10T00:00:00' AS SmallDateTime))
INSERT [dbo].[tbl_user] ([id], [username], [password], [fullname], [email], [phonenum], [avatar], [blocked], [last_login]) VALUES (3, N'admin', N'111111', N'Quản trị viên', N'adminqewd@gmail.com', N'0389847550', N'admin.png', 0, CAST(N'2022-10-09T00:00:00' AS SmallDateTime))
SET IDENTITY_INSERT [dbo].[tbl_user] OFF
GO
/****** Object:  Index [UQ__tbl_my_b__D2D146366F13570E]    Script Date: 10/12/2022 10:47:19 AM ******/
ALTER TABLE [dbo].[tbl_my_bcoin] ADD UNIQUE NONCLUSTERED 
(
	[id_user] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__tbl_mysh__D2D146362E0E8460]    Script Date: 10/12/2022 10:47:19 AM ******/
ALTER TABLE [dbo].[tbl_myshop] ADD UNIQUE NONCLUSTERED 
(
	[id_user] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__tbl_post__C71FE366E91294F7]    Script Date: 10/12/2022 10:47:19 AM ******/
ALTER TABLE [dbo].[tbl_post] ADD UNIQUE NONCLUSTERED 
(
	[id_card] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__tbl_prod__C71FE3664B4EB337]    Script Date: 10/12/2022 10:47:19 AM ******/
ALTER TABLE [dbo].[tbl_product] ADD UNIQUE NONCLUSTERED 
(
	[id_card] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__tbl_regi__D2D146368BBB88FC]    Script Date: 10/12/2022 10:47:19 AM ******/
ALTER TABLE [dbo].[tbl_register_active] ADD UNIQUE NONCLUSTERED 
(
	[id_user] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tbl_card] ADD  DEFAULT ((0)) FOR [hidden]
GO
ALTER TABLE [dbo].[tbl_category] ADD  DEFAULT ((0)) FOR [blocked]
GO
ALTER TABLE [dbo].[tbl_news] ADD  DEFAULT ((0)) FOR [hidden]
GO
ALTER TABLE [dbo].[tbl_register_active] ADD  DEFAULT ((0)) FOR [actived]
GO
ALTER TABLE [dbo].[tbl_user] ADD  DEFAULT ((1)) FOR [blocked]
GO
ALTER TABLE [dbo].[tbl_address]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_authority]  WITH CHECK ADD FOREIGN KEY([id_role])
REFERENCES [dbo].[tbl_role] ([id])
GO
ALTER TABLE [dbo].[tbl_authority]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_bcoin_history]  WITH CHECK ADD FOREIGN KEY([act_by])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_bcoin_history]  WITH CHECK ADD FOREIGN KEY([id_action])
REFERENCES [dbo].[tbl_action] ([id])
GO
ALTER TABLE [dbo].[tbl_bcoin_history]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_bservice_history]  WITH CHECK ADD FOREIGN KEY([act_by])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_bservice_history]  WITH CHECK ADD FOREIGN KEY([id_action])
REFERENCES [dbo].[tbl_action] ([id])
GO
ALTER TABLE [dbo].[tbl_bservice_history]  WITH CHECK ADD FOREIGN KEY([id_bservice])
REFERENCES [dbo].[tbl_bservice] ([id])
GO
ALTER TABLE [dbo].[tbl_bservice_history]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_bservice_price]  WITH CHECK ADD FOREIGN KEY([id_bservice])
REFERENCES [dbo].[tbl_bservice] ([id])
GO
ALTER TABLE [dbo].[tbl_bservice_price]  WITH CHECK ADD FOREIGN KEY([price_by])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_bservice_subbanner]  WITH CHECK ADD FOREIGN KEY([id_bservice])
REFERENCES [dbo].[tbl_bservice] ([id])
GO
ALTER TABLE [dbo].[tbl_card]  WITH CHECK ADD FOREIGN KEY([id_status])
REFERENCES [dbo].[tbl_status] ([id])
GO
ALTER TABLE [dbo].[tbl_card_subbanner]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_cart]  WITH CHECK ADD FOREIGN KEY([id_product])
REFERENCES [dbo].[tbl_product] ([id])
GO
ALTER TABLE [dbo].[tbl_cart]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_category]  WITH CHECK ADD FOREIGN KEY([add_by])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_category]  WITH CHECK ADD FOREIGN KEY([id_status])
REFERENCES [dbo].[tbl_status] ([id])
GO
ALTER TABLE [dbo].[tbl_commentcard]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_commentcard]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_displayfee_history]  WITH CHECK ADD FOREIGN KEY([act_by])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_displayfee_history]  WITH CHECK ADD FOREIGN KEY([id_action])
REFERENCES [dbo].[tbl_action] ([id])
GO
ALTER TABLE [dbo].[tbl_displayfee_history]  WITH CHECK ADD FOREIGN KEY([id_displayfee])
REFERENCES [dbo].[tbl_displayfee] ([id])
GO
ALTER TABLE [dbo].[tbl_fail_history]  WITH CHECK ADD FOREIGN KEY([fail_by])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_fail_history]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_follow_shop]  WITH CHECK ADD FOREIGN KEY([id_shop])
REFERENCES [dbo].[tbl_myshop] ([id])
GO
ALTER TABLE [dbo].[tbl_follow_shop]  WITH CHECK ADD FOREIGN KEY([id_userFollow])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_hidecard]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_hidecard]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_lovecard]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_lovecard]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_my_bcoin]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_mybservice]  WITH CHECK ADD FOREIGN KEY([id_bservice])
REFERENCES [dbo].[tbl_bservice] ([id])
GO
ALTER TABLE [dbo].[tbl_mybservice]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_myshop]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_news]  WITH CHECK ADD FOREIGN KEY([id_got])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_news]  WITH CHECK ADD FOREIGN KEY([id_sent])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_order]  WITH CHECK ADD FOREIGN KEY([id_buyer])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_order]  WITH CHECK ADD FOREIGN KEY([id_saler])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_order]  WITH CHECK ADD FOREIGN KEY([id_status])
REFERENCES [dbo].[tbl_status] ([id])
GO
ALTER TABLE [dbo].[tbl_orderdetail]  WITH CHECK ADD FOREIGN KEY([id_order])
REFERENCES [dbo].[tbl_order] ([id])
GO
ALTER TABLE [dbo].[tbl_orderdetail]  WITH CHECK ADD FOREIGN KEY([id_product])
REFERENCES [dbo].[tbl_product] ([id])
GO
ALTER TABLE [dbo].[tbl_post]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_product]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_product]  WITH CHECK ADD FOREIGN KEY([id_category])
REFERENCES [dbo].[tbl_category] ([id])
GO
ALTER TABLE [dbo].[tbl_product_history]  WITH CHECK ADD FOREIGN KEY([act_by])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_product_history]  WITH CHECK ADD FOREIGN KEY([id_action])
REFERENCES [dbo].[tbl_action] ([id])
GO
ALTER TABLE [dbo].[tbl_product_history]  WITH CHECK ADD FOREIGN KEY([id_product])
REFERENCES [dbo].[tbl_product] ([id])
GO
ALTER TABLE [dbo].[tbl_register_active]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_repcomment]  WITH CHECK ADD FOREIGN KEY([id_comment])
REFERENCES [dbo].[tbl_commentcard] ([id])
GO
ALTER TABLE [dbo].[tbl_repcomment]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_reportcard]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_reportcard]  WITH CHECK ADD FOREIGN KEY([id_problem])
REFERENCES [dbo].[tbl_problem] ([id])
GO
ALTER TABLE [dbo].[tbl_reportcard]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
ALTER TABLE [dbo].[tbl_sharecard]  WITH CHECK ADD FOREIGN KEY([id_card])
REFERENCES [dbo].[tbl_card] ([id])
GO
ALTER TABLE [dbo].[tbl_sharecard]  WITH CHECK ADD FOREIGN KEY([id_user])
REFERENCES [dbo].[tbl_user] ([id])
GO
USE [master]
GO
ALTER DATABASE [Biglobby] SET  READ_WRITE 
GO
