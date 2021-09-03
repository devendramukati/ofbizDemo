<#if supplierInfo?has_content>
     <#assign primaryTelecomNumber = (supplierInfo.primaryTelecomNumber)!/>
     <dl class="dl-horizontal">
       <dt>${uiLabelMap.PartyPartyId}</dt>
       <dd>${(supplierInfo.partyId)!}</dd>

       <dt>${uiLabelMap.SupplierName}</dt>
       <dd>${(supplierInfo.supplierName)!"NA"}</dd>

       <dt>${uiLabelMap.SupplierEmail}</dt>
       <dd>${(supplierInfo.emailId)!"NA"}</dd>

       <dt>${uiLabelMap.SupplierPhone}</dt>
       <dd>${(primaryTelecomNumber.contactNumber)!"NA"}</dd>
     </dl>
  <#else>
    <h3>No Supplier Found..!!</h3>
</#if>
