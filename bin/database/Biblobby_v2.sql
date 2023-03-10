USE [master]
GO
/****** Object:  Database [Biglobby]    Script Date: 10/7/2022 10:36:09 PM ******/
CREATE DATABASE [Biglobby]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Biglobby', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.ROOT\MSSQL\DATA\Biglobby.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Biglobby_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.ROOT\MSSQL\DATA\Biglobby_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
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
ALTER DATABASE [Biglobby] SET  DISABLE_BROKER 
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
ALTER DATABASE [Biglobby] SET RECOVERY SIMPLE 
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
/****** Object:  Table [dbo].[Action]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Action](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[action] [nvarchar](100) NULL,
	[description] [nvarchar](500) NULL,
 CONSTRAINT [PK_Action] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Address]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Address](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[fullname] [nvarchar](50) NULL,
	[phonenum] [nvarchar](10) NULL,
	[address] [nvarchar](500) NULL,
 CONSTRAINT [PK_UserAddress] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Authority]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Authority](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idRole] [bigint] NULL,
	[authorizeDate] [smalldatetime] NULL,
 CONSTRAINT [PK_Authority] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BCoinHistory]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BCoinHistory](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[quantity] [int] NULL,
	[idAction] [bigint] NULL,
	[note] [nvarchar](500) NULL,
	[coinDate] [smalldatetime] NULL,
	[actBy] [bigint] NULL,
 CONSTRAINT [PK_BCoinHistory] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BService]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BService](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[bservice] [nvarchar](100) NULL,
	[banner] [nvarchar](100) NULL,
	[description] [nvarchar](500) NULL,
 CONSTRAINT [PK_BService] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BServiceHistory]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BServiceHistory](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idBService] [bigint] NULL,
	[actDate] [smalldatetime] NULL,
	[useDays] [int] NULL,
	[actBy] [bigint] NULL,
	[idAction] [bigint] NULL,
 CONSTRAINT [PK_BServiceHistory] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BServicePrice]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BServicePrice](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[price] [float] NULL,
	[priceDate] [smalldatetime] NULL,
	[priceBy] [bigint] NULL,
	[idBService] [bigint] NULL,
	[note] [nvarchar](500) NULL,
 CONSTRAINT [PK_BServicePrice] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BServiceSubBanner]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BServiceSubBanner](
	[id] [bigint] NOT NULL,
	[idBService] [bigint] NOT NULL,
	[subbanner] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_BServiceSubBanner] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Card]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Card](
	[id] [bigint] NOT NULL,
	[isProduct] [bit] NULL,
	[idStatus] [bigint] NULL,
	[hidden] [bit] NULL,
 CONSTRAINT [PK_Post] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CardSubBanner]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CardSubBanner](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idCard] [bigint] NULL,
	[subbanner] [nvarchar](100) NULL,
 CONSTRAINT [PK_SubBanner] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cart]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cart](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idProduct] [bigint] NULL,
	[quantity] [int] NULL,
	[cartDate] [smalldatetime] NULL,
 CONSTRAINT [PK_Cart] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[category] [nvarchar](100) NULL,
	[description] [nvarchar](500) NULL,
	[blocked] [bit] NULL,
	[addBy] [bigint] NULL,
	[idStatus] [bigint] NULL,
	[addDate] [smalldatetime] NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CommentCard]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CommentCard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idCard] [bigint] NULL,
	[commentDate] [smalldatetime] NULL,
	[content] [nvarchar](500) NULL,
 CONSTRAINT [PK_CommentProduct] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DisplayFee]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DisplayFee](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[priceFrom] [float] NULL,
	[priceTo] [float] NULL,
	[fixedFee] [float] NULL,
	[percentFee] [float] NULL,
	[description] [nvarchar](500) NULL,
 CONSTRAINT [PK_DisplayFee] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DisplayFeeHistory]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DisplayFeeHistory](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[priceFrom] [float] NULL,
	[priceTo] [float] NULL,
	[fixedFee] [float] NULL,
	[percentFee] [float] NULL,
	[actDate] [smalldatetime] NULL,
	[actBy] [bigint] NULL,
	[idDisplayFee] [bigint] NULL,
	[idAction] [bigint] NULL,
 CONSTRAINT [PK_DFeeHistory] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FailHistory]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FailHistory](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idCard] [bigint] NULL,
	[failReason] [nvarchar](500) NULL,
	[failDate] [smalldatetime] NULL,
	[failBy] [bigint] NULL,
 CONSTRAINT [PK_FailProduct] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FollowShop]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FollowShop](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUserFollow] [bigint] NULL,
	[idUserShop] [bigint] NULL,
	[followDate] [smalldatetime] NULL,
 CONSTRAINT [PK_FollowShop] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HideCard]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HideCard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idCard] [bigint] NULL,
	[hideDate] [smalldatetime] NULL,
 CONSTRAINT [PK_HideProduct] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoveCard]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoveCard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idCard] [bigint] NULL,
	[loveDate] [smalldatetime] NULL,
 CONSTRAINT [PK_LoveProduct] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MyBCoin]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MyBCoin](
	[idUser] [bigint] NOT NULL,
	[coinnum] [int] NULL,
 CONSTRAINT [PK_MyBCoin] PRIMARY KEY CLUSTERED 
(
	[idUser] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MyBService]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MyBService](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idBService] [bigint] NULL,
 CONSTRAINT [PK_MyBService] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MyShop]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MyShop](
	[idUser] [bigint] NOT NULL,
	[intro] [nvarchar](100) NULL,
	[location] [nvarchar](500) NULL,
 CONSTRAINT [PK_MyShop] PRIMARY KEY CLUSTERED 
(
	[idUser] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[News]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[News](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUserSend] [bigint] NULL,
	[idUserGet] [bigint] NULL,
	[content] [nvarchar](500) NULL,
	[newsDate] [smalldatetime] NULL,
	[hidden] [bit] NULL,
 CONSTRAINT [PK_News] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idBuyer] [bigint] NULL,
	[fullname] [nvarchar](100) NULL,
	[phonenum] [nvarchar](10) NULL,
	[address] [nvarchar](500) NULL,
	[addDate] [smalldatetime] NULL,
	[noteBuyer] [nvarchar](500) NULL,
	[noteSaler] [nvarchar](500) NULL,
	[idStatus] [bigint] NULL,
	[idSaler] [bigint] NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idOrder] [bigint] NULL,
	[idProduct] [bigint] NULL,
	[quantity] [int] NULL,
	[price] [float] NULL,
 CONSTRAINT [PK_OrderDetail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Post]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Post](
	[idCard] [bigint] NOT NULL,
	[title] [nvarchar](100) NULL,
	[content] [nvarchar](500) NULL,
	[banner] [nvarchar](100) NULL,
 CONSTRAINT [PK_PostContent] PRIMARY KEY CLUSTERED 
(
	[idCard] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Problem]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Problem](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[problem] [nvarchar](100) NULL,
	[description] [nvarchar](500) NULL,
 CONSTRAINT [PK_Problem_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[idCard] [bigint] NOT NULL,
	[product] [nvarchar](100) NULL,
	[banner] [nvarchar](100) NULL,
	[available] [int] NULL,
	[description] [nvarchar](500) NULL,
	[price] [float] NULL,
	[pricePercent] [float] NULL,
	[idCategory] [bigint] NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[idCard] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductHistory]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductHistory](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idProduct] [bigint] NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[actDate] [smalldatetime] NULL,
	[actBy] [bigint] NULL,
	[idAction] [bigint] NULL,
 CONSTRAINT [PK_ProductHistory] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RegisterActive]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RegisterActive](
	[idUser] [bigint] NOT NULL,
	[registerDate] [smalldatetime] NULL,
	[actived] [bit] NULL,
 CONSTRAINT [PK_RegisterActive_1] PRIMARY KEY CLUSTERED 
(
	[idUser] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Repcomment]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Repcomment](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idComment] [bigint] NULL,
	[idUser] [bigint] NULL,
	[repcommentDate] [smalldatetime] NULL,
	[content] [nvarchar](500) NULL,
 CONSTRAINT [PK_RepComment] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ReportCard]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ReportCard](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idUser] [bigint] NULL,
	[idCard] [bigint] NULL,
	[reportDate] [smalldatetime] NULL,
	[idProblem] [bigint] NULL,
	[note] [nvarchar](500) NULL,
 CONSTRAINT [PK_ReportProduct] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[role] [nvarchar](100) NULL,
	[description] [nvarchar](500) NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ShareCard]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ShareCard](
	[id] [bigint] NOT NULL,
	[idUser] [bigint] NULL,
	[idCard] [bigint] NULL,
	[shareDate] [smalldatetime] NULL,
 CONSTRAINT [PK_ShareCard] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Status]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Status](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[status] [nvarchar](100) NULL,
	[description] [nvarchar](500) NULL,
 CONSTRAINT [PK_Status] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 10/7/2022 10:36:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NULL,
	[password] [nvarchar](50) NULL,
	[fullname] [nvarchar](50) NULL,
	[email] [nvarchar](50) NULL,
	[phonenum] [nvarchar](10) NULL,
	[avatar] [nvarchar](100) NULL,
	[blocked] [bit] NULL,
	[lastLogin] [smalldatetime] NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Address]  WITH CHECK ADD  CONSTRAINT [FK_DeliveryAddress_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK_DeliveryAddress_User]
GO
ALTER TABLE [dbo].[Authority]  WITH CHECK ADD  CONSTRAINT [FK_Authority_Role] FOREIGN KEY([idRole])
REFERENCES [dbo].[Role] ([id])
GO
ALTER TABLE [dbo].[Authority] CHECK CONSTRAINT [FK_Authority_Role]
GO
ALTER TABLE [dbo].[Authority]  WITH CHECK ADD  CONSTRAINT [FK_Authority_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Authority] CHECK CONSTRAINT [FK_Authority_User]
GO
ALTER TABLE [dbo].[BCoinHistory]  WITH CHECK ADD  CONSTRAINT [FK_BCoinHistory_Action] FOREIGN KEY([idAction])
REFERENCES [dbo].[Action] ([id])
GO
ALTER TABLE [dbo].[BCoinHistory] CHECK CONSTRAINT [FK_BCoinHistory_Action]
GO
ALTER TABLE [dbo].[BCoinHistory]  WITH CHECK ADD  CONSTRAINT [FK_BCoinHistory_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[BCoinHistory] CHECK CONSTRAINT [FK_BCoinHistory_User]
GO
ALTER TABLE [dbo].[BCoinHistory]  WITH CHECK ADD  CONSTRAINT [FK_BCoinHistory_User1] FOREIGN KEY([actBy])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[BCoinHistory] CHECK CONSTRAINT [FK_BCoinHistory_User1]
GO
ALTER TABLE [dbo].[BServiceHistory]  WITH CHECK ADD  CONSTRAINT [FK_BServiceHistory_Action] FOREIGN KEY([idAction])
REFERENCES [dbo].[Action] ([id])
GO
ALTER TABLE [dbo].[BServiceHistory] CHECK CONSTRAINT [FK_BServiceHistory_Action]
GO
ALTER TABLE [dbo].[BServiceHistory]  WITH CHECK ADD  CONSTRAINT [FK_BServiceHistory_BService] FOREIGN KEY([idBService])
REFERENCES [dbo].[BService] ([id])
GO
ALTER TABLE [dbo].[BServiceHistory] CHECK CONSTRAINT [FK_BServiceHistory_BService]
GO
ALTER TABLE [dbo].[BServiceHistory]  WITH CHECK ADD  CONSTRAINT [FK_BServiceHistory_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[BServiceHistory] CHECK CONSTRAINT [FK_BServiceHistory_User]
GO
ALTER TABLE [dbo].[BServiceHistory]  WITH CHECK ADD  CONSTRAINT [FK_BServiceHistory_User1] FOREIGN KEY([actBy])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[BServiceHistory] CHECK CONSTRAINT [FK_BServiceHistory_User1]
GO
ALTER TABLE [dbo].[BServicePrice]  WITH CHECK ADD  CONSTRAINT [FK_BServicePrice_BService] FOREIGN KEY([idBService])
REFERENCES [dbo].[BService] ([id])
GO
ALTER TABLE [dbo].[BServicePrice] CHECK CONSTRAINT [FK_BServicePrice_BService]
GO
ALTER TABLE [dbo].[BServicePrice]  WITH CHECK ADD  CONSTRAINT [FK_BServicePrice_User] FOREIGN KEY([priceBy])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[BServicePrice] CHECK CONSTRAINT [FK_BServicePrice_User]
GO
ALTER TABLE [dbo].[BServiceSubBanner]  WITH CHECK ADD FOREIGN KEY([idBService])
REFERENCES [dbo].[BService] ([id])
GO
ALTER TABLE [dbo].[Card]  WITH CHECK ADD  CONSTRAINT [FK_Card_Status] FOREIGN KEY([idStatus])
REFERENCES [dbo].[Status] ([id])
GO
ALTER TABLE [dbo].[Card] CHECK CONSTRAINT [FK_Card_Status]
GO
ALTER TABLE [dbo].[CardSubBanner]  WITH CHECK ADD  CONSTRAINT [FK_Subbanner_BService] FOREIGN KEY([idCard])
REFERENCES [dbo].[BService] ([id])
GO
ALTER TABLE [dbo].[CardSubBanner] CHECK CONSTRAINT [FK_Subbanner_BService]
GO
ALTER TABLE [dbo].[CardSubBanner]  WITH CHECK ADD  CONSTRAINT [FK_Subbanner_Post] FOREIGN KEY([idCard])
REFERENCES [dbo].[Post] ([idCard])
GO
ALTER TABLE [dbo].[CardSubBanner] CHECK CONSTRAINT [FK_Subbanner_Post]
GO
ALTER TABLE [dbo].[CardSubBanner]  WITH CHECK ADD  CONSTRAINT [FK_Subbanner_Product] FOREIGN KEY([idCard])
REFERENCES [dbo].[Product] ([idCard])
GO
ALTER TABLE [dbo].[CardSubBanner] CHECK CONSTRAINT [FK_Subbanner_Product]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK_Cart_Product] FOREIGN KEY([idProduct])
REFERENCES [dbo].[Product] ([idCard])
GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK_Cart_Product]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK_Cart_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK_Cart_User]
GO
ALTER TABLE [dbo].[Category]  WITH CHECK ADD  CONSTRAINT [FK_Category_Status] FOREIGN KEY([idStatus])
REFERENCES [dbo].[Status] ([id])
GO
ALTER TABLE [dbo].[Category] CHECK CONSTRAINT [FK_Category_Status]
GO
ALTER TABLE [dbo].[Category]  WITH CHECK ADD  CONSTRAINT [FK_Category_User] FOREIGN KEY([addBy])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Category] CHECK CONSTRAINT [FK_Category_User]
GO
ALTER TABLE [dbo].[CommentCard]  WITH CHECK ADD  CONSTRAINT [FK_CommentCard_Card] FOREIGN KEY([idCard])
REFERENCES [dbo].[Card] ([id])
GO
ALTER TABLE [dbo].[CommentCard] CHECK CONSTRAINT [FK_CommentCard_Card]
GO
ALTER TABLE [dbo].[CommentCard]  WITH CHECK ADD  CONSTRAINT [FK_CommentCard_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[CommentCard] CHECK CONSTRAINT [FK_CommentCard_User]
GO
ALTER TABLE [dbo].[DisplayFeeHistory]  WITH CHECK ADD  CONSTRAINT [FK_DisplayFeeHistory_DisplayFee] FOREIGN KEY([idDisplayFee])
REFERENCES [dbo].[DisplayFee] ([id])
GO
ALTER TABLE [dbo].[DisplayFeeHistory] CHECK CONSTRAINT [FK_DisplayFeeHistory_DisplayFee]
GO
ALTER TABLE [dbo].[DisplayFeeHistory]  WITH CHECK ADD  CONSTRAINT [FK_DisplayFeeHistory_User] FOREIGN KEY([actBy])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[DisplayFeeHistory] CHECK CONSTRAINT [FK_DisplayFeeHistory_User]
GO
ALTER TABLE [dbo].[FailHistory]  WITH CHECK ADD  CONSTRAINT [FK_FailCard_Card] FOREIGN KEY([idCard])
REFERENCES [dbo].[Card] ([id])
GO
ALTER TABLE [dbo].[FailHistory] CHECK CONSTRAINT [FK_FailCard_Card]
GO
ALTER TABLE [dbo].[FollowShop]  WITH CHECK ADD  CONSTRAINT [FK_FollowShop_User] FOREIGN KEY([idUserFollow])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[FollowShop] CHECK CONSTRAINT [FK_FollowShop_User]
GO
ALTER TABLE [dbo].[FollowShop]  WITH CHECK ADD  CONSTRAINT [FK_FollowShop_User1] FOREIGN KEY([idUserShop])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[FollowShop] CHECK CONSTRAINT [FK_FollowShop_User1]
GO
ALTER TABLE [dbo].[HideCard]  WITH CHECK ADD  CONSTRAINT [FK_HideCard_Card] FOREIGN KEY([idCard])
REFERENCES [dbo].[Card] ([id])
GO
ALTER TABLE [dbo].[HideCard] CHECK CONSTRAINT [FK_HideCard_Card]
GO
ALTER TABLE [dbo].[HideCard]  WITH CHECK ADD  CONSTRAINT [FK_HideCard_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[HideCard] CHECK CONSTRAINT [FK_HideCard_User]
GO
ALTER TABLE [dbo].[LoveCard]  WITH CHECK ADD  CONSTRAINT [FK_LoveCard_Card] FOREIGN KEY([idCard])
REFERENCES [dbo].[Card] ([id])
GO
ALTER TABLE [dbo].[LoveCard] CHECK CONSTRAINT [FK_LoveCard_Card]
GO
ALTER TABLE [dbo].[LoveCard]  WITH CHECK ADD  CONSTRAINT [FK_LoveCard_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[LoveCard] CHECK CONSTRAINT [FK_LoveCard_User]
GO
ALTER TABLE [dbo].[MyBCoin]  WITH CHECK ADD  CONSTRAINT [FK_MyBCoin_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[MyBCoin] CHECK CONSTRAINT [FK_MyBCoin_User]
GO
ALTER TABLE [dbo].[MyBService]  WITH CHECK ADD  CONSTRAINT [FK_MyBService_BService] FOREIGN KEY([idBService])
REFERENCES [dbo].[BService] ([id])
GO
ALTER TABLE [dbo].[MyBService] CHECK CONSTRAINT [FK_MyBService_BService]
GO
ALTER TABLE [dbo].[MyBService]  WITH CHECK ADD  CONSTRAINT [FK_MyBService_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[MyBService] CHECK CONSTRAINT [FK_MyBService_User]
GO
ALTER TABLE [dbo].[MyShop]  WITH CHECK ADD  CONSTRAINT [FK_MyShop_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[MyShop] CHECK CONSTRAINT [FK_MyShop_User]
GO
ALTER TABLE [dbo].[News]  WITH CHECK ADD  CONSTRAINT [FK_News_User] FOREIGN KEY([idUserGet])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[News] CHECK CONSTRAINT [FK_News_User]
GO
ALTER TABLE [dbo].[News]  WITH CHECK ADD  CONSTRAINT [FK_News_User1] FOREIGN KEY([idUserSend])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[News] CHECK CONSTRAINT [FK_News_User1]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Status] FOREIGN KEY([idStatus])
REFERENCES [dbo].[Status] ([id])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Status]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_User] FOREIGN KEY([idBuyer])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_User]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_User1] FOREIGN KEY([idSaler])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_User1]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Order] FOREIGN KEY([idOrder])
REFERENCES [dbo].[Order] ([id])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Order]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Product] FOREIGN KEY([idProduct])
REFERENCES [dbo].[Product] ([idCard])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Product]
GO
ALTER TABLE [dbo].[Post]  WITH CHECK ADD  CONSTRAINT [FK_Post_Card] FOREIGN KEY([idCard])
REFERENCES [dbo].[Card] ([id])
GO
ALTER TABLE [dbo].[Post] CHECK CONSTRAINT [FK_Post_Card]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_Card] FOREIGN KEY([idCard])
REFERENCES [dbo].[Card] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_Card]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_Category] FOREIGN KEY([idCategory])
REFERENCES [dbo].[Category] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_Category]
GO
ALTER TABLE [dbo].[ProductHistory]  WITH CHECK ADD  CONSTRAINT [FK_ProductHistory_Action] FOREIGN KEY([idAction])
REFERENCES [dbo].[Action] ([id])
GO
ALTER TABLE [dbo].[ProductHistory] CHECK CONSTRAINT [FK_ProductHistory_Action]
GO
ALTER TABLE [dbo].[ProductHistory]  WITH CHECK ADD  CONSTRAINT [FK_ProductHistory_Product] FOREIGN KEY([idProduct])
REFERENCES [dbo].[Product] ([idCard])
GO
ALTER TABLE [dbo].[ProductHistory] CHECK CONSTRAINT [FK_ProductHistory_Product]
GO
ALTER TABLE [dbo].[ProductHistory]  WITH CHECK ADD  CONSTRAINT [FK_ProductHistory_User] FOREIGN KEY([actBy])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[ProductHistory] CHECK CONSTRAINT [FK_ProductHistory_User]
GO
ALTER TABLE [dbo].[RegisterActive]  WITH CHECK ADD  CONSTRAINT [FK_RegisterActive_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[RegisterActive] CHECK CONSTRAINT [FK_RegisterActive_User]
GO
ALTER TABLE [dbo].[Repcomment]  WITH CHECK ADD  CONSTRAINT [FK_Repcomment_CommentCard] FOREIGN KEY([idComment])
REFERENCES [dbo].[CommentCard] ([id])
GO
ALTER TABLE [dbo].[Repcomment] CHECK CONSTRAINT [FK_Repcomment_CommentCard]
GO
ALTER TABLE [dbo].[Repcomment]  WITH CHECK ADD  CONSTRAINT [FK_Repcomment_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Repcomment] CHECK CONSTRAINT [FK_Repcomment_User]
GO
ALTER TABLE [dbo].[ReportCard]  WITH CHECK ADD  CONSTRAINT [FK_ReportCard_ReportCard] FOREIGN KEY([idProblem])
REFERENCES [dbo].[Problem] ([id])
GO
ALTER TABLE [dbo].[ReportCard] CHECK CONSTRAINT [FK_ReportCard_ReportCard]
GO
ALTER TABLE [dbo].[ReportCard]  WITH CHECK ADD  CONSTRAINT [FK_ReportCard_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[ReportCard] CHECK CONSTRAINT [FK_ReportCard_User]
GO
ALTER TABLE [dbo].[ShareCard]  WITH CHECK ADD  CONSTRAINT [FK_ShareCard_Card] FOREIGN KEY([idCard])
REFERENCES [dbo].[Card] ([id])
GO
ALTER TABLE [dbo].[ShareCard] CHECK CONSTRAINT [FK_ShareCard_Card]
GO
ALTER TABLE [dbo].[ShareCard]  WITH CHECK ADD  CONSTRAINT [FK_ShareCard_User] FOREIGN KEY([idUser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[ShareCard] CHECK CONSTRAINT [FK_ShareCard_User]
GO
USE [master]
GO
ALTER DATABASE [Biglobby] SET  READ_WRITE 
GO
