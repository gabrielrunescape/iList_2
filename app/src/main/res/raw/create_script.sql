-- CURRENT VERSION APPLICATION 1.0 --
CREATE TABLE IF NOT EXISTS `Status` (
    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    Descricao VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS `Unidade` (
    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    Descricao VARCHAR(16) NOT NULL,
    Abreviacao VARCHAR(4) NULL
);

CREATE TABLE IF NOT EXISTS `Item` (
    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    Nome VARCHAR(16) NOT NULL,
    Status INTEGER NOT NULL,
    Quantidade INTEGER NOT NULL DEFAULT 1,
    Unidade INTEGER NOT NULL,
    FOREIGN KEY (Unidade) REFERENCES `Unidade` (ID),
    FOREIGN KEY (Status) REFERENCES `Status` (ID)
);

CREATE TABLE IF NOT EXISTS `Orcamento` (
    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    Estabelecimento VARCHAR(32) NULL,
    Preco REAL NOT NULL,
    Observacao TEXT NULL,
    Item INTEGER NOT NULL,
    Comprado INTEGER NULL,
    FOREIGN KEY (Item) REFERENCES `Item` (ID),
    CONSTRAINT uc_Item_Comprado UNIQUE (Item, Comprado)
);

-- INSERT REGION ON TABLES `Status` AND `Unidade` --
INSERT INTO Status (ID, Descricao, Abreviacao) VALUES (NULL, 'À comprar');
INSERT INTO Status (ID, Descricao, Abreviacao) VALUES (NULL, 'Orçamento feito');
INSERT INTO Status (ID, Descricao, Abreviacao) VALUES (NULL, 'Comprado');

INSERT INTO `Unidade` VALUES (NULL, 'Metro', 'm');
INSERT INTO `Unidade` VALUES (NULL, 'Peça', 'pc');
INSERT INTO `Unidade` VALUES (NULL, 'Unidade', 'un');