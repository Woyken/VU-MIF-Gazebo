package lt.vu.mif.ui.paging;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.utils.constants.Constants;

@Setter
@Getter
public class Paging {
    private static final int PAGES_TO_SHOW = 5;

    private int index = 0;
    private int activePage = Constants.ACTIVE_PAGE;
    private int pageSize = Constants.PAGE_SIZE;
    private int totalPages;

    public Paging() {
    }

    public Paging(int totalPages) {
        this.totalPages = totalPages;
    }

    public void next() {
        if (activePage + 1 < totalPages) {
            activePage++;
        }

        if (activePage >= PAGES_TO_SHOW) {
            index++;
        }
    }

    public void previous() {
        if (activePage> 0) {
            activePage--;
        }

        if (index > 0) {
            index --;
        }
    }

    public void reset() {
        activePage = Constants.ACTIVE_PAGE;
        totalPages = 0;
        index = 0;
    }

    public Object[] getTotalPages() {
        return new Object[totalPages];
    }

    public int getPagesToShow() {
        return PAGES_TO_SHOW;
    }

    public int getMinValue() {
        return totalPages >= PAGES_TO_SHOW ? PAGES_TO_SHOW : totalPages;
    }
}