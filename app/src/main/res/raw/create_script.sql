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
    Comprado INTEGER NOT NULL DEFAULT 0,
    Unidade INTEGER NOT NULL,
    FOREIGN KEY (Unidade) REFERENCES `Unidade` (ID),
    FOREIGN KEY (Status) REFERENCES `Status` (ID)
);

CREATE TABLE IF NOT EXISTS `Orcamento` (
    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    Estabelecimento VARCHAR(32) NULL,
    Quantidade INTEGER NOT NULL,
    Preco REAL NOT NULL,
    Observacao TEXT NULL,
    Item INTEGER NOT NULL,
    FOREIGN KEY (Item) REFERENCES `Item` (ID)
);

-- INSERT REGION ON TABLES `Status` AND `Unidade` --
INSERT INTO `Status` (ID, Descricao) VALUES (NULL, 'À comprar');
INSERT INTO `Status` (ID, Descricao) VALUES (NULL, 'Parcialmente comprado');
INSERT INTO `Status` (ID, Descricao) VALUES (NULL, 'Comprado');
INSERT INTO `Status` (ID, Descricao) VALUES (NULL, 'Comprado com excesso');

INSERT INTO `Unidade` (ID, Descricao, Abreviacao) VALUES (NULL, 'Metro', 'm');
INSERT INTO `Unidade` (ID, Descricao, Abreviacao) VALUES (NULL, 'Peça', 'pc');
INSERT INTO `Unidade` (ID, Descricao, Abreviacao) VALUES (NULL, 'Unidade', 'un');