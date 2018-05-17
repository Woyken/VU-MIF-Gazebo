(function() {
    $('.js-add-to-cart').on('click', function(e) {
       $(this).notify("Pridėta!", {
           autoHideDelay: 2000,
           position: "bottom center",
           className: "success",
       });
    });
})();