$(document).ready(function() {
  var $shortenUrl = $("#shorten_url");
  var $shortenBtn = $("#shorten_btn");
  var $urlDivider = $(".url__divider");
  var $copyBtn = $(".copy__btn");
  var clipboard = new Clipboard(".copy__btn");

  clipboard.on('success', function(e) {
    $urlDivider.text("Copied!");
    $copyBtn.css("display", "none");
  });

  $shortenBtn.click(function() {
    if ($shortenUrl.val() == "" || $shortenUrl.val() == null) {
        alert("Olá amigo! Cole primeiro sua url :)");
        return;
    }
    $.ajax({
        type: "post",
        url: "/api/structure/urls",
        data: JSON.stringify({ "originLink": document.getElementById("shorten_url").value }),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            var location = window.location;
            var url = location.protocol.concat("//", location.host, "/", data.linkId);
            $urlDivider.text(url);
            $copyBtn.css("display", "block");
            $copyBtn.attr("data-clipboard-text", url);
            $shortenUrl.val("");
        },
        error: function(data) {
            alert(data.message);
        }
    });
  });
});
