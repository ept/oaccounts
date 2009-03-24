package org.oaccounts.docgen

import scala.xml.PrettyPrinter

/**
 * Documentation generator for UBL and XBRL GL schemas.
 */
object DocGen {
  
  def main(args: Array[String]) {
    
    (new java.io.File("doc/_includes")).mkdirs
    (new java.io.File("doc/ubl")).mkdirs
    
    val invoice = new Element(None, None, new ScanImports("xsd/maindoc/UBL-Invoice-2.0.xsd"), "", "Invoice")
    invoice.writeAllFiles()
    
    /*
    val sbInvoice = new Element(None, None, new ScanImports("xsd/maindoc/UBL-SelfBilledInvoice-2.0.xsd"), "", "SelfBilledInvoice")
    sbInvoice.writeAllFiles()
    
    val creditNote = new Element(None, None, new ScanImports("xsd/maindoc/UBL-CreditNote-2.0.xsd"), "", "CreditNote")
    creditNote.writeAllFiles()
    
    val sbCreditNote = new Element(None, None, new ScanImports("xsd/maindoc/UBL-SelfBilledCreditNote-2.0.xsd"), "", "SelfBilledCreditNote")
    sbCreditNote.writeAllFiles()
    
    val statement = new Element(None, None, new ScanImports("xsd/maindoc/UBL-Statement-2.0.xsd"), "", "Statement")
    statement.writeAllFiles()
    */
    
    //println(new PrettyPrinter(100, 4).format(el.example))
  
    //println(el.childSequence(10).simpleContent.attributes.mkString(" "))
  }
}
