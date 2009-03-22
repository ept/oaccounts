package org.oaccounts.docgen

import scala.xml.PrettyPrinter

/**
 * Documentation generator for UBL and XBRL GL schemas.
 */
object DocGen {
  
  def main(args: Array[String]) {
    
    val el = new Element(None, new ScanImports("os-UBL-2.0/xsd/maindoc/UBL-Invoice-2.0.xsd"), "", "Invoice")
    println(new PrettyPrinter(100, 4).format(el.example))
    
    // println(new PrettyPrinter(80, 4).format(xml))
    
    /*
    val result = xml match {
      case <ubl:Invoice>{ ch @ _* }</ubl:Invoice> => println(new PrettyPrinter(80, 4).format(ch))
    }
    */
    /*
    var tax_total = 0.0
    var net_total = 0.0
    
    for (line <- xml \\ "InvoiceLine") {
      for (tax_amount <- line \ "TaxTotal" \ "TaxAmount") {
        tax_total += tax_amount.text.toDouble
      }
      for (net_amount <- line \ "LineExtensionAmount") {
        net_total += net_amount.text.toDouble
      }
    }
    
    println("Tax total: " + tax_total)
    println("Net total: " + net_total)
    
    val result = xml match {
      case Elem(pref, tag, attr, scope, ch @ _*) => Elem(pref, "badger", attr, scope, ch : _*)
      // case <Invoice>{ ch @ _* }</Invoice> => <Invoice>{ ch }</Invoice>
    }
    println(new PrettyPrinter(80, 4).format(result))
    */
  }
}
