<#assign primaryPostalAddress = (supplierInfo.primaryPostalAddress)!/>
<#if primaryPostalAddress?has_content>

     <dl class="dl-horizontal">
       <dt>${uiLabelMap.SupplierAddressOne}</dt>
       <dd>
         <#if (primaryPostalAddress.address1)?has_content>
           ${(primaryPostalAddress.address1)!}
         <#else>
           NA
         </#if>
       </dd>

       <dt>${uiLabelMap.SupplierAddressTwo}</dt>
       <dd>${(primaryPostalAddress.address2)!"NA"}</dd>

       <dt>${uiLabelMap.SupplierCity}</dt>
       <dd>${(primaryPostalAddress.city)!"NA"}</dd>

       <dt>${uiLabelMap.SupplierPostalCode}</dt>
       <dd>${(primaryPostalAddress.postalCode)!"NA"}</dd>

       <dt>${uiLabelMap.SupplierState}</dt>
       <dd>
         <#if (primaryPostalAddress.stateName)?has_content>
           ${(primaryPostalAddress.stateName)!}
         <#else>
           NA
         </#if>
       </dd>

       <dt>${uiLabelMap.SupplierCountry}</dt>
       <dd>${(primaryPostalAddress.countryName)!"NA"}</dd>
     </dl>
  <#else>
    <h4>No Address Found..!!</h4>
</#if>