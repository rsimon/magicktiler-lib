package org.pelagios.tools.geoparsing

import java.io.{ File, FileInputStream, PrintWriter }
import java.util.zip.GZIPInputStream
import javax.swing.JFileChooser
import scala.io.Source
import at.ait.dme.magicktiler.ptif.PTIFConverter
import at.ait.dme.magicktiler.zoomify.ZoomifyTiler
import at.ait.dme.magicktiler.tms.TMSTiler
import at.ait.dme.magicktiler.gmaps.GoogleMapsTiler
import at.ait.dme.magicktiler.image.ImageFormat

/**
 * Converts a plaintext file to CSV.
 * 
 * @author Rainer Simon <rainer.simon@ait.ac.at>
 */
object SimpleTiler extends App {
  
  val fileChooser = new FileChooser()
  val inputFile = fileChooser.getFile()
  
  if (inputFile.isEmpty) {
    println("Operation canceled by user.")
  } else {
    println("\n\n")
    
    val input = inputFile.get
    val images = 
      if (input.isDirectory) {
        println("Tiling a directory of images: " + input.getName)
        input.listFiles().toSeq
      } else {
        println("Tiling a single image: " + input.getName)
        Seq(input)
      }
    
    // Collect settings via CLI
    print("Choose target format: [Z]oomify (default), [P]TIF, [T]MS, [G]oogle Maps: ")
    val tiler = System.console.readLine.toLowerCase match {
      case "p" => new PTIFConverter()
      case "g" => new GoogleMapsTiler()
      case "t" => new TMSTiler()
      case _ => new ZoomifyTiler()
    }
    println()
    
    print("Tileformat [J]PEG (default) or [P]NG: ")
    val tileformat = System.console.readLine.toLowerCase match {
      case "p" => ImageFormat.PNG
      case _ => ImageFormat.JPEG 
    }
    tiler.setTileFormat(tileformat)
    println()
    
    if (tileformat == ImageFormat.JPEG) {
      print("Set JPEG compression quality (default = 95): ")
      val compression = try {
        System.console.readLine.toInt
      } catch {
        case _: Throwable => 95
      }
    }
    
    val tilesetInfo = images.map(image => {
      val destinationFolder = new File(image.getParent, image.getName.substring(0, image.getName.lastIndexOf(".")) + "_tiled")
      println("Writing result to " + destinationFolder.getAbsolutePath)
      tiler.convert(image, destinationFolder) 
    })
  }

}

class FileChooser {
  
  val chooser = new JFileChooser()
  chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES)
 
  def getFile(): Option[File] = {
    val result = chooser.showOpenDialog(null)
    if (result == JFileChooser.CANCEL_OPTION) {
      None
    } else {
      Some(chooser.getSelectedFile())
    }
  }
  
}
