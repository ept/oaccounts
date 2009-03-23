package org.oaccounts.docgen

import scala.collection.Map
import scala.xml.{Elem, Null, TopScope, PrettyPrinter, NodeSeq, EntityRef, Text, UnprefixedAttribute}

class Attribute(nsMapping: Map[String, Elem], defaultPrefix: String, definitionElem: Elem) {
  
  val name = (definitionElem \ "@name").firstOption.getOrElse(error("Attribute has no name")).toString()
  
  val typeName = (definitionElem \ "@type").firstOption.getOrElse(error("Attribute %s has no type".format(name))).toString()

  val required = (definitionElem \ "@use").firstOption.flatMap(node => Some(node.toString())) match {
    case Some(use) => use == "required"
    case None => false
  }
  
  def htmlDescription: Elem =
    <span class={ if (required) "att required" else "att optional" }>
      { name + "=" }<span class="content">{ typeName }</span>
    </span>
  
  override def toString() = "%s=\"<%s>\"".format(name, typeName)

}
