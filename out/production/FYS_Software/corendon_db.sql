-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Machine: sql7.freemysqlhosting.net
-- Genereertijd: 06 feb 2017 om 11:28
-- Serverversie: 5.5.49-0ubuntu0.14.04.1
-- PHP-versie: 5.3.28

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `sql7145943`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_airports`
--

CREATE TABLE IF NOT EXISTS `cn_airports` (
  `airportId` int(11) NOT NULL AUTO_INCREMENT,
  `airportName` varchar(255) NOT NULL,
  `airportCode` varchar(5) NOT NULL,
  `airportCountryCode` varchar(5) NOT NULL,
  PRIMARY KEY (`airportId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=43 ;

--
-- Gegevens worden uitgevoerd voor tabel `cn_airports`
--

INSERT INTO `cn_airports` (`airportId`, `airportName`, `airportCode`, `airportCountryCode`) VALUES
(0, '-', '---', '--'),
(1, 'Eindhoven', 'EIN', 'NL'),
(2, 'Rotterdam', 'RTM', 'NL'),
(3, 'Brussels Airport', 'BRU', 'BE'),
(4, 'Brussels Midi Stn', 'ZYR', 'BE'),
(5, 'Brussels S Charleroi', 'CRL', 'BE'),
(6, 'Brussels Airport International', 'BRU', 'BE'),
(7, 'Dresden', 'DRS', 'DE'),
(8, 'Erfurt-Weimar', 'ERF', 'DE'),
(9, 'Frankfurt Intl', 'FRA', 'DE'),
(10, 'Friedrichshafen', 'FDH', 'DE'),
(11, 'Hannover', 'HAJ', 'DE'),
(12, 'Karlsruhe Bus Station', 'XET', 'DE'),
(13, 'Leipzig', 'LEJ', 'DE'),
(14, 'Leipzig Hbf Rail Stn', 'XIT', 'DE'),
(15, 'Munich International', 'MUC', 'DE'),
(16, 'Stuttgart', 'SGT', 'US'),
(17, 'Stuttgart Airport', 'STR', 'DE'),
(18, 'Nicosia', 'NIC', 'CY'),
(19, 'Kosice', 'KSC', 'SK'),
(20, 'Kosipe', 'KSP', 'PG'),
(21, 'Kostanay', 'KSN', 'KZ'),
(22, 'Kostroma', 'KMW', 'RU'),
(23, 'Nikos Kazantzakis', 'HER', 'GR'),
(24, 'Oshkosh', 'OKS', 'US'),
(25, 'Governador Valadares', 'GVR', 'BR'),
(26, 'Eloy Alfaro Intl', 'MEC', 'EC'),
(27, 'Faro', 'ZFA', 'CA'),
(28, 'Fuerteventura', 'FUE', 'ES'),
(29, 'Gran Canaria', 'LPA', 'ES'),
(30, 'Ibiza', 'IBZ', 'ES'),
(31, 'Lanzarote', 'ACE', 'ES'),
(32, 'Malaga Airport', 'AGP', 'ES'),
(33, 'Malaga Railway Stn', 'YJM', 'ES'),
(34, 'Tenerife Norte', 'TFN', 'ES'),
(35, 'Tenerife Sur', 'TFS', 'ES'),
(36, 'Antalya', 'AYT', 'TR'),
(37, 'Dalaman', 'DLM', 'TR'),
(38, 'Konya', 'KYA', 'TR'),
(39, 'Dubai Creek Spb', 'DCG', 'AE'),
(40, 'Dubai Intl', 'DXB', 'AE'),
(41, 'Zurich Airport', 'ZRH', 'CH'),
(42, 'Schiphol Airport', 'AMS', 'NL');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_log`
--

CREATE TABLE IF NOT EXISTS `cn_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOG_TYPE` varchar(10) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `LOG_MESSAGE` text NOT NULL,
  `LOG_DATE` datetime NOT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=99 ;

--
-- Gegevens worden uitgevoerd voor tabel `cn_log`
--

INSERT INTO `cn_log` (`LOG_ID`, `LOG_TYPE`, `USER_ID`, `LOG_MESSAGE`, `LOG_DATE`) VALUES
(1, 'NORMAL', 4, 'LOGIN', '2017-01-17 11:57:43'),
(2, 'NORMAL', 3, 'LOGIN', '2017-01-17 12:01:54'),
(3, 'NORMAL', 3, 'LOGIN', '2017-01-17 12:02:04'),
(4, 'NORMAL', 1, 'LOGIN', '2017-01-17 12:03:06'),
(5, 'NORMAL', 1, 'LOGIN', '2017-01-17 12:03:48'),
(6, 'NORMAL', 4, 'LOGIN', '2017-01-17 12:53:06'),
(7, 'NORMAL', 3, 'LOGIN', '2017-01-17 12:53:28'),
(8, 'NORMAL', 1, 'LOGIN', '2017-01-17 12:57:51'),
(9, 'NORMAL', 5, 'LOGIN', '2017-01-17 12:58:43'),
(10, 'NORMAL', 1, 'LOGIN', '2017-01-17 12:59:05'),
(11, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:00:58'),
(12, 'NORMAL', 5, 'LOGIN', '2017-01-17 13:01:13'),
(13, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:02:19'),
(14, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:03:09'),
(15, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:03:43'),
(16, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:04:06'),
(17, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:06:19'),
(18, 'NORMAL', 5, 'LOGIN', '2017-01-17 13:06:26'),
(19, 'NORMAL', 5, 'Updated user: 1', '2017-01-17 13:06:55'),
(20, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:07:31'),
(21, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:18:39'),
(22, 'NORMAL', 1, 'Password Reset Requested', '2017-01-17 13:19:18'),
(23, 'NORMAL', 0, 'Changed the password with reset code', '2017-01-17 13:20:24'),
(24, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:20:28'),
(25, 'NORMAL', 5, 'LOGIN', '2017-01-17 13:30:47'),
(26, 'NORMAL', 5, 'Updated user: 4', '2017-01-17 13:33:50'),
(27, 'NORMAL', 5, 'LOGIN', '2017-01-17 13:34:12'),
(28, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:36:48'),
(29, 'NORMAL', 5, 'LOGIN', '2017-01-17 13:52:51'),
(30, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:56:59'),
(31, 'NORMAL', 1, 'Password Reset Requested', '2017-01-17 13:57:16'),
(32, 'NORMAL', 0, 'Changed the password with reset code', '2017-01-17 13:58:02'),
(33, 'NORMAL', 1, 'LOGIN', '2017-01-17 13:58:06'),
(34, 'NORMAL', 1, 'LOGIN', '2017-01-17 14:01:23'),
(35, 'NORMAL', 5, 'LOGIN', '2017-01-17 14:01:55'),
(36, 'NORMAL', 1, 'LOGIN', '2017-01-18 12:57:21'),
(37, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:07:04'),
(38, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:15:33'),
(39, 'NORMAL', 1, 'Deleted Luggage ID: 52', '2017-01-18 13:16:38'),
(40, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:20:09'),
(41, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:20:55'),
(42, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:24:05'),
(43, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:24:41'),
(44, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:25:02'),
(45, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:27:03'),
(46, 'NORMAL', 1, 'Edited Luggage ID: 50', '2017-01-18 13:27:18'),
(47, 'NORMAL', 1, 'Edited Luggage ID: 51', '2017-01-18 13:27:59'),
(48, 'NORMAL', 1, 'Edited Luggage ID: 51', '2017-01-18 13:28:07'),
(49, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:37:37'),
(50, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:38:16'),
(51, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:38:34'),
(52, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:39:17'),
(53, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:39:39'),
(54, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:39:54'),
(55, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:39:59'),
(56, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:48:13'),
(57, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:48:24'),
(58, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:49:36'),
(59, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:50:36'),
(60, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:50:47'),
(61, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:51:47'),
(62, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:52:30'),
(63, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:53:19'),
(64, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:58:26'),
(65, 'NORMAL', 1, 'LOGIN', '2017-01-18 13:59:45'),
(66, 'NORMAL', 1, 'LOGIN', '2017-01-18 14:01:20'),
(67, 'NORMAL', 1, 'LOGIN', '2017-01-18 14:02:53'),
(68, 'NORMAL', 1, 'LOGIN', '2017-01-18 14:04:07'),
(69, 'NORMAL', 1, 'LOGIN', '2017-01-18 15:40:32'),
(70, 'NORMAL', 1, 'LOGIN', '2017-01-18 16:05:35'),
(71, 'NORMAL', 1, 'Added Luggage: 54', '2017-01-18 16:06:19'),
(72, 'NORMAL', 1, 'LOGIN', '2017-01-18 16:50:44'),
(73, 'NORMAL', 5, 'LOGIN', '2017-01-18 16:51:41'),
(74, 'NORMAL', 1, 'LOGIN', '2017-01-18 17:48:45'),
(75, 'NORMAL', 1, 'LOGIN', '2017-01-30 14:47:22'),
(76, 'NORMAL', 1, 'LOGIN', '2017-01-30 14:51:37'),
(77, 'NORMAL', 1, 'LOGIN', '2017-01-30 15:03:18'),
(78, 'NORMAL', 1, 'LOGIN', '2017-01-31 11:26:16'),
(79, 'NORMAL', 1, 'LOGIN', '2017-01-31 12:13:00'),
(80, 'NORMAL', 3, 'LOGIN', '2017-02-01 12:01:31'),
(81, 'NORMAL', 1, 'LOGIN', '2017-02-01 12:03:03'),
(82, 'NORMAL', 3, 'LOGIN', '2017-02-01 12:03:15'),
(83, 'NORMAL', 5, 'LOGIN', '2017-02-01 12:03:23'),
(84, 'NORMAL', 5, 'LOGIN', '2017-02-01 12:03:33'),
(85, 'NORMAL', 1, 'LOGIN', '2017-02-01 21:49:12'),
(86, 'NORMAL', 3, 'LOGIN', '2017-02-01 21:50:12'),
(87, 'NORMAL', 1, 'LOGIN', '2017-02-01 21:51:24'),
(88, 'NORMAL', 1, 'LOGIN', '2017-02-01 21:52:04'),
(89, 'NORMAL', 1, 'LOGIN', '2017-02-01 21:53:07'),
(90, 'NORMAL', 1, 'LOGIN', '2017-02-01 21:53:34'),
(91, 'NORMAL', 1, 'LOGIN', '2017-02-01 22:14:31'),
(92, 'NORMAL', 1, 'LOGIN', '2017-02-01 22:28:22'),
(93, 'NORMAL', 1, 'LOGIN', '2017-02-01 22:28:56'),
(94, 'NORMAL', 1, 'LOGIN', '2017-02-01 22:32:03'),
(95, 'NORMAL', 1, 'LOGIN', '2017-02-01 22:38:15'),
(96, 'NORMAL', 1, 'LOGIN', '2017-02-06 12:27:03'),
(97, 'NORMAL', 1, 'LOGIN', '2017-02-06 12:27:46'),
(98, 'NORMAL', 5, 'LOGIN', '2017-02-06 12:28:03');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_luggage`
--

