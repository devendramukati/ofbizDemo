package com.companyname.ofbizdemo.services;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.party.party.PartyHelper;
import org.apache.ofbiz.service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierServices {

    public static final String module = SupplierServices.class.getName();

    public static Map<String, Object> createSupplier(DispatchContext dctx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        String partyId = null;
        String telecomContactMechId = null;
        String emailContactMechId = null;
        String postalContactMechId = null;
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map<String, Object> serviceContext = new HashMap();
        //Testing purpose
        Debug.log("======================== IS CREATE SERVICE: " + context.get("isCreate") + "======");

        try {
            Debug.log("================= Creating Supplier =============");
            serviceContext.put("userLogin", userLogin);
            serviceContext.put("groupName", context.get("supplierName"));
            result = dispatcher.runSync("createPartyGroup", serviceContext);
            if (ServiceUtil.isError(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), module);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
            partyId = (String) result.get("partyId");
            Debug.log("================" + partyId + "==================");

            Debug.log("================= Supplier phone number =============");
            serviceContext.clear(); //clearing before re-use
            serviceContext.put("userLogin", userLogin);
            serviceContext.put("contactNumber", context.get("phoneNumber"));
            serviceContext.put("partyId", partyId);
            serviceContext.put("contactMechPurposeTypeId", "PRIMARY_PHONE");
            result = dispatcher.runSync("createPartyTelecomNumber", serviceContext);
            if (ServiceUtil.isError(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), module);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
            telecomContactMechId = (String) result.get("contactMechId");

            Debug.log("================ telecomContactMechId = " + telecomContactMechId + "==================");

            //Block for email information
            Debug.log("================= Supplier Email id =============");
            serviceContext.clear(); //clearing before re-use
            serviceContext.put("userLogin", userLogin);
            serviceContext.put("emailAddress", context.get("emailId"));
            serviceContext.put("partyId", partyId);
            serviceContext.put("contactMechPurposeTypeId", "PRIMARY_EMAIL");
            result = dispatcher.runSync("createPartyEmailAddress", serviceContext);
            if (ServiceUtil.isError(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), module);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
            emailContactMechId = (String) result.get("contactMechId");

            Debug.log("================ emailContactMechId = " + emailContactMechId + "==================");

            //Block for supplier postal address
            Debug.log("================= Supplier Address =============");
            serviceContext.clear();
            serviceContext = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, context);
            Debug.log("===================== CITY: " + context.get("city") + "=====================");
            serviceContext.put("partyId", partyId);
            serviceContext.put("contactMechPurposeTypeId", "PRIMARY_LOCATION");
            result = dispatcher.runSync("createPartyPostalAddress", serviceContext);
            if (ServiceUtil.isError(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), module);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
            postalContactMechId = (String) result.get("contactMechId");

            Debug.log("================= Supplier Role =============");
            serviceContext.clear(); //clearing before re-use
            serviceContext.put("userLogin", userLogin);
            serviceContext.put("partyId", partyId);
            serviceContext.put("roleTypeId", "SUPPLIER");
            result = dispatcher.runSync("createPartyRole", serviceContext);
            if (ServiceUtil.isError(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), module);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }

            Debug.log("================= Set Supplier Relationship =============");
            serviceContext.clear(); //clearing before re-use
            serviceContext.put("userLogin", userLogin);
            serviceContext.put("partyIdTo", partyId);
            serviceContext.put("partyIdFrom", "Company");
            serviceContext.put("roleTypeIdFrom", "INTERNAL_ORGANIZATIO");
            serviceContext.put("roleTypeIdTo", "SUPPLIER");
            result = dispatcher.runSync("createPartyRelationship", serviceContext);
            if (ServiceUtil.isError(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), module);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(e.getMessage());
        }
        Debug.log("================ postalContactMechId = " + postalContactMechId + "==================");
        result = ServiceUtil.returnSuccess();
        result.put("partyId", partyId);
        return result;
    }

    public static Map<String, Object> getSuppliers(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        List<Map<String, Object>> suppliers = new ArrayList<Map<String, Object>>();
        try {
              List<GenericValue> partyRoles = EntityQuery.use(delegator).from("PartyRole").where("roleTypeId", "SUPPLIER").queryList();
              for(GenericValue partyRole: partyRoles) {
                  Map<String, Object> supplierInfo = new HashMap();
                  supplierInfo.put("partyId", partyRole.getString("partyId"));
                  supplierInfo.put("supplierName", PartyHelper.getPartyName(delegator, partyRole.getString("partyId"), false));
                  GenericValue emailAddress =  EntityQuery.use(delegator).from("PartyContactDetailByPurpose").where("partyId", partyRole.getString("partyId"), "contactMechPurposeTypeId", "PRIMARY_EMAIL").filterByDate().queryFirst();
                  if (UtilValidate.isNotEmpty(emailAddress)) {
                      supplierInfo.put("emailId", emailAddress.getString("infoString"));
                  }
                  GenericValue phoneNumber = EntityQuery.use(delegator).from("PartyContactDetailByPurpose").where("partyId", partyRole.getString("partyId"), "contactMechPurposeTypeId", "PRIMARY_PHONE").filterByDate().queryFirst();
                  if (UtilValidate.isNotEmpty(phoneNumber)) {
                      supplierInfo.put("primaryTelecomNumber", phoneNumber);
                  }
                  GenericValue postalAddress = EntityQuery.use(delegator).from("PartyContactDetailByPurpose").where("partyId", partyRole.getString("partyId"), "contactMechPurposeTypeId", "PRIMARY_LOCATION").filterByDate().queryFirst();
                  if (UtilValidate.isNotEmpty(postalAddress)) {
                      supplierInfo.put("primaryPostalAddress", postalAddress);
                  }
                  suppliers.add(supplierInfo);
              }
        } catch(GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(e.getMessage());
        }
        result.put("suppliers", suppliers);
        return result;
    }

    public static Map<String, Object> sendCreateSupplierNotification(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Debug.log("======sendCreateSupplierNotification======== PARTY_ID: " + context.get("partyIdTo") + "=====================");
        //TODO: Need to call send email Notification service
        return result;
    }
}




