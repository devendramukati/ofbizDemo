package com.companyname.ofbizdemo.events;

import com.companyname.ofbizdemo.services.SupplierServices;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SupplierEvents {
    public static final String module = SupplierEvents.class.getName();
    public static final String resource = "SupplierUiLabels";
    public static String createSupplierEvent(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = UtilHttp.getLocale(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

      //  String supplierName = request.getParameter("supplierName");
        String supplierName = request.getParameter("groupName");
        String phoneNumber = request.getParameter("phoneNumber");

        if (UtilValidate.isEmpty(supplierName) || UtilValidate.isEmpty(phoneNumber)) {
            String errMsg = "Supplier Name and phone number are required fields on the form and can't be empty.";
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        //TESTING
        Debug.log("============ IS CREATE EVENT: " + request.getParameter("isCreate") + "========");
        try {
            Map<String, Object> serviceContext = new HashMap<>();
            // put attributes values
            //TESTING
            serviceContext.put("isCreate", request.getParameter("isCreate"));
            serviceContext.put("userLogin", userLogin);
          //serviceContext.put("supplierName", supplierName);
            serviceContext.put("supplierName", supplierName);

            serviceContext.put("phoneNumber", phoneNumber);
            serviceContext.put("emailId", request.getParameter("emailId"));
            serviceContext.put("address1", request.getParameter("address1"));
            serviceContext.put("address2", request.getParameter("address2"));
            serviceContext.put("city", request.getParameter("city"));
            serviceContext.put("postalCode", request.getParameter("postalCode"));
            serviceContext.put("stateProvinceGeoId", request.getParameter("stateProvinceGeoId"));
            serviceContext.put("countryGeoId", request.getParameter("countryGeoId"));

            Map<String, Object> result = dispatcher.runSync("createSupplier", serviceContext);
            if (ServiceUtil.isError(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), module);
                request.setAttribute("_ERROR_MESSAGE_", ServiceUtil.getErrorMessage(result));
                return "error";
            }
        } catch (GenericServiceException e) {
            String errMsg = UtilProperties.getMessage(resource, "SupplierCreateSupplierErrMsg", locale) + e.toString();
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        request.setAttribute("_EVENT_MESSAGE_", "Supplier created succesfully.");
        return "success";
    }
}
