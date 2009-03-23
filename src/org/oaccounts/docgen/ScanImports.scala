package org.oaccounts.docgen

import scala.collection.Map
import scala.collection.mutable.{HashMap, HashSet}
import scala.xml.{Elem, XML}
import java.io.File
import java.net.URL

class ScanImports(filename: String) extends Map[String, Elem] {

  var nsToURL = new HashMap[String, URL]
  var nsToSchema = new HashMap[String, Elem]
  var urlToSchema = new HashMap[URL, Elem]
  var prefixToSchema = new HashMap[String, Elem]
  var prefixUnknown = new HashSet[String]
  
  prefixToSchema += "" -> loadFile(None, filename)

  
  def loadFile(sourceURL: Option[URL], referenceURL: String): Elem = {
    val targetURL = sourceURL match {
      case Some(url) => new URL(url, referenceURL)
      case None => new File(referenceURL).getCanonicalFile().toURI().toURL()
    }
    if (targetURL.getProtocol() != "file") error("Protocol %s is not supported for XML loading".format(targetURL.getProtocol()))
    
    val xml = XML.loadFile(targetURL.getPath())
    val targetNS = (xml \ "@targetNamespace").firstOption.
      getOrElse(error("Schema %s has no targetNamespace attribute".format(targetURL))).
      toString(true)
    println("Namespace %s -> File %s".format(targetNS, targetURL.getPath()))
    
    nsToURL += targetNS -> targetURL
    nsToSchema += targetNS -> xml
    urlToSchema += targetURL -> xml
    xml
  }
  
  def get(prefix: String): Option[Elem] = {
    if (prefixUnknown.contains(prefix)) return None
    
    if (!prefixToSchema.contains(prefix)) {
      // we do not yet have an import for this prefix. try to find one.
      for (nsURI <- nsToSchema.values.map(schema => schema.getNamespace(prefix)).filter(uri => uri != null)) {
        println("Namespace %s -> Prefix %s".format(nsURI, prefix))
        // the schema for that namespace has already been loaded
        if (nsToSchema.contains(nsURI)) {
          prefixToSchema += prefix -> nsToSchema.get(nsURI).get
        }
        
        // not loaded: try to find an import for that namespace URI
        for ((schemaURL, schema) <- urlToSchema) {
          for (location <- (schema \\ "import").filter(el => el \ "@namespace" == nsURI) \ "@schemaLocation") {
            prefixToSchema += prefix -> loadFile(Some(schemaURL), location.toString())
          }
        }
      }
    }
    
    if (!prefixToSchema.contains(prefix)) prefixUnknown += prefix
    prefixToSchema.get(prefix)
  }
  
  def size: Int = prefixToSchema.size
  
  def elements: Iterator[(String, Elem)] = prefixToSchema.elements
  
}