CREATE TABLE IF NOT EXISTS `cn_luggage` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LABELNR` varchar(255) DEFAULT NULL,
  `FLIGHTNR` varchar(255) DEFAULT NULL,
  `DESTINATION` int(11) DEFAULT NULL,
  `TRAVELER_ID` int(11) DEFAULT NULL,
  `DEPARTURE_ID` int(11) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `COLOR` text,
  `BRAND` varchar(255) DEFAULT NULL,
  `WEIGHT_CLASS` int(11) DEFAULT NULL,
  `STATE` int(11) NOT NULL DEFAULT '0',
  `LDATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

--
-- Gegevens worden uitgevoerd voor tabel `cn_luggage`
--

INSERT INTO `cn_luggage` (`ID`, `LABELNR`, `FLIGHTNR`, `DESTINATION`, `TRAVELER_ID`, `DEPARTURE_ID`, `TYPE`, `COLOR`, `BRAND`, `WEIGHT_CLASS`, `STATE`, `LDATE`) VALUES
(47, '5648', '4594864', 36, 2, 3, 'Bag', 'Red', 'Nike', 20, 0, '2017-01-17'),
(49, '48945', 'HV456486', 7, 4, 37, 'Shoes', 'Pink', 'Adidas', 10, 0, '2017-01-05'),
(53, '123HE43', '666-HEL', 39, 0, 31, 'Handbag', 'Orange', 'Versace', 30, 2, '2017-01-29'),
(54, '5648', '', 3, 0, 0, 'bag', 'red', '', 0, 2, '2017-01-18');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_matchedluggage`
--

CREATE TABLE IF NOT EXISTS `cn_matchedluggage` (
  `matchedId` int(11) NOT NULL AUTO_INCREMENT,
  `lostId` int(11) NOT NULL,
  `foundId` int(11) NOT NULL,
  `matchedState` varchar(25) DEFAULT NULL,
  `matchedDate` date NOT NULL,
  PRIMARY KEY (`matchedId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Gegevens worden uitgevoerd voor tabel `cn_matchedluggage`
