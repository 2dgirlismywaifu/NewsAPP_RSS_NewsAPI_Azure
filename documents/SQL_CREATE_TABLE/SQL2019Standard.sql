USE [newsapp-android]
GO
EXEC sys.sp_dropextendedproperty @name=N'MS_Description', @level0type=N'SCHEMA', @level0name=N'dbo',
     @level1type=N'TABLE', @level1name=N'USER_PASSLOGIN'
GO
ALTER TABLE [dbo].[USER_SSO_INFORMATION]
    DROP CONSTRAINT [FK_USER_SSO_INFORMATION_USER_SSO]
GO
ALTER TABLE [dbo].[USER_INFORMATION]
    DROP CONSTRAINT [FK_USER_INFORMATION_USER_PASSLOGIN]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
    DROP CONSTRAINT [FK_SYNC_SUBSCRIBE_SSO_USER_SSO]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
    DROP CONSTRAINT [FK_SYNC_SUBSCRIBE_SSO_NEWS_SOURCE]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE]
    DROP CONSTRAINT [FK_SYNC_SUBSCRIBE_USER_PASSLOGIN]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE]
    DROP CONSTRAINT [FK_SYNC_SUBSCRIBE_NEWS_SOURCE]
GO
ALTER TABLE [dbo].[SYNC_NEWS_FAVOURITE_SSO]
    DROP CONSTRAINT [FK_SYNC_NEWS_FAVOURITE_SSO_USER_SSO]
GO
ALTER TABLE [dbo].[SYNC_NEWS_FAVOURITE]
    DROP CONSTRAINT [FK_SYNC_NEWS_FAVOURITE_USER_PASSLOGIN]
GO
ALTER TABLE [dbo].[NEWS_DETAIL]
    DROP CONSTRAINT [FK_NEWS_DETAIL_NEWSTYPE_IMAGE]
GO
ALTER TABLE [dbo].[NEWS_DETAIL]
    DROP CONSTRAINT [FK_NEWS_DETAIL_NEWS_SOURCE]
GO
ALTER TABLE [dbo].[IMAGE_INFORMATION]
    DROP CONSTRAINT [FK_IMAGE_INFORMATION_NEWS_SOURCE]
GO
/****** Object:  Table [dbo].[USER_SSO_INFORMATION]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[USER_SSO_INFORMATION]')
             AND type in (N'U'))
    DROP TABLE [dbo].[USER_SSO_INFORMATION]
GO
/****** Object:  Table [dbo].[USER_SSO]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[USER_SSO]')
             AND type in (N'U'))
    DROP TABLE [dbo].[USER_SSO]
GO
/****** Object:  Table [dbo].[USER_PASSLOGIN]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[USER_PASSLOGIN]')
             AND type in (N'U'))
    DROP TABLE [dbo].[USER_PASSLOGIN]
GO
/****** Object:  Table [dbo].[USER_INFORMATION]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[USER_INFORMATION]')
             AND type in (N'U'))
    DROP TABLE [dbo].[USER_INFORMATION]
GO
/****** Object:  Table [dbo].[SYNC_SUBSCRIBE_SSO]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[SYNC_SUBSCRIBE_SSO]')
             AND type in (N'U'))
    DROP TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
GO
/****** Object:  Table [dbo].[SYNC_SUBSCRIBE]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[SYNC_SUBSCRIBE]')
             AND type in (N'U'))
    DROP TABLE [dbo].[SYNC_SUBSCRIBE]
GO
/****** Object:  Table [dbo].[SYNC_NEWS_FAVOURITE_SSO]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[SYNC_NEWS_FAVOURITE_SSO]')
             AND type in (N'U'))
    DROP TABLE [dbo].[SYNC_NEWS_FAVOURITE_SSO]
GO
/****** Object:  Table [dbo].[SYNC_NEWS_FAVOURITE]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[SYNC_NEWS_FAVOURITE]')
             AND type in (N'U'))
    DROP TABLE [dbo].[SYNC_NEWS_FAVOURITE]
GO
/****** Object:  Table [dbo].[NEWSTYPE_IMAGE]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[NEWSTYPE_IMAGE]')
             AND type in (N'U'))
    DROP TABLE [dbo].[NEWSTYPE_IMAGE]
GO
/****** Object:  Table [dbo].[NEWSAPI_COUNTRY]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[NEWSAPI_COUNTRY]')
             AND type in (N'U'))
    DROP TABLE [dbo].[NEWSAPI_COUNTRY]
GO
/****** Object:  Table [dbo].[NEWS_SOURCE]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[NEWS_SOURCE]')
             AND type in (N'U'))
    DROP TABLE [dbo].[NEWS_SOURCE]
GO
/****** Object:  Table [dbo].[NEWS_DETAIL]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[NEWS_DETAIL]')
             AND type in (N'U'))
    DROP TABLE [dbo].[NEWS_DETAIL]
GO
/****** Object:  Table [dbo].[IMAGE_INFORMATION]    Script Date: 20/02/2023 22:34:34 ******/
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'[dbo].[IMAGE_INFORMATION]')
             AND type in (N'U'))
    DROP TABLE [dbo].[IMAGE_INFORMATION]
