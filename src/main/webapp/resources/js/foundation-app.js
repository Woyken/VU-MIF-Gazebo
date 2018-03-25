(function () {
    $(document).foundation();

    $('#login-form-register').on('click', function(e) {
       e.preventDefault();
       $('#login-modal').foundation('close');
       $('#register-modal').foundation('open');
    });
})();