--

INSERT INTO `cn_matchedluggage` (`matchedId`, `lostId`, `foundId`, `matchedState`, `matchedDate`) VALUES
(4, 47, 54, 'SEND_TEXT', '2017-01-18');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_options`
--

CREATE TABLE IF NOT EXISTS `cn_options` (
  `OPTION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OPTION_NAME` text NOT NULL,
  `OPTION_VALUE_EN` text NOT NULL,
  `OPTION_VALUE_TR` text NOT NULL,
  `OPTION_VALUE_SP` text NOT NULL,
  `OPTION_VALUE_NL` text NOT NULL,
  PRIMARY KEY (`OPTION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=118 ;

--
-- Gegevens worden uitgevoerd voor tabel `cn_options`
--

INSERT INTO `cn_options` (`OPTION_ID`, `OPTION_NAME`, `OPTION_VALUE_EN`, `OPTION_VALUE_TR`, `OPTION_VALUE_SP`, `OPTION_VALUE_NL`) VALUES
(1, 'USERNAME_TEXT', 'Username', 'Kullanici adi', 'Nombre de usuario', 'Gebruikersnaam'),
(2, 'PASSWORD_TEXT', 'Password', 'Sifre', 'Contrasena', 'Wachtwoord'),
(3, 'LOGIN_TEXT', 'Login', 'Giris', 'Iniciar Sesion', 'Inloggen'),
(4, 'INCORRECT_LOGIN', 'Entered username or password is incorrect !', 'Girilen kullanici adi veya sifre yanlis !', 'Nombre de usuario o contrasena son incorrectas!', 'Gebruikersnaam / Wachtwoord incorrect !'),
(5, 'LOGOUT_TEXT', 'Logout', 'Cikis', 'Cerrar Sesion', 'Uitloggen'),
(6, 'LOST_LUGGAGE_TEXT', 'Luggage', 'Bagaj', 'Perdido', 'Bagage'),
(7, 'SETTINGS_TEXT', 'Settings', 'Ayarlar', 'Ajustes', 'Instellingen'),
(8, 'PROFILE_TEXT', 'Profile', 'Profil', 'Perfil', 'Profiel'),
(9, 'CONTRIBUTORS_TEXT', 'Contributors', 'Calisanlar', 'Contribuidores\n', 'Werknemers'),
(10, 'STATISTICS_TEXT', 'Statistics', 'Istatistikler', 'Estadistica', 'Statistieken'),
(11, 'TRAVELER_TEXT', 'Travelers', 'Yolcular', 'Viajeros', 'Reizigers'),
(12, 'MATCHES_TEXT', 'Matched Luggage', 'Muhtemel Bulunan', 'Equipaje combinado', 'Gematchte Bagage'),
(13, 'FORGOT_PASSWORD_TEXT', 'Forgot my password', 'Sifremi unuttum', 'Olvidé mi contraseña\n', 'Ik weet mijn wachtwoord niet'),
(14, 'SEND_PASSWORD_TEXT', 'Send Email', 'Mail Yolla', 'Enviar correo electrónico\n', 'Stuur een email'),
(15, 'EMAIL_TEXT', 'Fill your email here...', 'Mail adresinizi giriniz...', 'Llene su correo electronico aquí', 'Voer hier uw email in'),
(16, 'GO_LOGIN_TEXT', 'Go back to login', 'Giris', 'Volver a iniciar sesion', 'Ga terug naar het inlogscherm'),
(17, 'ERROR_TEXT', 'Error', 'Hata', 'Error', 'Fout'),
(18, 'ERROR_EMPTY_EMAIL_TEXT', 'Email address is invalid or empty !', 'Mail adresiniz hatali !', '\nLa dirección de correo electrónico no es válida o está vacía.', 'Email-adres is leeg of incorrect !'),
(19, 'EMAIL_NOT_FOUND_TEXT', 'Your email is not found in our system !', 'Mail adresiniz bulunamadi !', '\nTu correo electronico no se encuentra en nuestro sistema', 'Email adres is niet gevonden in het systeem'),
(20, 'EMAIL_DISABLED_TEXT', 'Your account has been disabled, please contact your manager !', 'Hesabiniz kilitlendi, yetkiliye bildiriniz !', 'Su cuenta ha sido desactivada, por favor contacte a su manager!\n', 'Uw account is uitgeschakeld, neem contact op met uw beheerder'),
(21, 'EMAIL_SEND_TEXT', 'Check your inbox. We''ve sent you a temporary password', 'Mailinizi kontrol ediniz, Gecici bir sifre yollanmistir', 'Revisa tu correo. Te hemos enviado una contraseña temporal.\n', 'Controleer uw inbox, we hebben u en bericht gestuurd'),
(22, 'PASSWORD_RESET_TEXT', 'Password Reset Code', 'Gecici Sifre', 'Codigo de restablecimiento de contraseña', 'Tijdelijk Wachtwoord'),
(23, 'PASSWORD_RESET_MESSAGE_TEXT', 'This is your temporary password|| Account ID: #ACC#||\nTemp Password: #PASS#', 'Istek uzere sifrenizi sifirlamak icin gecici sifre:||\nHesap: #ACC#||\nGecici Sifre: #PASS#', '#ACC#||#PASS#', 'Dit is uw tijdelijk wachtwoord:|| Account ID: #ACC#||Wachtwoord: #PASS#'),
(24, 'CLOSE_PROGRAM_TEXT', 'Close Program', 'Programi Kapat', 'Cerrar programa\n', 'Programma afsluiten'),
(25, 'ADD_TEXT', 'Add', 'Yeni', 'Anadir', 'Toevoegen'),
(26, 'REGISTER_TEXT', 'Register', 'Kayit olmak', 'Registro', 'Registreren'),
(27, 'SEARCH_TXT', 'Search', 'Arama', 'Buscar', 'Zoeken'),
(28, 'EDIT_TEXT', 'Edit', 'Düzenle', 'Editar', 'Wijzigen'),
(29, 'SAVE_TEXT', 'Save', 'Kaydet', 'Salvar', 'Opslaan'),
(30, 'RESET_PASS_HEADER', 'Enter new password', 'Yeni sifre giriniz', 'Introduzca una nueva contraseña', 'Voer nieuwe wachtwoord in'),
(31, 'NEW_PASSWORD_TEXT', 'Your new password', 'Yeni sifreniz', 'Tu nueva contraseña\n', 'Uw nieuwe wachtwoord'),
(32, 'REPEAT_NEW_PASSWORD_TEXT', 'Repeat your new password', 'Sifrenizi yeniden giriniz', 'Repite tu nueva contraseña\n', 'Herhaal nieuwe wachtwoord'),
(33, 'SAVE_PASSWORD_TEXT', 'Save password', 'Sifremi Kaydet', 'guardar contraseña\n', 'Sla wachtwoord op'),
(34, 'BROWSE_TEXT', 'Browse', 'Gozat', 'Vistazo\r\n', 'Bladeren'),
(35, 'ADVANCED_SEARCH_TEXT', 'Advanced search', 'Gelismis Arama', 'Busqueda Avanzada', 'Geavanceerd Zoeken'),
(36, 'INCORRECT_PASSWORD_LENGTH_TEXT', 'Your password length must be longer than 6 characters', 'Sifreniz 6 veya daha fazla harftan olusmasi lazim', 'La longitud de la contraseña debe tener más de 6 caracteres\n', 'Wachtwoord moet minstens 6 tekens bevatten'),
(37, 'INCORRECT_PASSWORD_SAME_TEXT', 'Your password is not the same as the confirm password', 'Sifreniz ve yeniden sifreniz tutmuyor', 'Su contraseña no es la misma que la contraseña de confirmación\n', 'Wachtwoord en herhaal wachtwoord komen niet overeen !'),
(38, 'AMOUNT_TEST', 'test', 'moemfiom', 'dmwim', 'wmdiwmwim'),
(39, 'BACK_TEXT', 'Back', 'Geri', 'Espalda', 'Terug'),
(40, 'TYPE_OF_CLOSURE_TEXT', 'Type of closure', '..', 'Tipo de cierre', '...'),
(41, 'CHOOSE_A_LUGGAGE_TYPE_TEXT', 'Choose a luggage type', 'Bagaj tipi secin', 'Elegir un tipo de equipaje', 'Bagage Type'),
(42, 'AMOUNT_OF_WHEELS_TEXT', 'Amount of wheels', '..', 'Cantidad de ruedas', '...'),
(43, 'CHOOSE_A_COLOUR_TEXT', 'Choose a colour', 'Bir renk seçin', 'Elija un color', 'Kies een kleur'),
(44, 'CHOOSE_A_BRAND_TEXT', 'Choose a brand', 'Bir marka seçin', 'Elija una marca', 'Kies een merk'),
(45, 'CHOOSE_A_WEIGHT_TEXT', 'Choose a weight', '..', 'Elija un peso', 'Kies een gewicht'),
(46, 'CLOSE_ADVANCED_SEARCH_TEXT', 'Close advanced search', '..', 'Cerrar busqueda avanzada', ''),
(47, 'LUCHTHAVEN_TEXT', 'Airport', 'Havalimani', 'Aeropuerto', 'Luchthaven'),
(48, 'PERIODE_TEXT', 'Period', 'donem', 'Periodo', 'Periode'),
(49, 'FIRST_NAME_LABEL', 'First name', 'Adi', 'Nombre de pila\n', 'Voornaam'),
(50, 'LAST_NAME_LABEL', 'Last name', 'Soy adi', 'Apellido\n', 'Achternaam'),
(51, 'DATE_OF_BIRTH_LABEL', 'Date of birth', 'Dogum Tarihi', 'Fecha de nacimiento\n', 'Geboorte datum'),
(52, 'ZIP_CODE_LABEL', 'Zip Code', 'Posta Kodu', 'Código postal\n', 'Postcode'),
(53, 'CITY_LABEL', 'City', 'Sehir', 'Ciudad\n', 'Stad'),
(54, 'COUNTRY_LABEL', 'Country', 'Ulke', 'País\n', 'Land'),
(55, 'MAIL_ADDRESS_LABEL', 'Email address', 'Mail adresi', 'Dirección de correo electrónico\n', 'Email adres'),
(56, 'PHONE_NUMBER_LABEL', 'Phone number', 'Telefon numarasi', 'Número de teléfono\n', 'Mobielnummer'),
(57, 'USER_REGISTRATION_LABEL', 'User Registration', 'Kullanici Ekeleme', 'Registro de usuario\n', 'Gebruiker Registreren'),
(58, 'ADDRESS_LABEL', 'Address', 'Adres', '..', 'Adres'),
(59, 'SEARCH_CONTRIBUTOR_LABEL', 'Search Contributor', 'Isci Ara', 'Colaborador de la búsqueda\n', 'Zoek medewerker'),
(60, 'DATE_LABEL', 'Date', 'Tarih', 'Fecha\n', 'Datum'),
(61, 'TIME_LABEL', 'Time', 'Saat', 'Tiempo', 'Tijd'),
(62, 'LOST_AND_FOUND_ID_LABEL', 'LOST AND FOUND ID', 'LOST FOUND ID', '..', 'LOST AND FOUND ID'),
(63, 'LUGGAGE_INFORMATION_LABEL', '..', '..', '..', '...'),
(64, 'LUGGAGE_TYPE_LABEL', 'Luggage type', 'Bagaj Tipi', 'Tipo de equipaje\n', 'Bagage Type'),
(65, 'LUGGAGE_BRAND_LABEL', 'Luggage brand', 'Bagaj Markasi', 'Marca de equipaje\n', 'Bagage Merk'),
(66, 'LUGGAGE_COLOUR_LABEL', 'Luggage color', 'Bagaj Rengi', 'Color del equipaje\n', 'Bagage Kleur'),
(67, 'AMOUNT_OF_WHEELS_LABEL', 'Amount of wheels', 'Teker adeti', 'Cantidad de ruedas\n', 'Aantal wielen'),
(68, 'WEIGHT_CLASS_LABEL', 'Weight Class', 'Agirlilik sinifi', 'Categoría de peso\n', 'Gewichtsklasse'),
(69, 'TYPE_OF_CLOSURE_LABEL', '..', '..', '..', '...'),
(70, 'LUGGAGE_LABEL_INFORMATION_LABEL', '..', '..', '..', '...'),
(71, 'LABEL_NUMBER_LABEL', 'Label number', 'Etiket numarasi', 'Número de etiqueta\n', 'Labelnummer'),
(72, 'FLIGHT_NUMBER_LABEL', 'Flight number', 'Ucus numarasi', 'Número de vuelo\n', 'Vluchtnummer'),
(73, 'DESTINATION_LABEL', 'Destination', 'Varis yeri', 'Destino\n', 'Bestemming'),
(74, 'TRAVELLER_NAME_LABEL', 'Traveler Name', 'Yolcu Ismi', 'Nombre del viajero\n', 'Reizigersnaam'),
(75, 'NAME_TEXT', 'Name', 'Isim', 'Nombre\n', 'Naam'),
(76, 'LAST_LOGIN_TEXT', 'Last Login', 'Son Giris', 'Último acceso\n', 'Laatst Ingelogd'),
(77, 'CREATED_TEXT', 'Account Created Date', 'Hesap Acilis Tarihi', 'Cantidad creada fecha\n', 'Account aangemaakt op'),
(78, 'CANCEL_BACK_TEXT', 'Cancel', 'Iptal', 'Cancelar\n', 'Annuleren'),
(79, 'SEARCH_TEXT', 'Search', 'Ara', 'Buscar\n', 'Zoeken'),
(80, 'PASSWORD_SAVED_TEXT', 'Password Saved !', 'Sifreniz kaydedilmistir', '¡Contraseña guardada!\n', 'Wachtwoord opgeslagen'),
(81, 'MESSAGE_TEXT', 'Message', 'Mesaj', 'Mensaje\n', 'Bericht'),
(82, 'PROGRAM_NAME', 'Corendon Luggage System', 'Corendon Bagaj Sistemi', 'Sistema de equipaje Corendon\n', 'Corendon Bagage Systeem'),
(83, 'DEPARTURE_TEXT', 'Departure', 'Kalkis Yeri', 'Salida\n', 'Vertrek'),
(84, 'COLOR_TEXT', 'Color', 'Renk', 'Color', 'Kleur'),
(85, 'STATE_TEXT', 'State', 'Durum', 'Estado\n', 'Status'),
(86, 'Lost', 'Lost', 'Kayip', 'Perdida', 'Verloren'),
(87, 'Found', 'Found', 'Bulundu', 'Fundar', 'Gevonden'),
(88, 'COLOR_TYPE_REQUIRED', 'Color,Type, Destination, State and Date is required !', 'Bagaj rengi, tipi, varis yeri, tarih ve durum giriniz', 'Color, Tipo, Destino, Estado y Fecha es requerido!', 'Bagage kleur, type, bestemming, datum en status is verplicht !'),
(89, 'ADD_LUGGAGE', 'Add Luggage', 'Bagaj Ekle', 'Añadir equipaje\n', 'Bagage Toevoegen'),
(90, 'DELETE_TEXT', 'Delete', 'Sil', 'Borrar\n', 'Verwijderen'),
(91, 'RESET_TEXT', 'Reset', 'Sifirla', 'Reiniciar\n', 'Reset'),
(92, 'EDIT_TRAVELER_TEXT', 'Edit Traveler', 'Yolcu Bilgileri Degistir', 'Editar viajero\n', 'Wijzig Reiziger'),
(93, 'ALL_FIELD_ARE_REQUIRED_TEXT', 'All fields are required !', 'Lutfen butun sorulan bilgileri giriniz !', 'Todos los campos son obligatorios !\n', 'Alle velden zijn verplicht in te vullen !'),
(94, 'REQUIRED_TEXT', 'Required', 'Zorunlu', 'Necesario\n', 'Verplicht'),
(95, 'SELECT_OPTION_TEXT', 'Select an option', 'Seciniz', 'Seleccione una opcion\n', 'Kies een optie'),
(96, 'KEYWORD_TEXT', 'Keyword', 'Arama Kelimesi', 'Palabra clave\n', 'Zoekwoord'),
(97, 'LEVEL_TEXT', 'User Level', 'Kullanici Tipi', 'Nivel de usuario\n', 'Rol'),
(98, 'MANAGER_TEXT', 'Manager', 'Menajer', 'Gerente', 'Manager'),
(99, 'EMPLOYEE_TEXT', 'Employee', 'Calisan', 'Empleado\n', 'Medewerker'),
(100, 'ACTIVE_TEXT', 'Active', 'Aktif', 'Activo', 'Actief'),
(101, 'DISABLED_TEXT', 'Disabled', 'Erisime Kapali', 'Discapacitado\n', 'Geblokkeerd'),
(102, 'MEN_TEXT', 'Man', 'Bay', 'Hombre', 'Man'),
(103, 'WOMAN_TEXT', 'Woman', 'Bayan', 'Mujer', 'Vrouw'),
(104, 'USER_EDIT_TEXT', 'Edit User', 'Kullanici Bilgileri Degistirme', 'Editar usuario\n', 'Wijzig Gebruiker'),
(105, 'CREATED_DATE_TEXT', 'Created on', 'Hesap Acilis Tarihi', 'Creado en\n', 'Account aangemaakt op'),
(106, 'GENDER_TEXT', 'Gender', 'Cinsiyet', 'Sexo', 'Geslacht'),
(107, 'STAR_FIELDS_REQUIRED', 'Fields with a star are required !', 'Yildizli bolgeleri doldurunuz !', 'Los campos que lleven una estrella son obligatorios!\n', 'Velden met een * zijn verplicht !'),
(108, 'NOTHING_SELECTED_TEXT', 'Please select a row', 'Lutfen seciniz', 'Seleccione una fila\n', 'Selecteer een optie !'),
(109, 'MATCHED_TEXT', 'Matched', 'Eslesti', 'Apareado\n', 'Gematched'),
(110, 'SEND_TEXT', 'Sent', 'Geri yollandi', 'Expedido\n', 'Verstuurd'),
(111, 'EXPORT_TEXT', 'Export PDF', 'PDF Cikart', 'Exportar PDF\n', 'Exporteer PDF'),
(112, 'ALL_AIRPORTS_TEXT', 'All Airports', 'Tum Havalimanlari', 'Todos los aeropuertos\n', 'Alle Luchthavens'),
(113, 'DEAR_TEXT', 'Dear', 'Sayin', 'Querido', 'Beste'),
(114, 'LOST_LUG_DET_TEXT', 'In this email you''ll find your lost luggage details', 'Bu mailin icind kayip bagajinizin bilgileri vardir', 'En este correo electrónico encontrarás los detalles de tu equipaje perdido', 'In deze email vind u de details van uw verloren bagage'),
(115, 'LOST_LUG_SUBJECT', 'Lost Luggage Registrated', 'Kayip Bagajiniz Isleme Girmistir', 'Equipaje perdido registrado', 'Verloren bagage is in behandeling genomen'),
(116, 'NO_RESULTS_TEXT', 'No information found', 'Bilgi bulunamadi !', 'No se encontró información', 'Geen informatie gevonden !'),
(117, 'ENGLISH_TXT', ' ', 'Kelimeleri Ingilizce dilinde giriniz', 'en Inglés!', 'in het Engels !');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_state`
--

CREATE TABLE IF NOT EXISTS `cn_state` (
  `STATE_ID` int(11) NOT NULL,
  `VALUE` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `cn_state`
--

INSERT INTO `cn_state` (`STATE_ID`, `VALUE`) VALUES
(0, 'Lost'),
(2, 'Found');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_traveler`
--

