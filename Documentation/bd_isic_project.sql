-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mar 12 Avril 2016 à 11:16
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `bd_isic_project`
--

-- --------------------------------------------------------

--
-- Structure de la table `assignation`
--

CREATE TABLE IF NOT EXISTS `assignation` (
  `assignation_id` int(11) NOT NULL AUTO_INCREMENT,
  `taux_engagment_projet` int(11) DEFAULT NULL,
  `assistant_id` int(11) NOT NULL,
  `projet_id` int(11) NOT NULL,
  PRIMARY KEY (`assignation_id`),
  KEY `assistant_id` (`assistant_id`),
  KEY `projet_id` (`projet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `assistant`
--

CREATE TABLE IF NOT EXISTS `assistant` (
  `assistant_id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `login` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(80) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `abreviation` varchar(3) NOT NULL,
  `domaine` varchar(30) NOT NULL,
  `fonction` varchar(50) NOT NULL,
  `nombre_heure_annuelle` int(11) NOT NULL,
  `taux_engagement` int(11) NOT NULL,
  `taux_enseignement` int(11) NOT NULL,
  `taux_taches_internes` int(11) NOT NULL,
  `groupe_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`assistant_id`),
  KEY `groupe_id` (`groupe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `assistant`
--

INSERT INTO `assistant` (`assistant_id`, `nom`, `prenom`, `login`, `password`, `email`, `adresse`, `abreviation`, `domaine`, `fonction`, `nombre_heure_annuelle`, `taux_engagement`, `taux_enseignement`, `taux_taches_internes`, `groupe_id`) VALUES
(3, 'Di Stasio', 'Leonardo', 'leonardo.distasio', '098f6bcd4621d373cade4e832627b4f6', 'yohsk10@gmail.com', 'Chemin Fin de là Outre', 'LDS', 'INF3 DLM', 'ADMIN', 1200, 50, 20, 10, 3),
(4, 'Assunçao', 'Jeshon', 'jeshon.assuncao', '098f6bcd4621d373cade4e832627b4f6', 'jeshon.assuncao@he-arc.ch', '', 'jas', 'INF3', 'USER', 1000, 70, 20, 0, 5);

-- --------------------------------------------------------

--
-- Structure de la table `groupe_competence`
--

CREATE TABLE IF NOT EXISTS `groupe_competence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acronyme` varchar(4) NOT NULL,
  `nom` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Contenu de la table `groupe_competence`
--

INSERT INTO `groupe_competence` (`id`, `acronyme`, `nom`) VALUES
(1, 'IDEa', 'Conception de produits centrée utilisateurs'),
(2, 'IDEb', 'Mécanique numérique'),
(3, 'IDEc', 'Conception des moyens de production'),
(4, 'IPEa', 'Procédés industriels'),
(5, 'IPEb', 'Métrologie et vision industrielle'),
(6, 'MICa', 'Ingénierie horlogère'),
(7, 'MICb', 'Electronique basse consommation'),
(8, 'MICc', 'Micro & nano-systèmes'),
(9, 'MICd', 'Ingénierie des surfaces'),
(10, 'TICa', 'Systèmes informatiques embarqués'),
(11, 'TICb', 'Technologies d''interaction'),
(12, 'TICc', 'Analyse de données'),
(13, 'TICd', 'Imagerie');

-- --------------------------------------------------------

--
-- Structure de la table `projet`
--

CREATE TABLE IF NOT EXISTS `projet` (
  `projet_id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `sagex` bigint(20) NOT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime DEFAULT NULL,
  `tarif_horaire_assistant` decimal(25,0) DEFAULT NULL,
  `tarif_horaire_adjoint` decimal(25,0) DEFAULT NULL,
  `budget` decimal(25,0) DEFAULT NULL,
  `chef` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`projet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `assignation`
--
ALTER TABLE `assignation`
  ADD CONSTRAINT `assignation_ibfk_1` FOREIGN KEY (`assistant_id`) REFERENCES `assistant` (`assistant_id`),
  ADD CONSTRAINT `assignation_ibfk_2` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`projet_id`);

--
-- Contraintes pour la table `assistant`
--
ALTER TABLE `assistant`
  ADD CONSTRAINT `assistant_ibfk_1` FOREIGN KEY (`groupe_id`) REFERENCES `groupe_competence` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
