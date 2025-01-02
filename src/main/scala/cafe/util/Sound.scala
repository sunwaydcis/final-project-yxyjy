package cafe.util

import scalafx.scene.media.{Media, MediaPlayer}

import java.io.File

object Sound:
  private val clickSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/click.mp3").toURI.toString))
  private val popSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/pop.mp3").toURI.toString))
  private val kachingSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/kaching.mp3").toURI.toString))
  private val welcomeSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/welcome.mp3").toURI.toString))
  private val correctSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/correct.mp3").toURI.toString))
  private val bgm: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/bgm.mp3").toURI.toString))

  def playClickSound(): Unit =
    try
      clickSound.stop()
      clickSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def playPopSound(): Unit =
    try
      popSound.stop()
      popSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def playKachingSound(): Unit =
    try
      kachingSound.stop()
      kachingSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def playWelcomeSound(): Unit =
    try
      welcomeSound.stop()
      welcomeSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def playCorrectSound(): Unit =
    try
      correctSound.stop()
      correctSound.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def playBgm(): Unit =
    try
      bgm.cycleCount = 99
      bgm.play()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")

  def stopBgm(): Unit =
    try
      bgm.stop()
    catch
      case e: Exception =>
        println(s"Error playing sound: ${e.getMessage}")
