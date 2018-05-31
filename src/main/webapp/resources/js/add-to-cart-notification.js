(function() {

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
        showSmallNotification(this, true);
    });

    // When added to cart in details
    $('.js-add-to-cart-details').on('click', function (e) {
        showBigNotification(this, true);
    });


    // Shown in details page
    function showBigNotification(context, usesThis) {
        // Get the button bellow which we will show the notification
        const $submitButton = $($($(context).closest('input')[0])[0]);

        // Get the button's dimensions and location
        const buttonWidth = $submitButton.outerWidth();
        const buttonHeight = $submitButton.outerHeight();
        const {left, top} = $submitButton.offset();

        // Show the notification
        const test = $(context).notify(notifSuccessMessage, {
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
    }


    // Shown in list
    // This method is called two ways:
    // 1. With the onClick event naturally (listening to it), in which case context = this
    // 2. When attaching via 'DOMSubtreeModified' which passes as context the outer wrapper.
    function showSmallNotification(context, usesThis) {
        if (usesThis == 'undefined' || !usesThis) {
            context = context.target;
        }
        $(context).notify(notifSuccessMessage);
    }


    // When filtering takes place, for some reason, the onClick event detaches, so here we reattach it.
    $('body').on('DOMSubtreeModified', '#productList', function() {
        $('.js-add-to-cart').off('click');
        $('.js-add-to-cart').on('click', showSmallNotification);
    });

})();