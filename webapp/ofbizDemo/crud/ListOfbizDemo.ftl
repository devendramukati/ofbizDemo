<table class="table table-bordered table-striped table-hover">
    <thead>
        <tr>
          <th>${uiLabelMap.OfbizDemoId}</th>
          <th>${uiLabelMap.OfbizDemoType}</th>
          <th>${uiLabelMap.OfbizDemoFirstName}</th>
          <th>${uiLabelMap.OfbizDemoLastName}</th>
          <th>${uiLabelMap.OfbizDemoComment}</th>
        </tr>
    </thead>
    <tbody>
        <#list ofbizDemoList as ofbizDemo>
            <tr>
              <td>${ofbizDemo.ofbizDemoId}</td>
              <td>${ofbizDemo.getRelatedOne("OfbizDemoType").get("description", locale)}</td>
              <td>${ofbizDemo.firstName?default("NA")}</td>
              <td>${ofbizDemo.lastName?default("NA")}</td>
              <td>${ofbizDemo.comments!}</td>
            </tr>
        </#list>
    </tbody>
</table>
