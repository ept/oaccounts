package org.oaccounts.docgen

import scala.xml.{Elem, Null, TopScope, PrettyPrinter, NodeSeq, EntityRef, Text}
import scala.xml._

class Type(definitionElem: Elem, element: Element) {
  
  lazy val typeName = (definitionElem \ "@name").firstOption.
    getOrElse(error("Definition of type has no name")).
    toString()
  
  def htmlChildren: NodeSeq = element.childSequence.map(
    el => try {
            <div class="elref">
              <a href={el.typeObj.typeName + ".html"}>&lt;{ el.localName }...&gt;</a>
            </div>
          } catch {
            case e: RuntimeException => { 
              println("Warning: %s".format(e))
              <div class="elref">&lt;{ el.localName }...&gt;</div>
            }
          }
  )
  
  def htmlElem: Elem = {
    if (element.childSequence.isEmpty) {
      <div class="el">
        <div class="tag">&lt;{ element.localName }/&gt;</div>
      </div>
    } else {
      <div class="el">
        <div class="tag">&lt;{ element.localName }&gt;</div>
        { Elem(null, "div", new UnprefixedAttribute("class", "ch", Null), TopScope, htmlChildren:_*) }
        <div class="tag">&lt;/{ element.localName }&gt;</div>
      </div>
    }
  }
  
  def writeIncludeFile() {
    val writer = new java.io.FileWriter("doc/_includes/UBL_%s.html".format(typeName))
    writer.write(new PrettyPrinter(100, 4).format(htmlElem))
    writer.close()
  }
  
  def writeFragmentFile() {
    val writer = new java.io.FileWriter("doc/ubl/%s_frag.html".format(typeName))
    writer.write("---\nlayout: nil\n---\n")
    writer.write("{%% include UBL_%s.html %%}".format(typeName))
    writer.close()
  }
  
  def writeFullFile() {
    val writer = new java.io.FileWriter("doc/ubl/%s.html".format(typeName))
    writer.write("---\nlayout: ubldoc\ntitle: %s\n---\n".format(typeName))
    writer.write("{%% include UBL_%s.html %%}".format(typeName))
    writer.close()
  }
  
  def writeAllTypes() {
    writeIncludeFile()
    writeFragmentFile()
    writeFullFile()
    for (child <- element.childSequence) {
      try {
        child.typeObj.writeAllTypes()
      } catch {
        case e: RuntimeException => println("Warning: %s".format(e))
      }
    }
  }
}
