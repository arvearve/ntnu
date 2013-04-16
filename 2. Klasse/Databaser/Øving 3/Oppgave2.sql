SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `Poststed`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Poststed` (
  `postnr` INT NOT NULL ,
  `poststed` VARCHAR(45) NULL ,
  PRIMARY KEY (`postnr`) ,
  UNIQUE INDEX `postnr_UNIQUE` (`postnr` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Kunde`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Kunde` (
  `kundenr` INT NOT NULL AUTO_INCREMENT ,
  `navn` VARCHAR(45) NOT NULL ,
  `kredittgrense` INT NOT NULL COMMENT '\n	' ,
  `postnr` INT NOT NULL ,
  PRIMARY KEY (`kundenr`) ,
  INDEX `kunde_poststed_idx` (`postnr` ASC) ,
  CONSTRAINT `kunde_poststed`
    FOREIGN KEY (`postnr` )
    REFERENCES `Poststed` (`postnr` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Artikkel`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Artikkel` (
  `artnr` INT NOT NULL AUTO_INCREMENT ,
  `navn` VARCHAR(45) NOT NULL ,
  `ant` INT NOT NULL ,
  `pris` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`artnr`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Bestilling`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Bestilling` (
  `artnr` INT NOT NULL ,
  `kundenr` INT NOT NULL ,
  `kvantum` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`artnr`, `kundenr`) ,
  INDEX `artnr_idx` (`artnr` ASC) ,
  INDEX `kundnr_idx` (`kundenr` ASC) ,
  CONSTRAINT `artnr`
    FOREIGN KEY (`artnr` )
    REFERENCES `Artikkel` (`artnr` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `kundnr`
    FOREIGN KEY (`kundenr` )
    REFERENCES `Kunde` (`kundenr` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `mydb` ;
USE `mydb`;

DELIMITER $$

DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
