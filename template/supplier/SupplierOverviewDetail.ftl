<#-- This will include screens in ftl -->
${screens.render("component://ofbizDemo/widget/SupplierScreens.xml#SupplierDetail")}
${screens.render("component://ofbizDemo/widget/SupplierScreens.xml#SupplierAddress")}

<#-- This will include ftl in ftl
<#include "SupplierDetail.ftl"/>
<#include "SupplierAddress.ftl"/> -->
