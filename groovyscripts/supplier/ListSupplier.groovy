import org.apache.ofbiz.party.party.PartyHelper
suppliers = []
partyRoles = from("PartyRole").where("roleTypeId", "SUPPLIER").queryList()
partyRoles.each { partyRole ->
    supplierInfo = [:]
    supplierInfo.partyId = partyRole.partyId
    supplierInfo.supplierName = PartyHelper.getPartyName(delegator, partyRole.partyId, false)
    emailPartyContactMechPurpose = from("PartyContactMechPurpose").where("partyId", partyRole.partyId, "contactMechPurposeTypeId", "PRIMARY_EMAIL").filterByDate().queryFirst()
    if (emailPartyContactMechPurpose) {
        emailContactMech = select("infoString").from("ContactMech").where("contactMechId", emailPartyContactMechPurpose.contactMechId).queryOne()
        supplierInfo.emailId = emailContactMech?.infoString
    }

    phonePartyContactMechPurpose = from("PartyContactMechPurpose").where("partyId", partyRole.partyId, "contactMechPurposeTypeId", "PRIMARY_PHONE").filterByDate().queryFirst()
    if (phonePartyContactMechPurpose) {
        phoneContactMech = from("TelecomNumber").where("contactMechId", phonePartyContactMechPurpose.contactMechId).queryOne()
        supplierInfo.primaryTelecomNumber = phoneContactMech
    }

    addressPartyContactMechPurpose = from("PartyContactMechPurpose").where("partyId", partyRole.partyId, "contactMechPurposeTypeId", "PRIMARY_LOCATION").filterByDate().queryFirst()
    if (addressPartyContactMechPurpose) {
        supplierAddressMap = [:]
        supplierPostalAddress = from("PostalAddress").where("contactMechId", addressPartyContactMechPurpose.contactMechId).queryOne()
        if (supplierPostalAddress) {
            supplierAddressMap.putAll(supplierPostalAddress)
            stateGeo = from("Geo").where("geoId", supplierPostalAddress.stateProvinceGeoId).queryOne()
            supplierAddressMap.stateName = stateGeo?.geoName
            countryGeo = from("Geo").where("geoId",supplierPostalAddress.countryGeoId).queryOne()
            supplierAddressMap.countryName = countryGeo?.geoName
        }
        supplierInfo.primaryPostalAddress = supplierAddressMap
    }
    suppliers.add(supplierInfo)
}
//context.suppliers = suppliers
result = runService('getSuppliers', [userLogin : userLogin])
context.suppliers = result.suppliers
println(suppliers)