GO
/****** Object:  Table [dbo].[IMAGE_INFORMATION]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IMAGE_INFORMATION]
(
    [image_id]  [int]           NOT NULL,
    [source_id] [int]           NOT NULL,
    [image]     [nvarchar](max) NULL,
    CONSTRAINT [PK_IMAGE_INFORMATION] PRIMARY KEY CLUSTERED
        (
         [image_id] ASC,
         [source_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NEWS_DETAIL]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NEWS_DETAIL]
(
    [source_id] [int]           NOT NULL,
    [url_type]  [nvarchar](255) NOT NULL,
    [url]       [nvarchar](max) NULL,
    CONSTRAINT [PK_NEWS_HEALTH_DETAIL] PRIMARY KEY CLUSTERED
        (
         [source_id] ASC,
         [url_type] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NEWS_SOURCE]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NEWS_SOURCE]
(
    [source_id]   [int]           NOT NULL,
    [source_name] [nvarchar](255) NULL,
    [urlmain]     [nvarchar](max) NULL,
    [information] [nvarchar](max) NULL,
    CONSTRAINT [PK_NEWS_SOURCE] PRIMARY KEY CLUSTERED
        (
         [source_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NEWSAPI_COUNTRY]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NEWSAPI_COUNTRY]
(
    [country_id]   [int]          NOT NULL,
    [country_code] [nvarchar](50) NULL,
    [country_name] [nvarchar](50) NULL,
    CONSTRAINT [PK_NEWSAPI_COUNTRY] PRIMARY KEY CLUSTERED
        (
         [country_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NEWSTYPE_IMAGE]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NEWSTYPE_IMAGE]
(
    [url_type]  [nvarchar](255) NOT NULL,
    [name_type] [nvarchar](50)  NULL,
    [url_image] [nvarchar](max) NULL,
    CONSTRAINT [PK_NEWSTYPE_IMAGE] PRIMARY KEY CLUSTERED
        (
         [url_type] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SYNC_NEWS_FAVOURITE]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SYNC_NEWS_FAVOURITE]
(
    [favourite_id] [int]           NOT NULL,
    [user_id]      [int]           NOT NULL,
    [url]          [nvarchar](max) NULL,
    [title]        [nvarchar](max) NULL,
    [image_url]    [nvarchar](max) NULL,
    [pubdate]      [nvarchar](max) NULL,
    [source_name]  [nvarchar](max) NULL,
    CONSTRAINT [PK_SYNC_NEWS_FAVOURITE] PRIMARY KEY CLUSTERED
        (
         [favourite_id] ASC,
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SYNC_NEWS_FAVOURITE_SSO]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SYNC_NEWS_FAVOURITE_SSO]
(
    [favourite_id] [int]           NOT NULL,
    [user_id]      [int]           NOT NULL,
    [url]          [nvarchar](max) NULL,
    [title]        [nvarchar](max) NULL,
    [image_url]    [nvarchar](max) NULL,
    [pubdate]      [nvarchar](max) NULL,
    [source_name]  [nvarchar](max) NULL,
    CONSTRAINT [PK_SYNC_NEWS_FAVOURITE_SSO] PRIMARY KEY CLUSTERED
        (
         [favourite_id] ASC,
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SYNC_SUBSCRIBE]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SYNC_SUBSCRIBE]
(
    [sync_id]   [int] NOT NULL,
    [user_id]   [int] NOT NULL,
    [source_id] [int] NOT NULL,
    CONSTRAINT [SYNC_SUBSCRIBE_PK] PRIMARY KEY CLUSTERED
        (
         [sync_id] ASC,
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SYNC_SUBSCRIBE_SSO]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
(
    [sync_id]   [int] NOT NULL,
    [user_id]   [int] NOT NULL,
    [source_id] [int] NOT NULL,
    CONSTRAINT [SYNC_SUBSCRIBE_SSO_PK] PRIMARY KEY CLUSTERED
        (
         [sync_id] ASC,
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[USER_INFORMATION]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USER_INFORMATION]
(
    [user_id]  [int]           NOT NULL,
    [name]     [nvarchar](max) NULL,
    [birthday] [date]          NULL,
    [gender]   [nvarchar](max) NULL,
    [avatar]   [nvarchar](max) NULL,
    CONSTRAINT [USER_INFORMATION_PK] PRIMARY KEY CLUSTERED
        (
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[USER_PASSLOGIN]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USER_PASSLOGIN]
(
    [user_id]  [int]           NOT NULL,
    [email]    [nvarchar](max) NOT NULL,
    [password] [nvarchar](max) NULL,
    [salt]     [nvarchar](max) NULL,
    [nickname] [nvarchar](max) NULL,
    [verify]   [nvarchar](20)  NULL,
    [recovery] [nvarchar](max) NULL,
    CONSTRAINT [USER_PASSLOGIN_PK] PRIMARY KEY CLUSTERED
        (
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[USER_SSO]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USER_SSO]
(
    [user_id]  [int]           NOT NULL,
    [email]    [nvarchar](max) NULL,
    [nickname] [nvarchar](max) NULL,
    [verify]   [nvarchar](20)  NULL,
    CONSTRAINT [PK_USER_SSO] PRIMARY KEY CLUSTERED
        (
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[USER_SSO_INFORMATION]    Script Date: 20/02/2023 22:34:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USER_SSO_INFORMATION]
(
    [user_id]  [int]           NOT NULL,
    [name]     [nvarchar](max) NULL,
    [birthday] [date]          NULL,
    [gender]   [nvarchar](max) NULL,
    [avatar]   [nvarchar](max) NULL,
    CONSTRAINT [PK_USER_SSO_INFORMATION] PRIMARY KEY CLUSTERED
        (
         [user_id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[IMAGE_INFORMATION]
    WITH CHECK ADD CONSTRAINT [FK_IMAGE_INFORMATION_NEWS_SOURCE] FOREIGN KEY ([source_id])
        REFERENCES [dbo].[NEWS_SOURCE] ([source_id])
GO
ALTER TABLE [dbo].[IMAGE_INFORMATION]
    CHECK CONSTRAINT [FK_IMAGE_INFORMATION_NEWS_SOURCE]
GO
ALTER TABLE [dbo].[NEWS_DETAIL]
    WITH CHECK ADD CONSTRAINT [FK_NEWS_DETAIL_NEWS_SOURCE] FOREIGN KEY ([source_id])
        REFERENCES [dbo].[NEWS_SOURCE] ([source_id])
GO
ALTER TABLE [dbo].[NEWS_DETAIL]
    CHECK CONSTRAINT [FK_NEWS_DETAIL_NEWS_SOURCE]
GO
ALTER TABLE [dbo].[NEWS_DETAIL]
    WITH CHECK ADD CONSTRAINT [FK_NEWS_DETAIL_NEWSTYPE_IMAGE] FOREIGN KEY ([url_type])
        REFERENCES [dbo].[NEWSTYPE_IMAGE] ([url_type])
GO
ALTER TABLE [dbo].[NEWS_DETAIL]
    CHECK CONSTRAINT [FK_NEWS_DETAIL_NEWSTYPE_IMAGE]
GO
ALTER TABLE [dbo].[SYNC_NEWS_FAVOURITE]
    WITH CHECK ADD CONSTRAINT [FK_SYNC_NEWS_FAVOURITE_USER_PASSLOGIN] FOREIGN KEY ([user_id])
        REFERENCES [dbo].[USER_PASSLOGIN] ([user_id])
GO
ALTER TABLE [dbo].[SYNC_NEWS_FAVOURITE]
    CHECK CONSTRAINT [FK_SYNC_NEWS_FAVOURITE_USER_PASSLOGIN]
GO
ALTER TABLE [dbo].[SYNC_NEWS_FAVOURITE_SSO]
    WITH CHECK ADD CONSTRAINT [FK_SYNC_NEWS_FAVOURITE_SSO_USER_SSO] FOREIGN KEY ([user_id])
        REFERENCES [dbo].[USER_SSO] ([user_id])
GO
ALTER TABLE [dbo].[SYNC_NEWS_FAVOURITE_SSO]
    CHECK CONSTRAINT [FK_SYNC_NEWS_FAVOURITE_SSO_USER_SSO]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE]
    WITH CHECK ADD CONSTRAINT [FK_SYNC_SUBSCRIBE_NEWS_SOURCE] FOREIGN KEY ([source_id])
        REFERENCES [dbo].[NEWS_SOURCE] ([source_id])
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE]
    CHECK CONSTRAINT [FK_SYNC_SUBSCRIBE_NEWS_SOURCE]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE]
    WITH CHECK ADD CONSTRAINT [FK_SYNC_SUBSCRIBE_USER_PASSLOGIN] FOREIGN KEY ([user_id])
        REFERENCES [dbo].[USER_PASSLOGIN] ([user_id])
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE]
    CHECK CONSTRAINT [FK_SYNC_SUBSCRIBE_USER_PASSLOGIN]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
    WITH CHECK ADD CONSTRAINT [FK_SYNC_SUBSCRIBE_SSO_NEWS_SOURCE] FOREIGN KEY ([source_id])
        REFERENCES [dbo].[NEWS_SOURCE] ([source_id])
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
    CHECK CONSTRAINT [FK_SYNC_SUBSCRIBE_SSO_NEWS_SOURCE]
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
    WITH CHECK ADD CONSTRAINT [FK_SYNC_SUBSCRIBE_SSO_USER_SSO] FOREIGN KEY ([user_id])
        REFERENCES [dbo].[USER_SSO] ([user_id])
GO
ALTER TABLE [dbo].[SYNC_SUBSCRIBE_SSO]
    CHECK CONSTRAINT [FK_SYNC_SUBSCRIBE_SSO_USER_SSO]
GO
ALTER TABLE [dbo].[USER_INFORMATION]
    WITH CHECK ADD CONSTRAINT [FK_USER_INFORMATION_USER_PASSLOGIN] FOREIGN KEY ([user_id])
        REFERENCES [dbo].[USER_PASSLOGIN] ([user_id])
GO
ALTER TABLE [dbo].[USER_INFORMATION]
    CHECK CONSTRAINT [FK_USER_INFORMATION_USER_PASSLOGIN]
GO
ALTER TABLE [dbo].[USER_SSO_INFORMATION]
    WITH CHECK ADD CONSTRAINT [FK_USER_SSO_INFORMATION_USER_SSO] FOREIGN KEY ([user_id])
        REFERENCES [dbo].[USER_SSO] ([user_id])
GO
ALTER TABLE [dbo].[USER_SSO_INFORMATION]
    CHECK CONSTRAINT [FK_USER_SSO_INFORMATION_USER_SSO]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'NORMAL LOGIN', @level0type=N'SCHEMA',
     @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'USER_PASSLOGIN'
GO
