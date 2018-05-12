(function() {
    // Turn on Foundation
    $(document).foundation();

    const selectorRateExperienceModal = '#rate-shopping-experience-modal';
    const selectorRatingStars = '.rating-stars';
    const selectorStar = '.rating-star';

    const attributeModalShouldOpen = 'data-js-show';
    const attributeStarValue = 'data-value';

    let currentlySelectedStars = 1;

    // The 'empty' and 'filled' classes will only work if the class 'rating-star' is set
    const classEmptyStar = 'empty';
    const classFilledStar = 'filled';


    /* Check if modal should be opened at all */
    $(document).ready(function() {
        const shouldBeOpened = $(selectorRateExperienceModal).attr(attributeModalShouldOpen);
        if (shouldBeOpened === "true") {
            $(selectorRateExperienceModal).foundation('open');
        }
    });

    $(selectorStar).on('mouseover', function(e) {
       const starNumber = $(e.target).attr(attributeStarValue);
       unmarkAllStars();
       markStarsAsSelected(starNumber);
    });

    $(selectorStar).on('click', function(e) {
        const starNumber = $(e.target).attr(attributeStarValue);
        unmarkAllStars();
        markStarsAsSelected(starNumber);
        currentlySelectedStars = starNumber;
    });

    $(selectorRatingStars).on('mouseleave', function(e) {
        unmarkAllStars();
        if (currentlySelectedStars === 0) {
            return;
        }
        markStarsAsSelected(currentlySelectedStars);
    });


    /* Make it seem like no star has been selected */
    function unmarkAllStars() {
        const parent = $(selectorRatingStars)[0];
        const children = parent.children;
        for (var i = 0; i < children.length; i++) {
            const child = $(children[i]);
            child.removeClass(classFilledStar);
            child.addClass(classEmptyStar);
        }
    }

    /* Make it seem like some stars have been selected
    * ARGS
    *   numberOfStars: number of stars to be marked as selected. */
    function markStarsAsSelected(numberOfStars) {
        const parent = $(selectorRatingStars)[0];
        for (var i = 0; i < numberOfStars; i++) {
            const childStar = $(parent.children[i]);
            childStar.removeClass(classEmptyStar);
            childStar.addClass(classFilledStar);
        }
    }

})();