USE [KurirskaSluzba]
GO
/****** Object:  StoredProcedure [dbo].[spOdobriZahtev]    Script Date: 15/09/2021 00:50:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[spOdobriZahtev] 
	@IdK varchar(100)
AS
BEGIN
	Insert into Kurir (RegBr,KorImeKur) VALUES ((SELECT RegBr FROM KurirZahtev WHERE KorIme = @IdK),@IdK);
	Delete from KurirZahtev where KorIme=@IdK;
END
