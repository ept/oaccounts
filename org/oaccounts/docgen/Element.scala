package org.oaccounts.docgen

import scala.collection.mutable.ListBuffer
import scala.collection.Map
import scala.xml.{Elem, Null, TopScope}

class Element(parent: Option[Element], nsMapping: Map[String, Elem], defaultPrefix: String, refName: String) {

  val (nsPrefix, localName) = if (refName.contains(":")) {
    val splitName = refName.split(":", 2)
    (splitName(0), splitName(1))
  } else (defaultPrefix, refName)
  
  println(path)
  
  lazy val xsdDoc = nsMapping(nsPrefix)
  
  lazy val definitionElem = (xsdDoc \ "element").find(elem => elem \ "@name" == localName).
    getOrElse(error("Definition of element %s not found".format(localName)))
  
  lazy val typeName = (definitionElem \ "@type").firstOption.
    getOrElse(error("Definition of element %s has no type name".format(localName))).
    toString()
  
  lazy val typeElem = (xsdDoc \ "complexType").find(elem => elem \ "@name" == typeName).
    getOrElse(error("Type %s not found".format(typeName))).
    asInstanceOf[scala.xml.Elem]
  
  lazy val typeObj = new Type(typeElem, this)
  
  lazy val childSequence: ListBuffer[Element] = {
    var list = new ListBuffer[Element]
    try {
      val sequence = typeElem \ "sequence" \ "element"
      for (child <- sequence) {
        try {
          val elRef = (child \ "@ref").firstOption.
                        getOrElse(error("Type %s has sequence/element child without @ref".format(typeName))).
                        toString()
          val el = new Element(Some(this), nsMapping, nsPrefix, elRef)
          if (isRecursive(el)) error("Recursive type definition in element %s".format(elRef))
          list += el
        } catch {
          case e: NullPointerException => e.printStackTrace()
          case e: RuntimeException => println("Warning: %s".format(e))
        }
      }
    } catch {
      case e: NullPointerException => e.printStackTrace()
      case e: RuntimeException => println("Warning: %s".format(e))
    }
    list
  }
  
  def example: Elem = {
    val children = childSequence.map(ch => ch.example)
    Elem(null, localName, Null, TopScope, children:_*)
  }
  
  def printChildren() {
    for (childElem <- typeElem \ "sequence" \ "element") {
      println(childElem \ "@ref")
    }
  }
  
  def path: String = parent match {
    case Some(p) => "%s / %s".format(p.path, localName)
    case None => localName
  }
  
  def isRecursive(newEl: Element): Boolean = {
    (localName == newEl.localName) || (parent match {
      case Some(p) => p.isRecursive(newEl)
      case None => false
    })
  }
  
}
