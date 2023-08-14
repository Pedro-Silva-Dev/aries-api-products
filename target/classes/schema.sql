CREATE TABLE IF NOT EXISTS `categorias` (
  `id` int PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `nome` varchar(300) NOT NULL,
  `ativo` tinyint(1) DEFAULT TRUE,
  `dhc` timestamp DEFAULT current_timestamp,
  `dhu` timestamp DEFAULT current_timestamp
);

CREATE TABLE IF NOT EXISTS `produtos` (
  `id` int PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `nome` varchar(300) NOT NULL,
  `descricao` varchar(500) DEFAULT NULL,
  `preco` decimal(8,2) NOT NULL,
  `estoque` int NOT NULL,
  `ativo` tinyint(1) DEFAULT TRUE,
  `dhc` timestamp DEFAULT current_timestamp,
  `dhu` timestamp DEFAULT current_timestamp,
  `categoria_id` int NOT NULL,
  FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
);

CREATE TABLE IF NOT EXISTS `promocoes` (
  `id` int PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `nome` varchar(300) NOT NULL,
  `descricao` varchar(500) DEFAULT NULL,
  `desconto` decimal(8,2) NOT NULL,
  `deconto_percentual` tinyint(1) DEFAULT TRUE,
  `ativo` tinyint(1) DEFAULT TRUE,
  `dhi` timestamp DEFAULT current_timestamp,
  `dhf` timestamp DEFAULT current_timestamp,
  `dhc` timestamp DEFAULT current_timestamp,
  `dhu` timestamp DEFAULT current_timestamp
); 

CREATE TABLE IF NOT EXISTS `produtos_promocoes` (
  `id` int PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `produto_id` int NOT NULL,
  `promocao_id` int NOT NULL,
  FOREIGN KEY (`produto_id`) REFERENCES `produtos` (`id`),
  FOREIGN KEY (`promocao_id`) REFERENCES `promocoes` (`id`)
);

CREATE OR REPLACE VIEW view_detalhes_produtos
AS 
SELECT p.id, p.nome, p.descricao, p.preco, p.estoque, p.ativo, p.dhc, p.dhu, c.id AS categoria_id, c.nome AS categoria 
	FROM produtos p 
	JOIN categorias c ON p.categoria_id = c.id
WHERE c.ativo IS TRUE;

CREATE OR REPLACE VIEW view_detalhes_promocoes_produtos
AS 
SELECT pr.id, pr.nome, pr.descricao, pr.desconto, pr.deconto_percentual, pr.ativo, pr.dhi, pr.dhf, pr.dhc, pr.dhu, p.nome AS produto, p.id AS produto_id, p.preco AS produto_preco, p.descricao produto_descricao, p.estoque AS produto_estoque, c.id AS categoria_id, c.nome AS categoria 
	FROM produtos p 
	JOIN categorias c ON p.categoria_id = c.id
	JOIN produtos_promocoes ppr ON ppr.produto_id = p.id
	JOIN promocoes pr ON ppr.promocao_id = pr.id
WHERE c.ativo IS TRUE AND p.ativo IS TRUE;


