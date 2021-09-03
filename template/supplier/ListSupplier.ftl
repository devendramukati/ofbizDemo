<#if suppliers?has_content>
  <table class="table table-bordered table-striped table-hover">
    <thead>
      <tr>
        <th>${uiLabelMap.PartyPartyId}</th>
        <th>${uiLabelMap.SupplierName}</th>
        <th>${uiLabelMap.SupplierEmail}</th>
        <th>${uiLabelMap.SupplierPhone}</th>
        <th>${uiLabelMap.SupplierPostalAddress}</th>
      </tr>
    </thead>
    <tbody>
      <#list suppliers as supplier>
      <#assign primaryTelecomNumber = (supplier.primaryTelecomNumber)!/>
      <#assign primaryPostalAddress = (supplier.primaryPostalAddress)!/>
      <tr>
   <#-- <td> <a href="<@ofbizUrl>SupplierOverview</@ofbizUrl>?partyId=${(supplier.partyId)!}" class="" id="">${(supplier.partyId)!}</a> </td> -->
        <td> <a href="<@ofbizUrl>SupplierOverviewDetail</@ofbizUrl>?partyId=${(supplier.partyId)!}" class="" id="">${(supplier.partyId)!}</a> </td>
        <td>${(supplier.supplierName)!}</td>
        <td>${(supplier.emailId)!"NA"}</td>
        <td>${(primaryTelecomNumber.contactNumber)!}</td>
        <td>
          <#if (primaryPostalAddress.address1)?has_content>
            ${(primaryPostalAddress.address1)!}
            <#if (primaryPostalAddress.address2)?has_content>
              , ${(primaryPostalAddress.address2)!}
            </#if>
            <#if (primaryPostalAddress.city)?has_content>
              , ${(primaryPostalAddress.city)!}
            </#if>
            <#if (primaryPostalAddress.postalCode)?has_content>
              , ${(primaryPostalAddress.postalCode)!}
            </#if>
            <#if (primaryPostalAddress.stateGeoName)?has_content>
              , ${(primaryPostalAddress.stateGeoName)!}
            </#if>
            <#if (primaryPostalAddress.countryGeoName)?has_content>
              , ${(primaryPostalAddress.countryGeoName)!}
            </#if>
          <#else>
            NA
          </#if>
        </td>
      </tr>
      </#list>
    </tbody>
  </table>
<#else>
  <h5>No Supplier Found..!!</h5>
</#if>