CREATE TABLE IF NOT EXISTS `cn_traveler` (
  `TID` int(11) NOT NULL AUTO_INCREMENT,
  `TNAME` varchar(255) NOT NULL,
  `TLASTNAME` varchar(255) NOT NULL,
  `TDATE_BIRTH` date NOT NULL,
  `TADDRESS` text NOT NULL,
  `TCITY` varchar(255) NOT NULL,
  `TCOUNTRY` varchar(255) NOT NULL,
  `TZIPCODE` varchar(255) NOT NULL,
  `TPHONE` varchar(255) NOT NULL,
  `TMAIL` varchar(255) NOT NULL,
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Gegevens worden uitgevoerd voor tabel `cn_traveler`
--

INSERT INTO `cn_traveler` (`TID`, `TNAME`, `TLASTNAME`, `TDATE_BIRTH`, `TADDRESS`, `TCITY`, `TCOUNTRY`, `TZIPCODE`, `TPHONE`, `TMAIL`) VALUES
(2, 'Tina', 'Feliciano', '1995-02-21', 'Passaic Street', 'Washington', 'United States', 'DC20036', '2022619508', 'TinaMFeliciano@dayrep.com'),
(3, 'Carla ', 'Fabiano', '1978-01-11', 'Charla Lane 1928 ', 'Mesquite', 'United States', 'TX75150', '972-686-8959', 'CarlaJFabiano@rhyta.com'),
(4, 'Dané', 'Cameron', '1966-02-27', 'De Holtmaden 147', 'Hoogeveen', 'Nederland', '7909CJ', '0656115355', 'DaneCameron@armyspy.com'),
(5, 'Job', 'Jongebloet', '1996-01-10', 'Weesperzijde 190', 'Amsterdam', 'Netherlands', '1061DH', '0654760890', 'job@hva.nl');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `cn_users`
--

CREATE TABLE IF NOT EXISTS `cn_users` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `GENDER` varchar(10) NOT NULL,
  `USERNAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `TEMP_PASSWORD` varchar(255) DEFAULT NULL,
  `MAIL` varchar(255) NOT NULL,
  `LEVEL` int(11) NOT NULL,
  `AIRPORT_ID` int(11) NOT NULL,
  `CREATED_DATE` date NOT NULL,
  `USER_STATE` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Gegevens worden uitgevoerd voor tabel `cn_users`
--

INSERT INTO `cn_users` (`USER_ID`, `NAME`, `GENDER`, `USERNAME`, `PASSWORD`, `TEMP_PASSWORD`, `MAIL`, `LEVEL`, `AIRPORT_ID`, `CREATED_DATE`, `USER_STATE`) VALUES
(1, 'Emin Torun', 'M', 'torunm001', 'FCEA920F7412B5DA7BE0CF42B8C93759', '', 'torundevelopment@gmail.com', 1, 42, '2016-10-12', 1),
(3, 'Danny Toorop', 'M', 'danny', 'b7bee6b36bd35b773132d4e3a74c2bb5', '', 'obinida@gmail.com', 1, 34, '2016-11-29', 1),
(4, 'Aschwin', 'M', 'aschwin', '7e34a8ef5e56da15c666d494eaf88ecc', '', 'ashhas.boy@gmail.com', 2, 34, '2016-11-29', 0),
(5, 'Minjon Michielse', 'V', 'minjon', 'a0d98ab3a2fd00c803cc95b56a6b8fb1', '', 'minjon_1997@hotmail.com', 2, 34, '2016-11-29', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
