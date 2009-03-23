package org.oaccounts.docgen

import scala.collection.mutable.ListBuffer
import scala.collection.Map
import scala.xml.{Elem, Null, TopScope, PrettyPrinter, NodeSeq, EntityRef, Text, UnprefixedAttribute}

class Element(parent: Option[Element], nsMapping: Map[String, Elem], defaultPrefix: String, refName: String) {

  val (nsPrefix, localName) = if (refName.contains(":")) {
    val splitName = refName.split(":", 2)
    (splitName(0), splitName(1))
  } else (defaultPrefix, refName)
  
  if (false) println(path) // for debugging
  
  lazy val xsdDoc = nsMapping(nsPrefix)
  
  lazy val definitionElem = (xsdDoc \ "element").find(elem => elem \ "@name" == localName).
    getOrElse(error("Definition of element %s not found".format(localName)))
  
  lazy val typeName = (definitionElem \ "@type").firstOption.
    getOrElse(error("Definition of element %s has no type name".format(localName))).
    toString()
  
  lazy val typeElem = (xsdDoc \ "complexType").find(elem => elem \ "@name" == typeName).
    getOrElse(error("Type %s not found".format(typeName))).
    asInstanceOf[scala.xml.Elem]
  
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

  def isSimple: Boolean = childSequence.isEmpty
  
  def htmlChildren: NodeSeq = childSequence.map(
    el => try {
            if (el.isSimple) el.htmlElem else {
              <div class="elref">
                <a href={el.localName + ".html"}>&lt;{ el.localName }...&gt;</a>
              </div>
            }
          } catch {
            case e: RuntimeException => { 
              println("Warning: %s".format(e))
              <div class="elref">&lt;{ el.localName }...&gt;</div>
            }
          }
  )
  
  def htmlSimpleContent: NodeSeq =
    <span class="content">{ typeElem \ "simpleContent" \ "extension" \ "@base" }</span>
  
  def htmlElem: Elem = {
    if (isSimple) {
      <div class="el">
        <span class="tag">&lt;{ localName }&gt;</span>
        { htmlSimpleContent }
        <span class="tag">&lt;/{ localName }&gt;</span>
      </div>
    } else {
      <div class="el">
        <div class="tag">&lt;{ localName }&gt;</div>
        { Elem(null, "div", new UnprefixedAttribute("class", "ch", Null), TopScope, htmlChildren:_*) }
        <div class="tag">&lt;/{ localName }&gt;</div>
      </div>
    }
  }
  
  def writeIncludeFile() {
    val writer = new java.io.FileWriter("doc/_includes/UBL_%s.html".format(localName))
    writer.write(new PrettyPrinter(100, 4).format(htmlElem))
    writer.close()
  }
  
  def writeFragmentFile() {
    val writer = new java.io.FileWriter("doc/ubl/%s_frag.html".format(localName))
    writer.write("---\nlayout: nil\n---\n")
    writer.write("{%% include UBL_%s.html %%}".format(localName))
    writer.close()
  }
  
  def writeFullFile() {
    val writer = new java.io.FileWriter("doc/ubl/%s.html".format(localName))
    writer.write("---\nlayout: ubldoc\ntitle: %s\n---\n".format(localName))
    writer.write("{%% include UBL_%s.html %%}".format(localName))
    writer.close()
  }
  
  def writeAllFiles() {
    writeIncludeFile()
    writeFragmentFile()
    writeFullFile()
    for (child <- childSequence) {
      try {
        child.writeAllFiles()
      } catch {
        case e: RuntimeException => println("Warning: %s".format(e))
      }
    }
  }
}
