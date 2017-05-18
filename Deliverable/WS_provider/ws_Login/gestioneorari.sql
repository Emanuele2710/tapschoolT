-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 16, 2017 alle 10:18
-- Versione del server: 10.1.21-MariaDB
-- Versione PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestioneorari`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `classi`
--

CREATE TABLE `classi` (
  `ID` int(11) NOT NULL,
  `classe` int(1) NOT NULL,
  `sezione` char(1) NOT NULL,
  `indirizzo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `classi`
--

INSERT INTO `classi` (`ID`, `classe`, `sezione`, `indirizzo`) VALUES
(1, 1, 'a', 'informatica'),
(2, 1, 'b', 'informatica'),
(3, 1, 'c', 'informatica'),
(4, 2, 'a', 'informatica'),
(5, 2, 'b', 'informatica'),
(6, 2, 'c', 'informatica'),
(7, 1, 'a', 'chimico'),
(8, 1, 'b', 'chimico'),
(9, 1, 'c', 'chimico'),
(10, 2, 'a', 'chimico');

-- --------------------------------------------------------

--
-- Struttura della tabella `eventi`
--

CREATE TABLE `eventi` (
  `ID_Evento` int(11) NOT NULL,
  `data` date NOT NULL,
  `ora` time NOT NULL,
  `note` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `hannoprof`
--

CREATE TABLE `hannoprof` (
  `ID_Professore` int(11) NOT NULL,
  `ID_Evento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `hannostud`
--

CREATE TABLE `hannostud` (
  `ID_Studente` int(11) NOT NULL,
  `ID_Evento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `insegnano`
--

CREATE TABLE `insegnano` (
  `ID_Professore` int(11) NOT NULL,
  `ID_Materia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `materie`
--

CREATE TABLE `materie` (
  `ID` int(11) NOT NULL,
  `nome` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `materie`
--

INSERT INTO `materie` (`ID`, `nome`) VALUES
(1, 'informatica'),
(2, 'matematica'),
(3, 'chimica'),
(4, 'telcomunicazioni'),
(5, 'gestione'),
(6, 'storia'),
(7, 'scienze');

-- --------------------------------------------------------

--
-- Struttura della tabella `professori`
--

CREATE TABLE `professori` (
  `ID` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `cognome` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` char(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `studenti`
--

CREATE TABLE `studenti` (
  `ID` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `cognome` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` char(32) NOT NULL,
  `ID_Classe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `tengono`
--

CREATE TABLE `tengono` (
  `ID_Professore` int(11) NOT NULL,
  `ID_Studente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `classi`
--
ALTER TABLE `classi`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `eventi`
--
ALTER TABLE `eventi`
  ADD PRIMARY KEY (`ID_Evento`);

--
-- Indici per le tabelle `hannoprof`
--
ALTER TABLE `hannoprof`
  ADD PRIMARY KEY (`ID_Professore`,`ID_Evento`),
  ADD KEY `ID_Evento` (`ID_Evento`);

--
-- Indici per le tabelle `hannostud`
--
ALTER TABLE `hannostud`
  ADD PRIMARY KEY (`ID_Studente`,`ID_Evento`),
  ADD KEY `ID_Evento` (`ID_Evento`);

--
-- Indici per le tabelle `insegnano`
--
ALTER TABLE `insegnano`
  ADD PRIMARY KEY (`ID_Professore`,`ID_Materia`),
  ADD KEY `ID_Materia` (`ID_Materia`);

--
-- Indici per le tabelle `materie`
--
ALTER TABLE `materie`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `professori`
--
ALTER TABLE `professori`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `studenti`
--
ALTER TABLE `studenti`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Classe` (`ID_Classe`);

--
-- Indici per le tabelle `tengono`
--
ALTER TABLE `tengono`
  ADD PRIMARY KEY (`ID_Professore`,`ID_Studente`),
  ADD KEY `ID_Studente` (`ID_Studente`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `classi`
--
ALTER TABLE `classi`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT per la tabella `eventi`
--
ALTER TABLE `eventi`
  MODIFY `ID_Evento` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `materie`
--
ALTER TABLE `materie`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT per la tabella `professori`
--
ALTER TABLE `professori`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `studenti`
--
ALTER TABLE `studenti`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `hannoprof`
--
ALTER TABLE `hannoprof`
  ADD CONSTRAINT `hannoprof_ibfk_1` FOREIGN KEY (`ID_Professore`) REFERENCES `professori` (`ID`),
  ADD CONSTRAINT `hannoprof_ibfk_2` FOREIGN KEY (`ID_Evento`) REFERENCES `eventi` (`ID_Evento`);

--
-- Limiti per la tabella `hannostud`
--
ALTER TABLE `hannostud`
  ADD CONSTRAINT `hannostud_ibfk_1` FOREIGN KEY (`ID_Studente`) REFERENCES `studenti` (`ID`),
  ADD CONSTRAINT `hannostud_ibfk_2` FOREIGN KEY (`ID_Evento`) REFERENCES `eventi` (`ID_Evento`);

--
-- Limiti per la tabella `insegnano`
--
ALTER TABLE `insegnano`
  ADD CONSTRAINT `insegnano_ibfk_1` FOREIGN KEY (`ID_Professore`) REFERENCES `professori` (`ID`),
  ADD CONSTRAINT `insegnano_ibfk_2` FOREIGN KEY (`ID_Materia`) REFERENCES `materie` (`ID`);

--
-- Limiti per la tabella `studenti`
--
ALTER TABLE `studenti`
  ADD CONSTRAINT `studenti_ibfk_1` FOREIGN KEY (`ID_Classe`) REFERENCES `classi` (`ID`);

--
-- Limiti per la tabella `tengono`
--
ALTER TABLE `tengono`
  ADD CONSTRAINT `tengono_ibfk_1` FOREIGN KEY (`ID_Professore`) REFERENCES `professori` (`ID`),
  ADD CONSTRAINT `tengono_ibfk_2` FOREIGN KEY (`ID_Studente`) REFERENCES `studenti` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
