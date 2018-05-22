// (function() {
//
//     // When the user scrolls
//     $(window).scroll(function() {
//         const footer = $('#footer')[0];
//         const aside = $('#filter-aside-wrapper')[0];
//
//         if (isScrolledIntoView(footer)) {
//            debugger;
//            const footersBox = footer.getBoundingClientRect();
//            const footersTop = footersBox.top;
//            const footersVisibleHeight = window.innerHeight - footersTop;
//
//            const asidesBox = aside.getBoundingClientRect();
//            const asidesBottom = asidesBox.bottom;
//            const asidesTop = asidesBox.top;
//
//            // They are already overlapping
//            if (footersTop < asidesBottom) {
//                const overlapingHeight = asidesBottom - footersTop;
//                const currentAsideHeight = $(aside).css('height');
//                $(aside).css('height', currentAsideHeight - overlapingHeight);
//                return;
//            }
//
//            const asidesCurrentHeight = $(aside).css('height');
//            const extraSpace = footersTop - asidesBottom;
//            console.log(`extraSpace: ${extraSpace}`);
//
//            $(aside).css('height', asidesCurrentHeight + extraSpace);
//         } else {
//             const asidesBox = aside.getBoundingClientRect();
//             const asidesBottom = asidesBox.bottom;
//             const asidesTop = asidesBox.top;
//             $(aside).css('height', window.innerHeight - asidesTop - 20);
//         }
//     });
//
//
//
//     // Returns true if the passed in element is visible in the viewport.
//     function isScrolledIntoView(element) {
//         const rectangle = element.getBoundingClientRect();
//         const {top, bottom} = rectangle;
//         const isVisible = (top >= 0) && (bottom <= window.innerHeight);
//         return isVisible;
//     }
// })();