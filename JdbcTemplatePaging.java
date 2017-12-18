import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.data.repository.support.PageableExecutionUtils.TotalSupplier;

/**
 * @param content list from jdbcTemplate
 * @param pageNum : zero-based, if null default to zero
 * @param pageSize : if null default list size
 * @return Page object (zero-based)
 */
public static <T> Page<T> pagingList(List<T> content, Integer pageNum, Integer pageSize){
    Assert.notNull(content, "List content must not be null!");
    pageNum = (pageNum == null) ? 0 : pageNum;
    pageSize = (pageSize == null) ? content.size() : pageSize;

    PageRequest pageable = new PageRequest(pageNum, pageSize);

    //System.out.println(" ======== PAGEABLE ========= ");
    //System.out.println(" pageable.getOffset     : " + pageable.getOffset());
    //System.out.println(" pageable.getPageNumber : " + pageable.getPageNumber());
    //System.out.println(" pageable.getPageSize   : " + pageable.getPageSize());

    TotalSupplier totalSupplier = new TotalSupplier() {

        @Override
        public long get() {
            return content.size();
        }
    };

    int fromOffset = pageable.getOffset();
    int toOffset =  Math.min(content.size(), fromOffset + pageSize);
    List<T> listPaging = new ArrayList<>();
    if(fromOffset < toOffset){
        listPaging = content.subList(fromOffset, toOffset);
    }
    return PageableExecutionUtils.getPage(listPaging, pageable, totalSupplier);
}