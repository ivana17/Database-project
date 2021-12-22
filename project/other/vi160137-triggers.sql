USE [KurirskaSluzba]
GO
/****** Object:  Trigger [dbo].[TR_TransportOffer_]    Script Date: 15/09/2021 00:49:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[TR_TransportOffer_]
   ON  [dbo].[Ponuda]
   AFTER Update
AS 
BEGIN
	Declare @IdPon int
	Declare @IdPak int
	Declare @stat1 bit
	Declare @stat2 bit

	select @stat1 = [Status], @IdPak = [IdPak]
	from inserted

	select @stat2 = [Status]
	from deleted

	if(@stat1 = 1 AND @stat2 = 0)
	begin

		delete from Ponuda
		where IdPak = @IdPak AND @IdPon <> IdPon
	end

END
