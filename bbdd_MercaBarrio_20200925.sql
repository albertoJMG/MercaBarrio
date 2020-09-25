CREATE DATABASE  IF NOT EXISTS `mercaBarrio` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mercaBarrio`;
-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: mercaBarrio
-- ------------------------------------------------------
-- Server version	5.7.31-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `SEQUENCE`
--

DROP TABLE IF EXISTS `SEQUENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE`
--

LOCK TABLES `SEQUENCE` WRITE;
/*!40000 ALTER TABLE `SEQUENCE` DISABLE KEYS */;
INSERT INTO `SEQUENCE` VALUES ('SEQ_GEN',400);
/*!40000 ALTER TABLE `SEQUENCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrador` (
  `usuarioID` bigint(20) NOT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`usuarioID`),
  CONSTRAINT `FK_administrador_usuarioID` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (1,'MG','Alberto J.');
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `usuarioID` bigint(20) NOT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `dni` varchar(10) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`usuarioID`),
  UNIQUE KEY `UNQ_cliente_0` (`dni`),
  CONSTRAINT `FK_cliente_usuarioID` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (2,'Marrufo Garcia','11111111H','Alberto Jose'),(3,'Herrojo Garcia','22222222H','Cristina'),(4,'Benito Ariza','3333333H','Rosa'),(5,'Marrufo Garcia','4444444H','Ismael'),(6,'Marrufo Jimenez','55555555H','Jose Antonio'),(7,'Garcia Delgado','66666666H','Maria del Valle');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `envio`
--

DROP TABLE IF EXISTS `envio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `envio` (
  `id_envio` bigint(20) NOT NULL,
  `ESTADO_ENVIO` varchar(255) DEFAULT NULL,
  `fecha_envio` date DEFAULT NULL,
  `fecha_recepcion` date DEFAULT NULL,
  `FK_pedidoE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_envio`),
  KEY `FK_envio_FK_pedidoE` (`FK_pedidoE`),
  CONSTRAINT `FK_envio_FK_pedidoE` FOREIGN KEY (`FK_pedidoE`) REFERENCES `pedido` (`id_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `envio`
--

LOCK TABLES `envio` WRITE;
/*!40000 ALTER TABLE `envio` DISABLE KEYS */;
/*!40000 ALTER TABLE `envio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `id_pedido` bigint(20) NOT NULL,
  `confirmacion_cliente` tinyint(1) DEFAULT '0',
  `destinatario` varchar(150) DEFAULT NULL,
  `fecha_pedido` date DEFAULT NULL,
  `importe` double DEFAULT NULL,
  `lugar_entrega` varchar(150) DEFAULT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL,
  `cliente` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `FK_pedido_cliente` (`cliente`),
  CONSTRAINT `FK_pedido_cliente` FOREIGN KEY (`cliente`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto` (
  `id_producto` bigint(20) NOT NULL,
  `alergenos` varchar(500) DEFAULT NULL,
  `descripcion_corta` varchar(100) DEFAULT NULL,
  `cantidad_suministro` int(11) DEFAULT NULL,
  `cond_conservacion` varchar(500) DEFAULT NULL,
  `controles` varchar(500) DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `estadoProducto` tinyint(1) DEFAULT '0',
  `nombre` varchar(50) DEFAULT NULL,
  `foto` varchar(200) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `tipoIVA` int(11) DEFAULT NULL,
  `unidad` varchar(10) DEFAULT NULL,
  `unidad_suministro` varchar(20) DEFAULT NULL,
  `tienda` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `FK_producto_tienda` (`tienda`),
  CONSTRAINT `FK_producto_tienda` FOREIGN KEY (`tienda`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (150,'N/A','La dorada (Esparus Aurata), muy consumida y valorada dentro de la gastronomía española.',400,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C','Ha pasado los controles determinados por la CE','La dorada, de la familia de los espáridos, es un pez que se caracteriza por tener un cuerpo alto, ovalado y comprimido, con una larga aleta dorsal. Cabeza alta, compacta y con frente muy arqueada o convexa. Ojo pequeño y mejillas con escamas, que llegan hasta debajo del ojo y hasta la parte posterior de la nuca por encima de la cabeza. Labios gruesos, boca baja con una dentadura poderosa preparada para triturar moluscos.',1,'Dorada','dorada.jpg',3.96,20,21,'Kilo','gramos',8),(151,'N/A','La trucha (Oncorhynchus Mykiss) es un pescado semigraso de carne muy sabrosa',300,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C','Ha pasado los controles determinados por la CE','La trucha, Oncorhynchus mykiss, de la familia de los salmónidos, tiene su origen —a nivel comercial— en la acuicultura. Este pescado es de forma fusiforme, cuerpo alargado comprimido, con tronco caudal alto. Lo más característico y diferencial respecto a otros afines es una banda de colores irisados en verde, rojo y azul, «arco iris», situada a lo largo de cada lado del cuerpo, que es la que le confiere su nombre, más perceptible con el efecto del agua y el sol.',1,'Trucha','trucha.jpg',3.16,20,21,'Kilo','gramos',8),(152,'N/A','El salmón (Salmo Salar) es un pescado azul muy demandado',7,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C','Ha pasado los controles determinados por la CE','El salmón de la familia de los salmónidos, es un habitante de aguas frías, dulces y saladas, buen nadador, de cuerpo cubierto por escamas, poderosa musculatura y voraz, que se alimenta de crustáceos y otros peces. Dispone de una gran boca, aunque no llega a alcanzar la vertical del centro del ojo, con fuertes dientes.',1,'Salmón','salmon.jpg',76.3,20,21,'Kilo','kilo',8),(153,'N/A','El calamar (Loligo Reynaud) es un producto que está presente en la cultura gastronómica de España',500,'Mantener a una temperatura entre 0° y 4°.  Producto descongelado, no volver a congelar.','Ha pasado los controles determinados por la CE','El calamar se caracteriza por su cuerpo musculoso, alargado con forma de cilindro comprimido y afilado, terminado con dos aletas triangulares negras, a modo de torpedo. Formado por dos regiones: la cabeza, cercana a los brazos, donde se hallan los ojos con párpados transparentes y la boca; y el manto, donde se aloja la «pluma» o esqueleto de naturaleza córnea, indicio de la concha de otros cefalópodos, que fortalece al animal y participa en su movimiento.',1,'Calamar','calamar.jpg',7.3,20,21,'Kilo','gramos',8),(154,'N/A','La palometa, también conocida como japuta, es uno de los pescados mas abundantes y consumidos',900,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','La japuta o palometa negra, con nombre científico Brama brama, es un pez que pertenece a la familia de los brámidos, dentro del orden de los Perciformes. Con cuerpo de altura moderada y comprimido lateralmente. Su boca es grande y oblicua. Sus ojos, saltones. De color gris plomizo, casi negro.',1,'Palometa','palometa.jpg',11.88,20,21,'Kilo','gramos',8),(155,'N/A','Al rodaballo se le considera como uno de los mejores y más sobrosos pescados.',1200,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','El rodaballo, Scophtalmus maximus, de la familia de los escoftálmidos, es un pez plano, pero más grueso que los lenguados. Forma casi circular. Cabeza relativamente grande. Ojos sobre el lado izquierdo, mira a la izquierda, relativamente alejados al aparecer una cresta ósea entre ambos. Boca grande y mandíbula inferior prominente con dientes pequeños y puntiagudos.',1,'Rodaballo','rodaballo.jpg',23.66,20,21,'Kilo','gramos',8),(156,'N/A','El rape es un pescado blanco que destaca por su gran cantidad de proteínas y pocas grasas.',1300,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','El rape común o rape blanco es un pez sin espinas  y de color pardo jaspeado en tonos violáceos o rojizos, según la especie. La forma irregular de su cuerpo, junto con el color de su piel, le permiten mimetizarse en su medio.',1,'Rape Negro','rapeNegro.jpg',18.06,20,21,'Kilo','gramos',8),(157,'N/A','Es muy valorado en la gastronomía por su carne blanca, compacta y de sabor extraordinario.',700,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','El cuerpo es de color rosa intenso en el dorso, y va palideciendo hacia la parte ventral. Los flancos presentan 5 o 6 anchas bandas transversales más oscuras y de contorno irregular. La aleta dorsal presenta una mancha oscura, que no siempre es conspicua, situada sobre los últimos radios.',1,'Cabra','cabra.jpg',8.07,20,21,'Kilo','gramos',8),(160,'N/A','La dorada (Esparus Aurata), muy consumida y valorada dentro de la gastronomía española.',400,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C','Ha pasado los controles determinados por la CE','La dorada, de la familia de los espáridos, es un pez que se caracteriza por tener un cuerpo alto, ovalado y comprimido, con una larga aleta dorsal. Cabeza alta, compacta y con frente muy arqueada o convexa. Ojo pequeño y mejillas con escamas, que llegan hasta debajo del ojo y hasta la parte posterior de la nuca por encima de la cabeza. Labios gruesos, boca baja con una dentadura poderosa preparada para triturar moluscos.',1,'Dorada','dorada.jpg',3.96,20,21,'Kilo','gramos',10),(161,'N/A','La trucha (Oncorhynchus Mykiss) es un pescado semigraso de carne muy sabrosa',300,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C','Ha pasado los controles determinados por la CE','La trucha, Oncorhynchus mykiss, de la familia de los salmónidos, tiene su origen —a nivel comercial— en la acuicultura. Este pescado es de forma fusiforme, cuerpo alargado comprimido, con tronco caudal alto. Lo más característico y diferencial respecto a otros afines es una banda de colores irisados en verde, rojo y azul, «arco iris», situada a lo largo de cada lado del cuerpo, que es la que le confiere su nombre, más perceptible con el efecto del agua y el sol.',1,'Trucha','trucha.jpg',3.16,20,21,'Kilo','gramos',10),(162,'N/A','El salmón (Salmo Salar) es un pescado azul muy demandado',7,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C','Ha pasado los controles determinados por la CE','El salmón de la familia de los salmónidos, es un habitante de aguas frías, dulces y saladas, buen nadador, de cuerpo cubierto por escamas, poderosa musculatura y voraz, que se alimenta de crustáceos y otros peces. Dispone de una gran boca, aunque no llega a alcanzar la vertical del centro del ojo, con fuertes dientes.',1,'Salmón','salmon.jpg',76.3,20,21,'Kilo','kilo',10),(163,'N/A','El calamar (Loligo Reynaud) es un producto que está presente en la cultura gastronómica de España',500,'Mantener a una temperatura entre 0° y 4°.  Producto descongelado, no volver a congelar.','Ha pasado los controles determinados por la CE','El calamar se caracteriza por su cuerpo musculoso, alargado con forma de cilindro comprimido y afilado, terminado con dos aletas triangulares negras, a modo de torpedo. Formado por dos regiones: la cabeza, cercana a los brazos, donde se hallan los ojos con párpados transparentes y la boca; y el manto, donde se aloja la «pluma» o esqueleto de naturaleza córnea, indicio de la concha de otros cefalópodos, que fortalece al animal y participa en su movimiento.',1,'Calamar','calamar.jpg',7.3,20,21,'Kilo','gramos',10),(164,'N/A','La palometa, también conocida como japuta, es uno de los pescados mas abundantes y consumidos',900,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','La japuta o palometa negra, con nombre científico Brama brama, es un pez que pertenece a la familia de los brámidos, dentro del orden de los Perciformes. Con cuerpo de altura moderada y comprimido lateralmente. Su boca es grande y oblicua. Sus ojos, saltones. De color gris plomizo, casi negro.',1,'Palometa','palometa.jpg',11.88,20,21,'Kilo','gramos',10),(165,'N/A','Al rodaballo se le considera como uno de los mejores y más sobrosos pescados.',1200,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','El rodaballo, Scophtalmus maximus, de la familia de los escoftálmidos, es un pez plano, pero más grueso que los lenguados. Forma casi circular. Cabeza relativamente grande. Ojos sobre el lado izquierdo, mira a la izquierda, relativamente alejados al aparecer una cresta ósea entre ambos. Boca grande y mandíbula inferior prominente con dientes pequeños y puntiagudos.',1,'Rodaballo','rodaballo.jpg',23.66,20,21,'Kilo','gramos',10),(172,'N/A','El salmón (Salmo Salar) es un pescado azul muy demandado',7,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C','Ha pasado los controles determinados por la CE','El salmón de la familia de los salmónidos, es un habitante de aguas frías, dulces y saladas, buen nadador, de cuerpo cubierto por escamas, poderosa musculatura y voraz, que se alimenta de crustáceos y otros peces. Dispone de una gran boca, aunque no llega a alcanzar la vertical del centro del ojo, con fuertes dientes.',1,'Salmón','salmon.jpg',76.3,20,21,'Kilo','kilo',15),(173,'N/A','El calamar (Loligo Reynaud) es un producto que está presente en la cultura gastronómica de España',500,'Mantener a una temperatura entre 0° y 4°.  Producto descongelado, no volver a congelar.','Ha pasado los controles determinados por la CE','El calamar se caracteriza por su cuerpo musculoso, alargado con forma de cilindro comprimido y afilado, terminado con dos aletas triangulares negras, a modo de torpedo. Formado por dos regiones: la cabeza, cercana a los brazos, donde se hallan los ojos con párpados transparentes y la boca; y el manto, donde se aloja la «pluma» o esqueleto de naturaleza córnea, indicio de la concha de otros cefalópodos, que fortalece al animal y participa en su movimiento.',1,'Calamar','calamar.jpg',7.3,20,21,'Kilo','gramos',15),(174,'N/A','La palometa, también conocida como japuta, es uno de los pescados mas abundantes y consumidos',900,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','La japuta o palometa negra, con nombre científico Brama brama, es un pez que pertenece a la familia de los brámidos, dentro del orden de los Perciformes. Con cuerpo de altura moderada y comprimido lateralmente. Su boca es grande y oblicua. Sus ojos, saltones. De color gris plomizo, casi negro.',1,'Palometa','palometa.jpg',11.88,20,21,'Kilo','gramos',15),(175,'N/A','Al rodaballo se le considera como uno de los mejores y más sobrosos pescados.',1200,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','El rodaballo, Scophtalmus maximus, de la familia de los escoftálmidos, es un pez plano, pero más grueso que los lenguados. Forma casi circular. Cabeza relativamente grande. Ojos sobre el lado izquierdo, mira a la izquierda, relativamente alejados al aparecer una cresta ósea entre ambos. Boca grande y mandíbula inferior prominente con dientes pequeños y puntiagudos.',1,'Rodaballo','rodaballo.jpg',23.66,20,21,'Kilo','gramos',15),(176,'N/A','El rape es un pescado blanco que destaca por su gran cantidad de proteínas y pocas grasas.',1300,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','El rape común o rape blanco es un pez sin espinas  y de color pardo jaspeado en tonos violáceos o rojizos, según la especie. La forma irregular de su cuerpo, junto con el color de su piel, le permiten mimetizarse en su medio.',1,'Rape Negro','rapeNegro.jpg',18.06,20,21,'Kilo','gramos',15),(177,'N/A','Es muy valorado en la gastronomía por su carne blanca, compacta y de sabor extraordinario.',700,'El pescado fresco siempre ha de mantenerse a una temperatura de entre 0 y 4º C.','Ha pasado los controles determinados por la CE','El cuerpo es de color rosa intenso en el dorso, y va palideciendo hacia la parte ventral. Los flancos presentan 5 o 6 anchas bandas transversales más oscuras y de contorno irregular. La aleta dorsal presenta una mancha oscura, que no siempre es conspicua, situada sobre los últimos radios.',1,'Cabra','cabra.jpg',8.07,20,21,'Kilo','gramos',15),(181,'N/A','La chuleta es uno de los cortes más apreciados del cerdo',500,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Con un precio asequible, sabrosa y tierna, permite preparar platos consistentes y muy fáciles de cocinar.',1,'Chuletas de cerdo blanco','chuletaCerdoBlanco.jpg',3.25,20,21,'Kilo','gramos',9),(182,'N/A','El solomillo es una pieza de carne procedente de la parte lumbar.',400,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Consta de varias partes: la cabeza, el rosario, el centro y la punta.',1,'Solomillo de cerdo blanco','solomilloCerdo.jpg',3.71,20,21,'Kilo','gramos',9),(183,'N/A','También se suele llamar presa a la bola ibérica, la parte más tierna de la aguja.',600,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Es una de las piezas más apreciadas del cerdo ibérico por su jugosidad e infiltración de grasa.',1,'Presa iberica','presaIberica.jpg',10.29,20,21,'Kilo','gramos',9),(184,'N/A','El redondo de ternera es una especie de lomo, situado en la parte alta de las patas traseras.',2,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Carne tierna, su característica forma la define: redonda, sin nervios y con poca grasa, procedente de los cuartos traseros. Es una pieza ideal para asados, guisos, mechados',1,'Redondo de ternera','redondoDeTernera.jpg',10.54,20,21,'Kilo','kilo',9),(185,'N/A','Una de las mejores piezas de la ternera para comer a la brasa o plancha.',400,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Hembras de vacuno de origen español alimentadas a base de cereales un mínimo de 6 meses.',1,'Entrecot de ternera','entrecot-de-ternera.jpg',5.54,20,21,'Kilo','gramos',9),(186,'N/A','Churrasco de ternera',950,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Despiece que se obtiene de la falda de la ternera cortada transversalmente y no siguiendo la forma de la costilla.',1,'Churrasco de ternera','churrasco.jpg',4.9,20,21,'Kilo','gramos',9),(187,'N/A','Cuartos traseros de pollo',250,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','N/A ',1,'Cuartos traseros de pollo','muslo-de-pollo-traseros.jpg',1.95,20,21,'Kilo','gramos',9),(188,'N/A','Pechuga de pollo entera',380,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','N/A ',1,'Pechuga de pollo','pechuga-de-pollo-entera.jpg',2.01,20,21,'Kilo','gramos',9),(189,'N/A','Pollo entero',2,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','N/A ',1,'Pollo entero','pollos-enteros.jpg',1.71,20,21,'Kilo','kilo',9),(192,'N/A','El solomillo es una pieza de carne procedente de la parte lumbar.',400,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Consta de varias partes: la cabeza, el rosario, el centro y la punta.',1,'Solomillo de cerdo blanco','solomilloCerdo.jpg',3.71,20,21,'Kilo','gramos',12),(193,'N/A','También se suele llamar presa a la bola ibérica, la parte más tierna de la aguja.',600,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Es una de las piezas más apreciadas del cerdo ibérico por su jugosidad e infiltración de grasa.',1,'Presa iberica','presaIberica.jpg',10.29,20,21,'Kilo','gramos',12),(194,'N/A','El redondo de ternera es una especie de lomo, situado en la parte alta de las patas traseras.',2,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Carne tierna, su característica forma la define: redonda, sin nervios y con poca grasa, procedente de los cuartos traseros. Es una pieza ideal para asados, guisos, mechados',1,'Redondo de ternera','redondoDeTernera.jpg',10.54,20,21,'Kilo','kilo',12),(195,'N/A','Una de las mejores piezas de la ternera para comer a la brasa o plancha.',400,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Hembras de vacuno de origen español alimentadas a base de cereales un mínimo de 6 meses.',1,'Entrecot de ternera','entrecot-de-ternera.jpg',5.54,20,21,'Kilo','gramos',12),(196,'N/A','Churrasco de ternera',950,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','Despiece que se obtiene de la falda de la ternera cortada transversalmente y no siguiendo la forma de la costilla.',1,'Churrasco de ternera','churrasco.jpg',4.9,20,21,'Kilo','gramos',12),(197,'N/A','Cuartos traseros de pollo',250,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','N/A ',1,'Cuartos traseros de pollo','muslo-de-pollo-traseros.jpg',1.95,20,21,'Kilo','gramos',12),(198,'N/A','Pechuga de pollo entera',380,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','N/A ',1,'Pechuga de pollo','pechuga-de-pollo-entera.jpg',2.01,20,21,'Kilo','gramos',12),(199,'N/A','Pollo entero',2,'La carne siempre ha de mantenerse a una temperatura de entre -1 y 2º C.','Ha pasado los controles determinados por la CE','N/A ',1,'Pollo entero','pollos-enteros.jpg',1.71,20,21,'Kilo','kilo',12),(201,'N/A','Una de las tartas más famosas de nuestro obrador.',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Delicioso pastel tierno y jugoso, que combina sabores mediterráneos como la zanahoria, la piña, las nueces y la canela y otras especias.',1,'Tarta de zanahoria','tartaZanahoria.jpeg',20,6,21,'Unidad','unidad',11),(202,'N/A','Una refrescante tarta.',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Conjuga el delicioso bizcocho de limón con la confitura de mora, todo ello cubierto de frosting y finas láminas de almendras',1,'Tarta de limon y moras','tartaLimon.jpeg',20,6,21,'Unidad','unidad',11),(203,'N/A','Tarta Red Velvet.',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Un poco de fantasía roja, ya que el color rojo del bizcocho es lo que caracteriza esta tarta, además de su exquisita jugosidad. Con un sabor delicado a vainilla, y cubiertos con una crema de queso blanco que intensifica su color y sabor.',1,'Tarta Red Velvet','tartaRedVelvet.jpeg',20,6,21,'Unidad','unidad',11),(204,'N/A','La delicadeza y exquisitez de esta tarta hace de ella una de las favoritas de nuestros clientes.',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Es un suave bizcocho en el que la calabaza endulza y da un original sabor a esta tarta cuyas capas se rellenan con nuestro Frosting que no contiene mantequilla, es delicioso y con una textura que acompaña en perfecta armonía el bizcocho.',1,'Tarta de Calabaza','tartaCalabaza.jpeg',20,6,21,'Unidad','unidad',11),(205,'N/A','Ideal para tomar templado con un poco de frosting o helado.1kg aprox',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Sin duda un dulce que crea adictos, su jugoso relleno con manzana, pera, nueces, pasas maceradas en ron y Cointreau, albaricoque y ciruelas se envuelve con una trenza de exquisito hojaldre.',1,'Strudel de Manzana y Pera','strudel.jpeg',20,6,21,'Unidad','unidad',11),(206,'N/A','Ideal para tomar en el desayuno o en la merienda',380,'Mantener en frio','Ha pasado los controles determinados por la CE','Sin duda un dulce que crea adictos.',1,'Palmera XXL de chocolate','palmeraChocolate.webp',6,8,21,'Kilo','kilo',11),(210,'N/A','Tarta Red Velvet.',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Un poco de fantasía roja, ya que el color rojo del bizcocho es lo que caracteriza esta tarta, además de su exquisita jugosidad. Con un sabor delicado a vainilla, y cubiertos con una crema de queso blanco que intensifica su color y sabor.',1,'Tarta Red Velvet','tartaRedVelvet.jpeg',20,6,21,'Unidad','unidad',16),(211,'N/A','La delicadeza y exquisitez de esta tarta hace de ella una de las favoritas de nuestros clientes.',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Es un suave bizcocho en el que la calabaza endulza y da un original sabor a esta tarta cuyas capas se rellenan con nuestro Frosting que no contiene mantequilla, es delicioso y con una textura que acompaña en perfecta armonía el bizcocho.',1,'Tarta de Calabaza','tartaCalabaza.jpeg',20,6,21,'Unidad','unidad',16),(212,'N/A','Ideal para tomar templado con un poco de frosting o helado.1kg aprox',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Sin duda un dulce que crea adictos, su jugoso relleno con manzana, pera, nueces, pasas maceradas en ron y Cointreau, albaricoque y ciruelas se envuelve con una trenza de exquisito hojaldre.',1,'Strudel de Manzana y Pera','strudel.jpeg',20,6,21,'Unidad','unidad',16),(213,'N/A','Ideal para tomar en el desayuno o en la merienda',380,'Mantener en frio','Ha pasado los controles determinados por la CE','Sin duda un dulce que crea adictos.',1,'Palmera XXL de chocolate','palmeraChocolate.webp',6,8,21,'Kilo','kilo',16),(220,'N/A','Pieza de 600grs',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Gracias a su gran riqueza en nutrientes, la espelta es un gran aliado para nuestra salud, apoyando de forma saludable sus funciones en el sistema cardiovascular.',1,'Pan Espelta','Pan-espelta-artesano.webp',4.5,10,21,'Unidad','unidad',13),(221,'N/A','Pieza de 1kg',1,'Mantener en frio','Ha pasado los controles determinados por la CE','El pan de centeno está hecho con harina de centeno y mezclado con un poco de trigo.',1,'Pan de Centeno','pan-de-centeno-puro.jpg',2.5,10,21,'Unidad','unidad',13),(223,'N/A','Pieza de 1kg',1,'Mantener en frio','Ha pasado los controles determinados por la CE','Pan tradicional de trigo con moño, elaborado con una masa de hidratación muy alta.',1,'Pan de Centeno','pan-de-trigo.jpg',2.5,10,21,'Unidad','unidad',13),(230,'N/A','Staedtler Noris 120 Lápiz de grafito',1,'N/A','Dispone de marcado CE','Staedtler Noris 120 Lápiz de grafito, mina HB, cuerpo hexagonal amarillo y negro.',1,'Lápiz','lapiz.jpg',0.36,50,21,'Unidad','unidad',14),(231,'N/A','BIC Cristal',1,'N/A','Dispone de marcado CE','BIC Cristal Original Bolígrafo de punta de bola, punta mediana de 1 mm, cuerpo translúcido, tinta azul.',1,'Bolígrafo','boliBIC.jpg',0.2,50,21,'Unidad','unidad',14),(232,'N/A','Tapa de polipropileno flexible de 800 micras.',1,'N/A','Dispone de marcado CE','Esta agenda día página de espiral de la gama Dohe Syncro le permitirá organizar su día a día fácilmente dado que es un complemento imprescindible en su mesa o bolso para su trabajo diario.',1,'Agenda 2021','agenda.jpg',7.49,50,21,'Unidad','unidad',14),(233,'N/A','Cuaderno formato folio.',1,'N/A','Dispone de marcado CE','Tapa blanda de cartón con esquinas redondeadas. Espiral simple. Papel ecológico PEFC de 60 g/m²',1,'Cuaderno','cuaderno.jpg',1.49,50,21,'Unidad','unidad',14);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subPedido`
--

DROP TABLE IF EXISTS `subPedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subPedido` (
  `id_subPedido` bigint(20) NOT NULL,
  `cantidad_producto` int(11) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `pedido` bigint(20) DEFAULT NULL,
  `producto` bigint(20) DEFAULT NULL,
  `tienda` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_subPedido`),
  KEY `FK_subPedido_producto` (`producto`),
  KEY `FK_subPedido_pedido` (`pedido`),
  KEY `FK_subPedido_tienda` (`tienda`),
  CONSTRAINT `FK_subPedido_pedido` FOREIGN KEY (`pedido`) REFERENCES `pedido` (`id_pedido`),
  CONSTRAINT `FK_subPedido_producto` FOREIGN KEY (`producto`) REFERENCES `producto` (`id_producto`),
  CONSTRAINT `FK_subPedido_tienda` FOREIGN KEY (`tienda`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subPedido`
--

LOCK TABLES `subPedido` WRITE;
/*!40000 ALTER TABLE `subPedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `subPedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tienda`
--

DROP TABLE IF EXISTS `tienda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tienda` (
  `usuarioID` bigint(20) NOT NULL,
  `aceptada` tinyint(1) DEFAULT '0',
  `cif` varchar(10) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `nombre` varchar(150) DEFAULT NULL,
  `nombreAvatar` varchar(200) DEFAULT NULL,
  `responsable` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`usuarioID`),
  UNIQUE KEY `UNQ_tienda_0` (`cif`),
  CONSTRAINT `FK_tienda_usuarioID` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tienda`
--

LOCK TABLES `tienda` WRITE;
/*!40000 ALTER TABLE `tienda` DISABLE KEYS */;
INSERT INTO `tienda` VALUES (8,1,'H11111111','Pescaderia fundada en 1920. Venta de pescado y mariscos','Pescaderia Manolo','test.jpg','Manolo García'),(9,1,'H22222222','Productos cárnicos naturales','Caniceria H. Jurado','test.jpg','José Jurado'),(10,1,'H33333333','Pescados, mariscos y ahumados','Pescaderia Gutierrez','test.jpg','Guty'),(11,1,'H44444444','La pasteleria profesional a tu alcance','Pasteleria El Azucar','test.jpg','Nataliza Sanchez'),(12,1,'H55555555','Especialistas en carne de vacuno','Carniceria Martinez','test.jpg','Alfredo Martinez'),(13,1,'H66666666','Pan freco todos los dias','Panaderia Gemelas','test.jpg','Laura'),(14,0,'H77777777','Papeleria y libreria. Expertos en material escolar','Papeleria PETRUS','test.jpg','Jose Petrus'),(15,0,'H88888888','El pescado al mejor precio','Pescaderia La Gamba','test.jpg','Jose Morente'),(16,0,'H99999999','Pasteleria casera','Pasteleria San Bartolomé','test.jpg','Tony Stark'),(17,0,'H12121212','Expertos en ferretería, herramientas, artículos para el hogar y jardín','Ferreteria Perez','test.jpg','Tomas Perez');
/*!40000 ALTER TABLE `tienda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_usuario` bigint(20) NOT NULL,
  `DTYPE` varchar(31) DEFAULT NULL,
  `barrio` varchar(200) DEFAULT NULL,
  `cp` varchar(10) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nombre_usuario` varchar(20) DEFAULT NULL,
  `numeroVia` varchar(5) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `tipoVIA` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UNQ_usuario_0` (`nombre_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Administrador',NULL,NULL,NULL,NULL,'admin',NULL,'8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',NULL,NULL),(2,'Cliente','AEROPUERTO VIEJO','41001','ABAD GORDILLO','cliente01@cliente01.com','a','1','ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb','666999000','CALLE'),(3,'Cliente','ALFALFA','51001','ABAD GORDILLO','cliente02@cliente02.com','b','2','3e23e8160039594a33894f6564e1b1348bbd7a0088d42c4acb73eeaed59c009d','666999000','CALLE'),(4,'Cliente','AEROPUERTO VIEJO','61001','ABAD GORDILLO','cliente03@cliente03.com','c','3','2e7d2c03a9507ae265ecf5b5356885a53393a2029d241394997265a1a25aefc6','666999000','CALLE'),(5,'Cliente','ALFALFA','41001','ABAD GORDILLO','cliente04@cliente04.com','d','1','18ac3e7343f016890c510e93f935261169d9e3f565436429830faf0934f4f8e4','666999000','CALLE'),(6,'Cliente','AMATE','41001','ABAD GORDILLO','cliente05@cliente05.com','e','1','3f79bb7b435b05321651daefd374cdc681dc06faa65e374e38337b88ca046dea','666999000','CALLE'),(7,'Cliente','AMATE','41001','ABAD GORDILLO','cliente06@cliente06.com','f','1','252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111','666999000','CALLE'),(8,'Tienda','AEROPUERTO VIEJO','41001','ABEDUL','tienda01@tienda01','z','1','594e519ae499312b29433b7dd8a97ff068defcba9755b6d5d00e84c524d67b06','555111111','CALLE'),(9,'Tienda','ALFALFA','41002','HIPERION','tienda02@tienda02','x','1','2d711642b726b04401627ca9fbac32f5c8530fb1903cc4db02258717921a4881','555111111','PASEO'),(10,'Tienda','AEROPUERTO VIEJO','41003','TEBA','tienda03@tienda03','y','1','a1fce4363854ff888cff4b8e7875d600c2682390412a8cf79b37d0b11148b0fa','555111111','CALLE'),(11,'Tienda','AEROPUERTO VIEJO','41004','TENIENTE RODRIGUEZ CARMONA','tienda04@tienda04','w','1','50e721e49c013f00c62cf59f2163542a9d8df02464efeb615d31051b0fddc326','555111111','CALLE'),(12,'Tienda','AEROPUERTO VIEJO','41005','LUIS HUIDOBRO','tienda05@tienda05','v','1','4c94485e0c21ae6c41ce1dfe7b6bfaceea5ab68e40a2476f50208e526f506080','555111111','CALLE'),(13,'Tienda','ALFALFA','41006','JESUS DE LA REDENCION','tienda06@tienda06','u','1','0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6','555111111','PLAZA'),(14,'Tienda','AMATE','41001','JOAQUIN GUICHOT','tienda07@tienda07','t','1','e3b98a4da31a127d4bde6e43033f66ba274cab0eb7eb1c70ec41402bf6273dd8','555111111','CALLE'),(15,'Tienda','AMATE','41001','JOSE BERMEJO','tienda08@tienda08','s','1','043a718774c572bd8a25adbeb1bfcd5c0256ae11cecf9f9c3f925d0e52beaf89','555111111','CALLE'),(16,'Tienda','AMATE','41001','MADRE DE CRISTO','tienda09@tienda09','r','1','454349e422f05297191ead13e21d3db520e5abef52055e4964b82fb213f593a1','555111111','CALLE'),(17,'Tienda','AMATE','41001','DELEITE','tienda10@tienda10','q','1','8e35c2cd3bf6641bdb0e2050b76932cbb2e6034a0ddacc1d9bea82a6ba57f7cf','555111111','CALLE');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-25 16:45:50
