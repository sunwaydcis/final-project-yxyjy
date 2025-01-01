package cafe.util

import scalafx.scene.media.{Media, MediaPlayer}

import java.io.File

object Sound:
  val clickSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/click.mp3").toURI.toString))
  val popSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/pop.mp3").toURI.toString))
  val kachingSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/kaching.mp3").toURI.toString))
  val welcomeSound: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/welcome.mp3").toURI.toString))
  val bgm: MediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/cafe.media/bgm.mp3").toURI.toString))

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
