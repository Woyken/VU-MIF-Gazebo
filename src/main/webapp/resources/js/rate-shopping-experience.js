(function() {
    // Turn on Foundation
    $(document).foundation();

    const selectorRateExperienceModal = '#rate-shopping-experience-modal';
    const selectorRatingStars = '.rating-stars';
    const selectorStar = '.rating-star';

    const attributeModalShouldOpen = 'data-js-show';
    const attributeStarValue = 'data-value';

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
       unmarkAllStars(e.target.parentElement);
       markStarsAsSelected(e.target.parentElement, starNumber);
    });

    $(selectorStar).on('click', function(e) {
        const starNumber = $(e.target).attr(attributeStarValue);
        unmarkAllStars(e.target.parentElement);
        markStarsAsSelected(e.target.parentElement, starNumber);
        e.target.parentElement.currentlySelectedStars = starNumber;
    });

    $(selectorRatingStars).on('mouseleave', function(e) {
        unmarkAllStars(e.target.parentElement);
        if (e.target.parentElement.currentlySelectedStars === undefined || e.target.parentElement.currentlySelectedStars === 0) {
            return;
        }
        markStarsAsSelected(e.target.parentElement, e.target.parentElement.currentlySelectedStars);
    });


    /* Make it seem like no star has been selected */
    function unmarkAllStars(parent) {
        const children = parent.children;
        for (let i = 0; i < children.length; i++) {
            const child = $(children[i]);
            child.removeClass(classFilledStar);
            child.addClass(classEmptyStar);
        }
    }

    /* Make it seem like some stars have been selected
    * ARGS
    *   numberOfStars: number of stars to be marked as selected. */
    function markStarsAsSelected(parent, numberOfStars) {
        for (let i = 0; i < numberOfStars; i++) {
            const childStar = $(parent.children[i]);
            childStar.removeClass(classEmptyStar);
            childStar.addClass(classFilledStar);
        }
    }

})();