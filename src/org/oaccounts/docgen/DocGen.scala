package org.oaccounts.docgen

import scala.xml.PrettyPrinter

/**
 * Documentation generator for UBL and XBRL GL schemas.
 */
object DocGen {
  
  def main(args: Array[String]) {
    
    val invoice = new Element(None, None, new ScanImports("os-UBL-2.0/xsd/maindoc/UBL-Invoice-2.0.xsd"), "", "Invoice")
    invoice.writeAllFiles()
    
    /*
    val sbInvoice = new Element(None, None, new ScanImports("os-UBL-2.0/xsd/maindoc/UBL-SelfBilledInvoice-2.0.xsd"), "", "SelfBilledInvoice")
    sbInvoice.writeAllFiles()
    
    val creditNote = new Element(None, None, new ScanImports("os-UBL-2.0/xsd/maindoc/UBL-CreditNote-2.0.xsd"), "", "CreditNote")
    creditNote.writeAllFiles()
    
    val sbCreditNote = new Element(None, None, new ScanImports("os-UBL-2.0/xsd/maindoc/UBL-SelfBilledCreditNote-2.0.xsd"), "", "SelfBilledCreditNote")
    sbCreditNote.writeAllFiles()
    
    val statement = new Element(None, None, new ScanImports("os-UBL-2.0/xsd/maindoc/UBL-Statement-2.0.xsd"), "", "Statement")
    statement.writeAllFiles()
    */
    
    //println(new PrettyPrinter(100, 4).format(el.example))
  
    //println(el.childSequence(10).simpleContent.attributes.mkString(" "))
  }
}
