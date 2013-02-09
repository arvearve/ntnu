SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Person`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Person` (
  `personNummer` INT NOT NULL ,
  `navn` VARCHAR(45) NOT NULL ,
  `adresse` VARCHAR(45) NOT NULL ,
  `f.dato` DATETIME NOT NULL ,
  `kjønn` VARCHAR(45) NOT NULL ,
  `tel` VARCHAR(45) NULL ,
  `rådgiverId` INT NULL ,
  PRIMARY KEY (`personNummer`) ,
  INDEX `Rådgiver_idx` (`rådgiverId` ASC) ,
  CONSTRAINT `Rådgiver`
    FOREIGN KEY (`rådgiverId` )
    REFERENCES `Person` (`personNummer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Kunde`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Kunde` (
  `kundenr` INT NOT NULL ,
  `navn` VARCHAR(45) NULL ,
  `addresse` VARCHAR(45) NULL ,
  PRIMARY KEY (`kundenr`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Oppdrag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Oppdrag` (
  `OppdragsNr` VARCHAR(45) NOT NULL ,
  `fradato` DATE NOT NULL ,
  `tildato` DATE NOT NULL ,
  `Stilling` VARCHAR(45) NOT NULL ,
  `lønn` INT NOT NULL ,
  `personId` INT NOT NULL ,
  `kundeId` INT NOT NULL ,
  INDEX `Rådgiver_idx` (`personId` ASC) ,
  INDEX `HarBestilt_idx` (`kundeId` ASC) ,
  PRIMARY KEY (`OppdragsNr`) ,
  CONSTRAINT `Rådgiver`
    FOREIGN KEY (`personId` )
    REFERENCES `Person` (`personNummer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `HarBestilt`
    FOREIGN KEY (`kundeId` )
    REFERENCES `Kunde` (`kundenr` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Kompetanse`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Kompetanse` (
  `kompetanseId` VARCHAR(45) NOT NULL ,
  `område` VARCHAR(45) NOT NULL ,
  `stilling` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`kompetanseId`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Utdanning`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Utdanning` (
  `UtdanningId` INT NOT NULL ,
  `Skole` VARCHAR(45) NOT NULL ,
  `Grad` VARCHAR(45) NOT NULL ,
  `kursnavn` VARCHAR(45) NOT NULL ,
  `antår` INT NULL ,
  PRIMARY KEY (`UtdanningId`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rel_person_utdanning`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `rel_person_utdanning` (
  `personnummer` INT NOT NULL ,
  `utdanningId` INT NOT NULL ,
  PRIMARY KEY (`personnummer`, `utdanningId`) ,
  INDEX `PersonHarUtdanning_idx` (`personnummer` ASC) ,
  INDEX `PersonHarUtdanning_idx1` (`utdanningId` ASC) ,
  CONSTRAINT `PersonHarUtdanning`
    FOREIGN KEY (`personnummer` )
    REFERENCES `Person` (`personNummer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PersonHarUtdanning`
    FOREIGN KEY (`utdanningId` )
    REFERENCES `Utdanning` (`UtdanningId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rel_person_kompetanse`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `rel_person_kompetanse` (
  `person` INT NOT NULL ,
  `kompetanse` VARCHAR(45) NOT NULL ,
  `grad` VARCHAR(45) NULL ,
  PRIMARY KEY (`person`, `kompetanse`) ,
  INDEX `person_idx` (`person` ASC) ,
  INDEX `kompetanse_idx` (`kompetanse` ASC) ,
  CONSTRAINT `person`
    FOREIGN KEY (`person` )
    REFERENCES `Person` (`personNummer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `kompetanse`
    FOREIGN KEY (`kompetanse` )
    REFERENCES `Kompetanse` (`kompetanseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
