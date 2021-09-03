import org.apache.ofbiz.entity.util.EntityQuery
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.base.util.UtilProperties
import org.apache.ofbiz.entity.util.EntityUtilProperties
//test = 1

//context.put("test", test)

//context.test = test

//println(test+ "++++++++++++++++++" + context.test)
//println("!======================!" + parameters.headerItem)

countries = EntityQuery.use(delegator).from("Geo").where("geoTypeId", "COUNTRY").orderBy("geoId").queryList()

//println("================ " + countries + " =============")

context.countries = countries

states = EntityQuery.use(delegator).from("Geo").where("geoTypeId", "STATE").orderBy("geoId").queryList()

//println("+++++++++++++++" + states + "++++++++++++++")
context.states = states

//context.defaultCountryGeoId = UtilProperties.getPropertyValue("supplier", "default.supplier.countryGeoId", "USA")
//context.defaultStateGeoId = UtilProperties.getPropertyValue("supplier", "default.supplier.stateProvinceGeoId", "NY")
//println("============= TESTING PROPERTY COUNTRY:" + context.defaultCountry + "=======================")
//println("============= TESTING PROPERTY STATE:" + context.defaultState + "=======================")

context.defaultCountryGeoId = EntityUtilProperties.getPropertyValue("supplier", "default.supplier.countryGeoId", "USA", delegator)
context.defaultStateGeoId = EntityUtilProperties.getPropertyValue("supplier", "default.supplier.stateProvinceGeoId", "NY", delegator)
println("============= TESTING PROPERTY COUNTRY:" + context.defaultCountryGeoId + "=======================")
println("============= TESTING PROPERTY STATE:" + context.defaultStateGeoId + "=======================")


//Define a map in Groovy

//supplierInfo = [:]
//supplierInfo.countries

// List<GenericValue> countries = EntityQuery.use(delegator).from("Geo").where("geoTypeId", "COUNTRY").orderBy("geoId").queryList()
//println("================ " + countries + " =============")
//context.put("countries", countries)
