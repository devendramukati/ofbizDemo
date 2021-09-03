<div class="screenlet-body">
  <form id="createOfbizDemoEvent" method="post" action="<@ofbizUrl>createSupplier</@ofbizUrl>">
    <input type="hidden" name="isCreate" value="Y"/>

    <fieldset>

      <div class="control-group">
        <label class="control-label" for="groupName">${uiLabelMap.SupplierName}</label>
         <div class="controls">
           <input type="text" name="groupName" id="groupName" class='required' maxlength="100" />*
        </div>
      </div>

     <#-- <div class="control-group">
        <label class="control-label" for="supplierName">${uiLabelMap.SupplierName}</label>
        <div class="controls">
          <input type="text" name="supplierName" id="supplierName" class='required' maxlength="100" />*
        </div>
      </div> -->

      <div class="control-group">
        <label class="control-label" for="phoneNumber">${uiLabelMap.SupplierPhone}</label>
        <div class="controls">
          <input type="text" name="phoneNumber" id="phoneNumber" class='required' maxlength="13" />*
        </div>
      </div>

      <div class="control-group">
        <label class="control-label" for="emailId">${uiLabelMap.SupplierEmail}</label>
        <div class="controls">
          <input type="email" name="emailId" id="emailId" class='required' maxlength="50" />*
        </div>
      </div>

      <div class="control-group">
        <label class="control-label" for="address1">${uiLabelMap.SupplierAddressOne}</label>
        <div class="controls">
          <input type="text" name="address1" id="address1" class='required' maxlength="200" />*
        </div>
      </div>

      <div class="control-group">
        <label class="control-label" for="address2">${uiLabelMap.SupplierAddressTwo}</label>
        <div class="controls">
          <input type="text" name="address2" id="address2" maxlength="100" />
        </div>
      </div>

      <div class="control-group">
        <label class="control-label" for="cityName">${uiLabelMap.SupplierCity}</label>
        <div class="controls">
          <input type="text" name="city" id="cityName" class='required' maxlength="100" />*
        </div>
      </div>

      <div class="control-group">
         <label class="control-label" for="postalCode">${uiLabelMap.SupplierPostalCode}</label>
         <div class="controls">
           <input type="text" name="postalCode" id="postalCode" class='required' maxlength="10" />*
         </div>
      </div>

      <div class="control-group">
         <label class="control-label" for="stateProvinceGeoId">${uiLabelMap.SupplierState}</label>
         <div class="controls">
            <select id="stateProvinceGeoId" name="stateProvinceGeoId">
              <#list states as state>
                <option value="${(state.geoId)!}" <#if state.geoId == defaultStateGeoId>selected</#if> >${(state.geoName)!"-"}</option>
              </#list>
            </select>
         </div>
      </div>

      <div class="control-group">
          <label class="control-label" for="countryGeoId">${uiLabelMap.SupplierCountry}</label>
          <div class="controls">
            <select id="countryGeoId" name="countryGeoId">
              <#list countries as country>
                <option value="${(country.geoId)!}" <#if country.geoId == defaultCountryGeoId>selected</#if> >${(country.geoName)!"-"}</option>
              </#list>
            </select>
          </div>
        </div>

    </fieldset>

    <input type="submit" value="${uiLabelMap.SupplierCreate}" />

  </form>
</div>