
CREATE TABLE [Administrator]
( 
	[KorImeAdmin]        varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL 
)
go

CREATE TABLE [Grad]
( 
	[IdG]                int  IDENTITY ( 1,1 )  NOT NULL ,
	[Naziv]              varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[PostBroj]           varchar(100)  NOT NULL 
)
go

CREATE TABLE [Korisnik]
( 
	[Ime]                varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[Prezime]            varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[KorIme]             varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[Sifra]              varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[BrPoslPaketa]       int  NOT NULL 
	CONSTRAINT [Nula_781671784]
		 DEFAULT  0
)
go

CREATE TABLE [Kurir]
( 
	[BrIspPaketa]        int  NOT NULL 
	CONSTRAINT [Nula_352119338]
		 DEFAULT  0,
	[Profit]             decimal(10,3)  NOT NULL 
	CONSTRAINT [Nula_664378298]
		 DEFAULT  0,
	[Status]             bit  NOT NULL 
	CONSTRAINT [Nula_430418619]
		 DEFAULT  0,
	[RegBr]              varchar(100)  NOT NULL ,
	[KorImeKur]          varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[TrProfit]           decimal(10,3)  NULL 
	CONSTRAINT [Nula_1485918620_1985109285]
		 DEFAULT  0
)
go

CREATE TABLE [KurirZahtev]
( 
	[RegBr]              varchar(100)  NOT NULL ,
	[KorIme]             varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[KorImeAdmin]        varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL 
)
go

CREATE TABLE [Opstina]
( 
	[IdO]                int  IDENTITY ( 1,1 )  NOT NULL ,
	[Naziv]              varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL ,
	[Xkoord]             int  NOT NULL ,
	[Ykoord]             int  NOT NULL ,
	[IdG]                int  NOT NULL 
)
go

CREATE TABLE [Paket]
( 
	[IdPak]              int  IDENTITY ( 1,1 )  NOT NULL ,
	[Status]             smallint  NOT NULL 
	CONSTRAINT [Nula_363763380]
		 DEFAULT  0,
	[Cena]               decimal(10,3)  NOT NULL ,
	[VremeZahteva]       datetime  NULL ,
	[Tip]                smallint  NULL 
	CONSTRAINT [TipPaketa_806812596_45655223]
		CHECK  ( [Tip]=0 OR [Tip]=1 OR [Tip]=2 ),
	[Tezina]             decimal(10,3)  NULL ,
	[IdOpsOd]            int  NOT NULL ,
	[IdOpsDo]            int  NOT NULL ,
	[KorIme]             varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL 
)
go

ALTER TABLE [Paket]
	 WITH CHECK ADD CONSTRAINT [StatusPaketa_75083639] CHECK  ( [Status]=0 OR [Status]=1 OR [Status]=2 OR [Status]=3 )
go

CREATE TABLE [Ponuda]
( 
	[Procenat]           decimal(10,3)  NULL ,
	[IdPon]              int  IDENTITY ( 1,1 )  NOT NULL ,
	[Status]             bit  NOT NULL 
	CONSTRAINT [Nula_1485918620_791631673]
		 DEFAULT  0,
	[IdPak]              int  NOT NULL ,
	[KorImeKur]          varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL 
)
go

CREATE TABLE [Vozilo]
( 
	[RegBr]              varchar(100)  NOT NULL ,
	[TipGoriva]          smallint  NOT NULL ,
	[Potrosnja]          decimal(10,3)  NOT NULL 
)
go

ALTER TABLE [Vozilo]
	 WITH CHECK ADD CONSTRAINT [TipGoriva_2021023082] CHECK  ( [TipGoriva]=0 OR [TipGoriva]=1 OR [TipGoriva]=2 )
go

ALTER TABLE [Administrator]
	ADD CONSTRAINT [XPKAdministrator] PRIMARY KEY  CLUSTERED ([KorImeAdmin] ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XPKGrad] PRIMARY KEY  CLUSTERED ([IdG] ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XAKNaziv] UNIQUE ([Naziv]  ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XAKPostBroj] UNIQUE ([PostBroj]  ASC)
go

ALTER TABLE [Korisnik]
	ADD CONSTRAINT [XPKKorisnik] PRIMARY KEY  CLUSTERED ([KorIme] ASC)
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [XPKKurir] PRIMARY KEY  CLUSTERED ([KorImeKur] ASC)
go

ALTER TABLE [KurirZahtev]
	ADD CONSTRAINT [XPKKurirZahtev] PRIMARY KEY  CLUSTERED ([RegBr] ASC,[KorIme] ASC)
go

ALTER TABLE [Opstina]
	ADD CONSTRAINT [XPKOpstina] PRIMARY KEY  CLUSTERED ([IdO] ASC)
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [XPKPaket] PRIMARY KEY  CLUSTERED ([IdPak] ASC)
go

ALTER TABLE [Ponuda]
	ADD CONSTRAINT [XPKPonuda] PRIMARY KEY  CLUSTERED ([IdPon] ASC,[IdPak] ASC,[KorImeKur] ASC)
go

ALTER TABLE [Vozilo]
	ADD CONSTRAINT [XPKVozilo] PRIMARY KEY  CLUSTERED ([RegBr] ASC)
go


ALTER TABLE [Administrator]
	ADD CONSTRAINT [R_19] FOREIGN KEY ([KorImeAdmin]) REFERENCES [Korisnik]([KorIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([RegBr]) REFERENCES [Vozilo]([RegBr])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_20] FOREIGN KEY ([KorImeKur]) REFERENCES [Korisnik]([KorIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [KurirZahtev]
	ADD CONSTRAINT [R_7] FOREIGN KEY ([KorIme]) REFERENCES [Korisnik]([KorIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [KurirZahtev]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([RegBr]) REFERENCES [Vozilo]([RegBr])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [KurirZahtev]
	ADD CONSTRAINT [R_18] FOREIGN KEY ([KorImeAdmin]) REFERENCES [Administrator]([KorImeAdmin])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Opstina]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([IdG]) REFERENCES [Grad]([IdG])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Paket]
	ADD CONSTRAINT [R_23] FOREIGN KEY ([IdOpsOd]) REFERENCES [Opstina]([IdO])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [R_25] FOREIGN KEY ([IdOpsDo]) REFERENCES [Opstina]([IdO])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [R_26] FOREIGN KEY ([KorIme]) REFERENCES [Korisnik]([KorIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Ponuda]
	ADD CONSTRAINT [R_35] FOREIGN KEY ([KorImeKur]) REFERENCES [Kurir]([KorImeKur])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Ponuda]
	ADD CONSTRAINT [R_27] FOREIGN KEY ([IdPak]) REFERENCES [Paket]([IdPak])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

CREATE PROCEDURE [dbo].[spOdobriZahtev] 
	@IdK varchar(100)
AS
BEGIN
	Insert into Kurir (RegBr,KorImeKur) VALUES ((SELECT RegBr FROM KurirZahtev WHERE KorIme = @IdK),@IdK);
	Delete from KurirZahtev where KorIme=@IdK;
END

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

