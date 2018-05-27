(function () {

    // Variables for notification
    const notifSuccessMessage = "Pridėta!";
    const notifPosition = "bottom center";
    const notifHidesAfter = 2000; // 2 seconds

    let wasInitRun = false;
    if (!wasInitRun) {
        init();
        wasInitRun = true;
    }

    function init() {

        // Create style for the bigger version of notification
        $.notify.addStyle('big', {
            html: `<div>
                        <div class="notifyjs-bootstrap-base notifyjs-bootstrap-success" >
                            <p class="text-center margin-0">
                                <span data-notify-text />
                            </p>
                        </div>
                    </div>`,
        });

        // Set defaults
        $.notify.defaults({
            autoHideDelay: notifHidesAfter,
            position: notifPosition,
            className: "success",
        });
    }

    // When added to cart in list
    $('.js-add-to-cart').on('click', function () {
        $(this).notify(notifSuccessMessage);
    });

    // When added to cart in details page
    $('.js-add-to-cart-details').on('click', function (e) {
        // Get the button bellow which we will show the notification
        const $submitButton = $($(this).closest('input')[0]);

        // Get the button's dimensions and location
        const buttonWidth = $submitButton.outerWidth();
        const buttonHeight = $submitButton.outerHeight();
        const {left, top} = $submitButton.offset();

        // Show the notification
        const test = $(this).notify(notifSuccessMessage, {
            style: 'big',
            arrowShow: false,
        });

        // Change the notifications position according to the button
        const topGap = 5; // Gap between notification and button
        const notificationPosition = {
            left: left,
            top: top + buttonHeight + topGap
        };
        $('.notifyjs-container')
        .offset(notificationPosition)   // change position
        .width(buttonWidth);            // change width
    });
})();