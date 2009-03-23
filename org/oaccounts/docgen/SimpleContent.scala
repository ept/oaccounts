package org.oaccounts.docgen

import scala.collection.Map
import scala.xml.{Elem, Null, TopScope, PrettyPrinter, NodeSeq, EntityRef, Text, UnprefixedAttribute}

class SimpleContent(nsMapping: Map[String, Elem], defaultPrefix: String, refName: String) {

  val (nsPrefix, localName) = if (refName.contains(":")) {
    val splitName = refName.split(":", 2)
    (splitName(0), splitName(1))
  } else (defaultPrefix, refName)

  val xsdDoc = nsMapping(nsPrefix)
  
  lazy val definitionElem = (xsdDoc \ "complexType").find(elem => elem \ "@name" == localName).
    getOrElse((xsdDoc \ "simpleType").find(elem => elem \ "@name" == localName).
      getOrElse(error("Type %s:%s not found".format(nsPrefix, localName)))).
    asInstanceOf[scala.xml.Elem]
  
  lazy val baseName = (definitionElem \\ "extension" \ "@base").firstOption match {
    case Some(name) => Some(name.toString())
    case None => (definitionElem \\ "restriction" \ "@base").firstOption match {
      case Some(name) => Some(name.toString())
      case None => None
    }
  }

  lazy val baseObj = try {
    baseName.flatMap(name => Some(new SimpleContent(nsMapping, nsPrefix, name)))
  } catch {
    case e: java.util.NoSuchElementException => None  // if namespace prefix cannot be resolved to a schema
  }
  
  lazy val finalBaseName: String = baseObj match {
    case Some(base) => try {
      base.finalBaseName
    } catch {
      case e: java.util.NoSuchElementException => baseName.get
    }
    case None => baseName.get
  }
    
  lazy val ownAttrs =
    (definitionElem \\ "attribute").map(el => new Attribute(nsMapping, nsPrefix, el.asInstanceOf[Elem]))
  
  private def mergeAttrs(a1: Seq[Attribute], a2: Seq[Attribute]): Seq[Attribute] = {
    a1 ++ (a2.filter(a2a => a1.filter(a1a => a1a.name == a2a.name).isEmpty))
  }
  
  lazy val attributes: Seq[Attribute] = baseObj match {
    case Some(base) => mergeAttrs(base.attributes, ownAttrs)
    case None => ownAttrs
  }
  
  lazy val htmlAttributes: NodeSeq = attributes.map(a => a.htmlDescription)
  
  lazy val htmlContent = <span class="content">{ finalBaseName }</span>
  
}
