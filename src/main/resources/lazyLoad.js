$(function() {
  /* Expects tabs created with a `href`s that have `data-entity` and `data-id` attributes, like the following.
   SmartTab does this.
    <ul class="nav nav-tabs">
     <li data-toggle="tooltip" title="blah blah"><a href='#LectureAbout' data-toggle='tab' data-entity='lecture' data-id='62'>blah blah</a></li>
   </ul> */
  $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    var attributes = e.target.attributes;
    var href = attributes.href.value;
    var i = href.indexOf("#");
    var tabName = href;
    if (i>=0) tabName = href.substring(i+1);
    var fetch = $(href).data("fetch");
    if ("lazy"===fetch) {
      $(href).html("<p style='margin: 2em'>Loading...</p>");
      var artifact = attributes.getNamedItem("data-entity").value;
      var id = attributes.getNamedItem("data-id").value;
      var url = "/demandLoad/" + tabName + "/" + artifact + "/" + id;
      $.get(url, function(data) {
        //console.log(url + " returned: " + data);
        $(href).data("fetch", "done");
        $(href).html(data);
        solutionsHandler();
      });
    }
  });
});
