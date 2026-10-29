package section01.subsection03.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import section01.common.BookDAO;
import section01.common.BookDTO;

import java.util.List;

@Service("bookServiceConstructor")
public class BookService {

    private BookDAO bookDAO;

    // 생성자 주입
    // BookService 빈을 생성할때 BookDAO 타입의 빈을 생성자 인자로 전달한다.
    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    // 도서목록 조회
    public List<BookDTO> selectAllBooks() {

        return bookDAO.selectBookList();
    }

    // sequence로 도서 조회
    public BookDTO searchBookBySequence(int sequence) {

        return bookDAO.selectOneBook(sequence);
    }

}
