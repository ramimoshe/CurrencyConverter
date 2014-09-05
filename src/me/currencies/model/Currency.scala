
package me.currencies.model

/**
 * <pre> Currency Definition <pre/>
 * @param name the name of the currency
 * @param unit the unit of the currency
 * @param currencyCode the code of the currency
 * @param country the country of the currency
 * @param rate the rate of the currency
 * @param change the change of the currency
 */
class Currency(name : String ,unit : Int , currencyCode : String , country : String ,rate : Double ,change : Double){

  var m_name = name
  var m_unit = unit
  var m_currencyCode = currencyCode
  var m_country = country
  var m_rate = rate
  var m_change = change

}

